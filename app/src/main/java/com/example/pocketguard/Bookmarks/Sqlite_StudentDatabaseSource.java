package com.example.pocketguard.Bookmarks;

import static com.example.pocketguard.Bookmarks.Sqlite_StudentDatabaseHelper.STUDENT_TABLE;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
/**
 * Data source class to handle CRUD operations for student data.
 * This class uses the DAO (Data Access Object) design pattern.
 */
public class Sqlite_StudentDatabaseSource {

    Sqlite_StudentDatabaseHelper studentDatabaseHelper ;
    SQLiteDatabase sqLiteDatabase ;
    Sqlite_StudentModel studentModel ;
    /**
     * Constructor for Sqlite_StudentDatabaseSource.
     *
     * @param context The current context.
     */
    public Sqlite_StudentDatabaseSource(Context context) {

        studentDatabaseHelper = new Sqlite_StudentDatabaseHelper(context);

    }

    /**
     * Opens the database for writing.
     */

    public void open()
    {
    sqLiteDatabase =studentDatabaseHelper.getWritableDatabase();
    }
    /**
     * Closes the database.
     */
    public void close()
    {
        studentDatabaseHelper.close();
    }
    /**
     * Adds a new student to the database.
     *
     * @param studentModel The student model containing the data to be added.
     * @return true if the student was added successfully, false otherwise.
     */
    public boolean addStudent(Sqlite_StudentModel studentModel)
    {
        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Sqlite_StudentDatabaseHelper.COL_NAME,studentModel.getName());
        contentValues.put(Sqlite_StudentDatabaseHelper.COL_AGE,studentModel.getAge());
        contentValues.put(Sqlite_StudentDatabaseHelper.COL_ADDRESS,studentModel.getAddress());
        Long insertedRow = sqLiteDatabase.insert(STUDENT_TABLE, null,contentValues);
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
     * Updates an existing student in the database.
     *
     * @param studentModel The student model containing the updated data.
     * @return true if the student was updated successfully, false otherwise.
     */
    @SuppressLint("SuspiciousIndentation")
    public boolean updateStudent (Sqlite_StudentModel studentModel)
    {
        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Sqlite_StudentDatabaseHelper.COL_NAME,studentModel.getName());
        contentValues.put(Sqlite_StudentDatabaseHelper.COL_AGE,studentModel.getAge());
        contentValues.put(Sqlite_StudentDatabaseHelper.COL_ADDRESS,studentModel.getAddress());

        int updatedRow = sqLiteDatabase.update(STUDENT_TABLE,contentValues, Sqlite_StudentDatabaseHelper.COL_ID+" =?",new String[]{String.valueOf(studentModel.getId())});
        this.close();

 
        if(updatedRow>0){
            return true;
        }else{
            return false;
        } 
    }


    /**
     * Deletes a student from the database.
     *
     * @param model The student model to be deleted.
     * @return true if the student was deleted successfully, false otherwise.
     */

    public boolean deleteStudent(Sqlite_StudentModel model)
    {
        this.open();
        int deletedRow = sqLiteDatabase.delete(STUDENT_TABLE, Sqlite_StudentDatabaseHelper.COL_ID+" =?", new String[]{String.valueOf(model.getId())});
        this.close();

        if (deletedRow>0)
        {
            return true;
        }else {
            return false;
        }

    }
    /**
     * Deletes all students from the database.
     *
     * @return true if all students were deleted successfully, false otherwise.
     */


    public boolean delete_All_Student()
    {
        this.open();
        int deletedAll = sqLiteDatabase.delete(STUDENT_TABLE, null, null);
        this.close();

        if (deletedAll>0)
        {
            return true;
        }else {
            return false;
        }

    }


    /**
     * Retrieves all students from the database.
     *
     * @return An ArrayList of Sqlite_StudentModel containing all students.
     */
    @SuppressLint("Range")
    public  ArrayList<Sqlite_StudentModel> getAllStudent()
    {

        this.open();
        ArrayList<Sqlite_StudentModel> arrayList = new ArrayList<>();

                     //Cursor cursor = sqLiteDatabase.rawQuery("Select * from Userdetails", null);
             Cursor cursor = sqLiteDatabase.query(STUDENT_TABLE,null,null,null,null,null,null,null) ;

             if(cursor.moveToFirst())
             {
                 do {
                    String name = cursor.getString(cursor.getColumnIndex(studentDatabaseHelper.COL_NAME));
                    int age = cursor.getInt(cursor.getColumnIndex(studentDatabaseHelper.COL_AGE));
                    String address = cursor.getString(cursor.getColumnIndex(studentDatabaseHelper.COL_ADDRESS));
                    int id = cursor.getInt(cursor.getColumnIndex(studentDatabaseHelper.COL_ID));

                     Sqlite_StudentModel studentModel = new Sqlite_StudentModel(id,name,age,address)  ;
                     arrayList.add(studentModel);

                 } while (cursor.moveToNext()) ;
             }
                this.close();
             cursor.close();

        return arrayList;
    }

}
