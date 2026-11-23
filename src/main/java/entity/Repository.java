package entity;

public class Repository {

    private final String name;
    private final User owner;

    public Repository(String name, User owner){
        if ("".equals(name)) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if ("".equals(owner.getName())) {
            throw new IllegalArgumentException("Owner cannot be empty");
        }
        this.name = name;
        this.owner = owner;
    }
    public String getName() { return name; }

    public User getOwner() { return owner; }

    /**
     * The raw repository with no edits
     */
    private CommitTree gitTreePure;
    /**
     * The repository as visible to the user, may be edited to fulfill certain use cases such as date restrictions
     */
    private CommitTree viewingTree;
}
