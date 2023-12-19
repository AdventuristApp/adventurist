package com.adventurist.adventurist.adapter;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;


import com.adventurist.adventurist.PlanDetailsActivity;
import android.app.AlertDialog;
import com.adventurist.adventurist.R;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Plan;
import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.List;
public class PlanListRecyclerViewAdapter extends RecyclerView.Adapter<PlanListRecyclerViewAdapter.PlanListViewHolder>
        implements ItemTouchHelperAdapter {

    private List<Plan> plans;
    private Context callingActivity;
    private OnDeleteClickListener onDeleteClickListener;

    public static final String TAG = "Adapter";


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

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanListViewHolder holder, int position) {
        Plan plan = plans.get(position);

        holder.bind(plan);

//        holder.itemView.setOnClickListener(v -> {
//            Intent goToPlanDetails = new Intent(callingActivity, PlanDetailsActivity.class);
//            goToPlanDetails.putExtra("planName", plan.getPlanName());
//            goToPlanDetails.putExtra("numberOfDays", plan.getNumberOfDays());
//            goToPlanDetails.putExtra("destination", plan.getDestination());
//            goToPlanDetails.putExtra("budget", plan.getBudget());
//
//            callingActivity.startActivity(goToPlanDetails);
//        });

        holder.itemView.setOnClickListener(v -> {
            showPopupMessage(plan);
        });


        holder.deleteButton.setOnClickListener(v -> {
            showDeleteConfirmationDialog(plan, holder.getAdapterPosition());
        });
    }

//    private void showPopupMessage(Plan plan) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(callingActivity, R.style.CustomAlertDialog);
//        builder.setTitle("Plan Details");
//
//        String message = "<br/><b style='color:#3366cc;'>Plan:</b> " + plan.getPlanName() +
//                "<br/><b style='color:#3366cc;'>Days:</b> " + plan.getNumberOfDays() +
//                "<br/><b style='color:#3366cc;'>Destination:</b> " + plan.getDestination() +
//                "<br/><b style='color:#3366cc;'>Budget:</b> " + plan.getBudget();
//
//        TextView messageTextView = new TextView(callingActivity);
//        messageTextView.setText(Html.fromHtml(message));
//        messageTextView.setGravity(Gravity.CENTER);
//
//        builder.setView(messageTextView);
//
//        builder.setPositiveButton(Html.fromHtml("<font color='#870707'>Close</font>"), (dialog, which) -> {
//        });
//
//        builder.create().show();
//    }


    private void showPopupMessage(Plan plan) {
        AlertDialog.Builder builder = new AlertDialog.Builder(callingActivity, R.style.CustomAlertDialog);

        TextView titleTextView = new TextView(callingActivity);

        int paddingTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, callingActivity.getResources().getDisplayMetrics());
        titleTextView.setPadding(0, paddingTop, 0, 0);

        titleTextView.setText("Plan Details");
        titleTextView.setGravity(Gravity.CENTER);
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        titleTextView.setTextColor(ContextCompat.getColor(callingActivity, R.color.black));

        builder.setCustomTitle(titleTextView);

        String message = "<br/><br/><br/><b style='color:#3366cc;'>Plan:</b> " + plan.getPlanName() +
                "<br/><br/><b style='color:#3366cc;'>Days:</b> " + plan.getNumberOfDays() +
                "<br/><br/><b style='color:#3366cc;'>Destination:</b> " + plan.getDestination() +
                "<br/><br/><b style='color:#3366cc;'>Budget:</b> " + plan.getBudget();

        TextView messageTextView = new TextView(callingActivity);
        messageTextView.setText(Html.fromHtml(message));
        messageTextView.setGravity(Gravity.CENTER);

        builder.setView(messageTextView);

        builder.setPositiveButton(Html.fromHtml("<font color='#870707'>Close</font>"), (dialog, which) -> {
        });

        builder.create().show();
    }

    private void showDeleteConfirmationDialog(Plan plan, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(callingActivity);
        builder.setTitle("Delete Plan");
        builder.setMessage("Are you sure you want to delete this plan?");

        builder.setPositiveButton(Html.fromHtml("<font color='#870707'>Yes</font>"), (dialog, which) -> {
            deletePlan(plan, position);
            Snackbar.make(((AppCompatActivity) callingActivity).findViewById(android.R.id.content),
                    "Plan deleted", Snackbar.LENGTH_SHORT).show();
        });

        builder.setNegativeButton(Html.fromHtml("<font color='#3366cc'>No</font>"), (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }


    private void deletePlan(Plan plan, int position) {
        Amplify.API.mutate(
                com.amplifyframework.api.graphql.model.ModelMutation.delete(plan),
                success -> {
                    Log.i(TAG, "Deleted plan successfully");
                    runOnUiThread(() -> {
                        plans.remove(position);
                        notifyItemRemoved(position);
                    });
                },
                fail -> Log.e(TAG, "Failed to delete plan", fail)
        );
    }

    @Override
    public int getItemCount() {
        return plans.size();
    }

    public static class PlanListViewHolder extends RecyclerView.ViewHolder {
        private TextView planFragmentTextViewPlanName;
        private TextView planFragmentTextViewDestination;
        private ImageView deleteButton;

        public PlanListViewHolder(@NonNull View itemView) {
            super(itemView);
            planFragmentTextViewPlanName = itemView.findViewById(R.id.planNameFragment);
            planFragmentTextViewDestination = itemView.findViewById(R.id.destinationFragment);
            deleteButton = itemView.findViewById(R.id.deletePlanButton);
        }

        public void bind(Plan plan) {
            planFragmentTextViewPlanName.setText(plan.getPlanName());
            planFragmentTextViewDestination.setText(plan.getDestination());
        }
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(plans, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
//    keep it like this until i see if i want it or not ._.
//        plans.remove(position);
//        notifyItemRemoved(position);    }
    }
}
