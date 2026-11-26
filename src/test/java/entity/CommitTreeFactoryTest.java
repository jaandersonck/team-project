package entity;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommitTreeFactoryTest {

    private final CommitTreeFactory factory = new CommitTreeFactory();

    /** Shortcut for creating commits **/
    private static Commit commit(String sha, String... parentShas) {
        return new Commit(sha, List.of(parentShas), "author", "message " + sha, new Date());
    }

    /** The SHAs along one line, oldest first, following nextCommit. */
    private static List<String> line(CommitTree start) {
        List<String> shas = new ArrayList<String>();
        for (CommitTree node = start; node != null; node = node.getNextCommit()) {
            shas.add(node.getCommit().sha);
        }
        return shas;
    }

    /** The node for {@code sha} on the line starting at {@code start}. */
    private static CommitTree find(CommitTree start, String sha) {
        for (CommitTree node = start; node != null; node = node.getNextCommit()) {
            if (node.getCommit().sha.equals(sha)) {
                return node;
            }
        }
        fail(sha + " is not on this line");
        return null;
    }

    /** Counts every commit in the tree, skipping the fake root. */
    private static int countCommits(CommitTree start) {
        int count = 0;
        for (CommitTree node = start; node != null; node = node.getNextCommit()) {
            if (!node.isFakeRoot()) {
                count++;
            }
            for (CommitTree branch : node.getBranches()) {
                count += countCommits(branch);
            }
        }
        return count;
    }

    /*
     * main:          A - B - C - M      (M merges F2)
     * feature-login:      \- F1 - F2
     * wip:                    \- W1     (forks from C)
     */
    private static List<Commit> sampleHistory() {
        return List.of(
                commit("A"),
                commit("B", "A"),

                // branches off of B
                commit("C", "B"),
                commit("F1", "B"),

                commit("F2", "F1"),

                // branches off of C
                commit("W1", "C"),
                commit("M", "C", "F2")
        );
    }

    /** The same history as listed for main alone: everything reachable from M, so no W1. */
    private static List<Commit> mainHistory() {
        return List.of(
                commit("A"),
                commit("B", "A"),
                commit("F1", "B"),
                commit("C", "B"),
                commit("F2", "F1"),
                commit("M", "C", "F2")
        );
    }

    @Test
    void noCommitsGivesFakeRootWithNoBranches() {
        CommitTree root = factory.generateCommitTree("main", List.of());

        assertTrue(root.isFakeRoot());
        assertNull(root.getCommit());
        assertTrue(root.getBranches().isEmpty());
    }

    @Test
    void linearHistoryIsOneLineOffTheFakeRoot() {
        CommitTree root = factory.generateCommitTree("main", List.of(
                commit("A"),
                commit("B", "A"),
                commit("C", "B")
        ));

        assertTrue(root.isFakeRoot());
        assertNull(root.getNextCommit());
        assertEquals(1, root.getBranches().size());

        CommitTree mainLine = root.getBranches().get(0);
        assertEquals(List.of("A", "B", "C"), line(mainLine));
        assertEquals("main", mainLine.getBranchName());
        assertTrue(mainLine.getBranches().isEmpty());
    }

    @Test
    void branchesSplitOffWhereTheyForked() {
        // this mimics a scenario when we pull in multiple branches worth of commits, so we can label their heads
        LinkedHashMap<String, String> heads = new LinkedHashMap<String, String>();
        heads.put("main", "M"); // default branch, so placed first
        heads.put("feature-login", "F2");
        heads.put("wip", "W1");

        CommitTree root = factory.generateCommitTreeFromHeads(heads, sampleHistory());

        // confirm that the tree structure reflects the structure drawn with sampleHistory above.

        assertEquals(1, root.getBranches().size());
        CommitTree mainLine = root.getBranches().get(0);
        assertEquals(List.of("A", "B", "C", "M"), line(mainLine));

        CommitTree feature = find(mainLine, "B").getBranches().get(0);
        assertEquals(List.of("F1", "F2"), line(feature));
        assertEquals("feature-login", feature.getBranchName());

        CommitTree wip = find(mainLine, "C").getBranches().get(0);
        assertEquals(List.of("W1"), line(wip));
        assertEquals("wip", wip.getBranchName());
    }

    @Test
    void cutOffLineIsItsOwnLineAfterTheMainLine() {
        // commits B and F1 reference commits that we didn't 'fetch'
        List<Commit> commits = List.of(
                commit("B", "Y"),
                commit("F1", "Z"),
                commit("C", "B"),
                commit("F2", "F1"),
                commit("M", "C", "F2")
        );
        // represents {Y} -> B -> C -> M    (main branch)
        //                           ^
        //          {Z} -> F1  -> F2/       (other unfinished branch, mentioned by a merge commit)
        // note: both Y and Z don't exist in our context, never 'fetched'
        // the earliest existing roots should be treated as roots instead

        CommitTree root = factory.generateCommitTree("main", commits);

        assertEquals(2, root.getBranches().size());
        assertEquals(List.of("B", "C", "M"), line(root.getBranches().get(0)));
        CommitTree cutOff = root.getBranches().get(1);
        assertEquals(List.of("F1", "F2"), line(cutOff));
        assertEquals(List.of("Z"), cutOff.getCommit().parentShas);
    }

    @Test
    void inputOrderDoesNotMatter() {
        List<Commit> reversed = new ArrayList<Commit>(mainHistory());
        Collections.reverse(reversed);

        CommitTree mainLine = factory.generateCommitTree("main", reversed).getBranches().get(0);

        assertEquals(List.of("A", "B", "C", "M"), line(mainLine));
        assertEquals("main", mainLine.getBranchName());
    }
}
