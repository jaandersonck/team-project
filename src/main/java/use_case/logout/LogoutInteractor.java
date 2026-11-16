package use_case.logout;

/**
 * The Logout Interactor.
 */
public class LogoutInteractor implements LogoutInputBoundary {
    private final LogoutUserDataAccessInterface userDataAccessObject;
    private final LogoutTokenDataAccessInterface tokenDataAccessObject;
    private final LogoutOutputBoundary logoutPresenter;

    public LogoutInteractor(LogoutUserDataAccessInterface userDataAccessInterface,
                            LogoutTokenDataAccessInterface tokenDataAccessInterface,
                            LogoutOutputBoundary logoutOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.tokenDataAccessObject = tokenDataAccessInterface;
        this.logoutPresenter = logoutOutputBoundary;
    }

    @Override
    public void execute() {
        userDataAccessObject.setCurrentUser(null);
        tokenDataAccessObject.save("");

        // Empty LogoutOutputData
        logoutPresenter.prepareSuccessView(new LogoutOutputData());
    }
}

