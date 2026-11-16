package use_case.restore_session;

/**
 * Input Boundary for actions which are related to restoring the session.
 */
public interface RestoreSessionInputBoundary {

    /**
     * Executes the restore session use case.
     * @param restoreSessionInputData the input data
     */
    void execute(RestoreSessionInputData restoreSessionInputData);
}
