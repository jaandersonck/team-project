package use_case.logout;

import entity.User;

/**
 * DAO interface for the Logout Use Case.
 */
public interface LogoutUserDataAccessInterface {
    /**
     * Define who is operating the program
     * @param user the new current User
     */
    void setCurrentUser(User user);
}
