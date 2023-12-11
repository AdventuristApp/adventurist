package com.adventurist.adventurist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PlanDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_details);

        Intent intent = getIntent();
        String planName = intent.getStringExtra("planName");
        int numberOfDays = intent.getIntExtra("numberOfDays", 0);
        String destination = intent.getStringExtra("destination");
        double budget = intent.getDoubleExtra("budget", 0.0);

        TextView planNameTextView = findViewById(R.id.planNameDetails);
        planNameTextView.setText(planName);

        TextView numberOfDaysTextView = findViewById(R.id.numberOfDaysDetails);
        numberOfDaysTextView.setText(String.valueOf(numberOfDays));

        TextView destinationTextView = findViewById(R.id.destinationDetails);
        destinationTextView.setText(destination);

        TextView budgetTextView = findViewById(R.id.budgetDetails);
        budgetTextView.setText(String.valueOf(budget));

    }
}