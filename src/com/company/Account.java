package com.company;

public class Account {
    private static int initialID = 69420;
    private double balance;
    private int accountID;

   public Account() {
        accountID = initialID++;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getAccountID() {
        return accountID;
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Not enough money on the account");
            return;
        }
        balance -= amount;
    }

    public void deposit(double amount) {
        if (amount < 0) {
            System.out.println("Invalid Deposit");
            return;
        }
        balance += amount;
    }

    @Override
    public String toString() {
        return "accountID: " + accountID + "\n" +
                "balance: " + balance + "\n"+" DKK"+ "\n";


    }
}
