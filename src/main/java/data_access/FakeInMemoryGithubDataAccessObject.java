package data_access;

import java.util.HashMap;
import java.util.Map;

import use_case.login.LoginUserDataAccessInterface;
import entity.User;

// This is a fake DataAccessObject replicating the behaviour of the GitHubAPI.

public class FakeInMemoryGithubDataAccessObject implements LoginUserDataAccessInterface {
    private final Map<String, User> userMap;
    private User currentUser;

    /**
     * Creates a fake repository with the given users.
     * @param userMap a map from token to the user associated with that token
     */
    public FakeInMemoryGithubDataAccessObject(Map<String, User> userMap) {
        this.userMap = new HashMap<String, User>(userMap);
    }

    @Override
    public boolean tokenValid(String token) {
        return userMap.containsKey(token);
    }

    @Override
    public User getUser(String token) {
        return userMap.get(token);
    }

    @Override
    public void setCurrentUser(User user) {
        currentUser = user;
    }
}
