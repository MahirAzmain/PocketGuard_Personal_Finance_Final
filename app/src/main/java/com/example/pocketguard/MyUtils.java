package com.example.pocketguard;

import java.text.SimpleDateFormat;
import java.util.Locale;
/**
 * The {@code MyUtils} class provides utility methods and constants for the PocketGuard application.
 *
 * <p>This class employs the Utility design pattern by offering a collection of static methods and constants
 * that can be accessed without creating an instance of the class.</p>
 *
 * <p>The class includes:</p>
 * <ul>
 *   <li>Constants for various payment methods and categories for income and expenses.</li>
 *   <li>Date formatters for user-friendly date representations.</li>
 * </ul>
 */
public class MyUtils {
    /**
     * An array of supported payment methods.
     */
    public static final String[] Methods_TYPES = {"Wire Transfer", "Credit Card", "EMI", "Case Payments"};
    //public static final String[] Category_TYPES_Income = {"Salary", "Savings", "Income", "Others"};

    /**
     * An array of supported income categories.
     */

    public static final String[] Category_TYPES_Income = {"Salary", "Savings", "Income"};
    //public static final String[] Category_TYPES_Expense = {"Investment", "Travel", "Food & Drinks", "Entertainment", "Shopping", "Housing", "Vehicles", "Transportation", "Communication", "Others"};
    /**
     * An array of supported expense categories.
     */
    public static final String[] Category_TYPES_Expense = {"Investment", "Travel", "Food & Drinks", "Entertainment", "Shopping", "Housing", "Vehicles", "Transportation", "Communication"};
    /**
     * Date formatter for user-facing dates (format: yyyy-MM-dd).
     */
    public static final SimpleDateFormat sdfUser = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    /**
     * Date formatter for single item display dates (format: dd MMM, yyyy).
     * <p>Note:
     * <ul>
     *   <li>Two 'M' characters for numeric month (09).</li>
     *   <li>Three 'M' characters for abbreviated month (Sep).</li>
     *   <li>Four 'M' characters for full month name (September).</li>
     * </ul>
     * </p>
     */
    public static final SimpleDateFormat simpleDateFormat_for_single_item = new SimpleDateFormat("dd MMM, yyyy", Locale.getDefault()); // ekhane 2 ta M nane 09, 3 ta M name Sep, 4 ta M mane September

}
