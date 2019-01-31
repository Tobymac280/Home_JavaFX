package com.projects.codepractitioner;

/*
 * Description: Main method. Connects all functionality.
 * Created: 12/03/2018
 * Author: Nik
 */

import com.projects.codepractitioner.POJO.Account.Account;
import com.projects.codepractitioner.POJO.Account.AccountList;
import com.projects.codepractitioner.POJO.Database;
import com.projects.codepractitioner.Windows.PopupBox;
import com.projects.codepractitioner.Windows.UserBoxes;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    // Change these values to change the window's values
    private static String windowTitle = "Login Screen";
    private final double WIDTH = 600; // sets the window's width
    private final double HEIGHT = 400; // sets the window's height
    // Create a collection of accounts
    private static AccountList accountsList; // this will allow easier use of accounts when adding and deleting

    public static void main(String[] args) {
        Database.createConnection(); // create the connection
        accountsList = new AccountList();
        accountsList.setAccounts(Database.getAccounts());
        launch(args);
    }

    /* Author: Nik */
    public void start(Stage stage) {
        // Objects for the scene
        // TOP objects
        Button about = new Button("About"); // about button

        // CENTER objects
        // Username objects
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        // Password objects
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");
        // Submit button
        Button login_Button = new Button("Login");
        Button register_Button = new Button("Register");
        Button logout_Button = new Button("Logout");
        // Error label
        Label errorLabel = new Label("");

        // Events -- the place to add new functionality when the user clicks an object
        stage.setOnCloseRequest(event -> stage.close()); // tells the window to close completely when you hit the X (quit) button
        login_Button.setOnAction(event -> {
            // Check what's in the username field and password field
            if (!(usernameField.getText().equals("")) || !(passwordField.getText().equals(""))) { // if the fields are not empty
                Account checkerAccount = accountsList.findByUsername(usernameField.getText());
                if (checkerAccount != null) { // if an account was found
                    // check if the password matches
                    if (checkerAccount.getPassword().equals(passwordField.getText())) {
                        // open window for displaying settings
                        errorLabel.setStyle("-fx-text-fill: green");
                        errorLabel.setText("Account logged in.");
                        UserBoxes.displaySettings(checkerAccount);
                        // after this method returns, update account that was possibly changed
                    } else { // no password found
                        // display an error message
                        errorLabel.setStyle("-fx-text-fill: red");
                        errorLabel.setText("Incorrect password.");
                    }
                } else { // no username found
                    // display an error message
                    errorLabel.setStyle("-fx-text-fill: red");
                    errorLabel.setText("Username not found.");
                }
            } else {
                // display an error message
                errorLabel.setStyle("-fx-text-fill: red");
                errorLabel.setText("You must enter text into both fields.");
            }
        });
        about.setOnAction(event -> {
            ArrayList<String> labels = new ArrayList<>(), information = new ArrayList<>(); // creates and initializes two new ArrayList objects
            // Populate the labels list
            labels.add("What is this program?");
            labels.add("Author");
            // Populate the information list
            information.add("This program allows many users the ability to track tasks and take fun \"Daily Quizzes\".");
            information.add("Nik Fernandez. He created this in December of 2018.");
            PopupBox.display("About", labels, information); // calls the method to show the window
        });
        register_Button.setOnAction(event -> {
            // Open the window
            Account newAccount = UserBoxes.createAccount();
            // Add newAccount to the list, as long as it was properly created
            if (newAccount != null) {
                accountsList.addAccount(newAccount);
            }
        });

        logout_Button.setOnAction(event -> {
            System.exit(0);
        });

        // Layout -- the place to put all of the objects
        BorderPane layout = new BorderPane();
        // TOP
        // Creates a horizontal box layout for the top of the window -- this includes things like Settings and Quit buttons
        HBox topLayout = new HBox(20);
        topLayout.getChildren().add(about);
        // CENTER
        GridPane centerLayout = new GridPane();
        centerLayout.setAlignment(Pos.CENTER); // SETS WHERE THE LOGIN OPTIONS ARE ALIGNED ON THE SCREEN
        centerLayout.setHgap(10); // sets the horizontal gap
        centerLayout.setVgap(10); // sets the vertical gap
        GridPane.setConstraints(usernameLabel, 0, 0); // first row, first column
        GridPane.setConstraints(usernameField, 1, 0); // first row, second column
        GridPane.setConstraints(passwordLabel, 0, 1); // second row, first column
        GridPane.setConstraints(passwordField, 1, 1); // second row, second column
        GridPane.setConstraints(login_Button, 0, 2); // third row, first column
        GridPane.setConstraints(register_Button, 1, 2); // third row, second column
        GridPane.setConstraints(logout_Button, 2, 2); // third row, second column
        GridPane.setConstraints(errorLabel, 0, 3);
        centerLayout.getChildren().setAll(usernameField, usernameLabel, passwordField, passwordLabel, login_Button, register_Button, logout_Button, errorLabel); // adds all of the objects to the GridPane
        // Add the layouts to the main BorderPane layout
        layout.setTop(topLayout); // adds the TOP layout to the BorderPane
        layout.setCenter(centerLayout); // adds the CENTER layout to the BorderPane

        // Scene -- creates a scene and specifies its WIDTH and HEIGHT (these two constants can found at the top of this file, as a member variable)
        Scene mainScene = new Scene(layout, WIDTH, HEIGHT);
        mainScene.getStylesheets().add("/com/projects/codepractitioner/Resources/master.css");

        // Main stage options
        // set the window's title
        stage.setTitle(windowTitle);
        // set the scene
        stage.setScene(mainScene);
        stage.show();
    }
}
