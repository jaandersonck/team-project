package interface_adapter.restore_session;

import use_case.restore_session.RestoreSessionInputBoundary;
import use_case.restore_session.RestoreSessionInputData;

/**
 * The controller for the Restore Session use case.
 */
public class RestoreSessionController {

    private final RestoreSessionInputBoundary restoreSessionUseCaseInteractor;

    public RestoreSessionController(RestoreSessionInputBoundary restoreSessionUseCaseInteractor) {
        this.restoreSessionUseCaseInteractor = restoreSessionUseCaseInteractor;
    }

    /**
     * Executes the Restore Session Use Case.
     */
    public void execute() {
        restoreSessionUseCaseInteractor.execute(new RestoreSessionInputData());
    }
}
