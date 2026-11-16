package use_case.restore_session;

import entity.User;

/**
 * DAO interface for saved sessions for the Restore Session use case
 */
public interface RestoreSessionTokenDataAccessInterface {

    /**
     * Retrieve a saved session token
     * @return a potentially valid authentication token, or the empty string
     */
    String getToken();
}
