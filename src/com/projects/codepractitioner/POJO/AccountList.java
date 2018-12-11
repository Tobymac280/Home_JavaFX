package com.projects.codepractitioner.POJO;


/*
 * Description: This is a class for holding multiple accounts at a time
 * Created: 12/03/2018
 * Author: Nik
 */

import java.util.ArrayList;

public class AccountList {
    private ArrayList<Account> accounts;
    // A variable for tracking how man accounts their are
    private static int numberAccounts = 0;

    public AccountList() {
        this.accounts = new ArrayList<>();
    }

    // getter
    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    // setter
    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    // Methods

    /**
     * Allows someone to add to this collection of Account objects
     */
    public void addAccount(String username, String password, String firstname, String lastName, int age) {
        if (findByUsername(username) == null) { // if an Account with that name doesn't exist yet
            accounts.add(new Account(username, password, firstname, lastName, age));
        } else {
            System.out.println("Account couldn't be created! Copied username.");
        }
    }

    /**
     * Allows someone to add to this collection of Account objects
     */
    public void addAccount(Account newAccount) {
        // simply passes all fields from newAccount to the other method
        addAccount(newAccount.getUsername(), newAccount.getPassword(), newAccount.getFirstName(), newAccount.getLastName(), newAccount.getAge());
    }

    /**
     * A method that checks if there are any users with that username yet, if there are return nothing
     */
    public Account findByUsername(String username) {
        if (accounts.isEmpty()) // there is nothing in the list yet
            return null; // return null
        for (Account account : accounts) { // for-each loop that will go through each Account object and check their name
            if (account.getUsername().equals(username)) // if the usernames match
                return account; // return the account
        }
        // found nothing, return null
        return null;
    }
}
