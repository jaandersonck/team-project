package entity;

/** 
 * Internally held representation of the git repo
 */
public class Repository {
    private String name;
    /**
     * The raw repository with no edits
     */
    private CommitTree gitTreePure;
    /**
     * The repository as visible to the user, may be edited to fulfill certain use cases such as date restrictions
     */
    private CommitTree viewingTree;

    public Repository(CommitTree repo, String name) {
        gitTreePure = repo;
        viewingTree = gitTreePure.clone();
        this.name = name;
    }
}
