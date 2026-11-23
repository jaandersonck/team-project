package use_case.search_repository;

import java.util.List;
import use_case.DataAccessException;

/**
 * Data access interface for the Search Repository use case.
 */
public interface SearchRepositoryDataAccessInterface {

    /**
     * Returns repositories accessible to the authenticated user.
     */
    List<SearchRepositoryResult> getAccessibleRepositories()
            throws DataAccessException;

    /**
     * Searches GitHub for public repositories matching a term.
     *
     * @param term the search query
     * @return matching repositories
     */
    List<SearchRepositoryResult> getRepositoriesByTerm(String term)
            throws DataAccessException;
}