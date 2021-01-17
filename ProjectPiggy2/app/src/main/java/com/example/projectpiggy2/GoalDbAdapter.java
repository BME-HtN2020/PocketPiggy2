package com.example.projectpiggy2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GoalDbAdapter extends DbAdapter {
    public static final String UID = "id";
    public static final String NAME = "name";
    public static final String AMOUNT_SAVED = "amount_saved";
    public static final String TOTAL_AMOUNT = "total_amount";
    public static final String IS_REACHED = "is_reached";

    private static final String TABLE_NAME="Goal";

    public GoalDbAdapter(Context context) {
        super(context);
    }

    public long insertGoal(String name, String totalAmount) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(AMOUNT_SAVED, "$0.00");
        contentValues.put(TOTAL_AMOUNT, totalAmount);
        contentValues.put(IS_REACHED, "false");
        long id = db.insert(TABLE_NAME, null , contentValues);
        return id;
    }

    public Goal getGoalData(String id) {
        String[] columns = {UID, NAME, AMOUNT_SAVED, TOTAL_AMOUNT,IS_REACHED};
        Cursor cursor =db.query(TABLE_NAME,columns,UID + "=" + id,
                null,null,null,null);
        cursor.moveToNext();
        String name =cursor.getString(cursor.getColumnIndex(NAME));
        String amountSaved = cursor.getString(cursor.getColumnIndex(AMOUNT_SAVED));
        String totalAmount = cursor.getString(cursor.getColumnIndex(TOTAL_AMOUNT));
        String isReached = cursor.getString(cursor.getColumnIndex(IS_REACHED));

        return new Goal(name, PriceFormatter.unformat(amountSaved),
                PriceFormatter.unformat(totalAmount), Boolean.parseBoolean(isReached));
    }

    public int deleteGoal(String id) {
        String[] whereArgs ={id};

        int count = db.delete(TABLE_NAME ,UID+" = ?",whereArgs);
        return  count;
    }

    public int updateGoal(String id, String amountSaved, String totalAmount, String isReached) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(AMOUNT_SAVED,amountSaved);
        contentValues.put(TOTAL_AMOUNT,totalAmount);
        contentValues.put(IS_REACHED, isReached);
        String[] whereArgs= {id};

        int count = db.update(TABLE_NAME, contentValues, UID+" = ?", whereArgs );
        return count;
    }

    public int updateGoalBalance(String id, String amountSaved, String isReached) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AMOUNT_SAVED,amountSaved);
        contentValues.put(IS_REACHED, isReached);
        String[] whereArgs= {id};
        int count = db.update(TABLE_NAME, contentValues, UID+" = ?", whereArgs );
        return count;
    }
}
