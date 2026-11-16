package use_case.restore_session;

/**
 * The output boundary for the Restore Session use case
 */
public interface RestoreSessionOutputBoundary {
    /**
     * Prepares the success view for the Restore Session use case.
     * @param outputData the output data
     */
    void prepareSuccessView(RestoreSessionOutputData outputData);

    /**
     * Prepares the failure view for the Restore Session use case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
