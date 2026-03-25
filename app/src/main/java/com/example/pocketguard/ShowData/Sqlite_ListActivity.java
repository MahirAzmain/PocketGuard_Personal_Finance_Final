package com.example.pocketguard.ShowData;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pocketguard.AddExpenceIncomeActivity;
import com.example.pocketguard.Database.Sqlite_DatabaseSource;
import com.example.pocketguard.Database.Sqlite_Model;
import com.example.pocketguard.Key_SharedPreference;
import com.example.pocketguard.Key_Static;
import com.example.pocketguard.R;

import java.text.ParseException;
import java.util.ArrayList;
/**
 * Activity for displaying SQLite data in a ListView.
 *
 * <p><b>Design Patterns Used:</b></p>
 * <ul>
 *     <li><b>Observer:</b> The observer pattern is used to handle item clicks in the ListView.</li>
 *     <li><b>Adapter:</b> This activity uses an adapter pattern through the usage of {@link Sqlite_Adapter} to adapt SQLite data to the ListView.</li>
 *     <li><b>Singleton:</b> SharedPreferences (spp) is used as a Singleton pattern to store key-value pairs.</li>
 *     <li><b>Builder:</b> Dialog creation uses the builder pattern through chained method calls to configure the dialog.</li>
 * </ul>
 */

public class Sqlite_ListActivity extends AppCompatActivity {

    Sqlite_DatabaseSource source;
    ListView listView;
    ArrayList<Sqlite_Model> arrayList;
    Sqlite_Adapter sqliteAdapter;
    ImageView imageView;
    SharedPreferences spp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_header_show_items);

        spp = getSharedPreferences(Key_SharedPreference.PREFS_KEY,MODE_PRIVATE);

        listView = findViewById(R.id.ListView);
        imageView = findViewById(R.id.prev_page);

        source = new Sqlite_DatabaseSource(this);

        set_in_listView();

        registerForContextMenu(listView); //delete operation

        image_previous_button();
    }

    /**
     * Sets up the ListView based on the selected list type (balance, income, expense).
     */
    public void set_in_listView() {
        if (Key_Static.BALANCE_LIST) {
            try {
                ((TextView)findViewById(R.id.current_sum)).setText(String.valueOf("Total = "+spp.getInt(Key_SharedPreference.TOTAL_BALANCE,0)));

                arrayList = source.getAllItems();
                sqliteAdapter = new Sqlite_Adapter(Sqlite_ListActivity.this, source.getAllItems());
                listView.setAdapter(sqliteAdapter);
            } catch (NullPointerException e) {
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (Key_Static.INCOME_LIST) {
            try {
                ((TextView)findViewById(R.id.current_sum)).setText(String.valueOf("Total = "+spp.getInt(Key_SharedPreference.TOTAL_INCOME,0)));

                arrayList = source.getAllItems();
                sqliteAdapter = new Sqlite_Adapter(Sqlite_ListActivity.this, source.get_income_transaction());
                listView.setAdapter(sqliteAdapter);
            } catch (NullPointerException e) {

            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (Key_Static.EXPENSE_LIST) {
            try {
                ((TextView)findViewById(R.id.current_sum)).setText(String.valueOf("Total = "+spp.getInt(Key_SharedPreference.TOTAL_EXPENSE,0)));

                arrayList = source.getAllItems();
                sqliteAdapter = new Sqlite_Adapter(Sqlite_ListActivity.this, source.get_expense_transaction());
                listView.setAdapter(sqliteAdapter);
            } catch (NullPointerException e) {

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(Key_Static.BALANCE_LIST){
                    Toast.makeText(Sqlite_ListActivity.this, "Welcome To Edit Panel", Toast.LENGTH_SHORT).show();
                    Sqlite_Model sqliteModel = arrayList.get(position);
                    Intent intent = new Intent(Sqlite_ListActivity.this, AddExpenceIncomeActivity.class);
                    intent.putExtra("Plaban-111", sqliteModel);
                    startActivity(intent);
                }else {

                    Dialog dialog = new Dialog(Sqlite_ListActivity.this);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setContentView(R.layout.dialog_edit_only_from_all_transaction);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.show();

                }

            }
        });
    }

    /**
     * Handles click events for the previous page image button.
     */
    public void image_previous_button() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onDestroy() {
        Key_Static.BALANCE_LIST = false;
        Key_Static.INCOME_LIST = false;
        Key_Static.EXPENSE_LIST = false;
        super.onDestroy();
    }

    @Override
    protected void onRestart() {

        set_in_listView();

        super.onRestart();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        //MenuInflater menuInflater = getMenuInflater();
        //menuInflater.inflate(R.menu.manu_delete_recond, menu);
        //menu.setHeaderTitle("Delete Record");

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getItemId() == R.id.delete_record) {
            Boolean status = source.delete_Record(arrayList.get(adapterContextMenuInfo.position));

            if (status) {

                set_in_listView();

                Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed To Delete", Toast.LENGTH_SHORT).show();
            }

        }
        return super.onContextItemSelected(item);
    }
}