package com.example.pocketguard.UnitTest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pocketguard.Account.Login;
import com.example.pocketguard.Account.Users;
import com.example.pocketguard.Account.CustomApplication;
import com.example.pocketguard.HomeActivity;
import com.example.pocketguard.Key_SharedPreference;
import com.example.pocketguard.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import org.robolectric.Robolectric;
import org.robolectric.shadows.ShadowLooper;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.Collections;

@RunWith(RobolectricTestRunner.class)
@Config(application = CustomApplication.class, sdk = {28}) // Adjust SDK version as needed
public class LoginTest {

    @Mock
    DatabaseReference mockedDatabaseReference;

    @Mock
    EditText et_email;

    @Mock
    EditText et_pass;

    Login login;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize the Login activity with Robolectric
        login = Robolectric.buildActivity(Login.class).create().get();

        // Inject mocks into the activity
        login.databaseReference = mockedDatabaseReference;
        login.et_email = et_email;
        login.et_pass = et_pass;

        // Mock the EditText fields
        when(et_email.getText()).thenReturn(new SpannableStringBuilder("choyan@gmail.com"));
        when(et_pass.getText()).thenReturn(new SpannableStringBuilder("12345"));
    }

    @Test
    public void testCodeForLogin_SuccessfulLogin() {
        // Mock DataSnapshot to have a user with correct email and password
        DataSnapshot snapshot = mock(DataSnapshot.class);
        DataSnapshot snap = mock(DataSnapshot.class);

        // Create a mock user object with the provided attributes
        Users user = new Users("choyan@gmail.com", "12345", "Choyan Barua", "02-05-2002", "20101", "20", "20", "0", "22.8977789", "89.5049115");

        // Mock the getValue method to return the mock user object
        when(snap.getValue(Users.class)).thenReturn(user);

        // Mock the getChildren method to return a list containing the mock childSnapshot
        when(snapshot.getChildren()).thenReturn(Collections.singletonList(snap));
        // Mock the EditText fields
        when(et_email.getText()).thenReturn(new SpannableStringBuilder("choyan@gmail.com"));
        when(et_pass.getText()).thenReturn(new SpannableStringBuilder("12345"));

        // Set up the behavior of addListenerForSingleValueEvent method to call onDataChange method with the mocked snapshot
        doAnswer(invocation -> {
            ValueEventListener valueEventListener = invocation.getArgument(0);
            valueEventListener.onDataChange(snapshot);
            return null;
        }).when(mockedDatabaseReference).addListenerForSingleValueEvent(any(ValueEventListener.class));

        // Call the method under test
        login.code_for_login();

        // Process any pending tasks in the main looper
        ShadowLooper mainLooper = Shadows.shadowOf(Looper.getMainLooper());
        mainLooper.runToEndOfTasks();

        // Check if flag_wrong_pass was correctly set to true for successful login
        Assert.assertTrue("Login should be successful", login.flag_wrong_pass);
    }

    @Test
    public void testCodeForLogin_FailureLogin() {
        // Mock DataSnapshot to have a user with correct email and password
        DataSnapshot snapshot = mock(DataSnapshot.class);
        DataSnapshot snap = mock(DataSnapshot.class);

        // Create a mock user object with the provided attributes
        Users user = new Users("choyan@gmail.com", "123456", "Choyan Barua", "02-05-2002", "20101", "20", "20", "0", "22.8977789", "89.5049115");

        // Mock the getValue method to return the mock user object
        when(snap.getValue(Users.class)).thenReturn(user);

        // Mock the getChildren method to return a list containing the mock childSnapshot
        when(snapshot.getChildren()).thenReturn(Collections.singletonList(snap));
        // Mock the EditText fields
        when(et_email.getText()).thenReturn(new SpannableStringBuilder("choyan@gmail.com"));
        when(et_pass.getText()).thenReturn(new SpannableStringBuilder("12345"));

        // Set up the behavior of addListenerForSingleValueEvent method to call onDataChange method with the mocked snapshot
        doAnswer(invocation -> {
            ValueEventListener valueEventListener = invocation.getArgument(0);
            valueEventListener.onDataChange(snapshot);
            return null;
        }).when(mockedDatabaseReference).addListenerForSingleValueEvent(any(ValueEventListener.class));

        // Call the method under test
        login.code_for_login();

        // Process any pending tasks in the main looper
        ShadowLooper mainLooper = Shadows.shadowOf(Looper.getMainLooper());
        mainLooper.runToEndOfTasks();

        // Check if flag_wrong_pass was correctly set to true for successful login
        Assert.assertFalse("Login should be successful", login.flag_wrong_pass);
    }


    @Test
    public void testCodeForLogin_EmptyFields() {
        // Mock the EditText fields to return empty text
        when(et_email.getText()).thenReturn(new SpannableStringBuilder(""));
        when(et_pass.getText()).thenReturn(new SpannableStringBuilder(""));

        // Mock dataSnapshot to have no children
        DataSnapshot snapshot = mock(DataSnapshot.class);
        when(snapshot.getChildren()).thenReturn(null);

        // Set up the behavior of addListenerForSingleValueEvent method to call onDataChange method with the mocked snapshot
        doAnswer(invocation -> {
            ValueEventListener valueEventListener = invocation.getArgument(0);
            valueEventListener.onDataChange(snapshot);
            return null;
        }).when(mockedDatabaseReference).addListenerForSingleValueEvent(any(ValueEventListener.class));

        // Call the method under test
        login.code_for_login();

        // Process any pending tasks in the main looper
        ShadowLooper mainLooper = Shadows.shadowOf(Looper.getMainLooper());
        mainLooper.runToEndOfTasks();

        // Assert that login was unsuccessful
        assertFalse(login.flag_wrong_pass);
    }
}
