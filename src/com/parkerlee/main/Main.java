/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parkerlee.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main class of the project
 * 
 * @author parkerlee
 */
public class Main extends Application {
    
    /**
     * Launches arguments
     * 
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Shows the login view. 
     * 
     * @param primaryStage  the stage to be initialized
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}