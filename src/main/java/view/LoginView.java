package view;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

import interface_adapter.login.LoginViewModelListener;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The View for when the user is logging into the program.
 */
public class LoginView extends JPanel implements ActionListener, LoginViewModelListener {

    private final LoginViewModel loginViewModel;

    private final JTextField tokenInputField = new JTextField(15);
    private final JCheckBox stayLoggedInCheckBox = new JCheckBox("Stay logged in");
    private final JLabel loginErrorField = new JLabel();

    private final JButton logIn;
    private LoginController loginController = null;

    public LoginView(LoginViewModel loginViewModel) {

        this.loginViewModel = loginViewModel;
        this.loginViewModel.addListener(this);

        final JLabel title = new JLabel("GitHub Repo Analyzer");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel("Token"), tokenInputField);

        final JPanel buttons = new JPanel();
        logIn = new JButton("Log In");
        buttons.add(logIn);
        buttons.add(stayLoggedInCheckBox);

        logIn.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(logIn)) {
                            final LoginState currentState = loginViewModel.getState();

                            loginController.execute(
                                    currentState.getToken(),
                                    currentState.isStayLoggedIn()
                            );
                        }
                    }
                }
        );

        tokenInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                // trim whitespace and line breaks that may show up when copy paste
                currentState.setToken(tokenInputField.getText().trim());
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        stayLoggedInCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final LoginState currentState = loginViewModel.getState();
                currentState.setStayLoggedIn(stayLoggedInCheckBox.isSelected());
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(usernameInfo);
        this.add(loginErrorField);
        this.add(buttons);
    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    /**
     * The login state was updated; refresh the token field and error message, if there is one.
     */
    @Override
    public void stateChanged() {
        setFields(loginViewModel.getState());
        loginErrorField.setText(loginViewModel.getState().getLoginError());
    }

    private void setFields(LoginState state) {
        tokenInputField.setText(state.getToken());
        stayLoggedInCheckBox.setSelected(state.isStayLoggedIn());
    }

    /**
     * Sets the controller used to run the login use case when Log In is clicked.
     * @param loginController the login controller
     */
    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
}