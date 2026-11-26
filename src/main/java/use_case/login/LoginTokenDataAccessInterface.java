package use_case.login;

import use_case.DataAccessException;

/**
 * DAO interface for the login use case's access of persisted tokens.
 */
public interface LoginTokenDataAccessInterface {
    /**
     * Save the token.
     * @param token the token to save
     * @throws DataAccessException if the token could not be saved
     */
    void save(String token) throws DataAccessException;
}