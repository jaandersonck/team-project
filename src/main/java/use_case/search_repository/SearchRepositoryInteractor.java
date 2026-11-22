package use_case.search_repository;

import java.util.ArrayList;
import java.util.List;

import use_case.DataAccessException;

/**
 * The Search Repository Interactor
 */
public class SearchRepositoryInteractor implements SearchRepositoryInputBoundary {

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

        // Normalize search term
        final String searchTerm = inputData.getSearchTerm().trim().toLowerCase();

        try {
            // Fetch all accessible repositories
            final List<SearchRepositoryResult> repositories = dataAccessObject.getAccessibleRepositories();

            // Filter repositories by name
            final List<SearchRepositoryResult> matches = new ArrayList<>();

            for (SearchRepositoryResult repo : repositories) {
                final String name = repo.getName().toLowerCase();

                if (name.contains(searchTerm)) {
                    matches.add(repo);
                }
            }

            // Send matching repositories to presenter
            final SearchRepositoryOutputData outputData = new SearchRepositoryOutputData(matches);

            presenter.prepareSuccessView(outputData);
        }
        catch (DataAccessException exception) {
            presenter.prepareFailView("Could not search repositories: " + exception.getMessage());
        }
    }
}