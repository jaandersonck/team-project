package data_access;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;

public class GitHubHTTPClient {
    private final String GITHUB_API_URL = "api.github.com";
    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    private final OkHttpClient client;
    public GitHubHTTPClient() {
        client = new OkHttpClient();
    }

    /**
     * Authenticated GET on some url
     * @param url following {@code GITHUB_API_URL} in format /{...}/{...}/...
     * @param query queries after the url i.e. ?sha={...}&something={...}...
     * @return the {@code Response} or throw {@code IOException}
     */
    public Response get(String url, Map<String, String> query) throws IOException {
        HttpUrl.Builder urlBuilder = new HttpUrl.Builder()
                .scheme("https")
                .addPathSegment(GITHUB_API_URL)
                .addPathSegment(url);

        Request.Builder reqBuilder = new Request.Builder()
                .addHeader("Authorization", "Bearer " + this.token);


        for (Map.Entry<String, String> qParam : query.entrySet()) {
            urlBuilder.addQueryParameter(qParam.getKey(), qParam.getValue());
        }

        reqBuilder.url(urlBuilder.build());

        Request req = reqBuilder.build();

        return client.newCall(req).execute();
    }

}
