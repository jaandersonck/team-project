package interface_adapter.select_repository;

import java.util.ArrayList;
import java.util.List;


public class SelectRepositoryState {

    private List<String> repositories = new ArrayList<>();

    public SelectRepositoryState(SelectRepositoryState copy){
        repositories = copy.repositories;
    }

    public SelectRepositoryState(){

    }

    public List<String> getRepositories(){ return repositories; }

    public void setRepositories(List<String> repositories){ this.repositories = repositories; }



}
