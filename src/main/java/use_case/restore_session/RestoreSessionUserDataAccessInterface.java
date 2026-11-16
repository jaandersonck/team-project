package use_case.restore_session;

import entity.User;

/**
 * DAO interface for the Restore Session use case
 */
public interface RestoreSessionUserDataAccessInterface {
    /**
     * Check if there's a user associated with the provided token
     * @param token the GitHub api token
     * @return true if a user with the given token exists; false otherwise
     */
    boolean tokenValid(String token);

    /**
     * Returns a user from the given token.
     * @param token the token to look up
     * @return the user associated with the provided token
     */
    User getUser(String token);

    // Business logic state: who is currently using the app?
    void setCurrentUser(User user);
}
