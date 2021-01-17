package com.example.projectpiggy2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserDbAdapter extends DbAdapter {
    public static final String UID = "id";
    public static final String PIN = "pin";
    public static final String NAME = "name";
    public static final String ACCOUNT = "account";
    public static final String CHORES = "chores";
    public static final String GOAL = "goal";

    private static final String TABLE_NAME = "User";

    AccountDbAdapter accountDbAdapter;
    ChoreDbAdapter choreDbAdapter;
    GoalDbAdapter goalDbAdapter;

    public UserDbAdapter(Context context) {
        super(context);
        this.accountDbAdapter = new AccountDbAdapter(context);
        this.choreDbAdapter = new ChoreDbAdapter(context);
        this.goalDbAdapter = new GoalDbAdapter(context);
    }

    public long insertUser(String pin, String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PIN, pin);
        contentValues.put(NAME, name);

        long accountId = accountDbAdapter.insertAccountData();

        contentValues.put(ACCOUNT, accountId);
        contentValues.put(CHORES, "");
        contentValues.put(GOAL, "");

        long id = db.insert(TABLE_NAME, null , contentValues);
        return id;
    }

    public User getUserData(String id) {
        String[] columns = {UID,PIN,NAME,
                ACCOUNT,CHORES,GOAL};
        Cursor cursor =db.query(TABLE_NAME,columns,UID + "=" + id,
                null,null,null,null);
        cursor.moveToNext();

        String pin = cursor.getString(cursor.getColumnIndex(PIN));
        String name =cursor.getString(cursor.getColumnIndex(NAME));
        String account = cursor.getString(cursor.getColumnIndex(ACCOUNT));
        String chores = cursor.getString(cursor.getColumnIndex(CHORES));
        String goal = cursor.getString(cursor.getColumnIndex(GOAL));

        Account userAccount = accountDbAdapter.getAccountData(account);
        List<Chore> userChores = new ArrayList<Chore>();
        String[] choreIds = DataWrapper.unwrap(chores);
        for (int i = 0; i < choreIds.length; i++) {
            userChores.add(choreDbAdapter.getChoreData(choreIds[i]));
        }
        Goal userGoal = goalDbAdapter.getGoalData(goal);

        return new User(pin, name, userAccount, userChores, userGoal, id);
    }

    public String getUserAccountId(String id) {
        String[] columns = {ACCOUNT};

        Cursor cursor = db.query(TABLE_NAME, columns, UID+"="+id,
                null, null, null, null);
        cursor.moveToNext();
        String account = cursor.getString(cursor.getColumnIndex(ACCOUNT));

        return account;
    }

    public String getUserChoresId(String id) {
        String[] columns = {CHORES};

        Cursor cursor = db.query(TABLE_NAME, columns, UID+"="+id,
                null, null, null, null);
        cursor.moveToNext();
        String chores = cursor.getString(cursor.getColumnIndex(CHORES));

        return chores;
    }

    public String getUserGoalId(String id) {
        String[] columns = {GOAL};

        Cursor cursor = db.query(TABLE_NAME, columns, UID+"="+id,
                null, null, null, null);
        cursor.moveToNext();
        String goal = cursor.getString(cursor.getColumnIndex(GOAL));

        return goal;
    }

    public int deleteUser(String id) {
        String[] whereArgs ={id};

        String accountId = getUserAccountId(id);
        String choresId = getUserChoresId(id);
        String goalId = getUserGoalId(id);

        if (!accountId.isEmpty()) {
            int accountDeleteResult = accountDbAdapter.deleteAccount(accountId);
            if (accountDeleteResult == 0) {
                // some kind of error occurred when deleting the account
                return 0;
            }
        }
        if (!choresId.isEmpty()) {
            String[] choresIds = DataWrapper.unwrap(choresId);
            for (int i = 0; i < choresIds.length; i++) {
                int choreDeleteResult = choreDbAdapter.deleteChore(choresIds[i]);
                if (choreDeleteResult == 0) {
                    // some kind of error occurred when deleting the chore
                    // implement logic to add the deleted account back in
                    return 0;
                }
            }
        }
        if (!goalId.isEmpty()) {
            int goalDeleteResult = goalDbAdapter.deleteGoal(goalId);
            if (goalDeleteResult == 0) {
                // some kind of error occurred when deleting the goal
                return 0;
            }
        }

        int count = db.delete(TABLE_NAME ,UID+" = ?",whereArgs);
        return  count;
    }

    public int updateUser(String id, String account, String chores, String goal) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACCOUNT,account);
        contentValues.put(CHORES,chores);
        contentValues.put(GOAL,goal);
        String[] whereArgs= {id};
        int count = db.update(TABLE_NAME,contentValues, UID+" = ?",whereArgs );
        return count;
    }

    // called when a new chore is added or when a chore is deleted
    public int updateUserChores(String id, String chores) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CHORES,chores);
        String[] whereArgs= {id};
        int count = db.update(TABLE_NAME,contentValues, UID+" = ?",whereArgs );
        return count;
    }

    // called when a new goal is added or when a goal is deleted
    public int updateUserGoal(String id, String goal) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(GOAL,goal);
        String[] whereArgs= {id};
        int count = db.update(TABLE_NAME,contentValues, UID+" = ?",whereArgs );
        return count;
    }
}
