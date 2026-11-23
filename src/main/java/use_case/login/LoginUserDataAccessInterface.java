package use_case.login;

import entity.User;
import use_case.DataAccessException;

/**
 * DAO interface for the Login Use Case.
 */
public interface LoginUserDataAccessInterface {

    /**
     * Check if there's a user associated with the provided token.
     * @param token the GitHub api token
     * @return true if a user with the given token exists; false otherwise
     * @throws DataAccessException if the check could not be completed
     */
    boolean tokenValid(String token) throws DataAccessException;

    /**
     * Returns a user from the given token.
     * @param token the token to look up
     * @return the user associated with the provided token
     * @throws DataAccessException if the user could not be fetched
     */
    User getUser(String token) throws DataAccessException;

    /**
     * Sets the user who is currently using the app.
     * @param user the user to mark as logged in
     */
    void setCurrentUser(User user);
}