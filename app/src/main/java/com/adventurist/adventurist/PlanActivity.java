package com.adventurist.adventurist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.adventurist.adventurist.adapter.PlanListRecyclerViewAdapter;
import com.adventurist.adventurist.helper.SimpleItemTouchHelperCallback;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Plan;

import java.util.ArrayList;
import java.util.List;

public class PlanActivity extends AppCompatActivity {
    public static final String TAG = "PlanActivity";

    private PlanListRecyclerViewAdapter adapter;

    List<Plan> plans = new ArrayList<>();

    private RecyclerView plansListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        setUpPlansRecyclerView();

        goToAddPlane();
        readPlans();
        enableSwipeAndDrag();
    }

    private void goToAddPlane() {
        ImageView addPlaneButton = findViewById(R.id.addPlan);
        addPlaneButton.setOnClickListener(v -> {
            Intent goToAddPlaneIntent = new Intent(this, AddPlanActivity.class);
            startActivity(goToAddPlaneIntent);
        });
    }

    private void setUpPlansRecyclerView() {
        plansListRecyclerView = findViewById(R.id.PlansRecyclerView);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 2);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        LinearLayoutManager layoutManager = new GridLayoutManager(this, 3);
        plansListRecyclerView.setLayoutManager(layoutManager);

        adapter = new PlanListRecyclerViewAdapter(plans, this);
        plansListRecyclerView.setAdapter(adapter);
    }

    private void readPlans() {
        AuthUser authUser = Amplify.Auth.getCurrentUser();

        if (authUser != null){
            String userId = authUser.getUserId();


            Amplify.API.query(
                    ModelQuery.list(Plan.class, Plan.USER_ID.eq(userId)),
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

    private void enableSwipeAndDrag() {
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(plansListRecyclerView);
    }
}