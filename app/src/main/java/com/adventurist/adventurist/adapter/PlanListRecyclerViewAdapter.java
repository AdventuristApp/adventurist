package com.adventurist.adventurist.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.adventurist.adventurist.PlanDetailsActivity;
import com.adventurist.adventurist.R;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Plan;

import java.util.List;

public class PlanListRecyclerViewAdapter extends RecyclerView.Adapter<PlanListRecyclerViewAdapter.PlanListViewHolder>{

    private List<Plan> plans;
    private Context callingActivity;

    public PlanListRecyclerViewAdapter(List<Plan> plans, Context callingActivity) {
        this.plans = plans;
        this.callingActivity = callingActivity;
    }

    @NonNull
    @Override
    public PlanListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View planFragment = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_plan_list, parent, false);
        return new PlanListViewHolder(planFragment);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanListViewHolder holder, int position) {
        Plan plan = plans.get(position);

        // Bind data to the views
        holder.bind(plan);

        // Set click listener
        holder.itemView.setOnClickListener(v -> {
            Intent goToPlanDetails = new Intent(callingActivity, PlanDetailsActivity.class);
            goToPlanDetails.putExtra("planName", plan.getPlanName());
            goToPlanDetails.putExtra("numberOfDays", plan.getNumberOfDays());
            goToPlanDetails.putExtra("destination", plan.getDestination());
            goToPlanDetails.putExtra("budget", plan.getBudget());

            callingActivity.startActivity(goToPlanDetails);
        });
    }

    @Override
    public int getItemCount() {
        return plans.size();
    }

    public static class PlanListViewHolder extends RecyclerView.ViewHolder {
        private TextView planFragmentTextViewPlanName;

        public PlanListViewHolder(@NonNull View itemView) {
            super(itemView);
            planFragmentTextViewPlanName = itemView.findViewById(R.id.planNameFragment);
        }

        public void bind(Plan plan) {
            planFragmentTextViewPlanName.setText(plan.getPlanName());
        }
    }



}
