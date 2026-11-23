package use_case.search_repository;

/**
 * Output Boundary for the Search Repository UC
 */
public interface SearchRepositoryOutputBoundary {

    void prepareSuccessView(SearchRepositoryOutputData outputData);

    void prepareFailView(String errorMessage);

}
