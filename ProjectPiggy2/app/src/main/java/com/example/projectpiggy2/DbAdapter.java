package com.example.projectpiggy2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DbAdapter {
    public static final String DATABASE_NAME = "pocketPiggyDatabase";
    public static final int DATABASE_VERSION = 3;
    public static final String ACCOUNT_TABLE_NAME = "Account";
    public static final String CHORE_TABLE_NAME = "Chore";
    public static final String GOAL_TABLE_NAME = "Goal";
    public static final String USER_TABLE_NAME = "User";

    public static DbHelper dbHelper;
    public static SQLiteDatabase db;

    private static final String CREATE_ACCOUNT_TABLE = "CREATE TABLE "+ACCOUNT_TABLE_NAME+
            " ("+AccountDbAdapter.UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+AccountDbAdapter.BALANCE+" VARCHAR(255));";
    private static final String CREATE_CHORE_TABLE = "CREATE TABLE "+CHORE_TABLE_NAME+
            " ("+ ChoreDbAdapter.UID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ChoreDbAdapter.TITLE+
            " VARCHAR(255), " + ChoreDbAdapter.DETAILS + " VARCHAR(255), " +ChoreDbAdapter. AMOUNT +
            " VARCHAR(255), " + ChoreDbAdapter.IS_ACCOMPLISHED + " VARCHAR(255));";
    private static final String CREATE_GOAL_TABLE = "CREATE TABLE " + GOAL_TABLE_NAME +
            " (" + GoalDbAdapter.UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + GoalDbAdapter.NAME +
            " VARCHAR(255), " + GoalDbAdapter.AMOUNT_SAVED + " VARCHAR(255), " + GoalDbAdapter.TOTAL_AMOUNT +
            " VARCHAR(255), " + GoalDbAdapter.IS_REACHED + " VARCHAR(255));";
    private static final String CREATE_USER_TABLE = "CREATE TABLE "+ USER_TABLE_NAME +
            " (" + UserDbAdapter.UID +" INTEGER PRIMARY KEY AUTOINCREMENT, " + UserDbAdapter.PIN +
            " VARCHAR(4), " + UserDbAdapter.NAME + " VARCHAR(255), " + UserDbAdapter.ACCOUNT +
            " VARCHAR(255), " + UserDbAdapter.CHORES + " VARCHAR(255), " + UserDbAdapter.GOAL + " VARCHAR(255));";

    public Context context;

    static class DbHelper extends SQLiteOpenHelper {
        public DbHelper(Context ctext) {
            super(ctext, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_ACCOUNT_TABLE);
            db.execSQL(CREATE_CHORE_TABLE);
            db.execSQL(CREATE_GOAL_TABLE);
            db.execSQL(CREATE_USER_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + ACCOUNT_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + CHORE_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + GOAL_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
            onCreate(db);
        }
    }

    public DbAdapter(Context context) {
        dbHelper = new DbHelper(context);
        this.context = context;
        db = dbHelper.getWritableDatabase();
    }
}
