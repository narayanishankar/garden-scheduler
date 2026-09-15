package com.example.gardenapp;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class LoginForm {

    public static User loggedInUser = null;

    public static void show() {
        Stage loginStage = new Stage();
        loginStage.setTitle("Login");

        Label errorLabel = new Label();

        Label userLabel = new Label("Username:");
        TextField userField = new TextField();

        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();

        Button loginBtn = new Button("Login");
        loginBtn.setOnAction(e -> {
            String username = userField.getText();
            String password = passField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                errorLabel.setText("Please enter username and password.");
                errorLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            User user = LoginDatabase.checkLogin(username, password);
            if (user!=null) {
                loggedInUser = user;
                errorLabel.setText("Login successful!");
                loginStage.close();
            } else {
                errorLabel.setText("Invalid username or password.");
                errorLabel.setStyle("-fx-text-fill: red;");
            }

        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(userLabel, userField, passLabel, passField, loginBtn, errorLabel);

        Scene scene = new Scene(layout, 300, 200);
        loginStage.setScene(scene);
        loginStage.showAndWait();
    }
}
