package com.example.projectpiggy2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    DbAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbAdapter = new DbAdapter(this);
    }

    // add a user and write it to the db
    // this function may have to be moved to the right activity (maybe sign up?)
    public boolean addUser(View view) {
        String email = ""; // logic to set the user email
        String pin = ""; // logic to set the user pin
        String name = ""; // logic to set the user name

        if (email.isEmpty() || pin.isEmpty() || name.isEmpty()) {
            // please try again
        } else {
            long id = dbAdapter.insertUser(email, pin, name);
            if (id <= 0) {
                // unsuccessful write to the database
                return false;
            }
            return true;
        }

        return false;
    }
}