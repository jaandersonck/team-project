package entity;

/**
 * An entity representing a GitHub user.
 */
public class User {

    private final String username;
    private final String name;
    private final String email;

    /**
     * Creates a new user given profile info.
     * @param username the username
     * @param name the display name
     * @param email the user's email
     */
    public User(String username, String name, String email) {
        this.username = username;
        this.name = name;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

}
