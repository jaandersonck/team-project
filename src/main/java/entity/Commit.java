package entity;

import java.util.ArrayList;
import java.util.Date;

public class Commit {
    public final String author;
    public final String message;
    public final Date date;
    public final String branchName; 
    public final ArrayList<String> editedFiles;

    @Override 
    public String toString() {
        return author + "\n" + message + "\n" + date + "\n" + branchName;
    }

    public Commit(String author, String message, Date date, String branchName, ArrayList<String> editedFiles) {
        this.author = author;
        this.message = message;
        this.date = date;
        this.branchName = branchName;
        this.editedFiles = editedFiles;
    }
}