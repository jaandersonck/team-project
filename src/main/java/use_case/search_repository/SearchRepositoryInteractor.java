package use_case.search_repository;

import java.util.List;

import use_case.DataAccessException;

/**
 * The Search Repository Interactor
 */
public class SearchRepositoryInteractor
        implements SearchRepositoryInputBoundary {

    private final SearchRepositoryDataAccessInterface dataAccessObject;
    private final SearchRepositoryOutputBoundary presenter;

    public SearchRepositoryInteractor(
            SearchRepositoryDataAccessInterface dataAccessObject,
            SearchRepositoryOutputBoundary presenter) {

        this.dataAccessObject = dataAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void execute(SearchRepositoryInputData inputData) {
        if (inputData == null || inputData.getSearchTerm() == null) {
            presenter.prepareFailView("Search term cannot be null!");
            return;
        }

        final String searchTerm = inputData.getSearchTerm().trim();

        try {
            final List<SearchRepositoryResult> repositories;

            if (searchTerm.isEmpty()) {
                // Fetch repositories accessible to the user.
                repositories = dataAccessObject.getAccessibleRepositories();
            }
            else {
                // Search GitHub for repositories matching the term.
                repositories = dataAccessObject.getRepositoriesByTerm(searchTerm);
            }

            // Send matching repositories to presenter.
            final SearchRepositoryOutputData outputData =
                    new SearchRepositoryOutputData(repositories);

            presenter.prepareSuccessView(outputData);
        }
        catch (DataAccessException exception) {
            presenter.prepareFailView(
                    "Could not search repositories: " + exception.getMessage());
        }
    }
}