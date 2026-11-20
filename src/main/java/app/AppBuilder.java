package app;

import data_access.InMemoryGithubDataAccessObject;
import data_access.InMemoryTokenDataAccessObject;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import view.LoginView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final UserFactory userFactory = new UserFactory();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // TODO: THESE ARE TEMPORARY, EFFECTIVELY USELESS DATA ACCESS OBJECTS, NEED TO BE REPLACED WITH REAL
    final InMemoryGithubDataAccessObject userDataAccessObject = new InMemoryGithubDataAccessObject(
            Map.of("PAULTOKEN", userFactory.create("paul1024", "paul", "paul@example.com"))
    );
    final InMemoryTokenDataAccessObject tokenDataAccessObject = new InMemoryTokenDataAccessObject();

    private LoginViewModel loginViewModel;
    private LoggedInViewModel loggedInViewModel; // todo: this is a placeholder and currently never assigned
    private LoginView loginView;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Creates the login view and its view model and adds it to the card panel.
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, LoginViewModel.VIEW_NAME);
        return this;
    }

    /**
     * Wires up the login presenter, interactor and controller, and connects the controller to the login view.
     * Must be called after {@link #addLoginView()}.
     * @return this builder
     */
    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, tokenDataAccessObject, loginOutputBoundary);

        LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    /**
     * Builds the application window, starting on the login view.
     * @return the application frame
     */
    public JFrame build() {
        final JFrame application = new JFrame("User Login Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        // Set the starting view
        viewManagerModel.setState(LoginViewModel.VIEW_NAME);
        viewManagerModel.notifyViewChanged();

        return application;
    }
}
