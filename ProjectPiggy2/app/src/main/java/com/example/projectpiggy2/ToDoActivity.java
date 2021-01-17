package com.example.projectpiggy2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.projectpiggy2.task_database.TaskContract;
import com.example.projectpiggy2.task_database.TaskDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ToDoActivity extends AppCompatActivity {
    private static final String TAG = "ToDoActivity";
    private TaskDatabaseHelper taskHelper;
    private DbAdapter dbAdapter;
    private ListView TaskList;
    private ArrayAdapter<String> arrAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        UserController.init(this);

        taskHelper = new TaskDatabaseHelper(this);
        TaskList = (ListView) findViewById(R.id.list_to_do);

        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                View choresLayout = getLayoutInflater().inflate(R.layout.chores, null);
                LinearLayout layout = choresLayout.findViewById(R.id.choresLayout);
                EditText titleEdit = choresLayout.findViewById(R.id.titleEditText);
                EditText amountEdit = choresLayout.findViewById(R.id.amountEditText);
                Spinner dropdown = choresLayout.findViewById(R.id.frequencySpinner);

                String[] items = {"1 Day", "4 Day", "1 Week"};
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
                dropdown.setAdapter(arrayAdapter);
                amountEdit.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                amountEdit.addTextChangedListener(new NumberTextWatcher(amountEdit, "#,###"));

                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setView(layout)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(titleEdit.getText());
                                String amount = String.valueOf(amountEdit.getText());
                                String frequency = String.valueOf(dropdown.getSelectedItem().toString());

                                UserController.createChore(task, frequency, amount);
                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel", null).create();
                dialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void completeTask(View view) {
        AlertDialog.Builder pinDialogBuilder = new AlertDialog.Builder(this);
        pinDialogBuilder.setTitle("PIN Required");
        pinDialogBuilder.setMessage("Please enter your PIN");
        pinDialogBuilder.setCancelable(false);
        View pinDialog = getLayoutInflater().inflate(R.layout.pin_dialog, null);
        pinDialogBuilder.setView(pinDialog.findViewById(R.id.pinLayout));

        EditText pinEditText = pinDialog.findViewById(R.id.pinEditText);

        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.title_task);
        String task = String.valueOf(taskTextView.getText());

        pinDialogBuilder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String enteredPin = pinEditText.getText().toString();
                if (UserController.getCurrentUser().verify(enteredPin)) {
                    UserController.completeChore(task);
                    updateUI();
                } else {
                    return;
                }
            }
        });

        pinDialogBuilder.create().show();
    }

    private void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();
        List<Chore> choreList = UserController.getCurrentUser().getChores();

        if (choreList != null) {
            for (int i = 0; i < choreList.size(); i++) {
                Chore chore = choreList.get(i);
                if (!chore.isAccomplished) {
                    taskList.add(choreList.get(i).getTitle());
                }
            }
        }

        if (arrAdapter == null) {
            arrAdapter = new ArrayAdapter<>(this, R.layout.todo_task, R.id.title_task, taskList);
            TaskList.setAdapter(arrAdapter);
        } else {
            arrAdapter.clear();
            arrAdapter.addAll(taskList);
            arrAdapter.notifyDataSetChanged();
        }
    }
}