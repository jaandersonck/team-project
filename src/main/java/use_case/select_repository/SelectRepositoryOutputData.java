package use_case.select_repository;


public class SelectRepositoryOutputData {

    private final String repoName;
    private final String owner;

    public SelectRepositoryOutputData (String repoName, String owner){

        this.repoName = repoName;
        this.owner = owner;

    }

    public String getRepoName(){ return repoName; }

    public String getOwner(){ return owner; }

}
