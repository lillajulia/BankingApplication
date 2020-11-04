package com.company;

public class MainAccount extends Account {
    private static String accountType = "Main";

    MainAccount(double firstDeposit) {
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
