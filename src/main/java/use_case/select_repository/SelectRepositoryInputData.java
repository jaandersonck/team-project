package use_case.select_repository;

/**
 * input data for select repository
 */

public class SelectRepositoryInputData {

    private final String repositoryURL;

    public SelectRepositoryInputData(String repositoryURL){

        this.repositoryURL = repositoryURL;
    }

    String getURL(){ return repositoryURL; }
}
