package com.example.pocketguard.Account;
/**
 * The Users class represents the model component in the MVC design pattern.
 * It encapsulates the user data, including personal details, account balances, and location information.
 *
 * <p>This class includes:
 * <ul>
 *   <li>Fields for storing user details such as email, password, name, date of birth, and security question</li>
 *   <li>Getters and setters for accessing and modifying these fields</li>
 *   <li>Two constructors: a no-argument constructor for Firebase's deserialization and a parameterized constructor for creating user objects</li>
 * </ul>
 */
public class Users {

    /** The user's email address. */
    public String email;
    /** The user's password. */
    public String pass;
    /** The user's name. */
    public String name;
    /** The user's date of birth. */
    public String dob;
    /** The user's security question answer. */
    public String security;
    /** The user's total balance. */
    public String total_balance;
    /** The user's total income. */
    public String total_income;
    /** The user's total expenses. */
    public String total_expense;
    /** The user's latitude for location-based services. */
    public String latitute;
    /** The user's longitude for location-based services. */
    public String longitute;


    /**
     * Default no-argument constructor required for Firebase deserialization.
     */

    public Users(){};

    /**
     * Parameterized constructor for creating a new user object with specified details.
     *
     * @param email the user's email address
     * @param pass the user's password
     * @param name the user's name
     * @param dob the user's date of birth
     * @param security the user's security question answer
     * @param total_balance the user's total balance
     * @param total_income the user's total income
     * @param total_expense the user's total expenses
     * @param latitute the user's latitude
     * @param longitute the user's longitude
     */
    public Users(String email, String pass, String name, String dob, String security, String total_balance, String total_income, String total_expense, String latitute, String longitute) {
        this.email = email;
        this.pass = pass;
        this.name = name;
        this.dob = dob;
        this.security = security;
        this.total_balance = total_balance;
        this.total_income = total_income;
        this.total_expense = total_expense;
        this.latitute = latitute;
        this.longitute = longitute;
    }
    /**
     * Gets the user's email address.
     *
     * @return the user's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     *
     * @param email the user's email address
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Gets the user's password.
     *
     * @return the user's password
     */
    public String getPass() {
        return pass;
    }
    /**
     * Sets the user's password.
     *
     * @param pass the user's password
     */
    public void setPass(String pass) {
        this.pass = pass;
    }
    /**
     * Gets the user's name.
     *
     * @return the user's name
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the user's name.
     *
     * @param name the user's name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Gets the user's date of birth.
     *
     * @return the user's date of birth
     */
    public String getDob() {
        return dob;
    }
    /**
     * Sets the user's date of birth.
     *
     * @param dob the user's date of birth
     */

    public void setDob(String dob) {
        this.dob = dob;
    }
    /**
     * Gets the user's security question answer.
     *
     * @return the user's security question answer
     */
    public String getSecurity() {
        return security;
    }

    /**
     * Sets the user's security question answer.
     *
     * @param security the user's security question answer
     */
    public void setSecurity(String security) {
        this.security = security;
    }
    /**
     * Gets the user's total balance.
     *
     * @return the user's total balance
     */
    public String getTotal_balance() {
        return total_balance;
    }
    /**
     * Sets the user's total balance.
     *
     * @param total_balance the user's total balance
     */
    public void setTotal_balance(String total_balance) {
        this.total_balance = total_balance;
    }

    /**
     * Gets the user's total income.
     *
     * @return the user's total income
     */
    public String getTotal_income() {
        return total_income;
    }
    /**
     * Sets the user's total income.
     *
     * @param total_income the user's total income
     */
    public void setTotal_income(String total_income) {
        this.total_income = total_income;
    }
    /**
     * Gets the user's total expenses.
     *
     * @return the user's total expenses
     */
    public String getTotal_expense() {
        return total_expense;
    }

    /**
     * Sets the user's total expenses.
     *
     * @param total_expense the user's total expenses
     */
    public void setTotal_expense(String total_expense) {
        this.total_expense = total_expense;
    }
    /**
     * Gets the user's latitude for location-based services.
     *
     * @return the user's latitude
     */

    public String getLatitute() {
        return latitute;
    }

    public void setLatitute(String latitute) {
        this.latitute = latitute;
    }
    /**
     * Gets the user's longitude for location-based services.
     *
     * @return the user's longitude
     */
    public String getLongitute() {
        return longitute;
    }
    /**
     * Sets the user's longitude for location-based services.
     *
     * @param longitute the user's longitude
     */
    public void setLongitute(String longitute) {
        this.longitute = longitute;
    }
}
