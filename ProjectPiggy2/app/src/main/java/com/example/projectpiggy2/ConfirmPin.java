package com.example.projectpiggy2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmPin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_pin);
        int pin = getIntent().getExtras().getInt("parentPinUnconfirmed");

        Button doneButton = (Button) findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editTextNumber = (EditText) findViewById(R.id.editTextNumber);

                if (editTextNumber.getText().length() == 4) {
                    int pinConfirm = Integer.parseInt(editTextNumber.getText().toString());

                    if (pin == pinConfirm) {
                        //save pin and continue

                    } else {
                        Intent incorrectPinIntent = new Intent(getApplicationContext(), ParentPin.class);
                        startActivity(incorrectPinIntent);
                    }
                } else {
                    //nothing happens and the user must enter a complete pin
                }
            }
        });
    }
}