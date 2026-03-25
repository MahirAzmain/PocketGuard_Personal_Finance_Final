package com.example.pocketguard;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Gpt extends AppCompatActivity {

    private ChatGPTClient chatGPTClient;

    private EditText editText;
    private Button sendButton;
    private TextView responseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpt);

        editText = findViewById(R.id.editText);
        sendButton = findViewById(R.id.sendButton);
        responseTextView = findViewById(R.id.responseTextView);


        // Set click listener for the sendButton
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userInput = editText.getText().toString().trim();

                // Clear the editText
                //editText.setText("");

                // Instantiate the ChatGPTClient
                chatGPTClient = new ChatGPTClient();

                // Log the client object to debug
                Log.d("testing_gpt", "onCreate: ChatGPTClient object created: " + chatGPTClient);

                // Create an ExecutorService to handle the network request on a separate thread
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.submit(() -> {
                    try {
                        String response = chatGPTClient.getChatGPTResponse(userInput);
                        Log.d("testing_gpt", "Response received: " + response);

                        //responseTextView.setText("Hello");
                        String responseText = parseResponse(response);
                        runOnUiThread(() -> responseTextView.setText(responseText));
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("testing_gpt", "Error fetching response: ", e);
                        runOnUiThread(() -> responseTextView.setText("Error: " + e.getMessage()));
                    }
                });

                // Shutdown the executor service after use
                executorService.shutdown();
            }
        });

    }

    private String parseResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray choices = jsonObject.getJSONArray("choices");
            if (choices.length() > 0) {
                JSONObject choice = choices.getJSONObject(0);
                return choice.getString("text");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("testing_gpt", "Error parsing response: ", e);
        }
        return "Error: Unable to parse response";
    }
}
