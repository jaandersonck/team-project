package interface_adapter.login;

/**
 * The state for the Login View Model.
 */
public class LoginState {
    private String token = "";
    private String loginError;
    private boolean stayLoggedIn;

    /**
     * Returns the token entered in the login view.
     * @return the token
     */
    public String getToken() { return token; }

    /**
     * Returns the error message from the last failed login attempt.
     * @return the login error, or null if there is none
     */
    public String getLoginError() {
        return loginError;
    }

    /**
     * Returns whether the user chose to stay logged in.
     * @return true if the token should be saved; false otherwise
     */
    public boolean isStayLoggedIn() {
        return stayLoggedIn;
    }

    /**
     * Sets the token entered in the login view.
     * @param token the token
     */
    public void setToken(String token) { this.token = token; }

    /**
     * Sets the error message to show for a failed login attempt.
     * @param usernameError the login error
     */
    public void setLoginError(String usernameError) {
        this.loginError = usernameError;
    }

    /**
     * Sets whether the user chose to stay logged in.
     * @param stayLoggedIn true if the token should be saved; false otherwise
     */
    public void setStayLoggedIn(boolean stayLoggedIn) {
        this.stayLoggedIn = stayLoggedIn;
    }
}