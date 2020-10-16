/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parkerlee.main;

import com.parkerlee.model.Appointment;
import com.parkerlee.model.AppointmentDAO;
import com.parkerlee.model.Customer;
import com.parkerlee.model.User;
import com.parkerlee.model.UserDAO;
import com.parkerlee.util.Location;
import com.parkerlee.util.Translator;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    
    @FXML
    private Label loadingText;
    
    @FXML
    private Label userLocationText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        String userLocation = Location.getUserLocationInfo();
        userLocationText.setText(userLocation);
        
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
    void loginButtonPressed(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
        
        String lang = Translator.getUserLanguage();
        if (lang.equals("fr")) {
            loadingText.setText("Se charge...");
        } else {
            loadingText.setText("Loading...");
        }
        loginButton.setDisable(true);
        
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        
        
        
        ObservableList<User> userList = UserDAO.getAllRecords();
        
        userList.forEach((user) -> {
            String dbUsername = user.getUsernameProperty().getValue();
            String dbPassword = user.getPasswordProperty().getValue();
            
            if (username.equals(dbUsername) && password.equals(dbPassword)) {
                errorText.setText("");
                
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerView.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
                    int userId;
                    try {
                        userId = UserDAO.getCurrentUserId(usernameTextField.getText());
                        CustomerController controller = loader.getController();
                        controller.getData(userId);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
            
                    stage.setScene(scene);
                    stage.show();
                    
                    showAppointmentAlert();
                    
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            } else {
                if (lang.equals("fr")) {
                    errorText.setText("Une erreur s'est produite avec le nom d'utilisateur et / ou le mot de passe fournis.");
                } else {
                    errorText.setText("There was an error with the username and/or password provided.");
                }
            }
        });
        
        loadingText.setText("");
        loginButton.setDisable(false);
    }
    
    private void showAppointmentAlert() throws ClassNotFoundException, SQLException {
        
        String apptStr = "";
        ObservableList<Appointment> apptList = AppointmentDAO.getAllRecordsInNext15Minutes();
        
        if (!apptList.isEmpty()) {
            for (int i = 0; i < apptList.size(); i++) {
                int apptId = apptList.get(i).getIdProperty().getValue();
                String start = apptList.get(i).getStartTimeProperty().getValue();
                
                apptStr += "Appointment ID: " + apptId + ", Start: " + start + "\n";
            }
        
            Alert alert = new Alert(Alert.AlertType.INFORMATION, apptStr, ButtonType.OK);        
            alert.showAndWait().filter(response -> response == ButtonType.OK);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No upcoming appointments", ButtonType.OK);        
            alert.showAndWait().filter(response -> response == ButtonType.OK);
        }
        
        
        
    }
    
}
