package data_access;
import java.util.prefs.Preferences;

import use_case.login.LoginTokenDataAccessInterface;
import use_case.logout.LogoutTokenDataAccessInterface;

// Use java's builtin prefs storage for storing the user token.
public class JavaPrefsTokenDataAccessObject implements LoginTokenDataAccessInterface, LogoutTokenDataAccessInterface {
    private final Preferences prefs;

    public JavaPrefsTokenDataAccessObject(String nodeName) {
        prefs = Preferences.userRoot().node(nodeName);
    }

    @Override
    public void save(String token) {
        prefs.put("token", token);
    }
}
