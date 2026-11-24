package data_access;

import java.io.IOException;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A small wrapper around OkHttp for GET requests to the GitHub API. It contains the session's
 * token, so other callers do not need to continually pass it
 */
public class GitHubHTTPClient {
    private static final String GITHUB_API_HOST = "api.github.com";

    private final OkHttpClient client = new OkHttpClient();
    private String token;

    /**
     * Set the session token
     * @param token the GitHub API token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Sends a GET request authenticated with the session token. (for logged in requests)
     * @param endpoint the endpoint, e.g. {@code "/user"}; see {@link #get(String, Map, String)}
     * @param query the query parameters
     * @return the response; the caller must close it
     * @throws IOException if the request could not be completed
     */
    public Response get(String endpoint, Map<String, String> query) throws IOException {
        return get(endpoint, query, token);
    }

    /**
     * Sends a GET request authenticated with the given token. eg. checking if a token is valid, there
     * will be no session token
     * @param endpoint the endpoint as GitHub's docs write it, with a leading slash, e.g.
     *                 {@code "/repos/owner/name/commits"}; it is split on {@code /}, and every
     *                 other character is encoded
     * @param query the query parameters, key to value
     * @param token the GitHub API token
     * @return the okhttp Response
     * @throws IOException if the request could not be completed
     * @throws IllegalArgumentException if {@code endpoint} doesn't start with {@code /}
     */
    public Response get(String endpoint, Map<String, String> query, String token) throws IOException {
        if (!endpoint.startsWith("/")) {
            throw new IllegalArgumentException("endpoint must start with '/': " + endpoint);
        }
        // addPathSegments would turn the leading slash into an empty segment, giving "//user"
        final HttpUrl.Builder url = new HttpUrl.Builder()
                .scheme("https")
                .host(GITHUB_API_HOST)
                .addPathSegments(endpoint.substring(1));
        for (Map.Entry<String, String> parameter : query.entrySet()) {
            url.addQueryParameter(parameter.getKey(), parameter.getValue());
        }

        final Request.Builder request = new Request.Builder()
                .url(url.build())
                .header("Accept", "application/vnd.github+json");
        if (token != null && !token.isEmpty()) {
            request.header("Authorization", "Bearer " + token);
        }
        return client.newCall(request.build()).execute();
    }
}
