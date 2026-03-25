  package com.example.pocketguard.Bookmarks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
  /**
   * Helper class to manage database creation and version management for student data.
   * This class uses the Singleton design pattern to ensure a single instance of the database helper.
   */
  public class Sqlite_StudentDatabaseHelper extends SQLiteOpenHelper {

      private static final String DATABASE_NAME = "Student.db";
      private static final int DATABASE_VERSION = 1;

      public static final String STUDENT_TABLE = "student_table";

      public static final String COL_ID = "id";
      public static final String COL_NAME = "name";
      public static final String COL_AGE = "age";
      public static final String COL_ADDRESS= "address";


      static final String CREATE_TABLE = "CREATE TABLE " + STUDENT_TABLE + " (" +
              COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
              COL_NAME + " TEXT," + COL_AGE + " INTEGER, " + COL_ADDRESS + " TEXT)";


      /**
       * Constructor for Sqlite_StudentDatabaseHelper.
       *
       * @param context The current context.
       */
      public Sqlite_StudentDatabaseHelper(@Nullable Context context) {
          super(context, STUDENT_TABLE, null, DATABASE_VERSION);
      }

      /**
       * Called when the database is created for the first time.
       *
       * @param db The database.
       */
      @Override
      public void onCreate(SQLiteDatabase db) {
          db.execSQL(CREATE_TABLE);
      }
      /**
       * Called when the database needs to be upgraded.
       *
       * @param db         The database.
       * @param oldVersion The old database version.
       * @param newVersion The new database version.
       */
      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
          db.execSQL( "DROP TABLE IF EXISTS " + STUDENT_TABLE);
          this.onCreate(db);
      }
  }
