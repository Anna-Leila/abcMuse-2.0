package com.annaleila.abcMuse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class PlayingFieldActivity extends AppCompatActivity {
    public static final String KEY_CONSONANTS = "com.annaLeila.abcMuse.consonants";
    private static final int DEFAULT_CONSONANTS = 10;
    public static final String KEY_VOWELS = "com.annaLeila.abcMuse.vowels";
    private static final int DEFAULT_VOWELS = 0;
    private static final int UPPER_BOUND_CONSONANTS = 21;
    private static final int UPPER_BOUND_VOWELS = 5;

    int score = 0;

    HashSet<String> wordsSet; // set of existing words - our dictionary
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


        TableLayout consonantsLayout = findViewById(R.id.consonants_table_layout);
        for (int i=0; i<3; i++) {
            TableRow row = new TableRow(this);
            for (int j=0; j<7; j++) {
                Button letterButton = new Button(this);
                letterButton.setTextSize(22);
                Character c = consonants.get(i*7 + j);
                String letter = c.toString() + ": " + alphabet[c - 'a'];
                letterButton.setText(letter);
                if (alphabet[c - 'a']>0) letterButton.setBackgroundColor(markedButton);
                else letterButton.setBackgroundColor(unmarkedButton);

                letterButton.setLayoutParams(new TableRow.LayoutParams(147, 150));

                letterButtons.add(letterButton);
                row.addView(letterButton, j);
            }
            consonantsLayout.addView(row, i);
        }

        TableLayout vowelsLayout = findViewById(R.id.vowels_table_layout);
        for (int i=0; i<1; i++) {
            TableRow row = new TableRow(this);
            for (int j=0; j<5; j++) {
                Button letterButton = new Button(this);
                letterButton.setTextSize(22);
                Character c = vowels.get(i*7 + j);
                String letter = c.toString() + ": " + alphabet[c - 'a'];
                letterButton.setText(letter);
                if (alphabet[c - 'a']>0) letterButton.setBackgroundColor(markedButton);
                else letterButton.setBackgroundColor(unmarkedButton);

                letterButton.setLayoutParams(new TableRow.LayoutParams(150, 150));

                letterButtons.add(letterButton);
                row.addView(letterButton, j);
            }
            vowelsLayout.addView(row, i);
        }

        getWords();
    }

    public void enterWords() {
        EditText editText = findViewById(R.id.words_editText);
        String content = editText.getText().toString();

    }

    public void getWords() { // get words out of the file and put them into wordSet
        try {
            InputStream inputStream = getAssets().open("popular_words.txt");
            int size = inputStream.available();
            byte[] readBytes = new byte[size];
            //inputStream.read(readBytes);
            String wordListContents = new String(readBytes, StandardCharsets.UTF_8);
            String[] words = wordListContents.split("\n");
            wordsSet = new HashSet<>();
            for (String word:words) {
                wordsSet.add(word.substring(0, word.length()-1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean contains(String word) { // check if wordSet contains String word
        return wordsSet.contains(word);
    }

    public void checkLetter(Character letter) { // check if needed letter is already used, change score accordingly
        boolean found = false;
        for (Button button:letterButtons) {
            if (button.getText().charAt(0)==letter && !button.getBackground().equals(markedButton)) {
                button.setBackgroundColor(markedButton);
                score++;
                found = true;
                break;
            }
        }
        if (!found) {
            score--;
        }
    }
}