package com.annaleila.abcMuse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

// activity with rules text
public class RulesActivity extends AppCompatActivity {
    // text with rules of playing
    private static final String TEXT = "Hello!\n" +
            "You should choose the total number of vowels and consonants. " +
            "The program will show the amount of each individual letter. You have to think of as many words with" +
            " available letters as you can. Each one letter will raise your score by 1. If You use a letter with" +
            " zero amount, your score will reduce by 1. Try to get highest score possible. Good luck!\n" +
            "\n" +
            "This funny word game will cheer You up and become a mental gymnastics. Don’t forget to tell your " +
            "friends about it\n" +
            "You can help the project:\n" +
            "https://www.paypal.com/paypalme/VZoikin\n" +
            "Your questions and suggestions are welcome:\n" +
            "annzoykina@gmail.com\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        // set back button enabled on tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // set text with rules
        TextView rules = findViewById(R.id.rules_textView);
        rules.setText(TEXT);
    }

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}