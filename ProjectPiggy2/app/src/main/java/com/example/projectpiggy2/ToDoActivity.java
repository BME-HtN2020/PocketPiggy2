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

        taskHelper = new TaskDatabaseHelper(this);
        dbAdapter = new DbAdapter(this);
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
//                LinearLayout layout = new LinearLayout(this);
//
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                layout.setLayoutParams(params);
//                layout.setOrientation(LinearLayout.VERTICAL);
//
//                final EditText titleEdit = new EditText(this);
//                Spinner dropdown = new Spinner(ToDoActivity.this);
//                final EditText amountEdit = new EditText(this);
//
//
//
//                layout.addView(titleEdit);
//                layout.addView(dropdown);
//                layout.addView(amountEdit);

                ConstraintLayout layout = findViewById(R.id.choresLayout);
                EditText titleEdit = findViewById(R.id.editTextSimple2);
                EditText amountEdit = findViewById(R.id.editTextSimple);
                Spinner dropdown = findViewById(R.id.spinner);

                String[] items = {"1 Day", "4 Day", "1 Week"};
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
                dropdown.setAdapter(arrayAdapter);
                amountEdit.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                amountEdit.addTextChangedListener(new NumberTextWatcher(amountEdit, "#,###"));

                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a new task").setMessage("What do you want to do next?")
                        .setView(layout)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(titleEdit.getText());
                                String amount = String.valueOf(amountEdit.getText());
                                String frequency = String.valueOf(dropdown.getSelectedItem().toString());

                                dbAdapter.insertChore(task, frequency, amount);
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

    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.title_task);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = taskHelper.getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE, TaskContract.TaskEntry.COL_TASK_TITLE + " = ?", new String[]{task});
        db.close();
        updateUI();
    }

    private void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = dbAdapter.choreDbHelper.getReadableDatabase();
        Cursor cursor = db.query("Chore",
                new String[]{"id", "title", "amount"},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex("amount");
            taskList.add(cursor.getString(idx));
        }

        if (arrAdapter == null) {
            arrAdapter = new ArrayAdapter<>(this, R.layout.todo_task, R.id.title_task, taskList);
            TaskList.setAdapter(arrAdapter);
        } else {
            arrAdapter.clear();
            arrAdapter.addAll(taskList);
            arrAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }
}