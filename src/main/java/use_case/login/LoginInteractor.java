package use_case.login;

import entity.User;
import use_case.DataAccessException;

/**
 * The Login Interactor.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final LoginUserDataAccessInterface userDataAccessObject;
    private final LoginTokenDataAccessInterface tokenDataAccessObject;
    private final LoginOutputBoundary loginPresenter;

    public LoginInteractor(LoginUserDataAccessInterface userDataAccessInterface,
                           LoginTokenDataAccessInterface tokenDataAccessInterface,
                           LoginOutputBoundary loginOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.tokenDataAccessObject = tokenDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
    }

    /**
     * Executes the login use case.
     * @param loginInputData the input data
     */
    @Override
    public void execute(LoginInputData loginInputData) {
        final String token = loginInputData.getToken();
        try {
            if (!userDataAccessObject.tokenValid(token)) {
                loginPresenter.prepareFailView("No GitHub account found associated with token.");
                return;
            }
            final User user = userDataAccessObject.getUser(token);
            userDataAccessObject.setCurrentUser(user);

            if (loginInputData.isStayLoggedIn()) {
                tokenDataAccessObject.save(token);
            }

            loginPresenter.prepareSuccessView(new LoginOutputData(user.getUsername(),
                    user.getName(), user.getEmail()));
        }
        catch (DataAccessException e) {
            loginPresenter.prepareFailView("Could not log in: " + e.getMessage());
        }
    }
}