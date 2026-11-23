package interface_adapter.search_repository;

import use_case.search_repository.SearchRepositoryInputBoundary;
import use_case.search_repository.SearchRepositoryInputData;

/**
 * Controller for Search Repository UC
 */
public class SearchRepositoryController {

    private final SearchRepositoryInputBoundary interactor;

    public SearchRepositoryController(SearchRepositoryInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes Search Repository UC
     */
    public void execute(String searchTerm) {
        final SearchRepositoryInputData inputData = new SearchRepositoryInputData(searchTerm);

        interactor.execute(inputData);
    }
}