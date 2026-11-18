package GitRepresentation;

import java.util.ArrayList;

public class CommitTree {
    private String branchName;
    private Commit node;
    private CommitTree nextCommit;
    private ArrayList<CommitTree> branches;
}
