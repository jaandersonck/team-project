package use_case.login;

import entity.User;

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

    @Override
    public void execute(LoginInputData loginInputData) {
        final String token = loginInputData.getToken();
        if (!userDataAccessObject.tokenValid(token)) {
            loginPresenter.prepareFailView("No GitHub account found associated with token.");
        }
        else {
            final User user = userDataAccessObject.getUser(token);
            userDataAccessObject.setCurrentUser(user);

            final LoginOutputData  loginOutputData = new LoginOutputData(user.getUsername(),
                    user.getName(), user.getEmail());

            if(loginInputData.isStayLoggedIn()) {
                tokenDataAccessObject.save(token);
            }

            loginPresenter.prepareSuccessView(loginOutputData);
        }
    }
}