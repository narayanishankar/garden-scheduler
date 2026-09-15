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
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter both a username and password.");
                alert.showAndWait();
                return;
            }

            String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
            if (!email.matches(emailPattern)) {
                new Alert(Alert.AlertType.ERROR, "Please enter a valid email address format.").show();
                return;
            }

            boolean success = LoginDatabase.addUser(username, email, password);

            if (success) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Account Created! You can now log in.");
                alert.showAndWait(); // This pauses until they click OK
                signupStage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Registration Error");
                alert.setHeaderText("Username Taken");
                alert.setContentText("The username '" + username + "' is already in use. Please try a different one.");
                alert.showAndWait();
            }
        });

        // Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(userLabel, userField, emailLabel, emailField, passLabel, passField, signupBtn);

        Scene scene = new Scene(layout, 300, 250);
        signupStage.setScene(scene);
        signupStage.show();
    }
}
