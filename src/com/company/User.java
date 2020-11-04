package com.company;

import java.util.ArrayList;

public class User {
    private final String name;
    private final String birthDate;
    private ArrayList<Account> accounts;

    public User(String name, String birthDate) {
        this.name = name;
        this.birthDate = birthDate;
        accounts = new ArrayList<>();

    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public Account getAccount(int accountID) {
        for (Account acc : accounts) {
            if (accountID == acc.getAccountID()) {
                return acc;
            }
        }
        return null;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String data = "Customer Information: \n" +
                "Name: " + getName() + "\n" +
                "Birthday: " + birthDate + "\n";
        for (Account acc : accounts) {
            data += acc.toString() + "\n";
        }
        return data;
    }
}
