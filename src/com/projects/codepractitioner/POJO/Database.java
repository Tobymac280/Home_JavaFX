package com.projects.codepractitioner.POJO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    private static Connection connection;

    public static void createConnection() {
        String username, password;

        System.out.println("Please enter the following credentials to login.");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        username = scanner.nextLine();
        System.out.print("Enter password: ");
        password = scanner.nextLine();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", username, password);
            // Database how now been successfully connected.
            System.err.println("Database was successfully connected.");

        } catch (ClassNotFoundException e) {
            System.err.println("Class not found.");
        } catch (SQLException e) {
            System.err.println("SQL exception thrown.");
        }
    }

    /**
     * Returns an ArrayList of Accounts -- retrieved from the database
     */
    public static ArrayList<Account> getAccounts() {
        ArrayList<Account> accounts = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM LOGIN_ACCOUNTS");
            while (resultSet.next()) { // as long as the next row is not null
                String username, password, firstName, lastName;
                int age;
                username = resultSet.getString("username");
                password = resultSet.getString("password");
                firstName = resultSet.getString("firstname");
                lastName = resultSet.getString("lastname");
                age = resultSet.getInt("age");
                // place all the new items into a new Acount object
                Account newAccount = new Account(username, password, firstName, lastName, age);
                // add the account to the list
                accounts.add(newAccount);
            }
        } catch (SQLException exc) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, exc);
        }

        return accounts;
    } // end of getAccounts()

    /**
     * Returns QuizItems object -- retrieved from the database
     */
    public static QuizItems getQuizItems() {
        QuizItems quizItems = new QuizItems();
        ArrayList<QuizItem> quizItemArrayList = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM flashcard");
            while (resultSet.next()) {
                String question, answer;
                question = resultSet.getString("question");
                answer = resultSet.getString("answer");
                QuizItem quizItem = new QuizItem(question, answer);
                quizItemArrayList.add(quizItem);
            }
        } catch (SQLException exc) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, exc);
        }
        // add the items to the QuizItems object
        quizItems.setQuizItems(quizItemArrayList);
        return quizItems;
    }
}
