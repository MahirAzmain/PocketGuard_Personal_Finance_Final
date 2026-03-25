package com.example.pocketguard.GoalSet;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketguard.Key_SharedPreference;
import com.example.pocketguard.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 * Adapter class for managing and displaying goal setting data in a RecyclerView.
 *
 * <p>This class follows the Adapter design pattern, which allows the RecyclerView to display
 * a list of items by binding the data to the views. It also implements the Filterable interface
 * to support filtering of the list based on the category.</p>
 */
public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private ArrayList<constructor> arrayList;

    List<constructor> arraylist_search;  // this is for searching
    /**
     * Constructs a new GoalAdapter with the specified context and list of goal data.
     *
     * @param context the context in which the adapter is operating
     * @param arrayList the list of goal data to be displayed
     */
    GoalAdapter(Context context, ArrayList<constructor> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

        this.arraylist_search = new ArrayList<>(arrayList);// this is for searching
    }
    /**
     * Returns a filter that performs filtering operations on the data set.
     *
     * @return the filter used to perform filtering operations
     */
    @Override
    public Filter getFilter() {
        return filter;
    }
    /**
     * Filter for performing filtering operations on the data set.
     */
    Filter filter = new Filter() {
        @Override  //run on background thread
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<constructor> filteredlist = new ArrayList<>();
            if (charSequence.toString().isEmpty()) {
                filteredlist.addAll(arraylist_search);
            } else {
                for (constructor list_search : arraylist_search) {
                    if (list_search.category.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredlist.add(list_search);
                    }
                    if (list_search.category.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredlist.add(list_search);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredlist;
            return filterResults;
        }

        @Override  // run on ui thread
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            arrayList.clear();
            arrayList.addAll((Collection<? extends constructor>) filterResults.values);
            notifyDataSetChanged();


        }
    };

    /**
     * Called when RecyclerView needs a new {@link MyViewHolder} of the given type to represent an item.
     *
     * @param parent the ViewGroup into which the new View will be added after it is bound to an adapter position
     * @param viewType the view type of the new View
     * @return a new ViewHolder that holds a View of the given view type
     */

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.list_item_spending_limit, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);


        return myViewHolder;
    }
    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder the ViewHolder which should be updated to represent the contents of the item at the given position
     * @param position the position of the item within the adapter's data set
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.category.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));
        holder.progressBar.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));
        holder.tv_progress.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));
        holder.tv_limit.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));
        holder.shw_total_percentage.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));

        holder.category.setText(arrayList.get(position).category.toString());
        holder.tv_progress.setText("Progress : "+String.valueOf(arrayList.get(position).progress_expense));
        holder.tv_limit.setText("Limit : "+String.valueOf(arrayList.get(position).limit_expense));

        holder.progressBar.setMax(100);
        if (Integer.valueOf(arrayList.get(position).progress_expense) == 0) {
            holder.progressBar.setProgress(0);
            holder.shw_total_percentage.setText("Filled : 0%");
        } else {
            int temp = (int) ((Double.valueOf(arrayList.get(position).progress_expense) / Double.valueOf(arrayList.get(position).limit_expense)) * 100);
            Log.d("Checking Progress value", "onBindViewHolder: " + temp +" ; "+arrayList.get(position).progress_expense+" ; "+arrayList.get(position).limit_expense);
            holder.progressBar.setProgress(temp);

            holder.shw_total_percentage.setText("Filled : "+String.valueOf(temp)+"%");
        }
    }
    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return the total number of items in this adapter
     */

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    /**
     * ViewHolder class for holding the views for each list item.
     */

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView category, tv_limit,tv_progress,shw_total_percentage;
        ProgressBar progressBar;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            category = itemView.findViewById(R.id.tv_item_category);
            progressBar = itemView.findViewById(R.id.progress_limit);
            tv_limit = itemView.findViewById(R.id.tv_limit);
            tv_progress = itemView.findViewById(R.id.tv_progress);
            shw_total_percentage = itemView.findViewById(R.id.shw_total_percentage);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //Initialize the dialog
                    Dialog dialog = new Dialog(context);
                    // Set background transparent
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    //setint view
                    dialog.setContentView(R.layout.dialog_main_for_goal_set);
                    dialog.setCanceledOnTouchOutside(true);
                    //Displaying Dialog
                    dialog.show();

                    EditText feedback_txt = dialog.findViewById(R.id.feedback_in_review_dialog);
                    Button btSubmit = dialog.findViewById(R.id.sending_feedback_btn_review_dialog);
                    feedback_txt.requestFocus();

                    btSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (feedback_txt.getText().toString().trim().length() != 0) {
                                dialog.dismiss();

                                String key = arrayList.get(getAdapterPosition()).category.toString();  // key = Salary
                                SharedPreferences spp = context.getSharedPreferences(Key_SharedPreference.PREFS_KEY,Context.MODE_PRIVATE);
                                spp.edit().putInt("LIMIT_" + key.toUpperCase(),Integer.valueOf(feedback_txt.getText().toString().trim())).apply(); //KEY = "LIMIT_" + key.toUpperCase() = LIMIT_SALARY


                                ((TextView)(itemView.findViewById(R.id.tv_limit))).setText(feedback_txt.getText().toString().trim());

                                progressBar.setMax(100);
                                if (Integer.valueOf(arrayList.get(getAdapterPosition()).progress_expense) == 0) {
                                    Log.d("Runtime_change", "onClick: 1");
                                    ((ProgressBar)(itemView.findViewById(R.id.progress_limit))).setProgress(0);
                                    ((TextView)(itemView.findViewById(R.id.shw_total_percentage))).setText("Filled : 0%");
                                } else {
                                    Log.d("Runtime_change", "onClick: 2");
                                    int temp = (int) ((Double.valueOf(arrayList.get(getAdapterPosition()).progress_expense) / Integer.valueOf(feedback_txt.getText().toString().trim())) * 100);
                                    Log.d("Checking Progress value", "onBindViewHolder: " + temp +" ; "+arrayList.get(getAdapterPosition()).progress_expense+" ; "+Integer.valueOf(feedback_txt.getText().toString().trim()));
                                    ((ProgressBar)(itemView.findViewById(R.id.progress_limit))).setProgress(temp);

                                    ((TextView)(itemView.findViewById(R.id.shw_total_percentage))).setText("Filled : "+String.valueOf(temp)+"%");
                                }




                            }else {
                                Toast.makeText(context, "Give The Limit !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dialog.findViewById(R.id.do_not_send_feedback).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    Toast.makeText(context, arrayList.get(getAdapterPosition()).category.toString() + "", Toast.LENGTH_SHORT).show();

                }
            });
        }


    }


}
