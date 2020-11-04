package com.company;

import java.util.Scanner;

public class MainMenu {

    private Scanner input;
    private DataBase dataBase;
    private boolean exit;
    private User loggedInUser = null;

    {
        input = new Scanner(System.in);
        dataBase = new DataBase();
        boolean exit;

    }

    public void runMainMenu() {
        while (!exit) {
            if (loggedInUser == null) {
                showLoginMenu();
                int action = getInput();
                performLoginAction(action);
            } else {
                showMainMenu();
                int action = getInput();
                performAction(action);
            }
        }
    }

    private void showLoginMenu() {
        System.out.println("Welcome to LJT Bank!");
        System.out.println("Please select the action you would like to perform!");
        System.out.println("1. Create User");
        System.out.println("2. Login");
        System.out.println("3. Exit");

    }

    private void showMainMenu() {
        System.out.println("Welcome " + loggedInUser.getName() + " to your LJT Bank!");
        System.out.println("Please select the action you would like to perform!");
        System.out.println("1. Create Bank Account");
        System.out.println("2. Display User Details");
        System.out.println("3. Withdraw Funds");
        System.out.println("4. Deposit Funds");
        System.out.println("5. Transfer Funds");
        System.out.println("6. Log out");
        System.out.println("7. Exit");

    }

    private int getInput() {
        int action = -1;
        do {
            System.out.println("Enter Selection:");
            try {
                action = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("The selected option does not exist, please try again!");
            }
            if ((action <= 0) || (action > 7)) {
                System.out.println("The selected option does not exist, please try again!");
            }
        }
        while ((action <= 0) || (action > 7));
        return action;
    }

    private void performLoginAction(int action) {
        switch (action) {
            case 1:
                createUser();
                break;
            case 2:
                login();
                break;
            case 3:
                System.out.println("Thank you for choosing LJT Bank. Have a nice day!");
                System.exit(0);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + action);
        }
    }

    private void login() { //TODO: return only name
        System.out.println("Please select user by typing in the username");
        for (User usr : dataBase.getUsers()) {
            System.out.println(usr.getName());
        }
        String name = input.nextLine();
        boolean flag = false;
        for (User usr : dataBase.getUsers()) {
            if (usr.getName().equals(name)) {
                loggedInUser = usr;
                System.out.println("Logged in as " + name);
            }
        }

    }

    private void createUser() { // TODO: check for duplicate names
        String name, birthDate;
        System.out.println("Please enter a username");
        name = input.nextLine();
        System.out.println("Please enter your Birthday (DD/MM/YYYY)");
        birthDate = input.nextLine();

        User user = new User(name, birthDate);
        dataBase.registerUser(user);
        System.out.println("Now you are registered. You may create a new account, or log in!");
    }

    private void performAction(int action) {
        switch (action) {
            case 1:
                createNewAccount();
                break;
            case 2:
                DisplayDetails();
                break;
            case 3:
                withdrawFunds();
                break;
            case 4:
                depositFunds();
                break;
            case 5:
                transferFunds();
                break;
            case 6:
                logout();
                break;
            case 7:
                System.out.println("Thank you for choosing LJT Bank. Have a nice day!");
                System.exit(0);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + action);
        }
    }

    private void logout() {
        loggedInUser = null;
    }

    private void transferFunds() {
        System.out.println("Select Account to Transfer from by typing the account ID"+ "\n");
        int from = chooseAccount(loggedInUser);
        System.out.println("Select user to transfer to by typing their name"+ "\n");
        for (User usr : dataBase.getUsers()) {
            System.out.println(usr.getName());
        }
        String name = input.nextLine();
        User userTo = dataBase.getUser(name);
        if (userTo == null) {
            System.out.println("invalid user");
            return;
        }

        int to = chooseAccount(userTo);
        while (to == from) {
            System.out.println("Cant transfer to the same account");
            System.out.println("Select user to transfer to by typing their name");
            for (User usr : dataBase.getUsers()) {
                System.out.println(usr.getName());
            }
            name = input.nextLine();
            userTo = dataBase.getUser(name);
            if (userTo == null) {
                System.out.println("invalid user");
                return;
            }
            to = chooseAccount(userTo);
        }
        System.out.println("Type amount to transfer");
        double amount = 0;
        try {
            amount = Double.parseDouble(input.nextLine());
        } catch (NumberFormatException e) {
            amount = 0;
        }
        if (amount > loggedInUser.getAccount(from).getBalance()) {
            System.out.println("insufficient funds");
        } else {
            loggedInUser.getAccount(from).withdraw(amount);
            userTo.getAccount(to).deposit(amount);
            System.out.println("You have now transferred "+amount+" DKK from account "+from+" to account "+to );
        }
    }

    private void depositFunds() {
        int account = chooseAccount(loggedInUser);
        if (account >= 0) {
            System.out.println("Enter the amount you would like to deposit:");
            double amount = 0;
            try {
                amount = Double.parseDouble(input.nextLine());
            } catch (NumberFormatException e) {
                amount = 0;
            }
            loggedInUser.getAccount(account).deposit(amount);
            System.out.println("You have deposited "+amount+ " DKK to account " + account+ "\n"+"Your new balance is "+loggedInUser.getAccount(account).getBalance());
        }
    }

    private int chooseAccount(User user) {
//        ArrayList<User> users = dataBase.getUsers();
        if (user.getAccounts().size() <= 0) {
            System.out.println("Currently no customers at the bank of LJT");
            return -1;
        }
        System.out.println("Select account by typing account ID"+ "\n");
        for (int x = 0; x < user.getAccounts().size(); x++) {
            System.out.println("Account Information" + "\n" + user.getAccounts().get(x).toString());
        }
        int account = 0;
        try {
            account = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            account = -1;
        }
        boolean flag = false;
        for (Account acc : user.getAccounts()) {
            if (acc.getAccountID() == account) {
                flag = true;
            }
        }
        if (flag == true) {
            return account;
        } else {
            return -1;
        }

    }

    private void withdrawFunds() {
        int account = chooseAccount(loggedInUser);
        if (account >= 0) {
            System.out.println("Enter the amount you would like to withdraw:");
            double amount = 0;
            try {
                amount = Double.parseDouble(input.nextLine());
            } catch (NumberFormatException e) {
                amount = 0;
            }
            loggedInUser.getAccount(account).withdraw(amount);
            System.out.println("You have withdrawn "+amount+ " DKK from account " + account+ "\n"+"Your new balance is "+loggedInUser.getAccount(account).getBalance());
        }
    }

    private void DisplayDetails() {
        System.out.println(loggedInUser.toString());
    }

    private void createNewAccount() {
        String accountType = "";
        double firstDeposit = 0;
        boolean validity = false;
        while (!validity) {
            System.out.println("Please select account type (main/savings):");
            accountType = input.nextLine();
            if (accountType.equalsIgnoreCase("savings") || (accountType.equalsIgnoreCase("main"))) {
                validity = true;
            } else {
                System.out.println("Please enter valid account type (main/savings)");
            }
        }
        validity = false;
        while (!validity) {
            System.out.println("Please make first deposit");
            try {
                firstDeposit = Double.parseDouble(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter numbers only!");
            }
            validity = true;
        }
        Account account;
        if (accountType.equalsIgnoreCase("MainAccount")) {
            account = new MainAccount(firstDeposit);
        } else {
            account = new SavingsAccount(firstDeposit);
        }
        loggedInUser.addAccount(account);
        System.out.println("You have now created a " + accountType + " bank account and deposited " + firstDeposit+" DKK"+"\n"+"You will now be returned to the main menu!");
    }
}



