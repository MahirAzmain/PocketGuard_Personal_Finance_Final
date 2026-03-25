package com.example.pocketguard.GoalSet;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.pocketguard.Key_SharedPreference;
import com.example.pocketguard.Key_Static_Methods;
import com.example.pocketguard.R;

import java.util.ArrayList;
/**
 * Activity class for displaying goal setting data in multiple RecyclerViews.
 *
 * <p>This class demonstrates the use of several design patterns, including the Adapter pattern
 * (through the use of GoalAdapter), Singleton pattern (for SharedPreferences), and the Factory Method pattern
 * (through the Key_Static_Methods class to retrieve goal information).</p>
 */
public class GoalRecyclerView extends AppCompatActivity {

    RecyclerView recyclerView_income,recyclerView_expense ;
    GoalAdapter goalAdapter1,goalAdapter2;
    SharedPreferences spp;

    String category;
    int progress,limit;

    ArrayList<String> list;
    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);

        recyclerView_income = findViewById(R.id.recyclerView_income);
        recyclerView_expense = findViewById(R.id.recyclerView_expense);

        //adapter muloto akta context ar akta arraylist cai..

        ArrayList<constructor> arrayList1 = new ArrayList<>();
        ArrayList<constructor> arrayList2 = new ArrayList<>();

        spp = getSharedPreferences(Key_SharedPreference.PREFS_KEY,MODE_PRIVATE);
        //String category = spp.getString(Key_SharedPreference.CATEGORY,"");
        //int progress = spp.getInt(Key_SharedPreference.PROGRESS,0);
        //int limit = spp.getInt(Key_SharedPreference.LIMIT,1);

        //runtime a constructor class ar akta object creat kora hoyacha.
/*        arrayList.add(new constructor("Income",399,400)) ;
        arrayList.add(new constructor("Expense",29,30)) ;
        arrayList.add(new constructor("Income",40,50)) ;
        arrayList.add(new constructor("Income",41,50)) ;*/

        making_array_list_from_SharedPreferences(arrayList1,arrayList2);

        goalAdapter1 = new GoalAdapter(GoalRecyclerView.this,arrayList1);
        goalAdapter2 = new GoalAdapter(GoalRecyclerView.this,arrayList2);

        //nica Recycler view ar tin doraner LayoutManager dakano hoyacha

        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true);
        //recyclerView.setLayoutManager(linearLayoutManager);

        //GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,true);
        //recyclerView.setLayoutManager(gridLayoutManager);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView_income.setLayoutManager(staggeredGridLayoutManager);
        recyclerView_income.setAdapter(goalAdapter1);

        StaggeredGridLayoutManager staggeredGridLayoutManager1 = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView_expense.setLayoutManager(staggeredGridLayoutManager1);
        recyclerView_expense.setAdapter(goalAdapter2);
    }
    /**
     * Populates the given ArrayLists with goal information retrieved from SharedPreferences.
     *
     * @param arrayList1 the list to be populated with income goal information
     * @param arrayList2 the list to be populated with expense goal information
     */

    public void making_array_list_from_SharedPreferences(ArrayList<constructor> arrayList1, ArrayList<constructor> arrayList2){

        list = Key_Static_Methods.get_goal_information(GoalRecyclerView.this,Key_SharedPreference.SALARY);
        arrayList1.add(new constructor(list.get(0),Integer.valueOf(list.get(1)),Integer.valueOf(list.get(2))));

        list = Key_Static_Methods.get_goal_information(GoalRecyclerView.this,Key_SharedPreference.SAVINGS);
        arrayList1.add(new constructor(list.get(0),Integer.valueOf(list.get(1)),Integer.valueOf(list.get(2))));

        list = Key_Static_Methods.get_goal_information(GoalRecyclerView.this,Key_SharedPreference.INCOME);
        arrayList1.add(new constructor(list.get(0),Integer.valueOf(list.get(1)),Integer.valueOf(list.get(2))));

        list = Key_Static_Methods.get_goal_information(GoalRecyclerView.this,Key_SharedPreference.OTHERS_INCOME);
        arrayList1.add(new constructor(list.get(0),Integer.valueOf(list.get(1)),Integer.valueOf(list.get(2))));

        //"Investment", "Travel", "Food & Drinks", "Entertainment", "Shopping", "Housing", "Vehicles", "Transportation", "Communication", "Others"

        list = Key_Static_Methods.get_goal_information(GoalRecyclerView.this,Key_SharedPreference.INVESTMENT);
        arrayList2.add(new constructor(list.get(0),Integer.valueOf(list.get(1)),Integer.valueOf(list.get(2))));

        list = Key_Static_Methods.get_goal_information(GoalRecyclerView.this,Key_SharedPreference.TRAVEL);
        arrayList2.add(new constructor(list.get(0),Integer.valueOf(list.get(1)),Integer.valueOf(list.get(2))));

        list = Key_Static_Methods.get_goal_information(GoalRecyclerView.this,Key_SharedPreference.FOOD_DRINKS);
        arrayList2.add(new constructor(list.get(0),Integer.valueOf(list.get(1)),Integer.valueOf(list.get(2))));

        list = Key_Static_Methods.get_goal_information(GoalRecyclerView.this,Key_SharedPreference.ENTERTAINMENT);
        arrayList2.add(new constructor(list.get(0),Integer.valueOf(list.get(1)),Integer.valueOf(list.get(2))));

        list = Key_Static_Methods.get_goal_information(GoalRecyclerView.this,Key_SharedPreference.SHOPPING);
        arrayList2.add(new constructor(list.get(0),Integer.valueOf(list.get(1)),Integer.valueOf(list.get(2))));

        list = Key_Static_Methods.get_goal_information(GoalRecyclerView.this,Key_SharedPreference.HOUSING);
        arrayList2.add(new constructor(list.get(0),Integer.valueOf(list.get(1)),Integer.valueOf(list.get(2))));

        list = Key_Static_Methods.get_goal_information(GoalRecyclerView.this,Key_SharedPreference.VEHICLES);
        arrayList2.add(new constructor(list.get(0),Integer.valueOf(list.get(1)),Integer.valueOf(list.get(2))));

        list = Key_Static_Methods.get_goal_information(GoalRecyclerView.this,Key_SharedPreference.TRANSPORTATION);
        arrayList2.add(new constructor(list.get(0),Integer.valueOf(list.get(1)),Integer.valueOf(list.get(2))));

        list = Key_Static_Methods.get_goal_information(GoalRecyclerView.this,Key_SharedPreference.COMMUNICATION);
        arrayList2.add(new constructor(list.get(0),Integer.valueOf(list.get(1)),Integer.valueOf(list.get(2))));

        list = Key_Static_Methods.get_goal_information(GoalRecyclerView.this,Key_SharedPreference.OTHERS_EXPENSE);
        arrayList2.add(new constructor(list.get(0),Integer.valueOf(list.get(1)),Integer.valueOf(list.get(2))));
    }

}