package data_access;

import use_case.login.LoginTokenDataAccessInterface;
import use_case.restore_session.RestoreSessionTokenDataAccessInterface;

public class FakeInMemoryTokenDataAccessObject implements RestoreSessionTokenDataAccessInterface,
        LoginTokenDataAccessInterface {
    private String token;

    public FakeInMemoryTokenDataAccessObject(String token) {
        this.token = token;
    }

    public FakeInMemoryTokenDataAccessObject() {
        token = "";
    }

    @Override
    public void save(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }
}
