package com.adventurist.adventurist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.adventurist.adventurist.adapter.PlanListRecyclerViewAdapter;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Plan;

import java.util.ArrayList;
import java.util.List;

public class PlanActivity extends AppCompatActivity {

    public static final String TAG = "PlanActivity";

    private RecyclerView recyclerView;

    private PlanListRecyclerViewAdapter adapter;

    List<Plan> plans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plane);

        setUpPlansRecyclerView();

        goToAddPlane();
        readPlans();
    }

    private void goToAddPlane() {
        Button addPlaneButton = findViewById(R.id.addPlan);
        addPlaneButton.setOnClickListener(v -> {
            Intent goToAddPlaneIntent = new Intent(this, AddPlanActivity.class);
            startActivity(goToAddPlaneIntent);
        });
    }

    private void setUpPlansRecyclerView() {
        RecyclerView tasksListRecyclerView = findViewById(R.id.PlansRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        tasksListRecyclerView.setLayoutManager(layoutManager);

        adapter = new PlanListRecyclerViewAdapter(plans, this);
        tasksListRecyclerView.setAdapter(adapter);
    }


    private void readPlans() {

       Amplify.API.query(
               ModelQuery.list(Plan.class),
               success -> {
                   Log.i(TAG, "Read plans successfully");
                   Log.i(TAG, "reading" + plans.toString());
                   plans.clear();
                   for (Plan planList : success.getData()){
                       plans.add(planList);
                   }
                   runOnUiThread(()->{
                       adapter.notifyDataSetChanged();
                   });
               },
               fail -> Log.i(TAG, "Did not read Plan")
       );
    }
}