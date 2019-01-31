package com.projects.codepractitioner.Windows;

/*
 * Description: This class contains static methods for displaying windows for when a user is logged in
 * Created: 12/03/2018
 * Author: Nik
 */

import com.projects.codepractitioner.POJO.*;
import com.projects.codepractitioner.POJO.Account.Account;
import com.projects.codepractitioner.POJO.Quiz.Quiz;
import com.projects.codepractitioner.POJO.Quiz.QuizItems;
import com.projects.codepractitioner.POJO.TodoItem.TodoItem;
import com.projects.codepractitioner.POJO.TodoItem.TodoItemList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;


public class UserBoxes {
    private static final String stylesheet = "/com/projects/codepractitioner/Resources/master.css";

    /**
     * Displays a settings window for an account that is passed in
     */
    public static void displaySettings(Account account) {
        Stage window = new Stage();

        /* Components */
        // TOP
        Button logout_Button = new Button("Logout"); // logs out the user
        Button quiz_Button = new Button("Take quiz");
        Button todoItems_Button = new Button("TODO Items");
        // CENTER
        TextArea displayArea = new TextArea();
        displayArea.setEditable(false); // don't let the user edit the text area
        displayArea.setText("ACCOUNT DETAILS\n----------------------------------------------------------\n" +
                "Username: " + account.getUsername() + "\n" +
                "First name: " + account.getFirstName() + "\n" +
                "Last name: " + account.getLastName() + "\n" +
                "Age: " + account.getAge() + "\n");

        /* Events */
        logout_Button.setOnAction(event -> window.close()); // closes this window
        // when the button is pushed, open the quizzes window
        quiz_Button.setOnAction(event -> {
            UserBoxes.dailyQuiz();
        });
        // window to display all of the TodoItems
        todoItems_Button.setOnAction(event -> {
            UserBoxes.todoItems();
        });

        /* Layouts */
        // TOP layout
        HBox topLayout = new HBox(20);
        topLayout.getChildren().addAll(logout_Button, quiz_Button, todoItems_Button);
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
        mainScene.getStylesheets().add(stylesheet);

        // Window options
        window.setTitle("Settings for " + account.getUsername());
        window.setScene(mainScene); // sets the scene
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();
    }

    /**
     * A window for providing a daily quiz for users.
     */
    public static void dailyQuiz() {
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
                if (theAnswer.equals("true")) {
                    theQuiz.addCorrect();
                } else if (theAnswer.equals("false")) {
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
                if (theAnswer.equals("false")) {
                    theQuiz.addCorrect();
                } else if (theAnswer.equals("true")) {
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
        mainScene.getStylesheets().add(stylesheet);

        /* Window options */
        window.setTitle("Daily Quiz");
        window.setScene(mainScene); // sets the scene
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();
    }

    public static void todoItems(){
        Stage window = new Stage();
        TodoItemList todoItemList = Database.getTodoItems();

        /* Components */
        TextArea todoList_TextArea = new TextArea();
        Button checkTodoItems_Button = new Button("Check for todo items.");

        /* Events */
        checkTodoItems_Button.setOnAction(event -> {
            todoList_TextArea.appendText("\n\nTodo List Items: \n");
            for(TodoItem currentItem : todoItemList.getTodoItems()){
                String isComplete = (currentItem.isCompleted()) ? "[X]" : "[]";
                todoList_TextArea.appendText("\"" + currentItem.getTitle() + "\" : \"" + currentItem.getDescription() + "\" : " + isComplete + "\n");
            }
        });

        /* Layouts */
        // main layout
        VBox mainLayout = new VBox();
        mainLayout.getChildren().add(todoList_TextArea);
        mainLayout.getChildren().add(checkTodoItems_Button);

        /* Scene */
        Scene mainScene = new Scene(mainLayout, 600, 400);
        mainScene.getStylesheets().add(stylesheet);

        /* Window options */
        window.setTitle("Todo Items");
        window.setScene(mainScene); // sets the scene
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();
    }

    /**
     * Creates a new account and returns an Account object
     */
    public static Account createAccount() {
        ArrayList<Account> accounts = new ArrayList<>();
        Account newAccount = null;
        Stage window = new Stage();

        /* Components */
        Label userName_Label = new Label("User name: ");
        TextField userName_TextField = new TextField();
        Label password_Label = new Label("Password: ");
        PasswordField password_TextField = new PasswordField();
        Label re_enter_password_Label = new Label("Re-enter password: ");
        PasswordField re_enter_password_TextField = new PasswordField();
        Label firstName_Label = new Label("First name: ");
        TextField firstName_TextField = new TextField();
        Label lastName_Label = new Label("Last name: ");
        TextField lastName_TextField = new TextField();
        Label age_Label = new Label("Age: ");
        TextField age_TextField = new TextField();
        Button submit_Button = new Button("Submit");
        Button cancel_Button = new Button("Cancel");
        Label notification_Label = new Label("Please enter information into fields");

        /* Events */
        submit_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (userName_TextField.getText().trim().length() >= 5 && password_TextField.getText().trim().length() >= 5
                        && re_enter_password_TextField.getText().trim().length() >= 5 && firstName_TextField.getText().trim().length() > 0 && lastName_TextField.getText().trim().length() > 0 && age_TextField.getText().trim().length() > 0) {
                    if (password_TextField.getText().equals(re_enter_password_TextField.getText())) { // are the passwords the same?
//                        int age = Integer.parseInt(age_TextField.getText());
//                        newAccount = new Account(userName_TextField.getText(), password_TextField.getText(), firstName_TextField.getText(), lastName_TextField.getText(), age);
                        int age = Integer.parseInt(age_TextField.getText());
                        Account newAccount = new Account(userName_TextField.getText(), password_TextField.getText(), firstName_TextField.getText(), lastName_TextField.getText(), age);
                        try{
                            Database.addAccount(newAccount);
                            notification_Label.setText("Account created.");
                            window.close();
                        }catch (SQLIntegrityConstraintViolationException exc){
                            notification_Label.setText("Account creation failed. That username already exists.");
                        }
                    } else {
                        notification_Label.setText("Passwords don't math.");
                    }
                } else {
                    notification_Label.setText("Fields must all have at least 5 characters, except for age, first name, and last name.");
                }
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
        firstRow.setAlignment(Pos.CENTER);
        HBox secondRow = new HBox();
        secondRow.getChildren().addAll(password_Label, password_TextField);
        secondRow.setAlignment(Pos.CENTER);
        HBox thirdRow = new HBox();
        thirdRow.getChildren().addAll(re_enter_password_Label, re_enter_password_TextField);
        thirdRow.setAlignment(Pos.CENTER);
        HBox fourthRow = new HBox();
        fourthRow.getChildren().addAll(firstName_Label, firstName_TextField);
        fourthRow.setAlignment(Pos.CENTER);
        HBox fifthRow = new HBox();
        fifthRow.getChildren().addAll(lastName_Label, lastName_TextField);
        fifthRow.setAlignment(Pos.CENTER);
        HBox sixthRow = new HBox();
        sixthRow.getChildren().addAll(age_Label, age_TextField);
        sixthRow.setAlignment(Pos.CENTER);
        HBox seventhRow = new HBox();
        seventhRow.getChildren().addAll(submit_Button, cancel_Button);
        seventhRow.setAlignment(Pos.CENTER);

        VBox mainLayout = new VBox(10);
        // add all of the layout to the main layout
        mainLayout.getChildren().addAll(firstRow, secondRow, thirdRow, fourthRow, fifthRow, sixthRow, seventhRow, notification_Label);
        mainLayout.setAlignment(Pos.CENTER);

        /* Scene */
        Scene mainScene = new Scene(mainLayout, 600, 400);
        mainScene.getStylesheets().add(stylesheet);

        /* Window options */
        window.setTitle("Register new account");
        window.setScene(mainScene); // sets the scene
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();

        return newAccount;
    }
}
