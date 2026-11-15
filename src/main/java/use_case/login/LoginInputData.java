package use_case.login;

/**
 * The Input Data for the Login Use Case.
 */
public class LoginInputData {

    private final String token;

    public LoginInputData(String token) {
        this.token = token;
    }

    String getToken() {
        return token;
    }
}
