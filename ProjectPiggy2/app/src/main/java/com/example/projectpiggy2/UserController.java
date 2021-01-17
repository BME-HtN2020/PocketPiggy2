package com.example.projectpiggy2;

import android.content.Context;

import java.util.Arrays;
import java.util.List;

public class UserController {
    private static DbAdapter dbAdapter;
    private static AccountDbAdapter accountDbAdapter;
    private static ChoreDbAdapter choreDbAdapter;
    private static GoalDbAdapter goalDbAdapter;
    private static UserDbAdapter userDbAdapter;
    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User a) {
        currentUser = a;
    }

    public static void init(Context context) {
        dbAdapter = new DbAdapter(context);
        accountDbAdapter = new AccountDbAdapter(context);
        choreDbAdapter = new ChoreDbAdapter(context);
        goalDbAdapter = new GoalDbAdapter(context);
        userDbAdapter = new UserDbAdapter(context);
    }

    public static void createUser(String pin, String name) {
        long id = userDbAdapter.insertUser(pin, name);
        String accountId = userDbAdapter.getUserAccountId(String.valueOf(id));
        Account account = accountDbAdapter.getAccountData(accountId);
        User b = new User(pin, name, account, null, null, String.valueOf(id));
        currentUser = b;
    }

    // this method sets the currentUser field to the found user
    // access newly found data through that static field
    public static void findUser(String name) {
        String id = userDbAdapter.getUserIdByName(name);
        User user = userDbAdapter.getUserData(id);
        currentUser = user;
    }

    public static void createChore(String title, String detail, String amount) {
        long id = choreDbAdapter.insertChore(title, detail, amount);
        String choresId = userDbAdapter.getUserChoresId(currentUser.getId());
        if (!choresId.isEmpty()) {
            choresId = choresId + ";";
        }
        choresId = choresId + id;
        userDbAdapter.updateUserChores(currentUser.getId(), choresId);
        currentUser.assignChore(title, detail, PriceFormatter.unformat(amount));
    }

    public static void completeChore(String title) {
        currentUser.doChore(title);
        String choreId = choreDbAdapter.getChoreIdByTitle(title);
        choreDbAdapter.updateChore(choreId, "true"); // call deleteChore instead if you want to remove it

        // the below holds logic to delete an attached chore from the user database
//        String userId = currentUser.getId();
//        String[] choreIds = DataWrapper.unwrap(userDbAdapter.getUserChoresId(userId));
//        List<String> temp = Arrays.asList(choreIds);
//        temp.remove(choreId);
//        String[] array = (String[]) temp.toArray();
//        String a = DataWrapper.wrap(array);
//        userDbAdapter.updateUserChores(userId, a);
    }

    public static void addAllowance(String addAmount) {
        double amount = PriceFormatter.unformat(addAmount);
        currentUser.giveAllowance(amount);
        String userId = currentUser.getId();
        String accountId = userDbAdapter.getUserAccountId(userId);
        String updatedBalance = PriceFormatter.format(currentUser.getAccount().getBalance());
        accountDbAdapter.updateAccountBalance(accountId, updatedBalance);
    }

    public static void setGoal(String name, String amount) {
        currentUser.setGoal(name, PriceFormatter.unformat(amount));
        long goalId = goalDbAdapter.insertGoal(name, amount);
        String userId = currentUser.getId();
        userDbAdapter.updateUserGoal(userId, String.valueOf(goalId));
    }

    public static void updateGoal(String amount) {
        double goalAmount = PriceFormatter.unformat(amount);
        currentUser.addSavings(goalAmount);
        String userId = currentUser.getId();
        String accountId = userDbAdapter.getUserAccountId(userId);
        String goalId = userDbAdapter.getUserGoalId(userId);
        boolean goalIsReached = currentUser.getGoal().getIsReached();
        String updatedAccountBalance = PriceFormatter.format(currentUser.getAccount().getBalance());
        accountDbAdapter.updateAccountBalance(accountId, updatedAccountBalance);
        String updatedGoalBalance = PriceFormatter.format(currentUser.getGoal().getAmountSaved());
        goalDbAdapter.updateGoalBalance(goalId, updatedGoalBalance, Boolean.toString(goalIsReached));
    }
}

