package com.example.pocketguard.Account;
/**
 * The Income_Expense_Balance_Update class represents an entity that holds information
 * about the balance, income, and expense of an account. This class follows the
 * JavaBeans design pattern.
 *
 * <p>The JavaBeans design pattern includes the following conventions:
 * <ul>
 *   <li>Private fields</li>
 *   <li>Public no-argument constructor</li>
 *   <li>Public getter and setter methods</li>
 * </ul>
 */
public class Income_Expense_Balance_Update {
    /**
     * The  balance of the account.
     */
    public String balance;
    /**
     * The income of the account.
     */
    public String income;
    /**
     * The  expense of the account.
     */
    public String expense;
    /**
     * Default no-argument constructor.
     * Initializes a new instance of the Income_Expense_Balance_Update class.
     */
    public Income_Expense_Balance_Update(){}
    /**
     * Parameterized constructor to initialize the balance, income, and expense.
     *
     * @param balance The balance of the account.
     * @param income The income of the account.
     * @param expense The expense of the account.
     */
    public Income_Expense_Balance_Update(String balance, String income, String expense) {
        this.balance = balance;
        this.income = income;
        this.expense = expense;
    }
    /**
     * Gets the balance of the account.
     *
     * @return The balance of the account.
     */
    public String getBalance() {
        return balance;
    }
    /**
     * Sets the balance of the account.
     *
     * @param balance The new balance of the account.
     */
    public void setBalance(String balance) {
        this.balance = balance;
    }
    /**
     * Gets the income of the account.
     *
     * @return The income of the account.
     */
    public String getIncome() {
        return income;
    }
    /**
     * Sets the income of the account.
     *
     * @param income The new income of the account.
     */
    public void setIncome(String income) {
        this.income = income;
    }
    /**
     * Gets the expense of the account.
     *
     * @return The expense of the account.
     */
    public String getExpense() {
        return expense;
    }

    /**
     * Sets the expense of the account.
     *
     * @param expense The new expense of the account.
     */
    public void setExpense(String expense) {
        this.expense = expense;
    }
}
