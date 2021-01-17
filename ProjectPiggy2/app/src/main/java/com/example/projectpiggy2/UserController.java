package com.example.projectpiggy2;

import android.content.Context;

import java.util.Arrays;
import java.util.List;

public class UserController {
    private static DbAdapter dbAdapter;
    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User a) {
        currentUser = a;
    }

    public static void init(Context context) {
        dbAdapter = new DbAdapter(context);
    }

    public static void createUser(String pin, String name) {
        long id = dbAdapter.insertUser(pin, name);
        String accountId = dbAdapter.getUserAccountId(String.valueOf(id));
        Account account = dbAdapter.getAccountData(accountId);
        User b = new User(pin, name, account, null, null, String.valueOf(id));
        currentUser = b;
    }

    public static void createChore(String title, String detail, String amount) {
        long id = dbAdapter.insertChore(title, detail, amount);
        String choresId = dbAdapter.getUserChoresId(currentUser.getId());
        choresId.concat(";" + id);
        dbAdapter.updateUserChores(currentUser.getId(), choresId);
        currentUser.assignChore(title, detail, Double.parseDouble(amount));
    }

    public static void completeChore(String title) {
        String choreId = dbAdapter.getChoreIdByTitle(title);
        dbAdapter.deleteChore(choreId);
        String userId = currentUser.getId();
        String[] choreIds = DataWrapper.unwrap(dbAdapter.getUserChoresId(userId));
        List<String> temp = Arrays.asList(choreIds);
        temp.remove(choreId);
        String[] array = (String[]) temp.toArray();
        String a = DataWrapper.wrap(array);
        dbAdapter.updateUserChores(userId, a);
    }
}

