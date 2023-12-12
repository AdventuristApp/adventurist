package com.adventurist.adventurist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Plan;
import com.google.android.material.snackbar.Snackbar;

public class AddPlanActivity extends AppCompatActivity {
    public static final String TAG = "AddPlanActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        setUpAddPlanButton();
    }

    private void setUpAddPlanButton(){
        Button addPlan = (Button) findViewById(R.id.addPlanButton);

        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Your Plan is added :D", Snackbar.LENGTH_LONG);

        addPlan.setOnClickListener(v -> {
            String planName = ((EditText) findViewById(R.id.addPlanName)).getText().toString();
            int numberOfDays = Integer.parseInt(((EditText) findViewById(R.id.addNumberOfDays)).getText().toString());
            String destination = ((EditText) findViewById(R.id.addDestination)).getText().toString();
            double budget = Double.parseDouble(((EditText) findViewById(R.id.addBudget)).getText().toString());

            AuthUser authUser = Amplify.Auth.getCurrentUser();
            if (authUser != null){
                String userId = authUser.getUserId();

                Plan newPlan = Plan.builder()
                        .planName(planName)
                        .numberOfDays(numberOfDays)
                        .destination(destination)
                        .budget(budget)
                        .userId(userId)
                        .build();

                Amplify.API.mutate(
                        ModelMutation.create(newPlan),
                        successRes -> Log.i(TAG, "Creating a plan successfully" + newPlan),
                        failureRes -> Log.e(TAG, "Failed with this res: " + failureRes)
                );

                snackbar.show();

            } else Log.e(TAG, "User is not authenticated");

            snackbar.show();
        });
    }
}