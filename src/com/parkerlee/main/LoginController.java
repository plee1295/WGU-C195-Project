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
import java.text.SimpleDateFormat;
import java.util.Date;
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
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

/**
 * FXML Controller class for user authentication
 *
 * @author parkerlee
 */
public class LoginController implements Initializable {
    
    /**
     * The label to display the title
     */
    @FXML
    private Label titleText;

    /**
     * The text field to collect the username
     */
    @FXML
    private TextField usernameTextField;

    /**
     * The text field to collect the password
     */
    @FXML
    private PasswordField passwordTextField;

    /**
     * The button to login the user
     */
    @FXML
    private Button loginButton;

    /**
     * The label to inform the user of how to login
     */
    @FXML
    private Label credentialsText;

    /**
     * The label to inform the user of the correct username to login
     */
    @FXML
    private Label usernameText;

    /**
     * The label to inform the user of the correct password to login
     */
    @FXML
    private Label passwordText;
    
    /**
     * The label to display if a user validation error occurs
     */
    @FXML
    private Label errorText;
    
    /* The label to display while the controller is working on authentication
     * 
     */
    @FXML
    private Label loadingText;
    
    /**
     * The label to display the country and time zone of the system
     */
    @FXML
    private Label userLocationText;

    /**
     * Initializes the controller class. Gets user location data. Performs translation in english 
     * or french based on the system language. 
     * 
     * @param url
     * @param rb
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
    
    /**
     * Handles the login button pressed. Validates the username and password data is valid
     * and updates the labels accordingly. If valid, navigates the user to the customer view. 
     * Logs the attempt to login_attempts.txt.
     * 
     * @param event
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
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
        
        Logger logger = Logger.getLogger("LoginAttempts");  
        
        ObservableList<User> userList = UserDAO.getAllRecords();
        
        /**
         * This lambda expression is used to validate the username and password
         * by checking the inputs with current database data. The use of the lambda 
         * expression here is to avoid the use of a traditional for-loop and
         * easily loop over all of the user objects inside of the user list.
         */
        userList.forEach((user) -> {
            String dbUsername = user.getUsernameProperty().getValue();
            String dbPassword = user.getPasswordProperty().getValue();
            
            if (username.equals(dbUsername) && password.equals(dbPassword)) {
                errorText.setText("");
                
                try {
                    // This block configure the logger with handler and formatter  
                    FileHandler fh = new FileHandler("/Users/parkerlee/NetBeansProjects/ScheduleApp/login_attempts.txt");
                    logger.addHandler(fh);
                    SimpleFormatter formatter = new SimpleFormatter();
                    fh.setFormatter(formatter);

                    // the following statement is used to log any messages 
                    String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                    logger.info("Username: " + dbUsername + ", DateTime: " + timestamp + ", SUCCESS");
                    
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
                // This block configure the logger with handler and formatter  
                FileHandler fh;
                try {
                    fh = new FileHandler("/Users/parkerlee/NetBeansProjects/ScheduleApp/login_attempts.txt");
                    logger.addHandler(fh);
                    SimpleFormatter formatter = new SimpleFormatter();
                    fh.setFormatter(formatter);

                    // the following statement is used to log any messages 
                    String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                    logger.info("Username: " + dbUsername + ", DateTime: " + timestamp + ", FAILURE");
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SecurityException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }

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
    
    /**
     * Shows an alert to the user upon successful login. This alert informs the user
     * whether any appointments are scheduled within the next 15 minutes. 
     * 
     * @throws ClassNotFoundException
     * @throws SQLException
     */
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
