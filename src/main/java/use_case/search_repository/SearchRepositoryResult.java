package use_case.search_repository;

/**
 * Represents a repository found in a search
 */
public class SearchRepositoryResult {

    private final String owner;
    private final String name;

    public SearchRepositoryResult(String owner, String name) {
        this.owner = owner;
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }
}