/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parkerlee.main;

import com.parkerlee.model.User;
import com.parkerlee.model.UserDAO;
import com.parkerlee.util.Translator;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author parkerlee
 */
public class LoginController implements Initializable {
    
    @FXML
    private Label titleText;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Label credentialsText;

    @FXML
    private Label usernameText;

    @FXML
    private Label passwordText;
    
    @FXML
    private Label errorText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String lang = Translator.getUserLanguage();
        
        if (lang.equals("fr")) {
            titleText.setText("Utilisateur en Ligne");
            usernameTextField.setPromptText("Nom d'utilisateur");
            passwordTextField.setPromptText("Mot de passe");
            loginButton.setText("S'identifier");
            credentialsText.setText("Veuillez vous connecter avec les informations d'identification suivantes:");
            usernameText.setText("Nom d'utilisateur: root");
            passwordText.setText("Mot de passe: toor");
        }
    }   
    
    @FXML
    void loginButtonPressed(ActionEvent event) throws ClassNotFoundException, SQLException {
        
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        
        ObservableList<User> userList = UserDAO.getAllRecords();
        
        userList.forEach((user) -> {
            String dbUsername = user.getUsernameProperty().getValue();
            String dbPassword = user.getPasswordProperty().getValue();
            
            if (username.equals(dbUsername) && password.equals(dbPassword)) {
                errorText.setText("");
                // navigate to customer window
            } else {
                String lang = Translator.getUserLanguage();
                if (lang.equals("fr")) {
                    errorText.setText("Une erreur s'est produite avec le nom d'utilisateur et / ou le mot de passe fournis.");
                } else {
                    errorText.setText("There was an error with the username and/or password provided.");
                }
            }
        });
    }
    
}
