package use_case.login;

/**
 * Output Data for the Login Use Case.
 */
public class LoginOutputData {

    private final String username;
    private final String name;
    private final String email;

    public LoginOutputData(String username, String name, String email) {
        this.username = username;
        this.name = name;
        this.email = email;
    }

    /**
     * Returns the GitHub username of the logged-in user.
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the display name of the logged-in user.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the email address of the logged-in user.
     * @return the email
     */
    public String getEmail() {
        return email;
    }
}