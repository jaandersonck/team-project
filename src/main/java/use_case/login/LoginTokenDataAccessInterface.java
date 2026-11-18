package use_case.login;

import entity.User;

/**
 * DAO interface for the login use case's access of persisted tokens.
 */
public interface LoginTokenDataAccessInterface {
    /**
     * Save the token.
     * @param token the token to save
     */
    void save(String token);
}