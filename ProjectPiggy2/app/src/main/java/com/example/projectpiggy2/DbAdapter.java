package com.example.projectpiggy2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DbAdapter {
    AccountDbHelper accountDbHelper;
    ChoreDbHelper choreDbHelper;
    GoalDbHelper goalDbHelper;
    UserDbHelper userDbHelper;

    public DbAdapter(Context context)
    {
        choreDbHelper = new ChoreDbHelper(context);
        accountDbHelper = new AccountDbHelper(context);
        goalDbHelper = new GoalDbHelper(context);
        userDbHelper = new UserDbHelper(context);
    }

    public long insertAccountData() {
        SQLiteDatabase db = accountDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(accountDbHelper.BALANCE, "$0.00");
        long id = db.insert(accountDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }

    public Account getAccountData(String id)
    {
        SQLiteDatabase db = accountDbHelper.getWritableDatabase();
        String[] columns = {accountDbHelper.UID,accountDbHelper.BALANCE};
        Cursor cursor =db.query(accountDbHelper.TABLE_NAME,columns,accountDbHelper.UID + "=" + id,
                null,null,null,null);
        String balance = cursor.getString(cursor.getColumnIndex(accountDbHelper.BALANCE));
        Account account = new Account(PriceFormatter.unformat(balance));
        return account;
    }

    public int deleteAccount(String id)
    {
        SQLiteDatabase db = accountDbHelper.getWritableDatabase();
        String[] whereArgs ={id};

        int count =db.delete(accountDbHelper.TABLE_NAME ,accountDbHelper.UID+" = ?",whereArgs);
        return  count;
    }

    public int updateAccountBalance(String id, String newBalance) {
        SQLiteDatabase db = accountDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(accountDbHelper.BALANCE,newBalance);
        String[] whereArgs= {id};
        int count =db.update(accountDbHelper.TABLE_NAME,contentValues, accountDbHelper.UID+" = ?",whereArgs );
        return count;
    }

    public long insertGoal(String name, String totalAmount) {
        SQLiteDatabase db = goalDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(goalDbHelper.NAME, name);
        contentValues.put(goalDbHelper.AMOUNT_SAVED, "$0.00");
        contentValues.put(goalDbHelper.TOTAL_AMOUNT, totalAmount);
        contentValues.put(goalDbHelper.IS_REACHED, "false");
        long id = db.insert(goalDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }

    public Goal getGoalData(String id)
    {
        SQLiteDatabase db = goalDbHelper.getWritableDatabase();
        String[] columns = {goalDbHelper.UID,goalDbHelper.NAME,goalDbHelper.AMOUNT_SAVED,
                goalDbHelper.TOTAL_AMOUNT,goalDbHelper.IS_REACHED};
        Cursor cursor =db.query(goalDbHelper.TABLE_NAME,columns,goalDbHelper.UID + "=" + id,
                null,null,null,null);
        String name =cursor.getString(cursor.getColumnIndex(goalDbHelper.NAME));
        String amountSaved = cursor.getString(cursor.getColumnIndex(goalDbHelper.AMOUNT_SAVED));
        String totalAmount = cursor.getString(cursor.getColumnIndex(goalDbHelper.TOTAL_AMOUNT));
        String isReached = cursor.getString(cursor.getColumnIndex(goalDbHelper.IS_REACHED));

        return new Goal(name, PriceFormatter.unformat(amountSaved),
                PriceFormatter.unformat(totalAmount), Boolean.parseBoolean(isReached));
    }

    public int deleteGoal(String id)
    {
        SQLiteDatabase db = goalDbHelper.getWritableDatabase();
        String[] whereArgs ={id};

        int count =db.delete(goalDbHelper.TABLE_NAME ,goalDbHelper.UID+" = ?",whereArgs);
        return  count;
    }

    public int updateGoal(String id, String amountSaved, String totalAmount, String isReached)
    {
        SQLiteDatabase db = goalDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(goalDbHelper.AMOUNT_SAVED,amountSaved);
        contentValues.put(goalDbHelper.TOTAL_AMOUNT,totalAmount);
        contentValues.put(goalDbHelper.IS_REACHED, isReached);
        String[] whereArgs= {id};
        int count =db.update(goalDbHelper.TABLE_NAME,contentValues, goalDbHelper.UID+" = ?",whereArgs );
        return count;
    }

    public int updateGoalBalance(String id, String amountSaved, String isReached) {
        SQLiteDatabase db = goalDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(goalDbHelper.AMOUNT_SAVED,amountSaved);
        contentValues.put(goalDbHelper.IS_REACHED, isReached);
        String[] whereArgs= {id};
        int count =db.update(goalDbHelper.TABLE_NAME,contentValues, goalDbHelper.UID+" = ?",whereArgs );
        return count;
    }

    public long insertChore(String title, String details, String amount) {
        SQLiteDatabase db = choreDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(choreDbHelper.TITLE, title);
        contentValues.put(choreDbHelper.DETAILS, details);
        contentValues.put(choreDbHelper.AMOUNT, amount);
        contentValues.put(choreDbHelper.IS_ACCOMPLISHED, "false");
        long id = db.insert(choreDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }

    public Chore getChoreData(String id) {
        SQLiteDatabase db = choreDbHelper.getWritableDatabase();
        String[] columns = {choreDbHelper.UID,choreDbHelper.TITLE,choreDbHelper.DETAILS,choreDbHelper.AMOUNT,choreDbHelper.IS_ACCOMPLISHED};
        Cursor cursor =db.query(choreDbHelper.TABLE_NAME,columns,choreDbHelper.UID + "=" + id,
                null,null,null,null);
        String title =cursor.getString(cursor.getColumnIndex(choreDbHelper.TITLE));
        String details = cursor.getString(cursor.getColumnIndex(choreDbHelper.DETAILS));
        String amount = cursor.getString(cursor.getColumnIndex(choreDbHelper.AMOUNT));
        String isAccomplished = cursor.getString(cursor.getColumnIndex(choreDbHelper.IS_ACCOMPLISHED));

        return new Chore(title, details, PriceFormatter.unformat(amount), Boolean.parseBoolean(isAccomplished));
    }

    public String getChoreIdByTitle(String title) {
        SQLiteDatabase db = choreDbHelper.getWritableDatabase();
        String[] columns = {choreDbHelper.UID};
        Cursor cursor =db.query(choreDbHelper.TABLE_NAME,columns,choreDbHelper.TITLE + "=" + title,
                null,null,null,null);
        String id =cursor.getString(cursor.getColumnIndex(choreDbHelper.UID));
        return id;
    }

    public int deleteChore(String id) {
        SQLiteDatabase db = choreDbHelper.getWritableDatabase();
        String[] whereArgs ={id};

        int count =db.delete(choreDbHelper.TABLE_NAME ,choreDbHelper.UID+" = ?",whereArgs);
        return  count;
    }

    public int updateChore(String id, String isAccomplished) {
        SQLiteDatabase db = choreDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(choreDbHelper.IS_ACCOMPLISHED,isAccomplished);
        String[] whereArgs= {id};
        int count =db.update(choreDbHelper.TABLE_NAME,contentValues, choreDbHelper.UID+" = ?",whereArgs );
        return count;
    }

    public long insertUser(String pin, String name) {
        SQLiteDatabase db = userDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(userDbHelper.PIN, pin);
        contentValues.put(userDbHelper.NAME, name);

        long accountId = insertAccountData();

        contentValues.put(userDbHelper.ACCOUNT, accountId);
        contentValues.put(userDbHelper.CHORES, "");
        contentValues.put(userDbHelper.GOAL, "");

        long id = db.insert(userDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }

    public User getUserData(String id) {
        SQLiteDatabase db = userDbHelper.getReadableDatabase();
        String[] columns = {userDbHelper.UID,userDbHelper.PIN,userDbHelper.NAME,
                userDbHelper.ACCOUNT,userDbHelper.CHORES,userDbHelper.GOAL};
        Cursor cursor =db.query(userDbHelper.TABLE_NAME,columns,userDbHelper.UID + "=" + id,
                null,null,null,null);
        String email = cursor.getString(cursor.getColumnIndex(userDbHelper.EMAIL));
        String pin = cursor.getString(cursor.getColumnIndex(userDbHelper.PIN));
        String name =cursor.getString(cursor.getColumnIndex(userDbHelper.NAME));
        String account = cursor.getString(cursor.getColumnIndex(userDbHelper.ACCOUNT));
        String chores = cursor.getString(cursor.getColumnIndex(userDbHelper.CHORES));
        String goal = cursor.getString(cursor.getColumnIndex(userDbHelper.GOAL));

        Account userAccount = getAccountData(account);
        List<Chore> userChores = new ArrayList<Chore>();
        String[] choreIds = DataWrapper.unwrap(chores);
        for (int i = 0; i < choreIds.length; i++) {
            userChores.add(getChoreData(choreIds[i]));
        }
        Goal userGoal = getGoalData(goal);

        return new User(pin, name, userAccount, userChores, userGoal, id);
    }

    public String getUserAccountId(String id) {
        SQLiteDatabase db = userDbHelper.getReadableDatabase();
        String[] columns = {userDbHelper.ACCOUNT};

        Cursor cursor = db.query(userDbHelper.TABLE_NAME, columns, userDbHelper.UID+"="+id,
                null, null, null, null);
        String account = cursor.getString(cursor.getColumnIndex(userDbHelper.ACCOUNT));

        return account;
    }

    public String getUserChoresId(String id) {
        SQLiteDatabase db = userDbHelper.getReadableDatabase();
        String[] columns = {userDbHelper.CHORES};

        Cursor cursor = db.query(userDbHelper.TABLE_NAME, columns, userDbHelper.UID+"="+id,
                null, null, null, null);
        String chores = cursor.getString(cursor.getColumnIndex(userDbHelper.CHORES));

        return chores;
    }

    public String getUserGoalId(String id) {
        SQLiteDatabase db = userDbHelper.getReadableDatabase();
        String[] columns = {userDbHelper.GOAL};

        Cursor cursor = db.query(userDbHelper.TABLE_NAME, columns, userDbHelper.UID+"="+id,
                null, null, null, null);
        String goal = cursor.getString(cursor.getColumnIndex(userDbHelper.GOAL));

        return goal;
    }

    public int deleteUser(String id) {
        SQLiteDatabase db = choreDbHelper.getWritableDatabase();
        String[] whereArgs ={id};

        String accountId = getUserAccountId(id);
        String choresId = getUserChoresId(id);
        String goalId = getUserGoalId(id);

        if (!accountId.isEmpty()) {
            int accountDeleteResult = deleteAccount(accountId);
            if (accountDeleteResult == 0) {
                // some kind of error occurred when deleting the account
                return 0;
            }
        }
        if (!choresId.isEmpty()) {
            String[] choresIds = DataWrapper.unwrap(choresId);
            for (int i = 0; i < choresIds.length; i++) {
                int choreDeleteResult = deleteChore(choresIds[i]);
                if (choreDeleteResult == 0) {
                    // some kind of error occurred when deleting the chore
                    // implement logic to add the deleted account back in
                    return 0;
                }
            }
        }
        if (!goalId.isEmpty()) {
            int goalDeleteResult = deleteGoal(goalId);
            if (goalDeleteResult == 0) {
                // some kind of error occurred when deleting the goal
                return 0;
            }
        }

        int count = db.delete(userDbHelper.TABLE_NAME ,userDbHelper.UID+" = ?",whereArgs);
        return  count;
    }

    public int updateUser(String id, String account, String chores, String goal) {
        SQLiteDatabase db = userDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(userDbHelper.ACCOUNT,account);
        contentValues.put(userDbHelper.CHORES,chores);
        contentValues.put(userDbHelper.GOAL,goal);
        String[] whereArgs= {id};
        int count = db.update(userDbHelper.TABLE_NAME,contentValues, userDbHelper.UID+" = ?",whereArgs );
        return count;
    }

    // called when a new chore is added or when a chore is deleted
    public int updateUserChores(String id, String chores) {
        SQLiteDatabase db = userDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(userDbHelper.CHORES,chores);
        String[] whereArgs= {id};
        int count = db.update(userDbHelper.TABLE_NAME,contentValues, userDbHelper.UID+" = ?",whereArgs );
        return count;
    }

    // called when a new goal is added or when a goal is deleted
    public int updateUserGoal(String id, String goal) {
        SQLiteDatabase db = userDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(userDbHelper.GOAL,goal);
        String[] whereArgs= {id};
        int count = db.update(userDbHelper.TABLE_NAME,contentValues, userDbHelper.UID+" = ?",whereArgs );
        return count;
    }

    static class AccountDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "pocketPiggyDatabase";
        private static final String TABLE_NAME = "Account";
        private static final int DATABASE_Version = 1;
        private static final String UID="id";
        private static final String BALANCE = "balance";
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+BALANCE+" VARCHAR(255));";
        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public AccountDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                // do nothing as for now
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {
                // do nothing as for now
            }
        }
    }

    static class GoalDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "pocketPiggyDatabase";
        private static final String TABLE_NAME = "Goal";
        private static final int DATABASE_Version = 1;
        private static final String UID="id";
        private static final String NAME = "name";
        private static final String AMOUNT_SAVED = "amount_saved";
        private static final String TOTAL_AMOUNT = "total_amount";
        private static final String IS_REACHED = "is_reached";
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255) ," + AMOUNT_SAVED +
                " VARCHAR(255) ," + TOTAL_AMOUNT + " VARCHAR(255) ," + IS_REACHED + " VARCHAR(255));";
        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public GoalDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                // do nothing as for now
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {
                // do nothing as for now
            }
        }
    }

    static class ChoreDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "pocketPiggyDatabase";
        private static final String TABLE_NAME = "Chore";
        private static final int DATABASE_Version = 1;
        private static final String UID="id";
        private static final String TITLE = "title";
        private static final String DETAILS = "details";
        private static final String AMOUNT = "amount";
        private static final String IS_ACCOMPLISHED = "is_accomplished";
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+TITLE+" VARCHAR(255) ," + DETAILS +
                " VARCHAR(255) ," + AMOUNT + " VARCHAR(255) ," + IS_ACCOMPLISHED + " VARCHAR(255));";
        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public ChoreDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                // do nothing as for now
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {
                // do nothing as for now
            }
        }
    }

    static class UserDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "pocketPiggyDatabase";
        private static final String TABLE_NAME = "User";
        private static final int DATABASE_Version = 1;
        private static final String UID="id";
        private static final String EMAIL = "email";
        private static final String PIN = "pin";
        private static final String NAME = "name";
        private static final String ACCOUNT = "account";
        private static final String CHORES = "chores";
        private static final String GOAL = "goal";
        private static final String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME +
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, " + EMAIL + " VARCHAR(255) ," + PIN +
                " VARCHAR(255) ," + NAME + " VARCHAR(255) ," + ACCOUNT + " VARCHAR(255) ," + CHORES +
                " VARCHAR(255) ," + GOAL + " VARCHAR(255));";
        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public UserDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                // do nothing as for now
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {
                // do nothing as for now
            }
        }
    }
}
