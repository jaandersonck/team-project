package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

/**
 * The Presenter for the Login Use Case.
 */
public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          LoggedInViewModel loggedInViewModel,
                          LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.loginViewModel = loginViewModel;
    }

    /**
     * Prepares the success view for the Login Use Case by clearing the login view's state.
     * @param response the output data
     */
    @Override
    public void prepareSuccessView(LoginOutputData response) {
        // Login was successful, we can move onto the 'logged in view'

        // leaving this view behind, clear its state
        this.loginViewModel.setState(new LoginState());
        this.loginViewModel.notifyStateChanged();

        System.out.println("Login success");

        // todo: configure logged in view view and update its viewmodel/state
        // eg. (ca-login)
//        final LoggedInState loggedInState = loggedInViewModel.getState();
//        loggedInState.setUsername(response.getUsername());
//        this.loggedInViewModel.notifyStateChanged();
        // switch to the logged in view
//        this.viewManagerModel.setState(LoggedInViewModel.VIEW_NAME);
//        this.viewManagerModel.notifyViewChanged();
    }

    /**
     * Prepares the failure view for the Login Use Case by showing the error on the login view.
     * @param error the explanation of the failure
     */
    @Override
    public void prepareFailView(String error) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setLoginError(error);
        loginViewModel.notifyStateChanged();
    }
}