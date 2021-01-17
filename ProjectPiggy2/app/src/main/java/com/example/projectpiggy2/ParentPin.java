package com.example.projectpiggy2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ParentPin extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_pin);

        Button doneButton = (Button) findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editTextNumber = (EditText) findViewById(R.id.editTextNumber);


                if ( editTextNumber.getText().length() == 4 ) {
                    int pin = Integer.parseInt(editTextNumber.getText().toString());

                    Intent startIntent = new Intent(getApplicationContext(), ConfirmPin.class);
                    startIntent.putExtra("parentPinUnconfirmed", pin );
                    startActivity(startIntent);

                    //go to next activity -- confirm pin
                }
                else {
                    //nothing happens and the user must enter a complete pin
                }
                }


        });





    }
}

