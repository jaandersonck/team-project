package use_case.login;

import entity.User;

/**
 * DAO interface for the Login Use Case.
 */
public interface LoginUserDataAccessInterface {

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

    /**
     * Sets the user who is currently using the app.
     * @param user the user to mark as logged in
     */
    void setCurrentUser(User user);
}