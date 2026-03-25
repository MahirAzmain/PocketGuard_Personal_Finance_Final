package com.example.pocketguard.Bookmarks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pocketguard.R;

import java.util.ArrayList;
/**
 * Custom ArrayAdapter for displaying student data in a ListView.
 * This adapter uses the View Holder pattern to improve performance.
 */
public class Sqlite_StudentAdapter extends ArrayAdapter<Sqlite_StudentModel> {



    private Context context;
    private boolean isDark=false;
    RelativeLayout relativeLayout;

    private ArrayList<Sqlite_StudentModel> arrayList;

    /**
     * Constructor for Sqlite_StudentAdapter.
     *
     * @param context   The current context.
     * @param arrayList The list of student models to display.
     */
    public Sqlite_StudentAdapter(@NonNull Context context, ArrayList<Sqlite_StudentModel>  arrayList) {
        super(context, R.layout.sqlite_single_student_row, arrayList);

        this.context = context;
        this.arrayList = arrayList;

    }
    /**
     * Provides a view for an adapter.
     *
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent view that this view will be attached to.
     * @return A View corresponding to the data at the specified position.
     */

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {    //ati only arta row ka return kora

        // Inflate a new view if this is not an existing view being reused
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.sqlite_single_student_row, parent,false);


        // Initialize and set the data to the views
        TextView nameTV = v.findViewById(R.id.name);
        //TextView ageTV = v.findViewById(R.id.age);
        TextView addressTV = v.findViewById(R.id.address);

        nameTV.setText(arrayList.get(position).getName());
        //ageTV.setText(String.valueOf(arrayList.get(position).getAge()));
        addressTV.setText(arrayList.get(position).getAddress());


        TextView tv1 = v.findViewById(R.id.name);
        TextView tv2 = v.findViewById(R.id.address);
        ImageView imageView = v.findViewById(R.id.image_View_ID);



        return v ;   //returning the view


    }
}
