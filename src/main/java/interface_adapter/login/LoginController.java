package interface_adapter.login;

import use_case.login.LoginInputBoundary;
import use_case.login.LoginInputData;

/**
 * The controller for the Login Use Case.
 */
public class LoginController {

    private final LoginInputBoundary loginUseCaseInteractor;

    public LoginController(LoginInputBoundary loginUseCaseInteractor) {
        this.loginUseCaseInteractor = loginUseCaseInteractor;
    }

    /**
     * Executes the Login Use Case.
     * @param token the github authentication token for accessing the api
     * @param stayLoggedIn whether the token should be saved for future sessions
     */
    public void execute(String token, boolean stayLoggedIn) {
        final LoginInputData loginInputData = new LoginInputData(token, stayLoggedIn);

        loginUseCaseInteractor.execute(loginInputData);
    }
}