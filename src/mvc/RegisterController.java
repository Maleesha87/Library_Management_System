package mvc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import librarySystem.Login;

public class RegisterController {
    private RegisterModel model;
    private RegisterView view;

    public RegisterController(RegisterModel model, RegisterView view) {
        this.model = model;
        this.view = view;

        this.view.addSignUpListener(new SignUpListener());
        this.view.addSignInListener(new SignInListener());
    }

    private class SignUpListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleSignUp();
        }
    }

    private void handleSignUp() {
        model.setname(view.getName());
        model.setUsername(view.getUsername());
        model.setEmail(view.getEmail());
        model.setPassword(view.getPassword());

        if (model.validate()) {
            if (view.insertData()) {
                Login loginScreen = new Login();
                loginScreen.setVisible(true);
                view.dispose();
            }
        } else {
            view.displayMessage("Please enter a valid Gmail address and a password with at least 6 characters, one uppercase letter, and one digit.");
        }
    }

    private class SignInListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Login loginScreen = new Login();
            loginScreen.setVisible(true);
            view.dispose();
        }
    }
}
