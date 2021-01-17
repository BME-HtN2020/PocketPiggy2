package com.example.projectpiggy2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AccountDbAdapter extends DbAdapter {
    public static final String UID = "id";
    public static final String BALANCE = "balance";

    private static final String TABLE_NAME="Account";

    public AccountDbAdapter(Context context) {
        super(context);
    }

    public long insertAccountData() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BALANCE, "$0.00");
        long id = db.insert(TABLE_NAME, null , contentValues);
        return id;
    }

    public Account getAccountData(String id) {
        String[] columns = {UID,BALANCE};
        Cursor cursor =db.query(TABLE_NAME,columns,UID + "=" + id,
                null,null,null,null);
        cursor.moveToNext();
        String balance = cursor.getString(cursor.getColumnIndex(BALANCE));
        Account account = new Account(PriceFormatter.unformat(balance));
        return account;
    }

    public int deleteAccount(String id) {
        String[] whereArgs ={id};

        int count = db.delete(TABLE_NAME,UID+" = ?", whereArgs);
        return count;
    }

    public int updateAccountBalance(String id, String newBalance) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BALANCE,newBalance);
        String[] whereArgs= {id};
        int count = db.update(TABLE_NAME,contentValues, UID + " = ?", whereArgs);
        return count;
    }
}
