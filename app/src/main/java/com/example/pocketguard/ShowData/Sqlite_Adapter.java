package com.example.pocketguard.ShowData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pocketguard.Database.Sqlite_Model;
import com.example.pocketguard.Key_Static;
import com.example.pocketguard.MyUtils;
import com.example.pocketguard.R;

import java.util.ArrayList;
/**
 * Adapter for displaying SQLite data in a ListView.
 *
 * <p><b>Design Patterns Used:</b></p>
 * <ul>
 *     <li><b>Adapter:</b> This class extends ArrayAdapter to adapt SQLite data to the ListView.</li>
 *     <li><b>ViewHolder:</b> The ViewHolder pattern is used to improve ListView performance by recycling views.</li>
 * </ul>
 */
public class Sqlite_Adapter extends ArrayAdapter<Sqlite_Model> {



    private Context context;

    private ArrayList<Sqlite_Model> arrayList;
    /**
     * Constructor for the Sqlite_Adapter.
     *
     * @param context The context.
     * @param arrayList The list of SQLite data.
     */

    public Sqlite_Adapter(@NonNull Context context, ArrayList<Sqlite_Model>  arrayList) {
        super(context, R.layout.sqlite_single_student_row, arrayList);

        this.context = context;
        this.arrayList = arrayList;

    }

    /**
     * Gets the view for each item in the ListView.
     *
     * @param position The position of the item in the array.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup.
     * @return The view for the item at the specified position.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Inflating the layout for the list item
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.list_item_manager, parent,false);


        // Retrieving references to views within the list item layout
        TextView amount_tv = v.findViewById(R.id.amount_item_single);
        TextView category_tv = v.findViewById(R.id.category_item_single);
        TextView method_tv = v.findViewById(R.id.method_item_single);
        TextView date_tv = v.findViewById(R.id.date_item_single);
        TextView note_tv = v.findViewById(R.id.note_item_single);
        ImageView icon_singel_item = v.findViewById(R.id.icon_single_item);
        // Extracting data from the ArrayList based on the item position
        String str = String.valueOf(arrayList.get(position).getStatus());

        category_tv.setText(String.valueOf(arrayList.get(position).getCategory()));
        method_tv.setText(arrayList.get(position).getMethod());
        date_tv.setText(MyUtils.sdfUser.format(arrayList.get(position).getDate()));
        note_tv.setText(arrayList.get(position).getAdditional_note());
        // Setting appropriate icon and amount text based on income or expense
        if(str.equals(Key_Static.INCOME)){
            icon_singel_item.setImageResource(R.drawable.income_icon);
            amount_tv.setText("+"+String.valueOf(arrayList.get(position).getAmount()));
        }else if(str.equals(Key_Static.EXPENSE)){
            icon_singel_item.setImageResource(R.drawable.expense_icon);
            amount_tv.setText("-"+String.valueOf(arrayList.get(position).getAmount()));
        }

        return v ;

    }
}
