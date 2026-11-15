package use_case.select_repository;

import entity.Repository;

public interface SelectRepositoryDataAccessInterface {

    Repository getRepository(String url); // url is just the part after github.com

    void setCurrentRepository(Repository repository);

}
