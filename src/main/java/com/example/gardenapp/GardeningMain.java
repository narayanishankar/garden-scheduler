package com.example.gardenapp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GardeningMain extends Application {

    private User currentUser = null;

    private VBox homeBox;

    @Override
    public void start(Stage stage)  {
        LoginDatabase.connect();
        LoginDatabase.createUsersTable();

        //Menu bar, using buttons
        Button homeBtn = new Button("Home");
        Button journalBtn = new Button("Journal");
        Button scheduleBtn = new Button("Schedule");

        HBox topMenu = new HBox(10); // 10px spacing
        topMenu.setPadding(new Insets(10));
        topMenu.getChildren().addAll(homeBtn, journalBtn, scheduleBtn);

        BorderPane root = new BorderPane();
        root.setTop(topMenu);

        //Home page
        homePage(root);

        //Journal page
        Label journalLabel = new Label("Journal page will go here.");
        VBox journalBox = new VBox();
        journalBox.setPadding(new Insets(50));
        journalBox.getChildren().add(journalLabel);

        //Schedule page
        Label scheduleLabel = new Label("Schedule page will go here.");
        VBox scheduleBox = new VBox();
        scheduleBox.setPadding(new Insets(50));
        scheduleBox.getChildren().add(scheduleLabel);

        //Menu, switching pages
        homeBtn.setOnAction(e -> homePage(root));

        journalBtn.setOnAction(e -> root.setCenter(journalBox));
        scheduleBtn.setOnAction(e -> root.setCenter(scheduleBox));

        //Setting up the scene and stage components
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("GardeningApplication!");
        stage.setScene(scene);
        stage.show();
    }

    private void homePage(BorderPane root){
        homeBox = new VBox(20);
        homeBox.setPadding(new Insets(50));

        if (LoginForm.loggedInUser == null) {
            Label introText = new Label("Welcome to the Gardening Club website. Gardening is great.");
            introText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

            Button loginButton = new Button("Login");
            Button createAccountButton = new Button("Create Account");

            loginButton.setOnAction(e -> {
                LoginForm.show();
                homePage(root);
            });

            createAccountButton.setOnAction(e -> SignupForm.show());
            homeBox.getChildren().addAll(introText, loginButton, createAccountButton);
        } else {
            Label welcomeLabel = new Label("Welcome, " + currentUser.getUsername() + "!");
            welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
            homeBox.getChildren().add(welcomeLabel);
        }
        root.setCenter(homeBox);
    }

    public static void main(String[] args) {
        launch();
    }
}