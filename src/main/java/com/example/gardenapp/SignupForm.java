package com.example.gardenapp;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SignupForm {

    public static void show() {
        Stage signupStage = new Stage();
        signupStage.setTitle("Create New Account");

        Label errorLabel = new Label();

        // Labels and fields
        Label userLabel = new Label("Username:");
        TextField userField = new TextField();

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();

        Button signupBtn = new Button("Create Account");

        // Action when button is clicked
        signupBtn.setOnAction(e -> {
            String username = userField.getText();
            String email = emailField.getText();
            String password = passField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                errorLabel.setText("Username and password are required!");
                return;
            }

            boolean success = LoginDatabase.addUser(username, email, password);

            if (success) {
                errorLabel.setText("Account created successfully!");
                new Thread(() -> {
                    try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
                    signupStage.close();
                }).start();
            } else {
                errorLabel.setText("Error: username may already exist.");
            }
        });

        // Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(userLabel, userField, emailLabel, emailField, passLabel, passField, signupBtn, errorLabel);

        Scene scene = new Scene(layout, 300, 250);
        signupStage.setScene(scene);
        signupStage.show();
    }
}
