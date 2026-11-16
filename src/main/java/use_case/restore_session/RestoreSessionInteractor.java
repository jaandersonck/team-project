package use_case.restore_session;

import entity.User;
import use_case.login.LoginInputData;
import use_case.login.LoginOutputData;

public class RestoreSessionInteractor implements RestoreSessionInputBoundary {
    private final RestoreSessionDataAccessInterface restoreSessionDataAccessObject;
    private final RestoreSessionOutputBoundary restoreSessionPresenter;

    public RestoreSessionInteractor(RestoreSessionDataAccessInterface restoreSessionDataAccessObject,
                           RestoreSessionOutputBoundary restoreSessionPresenter) {
        this.restoreSessionDataAccessObject = restoreSessionDataAccessObject;
        this.restoreSessionPresenter = restoreSessionPresenter;
    }

    @Override
    public void execute(RestoreSessionInputData restoreSessionInputData) {
        final String token = restoreSessionDataAccessObject.getToken();

        if(token != null) {
            // token exists, check if valid
            if (!restoreSessionDataAccessObject.tokenValid(token)) {
                restoreSessionPresenter.prepareFailView("");
            } else {
                // token exists and valid success
                final User user = restoreSessionDataAccessObject.getUser(token);
                restoreSessionDataAccessObject.setCurrentUser(user);

                final RestoreSessionOutputData restoreSessionOutputData = new RestoreSessionOutputData(
                        user.getUsername(),
                        user.getName(),
                        user.getEmail()
                );

                restoreSessionPresenter.prepareSuccessView(restoreSessionOutputData);
            }
        } else {
            // no token exists
            restoreSessionPresenter.prepareFailView("");
        }
    }
}
