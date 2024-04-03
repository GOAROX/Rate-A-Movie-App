package com.example.myfirstapp;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ReportScreenActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-movie_list_item UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (AUTO_HIDE) {
                        delayedHide(AUTO_HIDE_DELAY_MILLIS);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    view.performClick();
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    private String movieName;
    private String dateWatched;
    private Date date;
    private Boolean direction;
    private Boolean story;
    private Boolean animation;
    private Boolean acting;
    private Boolean storybuilding;
    private Boolean yearn;
    private float rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_report_screen);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.back_button).setOnTouchListener(mDelayHideTouchListener);

        Button switchButt = findViewById(R.id.back_button); // Replace "switch" with your button's ID

        switchButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the NewPageActivity
                Intent intent = new Intent(ReportScreenActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        movieName = intent.getStringExtra("movieName");
        long dateInMillis = intent.getLongExtra("date", 0); // Default value if not found
        rating = intent.getFloatExtra("rating", 0);
        // Convert long value back to Date object
        date = new Date(dateInMillis);
        dateWatched = date.toString();
        direction = intent.getBooleanExtra("direction", false); // Default value if not found
        story = intent.getBooleanExtra("story", false); // Default value if not found
        animation = intent.getBooleanExtra("animation", false); // Default value if not found
        acting = intent.getBooleanExtra("acting", false); // Default value if not found
        storybuilding = intent.getBooleanExtra("storybuilding", false); // Default value if not found
        yearn = intent.getBooleanExtra("yearn", false); // Default value if not found

        //now make a function in here to print the values on screen and the a back button to go to home screen
        TextView textView = findViewById(R.id.printMovieName);
        textView.setText(movieName);
        textView = findViewById(R.id.printDate);
        textView.setText(date.toString());
        textView = findViewById(R.id.printRating);
        textView.setText(String.valueOf(rating));

        // Read existing JSON content from file, if any
        JSONArray existingJsonArray = readExistingJsonFromFile();

        // Create a JSON object for the current data entry
        JSONObject jsonObject = createJsonObject();

        // Add the new JSON object to the existing JSON array
        existingJsonArray.put(jsonObject);

        // Write the updated JSON array back to the file
        writeJsonToFile(existingJsonArray);

    }

    private JSONArray readExistingJsonFromFile() {
        // Create a file object for the JSON file
        File file = new File(getFilesDir(), "data.json");

        // Initialize an empty JSON array
        JSONArray jsonArray = new JSONArray();

        Log.d("success", "trying to read data.json");
        // Read existing JSON content from file, if any
        if (file.exists()) {
            Log.d("success", "file data.json already exists hence reading");
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                jsonArray = new JSONArray(stringBuilder.toString());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }

        return jsonArray;
    }

    private JSONObject createJsonObject() {
        // Create a JSON object for the current data entry
        Log.d("success", "created json object");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("movieName", movieName);
            jsonObject.put("dateWatched", dateWatched);
            //jsonObject.put("date", date.getTime()); // Store date as timestamp
            jsonObject.put("direction", direction);
            jsonObject.put("story", story);
            jsonObject.put("animation", animation);
            jsonObject.put("acting", acting);
            jsonObject.put("storybuilding", storybuilding);
            jsonObject.put("yearn", yearn);
            jsonObject.put("rating", rating);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void writeJsonToFile(JSONArray jsonArray) {
        // Create a file object for the JSON file
        Log.d("success", "about to write to file");
        File file = new File(getFilesDir(), "data.json");

        // Write the updated JSON array back to the file
        try {
            if (!file.exists()) {
                boolean created = file.createNewFile();
                if (!created) {
                    Toast.makeText(this, "Failed to Create File", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d("success", "created file data.json");
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(jsonArray.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}