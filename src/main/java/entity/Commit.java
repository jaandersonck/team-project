package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Commit {
    public final String sha;

    /**
     * SHAs of this commit's parents, in the order the GitHub API lists them.
     * Empty for the first commit, one for a normal commit, two or more for a merge
     */
    public final List<String> parentShas;

    public final String author;
    public final String message;
    public final Date date;
    public final String branchName;
    public final ArrayList<String> editedFiles;

    public Commit(String sha, List<String> parentShas, String author, String message, Date date,
                  String branchName, ArrayList<String> editedFiles) {
        this.sha = Objects.requireNonNull(sha, "sha");
        this.parentShas = List.copyOf(parentShas);
        this.author = author;
        this.message = message;
        this.date = date;
        this.branchName = branchName;
        this.editedFiles = editedFiles;
    }

    /**
     * Two commits are equal if they have the same SHA.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Commit)) {
            return false;
        }
        return sha.equals(((Commit) o).sha);
    }

    @Override
    public int hashCode() {
        return sha.hashCode();
    }

    @Override
    public String toString() {
        return author + "\n" + message + "\n" + date + "\n" + branchName;
    }
}
