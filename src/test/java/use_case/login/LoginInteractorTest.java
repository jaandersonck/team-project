package use_case.login;
import data_access.InMemoryGithubDataAccessObject;
import data_access.InMemoryTokenDataAccessObject;
import entity.UserFactory;
import entity.User;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LoginInteractorTest {

    @Test
    void successTest() {
        // Setup the conditions of a fake github api
        UserFactory factory = new UserFactory();
        User user = factory.create("paul100", "Paul", "paul@hotmail.com");

        LoginUserDataAccessInterface fakeAPI = new InMemoryGithubDataAccessObject(
                Map.of("paul_token_ssss", user));

        // Begin
        LoginTokenDataAccessInterface tokenStorage = new InMemoryTokenDataAccessObject();
        LoginInputData inputData = new LoginInputData("paul_token_ssss", false);

        // This creates a successPresenter that tests whether the test case is as we expect.
        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                assertEquals("paul100", user.getUsername());
                assertEquals("Paul", user.getName());
                assertEquals("paul@hotmail.com", user.getEmail());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(fakeAPI, tokenStorage, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureUserDoesNotExistTest() {
        // Setup the conditions of a fake github api
        UserFactory factory = new UserFactory();
        User user = factory.create("paul100", "Paul", "paul@hotmail.com");

        LoginUserDataAccessInterface fakeAPI = new InMemoryGithubDataAccessObject(
                Map.of("paul_token_ssss", user));

        // Begin
        LoginTokenDataAccessInterface tokenStorage = new InMemoryTokenDataAccessObject();
        LoginInputData inputData = new LoginInputData("not a valid token", false);

        // This creates a presenter that tests whether the test case is as we expect.
        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                // this should never be reached since the test case should fail
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("No GitHub account found associated with token.", error);
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(fakeAPI, tokenStorage, failurePresenter);
        interactor.execute(inputData);
    }


    @Test
    void stayLoggedInSavesTokenTest() {
        // Setup the conditions of a fake github api
        UserFactory factory = new UserFactory();
        User user = factory.create("paul100", "Paul", "paul@hotmail.com");

        LoginUserDataAccessInterface fakeAPI = new InMemoryGithubDataAccessObject(
                Map.of("paul_token_ssss", user));

        // Begin
        // Kept as the concrete type so the test can read back the saved token.
        InMemoryTokenDataAccessObject tokenStorage = new InMemoryTokenDataAccessObject();
        LoginInputData inputData = new LoginInputData("paul_token_ssss", true);

        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                assertEquals("paul100", user.getUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        // execute login with save session checked, we should be saving to the token store
        LoginInputBoundary interactor = new LoginInteractor(fakeAPI, tokenStorage, successPresenter);
        interactor.execute(inputData);

        assertEquals("paul_token_ssss", tokenStorage.getToken());
    }

    @Test
    void testNoSaveToken() {
        // Setup the conditions of a fake github api
        UserFactory factory = new UserFactory();
        User user = factory.create("paul100", "Paul", "paul@hotmail.com");

        LoginUserDataAccessInterface fakeAPI = new InMemoryGithubDataAccessObject(
                Map.of("paul_token_ssss", user));

        // Begin
        // Kept as the concrete type so the test can read back the saved token.
        InMemoryTokenDataAccessObject tokenStorage = new InMemoryTokenDataAccessObject();
        LoginInputData inputData = new LoginInputData("paul_token_ssss", false);

        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                assertEquals("paul100", user.getUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(fakeAPI, tokenStorage, successPresenter);
        interactor.execute(inputData);

        assertEquals("", tokenStorage.getToken());
    }
}