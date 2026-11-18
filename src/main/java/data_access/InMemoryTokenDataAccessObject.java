package data_access;

import use_case.login.LoginTokenDataAccessInterface;

public class InMemoryTokenDataAccessObject implements LoginTokenDataAccessInterface {
    private String token;

    public InMemoryTokenDataAccessObject(String token) {
        this.token = token;
    }

    public InMemoryTokenDataAccessObject() {
        token = "";
    }

    /**
     * Save the token in memory, replacing any previously stored token.
     * @param token the token to save
     */
    @Override
    public void save(String token) {
        this.token = token;
    }

    /**
     * Returns the currently stored token.
     * @return the stored token, or an empty string if none has been saved
     */
    public String getToken() { return token; }
}