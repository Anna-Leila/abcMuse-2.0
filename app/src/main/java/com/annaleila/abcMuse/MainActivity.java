package com.annaleila.abcMuse;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView consonantsLabel, vowelsLabel;
    int numberOfConsonants, numberOfVowels;
    private static final int consonantAdd = 1, vowelAdd = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setConsonants();
        setVowels();
    }

    public void setConsonants() {
        SeekBar consonantSeekBar = findViewById(R.id.consonants_seekBar);
        consonantSeekBar.setOnSeekBarChangeListener(consonantListener);

        int progress = consonantSeekBar.getProgress();
        consonantsLabel = findViewById(R.id.consonants_label);

        numberOfConsonants = progress + consonantAdd;
        String text = "The number of consonants: " + numberOfConsonants;
        consonantsLabel.setText(text);
    }

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

    public void setVowels() {
        SeekBar vowelSeekBar = findViewById(R.id.vowels_seekBar);
        vowelSeekBar.setOnSeekBarChangeListener(vowelListener);

        int progress = vowelSeekBar.getProgress();
        vowelsLabel = findViewById(R.id.vowels_label);

        numberOfVowels = progress + vowelAdd;
        String text = "The number of vowels: " + numberOfVowels;
        vowelsLabel.setText(text);
    }

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

    public void showRules(View view) {
        Intent intent = new Intent(this, RulesActivity.class);
        startActivity(intent);
    }

    public void showPlayingField(View view) {
        Intent intent = new Intent(this, PlayingFieldActivity.class);
        intent.putExtra(PlayingFieldActivity.KEY_CONSONANTS , numberOfConsonants);
        intent.putExtra(PlayingFieldActivity.KEY_VOWELS, numberOfVowels);
        startActivity(intent);
    }
}