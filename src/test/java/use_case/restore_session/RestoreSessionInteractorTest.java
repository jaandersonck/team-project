package use_case.restore_session;

import data_access.FakeInMemoryGithubDataAccessObject;
import data_access.FakeInMemoryTokenDataAccessObject;
import entity.UserFactory;
import entity.User;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RestoreSessionInteractorTest {

    @Test
    void successTest() {
        // Setup the conditions of a fake github api
        UserFactory factory = new UserFactory();
        User user = factory.create("paul100", "Paul", "paul@hotmail.com");

        RestoreSessionUserDataAccessInterface fakeAPI = new FakeInMemoryGithubDataAccessObject(
                Map.of("paul_token_ssss", user));

        // Begin
        RestoreSessionTokenDataAccessInterface tokenStorage =
                new FakeInMemoryTokenDataAccessObject("paul_token_ssss");
        RestoreSessionInputData inputData = new RestoreSessionInputData();

        // This creates a successPresenter that tests whether the test case is as we expect.
        RestoreSessionOutputBoundary successPresenter = new RestoreSessionOutputBoundary() {
            @Override
            public void prepareSuccessView(RestoreSessionOutputData user) {
                assertEquals("paul100", user.getUsername());
                assertEquals("Paul", user.getName());
                assertEquals("paul@hotmail.com", user.getEmail());
            }

            @Override
            public void prepareNoTokenView() {
                fail("Use case no token is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        RestoreSessionInputBoundary interactor = new RestoreSessionInteractor(fakeAPI, tokenStorage,
                successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureInvalidTokenTest() {
        // Setup the conditions of a fake github api
        UserFactory factory = new UserFactory();
        User user = factory.create("paul100", "Paul", "paul@hotmail.com");

        RestoreSessionUserDataAccessInterface fakeAPI = new FakeInMemoryGithubDataAccessObject(
                Map.of("paul_token_ssss", user));

        // Begin
        RestoreSessionTokenDataAccessInterface tokenStorage =
                new FakeInMemoryTokenDataAccessObject("not a valid token");
        RestoreSessionInputData inputData = new RestoreSessionInputData();

        // This creates a presenter that tests whether the test case is as we expect.
        RestoreSessionOutputBoundary failurePresenter = new RestoreSessionOutputBoundary() {
            @Override
            public void prepareSuccessView(RestoreSessionOutputData user) {
                // this should never be reached since the test case should fail
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareNoTokenView() {
                fail("Use case no token is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                // reaching the fail view is the expected outcome
            }
        };

        RestoreSessionInputBoundary interactor = new RestoreSessionInteractor(fakeAPI, tokenStorage,
                failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void noTokenTest() {
        // Setup the conditions of a fake github api
        UserFactory factory = new UserFactory();
        User user = factory.create("paul100", "Paul", "paul@hotmail.com");

        RestoreSessionUserDataAccessInterface fakeAPI = new FakeInMemoryGithubDataAccessObject(
                Map.of("paul_token_ssss", user));

        // Begin
        // No saved token: the fake token storage defaults to the empty string.
        RestoreSessionTokenDataAccessInterface tokenStorage = new FakeInMemoryTokenDataAccessObject();
        RestoreSessionInputData inputData = new RestoreSessionInputData();

        // This creates a presenter that tests whether the test case is as we expect.
        RestoreSessionOutputBoundary noTokenPresenter = new RestoreSessionOutputBoundary() {
            @Override
            public void prepareSuccessView(RestoreSessionOutputData user) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareNoTokenView() {
                // reaching the no token view is the expected outcome
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        RestoreSessionInputBoundary interactor = new RestoreSessionInteractor(fakeAPI, tokenStorage,
                noTokenPresenter);
        interactor.execute(inputData);
    }
}
