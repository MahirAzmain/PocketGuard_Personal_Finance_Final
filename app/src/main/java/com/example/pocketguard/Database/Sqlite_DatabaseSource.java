package com.example.pocketguard.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.pocketguard.Key_SharedPreference;
import com.example.pocketguard.Key_Static;
import com.example.pocketguard.Key_Static_Methods;
import com.example.pocketguard.MyUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
/**
 * Sqlite_DatabaseSource is a data access object (DAO) class that provides methods for interacting with the SQLite database.
 *
 * <p>This class follows the Data Access Object (DAO) design pattern, encapsulating all the code needed to perform CRUD operations
 * on the database. It also follows the Singleton design pattern to ensure only one instance of the database source is used
 * across the application.</p>
 */
public class Sqlite_DatabaseSource {

    Sqlite_DatabaseHelper sqliteDatabaseHelper;
    SQLiteDatabase sqLiteDatabase ;
    Sqlite_Model sqliteModel;
    Context context;
    /**
     * Constructor to initialize the database helper.
     *
     * @param context the context to use for locating paths to the database
     */
    public Sqlite_DatabaseSource(Context context) {
        this.context = context;
        sqliteDatabaseHelper = new Sqlite_DatabaseHelper(context);

    }

    /**
     * Opens a writable database.
     */
    public void open()
    {
    sqLiteDatabase = sqliteDatabaseHelper.getWritableDatabase();
    }
    /**
     * Closes the database.
     */
    public void close()
    {
        sqliteDatabaseHelper.close();
    }
    /**
     * Deletes all records from the database table.
     */
    public void delete_all_records(){
        this.open();
        sqLiteDatabase.execSQL("delete from "+ Sqlite_DatabaseHelper.ACCOUNT_TABLE);
        this.close();
    }
    /**
     * Adds a new record to the database.
     *
     * @param tempModel the model containing data to be inserted
     * @return true if the record was inserted successfully, false otherwise
     */
    public boolean add_Record(Sqlite_Model tempModel)
    {
        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Sqlite_DatabaseHelper.COL_STATUS,tempModel.status);
        contentValues.put(Sqlite_DatabaseHelper.COL_CATEGORY,tempModel.category);
        contentValues.put(Sqlite_DatabaseHelper.COL_DATE, MyUtils.sdfUser.format(tempModel.date));
        contentValues.put(Sqlite_DatabaseHelper.COL_AMOUNT,tempModel.amount);
        contentValues.put(Sqlite_DatabaseHelper.COL_ADDITIONAL_NOTE,tempModel.additional_note);
        contentValues.put(Sqlite_DatabaseHelper.COL_METHOD,tempModel.method);

        Key_Static_Methods.data_saved_to_SharedPreference(context, tempModel.status,tempModel.amount); // Saved

        Long insertedRow = sqLiteDatabase.insert(Sqlite_DatabaseHelper.ACCOUNT_TABLE, null,contentValues);
        this.close();
        if(insertedRow>0)
        {
            return true;
        }
        else {
            return false;
        }

    }

    /**
     * Updates an existing record in the database.
     *
     * @param tempModel the model containing data to be updated
     * @return true if the record was updated successfully, false otherwise
     */

 
    @SuppressLint("Range") 
    public boolean update_Record (Sqlite_Model tempModel)
    {
        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Sqlite_DatabaseHelper.COL_STATUS,tempModel.status);
        contentValues.put(Sqlite_DatabaseHelper.COL_CATEGORY,tempModel.category);
        contentValues.put(Sqlite_DatabaseHelper.COL_DATE, MyUtils.sdfUser.format(tempModel.date));
        contentValues.put(Sqlite_DatabaseHelper.COL_AMOUNT,tempModel.amount);
        contentValues.put(Sqlite_DatabaseHelper.COL_ADDITIONAL_NOTE,tempModel.additional_note);
        contentValues.put(Sqlite_DatabaseHelper.COL_METHOD,tempModel.method);

        int column_amount_previous;
        // Query the row before deleting it
        Cursor cursor = sqLiteDatabase.query(
                Sqlite_DatabaseHelper.ACCOUNT_TABLE,
                null, // Replace with the columns you want to retrieve or use null to retrieve all columns
                Sqlite_DatabaseHelper.COL_ID + " = ?",
                new String[]{String.valueOf(tempModel.getId())},
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            // Retrieve the data from the cursor
            column_amount_previous = cursor.getInt(cursor.getColumnIndex(Sqlite_DatabaseHelper.COL_AMOUNT));
            Log.d("Value Checking", "Previous Value = "+column_amount_previous+" New Value = "+tempModel.amount);

            Key_Static_Methods.data_saved_to_SP_for_update_list(context,tempModel.status,column_amount_previous,tempModel.amount);

            SharedPreferences spp = context.getSharedPreferences(Key_SharedPreference.PREFS_KEY,Context.MODE_PRIVATE);

            int percentage = (int) (((Float.valueOf(spp.getInt("PROGRESS_"+tempModel.getCategory().toUpperCase(),0) - column_amount_previous + tempModel.amount)) / (spp.getInt("LIMIT_"+tempModel.getCategory().toUpperCase(),0)))*100.0);
            if(percentage >50 && percentage <100){
                Key_Static_Methods.creating_custom_notification(context,"Your "+tempModel.getCategory()+" "+percentage+"%"+" Filled","Please Increase Limit For Further Insertion in this Category");
            }

            //For Goal update
           int final_amount = (spp.getInt("PROGRESS_"+tempModel.getCategory().toUpperCase(),0) - column_amount_previous) + tempModel.amount ;
            spp.edit().putInt("PROGRESS_"+tempModel.getCategory().toUpperCase() , final_amount).apply();


        }
        cursor.close();

        int updatedRow = sqLiteDatabase.update(Sqlite_DatabaseHelper.ACCOUNT_TABLE,contentValues, Sqlite_DatabaseHelper.COL_ID+" =?",new String[]{String.valueOf(tempModel.getId())});
        this.close();

 
        if(updatedRow>0){
            return true;
        }else { 
            return false;
        }

    }
    /**
     * Deletes a record from the database.
     *
     * @param model the model containing data to be deleted
     * @return true if the record was deleted successfully, false otherwise
     */


    public boolean delete_Record(Sqlite_Model model)
    {
        this.open();
        int deletedRow = sqLiteDatabase.delete(Sqlite_DatabaseHelper.ACCOUNT_TABLE, Sqlite_DatabaseHelper.COL_ID+" =?", new String[]{String.valueOf(model.getId())});

        Key_Static_Methods.data_saved_to_SharedPreference(context, model.status,model.amount); // Substraceted
        this.close();

        if (deletedRow>0)
        {
            return true;
        }else {
            return false;
        }

    }


    /**
     * Retrieves all records from the database.
     *
     * @return a list of all records
     * @throws ParseException if there is an error parsing the date
     */
    @SuppressLint("Range")
    public  ArrayList<Sqlite_Model> getAllItems() throws ParseException {

        this.open();
        ArrayList<Sqlite_Model> arrayList = new ArrayList<>();

                     //Cursor cursor = sqLiteDatabase.rawQuery("Select * from Userdetails", null);
             Cursor cursor = sqLiteDatabase.query(Sqlite_DatabaseHelper.ACCOUNT_TABLE,null,null,null,null,null,null,null) ;

             if(cursor.moveToFirst())
             {
                 do {
                    int id = cursor.getInt(cursor.getColumnIndex(sqliteDatabaseHelper.COL_ID));
                    String status = cursor.getString(cursor.getColumnIndex(sqliteDatabaseHelper.COL_STATUS));
                    String category = cursor.getString(cursor.getColumnIndex(sqliteDatabaseHelper.COL_CATEGORY));
                    Date date = MyUtils.sdfUser.parse(cursor.getString(cursor.getColumnIndex(sqliteDatabaseHelper.COL_DATE)));
                    int amount = cursor.getInt(cursor.getColumnIndex(sqliteDatabaseHelper.COL_AMOUNT));
                    String additional_note = cursor.getString(cursor.getColumnIndex(sqliteDatabaseHelper.COL_ADDITIONAL_NOTE));
                    String method = cursor.getString(cursor.getColumnIndex(sqliteDatabaseHelper.COL_METHOD));

                    Sqlite_Model sqliteModel = new Sqlite_Model(id,amount,status,category,method,date,additional_note);
                    arrayList.add(sqliteModel);

                 } while (cursor.moveToNext()) ;
             }
                this.close();
             cursor.close();

        return arrayList;
    }
    /**
     * Retrieves all income transactions from the database.
     *
     * @return a list of income transactions
     * @throws ParseException if there is an error parsing the date
     */
    @SuppressLint("Range")
    public  ArrayList<Sqlite_Model> get_income_transaction() throws ParseException {

        this.open();
        ArrayList<Sqlite_Model> arrayList = new ArrayList<>();

                     //Cursor cursor = sqLiteDatabase.rawQuery("Select * from Userdetails", null);
             Cursor cursor = sqLiteDatabase.query(Sqlite_DatabaseHelper.ACCOUNT_TABLE,
                     null,
                     Sqlite_DatabaseHelper.COL_STATUS + " = ?",
                     new String[]{Key_Static.INCOME},null,null,null,null) ;

             if(cursor.moveToFirst())
             {
                 do {
                    int id = cursor.getInt(cursor.getColumnIndex(sqliteDatabaseHelper.COL_ID));
                    String status = cursor.getString(cursor.getColumnIndex(sqliteDatabaseHelper.COL_STATUS));
                    String category = cursor.getString(cursor.getColumnIndex(sqliteDatabaseHelper.COL_CATEGORY));
                    Date date = MyUtils.sdfUser.parse(cursor.getString(cursor.getColumnIndex(sqliteDatabaseHelper.COL_DATE)));
                    int amount = cursor.getInt(cursor.getColumnIndex(sqliteDatabaseHelper.COL_AMOUNT));
                    String additional_note = cursor.getString(cursor.getColumnIndex(sqliteDatabaseHelper.COL_ADDITIONAL_NOTE));
                    String method = cursor.getString(cursor.getColumnIndex(sqliteDatabaseHelper.COL_METHOD));

                    Sqlite_Model sqliteModel = new Sqlite_Model(id,amount,status,category,method,date,additional_note);
                    arrayList.add(sqliteModel);

                 } while (cursor.moveToNext()) ;
             }
                this.close();
             cursor.close();

        return arrayList;
    }

    @SuppressLint("Range")
    public  ArrayList<Sqlite_Model> get_expense_transaction() throws ParseException {

        this.open();
        ArrayList<Sqlite_Model> arrayList = new ArrayList<>();

        //Cursor cursor = sqLiteDatabase.rawQuery("Select * from Userdetails", null);
        Cursor cursor = sqLiteDatabase.query(Sqlite_DatabaseHelper.ACCOUNT_TABLE,
                null,
                Sqlite_DatabaseHelper.COL_STATUS + " = ?",
                new String[]{Key_Static.EXPENSE},null,null,null,null) ;

             if(cursor.moveToFirst())
             {
                 do {
                    int id = cursor.getInt(cursor.getColumnIndex(sqliteDatabaseHelper.COL_ID));
                    String status = cursor.getString(cursor.getColumnIndex(sqliteDatabaseHelper.COL_STATUS));
                    String category = cursor.getString(cursor.getColumnIndex(sqliteDatabaseHelper.COL_CATEGORY));
                    Date date = MyUtils.sdfUser.parse(cursor.getString(cursor.getColumnIndex(sqliteDatabaseHelper.COL_DATE)));
                    int amount = cursor.getInt(cursor.getColumnIndex(sqliteDatabaseHelper.COL_AMOUNT));
                    String additional_note = cursor.getString(cursor.getColumnIndex(sqliteDatabaseHelper.COL_ADDITIONAL_NOTE));
                    String method = cursor.getString(cursor.getColumnIndex(sqliteDatabaseHelper.COL_METHOD));

                    Sqlite_Model sqliteModel = new Sqlite_Model(id,amount,status,category,method,date,additional_note);
                    arrayList.add(sqliteModel);

                 } while (cursor.moveToNext()) ;
             }
                this.close();
             cursor.close();

        return arrayList;
    }

}
