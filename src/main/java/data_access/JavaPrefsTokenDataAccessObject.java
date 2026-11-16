package data_access;
import java.util.prefs.Preferences;
import use_case.restore_session.RestoreSessionTokenDataAccessInterface;

// Use java's builtin prefs storage for storing the user token.
public class JavaPrefsTokenDataAccessObject implements RestoreSessionTokenDataAccessInterface {
    private final Preferences prefs;

    public JavaPrefsTokenDataAccessObject(String nodeName) {
        prefs = Preferences.userRoot().node(nodeName);
    }

    @Override
    public String getToken() {
        return prefs.get("token", "");
    }
}
