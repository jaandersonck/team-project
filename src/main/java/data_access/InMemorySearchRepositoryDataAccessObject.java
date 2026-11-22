package data_access;

import java.util.List;

import use_case.DataAccessException;
import use_case.search_repository.SearchRepositoryDataAccessInterface;
import use_case.search_repository.SearchRepositoryResult;

/**
 * An in-memory DAO for testing Search Repository UC
 */
public class InMemorySearchRepositoryDataAccessObject implements SearchRepositoryDataAccessInterface {

    private final List<SearchRepositoryResult> repositories;
    private boolean shouldFail;

    //Creates a fake DAO with predefined repositories
    public InMemorySearchRepositoryDataAccessObject(List<SearchRepositoryResult> repositories) {
        this.repositories = List.copyOf(repositories);
        this.shouldFail = false;
    }

    @Override
    public List<SearchRepositoryResult> getAccessibleRepositories()
            throws DataAccessException {

        if (shouldFail) {
            throw new DataAccessException("Simulated GitHub API failure.");
        }

        return repositories;
    }

    //Simulates a data access failure for testing.
    public void setShouldFail(boolean shouldFail) {
        this.shouldFail = shouldFail;
    }
}