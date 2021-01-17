package com.example.projectpiggy2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmPinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_pin);
        int pin = getIntent().getExtras().getInt("parentPinUnconfirmed");

        UserController.init(this);
        Button doneButton = (Button) findViewById(R.id.doneButton);
        String userName = getIntent().getExtras().getString("name");
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editTextNumber = (EditText) findViewById(R.id.editTextNumber);

                if (editTextNumber.getText().length() == 4) {
                    int pinConfirm = Integer.parseInt(editTextNumber.getText().toString());

                    if (pin == pinConfirm) {
                        //save pin and continue
                        UserController.createUser(String.valueOf(pin), userName);
                        Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(mainActivity);

                    } else {
                        Intent incorrectPinIntent = new Intent(getApplicationContext(), ParentPinActivity.class);
                        incorrectPinIntent.putExtra("name", userName);
                        startActivity(incorrectPinIntent);
                    }
                } else {
                    //nothing happens and the user must enter a complete pin
                }
            }
        });
    }
}