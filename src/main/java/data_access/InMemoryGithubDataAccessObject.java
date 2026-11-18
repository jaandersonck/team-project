package data_access;

import java.util.HashMap;
import java.util.Map;

import use_case.login.LoginUserDataAccessInterface;
import entity.User;

/**
 * A fake DataAccessObject replicating the behaviour of the GitHub API.
 */
public class InMemoryGithubDataAccessObject implements LoginUserDataAccessInterface {
    private final Map<String, User> userMap;
    private User currentUser;

    /**
     * Creates a fake repository with the given users.
     * @param userMap a map from token to the user associated with that token
     */
    public InMemoryGithubDataAccessObject(Map<String, User> userMap) {
        this.userMap = new HashMap<String, User>(userMap);
    }

    /**
     * Check if there's a user associated with the provided token.
     * @param token the GitHub api token
     * @return true if a user with the given token exists; false otherwise
     */
    @Override
    public boolean tokenValid(String token) {
        return userMap.containsKey(token);
    }

    /**
     * Returns a user from the given token.
     * @param token the token to look up
     * @return the user associated with the provided token
     */
    @Override
    public User getUser(String token) {
        return userMap.get(token);
    }

    /**
     * Sets the user who is currently using the app.
     * @param user the user to mark as logged in
     */
    @Override
    public void setCurrentUser(User user) {
        currentUser = user;
    }
}