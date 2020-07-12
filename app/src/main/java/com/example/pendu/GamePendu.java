package com.example.pendu;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GamePendu extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout container;
    private Button btn_send;
    private TextView lettres_tapees;
    private EditText letter;
    private ImageView image;

    private String word;
    private int found;
    private int error;
    private List<Character> listOfLetters = new ArrayList<>();
    private boolean win;
    private List<String> wordList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pendu_game);
        container = (LinearLayout) findViewById(R.id.word_container);
        btn_send = (Button) findViewById(R.id.btn_send);
        letter = (EditText) findViewById(R.id.et_letter);
        image = (ImageView) findViewById(R.id.iv_pendu);
        lettres_tapees = (TextView) findViewById(R.id.tv_lettres_tapees);

        initGame();
        btn_send.setOnClickListener(this);

    }
    //initialise le jeu pendu
    public void initGame() {
        word = generateWord();
        win = false;
        error = 0;
        found = 0;
        lettres_tapees.setText("");
        image.setBackgroundResource(R.drawable.first);
        listOfLetters = new ArrayList<>();

        container.removeAllViews();

        for (int i = 0; i < word.length(); i++) {
            TextView oneletter = (TextView) getLayoutInflater().inflate(R.layout.textview, null);
            container.addView(oneletter);
        }
    }

    //fonction au click du bouton pour propose une lettre
    @Override
    public void onClick(View v) {

        String letterFromInput = letter.getText().toString().toUpperCase();
        letter.setText("");

        if (letterFromInput.length() > 0) {
            if(!letterAlreadyUsed(letterFromInput.charAt(0), listOfLetters)){
                listOfLetters.add(letterFromInput.charAt(0));
                checkIfLetterIsInWord(letterFromInput, word);
            }
            // le jeu pendu gagner
            if(found == word.length()){
                win = true;
                createAlerte(win);
                //Toast.makeText(getApplicationContext(), "Victoire", Toast.LENGTH_SHORT).show();

            }
            // lettre ne se trouve pas dans le mot
            if(!word.contains(letterFromInput)){
                error ++;

            }
            setImage(error);
            //si 6 error renvoyer une alerte
            if(error == 6){
                win = false;
                createAlerte(win);
                //Toast.makeText(getApplicationContext(), "Perdu", Toast.LENGTH_SHORT).show();
            }
            //appel de la fct afficher les lettres
            showAlLetters(listOfLetters);
        }


    }

    //verifier si la lettre à déjà été tapées
    public boolean letterAlreadyUsed(char a, List<Character> listOfLetters) {

        for (int i = 0; i < listOfLetters.size(); i++) {
            if (listOfLetters.get(i) == a) {
                Toast.makeText(getApplicationContext(), "Vous avez déjà entrée cette lettres !!", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;

    }
    //verifier si la lettre fait bien partie du mot a trouver
    public void checkIfLetterIsInWord(String letter, String word){
        for(int i = 0; i < word.length(); i++){
            if(letter.equals(String.valueOf(word.charAt(i)))){
                TextView tv = (TextView) container.getChildAt(i);
                tv.setText((String.valueOf(word.charAt(i))));
                found++;
            };
        }
    }

    //afficher les lettres qui sont tapé
    public void showAlLetters(List<Character> listOfLetters){
        String chaine = "";
        for(int i = 0; i < listOfLetters.size(); i++){

            chaine += listOfLetters.get(i) + "\n";

        }
        if(!chaine.equals("")){
            lettres_tapees.setText(chaine);
        }
    }

    //gerer les erreurs et changer l'image a changer erreur
    public void setImage(int error){
        switch (error){
            case 1:
                image.setBackgroundResource(R.drawable.second);
                break;
            case 2:
                image.setBackgroundResource(R.drawable.third);
                break;
            case 3:
                image.setBackgroundResource(R.drawable.fourth);
                break;
            case 4:
                image.setBackgroundResource(R.drawable.fifth);
                break;
            case 5:
                image.setBackgroundResource(R.drawable.sixth);
                break;
            case 6:
                image.setBackgroundResource(R.drawable.seventh);
                break;
        }

    }
    //alert pour afficher victoire ou la defaite + bouton rejouer
    public void createAlerte(boolean win) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(win){
            builder.setTitle("Bravo : vous avez gagner !");
            builder.setMessage("Le mot que vous avez trouvez était : " + word);
            builder.setPositiveButton(getResources().getString(R.string.rejouer), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    initGame();
                }
            });

            builder.create().show();
        }
        if (!win) {
            builder.setTitle("Vous avez perdu !");
            builder.setMessage("Le mot à trouver était : " + word);
            builder.setPositiveButton(getResources().getString(R.string.rejouer), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    initGame();
                }
            });

            builder.create().show();
        }
    }

    public List getListOfWord() {

        try {
         BufferedReader buffer = new BufferedReader(new InputStreamReader(getAssets().open("pendu_liste.txt")));
         String line;
         while ((line = buffer.readLine()) != null){
             wordList.add(line);
         }
         buffer.close();
        }catch(IOException e){
           e.printStackTrace();
        }

        return wordList;
    }

    public String generateWord(){
        wordList = getListOfWord();
        int ramdom = (int) (Math.floor(Math.random() * wordList.size()));
        String word = wordList.get(ramdom).trim();
        return word;
    }
}
