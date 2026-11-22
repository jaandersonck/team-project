package entity;

import java.sql.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Builds a {@link CommitTree} from the flat commit lists the GitHub API returns.

 * git stores commits with information about their parents, not their children. This means
 * the only way to build a top-down tree is to start from the bottom. If a commit has
 * 0 parents, we consider it a root. 1 parent is considered a regular commit, and >= 2 parents
 * is considered a merge.

 * To solve some inconsistencies with a potentially incomplete commit history, and cases such as
 * multiple roots, every commit tree starts from a fake root, not assigned to a commit. Then,
 * we represent all branches available to us as branches off of this root. This also allows
 * for the case that we don't have the complete history of a branch; we'll treat the earliest known
 * commit as its root.

 * Every other commit is placed after its first parent: a child on the same branch continues the
 * line, and a child on another branch starts one of the parent's {@code branches}. A limitation of
 * the tree is that every node can only have one parent - merge commits can be found in {@link Commit#parentShas}.
 */
public class CommitTreeFactory {

    /**
     * Builds the tree for a single branch.
     * @param branchName the branch the commits were listed from
     * @param commits the branch's commits, in any order. This list must either be empty, or contain a root commit.
     * @return the fake root of the tree; it has no branches if there are no commits
     */
    public CommitTree generateCommitTree(String branchName, Collection<Commit> commits) {
        LinkedHashMap<String, String> headShaByBranch = new LinkedHashMap<>();

        if(commits.isEmpty()) { return CommitTree.createFakeRoot(); }

        // findHeadSha(commits) guaranteed to be not null since commits nonempty
        headShaByBranch.put(branchName, findHeadSha(commits));
        return generateCommitTreeFromHeads(headShaByBranch, commits);
    }


    /**
     * Builds one tree covering several branches, from each branch's own commit list.
     * Branches with no commits are dropped.
     * @param commitsByBranchName each branch's name mapped to the commits listed from it, in any
     *                            order, with the default branch first; commits shared between
     *                            branches may repeat
     * @return the fake root of the tree; it has no branches if there are no commits
     */
    public CommitTree generateCommitTree(LinkedHashMap<String, Collection<Commit>> commitsByBranchName) {
        LinkedHashMap<String, String> headShaByBranch = new LinkedHashMap<>();

        if(commitsByBranchName.isEmpty()) { return CommitTree.createFakeRoot(); }

        for (String branchName : commitsByBranchName.keySet()) {
            Collection<Commit> branchCommits = commitsByBranchName.get(branchName);

            if(branchCommits.isEmpty()) { continue; }

            // findHeadSha(branchCommits) is guaranteed to be not null, since branchCommits nonempty
            headShaByBranch.put(branchName, findHeadSha(branchCommits));
        }

        Collection<Commit> flatCommits = new ArrayList<>();

        // duplicates are fine, as they'll be ignored by the next method
        for(Collection<Commit> branchCommits : commitsByBranchName.values()) {
            flatCommits.addAll(branchCommits);
        }

        return generateCommitTreeFromHeads(headShaByBranch, flatCommits);
    }

    /**
     * Builds one tree covering several branches.
     * @param headShaByBranch each branch's name mapped to the SHA of its newest commit, with the default branch first.
     *                        Every head SHA must map to a commit.
     * @param commits every fetched commit from those branches, in any order; duplicates are ignored
     * @return the fake root of the tree
     */
    public CommitTree generateCommitTreeFromHeads(LinkedHashMap<String, String> headShaByBranch,
                                                  Collection<Commit> commits) {
        CommitTree fakeRoot = CommitTree.createFakeRoot();
        Map<String, Commit> bySha = mapBySha(commits);
        if (bySha.isEmpty()) {
            return fakeRoot;
        }

        Map<String, String> branchNameBySha = labelBranches(headShaByBranch, bySha);
        Map<String, List<Commit>> childrenBySha = mapChildren(bySha);
        List<Commit> roots = findRoots(bySha);

        // Generate the default branch's line first
        Commit defaultRoot = findDefaultRoot(headShaByBranch, bySha, roots);
        fakeRoot.addBranch(buildLine(defaultRoot, branchNameBySha, childrenBySha));
        for (Commit lineStart : roots) {
            if (!lineStart.equals(defaultRoot)) {
                fakeRoot.addBranch(buildLine(lineStart, branchNameBySha, childrenBySha));
            }
        }
        return fakeRoot;
    }

    /**
     * Map SHAs to {@code Commit}s
     * @param commits the commits to map
     * @return each commit's SHA mapped to the commit, in the order given
     */
    private Map<String, Commit> mapBySha(Collection<Commit> commits) {
        Map<String, Commit> bySha = new LinkedHashMap<>();
        for (Commit commit : commits) {
            bySha.putIfAbsent(commit.sha, commit);
        }
        return bySha;
    }

    /**
     * Determine what branch each commit belongs to.

     * Under the assumption that the default branch is first in {@code headShaByBranch}, main
     * will claim its commits first, as indented. Every following branch will claim as many
     * commits before it hits a commit that's already been claimed.

     * Commits that have inaccessible parents are not assigned to a branch.
     *
     * @param headShaByBranch each branch's name mapped to its head's SHA, default branch first
     * @param bySha the fetched commits, by SHA
     * @return each claimed commit's SHA mapped to its branch name
     */
    private Map<String, String> labelBranches(LinkedHashMap<String, String> headShaByBranch,
                                              Map<String, Commit> bySha) {
        Map<String, String> branchNameBySha = new HashMap<>();

        for (String branchName : headShaByBranch.keySet()) {
            // commit initialized as the head of the branch
            Commit commit = bySha.get(headShaByBranch.get(branchName));

            // traverse up the branch until a root and label
            while (commit != null && !branchNameBySha.containsKey(commit.sha)) {
                branchNameBySha.put(commit.sha, branchName);
                commit = bySha.get(firstParentSha(commit));
            }
        }
        return branchNameBySha;
    }

    /**
     * Map parents to children, so that we have forward relationships instead of backwards.
     * @param bySha the fetched commits, by SHA
     * @return each commit's SHA mapped to the fetched commits whose first parent it is
     */
    private Map<String, List<Commit>> mapChildren(Map<String, Commit> bySha) {
        Map<String, List<Commit>> childrenBySha = new HashMap<>();
        for (Commit commit : bySha.values()) {
            String firstParent = firstParentSha(commit);
            if (bySha.containsKey(firstParent)) {
                childrenBySha.putIfAbsent(firstParent, new ArrayList<>());
                childrenBySha.get(firstParent).add(commit);
            }
        }
        return childrenBySha;
    }

    /**
     * Find and return all commits that don't have parents, which we'll consider roots.
     * @param bySha the fetched commits, by SHA
     * @return the commits with no fetched first parent, in the order given
     */
    private List<Commit> findRoots(Map<String, Commit> bySha) {
        List<Commit> roots = new ArrayList<>();
        for (Commit commit : bySha.values()) {
            if (!bySha.containsKey(firstParentSha(commit))) {
                roots.add(commit);
            }
        }
        return roots;
    }

    /**
     * Finds the default/main branch's root commit, or the oldest commit on its path that has been
     * fetched. Resort to the next available branch if the head is for some reason unreachable.
     * @param headShaByBranch each branch's name mapped to its head's SHA, default branch first.
     * @param bySha fetched commits by sha
     * @param roots every line start, used if no branch's head was fetched
     * @return the oldest available commit on the default branch. If the head is unreachable on the default branch,
     * return the root of the next available branch.
     */
    private Commit findDefaultRoot(LinkedHashMap<String, String> headShaByBranch,
                                   Map<String, Commit> bySha, List<Commit> roots) {
        for (String headSha : headShaByBranch.values()) {
            Commit commit = bySha.get(headSha);
            if (commit == null) { continue; }

            while (bySha.containsKey(firstParentSha(commit))) {
                commit = bySha.get(firstParentSha(commit));
            }
            return commit;
        }

        return roots.get(0);
    }

    /**
     * Builds the line starting at {@code start}, along with every line that branches off it.
     * A child on the same branch continues the line; any other child starts a branch.
     * @param start the oldest commit of the line
     * @param branchNameBySha each commit's SHA mapped to its branch name, if it has one
     * @param childrenBySha each commit's SHA mapped to its first children
     * @return the node for {@code start}
     */
    private CommitTree buildLine(Commit start, Map<String, String> branchNameBySha,
                                 Map<String, List<Commit>> childrenBySha) {
        CommitTree startNode = new CommitTree(branchNameBySha.get(start.sha), start);
        Deque<CommitTree> toVisit = new ArrayDeque<>();
        toVisit.push(startNode);

        while (!toVisit.isEmpty()) {
            CommitTree node = toVisit.pop();
            List<Commit> children = childrenBySha.get(node.getCommit().sha);
            if(children == null) { continue; }

            for (Commit child : children) {
                CommitTree childNode = new CommitTree(branchNameBySha.get(child.sha), child);
                boolean sameBranch = Objects.equals(childNode.getBranchName(), node.getBranchName());
                if (sameBranch && node.getNextCommit() == null) {
                    node.setNextCommit(childNode);
                }
                else {
                    node.addBranch(childNode);
                }
                toVisit.push(childNode);
            }
        }
        return startNode;
    }

    /**
     * Finds the head of one branch's commits: the only commit that no other commit lists as a
     * parent, i.e. the newest one available.

     * For commit list of size >= 1 there will always be a head commit, a commit with no children;
     * the alternative is a cycle, and cycles cannot exist in git.

     * @param commits one branch's commits, in any order
     * @return the head's SHA, or null if there are no commits
     */
    private String findHeadSha(Collection<Commit> commits) {
        Set<String> parentShas = new HashSet<>();
        for (Commit commit : commits) {
            parentShas.addAll(commit.parentShas);
        }

        for (Commit commit : commits) {
            if (!parentShas.contains(commit.sha)) {
                return commit.sha;
            }
        }
        return null;
    }

    /**
     * Returns a commit's first parent, which is the previous commit on the same branch.
     * @param commit the commit
     * @return the first parent's SHA, or null for a root commit
     */
    private String firstParentSha(Commit commit) {
        return commit.parentShas.isEmpty() ? null : commit.parentShas.get(0);
    }
}
