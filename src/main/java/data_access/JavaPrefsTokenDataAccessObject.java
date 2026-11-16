package data_access;
import java.util.prefs.Preferences;

import use_case.login.LoginTokenDataAccessInterface;
import use_case.logout.LogoutTokenDataAccessInterface;
import use_case.restore_session.RestoreSessionTokenDataAccessInterface;

// Use java's builtin prefs storage for storing the user token.
public class JavaPrefsTokenDataAccessObject implements RestoreSessionTokenDataAccessInterface,
        LoginTokenDataAccessInterface, LogoutTokenDataAccessInterface {
    private final Preferences prefs;

    public JavaPrefsTokenDataAccessObject(String nodeName) {
        prefs = Preferences.userRoot().node(nodeName);
    }

    @Override
    public String getToken() {
        return prefs.get("token", "");
    }

    @Override
    public void save(String token) {
        prefs.put("token", token);
    }
}
