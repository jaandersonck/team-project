package interface_adapter.select_repository;

import use_case.select_repository.SelectRepositoryInputBoundary;
import use_case.select_repository.SelectRepositoryInputData;


public class SelectRepositoryController {

    private final SelectRepositoryInputBoundary selectRepositoryInteractor;

    public SelectRepositoryController(SelectRepositoryInputBoundary selectRepositoryInteractor){

        this.selectRepositoryInteractor = selectRepositoryInteractor;

    }

    /**
     * Executes the Select Repository Use Case
     * @param repositoryURL is the URL of the repository after github.com
     */

    public void execute(String repositoryURL){
        final SelectRepositoryInputData selectRepositoryInputData = new SelectRepositoryInputData(repositoryURL);
        selectRepositoryInteractor.execute(selectRepositoryInputData);
    }

}
