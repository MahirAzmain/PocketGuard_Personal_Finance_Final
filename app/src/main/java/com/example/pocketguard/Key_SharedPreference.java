package com.example.pocketguard;
/**
 * The {@code Key_SharedPreference} class is a utility class that contains all the keys
 * used for accessing shared preferences in the application. This class uses a Singleton
 * design pattern to ensure that the keys are centralized and consistent across the application.
 *
 * <p>This class provides a centralized location for managing preference keys, which helps in
 * maintaining consistency and avoiding hard-coded strings scattered throughout the codebase.</p>
 *
 * <p>Each key represents a specific preference value, categorized into various sections
 * such as account details, application state, financial data, and goal settings.</p>
 *
 * <p>The Singleton pattern is implemented here by making the constructor private and
 * providing public static final fields for accessing the keys directly.</p>
 *
 * <p><strong>Usage Example:</strong></p>
 * <pre>
 * {@code
 * SharedPreferences sharedPreferences = context.getSharedPreferences(Key_SharedPreference.PREFS_KEY, Context.MODE_PRIVATE);
 * String accountName = sharedPreferences.getString(Key_SharedPreference.ACCOUNT_NAME, "defaultName");
 * }
 * </pre>
 *
 * @see android.content.SharedPreferences
 */
public class Key_SharedPreference {
    /**
     * Key for accessing the shared preferences file.
     */
    public static String PREFS_KEY = "com.personalbudget.expensetracker.financeplanner";

    // Account Details
    /**
     * Key for storing the account name.
     */
    public static String ACCOUNT_NAME = "account_name";
    /**
     * Key for indicating if the login process is completed.
     */
    public static String LOGIN_DONE = "login_done";
    /**
     * Key for storing the user's PIN.
     */
    public static String MY_PIN = "pin";

    // Notification
    /**
     * Key for storing the FCM notification link.
     */
    public static String FCM_NOTIFICATION_LINK = "notification_link";
    // Application State
    /**
     * Key for indicating if the app is opened for the first time.
     */
    public static String FIRST_TIME_APP_OPEN_SPLASH_SCREEN= "splash_screen";

    // Financial Data
    /**
     * Key for storing the total balance.
     */
    public static String TOTAL_BALANCE ="balance_total";
    /**
     * Key for storing the total income.
     */
    public static String TOTAL_INCOME ="total_income";
    /**
     * Key for storing the total expense.
     */
    public static String TOTAL_EXPENSE ="total_expense";

    // Goal Settings
    /**
     * Key for storing the salary goal.
     */
    public static String SALARY="Salary";
    /**
     * Key for tracking progress towards the salary goal.
     */
    public static String PROGRESS_SALARY="PROGRESS_SALARY";
    /**
     * Key for setting the salary limit.
     */
    public static String LIMIT_SALARY="LIMIT_SALARY";
    /**
     * Key for storing the savings goal.
     */

    public static String SAVINGS="Savings";
    /**
     * Key for tracking progress towards the savings goal.
     */
    public static String PROGRESS_SAVINGS="PROGRESS_SAVINGS";
    /**
     * Key for setting the savings limit.
     */

    public static String LIMIT_SAVINGS="LIMIT_SAVINGS";
    /**
     * Key for storing the income goal.
     */

    public static String INCOME="Income";
    public static String PROGRESS_INCOME="PROGRESS_INCOME";
    public static String LIMIT_INCOME="LIMIT_INCOME";

    public static String OTHERS_INCOME="Others Income";
    public static String PROGRESS_OTHERS_INCOME="PROGRESS_OTHERS INCOME"; // HERE {PROGRESS_OTHERS INCOME} here Underscore and Space Important
    public static String LIMIT_OTHERS_INCOME="LIMIT_OTHERS INCOME";// HERE {PROGRESS_OTHERS INCOME} here Underscore and Space Important


    public static String INVESTMENT ="Investment";
    public static String PROGRESS_INVESTMENT="PROGRESS_INVESTMENT";
    public static String LIMIT_INVESTMENT="LIMIT_INVESTMENT";

    public static String TRAVEL="Travel";
    public static String PROGRESS_TRAVEL="PROGRESS_TRAVEL";
    public static String LIMIT_TRAVEL="LIMIT_TRAVEL";

    public static String FOOD_DRINKS="Food & Drinks";
    public static String PROGRESS_FOOD_DRINKS="PROGRESS_FOOD & DRINKS";// HERE {PROGRESS_FOOD & DRINKS} here & and Space Important
    public static String LIMIT_FOOD_DRINKS="LIMIT_FOOD & DRINKS";

    public static String ENTERTAINMENT="Entertainment";
    public static String PROGRESS_ENTERTAINMENT="PROGRESS_ENTERTAINMENT";
    public static String LIMIT_ENTERTAINMENT="LIMIT_ENTERTAINMENT";

    public static String SHOPPING="Shopping";
    public static String PROGRESS_SHOPPING="PROGRESS_SHOPPING";
    public static String LIMIT_SHOPPING="LIMIT_SHOPPING";

    public static String HOUSING="Housing";
    public static String PROGRESS_HOUSING="PROGRESS_HOUSING";
    public static String LIMIT_HOUSING="LIMIT_HOUSING";

    public static String VEHICLES="Vehicles";
    public static String PROGRESS_VEHICLES="PROGRESS_VEHICLES";
    public static String LIMIT_VEHICLES="LIMIT_VEHICLES";

    public static String TRANSPORTATION="Transportation";
    public static String PROGRESS_TRANSPORTATION="PROGRESS_TRANSPORTATION";
    public static String LIMIT_TRANSPORTATION="LIMIT_TRANSPORTATION";

    public static String COMMUNICATION="Communication";
    public static String PROGRESS_COMMUNICATION="PROGRESS_COMMUNICATION";
    public static String LIMIT_COMMUNICATION="LIMIT_COMMUNICATION";

    public static String OTHERS_EXPENSE="Others Expense";
    public static String PROGRESS_OTHERS_EXPENSE="PROGRESS_OTHERS EXPENSE";// HERE {PROGRESS_OTHERS EXPENSE} here Underscore and Space Important
    public static String LIMIT_OTHERS_EXPENSE="LIMIT_OTHERS EXPENSE"; // HERE {PROGRESS_OTHERS EXPENSE} here Underscore and Space Important
}