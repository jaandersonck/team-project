package data_access;

import java.util.HashMap;
import java.util.Map;

import use_case.login.LoginUserDataAccessInterface;
import entity.User;
import use_case.restore_session.RestoreSessionUserDataAccessInterface;

// This is a fake DataAccessObject replicating the behaviour of the GitHubAPI.

public class FakeInMemoryGithubDataAccessObject implements LoginUserDataAccessInterface,
        RestoreSessionUserDataAccessInterface {
    private final Map<String, User> userMap = new HashMap<String, User>();
    private User currentUser;

    /**
     * Add a user to the fake repository by a token
     * @param token anything
     * @param user anything
     */
    public void addUser(String token, User user) {
        userMap.put(token, user);
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
