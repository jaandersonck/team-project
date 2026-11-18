package interface_adapter.login;

import java.util.ArrayList;
import java.util.List;

/**
 * The View Model for the Login View.
 */
public class LoginViewModel {

    private final List<LoginViewModelListener> listeners = new ArrayList<>();

    public static final String VIEW_NAME = "log in";

    private LoginState state = new LoginState();

    /**
     * Returns the current login state.
     * @return the state
     */
    public LoginState getState() {
        return this.state;
    }

    /**
     * Replaces the current login state. Does not notify listeners.
     * @param state the new state
     */
    public void setState(LoginState state) {
        this.state = state;
    }

    /**
     * Registers a listener to be notified when the state changes.
     * @param listener the listener to add
     */
    public void addListener(LoginViewModelListener listener) {
        listeners.add(listener);
    }

    /**
     * Notifies all registered listeners that the state has changed.
     */
    public void notifyStateChanged() {
        for (LoginViewModelListener listener : listeners) {
            listener.stateChanged();
        }
    }

}