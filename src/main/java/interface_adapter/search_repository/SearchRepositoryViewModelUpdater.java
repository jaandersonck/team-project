package interface_adapter.search_repository;

import java.util.List;

/**
 * Defines updates required by the Search Repository Presenter
 */
public interface SearchRepositoryViewModelUpdater {

    void showSearchResults(List<String> repositories);

    void showSearchError(String errorMessage);
}