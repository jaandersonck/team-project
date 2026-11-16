package use_case.restore_session;

/**
 * Output Data for the Restore Session use case
 */
public class RestoreSessionOutputData {

    private final String username;
    private final String name;
    private final String email;

    public RestoreSessionOutputData(String username, String name, String email) {
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
