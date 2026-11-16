package use_case.logout;

/**
 * DAO interface for the Logout Use Case, specifically tokens.
 */
public interface LogoutTokenDataAccessInterface {
    /**
     * Save the token.
     * @param token the token to save
     */
    void save(String token);
}
