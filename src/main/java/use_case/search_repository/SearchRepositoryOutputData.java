package use_case.search_repository;

import java.util.List;

/**
 * Output data for SearchRepository UC
 */
public class SearchRepositoryOutputData {

    private final List<SearchRepositoryResult> repositories;

    public SearchRepositoryOutputData(
            List<SearchRepositoryResult> repositories) {
        this.repositories = List.copyOf(repositories);
    }

    public List<SearchRepositoryResult> getRepositories() {
        return repositories;
    }
}