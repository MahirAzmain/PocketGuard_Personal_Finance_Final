package com.example.pocketguard.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Sqlite_DatabaseHelper is a helper class to manage database creation and version management.
 *
 * <p>This class uses the Singleton design pattern to ensure that only one instance of the database helper
 * is created and used throughout the application. Additionally, it follows the Template Method design pattern
 * by defining the steps of the database creation and upgrade process in onCreate and onUpgrade methods, respectively.</p>
 */
public class Sqlite_DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "expense.db";
    private static final int DATABASE_VERSION = 1;

    public static final String ACCOUNT_TABLE = "income_expense_table";

    public static final String COL_ID = "id";
    public static final String COL_STATUS = "status_income_or_expense";
    public static final String COL_CATEGORY = "select_category";
    public static final String COL_DATE = "dates";
    public static final String COL_AMOUNT = "amount";
    public static final String COL_ADDITIONAL_NOTE = "additional_note";
    public static final String COL_METHOD = "select_mehtod";


    static final String CREATE_TABLE = "CREATE TABLE " + ACCOUNT_TABLE + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COL_STATUS + " TEXT," + COL_CATEGORY + " INTEGER," + COL_DATE + " TEXT," +
            COL_AMOUNT + " INTEGER," + COL_ADDITIONAL_NOTE + " TEXT," + COL_METHOD + " TEXT)";

    /**
     * Constructs a new instance of Sqlite_DatabaseHelper.
     *
     * @param context the context to use for locating paths to the database
     */
    public Sqlite_DatabaseHelper(@Nullable Context context) {
        super(context, ACCOUNT_TABLE, null, DATABASE_VERSION);
    }
    /**
     * Called when the database is created for the first time. This is where the creation of tables and
     * the initial population of the tables should happen.
     *
     * @param db the database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }
    /**
     * Called when the database needs to be upgraded. This method will drop the existing table and
     * create a new one.
     *
     * @param db the database
     * @param oldVersion the old database version
     * @param newVersion the new database version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS " + ACCOUNT_TABLE);
        this.onCreate(db);
    }
}
