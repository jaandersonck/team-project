package interface_adapter.login;

/**
 * The state for the Login View Model.
 */
public class LoginState {
    private String token = "";
    private String loginError;
    private boolean stayLoggedIn;

    public String getToken() { return token; }

    public String getLoginError() {
        return loginError;
    }

    public void setToken(String token) { this.token = token; }

    public void setLoginError(String usernameError) {
        this.loginError = usernameError;
    }
}
