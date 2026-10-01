package edu.course.lab02;

public class BankAccount {

    private int balance;

    public BankAccount(int initialBalance){
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative.");
        }
        this.balance = initialBalance;
    }

    public void deposit(int amount){
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit cannot be less than 0.");
        }
        this.balance +=  amount;
    }

    public void withdraw(int amount){
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdraw cannot be less than 0.");
        }

        if (amount > this.balance) {
            throw new IllegalArgumentException("Withdraw cannot be more than the amount available in the account.");
        }

        this.balance -= amount;
    }

    public int getBalance() {
        return this.balance;
    }
}
