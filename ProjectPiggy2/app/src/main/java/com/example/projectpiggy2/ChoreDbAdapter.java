package com.example.projectpiggy2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ChoreDbAdapter extends DbAdapter {
    public static final String UID = "id";
    public static final String TITLE = "title";
    public static final String DETAILS = "details";
    public static final String AMOUNT = "amount";
    public static final String IS_ACCOMPLISHED = "is_accomplished";

    private static final String TABLE_NAME = "Chore";

    public ChoreDbAdapter(Context context) {
        super(context);
    }

    public long insertChore(String title, String details, String amount) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, title);
        contentValues.put(DETAILS, details);
        contentValues.put(AMOUNT, amount);
        contentValues.put(IS_ACCOMPLISHED, "false");
        long id = db.insert(TABLE_NAME, null , contentValues);
        return id;
    }

    public Chore getChoreData(String id) {
        String[] columns = {UID,TITLE,DETAILS,AMOUNT,IS_ACCOMPLISHED};
        Cursor cursor =db.query(TABLE_NAME,columns,UID + "=" + id,
                null,null,null,null);
        cursor.moveToNext();
        String title =cursor.getString(cursor.getColumnIndex(TITLE));
        String details = cursor.getString(cursor.getColumnIndex(DETAILS));
        String amount = cursor.getString(cursor.getColumnIndex(AMOUNT));
        String isAccomplished = cursor.getString(cursor.getColumnIndex(IS_ACCOMPLISHED));

        return new Chore(title, details, PriceFormatter.unformat(amount), Boolean.parseBoolean(isAccomplished));
    }

    public String getChoreIdByTitle(String title) {
        String[] columns = {UID};
        Cursor cursor =db.query(TABLE_NAME,columns,TITLE + "=" + title,
                null,null,null,null);
        cursor.moveToNext();
        String id =cursor.getString(cursor.getColumnIndex(UID));
        return id;
    }

    public int deleteChore(String id) {
        String[] whereArgs ={id};

        int count =db.delete(TABLE_NAME ,UID+" = ?",whereArgs);
        return  count;
    }

    public int updateChore(String id, String isAccomplished) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(IS_ACCOMPLISHED,isAccomplished);
        String[] whereArgs= {id};
        int count =db.update(TABLE_NAME,contentValues, UID+" = ?",whereArgs );
        return count;
    }
}
