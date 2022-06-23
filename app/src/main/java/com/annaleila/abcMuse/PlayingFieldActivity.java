package com.annaleila.abcMuse;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PlayingFieldActivity extends AppCompatActivity {
    public static final String KEY_CONSONANTS = "com.annaLeila.abcMuse.consonants";
    private static final int DEFAULT_CONSONANTS = 10;
    public static final String KEY_VOWELS = "com.annaLeila.abcMuse.vowels";
    private static final int DEFAULT_VOWELS = 0;
    private static final int UPPER_BOUND_CONSONANTS = 21;
    private static final int UPPER_BOUND_VOWELS = 5;

    int markedButton = Color.rgb(0, 255, 127);
    int unmarkedButton = Color.rgb(255,53,184);
    Random random = new Random();
    ArrayList<Button> letterButtons = new ArrayList<>(); // list of button with available letters
    ArrayList<Character> vowels = new ArrayList<>(
            Arrays.asList('a', 'e', 'i', 'o', 'u')
    );
    ArrayList<Character> consonants = new ArrayList<>( // list of available consonants
            Arrays.asList('b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q',
                    'r', 's', 't', 'v', 'w', 'x', 'y', 'z'));
    int[] alphabet = new int[26];


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_field);
        Intent intent = getIntent();
        int numberOfConsonants = intent.getIntExtra(KEY_CONSONANTS, DEFAULT_CONSONANTS);

        for (int i=0; i<numberOfConsonants; i++) {
            Character letter = consonants.get(random.nextInt(UPPER_BOUND_CONSONANTS));
            alphabet[letter - 'a']+=1;
        }

        int numberOfVowels = intent.getIntExtra(KEY_VOWELS, DEFAULT_VOWELS);

        for (int i=0; i<numberOfVowels; i++) {
            Character letter = vowels.get(random.nextInt(UPPER_BOUND_VOWELS));
            alphabet[letter - 'a']+=1;
        }

        System.out.print(alphabet);

        FlexboxLayout consonantsLayout = findViewById(R.id.consonants_flexbox_layout_field);
        for (Character c:consonants) {
            Button letterButton = new Button(this);
            letterButton.setTextSize(20);
            String letter = c.toString() + ": " + alphabet[c - 'a'];
            letterButton.setText(letter);
            letterButton.setLayoutParams(new LinearLayout.LayoutParams(140, 40));
            if (alphabet[c - 'a']>0) letterButton.setBackgroundColor(markedButton);
            else letterButton.setBackgroundColor(unmarkedButton);
            consonantsLayout.addView(letterButton);
            letterButtons.add(letterButton);
        }

        FlexboxLayout vowelsLayout = findViewById(R.id.vowels_flexbox_layout_field);
        for (Character c:vowels) {
            Button letterButton = new Button(this);
            letterButton.setTextSize(20);
            String letter = c.toString() + ": " + alphabet[c - 'a'];
            letterButton.setText(letter);
            letterButton.setLayoutParams(new LinearLayout.LayoutParams(200, 50));
            if (alphabet[c - 'a']>0) letterButton.setBackgroundColor(markedButton);
            else letterButton.setBackgroundColor(unmarkedButton);
            vowelsLayout.addView(letterButton);
            letterButtons.add(letterButton);
        }
    }
}