package budget;

public class BankAccount {
    static double balance = 0.0;

    public static void addToBalance(double amount) {
        balance += amount;
    }

    public static void removeFromBalance(double amount) {
        balance = balance - amount <= 0 ? 0.0 : balance - amount;
    }

    public static void setBalance(double amount) {
        balance = amount;
    }

    public static double getBalance() {
        return balance;
    }
}