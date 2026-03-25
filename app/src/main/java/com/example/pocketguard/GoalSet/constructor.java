package com.example.pocketguard.GoalSet;
/**
 * The constructor class represents a model for goal setting related to expenses.
 * It includes the category of expense, the current progress towards the expense limit, and the expense limit itself.
 *
 * <p>This class follows the Transfer Object (TO) design pattern, also known as Data Transfer Object (DTO) pattern.
 * It is used to encapsulate data and transfer it between layers of the application. This pattern is particularly useful
 * for reducing the number of method calls by bundling multiple attributes into a single object.</p>
 */
public class constructor {

    String category;
    int progress_expense;
    int limit_expense;
    /**
     * Constructs a new constructor with specified category, progress expense, and limit expense.
     *
     * @param category the category of the expense
     * @param progress_expense the current progress towards the expense limit
     * @param limit_expense the maximum allowed expense for the category
     */

    public constructor(String category, int progress_expense, int limit_expense) {
        this.category = category;
        this.progress_expense = progress_expense;
        this.limit_expense = limit_expense;
    }
    /**
     * Gets the category of the expense.
     *
     * @return the category of the expense
     */

    public String getCategory() {
        return category;
    }
    /**
     * Sets the category of the expense.
     *
     * @param category the new category of the expense
     */
    public void setCategory(String category) {
        this.category = category;
    }
    /**
     * Gets the current progress towards the expense limit.
     *
     * @return the current progress towards the expense limit
     */
    public int getProgress_expense() {
        return progress_expense;
    }
    /**
     * Sets the current progress towards the expense limit.
     *
     * @param progress_expense the new progress towards the expense limit
     */
    public void setProgress_expense(int progress_expense) {
        this.progress_expense = progress_expense;
    }
    /**
     * Gets the maximum allowed expense for the category.
     *
     * @return the maximum allowed expense for the category
     */
    public int getLimit_expense() {
        return limit_expense;
    }
    /**
     * Sets the maximum allowed expense for the category.
     *
     * @param limit_expense the new maximum allowed expense for the category
     */
    public void setLimit_expense(int limit_expense) {
        this.limit_expense = limit_expense;
    }
}
