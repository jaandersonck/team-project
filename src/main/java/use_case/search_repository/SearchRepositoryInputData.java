package use_case.search_repository;

/**
 * Input data for SearchRepository UC
 */
public class SearchRepositoryInputData {

    private final String searchTerm;

    public SearchRepositoryInputData(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getSearchTerm() {
        return searchTerm;
    }
}