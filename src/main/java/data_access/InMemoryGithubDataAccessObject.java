package data_access;

import java.util.HashMap;
import java.util.Map;

import entity.User;
import use_case.login.LoginUserDataAccessInterface;

/**
 * A fake data access object that stands in for the GitHub API, for tests and offline use.
 */
public class InMemoryGithubDataAccessObject implements LoginUserDataAccessInterface {
    private final Map<String, User> userByToken;

    private String currentToken;
    private User currentUser;

    /**
     * Creates a fake GitHub that knows the given users.
     * @param userByToken each token mapped to the user it belongs to
     */
    public InMemoryGithubDataAccessObject(Map<String, User> userByToken) {
        this.userByToken = new HashMap<>(userByToken);
    }

    /**
     * Checks whether a user is associated with the token.
     * @param token the GitHub API token
     * @return true if a user with the given token exists; false otherwise
     */
    @Override
    public boolean tokenValid(String token) {
        return userByToken.containsKey(token);
    }

    /**
     * Returns the user the token belongs to.
     * @param token the GitHub API token
     * @return the user the token belongs to, or null if there isn't one
     */
    @Override
    public User getUser(String token) {
        return userByToken.get(token);
    }

    /**
     * Sets the token the app authenticates with from now on.
     * @param token the token to authenticate with
     */
    @Override
    public void setCurrentToken(String token) {
        currentToken = token;
    }

    /**
     * Sets the user who is currently using the app.
     * @param user the user to mark as logged in
     */
    @Override
    public void setCurrentUser(User user) {
        currentUser = user;
    }

    /**
     * Returns the token set by the last successful login.
     * @return the current token, or null if no login has succeeded
     */
    public String getCurrentToken() {
        return currentToken;
    }

    /**
     * Returns the user set by the last successful login.
     * @return the current user, or null if no login has succeeded
     */
    public User getCurrentUser() {
        return currentUser;
    }
}
