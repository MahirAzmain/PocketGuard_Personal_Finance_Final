package com.example.pocketguard.Database;

import java.io.Serializable;
import java.util.Date;
/**
 * Sqlite_Model represents a data model for storing and managing financial records in the SQLite database.
 *
 * <p>This class follows the Transfer Object (TO) design pattern, also known as Data Transfer Object (DTO) pattern.
 * It is used to encapsulate data and transfer it between layers of the application. This pattern is particularly useful
 * for reducing the number of method calls by bundling multiple attributes into a single object.</p>
 */

public class Sqlite_Model implements Serializable {

    int id, amount ;
    String status, category, method;
    Date date;
    String additional_note ;

    /**
     * Constructs a new Sqlite_Model with all attributes, including the id.
     *
     * @param id              the unique identifier of the record
     * @param amount          the amount of money
     * @param status          the status indicating whether it is income or expense
     * @param category        the category of the transaction
     * @param method          the method of transaction
     * @param date            the date of the transaction
     * @param additional_note additional notes regarding the transaction
     */
    public Sqlite_Model(int id, int amount, String status, String category, String method, Date date, String additional_note) {
        this.id = id;
        this.amount = amount;
        this.status = status;
        this.category = category;
        this.method = method;
        this.date = date;
        this.additional_note = additional_note;
    }
    /**
     * Constructs a new Sqlite_Model without an id. This constructor is typically used for creating new records.
     *
     * @param amount          the amount of money
     * @param status          the status indicating whether it is income or expense
     * @param category        the category of the transaction
     * @param method          the method of transaction
     * @param date            the date of the transaction
     * @param additional_note additional notes regarding the transaction
     */
    public Sqlite_Model(int amount, String status, String category, String method, Date date, String additional_note) {
        this.amount = amount;
        this.status = status;
        this.category = category;
        this.method = method;
        this.date = date;
        this.additional_note = additional_note;
    }

    /**
     * Gets the id of the record.
     *
     * @return the id of the record
     */
    public int getId() {
        return id;
    }
    /**
     * Sets the id of the record.
     *
     * @param id the new id of the record
     */

    public void setId(int id) {
        this.id = id;
    }
    /**
     * Gets the amount of the transaction.
     *
     * @return the amount of the transaction
     */
    public int getAmount() {
        return amount;
    }
    /**
     * Sets the amount of the transaction.
     *
     * @param amount the new amount of the transaction
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }
    /**
     * Gets the status of the transaction.
     *
     * @return the status of the transaction
     */
    public String getStatus() {
        return status;
    }
    /**
     * Sets the status of the transaction.
     *
     * @param status the new status of the transaction
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * Gets the category of the transaction.
     *
     * @return the category of the transaction
     */

    public String getCategory() {
        return category;
    }
    /**
     * Sets the category of the transaction.
     *
     * @param category the new category of the transaction
     */
    public void setCategory(String category) {
        this.category = category;
    }
    /**
     * Gets the method of the transaction.
     *
     * @return the method of the transaction
     */
    public String getMethod() {
        return method;
    }
    /**
     * Sets the method of the transaction.
     *
     * @param method the new method of the transaction
     */
    public void setMethod(String method) {
        this.method = method;
    }
    /**
     * Gets the date of the transaction.
     *
     * @return the date of the transaction
     */
    public Date getDate() {
        return date;
    }
    /**
     * Sets the date of the transaction.
     *
     * @param date the new date of the transaction
     */
    public void setDate(Date date) {
        this.date = date;
    }
    /**
     * Gets the additional notes of the transaction.
     *
     * @return the additional notes of the transaction
     */
    public String getAdditional_note() {
        return additional_note;
    }
    /**
     * Sets the additional notes of the transaction.
     *
     * @param additional_note the new additional notes of the transaction
     */
    public void setAdditional_note(String additional_note) {
        this.additional_note = additional_note;
    }
}
