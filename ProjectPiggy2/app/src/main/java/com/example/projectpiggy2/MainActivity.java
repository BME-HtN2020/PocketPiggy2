package com.example.projectpiggy2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView balanceText = findViewById(R.id.balanceText);
        TextView goalText = findViewById(R.id.goalsText);
        TextView amountToGoText = findViewById(R.id.amountToGoText);

        String balance = UserController.getCurrentUser().getAccount().toString();
        Goal userGoal = UserController.getCurrentUser().getGoal();
        String goalName = "";
        String goalTotalAmount = "";
        String goalAmountSaved = "$0.00";

        if (userGoal != null) {
            goalName = userGoal.getName();
            goalTotalAmount = PriceFormatter.format(userGoal.getTotalAmount());
            goalAmountSaved = PriceFormatter.format(userGoal.getTotalAmount());
        }

        balanceText.setText(balance);
        goalText.setText(goalName);
        amountToGoText.setText(goalAmountSaved);
    }
}