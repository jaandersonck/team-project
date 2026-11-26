package use_case.login;

import entity.User;
import use_case.DataAccessException;

/**
 * Logs the user in with a GitHub token: checks the token, makes it and its user current, and
 * saves the token if the user asked to stay logged in.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final LoginUserDataAccessInterface userDataAccessObject;
    private final LoginTokenDataAccessInterface tokenDataAccessObject;
    private final LoginOutputBoundary loginPresenter;

    /**
     * Creates the login interactor.
     * @param userDataAccessObject looks up users by token, and holds the current token and user
     * @param tokenDataAccessObject saves the token for staying logged in
     * @param loginPresenter the presenter told whether the login succeeded
     */
    public LoginInteractor(LoginUserDataAccessInterface userDataAccessObject,
                           LoginTokenDataAccessInterface tokenDataAccessObject,
                           LoginOutputBoundary loginPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.tokenDataAccessObject = tokenDataAccessObject;
        this.loginPresenter = loginPresenter;
    }

    /**
     * Logs in with the given token, showing the fail view if it's invalid or GitHub can't be reached.
     * @param loginInputData the token, and whether to stay logged in
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

            userDataAccessObject.setCurrentToken(token);
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
