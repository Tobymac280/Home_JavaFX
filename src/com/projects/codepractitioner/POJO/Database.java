package com.projects.codepractitioner.POJO;

import com.projects.codepractitioner.POJO.Account.Account;
import com.projects.codepractitioner.POJO.Quiz.QuizItem;
import com.projects.codepractitioner.POJO.Quiz.QuizItems;
import com.projects.codepractitioner.POJO.TodoItem.TodoItem;
import com.projects.codepractitioner.POJO.TodoItem.TodoItemList;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    private static Connection connection;

    public static void createConnection() {
//        String username, password;

//        System.out.println("Please enter the following credentials to login.");
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Enter username: ");
//        username = scanner.nextLine();
//        System.out.print("Enter password: ");
//        password = scanner.nextLine();

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "tobymac208", "Sparta_!3712");
            // Database how now been successfully connected.
            System.err.println("Database was successfully connected.");
        }catch (ClassNotFoundException e) {
            System.err.println("Class not found.");
        }catch (SQLException e) {
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
                // place all the new items into a new Account object
                Account newAccount = new Account(username, password, firstName, lastName, age);
                // add the account to the list
                accounts.add(newAccount);
            }
        } catch (SQLException exc) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, exc);
        }

        return accounts;
    } // end of getAccounts()

    /** Add a n account through the connection. */
    public static void addAccount(Account newAccount) throws SQLIntegrityConstraintViolationException{
        try(Statement statement = connection.createStatement()){
            String username = newAccount.getUsername(), password = newAccount.getPassword(), firstName = newAccount.getFirstName(), lastName = newAccount.getLastName();
            int age = newAccount.getAge();
            System.out.println("Username: " + username);
            // INSERT INTO login_accounts VALUES(username, password, firstName, lastName, age)
            String db_operation = "INSERT INTO login_accounts VALUES('" + username + "','" + password + "','" + firstName + "','" + lastName + "'," + age + ")";
            statement.execute(db_operation);
        }catch (SQLIntegrityConstraintViolationException exc){
            throw exc;
        }
        catch (SQLException exc){
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Attempting to add account.", exc);
        }
    }

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
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Attempting to get quiz items.", exc);
        }
        // add the items to the QuizItems object
        quizItems.setQuizItems(quizItemArrayList);
        return quizItems;
    }

    /**
     * Returns a list of TodoItems, obtained from the database. */
    public static TodoItemList getTodoItems(){
        TodoItemList todoItemList = new TodoItemList();
        ArrayList<TodoItem> todoItems = new ArrayList<>();

        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM todo_items");
            while(resultSet.next()){
                String title, description, bit;
                boolean isCompleted;
                title = resultSet.getString("title");
                description = resultSet.getString("description");
                bit = resultSet.getString("completed");
                if(bit.equals("1")){
                    isCompleted = true;
                }else{
                    isCompleted = false;
                }
                TodoItem todoItem = new TodoItem(title, description, isCompleted);
                todoItems.add(todoItem);
            }
        }catch (SQLException exc){
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Attempting to get todo items.", exc);
        }

        todoItemList.setTodoItems(todoItems);
        return todoItemList;
    }
}
