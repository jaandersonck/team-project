package use_case.search_repository;

import java.util.List;
import use_case.DataAccessException;

/**
 * Data Access Interface for the Search Repository use case
 */
public interface SearchRepositoryDataAccessInterface {

    /**
     * Retrieves repositories accessible to the currently authenticated GitHub user.
     *
     * Return a list of accessible repositories
     * Throws DataAccessException if retrieval fails
     */
    List<SearchRepositoryResult> getAccessibleRepositories()
            throws DataAccessException;
}