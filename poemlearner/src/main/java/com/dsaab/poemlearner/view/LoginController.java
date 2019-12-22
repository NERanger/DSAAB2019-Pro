package com.dsaab.poemlearner.view;

import com.dsaab.poemlearner.MainApp;
import com.dsaab.poemlearner.model.User;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label notMatchLabel;

    private MainApp mainApp;
    private boolean isValidated = false;

    @FXML
    private void initialize() {
        notMatchLabel.setText("");
    }

    @FXML
    private void handleLogin() {
        for(User user : mainApp.getUserData()) {
            if(user.getUserName().equals(userNameField.getText())) {
                if(user.getPassword().equals(passwordField.getText())) {
                    notMatchLabel.setText("");
                    isValidated = true;
                    mainApp.setCurrentUser(user);
                    mainApp.showModeSelectionView();
                }
            }
        }

        if(isValidated == false) {
            notMatchLabel.setText("Username or password not correct");
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}