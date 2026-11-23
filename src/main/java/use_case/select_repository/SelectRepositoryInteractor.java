package use_case.select_repository;

import entity.Repository;

public class SelectRepositoryInteractor implements SelectRepositoryInputBoundary {

    private final SelectRepositoryDataAccessInterface repositoryDataAccessObject;
    private final SelectRepositoryOutputBoundary selectRepositoryPresenter;


    public SelectRepositoryInteractor(SelectRepositoryDataAccessInterface repositoryDataAccessObject,
                                      SelectRepositoryOutputBoundary selectRepositoryPresenter ){

        this.repositoryDataAccessObject = repositoryDataAccessObject;
        this.selectRepositoryPresenter = selectRepositoryPresenter;

    };

    /**
     * executes the select repo use case
     * @param selectRepositoryInputData input data passed from the controller
     */
    public void execute(SelectRepositoryInputData selectRepositoryInputData){
        final String repoURL = selectRepositoryInputData.getURL();

        final Repository repository = repositoryDataAccessObject.getRepository(repoURL);
        repositoryDataAccessObject.setCurrentRepository(repository);

        String url = repository.getOwner().getName() + "/" + repository.getName();

        selectRepositoryPresenter.prepareSuccessView(new SelectRepositoryOutputData(repository.getName(),
                repository.getOwner().getName()));

    };


}
