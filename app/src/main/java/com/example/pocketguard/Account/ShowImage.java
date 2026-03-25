package com.example.pocketguard.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.pocketguard.Key_SharedPreference;
import com.example.pocketguard.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ShowImage extends AppCompatActivity {
    private StorageReference storageReference;
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private List<String> imageUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        imageUrls = new ArrayList<>();
        imageAdapter = new ImageAdapter(imageUrls);
        recyclerView.setAdapter(imageAdapter);

        storageReference = FirebaseStorage.getInstance().getReference();

        // Fetch user key and list images
        getUserKey(new UserKeyCallback() {
            @Override
            public void onUserKeyRetrieved(String userKey) {
                if (userKey != null) {
                    listImages(userKey);
                } else {
                    Toast.makeText(ShowImage.this, "Failed to retrieve user key", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

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

    private void listImages(String userKey) {
        StorageReference userImagesRef = storageReference.child("images/" + userKey);

        userImagesRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item : listResult.getItems()) {
                    item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageUrls.add(uri.toString());
                            imageAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ShowImage.this, "Failed to list images: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
