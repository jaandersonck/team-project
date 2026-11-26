package entity;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Internally held representation of the git repo
 */
public class Repository {
    public final String owner;
    public final String name;

    private Collection<Commit> commits;

    /**
     * The raw repository with no edits
     */
    private CommitTree gitTreePure;
    /**
     * The repository as visible to the user, may be edited to fulfill certain use cases such as date restrictions
     */
    private CommitTree viewingTree;

    public Repository(String name, String owner) {
        this.name = name;
        this.owner = owner;
    }

    /**
     * Returns the repository's full commit tree, as fetched.
     * @return the pure commit tree, or null if it hasn't been set yet
     */
    public CommitTree getGitTreePure() {
        return gitTreePure;
    }

    /**
     * Sets the repository's full commit tree, and resets the viewing tree to a copy of it.
     * @param gitTreePure the commit tree as fetched, or null to clear both trees
     */
    public void setGitTreePure(CommitTree gitTreePure) {
        this.gitTreePure = gitTreePure;
        if(gitTreePure == null) {
            viewingTree = null;
        } else {
            viewingTree = gitTreePure.genericClone();
        }
    }

    /**
     * Returns the commit tree as currently shown to the user.
     * @return the viewing tree, or null if no tree has been set yet
     */
    public CommitTree getViewingTree() {
        return viewingTree;
    }

    /**
     * Sets the commit tree shown to the user, e.g. after filtering by date.
     * @param viewingTree the commit tree to show
     */
    public void setViewingTree(CommitTree viewingTree) {
        this.viewingTree = viewingTree;
    }
}
