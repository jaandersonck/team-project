package fake_data_access;

import java.util.HashMap;
import java.util.Map;

import use_case.login.LoginUserDataAccessInterface;
import entity.User;

public class FakeInMemoryGitHubAPIDataAccessObject implements LoginUserDataAccessInterface {
    private final Map<String, User> userMap = new HashMap<String, User>();
    private User currentUser;

    /**
     * method for the fake dao only: map a token to a user
     * @param token
     * @param user
     */
    public void addUser(String token, User user) {
        userMap.put(token, user);
    }


    @Override
    public boolean tokenValid(String token) {
        return userMap.containsKey(token);
    }

    @Override
    public void save(User user) {
        
    }

    @Override
    public User getUser(String token) {
        return userMap.get(token);
    }

    @Override
    public void setCurrentUser(User user) {
        currentUser = user;
    }

    @Override
    public User getCurrentUser(User user) {
        return currentUser;
    }
}
