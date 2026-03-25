package com.example.pocketguard.WebPages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.pocketguard.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * Adapter class for displaying Firebase notifications in a ListView.
 *
 * <p>This class demonstrates the use of the Adapter design pattern.
 * It acts as a bridge between the data set (list of notifications) and the ListView UI component,
 * converting each data item into a view that can be displayed in the ListView.</p>
 */
public class Firebase_Notifications_Adapter_List extends ArrayAdapter {



    private Context context;
    private boolean isDark=false;

    private List<Firebase_Notifications_List> feedbacks;
    /**
     * Constructor for Firebase_Notifications_Adapter_List.
     *
     * @param context The current context.
     * @param arrayList The list of Firebase notifications to display.
     */
    public Firebase_Notifications_Adapter_List(@NonNull Context context, List<Firebase_Notifications_List>  arrayList) {
        super(context, R.layout.firebase_notifications_single_row, arrayList);

        this.context = context;
        this.feedbacks = arrayList;

    }
    /**
     * Provides a view for an adapter view (ListView, GridView, etc.)
     *
     * @param position The position of the data item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent The parent view that this view will be attached to.
     * @return A View corresponding to the data at the specified position.
     */

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {    //ati only arta row ka return kora

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.firebase_notifications_single_row, parent,false);

        Firebase_Notifications_List notifications  = feedbacks.get(position);

        ImageView imageView = convertView.findViewById(R.id.image_View_ID_notification);

        TextView tv1 = convertView.findViewById(R.id.name_notification);
        TextView tv2 = convertView.findViewById(R.id.address_notification);
        TextView tv3 = convertView.findViewById(R.id.notification_date_time);
        LinearLayout linearLayout = convertView.findViewById(R.id.container_single_notification_item);

        tv1.setText(notifications.getUsername());
        tv2.setText(notifications.getLink());
        tv3.setText(notifications.getDate());


        return convertView ;
    }
}
