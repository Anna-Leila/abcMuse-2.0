package com.annaleila.abcMuse;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

// activity for playing field
public class PlayingFieldActivity extends AppCompatActivity {
    // key for number of consonants
    public static final String KEY_CONSONANTS = "com.annaleila.abcMuse.consonants";

    // default number of consonants
    private static final int DEFAULT_CONSONANTS = 10;

    // key for number of vowels
    public static final String KEY_VOWELS = "com.annaleila.abcMuse.vowels";

    // default number of vowels
    private static final int DEFAULT_VOWELS = 0;

    // amount of consonants
    private static final int UPPER_BOUND_CONSONANTS = 21;

    // amount of vowels
    private static final int UPPER_BOUND_VOWELS = 5;

    int score = 0; // score of the player

    String recognised = "-"; // all recognised words in one string
    TextView output; // output view
    HashSet<String> wordsSet; // set of existing words - our dictionary

    // background color of button, letter on which is still can be used
    int markedButton = Color.rgb(0, 255, 127);

    // background color of button, letter on which is still can be used
    int unmarkedButton = Color.rgb(255,53,184);

    // random for choosing consonants and vowels
    Random random = new Random();

    // list of all vowels
    ArrayList<Character> vowels = new ArrayList<>(
            Arrays.asList('a', 'e', 'i', 'o', 'u')
    );

    // list of all consonants
    ArrayList<Character> consonants = new ArrayList<>(
            Arrays.asList('b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q',
                    'r', 's', 't', 'v', 'w', 'x', 'y', 'z'));

    int[] alphabet = new int[26]; // amount of each letter
    Button[] letterButtons = new Button[26]; // a button for each letter


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_field);

        // set back button enabled on tool bar
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();

        // get the number of consonants
        int numberOfConsonants = intent.getIntExtra(KEY_CONSONANTS, DEFAULT_CONSONANTS);
        // then choose random consonants from the list
        for (int i=0; i<numberOfConsonants; i++) {
            Character letter = consonants.get(random.nextInt(UPPER_BOUND_CONSONANTS));
            alphabet[letter - 'a']+=1;
        }
        // get the number of vowels
        int numberOfVowels = intent.getIntExtra(KEY_VOWELS, DEFAULT_VOWELS);
        // then choose random vowels from the list
        for (int i=0; i<numberOfVowels; i++) {
            Character letter = vowels.get(random.nextInt(UPPER_BOUND_VOWELS));
            alphabet[letter - 'a']+=1;
        }

        // set table layout for consonants: 3 row, 7 consonants in each
        TableLayout consonantsLayout = findViewById(R.id.consonants_table_layout);
        for (int i=0; i<3; i++) {
            TableRow row = new TableRow(this);
            for (int j=0; j<7; j++) {
                // create consonant button and set it's parameters
                Button letterButton = new Button(this);
                letterButton.setTextSize(22);
                Character c = consonants.get(i*7 + j);
                String text = c.toString() + ": " + alphabet[c - 'a'];
                letterButton.setText(text);
                if (alphabet[c - 'a']>0) letterButton.setBackgroundColor(markedButton);
                else letterButton.setBackgroundColor(unmarkedButton);

                letterButton.setLayoutParams(new TableRow.LayoutParams(147, 150));

                letterButtons[c - 'a'] = letterButton;
                row.addView(letterButton, j);
            }
            consonantsLayout.addView(row, i);
        }

        // set table layout for vowels: 1 row, all 5 vowels in it
        TableLayout vowelsLayout = findViewById(R.id.vowels_table_layout);
        for (int i=0; i<1; i++) {
            TableRow row = new TableRow(this);
            for (int j=0; j<5; j++) {
                // create vowel button and set it's parameters
                Button letterButton = new Button(this);
                letterButton.setTextSize(22);
                Character c = vowels.get(i*7 + j);
                String text = c.toString() + ": " + alphabet[c - 'a'];
                letterButton.setText(text);
                if (alphabet[c - 'a']>0) letterButton.setBackgroundColor(markedButton);
                else letterButton.setBackgroundColor(unmarkedButton);

                letterButton.setLayoutParams(new TableRow.LayoutParams(150, 150));

                letterButtons[c - 'a'] = letterButton;
                row.addView(letterButton, j);
            }
            vowelsLayout.addView(row, i);
        }

        output = findViewById(R.id.output_textView);
        setOutput();

        getWords();
    }


    public void getWords() { // get words out of the file and put them into wordSet
        try {
            InputStream inputStream = getAssets().open("popular_words.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            wordsSet = new HashSet<>();
            String word = "hello";
            while (word!=null) {
                wordsSet.add(word);
                word = reader.readLine();
            }
        } catch (IOException e) {
            output.setBackgroundColor(Color.rgb(0, 0, 0));
            e.printStackTrace();
        }
    }

    public void enterWords(View view) { // called when "enter" is pressed
        EditText editText = findViewById(R.id.words_editText);
        String text = editText.getText().toString();
        text = text.toLowerCase();
        String[] words = text.split(" ");
        if (recognised.equals("-")) recognised = "";
        for (String word:words) {
            // check if such word exists
            if (contains(word)) { // if it does exist,
                for (Character c:word.toCharArray()) { // then check every letter in it
                    checkLetter(c);
                }
                recognised+=", "+word; // add the word to recognised words
            }
        }
        editText.setText(""); // remove text that user typed in
        if (recognised.equals("")) recognised = "-";
        else if (recognised.charAt(0)==',') recognised = recognised.substring(2);

        // print out the results
        setOutput();
    }

    public boolean contains(String word) { // check if wordSet contains String word
        return wordsSet.contains(word);
    }

    public void setOutput() { // print out current results
        output.setText("");
        output.append("Your current score: "+score+"\n");
        output.append("Recognised words: "+recognised+"\n");
    }

    public void checkLetter(Character letter) { // check if needed letter is available, change score accordingly
        if (alphabet[letter - 'a']>0) {
            score++;
            alphabet[letter - 'a']--;

            Button letterButton = letterButtons[letter - 'a'];
            String text = letter.toString() + ": " + alphabet[letter - 'a'];
            letterButton.setText(text);

            if (alphabet[letter - 'a']>0) letterButton.setBackgroundColor(markedButton);
            else letterButton.setBackgroundColor(unmarkedButton);
        } else score--;
    }
}