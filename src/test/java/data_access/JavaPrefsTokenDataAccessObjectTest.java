package data_access;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import use_case.DataAccessException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JavaPrefsTokenDataAccessObjectTest {
    private Preferences testNode;

    @BeforeEach
    void setUp() {
        testNode = Preferences.userRoot().node("JavaPrefsTokenDataAccessObjectTest");
    }

    @AfterEach
    void tearDown() throws BackingStoreException {
        testNode.removeNode();
    }

    @Test
    void noSavedTokenIsEmpty() {
        assertEquals("", new JavaPrefsTokenDataAccessObject(testNode).getToken());
    }

    @Test
    void savedTokenIsReadBackByANewInstance() throws DataAccessException {
        new JavaPrefsTokenDataAccessObject(testNode).save("paul_token");

        assertEquals("paul_token", new JavaPrefsTokenDataAccessObject(testNode).getToken());
    }

    @Test
    void savingReplacesThePreviousToken() throws DataAccessException {
        JavaPrefsTokenDataAccessObject tokens = new JavaPrefsTokenDataAccessObject(testNode);
        tokens.save("old");
        tokens.save("new");

        assertEquals("new", tokens.getToken());
    }

    @Test
    void clearRemovesTheToken() throws DataAccessException {
        JavaPrefsTokenDataAccessObject tokens = new JavaPrefsTokenDataAccessObject(testNode);
        tokens.save("paul_token");
        tokens.clear();

        assertEquals("", tokens.getToken());
    }
}
