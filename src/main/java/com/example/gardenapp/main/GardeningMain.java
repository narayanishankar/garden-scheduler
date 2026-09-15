package com.example.gardenapp;

import com.example.gardenapp.journal.JournalController;
import com.example.gardenapp.journal.JournalDatabase;
import com.example.gardenapp.map.GardenMap;
import com.example.gardenapp.schedule.ScheduleDatabase;
import com.example.gardenapp.schedule.ScheduleLogic;
import com.example.gardenapp.signin.LoginDatabase;
import com.example.gardenapp.signin.LoginForm;
import com.example.gardenapp.signin.SignupForm;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GardeningMain extends Application {

    private BorderPane root;

    @Override
    public void start(Stage stage)  {
        // 1. Database Connections
        LoginDatabase.connect();
        LoginDatabase.createUsersTable();
        JournalDatabase.createTable();
        ScheduleDatabase.createTable();

        // 2. Initialize the main layout
        root = new BorderPane();
        createMenu();      // Builds the top bar
        createHomePage();  // Builds the center content

        // 3. Scene Setup
        root.setStyle("-fx-background-color: #f4f7f6;");
        Scene scene = new Scene(root, 1024, 768);
        stage.setTitle("Gardening Club Portal");
        stage.setScene(scene);
        stage.show();
    }

    private void createMenu(){
        HBox topMenu = new HBox(15);
        topMenu.setPadding(new Insets(10));
        topMenu.setAlignment(Pos.CENTER_LEFT);
        topMenu.setStyle("-fx-background-color: #d1e9ff; -fx-border-color: #b0c4de; -fx-border-width: 0 0 1 0;");

        Button homeBtn = new Button("Home");
        Button journalBtn = new Button("Journal");
        Button scheduleBtn = new Button("Schedule");
        Button mapBtn = new Button("Garden Map");

        String btnStyle = "-fx-background-color: transparent; -fx-font-weight: bold; -fx-cursor: hand;";
        homeBtn.setStyle(btnStyle);
        journalBtn.setStyle(btnStyle);
        scheduleBtn.setStyle(btnStyle);
        mapBtn.setStyle(btnStyle);

        // Add the basic navigation buttons first
        topMenu.getChildren().addAll(homeBtn, journalBtn, scheduleBtn);

        // Navigation Actions
        homeBtn.setOnAction(e -> createHomePage());
        journalBtn.setOnAction(e -> {
            if (LoginForm.loggedInUser != null) {
                root.setCenter(JournalController.getView());
            } else {
                showLoginReminder();
            }
        });

        scheduleBtn.setOnAction(e -> {
            if (LoginForm.loggedInUser != null) {
                root.setCenter(ScheduleLogic.getView());
            } else {
                showLoginReminder();
            }
        });

        mapBtn.setOnAction(e -> {
            if (LoginForm.loggedInUser != null) {
                GardenMap gardenMap = new GardenMap();
                root.setCenter(gardenMap.getView());
            } else {
                showLoginReminder();
            }
        });

        topMenu.getChildren().add(3, mapBtn);

        // Add the Username and Logout to the RIGHT if logged in
        if (LoginForm.loggedInUser != null) {
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS); // Pushes content to the right

            Label userDisplay = new Label("👤 " + LoginForm.loggedInUser.getUsername());
            userDisplay.setStyle("-fx-font-weight: bold; -fx-text-fill: #2e7d32; -fx-padding: 0 15 0 0;");

            Button logoutBtn = new Button("Logout");
            logoutBtn.setStyle("-fx-background-color: #ffcccc; -fx-font-size: 11px;");
            logoutBtn.setOnAction(e -> {
                LoginForm.loggedInUser = null;
                createMenu();     // Refresh menu
                createHomePage(); // Refresh home
            });

            topMenu.getChildren().addAll(spacer, userDisplay, logoutBtn);
        }

        root.setTop(topMenu);
    }

    private void createHomePage(){
        VBox homeBox = new VBox(20);
        homeBox.setPadding(new Insets(50));
        homeBox.setAlignment(Pos.CENTER);

        if (LoginForm.loggedInUser == null) {
            Label introText = new Label("Welcome to the Gardening Club");
            introText.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

            Button loginButton = new Button("Login");
            Button createAccountButton = new Button("Create Account");

            loginButton.setOnAction(e -> {
                LoginForm.show();
                // After the popup closes, refresh the UI
                createMenu();
                createHomePage();
            });

            createAccountButton.setOnAction(e -> SignupForm.show());
            homeBox.getChildren().addAll(introText, loginButton, createAccountButton);
        }  else {
            // Fix: Use LoginForm.loggedInUser instead of currentUser
            Label welcomeLabel = new Label("Welcome, " + LoginForm.loggedInUser.getUsername() + "!");
            welcomeLabel.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #2e7d32;");

            Label instructions = new Label("Select 'Journal' or 'Schedule' from the menu above to get started.");
            homeBox.getChildren().addAll(welcomeLabel, instructions);
        }

        root.setCenter(homeBox);
    }

    private void showLoginReminder() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Please login first to access this feature.");
        alert.show();
    }

    public static void main(String[] args) {
        launch();
    }
}