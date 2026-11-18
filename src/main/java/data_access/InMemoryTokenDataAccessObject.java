package data_access;

import use_case.login.LoginTokenDataAccessInterface;

public class InMemoryTokenDataAccessObject implements LoginTokenDataAccessInterface {
    private String token;

    public InMemoryTokenDataAccessObject(String token) {
        this.token = token;
    }

    public InMemoryTokenDataAccessObject() {
        token = "";
    }

    @Override
    public void save(String token) {
        this.token = token;
    }
}