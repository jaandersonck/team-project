package entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import interfaces.GenericCloneable;

public class CommitTree implements GenericCloneable<CommitTree> {
    private final String branchName;
    private final Commit node;

    /**
     * The next newer commit on the same branch; following it moves forward in time.
     */
    private CommitTree nextCommit;
    private ArrayList<CommitTree> branches;

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
     * @return a deep clone of the tree
    */
    @Override 
    public CommitTree clone() {
        CommitTree tree = new CommitTree(branchName, node);
        tree.nextCommit = nextCommit;
        tree.branches = branches;
        return tree;
    }
}
