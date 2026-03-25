package com.example.pocketguard.UnitTest;

import android.content.SharedPreferences;
import android.os.Looper;
import android.text.SpannableStringBuilder;
import android.widget.EditText;

import com.example.pocketguard.Account.CustomApplication;
import com.example.pocketguard.Account.Register;
import com.example.pocketguard.Account.Users;

import com.example.pocketguard.Key_SharedPreference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLooper;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import java.util.Collections;

@RunWith(RobolectricTestRunner.class)
@Config(application = CustomApplication.class, sdk = {28}) // Adjust SDK version as needed
public class RegisterTest {

    @Mock
    DatabaseReference mockedDatabaseReference;

    @Mock
    SharedPreferences sharedPreferences;

    @Mock
    SharedPreferences.Editor editor;

    @Mock
    EditText et_email;

    @Mock
    EditText et_pass;

    @Mock
    EditText et_name;

    @Mock
    EditText et_dob;

    @Mock
    EditText et_security;

    @Captor
    ArgumentCaptor<ValueEventListener> captor;

    private Register registerActivity;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize the Register activity with Robolectric
        registerActivity = Robolectric.buildActivity(Register.class).create().get();

        // Inject mocks into the activity
        registerActivity.db = mockedDatabaseReference;
        registerActivity.spp = sharedPreferences;
        registerActivity.et_email = et_email;
        registerActivity.et_pass = et_pass;
        registerActivity.et_name = et_name;
        registerActivity.et_dob = et_dob;
        registerActivity.et_security = et_security;

        // Mock SharedPreferences
        when(sharedPreferences.getString(Key_SharedPreference.ACCOUNT_NAME, "")).thenReturn("choyan@gmail.com");
        when(sharedPreferences.edit()).thenReturn(editor);

        // Mock EditText fields to return non-null values
        when(et_email.getText()).thenReturn(new SpannableStringBuilder("choyan@gmail.com"));
        when(et_pass.getText()).thenReturn(new SpannableStringBuilder("12345"));
        when(et_name.getText()).thenReturn(new SpannableStringBuilder("Choyan Barua"));
        when(et_dob.getText()).thenReturn(new SpannableStringBuilder("02-05-2002"));
        when(et_security.getText()).thenReturn(new SpannableStringBuilder("20101"));
    }

    @Test
    public void testFetchData_Success() {
        // Mock DataSnapshot to have a user with correct data
        DataSnapshot snapshot = mock(DataSnapshot.class);
        DataSnapshot snap = mock(DataSnapshot.class);

        // Create a mock user object
        Users user = new Users("choyan@gmail.com", "12345", "Choyan Barua", "02-05-2002", "20101", "", "", "", "", "");

        // Mock the getValue method to return the mock user object
        when(snap.getValue(Users.class)).thenReturn(user);

        // Mock the getChildren method to return a list containing the mock snapshot
        when(snapshot.getChildren()).thenReturn(Collections.singletonList(snap));

        // Set up the behavior of addListenerForSingleValueEvent method to call onDataChange with the mocked snapshot
        doAnswer(invocation -> {
            ValueEventListener valueEventListener = invocation.getArgument(0);
            valueEventListener.onDataChange(snapshot);
            return null;
        }).when(mockedDatabaseReference).addListenerForSingleValueEvent(any(ValueEventListener.class));

        // Call the method under test
        registerActivity.fetchData();

        // Capture the ValueEventListener
        verify(mockedDatabaseReference).addListenerForSingleValueEvent(captor.capture());

        // Trigger the onDataChange with the mocked snapshot
        captor.getValue().onDataChange(snapshot);

        // Verify the EditText fields were set correctly
        assertEquals(user.getEmail(), et_email.getText().toString());
        assertEquals(user.getPass(), et_pass.getText().toString());
        assertEquals(user.getName(), et_name.getText().toString());
        assertEquals(user.getDob(), et_dob.getText().toString());
        assertEquals(user.getSecurity(), et_security.getText().toString());
    }

    @Test
    public void testFetchData_Failure() {
        // Set up the behavior of addListenerForSingleValueEvent method to call onCancelled
        doAnswer(invocation -> {
            ValueEventListener valueEventListener = invocation.getArgument(0);
            valueEventListener.onCancelled(DatabaseError.fromException(new Exception("Firebase error")));
            return null;
        }).when(mockedDatabaseReference).addListenerForSingleValueEvent(any(ValueEventListener.class));

        // Call the method under test
        registerActivity.fetchData();

        // Capture the ValueEventListener
        verify(mockedDatabaseReference).addListenerForSingleValueEvent(captor.capture());

        // Trigger the onCancelled with a mock DatabaseError
        captor.getValue().onCancelled(DatabaseError.fromException(new Exception("Firebase error")));

        // Verify the EditText fields were not set
        verify(et_email, times(0)).setText(any(CharSequence.class));
        verify(et_pass, times(0)).setText(any(CharSequence.class));
        verify(et_name, times(0)).setText(any(CharSequence.class));
        verify(et_dob, times(0)).setText(any(CharSequence.class));
        verify(et_security, times(0)).setText(any(CharSequence.class));
    }
}
