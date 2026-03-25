package com.example.pocketguard;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.pocketguard.Database.Sqlite_DatabaseSource;
import com.example.pocketguard.Database.Sqlite_Model;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jsibbold.zoomage.ZoomageView;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date; 
import java.util.UUID; 

public class AddExpenceIncomeActivity extends AppCompatActivity {

    boolean flag_income_page = true;
    boolean flag_expense_page = false;
    boolean FLAG_CAMERA_CAPTURED = false;
    boolean FLAG_GALLERY_TAKEN = false;


    TextView tv_date, tv_method, tv_category;
    EditText et_amount, et_additional_data;
    LinearLayout layout_category, layout_method, layout_date;
    ZoomageView imageView;

    Sqlite_DatabaseSource sqliteDatabaseSource;
    Calendar calendar;
    Date date;
    RadioGroup radioGroup;
    RadioButton rb_income, rb_expense;

    Sqlite_Model studentModel;

    ImageView objectImageView;
    private static final int PICK_IMAGE_REQUEST = 111;
    private static final int CAMERA_CAPTURE_REQUEST = 211;
    private static final int CAMERA_PERMISSION_REQUEST = 311;
    private Uri imageFilePath_uri;
    private Bitmap imageToStore;

    //This date ensures Perfect Insert to Database
    int amount = 0;
    String category_check = "", method_check = "", additional_data = "";

    //Storage
    // Initialize FirebaseStorage
    private FirebaseStorage storage;
    private StorageReference storageReference;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.toolbar_in_add_incomeexpence);

        // Initialize FirebaseStorage instance
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_income_expense);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        sqliteDatabaseSource = new Sqlite_DatabaseSource(this);

        tv_date = findViewById(R.id.date_tv);
        et_additional_data = findViewById(R.id.note_et);
        radioGroup = findViewById(R.id.rg_switch);
        et_amount = findViewById(R.id.amount_et);
        layout_category = findViewById(R.id.category_layout);
        layout_date = findViewById(R.id.date_layout);
        layout_method = findViewById(R.id.method_layout);
        tv_category = findViewById(R.id.category_tv);
        tv_method = findViewById(R.id.method_tv);
        rb_income = findViewById(R.id.rb_income);
        rb_expense = findViewById(R.id.rb_expense);


        studentModel = (Sqlite_Model) getIntent().getSerializableExtra("Plaban-111");
        if (studentModel != null) {
            //Initialized null values with student model   because sometimes user some can't be updated
            amount = studentModel.getAmount();
            date = studentModel.getDate();
            category_check = studentModel.getCategory();
            method_check = studentModel.getMethod();
            additional_data = studentModel.getAdditional_note();


            if (studentModel.getStatus().equals(Key_Static.INCOME)) {

                flag_income_page = true;
                flag_expense_page = false;

                rb_expense.setVisibility(View.INVISIBLE);

                ViewGroup.LayoutParams params = rb_income.getLayoutParams();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;

                rb_income.setLayoutParams(params);

                //Setting Previous Value
                et_amount.setText(String.valueOf(studentModel.getAmount()));
                tv_date.setText(MyUtils.sdfUser.format(studentModel.getDate()));
                tv_category.setText(studentModel.getCategory());
                tv_method.setText(studentModel.getMethod());
                et_additional_data.setText(studentModel.getAdditional_note());
            } else {
                flag_income_page = false;
                flag_expense_page = true;

                rb_income.setVisibility(View.INVISIBLE);
                rb_expense.setChecked(true);

                ViewGroup.LayoutParams params = rb_expense.getLayoutParams();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                ;
                rb_expense.setLayoutParams(params);

                //Setting Previous Value
                et_amount.setText(String.valueOf(studentModel.getAmount()));
                tv_date.setText(MyUtils.sdfUser.format(studentModel.getDate()));
                tv_category.setText(studentModel.getCategory());
                tv_method.setText(studentModel.getMethod());
                et_additional_data.setText(studentModel.getAdditional_note());
            }

        }


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint({"ResourceType"})
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_income) {
                    flag_income_page = true;
                    flag_expense_page = false;
                    for_category();

                    AddExpenceIncomeActivity.this.tv_category.setText("Select Category");
                    AddExpenceIncomeActivity.this.tv_method.setText("Select Method");
                    AddExpenceIncomeActivity.this.et_additional_data.setText("");
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_expense) {
                    flag_income_page = false;
                    flag_expense_page = true;

                    AddExpenceIncomeActivity.this.tv_category.setText("Select Category");
                    AddExpenceIncomeActivity.this.tv_method.setText("Select Method");
                    AddExpenceIncomeActivity.this.et_additional_data.setText("");
                }
            }
        });


        Calendar instance = Calendar.getInstance();
        this.calendar = instance;
        instance.set(11, 0);
        this.calendar.clear(12);
        this.calendar.clear(13);
        this.calendar.clear(14);


        setting_back_button();
        for_category();
        for_method();
        for_date();
        for_image_bill();
    }

//    private String getUserKey(){
//        String keyStr =null;
//        SharedPreferences spp = getSharedPreferences(Key_SharedPreference.PREFS_KEY,MODE_PRIVATE);
//        String account_name = spp.getString(Key_SharedPreference.ACCOUNT_NAME,"");
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                try {
//                    for(DataSnapshot snap:snapshot.getChildren()){
//
//                        if(snap.child("email").getValue(String.class).equals(account_name)){
//                            keyStr.concat(snap.getKey());
//                        }
//
//                    }
//
//
//                } catch (NullPointerException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        Toast.makeText(AddExpenceIncomeActivity.this,keyStr, Toast.LENGTH_LONG).show();
//        return keyStr;
//    }

    public interface UserKeyCallback {
        void onUserKeyRetrieved(String userKey);
    }
    public void getUserKey(final UserKeyCallback callback) {
        SharedPreferences spp = getSharedPreferences(Key_SharedPreference.PREFS_KEY, MODE_PRIVATE);
        String account_name = spp.getString(Key_SharedPreference.ACCOUNT_NAME, "");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String keyStr = null;
                try {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        if (snap.child("email").getValue(String.class).equals(account_name)) {
                            keyStr = snap.getKey();
                            break;  // Exit loop once the key is found
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                // Call the callback with the result
                callback.onUserKeyRetrieved(keyStr);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }


    private void uploadImageToFirebase(Bitmap imageBitmap) {
        getUserKey(new UserKeyCallback() {
            @Override
            public void onUserKeyRetrieved(String userKey) {
                // Create a storage reference
                StorageReference imageRef = storageReference.child("images/" + userKey + "/" + UUID.randomUUID().toString() + ".jpg");

                // Convert bitmap to byte array
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                // Upload image to Firebase Storage
                UploadTask uploadTask = imageRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle unsuccessful uploads
                        Toast.makeText(AddExpenceIncomeActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Handle successful uploads
                        Toast.makeText(AddExpenceIncomeActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();

                        // Get the download URL for the image
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Do something with the download URL (e.g., save to database)
                                String imageUrl = uri.toString();
                                // Now you can save this URL to your database along with other data related to the image
                            }
                        });
                    }
                });
            }
        });
    }


    //For Taking Invoice Image
    public void for_image_bill() {
        objectImageView = findViewById(R.id.image_bill_transaction);
        this.objectImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddExpenceIncomeActivity.this);
                builder.setTitle(AddExpenceIncomeActivity.this.getString(R.string.receipts));
                builder.setCancelable(true);
                View inflate = AddExpenceIncomeActivity.this.getLayoutInflater().inflate(R.layout.dialog_image, (ViewGroup) null);
                builder.setView(inflate);
                imageView = (ZoomageView) inflate.findViewById(R.id.my_image);

                if (FLAG_GALLERY_TAKEN || FLAG_CAMERA_CAPTURED) {
                    imageView.setImageBitmap(imageToStore);
                } else {
                    imageView.setImageResource(R.drawable.camera);
                }


                builder.setNegativeButton(AddExpenceIncomeActivity.this.getString(R.string.change), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i4) {

                        showOptions();

                    }
                });
                builder.show();
            }
        });

    }

    public void showOptions() {


        View view = LayoutInflater.from(this).inflate(R.layout.selectiomoption, null);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AddExpenceIncomeActivity.this);
        builder.setView(view);
        Dialog dialog = builder.create();

        CardView cv_camera = view.findViewById(R.id.cv_camera);
        CardView cv_gallery = view.findViewById(R.id.cv_gallery);


        if (!isFinishing() && dialog != null) {
            dialog.show();
        }

        cv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                //Taking From Camera
                camera_permission();
            }
        });
        cv_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                chooseImage_GALLERY();
            }
        });
        dialog.setCancelable(true);

        dialog.show();
    }


    public void camera_permission(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST);
            Log.d("camera_permission", "camera_permission: not ok");
        } else {
            chooseImage_CAMERA();
            Log.d("camera_permission", "camera_permission: ok");
        }

    }
    @Override //Permission done listener
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                chooseImage_CAMERA();
            } else {
                // Permission denied, handle this case (e.g., show a message)
                Toast.makeText(this, "Permission Taking Failed !!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void chooseImage_GALLERY() {
        try {
            objectImageView = findViewById(R.id.image_bill_transaction);

            Intent objectIntent = new Intent();
            objectIntent.setType("image/*");

            objectIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(objectIntent, PICK_IMAGE_REQUEST);

        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void chooseImage_CAMERA() {
        try {
            objectImageView = findViewById(R.id.image_bill_transaction);

            Intent cameraIntent = new Intent();
            cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_CAPTURE_REQUEST);

        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                FLAG_CAMERA_CAPTURED = false;
                FLAG_GALLERY_TAKEN = true;

                imageFilePath_uri = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), imageFilePath_uri);

                objectImageView.setImageBitmap(imageToStore);
            } else if (requestCode == CAMERA_CAPTURE_REQUEST && resultCode == RESULT_OK) {
                FLAG_CAMERA_CAPTURED = true;
                FLAG_GALLERY_TAKEN = false;

                Bundle extras = data.getExtras();
                imageToStore = (Bitmap) extras.get("data");
                objectImageView.setImageBitmap(imageToStore);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Expection", Toast.LENGTH_SHORT).show();
        }

    }
    //For Taking Invoice Image

    public void for_date() {
        layout_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Showing Flow", "onCreateDialog: Button Clicked");
                AddExpenceIncomeActivity.this.showDialog(0);
            }
        });
    }

    public void for_category() {
        layout_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag_income_page == true) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(AddExpenceIncomeActivity.this);
                    builder.setTitle(getString(R.string.select_category));
                    builder.setItems(MyUtils.Category_TYPES_Income, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String str = MyUtils.Category_TYPES_Income[i];
                            category_check = str;
                            AddExpenceIncomeActivity.this.tv_category.setText(str);
                        }
                    });
                    builder.create().show();

                } else if (flag_expense_page == true) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(AddExpenceIncomeActivity.this);
                    builder.setTitle(getString(R.string.select_category));
                    builder.setItems(MyUtils.Category_TYPES_Expense, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String str = MyUtils.Category_TYPES_Expense[i];
                            category_check = str;
                            AddExpenceIncomeActivity.this.tv_category.setText(str);
                        }
                    });
                    builder.create().show();

                }


            }
        });
    }

    public void for_method() {
        layout_method.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AddExpenceIncomeActivity.this);
                builder.setTitle(getString(R.string.select_method));
                builder.setItems(MyUtils.Methods_TYPES, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String str = MyUtils.Methods_TYPES[i];
                        method_check = str;
                        AddExpenceIncomeActivity.this.tv_method.setText(str);
                    }
                });
                builder.create().show();

            }
        });
    }

    @Deprecated
    public Dialog onCreateDialog(int i, Bundle bundle) {

        //eta muloto id er upor depend kora dialog creat korsa
        Log.d("Showing Flow", "onCreateDialog: Date Picker Deprecated");
        if (i == 0) {
            Log.d("Showing Flow", "onCreateDialog: Date Picker Deprecated i=0");
            new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datePicker, int i2, int i3, int i4) {

                    AddExpenceIncomeActivity.this.calendar.set(i2, i3, i4);
                    Log.d("Showing Flow", "onDateSet: " + i2 + " " + i3 + " " + i4);
                    try {
                        date = MyUtils.sdfUser.parse(i2 + "-" + i3 + "-" + i4);
                        Log.d("Showing Flow", "onDateSet: " + date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    AddExpenceIncomeActivity.this.B();
                }
            }, this.calendar.get(1), this.calendar.get(2), this.calendar.get(5)).show();
            Log.d("Showing Flow", "onCreateDialog: Date Picker Deprecated i=0");

        }
        return super.onCreateDialog(i);
    }

    @Override
    protected void onDestroy() {
        Log.d("on destroy testing", "onDestroy: 1");
        super.onDestroy();
    }

    public void B() {
        this.tv_date.setText(MyUtils.sdfUser.format(this.calendar.getTime()));
    }

    @SuppressLint("LongLogTag")
    public void saving_data_to_database() {
        try {
            amount = Integer.parseInt(et_amount.getText().toString());
            additional_data = et_additional_data.getText().toString();

            if (date == null) {
                Toast.makeText(this, "Choose Date", Toast.LENGTH_SHORT).show();
            } else if (category_check == "") {
                Toast.makeText(this, "Select Category", Toast.LENGTH_SHORT).show();
            } else if (method_check == "") {
                Toast.makeText(this, "What's Method", Toast.LENGTH_SHORT).show();
            }else if(imageToStore == null){
                Toast.makeText(this, "Select Image", Toast.LENGTH_SHORT).show();
            } else {
                uploadImageToFirebase(imageToStore);
                if (flag_income_page == true) {
                    SharedPreferences spp = getSharedPreferences(Key_SharedPreference.PREFS_KEY,MODE_PRIVATE);
                    int percentage = (int) (((Float.valueOf(spp.getInt("PROGRESS_"+category_check.toUpperCase(),0) + amount)) / (spp.getInt("LIMIT_"+category_check.toUpperCase(),0)))*100.0);

                    int value = spp.getInt("LIMIT_"+category_check.toUpperCase(),0) - (spp.getInt("PROGRESS_"+category_check.toUpperCase(),0) + amount);
                    if(value >= 0){

                        if(percentage >50 && percentage <100){
                            Key_Static_Methods.creating_custom_notification(AddExpenceIncomeActivity.this,"Your "+category_check+" "+percentage+"%"+" Filled","Please Increase Limit For Further Insertion in this Category");
                        }

                        Sqlite_Model studentModel = new Sqlite_Model(amount, Key_Static.INCOME, category_check, method_check, date, additional_data);
                        Boolean status = sqliteDatabaseSource.add_Record(studentModel);

                        Log.d("Income_expense_to_sharedPreference", "Category ="+"PROGRESS_"+category_check.toUpperCase()+" amount ="+amount);
                        Log.d("Income_expense_to_sharedPreference", "percentage : "+percentage);

                        int final_amount = spp.getInt("PROGRESS_"+studentModel.getCategory().toUpperCase(),0) + amount ;
                        spp.edit().putInt("PROGRESS_"+category_check.toUpperCase() ,final_amount).apply();
                        setting_value_null_for_taking_another_record();

                        if (status) {
                            Toast.makeText(AddExpenceIncomeActivity.this, "Saved To Database", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddExpenceIncomeActivity.this, "Not Saved", Toast.LENGTH_SHORT).show();
                        }


                    }else {
                        Key_Static_Methods.limit_exiting_notice(this);
                    }


                } else if (flag_expense_page == true) {
                    SharedPreferences spp = getSharedPreferences(Key_SharedPreference.PREFS_KEY,MODE_PRIVATE);
                    int percentage = (int) (((Float.valueOf(spp.getInt("PROGRESS_"+category_check.toUpperCase(),0) + amount)) / (spp.getInt("LIMIT_"+category_check.toUpperCase(),0)))*100.0);

                    int value = spp.getInt("LIMIT_"+category_check.toUpperCase(),0) - (spp.getInt("PROGRESS_"+category_check.toUpperCase(),0) + amount);
                    if(value >= 0){

                        if(percentage >50 && percentage <100){
                            Key_Static_Methods.creating_custom_notification(AddExpenceIncomeActivity.this,"Your "+category_check+" "+percentage+"%"+" Filled","Please Increase Limit For Further Insertion in this Category");
                        }

                        Sqlite_Model studentModel = new Sqlite_Model(amount, Key_Static.EXPENSE, category_check, method_check, date, additional_data);
                        Boolean status = sqliteDatabaseSource.add_Record(studentModel);

                        Log.d("Income_expense_to_sharedPreference", "Category ="+"PROGRESS_"+category_check.toUpperCase()+" amount ="+amount);
                        int final_amount = spp.getInt("PROGRESS_"+studentModel.getCategory().toUpperCase(),0) + amount ;
                        spp.edit().putInt("PROGRESS_"+category_check.toUpperCase() ,final_amount).apply();

                        setting_value_null_for_taking_another_record();

                        if (status) {
                            Toast.makeText(AddExpenceIncomeActivity.this, "Saved To Database", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddExpenceIncomeActivity.this, "Not Saved", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Key_Static_Methods.limit_exiting_notice(this);
                    }

                }
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Give The Amount Properly", Toast.LENGTH_SHORT).show();
        }
    }

    public void setting_value_null_for_taking_another_record() {

        amount = 0;
        date = null;
        category_check = "";
        method_check = "";
        additional_data = "";

        et_amount.setText("");
        tv_date.setText("Set Date : 2023-02-14");
        tv_category.setText("Select Category");
        tv_method.setText("Select Method");
        et_additional_data.setText("");
    }

    //Back Button
    public void setting_back_button() {
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_income_expense, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.option_done) {

            if (studentModel != null) {

                try {
                    amount = Integer.parseInt(et_amount.getText().toString());
                    additional_data = et_additional_data.getText().toString();

                    if (date == null) {
                        Toast.makeText(this, "Choose Date", Toast.LENGTH_SHORT).show();
                    } else if (category_check == "") {
                        Toast.makeText(this, "Select Category", Toast.LENGTH_SHORT).show();
                    } else if (method_check == "") {
                        Toast.makeText(this, "What's Method", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences spp = getSharedPreferences(Key_SharedPreference.PREFS_KEY,MODE_PRIVATE);
                        int percentage = (int) (((Float.valueOf(spp.getInt("PROGRESS_"+category_check.toUpperCase(),0) + amount)) / (spp.getInt("LIMIT_"+category_check.toUpperCase(),0)))*100.0);

                        int value = spp.getInt("LIMIT_"+category_check.toUpperCase(),0) - (spp.getInt("PROGRESS_"+category_check.toUpperCase(),0) + amount);
                        if(value >= 0){

                            if(percentage >50 && percentage <100){
                                Key_Static_Methods.creating_custom_notification(AddExpenceIncomeActivity.this,"Your "+category_check+" "+percentage+"%"+" Filled","Please Increase Limit For Further Insertion in this Category");
                            }

                            String status = studentModel.getStatus();
                            int id = studentModel.getId();
                            Sqlite_Model studentModel = new Sqlite_Model(id, amount, status, category_check, method_check, date, additional_data);

                            Boolean flag = sqliteDatabaseSource.update_Record(studentModel);
                            // Ekhaner goal er update er code ta sqlite update function er modda
                            if (flag) {
                                Toast.makeText(AddExpenceIncomeActivity.this, "Database Updated", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AddExpenceIncomeActivity.this, "Update Failed", Toast.LENGTH_SHORT).show();
                            }

                        }else {
                            Key_Static_Methods.limit_exiting_notice(this);
                        }
                    }

                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Give The Amount Properly", Toast.LENGTH_SHORT).show();
                }
            } else {
                saving_data_to_database();
            }


        }
        return super.onOptionsItemSelected(menuItem);
    }
}