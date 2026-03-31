package model;
 
import java.util.ArrayList;
 
public class Account {
    public int accNo;
    public String name;
    public int pin;
    public double balance;
    public String type;
    public ArrayList<Transaction> history = new ArrayList<>();
 
    public Account(int accNo, String name, int pin, double balance, String type) {
        this.accNo = accNo;
        this.name = name;
        this.pin = pin;
        this.balance = balance;
        this.type = type;
        history.add(new Transaction(balance, "INIT", "CASH"));
    }
 
    // Constructor used when loading from file (skips adding INIT transaction)
    public Account(int accNo, String name, int pin, double balance, String type, boolean fromFile) {
        this.accNo = accNo;
        this.name = name;
        this.pin = pin;
        this.balance = balance;
        this.type = type;
    }
 
    public void deposit(double amt, String method) {
        balance += amt;
        history.add(new Transaction(amt, "DEPOSIT", method));
    }
 
    public void withdraw(double amt, String method) {
        if (type.equals("Current")) {
            // Current accounts can overdraft up to 5000
            if (amt > balance + 5000) {
                System.out.println("Insufficient funds (overdraft limit: 5000)");
                return;
            }
        } else {
            if (amt > balance) {
                System.out.println("Insufficient funds");
                return;
            }
        }
        balance -= amt;
        history.add(new Transaction(amt, "WITHDRAW", method));
    }
 
    public void interest() {
        if (type.equals("Savings")) {
            double i = balance * 0.05;
            balance += i;
            history.add(new Transaction(i, "INTEREST", "SYSTEM"));
        } else {
            System.out.println("Interest only applies to Savings accounts");
        }
    }
}
package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Transaction {
    public String id;
    public double amount;
    public String type;
    public String method;
    public String time;

    public Transaction(double amount, String type, String method) {
        this.amount = amount;
        this.type = type;
        this.method = method;
        this.id = "TXN" + (100000 + new Random().nextInt(900000));
        this.time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    @Override
    public String toString() {
        return id + " | " + type + " | " + amount + " | " + method + " | " + time;
    }
}
