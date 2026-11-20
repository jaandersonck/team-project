package data_access;

import com.sun.net.httpserver.HttpServer;
import entity.CommitTree;
import entity.CommitTreeFactory;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Runs the DAO against a local fake of GitHub's commit-list endpoint. */
class GithubApiDataAccessObjectTest {
    private HttpServer server;
    private final List<String> requestedQueries = new ArrayList<>();
    private int status = 200;
    private final List<String> pages = new ArrayList<>();

    private static String item(String sha, String... parents) {
        StringBuilder p = new StringBuilder();
        for (String parent : parents) {
            if (p.length() > 0) p.append(',');
            p.append("{\"sha\":\"").append(parent).append("\",\"url\":\"x\"}");
        }
        return "{\"sha\":\"" + sha + "\",\"commit\":{\"author\":{\"name\":\"Ada\","
                + "\"email\":\"a@x.com\",\"date\":\"2026-11-01T12:00:00Z\"},"
                + "\"message\":\"msg " + sha + "\"},\"author\":null,\"parents\":[" + p + "]}";
    }

    @BeforeEach
    void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/repos/octo/demo/commits", exchange -> {
            String query = exchange.getRequestURI().getRawQuery();
            requestedQueries.add(query);
            int page = Integer.parseInt(query.replaceAll(".*page=(\\d+).*", "$1"));
            String body;
            if (status != 200) {
                body = "{\"message\":\"Git Repository is empty.\"}";
            }
            else {
                body = page <= pages.size() ? pages.get(page - 1) : "[]";
            }
            byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(status, bytes.length);
            try (OutputStream out = exchange.getResponseBody()) {
                out.write(bytes);
            }
        });
        server.start();
    }

    @AfterEach
    void stop() {
        server.stop(0);
    }

    private GithubApiDataAccessObject dao() {
        GithubApiDataAccessObject dao = new GithubApiDataAccessObject(new OkHttpClient(),
                "http://127.0.0.1:" + server.getAddress().getPort(), new CommitTreeFactory());
        dao.selectRepository("octo", "demo");
        return dao;
    }

    @Test
    void parsesMergeIntoTree() {
        pages.add("[" + item("A", "B", "X1") + "," + item("X1", "B") + "," + item("B") + "]");

        CommitTree tree = dao().getCommitTree("main", 10);

        assertEquals("A", tree.getCommit().id);
        assertEquals("main", tree.getBranchName());
        assertEquals("B", tree.getNextCommit().getCommit().id);
        assertEquals("X1", tree.getBranches().get(0).getCommit().id);
        assertEquals("Ada", tree.getCommit().author);
        assertEquals("msg A", tree.getCommit().message);
        assertTrue(requestedQueries.get(0).contains("sha=main"));
    }

    @Test
    void stopsAtMaxCommitsAndMarksCutOff() {
        pages.add("[" + item("C", "B") + "," + item("B", "A") + "]");

        CommitTree tree = dao().getCommitTree("main", 2);

        assertEquals(1, requestedQueries.size());
        assertTrue(requestedQueries.get(0).contains("per_page=2"));
        assertTrue(tree.getNextCommit().isCutOff());
    }

    @Test
    void followsPages() {
        StringBuilder first = new StringBuilder("[");
        for (int i = 150; i > 50; i--) {
            if (i < 150) first.append(',');
            first.append(item("c" + i, "c" + (i - 1)));
        }
        pages.add(first.append(']').toString());
        pages.add("[" + item("c50") + "]");

        CommitTree tree = dao().getCommitTree("main", 500);

        assertEquals(2, requestedQueries.size());
        int count = 0;
        for (CommitTree t = tree; t != null; t = t.getNextCommit()) count++;
        assertEquals(101, count);
    }

    @Test
    void emptyRepositoryGivesNull() {
        status = 409;
        assertNull(dao().getCommitTree("main", 10));
    }

    @Test
    void requiresSelectedRepository() {
        GithubApiDataAccessObject dao = new GithubApiDataAccessObject(new OkHttpClient(),
                "http://127.0.0.1:1", new CommitTreeFactory());
        assertThrows(IllegalStateException.class, () -> dao.getCommitTree("main", 10));
    }
}
