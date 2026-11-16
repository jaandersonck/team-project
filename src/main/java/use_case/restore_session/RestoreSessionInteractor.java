package use_case.restore_session;

import entity.User;

public class RestoreSessionInteractor implements RestoreSessionInputBoundary {
    private final RestoreSessionUserDataAccessInterface restoreSessionUserDataAccessObject;
    private final RestoreSessionTokenDataAccessInterface restoreSessionTokenDataAccessObject;
    private final RestoreSessionOutputBoundary restoreSessionPresenter;

    public RestoreSessionInteractor(RestoreSessionUserDataAccessInterface restoreSessionUserDataAccessObject,
                                    RestoreSessionTokenDataAccessInterface restoreSessionTokenDataAccessObject,
                                    RestoreSessionOutputBoundary restoreSessionPresenter) {
        this.restoreSessionUserDataAccessObject = restoreSessionUserDataAccessObject;
        this.restoreSessionTokenDataAccessObject = restoreSessionTokenDataAccessObject;
        this.restoreSessionPresenter = restoreSessionPresenter;
    }

    @Override
    public void execute(RestoreSessionInputData restoreSessionInputData) {
        final String token = restoreSessionTokenDataAccessObject.getToken();

        if(!token.isEmpty()) {
            // token exists, check if valid
            if (!restoreSessionUserDataAccessObject.tokenValid(token)) {
                restoreSessionPresenter.prepareFailView("");
            } else {
                // token exists and valid success
                final User user = restoreSessionUserDataAccessObject.getUser(token);
                restoreSessionUserDataAccessObject.setCurrentUser(user);

                final RestoreSessionOutputData restoreSessionOutputData = new RestoreSessionOutputData(
                        user.getUsername(),
                        user.getName(),
                        user.getEmail()
                );

                restoreSessionPresenter.prepareSuccessView(restoreSessionOutputData);
            }
        } else {
            // no token exists
            restoreSessionPresenter.prepareNoTokenView();
        }
    }
}
