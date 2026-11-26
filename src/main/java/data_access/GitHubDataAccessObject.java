package data_access;

import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import entity.User;
import entity.UserFactory;
import okhttp3.Response;
import use_case.DataAccessException;
import use_case.login.LoginUserDataAccessInterface;

/**
 * Data access object for interacting with the real GitHub API
 */
public class GitHubDataAccessObject implements LoginUserDataAccessInterface {
    private final GitHubHTTPClient httpClient;
    private final UserFactory userFactory;

    private User currentUser;

    /**
     * Creates a data access object that talks to GitHub through the given client.
     * @param httpClient the client used for every request; it holds the session's token
     * @param userFactory the factory used to create users from GitHub's responses
     */
    public GitHubDataAccessObject(GitHubHTTPClient httpClient, UserFactory userFactory) {
        this.httpClient = httpClient;
        this.userFactory = userFactory;
    }

    /**
     * Check the token maps to a real GitHub user
     * @param token the GitHub API token
     * @return true if the token belongs to a GitHub account; false otherwise
     * @throws DataAccessException if GitHub could not be reached, or returned another error
     */
    @Override
    public boolean tokenValid(String token) throws DataAccessException {
        if (token == null || token.isEmpty()) {
            return false;
        }

        try (Response response = httpClient.get("/user", Map.of(), token)) {
            if (response.code() == HTTP_UNAUTHORIZED) {
                return false;
            }
            requireSuccess(response);
            return true;
        }
        catch (IOException e) {
            throw new DataAccessException("Could not reach the GitHub API.", e);
        }
    }

    /**
     * Returns the GitHub user the token belongs to.
     * @param token the GitHub API token
     * @return the user the token belongs to
     * @throws DataAccessException if there was an error along the way
     */
    @Override
    public User getUser(String token) throws DataAccessException {
        try (Response response = httpClient.get("/user", Map.of(), token)) {
            requireSuccess(response);

            final JSONObject user = new JSONObject(response.body().string());
            // name and email are null when the user hasn't made them public
            return userFactory.create(user.getString("login"), user.optString("name", null),
                    user.optString("email", null));
        }
        catch (IOException e) {
            throw new DataAccessException("Could not reach the GitHub API.", e);
        }
        catch (JSONException e) {
            throw new DataAccessException("GitHub API returned an unexpected user.", e);
        }
    }

    /**
     * Set the session token to authenticate future requests.
     * @param token the token to authenticate with
     */
    @Override
    public void setCurrentToken(String token) {
        httpClient.setToken(token);
    }

    /**
     * Set the logged in user.
     * @param user the user to mark as logged in
     */
    @Override
    public void setCurrentUser(User user) {
        currentUser = user;
    }

    /**
     * Return the user that is currently logged in.
     * @return the current user, or null if no login has succeeded
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Simple wrapper to check for failure based on the http status code: anything that's
     * not 2xx is an error.
     * @param response the response to check
     * @throws DataAccessException if the response's status isn't 2xx
     */
    private void requireSuccess(Response response) throws DataAccessException {
        if (!response.isSuccessful()) {
            throw new DataAccessException("GitHub API returned " + response.code() + errorMessage(response));
        }
    }

    /**
     * Reads the human-readable message GitHub puts in an error response, e.g. "Bad credentials".
     * @param response the failed response
     * @return the message prefixed with ": ", or an empty string if there isn't one
     */
    private String errorMessage(Response response) {
        try {
            final String message = new JSONObject(response.body().string()).optString("message");
            return message.isEmpty() ? "" : ": " + message;
        }
        catch (IOException | JSONException e) {
            return "";
        }
    }
}
