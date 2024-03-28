package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewPageActivity extends AppCompatActivity {

    private EditText movieNameText;
    private EditText dateText;
    private EditText ratingText;

    private String movieName;
    private String dateWatched;
    private Date date;
    private Boolean direction;
    private Boolean story;
    private Boolean animation;
    private Boolean acting;
    private Boolean storybuilding;
    private Boolean yearn;
    private int rating;

    //private TextView MovieName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_page);

        Button switchButt = findViewById(R.id.backButton); // Replace "switch" with your button's ID

        switchButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the NewPageActivity
                Intent intent = new Intent(NewPageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        movieNameText = findViewById(R.id.entermoviename);
        dateText = findViewById(R.id.enterDate);
        ratingText = findViewById(R.id.enterRating);
       // MovieName = findViewById(R.id.test);
    }

    public void submit(View view) {
        // Get user input from EditText
        if (validateMovieName() && validateDateWatched() && validateRating()) {
            Toast.makeText(this, "Movie Name: " + movieName, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Date Watched: " + date, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Rating: " + rating, Toast.LENGTH_SHORT).show();
        }
        CheckBox simpleCheckBox = (CheckBox) findViewById(R.id.checkDirecting);
        direction = simpleCheckBox.isChecked();
        simpleCheckBox = (CheckBox) findViewById(R.id.checkStory);
        story = simpleCheckBox.isChecked();
        simpleCheckBox = (CheckBox) findViewById(R.id.checkAnimation);
        animation = simpleCheckBox.isChecked();
        simpleCheckBox = (CheckBox) findViewById(R.id.checkActing);
        acting = simpleCheckBox.isChecked();
        simpleCheckBox = (CheckBox) findViewById(R.id.checkStorybuilding);
        storybuilding = simpleCheckBox.isChecked();
        simpleCheckBox = (CheckBox) findViewById(R.id.checkYearn);
        yearn = simpleCheckBox.isChecked();

    }

    private boolean validateMovieName() {
        // Validate movie name
        movieName = movieNameText.getText().toString().trim();
        if (movieName.isEmpty()) {
            Toast.makeText(this, "Please enter a movie name", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateDateWatched() {
        // Validate date watched
        dateWatched = dateText.getText().toString().trim();
        if (dateWatched.isEmpty()) {
            Toast.makeText(this, "Please enter the date watched", Toast.LENGTH_SHORT).show();
            return false;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat.setLenient(false); // Disable leniency to ensure strict parsing
        try {
            // Attempt to parse the date string
            date = dateFormat.parse(dateWatched);
            return true;
        } catch (ParseException e) {
            // If parsing fails, show an error message
            Toast toast = Toast.makeText(this, "Please enter a date in the format MM/DD/YYYY", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
    }

    private boolean validateRating() {
        // Validate rating
        String ratingString = ratingText.getText().toString().trim();
        if (ratingString.isEmpty()) {
            Toast.makeText(this, "Please enter a rating", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            rating = Integer.parseInt(ratingString);
            if (rating < 0 || rating > 10) {
                Toast.makeText(this, "Rating should be between 0 and 10", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid rating", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}