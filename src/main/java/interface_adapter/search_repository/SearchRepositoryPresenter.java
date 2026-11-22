package interface_adapter.search_repository;

import java.util.ArrayList;
import java.util.List;

import use_case.search_repository.SearchRepositoryOutputBoundary;
import use_case.search_repository.SearchRepositoryOutputData;
import use_case.search_repository.SearchRepositoryResult;

/**
 * The Presenter for Search Repository UC
 */
public class SearchRepositoryPresenter implements SearchRepositoryOutputBoundary {

    private final SearchRepositoryViewModelUpdater viewModel;

    public SearchRepositoryPresenter(SearchRepositoryViewModelUpdater viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(SearchRepositoryOutputData outputData) {

        final List<String> repositoryNames = new ArrayList<>();

        for (SearchRepositoryResult repo : outputData.getRepositories()) {
            repositoryNames.add(repo.getOwner() + "/" + repo.getName());
        }

        viewModel.showSearchResults(repositoryNames);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.showSearchError(errorMessage);
    }
}