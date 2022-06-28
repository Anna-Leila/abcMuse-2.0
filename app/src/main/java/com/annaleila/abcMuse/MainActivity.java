package com.annaleila.abcMuse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

// main activity
public class MainActivity extends AppCompatActivity {
    // consonantsLabel - label with text and the number of consonants for consonants seekbar
    // vowelsLabel - label with text and the number of vowels for vowels seekbar
    TextView consonantsLabel, vowelsLabel;

    // numberOfConsonants - current amount of consonants chosen on consonants seekbar
    // numberOfVowels - current amount of vowels chosen on vowels seekbar
    int numberOfConsonants, numberOfVowels;

    // consonantAdd - starting amount of consonants
    // vowelAdd - starting amount of vowels
    private static final int consonantAdd = 1, vowelAdd = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setConsonants();
        setVowels();
    }

    public void setConsonants() { // set listener and label for consonant seek bar
        SeekBar consonantSeekBar = findViewById(R.id.consonants_seekBar);
        consonantSeekBar.setOnSeekBarChangeListener(consonantListener);

        int progress = consonantSeekBar.getProgress(); // get starting number of consonants
        consonantsLabel = findViewById(R.id.consonants_label);

        numberOfConsonants = progress + consonantAdd;
        String text = "The number of consonants: " + numberOfConsonants;
        consonantsLabel.setText(text);
    }

    // listener for the number of consonants
    SeekBar.OnSeekBarChangeListener consonantListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            numberOfConsonants = progress + consonantAdd;
            String text = "The number of consonants: " + numberOfConsonants;
            consonantsLabel.setText(text);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    public void setVowels() { // set listener and label for vowel seek bar
        SeekBar vowelSeekBar = findViewById(R.id.vowels_seekBar);
        vowelSeekBar.setOnSeekBarChangeListener(vowelListener);

        int progress = vowelSeekBar.getProgress(); // get starting number of vowels
        vowelsLabel = findViewById(R.id.vowels_label);

        numberOfVowels = progress + vowelAdd;
        String text = "The number of vowels: " + numberOfVowels;
        vowelsLabel.setText(text);
    }

    // listener for the number of vowels
    SeekBar.OnSeekBarChangeListener vowelListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            numberOfVowels = progress + vowelAdd;
            String text = "The number of vowels: " + numberOfVowels;
            vowelsLabel.setText(text);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    public void showRules(View view) { // show rules activity
        Intent intent = new Intent(this, RulesActivity.class);
        startActivity(intent);
    }

    public void showPlayingField(View view) { // show playing field activity
        Intent intent = new Intent(this, PlayingFieldActivity.class);

        // send number of consonants to playing field activity
        intent.putExtra(PlayingFieldActivity.KEY_CONSONANTS , numberOfConsonants);

        // send number of vowels to playing field activity
        intent.putExtra(PlayingFieldActivity.KEY_VOWELS, numberOfVowels);
        startActivity(intent);
    }
}