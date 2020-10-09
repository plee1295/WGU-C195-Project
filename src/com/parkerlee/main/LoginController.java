/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parkerlee.main;

import com.parkerlee.model.User;
import com.parkerlee.model.UserDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private TextField passwordTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Label credentialsText;

    @FXML
    private Label usernameText;

    @FXML
    private Label passwordText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    @FXML
    void loginButtonPressed(ActionEvent event) throws ClassNotFoundException, SQLException {
        
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        
        ObservableList<User> userList = UserDAO.getAllRecords();
        
        userList.forEach((user) -> {
            System.out.println(user.getUsernameProperty().getValue());
            System.out.println(user.getPasswordProperty().getValue());
        });
    }
    
}
