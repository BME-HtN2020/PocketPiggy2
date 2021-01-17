package com.example.projectpiggy2.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectpiggy2.ParentPinActivity;
import com.example.projectpiggy2.R;
import com.example.projectpiggy2.UserController;

public class NameActivity extends AppCompatActivity {

    android.widget.Button submit;
    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.projectpiggy2.R.layout.activity_name);

        UserController.init(this);
        name = (EditText) findViewById(R.id.name_edit_text);
        submit = findViewById(R.id.button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter your name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // get the user name
                String userName = name.getText().toString();

                // create the intent for parent pin activity
                Intent parentPinIntent = new Intent(NameActivity.this, ParentPinActivity.class);

                // set extras to carry over the name
                parentPinIntent.putExtra("name", userName);

                // navigate to parent pin activity
                startActivity(parentPinIntent);
            }
        });
    }
}