package com.projects.codepractitioner.Windows;

/*
 * Description: This class contains static methods for displaying windows for when a user is logged in
 * Created: 12/03/2018
 * Author: Nik
 */

import com.projects.codepractitioner.POJO.*;
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

import javax.xml.soap.Text;

public class UserBoxes {
    /** Displays a settings window for an account that is passed in */
    public static void displaySettings(Account account){
        Stage window = new Stage();

        /* Components */
        // TOP
        Button logoutButton = new Button("Logout"); // logs out the user
        Button quizButton = new Button("Take quiz");
        // CENTER
        TextArea displayArea = new TextArea();
        displayArea.setEditable(false); // don't let the user edit the text area
        displayArea.setText("ACCOUNT DETAILS\n----------------------------------------------------------\n" +
                "Username: " + account.getUsername() + "\n" +
                "First name: " + account.getFirstName() + "\n" +
                "Last name: " + account.getLastName() + "\n" +
                "Age: " + account.getAge() + "\n");

        /* Events */
        logoutButton.setOnAction(event -> window.close()); // closes this window
        quizButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                UserBoxes.dailyQuiz();
            }
        });

        /* Layouts */
        // TOP layout
        HBox topLayout = new HBox(20);
        topLayout.getChildren().addAll(logoutButton, quizButton);
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

    public static void dailyQuiz(){
        Stage window = new Stage();
        // load everything in for the quiz
        QuizItems quizItems = Database.getQuizItems();
        Quiz theQuiz = new Quiz(quizItems);

        /* Components */
        TextField question_TextField = new TextField();
        question_TextField.setText(theQuiz.getQuestion());
        question_TextField.setEditable(false);
        Button true_Button = new Button("True");
        Button false_Button = new Button("False");
        Button cancel_Button = new Button("Cancel");
        Label notificationLabel = new Label("Choose an option.");

        /* Events */
        true_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String theAnswer = theQuiz.getAnswer().toLowerCase();
                if(theAnswer.equals("true")){
                    theQuiz.addCorrect();
                }else if(theAnswer.equals("false")){
                    theQuiz.addIncorrect();
                }
                // print out the results to the user
                notificationLabel.setText("Correct: " + theQuiz.getNumCorrect() + ", Incorrect: " + theQuiz.getNumIncorrect());
                question_TextField.setText(theQuiz.getQuestion());
            }
        });
        false_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String theAnswer = theQuiz.getAnswer().toLowerCase();
                if(theAnswer.equals("false")){
                    theQuiz.addCorrect();
                }else if(theAnswer.equals("true")){
                    theQuiz.addIncorrect();
                }
                // print out the results to the user
                notificationLabel.setText("Correct: " + theQuiz.getNumCorrect() + ", Incorrect: " + theQuiz.getNumIncorrect());
                question_TextField.setText(theQuiz.getQuestion());
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

    /** Creates a new account and returns an Account object */
    public static Account createAccount(){
        Account newAccount = null;
        Stage window = new Stage();

        /* Components */
        Label userName_Label = new Label("User name: ");
        TextField userName_TextField = new TextField();
        Label password_Label = new Label("Password: ");
        TextField password_TextField = new TextField();
        Label re_enter_password_Label = new Label("Re-enter password: ");
        TextField re_enter_password_TextField = new TextField();
        Label firstName_Label = new Label("First name: ");
        TextField firstName_TextField = new TextField();
        Label lastName_Label = new Label("Last name: ");
        TextField lastName_TextField = new TextField();
        Button submit_Button = new Button("Submit");
        Button cancel_Button = new Button("Cancel");

        /* Events */
        submit_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        cancel_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // make all fields empty
                userName_TextField.setText("");
                password_TextField.setText("");
                re_enter_password_TextField.setText("");
                firstName_TextField.setText("");
                lastName_TextField.setText("");
                // close this window
                window.close();
            }
        });
        /* Layouts */
        HBox firstRow = new HBox();
        firstRow.getChildren().addAll(userName_Label, userName_TextField);
        HBox secondRow = new HBox();
        secondRow.getChildren().addAll(password_Label, password_TextField);
        HBox thirdRow = new HBox();
        thirdRow.getChildren().addAll(re_enter_password_Label, re_enter_password_TextField);
        HBox fourthRow = new HBox();
        fourthRow.getChildren().addAll(firstName_Label, firstName_TextField);
        HBox fifthRow = new HBox();
        fifthRow.getChildren().addAll(lastName_Label, lastName_TextField);
        HBox sixthRow = new HBox();
        sixthRow.getChildren().addAll(submit_Button, cancel_Button);

        VBox mainLayout = new VBox();
        // add all of the layout to the main layout
        mainLayout.getChildren().addAll(firstRow, secondRow, thirdRow, fourthRow, fifthRow, sixthRow);

        /* Scene */
        Scene mainScene = new Scene(mainLayout, 600, 400);

        /* Window options */
        window.setTitle("Register new account");
        window.setScene(mainScene); // sets the scene
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();

        return newAccount;
    }
}
