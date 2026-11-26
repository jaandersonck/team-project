package data_access;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import use_case.DataAccessException;
import use_case.login.LoginTokenDataAccessInterface;

/**
 * Persists the saved token with {@link java.util.prefs.Preferences}.
 * The token is stored in plain text; tokens should have as narrow
 * a read only scope as possible.
 */
public class JavaPrefsTokenDataAccessObject implements LoginTokenDataAccessInterface {
    private static final String TOKEN_KEY = "githubToken";

    private final Preferences preferences;

    /**
     * Creates a token store in this app's preferences node.
     */
    public JavaPrefsTokenDataAccessObject() {
        this(Preferences.userNodeForPackage(JavaPrefsTokenDataAccessObject.class));
    }

    /**
     * Creates a token store with the given name
     * @param preferences the node to store the token in
     */
    public JavaPrefsTokenDataAccessObject(Preferences preferences) {
        this.preferences = preferences;
    }

    /**
     * Saves the token, replacing any previously saved token, and writes it to disk.
     * @param token the token to save
     * @throws DataAccessException if the token could not be written to the preferences store
     */
    @Override
    public void save(String token) throws DataAccessException {
        preferences.put(TOKEN_KEY, token);
        flush();
    }

    /**
     * Returns the saved token.
     * @return the saved token, or an empty string if none has been saved
     */
    public String getToken() {
        return preferences.get(TOKEN_KEY, "");
    }

    /**
     * Removes the saved token, e.g. on logout.
     * @throws DataAccessException if the change could not be written to the preferences store
     */
    public void clear() throws DataAccessException {
        preferences.remove(TOKEN_KEY);
        flush();
    }

    /**
     * Writes pending changes to disk now
     * @throws DataAccessException if the preferences store could not be written
     */
    private void flush() throws DataAccessException {
        try {
            preferences.flush();
        }
        catch (BackingStoreException e) {
            throw new DataAccessException("Could not save the token.", e);
        }
    }
}
