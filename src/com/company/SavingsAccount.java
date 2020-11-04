package com.company;

public class SavingsAccount extends Account {
    private static String accountType = "Savings";

    SavingsAccount(double firstDeposit) {
        super();
        this.setBalance(firstDeposit);
    }

    @Override
    public String toString() {
        return "Account type:" + accountType + " Account\n" +
                "Account ID:" + this.getAccountID() + "\n" +
                "Balance:" + this.getBalance()+" DKK"+ "\n";
    }
}

