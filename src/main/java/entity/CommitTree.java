package entity;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import interfaces.GenericCloneable;

public class CommitTree implements GenericCloneable<CommitTree> {
    private final String branchName;
    private final Commit node;

    /**
     * The next newer commit on the same branch; following it moves forward in time.
     */
    private CommitTree nextCommit;
    private final ArrayList<CommitTree> branches;

    /**
     * Creates a tree node for one commit, with no next commit or branches yet.
     * @param branchName the branch this commit belongs to, or null if unknown
     * @param node the commit at this node
     */
    public CommitTree(String branchName, Commit node) {
        this.branchName = branchName;
        this.node = node;
        this.branches = new ArrayList<>();
    }

    /**
     * Creates the fake root that every line of history branches off of.
     * Only {@link CommitTreeFactory} creates one.
     * @return a fake root with no branches yet
     */
    static CommitTree createFakeRoot() {
        return new CommitTree(null, null);
    }

    /**
     * Identify whether this is the fake root.
     * @return true if this is the fake root
     */
    public boolean isFakeRoot() {
        return node == null;
    }

    /**
     * Returns the branch this commit belongs to.
     * @return the branch name, or null if unknown
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * Returns the commit at this node.
     * @return the commit, or null for the fake root
     */
    public Commit getCommit() {
        return node;
    }

    /**
     * Returns the next newer commit on the same branch.
     * @return the next commit, or null if this is the branch's newest commit
     */
    public CommitTree getNextCommit() {
        return nextCommit;
    }

    /**
     * Returns the branches that split off at this commit.
     * @return a read-only list of the branches
     */
    public List<CommitTree> getBranches() {
        return Collections.unmodifiableList(branches);
    }

    /**
     * Sets the next newer commit on the same branch. Only {@link CommitTreeFactory} links nodes.
     * @param next the next commit
     */
    void setNextCommit(CommitTree next) {
        this.nextCommit = next;
    }

    /**
     * Adds a branch that splits off at this commit. Only {@link CommitTreeFactory} links nodes.
     * @param branch the first node of the branch
     */
    void addBranch(CommitTree branch) {
        branches.add(branch);
    }

    /**
     * Deep clone this node. {@code Commit}s themselves are immutable, so they don't need to be cloned.
     */
    @Override
    public CommitTree genericClone() {
        CommitTree copy = new CommitTree(branchName, node);

        // Traverse the tree, keeping track of the original and copy in parallel
        Deque<CommitTree> originals = new ArrayDeque<>();
        Deque<CommitTree> copies = new ArrayDeque<>();
        originals.push(this);
        copies.push(copy);

        while (!originals.isEmpty()) {
            CommitTree original = originals.pop();
            CommitTree originalCopy = copies.pop();

            if (original.nextCommit != null) {
                CommitTree nextCopy = new CommitTree(original.nextCommit.branchName,
                        original.nextCommit.node);
                originalCopy.nextCommit = nextCopy;

                // original corresponds with its copy
                originals.push(original.nextCommit);
                copies.push(nextCopy);
            }
            for (CommitTree branch : original.branches) {
                CommitTree branchCopy = new CommitTree(branch.branchName, branch.node);
                originalCopy.branches.add(branchCopy);

                // original corresponds with its copy
                originals.push(branch);
                copies.push(branchCopy);
            }
        }
        return copy;
    }
}
