package data_access;

import use_case.login.LoginTokenDataAccessInterface;

public class FakeInMemoryTokenDataAccessObject implements LoginTokenDataAccessInterface {
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
}
