package use_case.login;

import data_access.FakeInMemoryGithubDataAccessObject;
import entity.UserFactory;
import entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginInteractorTest {

    @Test
    void successTest() {
        // Setup the conditions of a fake github api
        UserFactory factory = new UserFactory();
        User user = factory.create("paul100", "Paul", "paul@hotmail.com");

        FakeInMemoryGithubDataAccessObject userRepository = new FakeInMemoryGithubDataAccessObject();
        userRepository.addUser("paul_token_ssss", user);

        // Begin
        LoginUserDataAccessInterface fakeAPI = userRepository;
        LoginInputData inputData = new LoginInputData("paul_token_ssss");

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

        LoginInputBoundary interactor = new LoginInteractor(fakeAPI, successPresenter);
        interactor.execute(inputData);
    }


//    @Test
//    void failurePasswordMismatchTest() {
//        LoginInputData inputData = new LoginInputData("Paul", "wrong");
//        LoginUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();
//
//        // For this failure test, we need to add Paul to the data access repository before we log in, and
//        // the passwords should not match.
//        UserFactory factory = new UserFactory();
//        User user = factory.create("Paul", "password");
//        userRepository.save(user);
//
//        // This creates a presenter that tests whether the test case is as we expect.
//        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
//            @Override
//            public void prepareSuccessView(LoginOutputData user) {
//                // this should never be reached since the test case should fail
//                fail("Use case success is unexpected.");
//            }
//
//            @Override
//            public void prepareFailView(String error) {
//                assertEquals("Incorrect password for \"Paul\".", error);
//            }
//        };
//
//        LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
//        interactor.execute(inputData);
//    }
//
//    @Test
//    void failureUserDoesNotExistTest() {
//        LoginInputData inputData = new LoginInputData("Paul", "password");
//        LoginUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();
//
//        // Add Paul to the repo so that when we check later they already exist
//
//        // This creates a presenter that tests whether the test case is as we expect.
//        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
//            @Override
//            public void prepareSuccessView(LoginOutputData user) {
//                // this should never be reached since the test case should fail
//                fail("Use case success is unexpected.");
//            }
//
//            @Override
//            public void prepareFailView(String error) {
//                assertEquals("Paul: Account does not exist.", error);
//            }
//        };
//
//        LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
//        interactor.execute(inputData);
//    }
}