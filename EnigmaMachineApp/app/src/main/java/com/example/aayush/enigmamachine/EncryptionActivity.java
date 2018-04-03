package com.example.aayush.enigmamachine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Scanner;

import Machine.Enigma;

public class EncryptionActivity extends AppCompatActivity {

    private Enigma enigma;
    private String plugboardText;
    private int leftRotorChoice;
    private int midRotorChoice;
    private int rightRotorChoice;
    private int leftRingChoice;
    private int midRingChoice;
    private int rightRingChoice;
    private int leftGroundChoice;
    private int midGroundChoice;
    private int rightGroundChoice;
    private int reflectorChoice;

    private final String[][] ENIGMA_ROTORS = {Enigma.I, Enigma.II, Enigma.III, Enigma.IV,
            Enigma.V, Enigma.VI, Enigma.VII, Enigma.VIII};
    private final String[] ENIGMA_REFLECTORS = {Enigma.B, Enigma.C};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encryption);
        final EditText outputField = findViewById(R.id.editText3);
        outputField.setEnabled(false);
        final EditText inputField = findViewById(R.id.editText2);
        inputField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                enigma.resetMachine();
                outputField.setText(enigma.print(inputField.getText().toString()));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                enigma.resetMachine();
                outputField.setText(enigma.print(inputField.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        Intent intent = getIntent();

        plugboardText = intent.getStringExtra(EncryptActivity.PLUGBOARD);

        leftRotorChoice = intent.getIntExtra(EncryptActivity.LEFTROTOR, 0);
        midRotorChoice = intent.getIntExtra(EncryptActivity.MIDROTOR, 0);
        rightRotorChoice = intent.getIntExtra(EncryptActivity.RIGHTROTOR, 0);

        leftRingChoice = intent.getIntExtra(EncryptActivity.LEFTRING, 0);
        midRingChoice = intent.getIntExtra(EncryptActivity.MIDRING, 0);
        rightRingChoice = intent.getIntExtra(EncryptActivity.RIGHTRING, 0);

        leftGroundChoice = intent.getIntExtra(EncryptActivity.LEFTGROUND, 0);
        midGroundChoice = intent.getIntExtra(EncryptActivity.MIDGROUND, 0);
        rightGroundChoice = intent.getIntExtra(EncryptActivity.RIGHTGROUND, 0);

        reflectorChoice = intent.getIntExtra(EncryptActivity.REFLECTOR, 0);

        runEnigma();
    }

    private void runEnigma(){
        String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        if(!Enigma.isDistinct(ENIGMA_ROTORS[leftRotorChoice], ENIGMA_ROTORS[midRotorChoice],
                ENIGMA_ROTORS[rightRotorChoice]))
            Toast.makeText(getApplicationContext(), "SELECT DISTINCT ROTORS!",
                    Toast.LENGTH_SHORT).show();

        enigma = new Enigma(ENIGMA_ROTORS[leftRotorChoice], ENIGMA_ROTORS[midRotorChoice],
                ENIGMA_ROTORS[rightRotorChoice], ENIGMA_REFLECTORS[reflectorChoice]);

        enigma.getLeftRotor().setRingHead(ALPHABET.charAt(leftRingChoice));
        enigma.getLeftRotor().setRotorHead(ALPHABET.charAt(leftGroundChoice));
        enigma.getMidRotor().setRingHead(ALPHABET.charAt(midRingChoice));
        enigma.getMidRotor().setRotorHead(ALPHABET.charAt(midGroundChoice));
        enigma.getRightRotor().setRingHead(ALPHABET.charAt(rightRingChoice));
        enigma.getRightRotor().setRotorHead(ALPHABET.charAt(rightGroundChoice));

        enigma.resetPlugboard();
        Scanner in = new Scanner(plugboardText);
        while(in.hasNext()){
            String wire = in.next();
            if(wire.length() == 2) {
                char src = wire.charAt(0);
                char dest = wire.charAt(1);
                if (enigma.isNotPlugged(src) && enigma.isNotPlugged(dest) && src != dest) {
                    enigma.addPlugboardWire(src, dest);
                }
            }
        }
        in.close();
    }
}
