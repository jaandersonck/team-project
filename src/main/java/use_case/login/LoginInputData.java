package use_case.login;

/**
 * The Input Data for the Login Use Case.
 */
public class LoginInputData {

    private final String token;
    private final boolean stayLoggedIn;

    public LoginInputData(String token, boolean stayLoggedIn) {
        this.token = token;
        this.stayLoggedIn = stayLoggedIn;
    }

    String getToken() {
        return token;
    }
    boolean isStayLoggedIn() { return stayLoggedIn; }
}