package entity;

import java.util.ArrayList;
import interfaces.GenericCloneable;

public class CommitTree implements GenericCloneable<CommitTree> {
    private String branchName;
    private Commit node;
    private CommitTree nextCommit;
    private ArrayList<CommitTree> branches;

    /**
     * 
     * @param cloned the object to be cloned from
     * @return
    */
    @Override 
    public CommitTree clone() {
        CommitTree tree = new CommitTree();
        tree.branchName = branchName;
        tree.node = node;
        tree.nextCommit = nextCommit;
        tree.branches = branches;
        return tree;
    }
}
