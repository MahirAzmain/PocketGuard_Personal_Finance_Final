package com.example.pocketguard.WebPages;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.pocketguard.Bookmarks.Sqlite_StudentDatabaseSource;
import com.example.pocketguard.Bookmarks.Sqlite_StudentModel;
import com.example.pocketguard.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
/**
 * Activity class for displaying web pages without ads.
 *
 * <p>This class demonstrates the use of the MVC (Model-View-Controller) and Singleton design patterns.
 * It acts as the controller, managing the web page view (WebView), user interactions, and bookmarking functionality.</p>
 */
public class All_Web_Pages_NoAds extends AppCompatActivity {


    ProgressBar progressBar;
    TextView textView;
    SwipeRefreshLayout swipeRefreshLayout;
    FloatingActionButton floatingActionButton_bookmark;
    String webPage, link, current_link, current_title;

    WebView superWebView;
    Sqlite_StudentDatabaseSource studentDatabaseSource;
    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_page_drawer_for_webpage);   // this is the set content view

        Log.d(TAG, "Web onCreat: is called new now");
        studentDatabaseSource = new Sqlite_StudentDatabaseSource(this);



        // Getting web page URL from intent or setting a default URL
        if (getIntent().getStringExtra("Web") != null) {
            webPage = getIntent().getStringExtra("Web");    //here url will be loaded
            Log.d(TAG, "onCreate: callingggg getIntent");
        }else {
            webPage = "https://www.google.com";
        }
        // Initializing views
        progressBar = findViewById(R.id.progressBar_ID);
        swipeRefreshLayout = findViewById(R.id.refreshing_ID);
        superWebView = findViewById(R.id.webView);
        textView = findViewById(R.id.App_bar_text_progress);

        try {
            superWebView.loadUrl(webPage); // Sometimes it generate null pointer exception
        } catch (Exception e) {
            e.printStackTrace();
        }


        bookmark_saved_caller();
        // Setting up WebView
        superWebView.setWebViewClient(new WebViewClient());


        WebSettings webSettings = superWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setSupportZoom(false);
        webSettings.setCacheMode(webSettings.LOAD_NO_CACHE);
        webSettings.setDomStorageEnabled(true);

        superWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        superWebView.setScrollbarFadingEnabled(true);
        //to enhance webpage
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setGeolocationEnabled(false);
        webSettings.setNeedInitialFocus(false);
        webSettings.setSaveFormData(false);


        // Setting up WebView progress listener
        superWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // Update progress and text view
                current_title = view.getTitle();
                current_link = view.getUrl();

                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
                String string = String.valueOf(newProgress);

                Log.d(TAG, "onProgressChanged: " + newProgress);
                if (newProgress < 100) {
                    textView.setText("  Loading " + string + "%  ");

                }
                if (newProgress == 100) {
                    textView.setText("");
                }


                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                }

                super.onProgressChanged(view, newProgress);
            }


        });



        // Setting up SwipeRefreshLayout for refreshing
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            // Refresh the web page
            @Override
            public void onRefresh() {
                    try {

                        if (superWebView.getOriginalUrl().isEmpty()) {
                            Toast.makeText(All_Web_Pages_NoAds.this, "Invalid....", Toast.LENGTH_LONG).show();
                            swipeRefreshLayout.setRefreshing(false);
                        } else if (!superWebView.getOriginalUrl().isEmpty()) {
                            superWebView.reload();
                            Toast.makeText(All_Web_Pages_NoAds.this, "Refreshing....", Toast.LENGTH_LONG).show();
                            swipeRefreshLayout.setRefreshing(false);
                        }

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }catch (Exception e){

                }

            }

        });

    }
    /**
     * Handles back button press.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (this.superWebView.canGoBack()) {
            this.superWebView.goBack();
        } else {
            finish();

        }
    }


    /**
     * Handles bookmark button click.
     */
   public void bookmark_saved_caller() {
        floatingActionButton_bookmark = findViewById(R.id.bottom_nav_bookmark_button);
        floatingActionButton_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create a new student model and add it to the database
                Sqlite_StudentModel studentModel = new Sqlite_StudentModel(current_title, 20, current_link);
                studentDatabaseSource.addStudent(studentModel);
                // Show a toast message indicating the page has been bookmarked
                Toast.makeText(All_Web_Pages_NoAds.this, "আপনার পেজ বুকমার্ক হিসাবে সংরক্ষিত হয়েছে", Toast.LENGTH_SHORT).show();

            }
        });

    }

}
