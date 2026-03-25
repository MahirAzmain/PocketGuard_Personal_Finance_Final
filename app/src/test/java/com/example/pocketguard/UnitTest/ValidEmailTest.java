//
//package com.example.pocketguard.UnitTest;
//
//import static org.mockito.Mockito.*;
//        import static org.junit.Assert.*;
//
//import android.content.SharedPreferences;
//
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//
//        import com.example.pocketguard.Account.Register;
//import com.google.firebase.database.DataSnapshot;
//        import com.google.firebase.database.DatabaseReference;
//        import com.google.firebase.database.FirebaseDatabase;
//
//import org.junit.Before;
//        import org.junit.Test;
//        import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//        import org.mockito.Mock;
//        import org.mockito.MockitoAnnotations;
//
//@RunWith(AndroidJUnit4.class)
//public class ValidEmailTest {
//
//    @Mock
//    SharedPreferences spp;
//
//    @Mock
//    SharedPreferences.Editor editor;
//
//    @Mock
//    FirebaseDatabase firebaseDatabase;
//
//    @Mock
//    DatabaseReference databaseReference;
//
//    @Mock
//    DataSnapshot dataSnapshot;
//
//    @InjectMocks
//    Register registerActivity;
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        when(firebaseDatabase.getReference(anyString())).thenReturn(databaseReference);
//        when(spp.edit()).thenReturn(editor);
//    }
//
//    @Test
//    public void testIsValidEmail() {
//        Register register = new Register();
//
//        assertTrue(register.isValidEmail("test@example.com"));
//        assertFalse(register.isValidEmail("invalid-email"));
//    }
//
//}
//
//
//
//
