package com.projects.codepractitioner.Windows;

/*
 * Description: This class contains static methods for displaying windows for when a user is logged in
 * Created: 02/18/2018
 * Author: Nik
 */

import com.projects.codepractitioner.POJO.Account;
import com.projects.codepractitioner.POJO.Database;
import com.projects.codepractitioner.POJO.QuizItem;
import com.projects.codepractitioner.POJO.QuizItems;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UserBoxes {
    /** Displays a settings window for an account that is passed in */
    public static void displaySettings(Account account, Database database){
        Stage window = new Stage();

        /* Components */
        // TOP
        Button logoutButton = new Button("Logout"); // logs out the user
        Button quizButton = new Button("Take quiz");
        Button inventory = new Button("Inventory"); // opens the inventory menu
        // CENTER
        TextArea displayArea = new TextArea();
        displayArea.setEditable(false); // don't let the user edit the text area
        displayArea.setText("ACCOUNT DETAILS\n----------------------------------------------------------\n" +
                "Username: " + account.getUsername() + "\n" +
                "First name: " + account.getFirstName() + "\n" +
                "Last name: " + account.getLastName() + "\n" +
                "Age: " + account.getAge() + "\n");
        // LEFT -- ONLY shows for admins
        Label titleLabel = new Label("Administrator Options:");
        Button addAccountButton = new Button("Create a new account");
        Button deleteAccount = new Button("Delete Account");

        /* Events */
        logoutButton.setOnAction(event -> window.close()); // closes this window
        quizButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                UserBoxes.dailyQuiz(database);
            }
        });

        /* Layouts */
        // TOP layout
        HBox topLayout = new HBox(150);
        topLayout.getChildren().addAll(logoutButton, quizButton, inventory);
        topLayout.getStyleClass().add("styled-box");
        topLayout.setAlignment(Pos.CENTER);
        // CENTER layout
        VBox centerLayout = new VBox(20);
        // center the layout in the center of the screen
        centerLayout.setAlignment(Pos.CENTER);
        centerLayout.getStyleClass().add("styled-box");
        // add objects to CENTER
        centerLayout.getChildren().add(displayArea);
        // LEFT layout -- admin layout
        VBox leftLayout = new VBox(20);
        leftLayout.setAlignment(Pos.CENTER);
        leftLayout.getStyleClass().add("styled-box");
        // MAIN layout
        Insets marginSize = new Insets(40);
        BorderPane mainLayout = new BorderPane();
        // set margins for each node -- spacing between each layout
        BorderPane.setMargin(topLayout, marginSize);
        BorderPane.setMargin(centerLayout, marginSize);
        BorderPane.setMargin(leftLayout, marginSize);
        // set the TOP layout
        mainLayout.setTop(topLayout);
        // set the center layout
        mainLayout.setCenter(centerLayout);

        // Scene
        Scene mainScene = new Scene(mainLayout, 600, 400);
        mainScene.getStylesheets().add("/com/projects/codepractitioner/Resources/master.css");

        // Window options
        window.setTitle("Settings for " + account.getUsername());
        window.setScene(mainScene); // sets the scene
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();
    }

    public static void dailyQuiz(Database database){
        Stage window = new Stage();
        QuizItems quizItems = database.getQuizItems();
        QuizItem quizItem = quizItems.getQuizItems().get(0); // sets it equal to the first quiz item
        String question = quizItem.getQuestion();
        String answer = quizItem.getAnswer();

        /* Components */
        TextField question_TextField = new TextField();
        question_TextField.setText(question);
        question_TextField.setEditable(false);
        Button true_Button = new Button("True");
        Button false_Button = new Button("False");
        Button cancel_Button = new Button("Cancel");
        Label notificationLabel = new Label("Choose an option.");

        /* Events */
        true_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(answer.toLowerCase().equals("true")){
                    notificationLabel.setText("Correct: " + 1);
                }else{
                    notificationLabel.setText("Incorrect: " + 1);
                }
            }
        });
        false_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(answer.toLowerCase().equals("false")){
                    notificationLabel.setText("Correct: " + 1);
                }else{
                    notificationLabel.setText("Incorrect: " + 1);
                }
            }
        });
        cancel_Button.setOnAction(event -> window.close());

        /* Layouts */
        // Main layout
        VBox mainLayout = new VBox();
        mainLayout.setAlignment(Pos.CENTER);
        // Button layout
        HBox buttonLayout = new HBox();
        buttonLayout.setAlignment(Pos.CENTER);
        // add buttons to button layout
        buttonLayout.getChildren().add(true_Button);
        buttonLayout.getChildren().add(false_Button);
        // add all objects to the main layout
        mainLayout.getChildren().add(question_TextField);
        mainLayout.getChildren().add(buttonLayout);
        mainLayout.getChildren().add(notificationLabel);
        mainLayout.getChildren().add(cancel_Button);
        // MAIN layout
        Insets marginSize = new Insets(40);
        // set margins for each node -- spacing between each layout
        BorderPane.setMargin(question_TextField, marginSize);
        BorderPane.setMargin(buttonLayout, marginSize);
        BorderPane.setMargin(notificationLabel, marginSize);
        BorderPane.setMargin(cancel_Button, marginSize);

        /* Scene */
        Scene mainScene = new Scene(mainLayout, 600, 400);

        /* Window options */
        window.setTitle("Daily Quiz");
        window.setScene(mainScene); // sets the scene
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();
    }
}
