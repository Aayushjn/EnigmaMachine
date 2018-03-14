package com.example.aayush.enigmamachine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class EncryptActivity extends AppCompatActivity {

    private static final String LOG_TAG = EncryptActivity.class.getSimpleName();

    public static final String PLUGBOARD = "com.example.android.enigmamachine.extra.PLUGBOARD";
    public static final String LEFTROTOR = "com.example.android.enigmamachine.extra.LEFTROTOR";
    public static final String MIDROTOR = "com.example.android.enigmamchine.extra.MIDROTOR";
    public static final String RIGHTROTOR = "com.example.android.enigmamachine.extra.RIGHTROTOR";
    public static final String LEFTRING = "com.example.android.enigmamachine.extra.LEFTRING";
    public static final String MIDRING = "com.example.android.enigmamachine.extra.MIDRING";
    public static final String RIGHTRING = "com.example.android.enigmamachine.extra.RIGHTRING";
    public static final String LEFTGROUND = "com.example.android,enigmamachine.extra.LEFTGROUND";
    public static final String MIDGROUND = "com.example.android.enigmamachine.extra.MIDGROUND";
    public static final String RIGHTGROUND = "com.example.android.enigmamachine.extra.RIGHTGROUND";
    public static final String REFLECTOR = "com.example.android.enigmamachine.extra.REFLECTOR";

    private EditText plugboardInput;
    private Spinner leftRotor;
    private Spinner midRotor;
    private Spinner rightRotor;
    private Spinner leftRing;
    private Spinner midRing;
    private Spinner rightRing;
    private Spinner leftGround;
    private Spinner midGround;
    private Spinner rightGround;
    private Spinner reflector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);

        plugboardInput = findViewById(R.id.editText);

        leftRotor = findViewById(R.id.spinner4);
        midRotor = findViewById(R.id.spinner5);
        rightRotor = findViewById(R.id.spinner6);
        ArrayAdapter<CharSequence> rotorAdapter = ArrayAdapter.createFromResource(this,
                R.array.rotor_array, android.R.layout.simple_spinner_dropdown_item);
        rotorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leftRotor.setAdapter(rotorAdapter);
        midRotor.setAdapter(rotorAdapter);
        rightRotor.setAdapter(rotorAdapter);

        leftRing = findViewById(R.id.spinner7);
        midRing = findViewById(R.id.spinner9);
        rightRing = findViewById(R.id.spinner10);
        ArrayAdapter<CharSequence> alphabetAdapter = ArrayAdapter.createFromResource(this,
                R.array.alphabet_array, android.R.layout.simple_spinner_dropdown_item);
        alphabetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leftRing.setAdapter(alphabetAdapter);
        midRing.setAdapter(alphabetAdapter);
        rightRing.setAdapter(alphabetAdapter);

        leftGround = findViewById(R.id.spinner11);
        midGround = findViewById(R.id.spinner12);
        rightGround = findViewById(R.id.spinner13);
        leftGround.setAdapter(alphabetAdapter);
        midGround.setAdapter(alphabetAdapter);
        rightGround.setAdapter(alphabetAdapter);

        reflector = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> reflectorAdapter = ArrayAdapter.createFromResource(this,
                R.array.reflector_array, android.R.layout.simple_spinner_dropdown_item);
        reflectorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reflector.setAdapter(reflectorAdapter);

    }

    public void onClickSave(View view) {
        Log.d(LOG_TAG, "Save button clicked!");
        Intent intent = new Intent(this, EncryptionActivity.class);

        String plugboardText = plugboardInput.getText().toString();

        int leftRotorChoice = leftRotor.getSelectedItemPosition();
        int midRotorChoice = midRotor.getSelectedItemPosition();
        int rightRotorChoice = rightRotor.getSelectedItemPosition();

        int leftRingChoice = leftRing.getSelectedItemPosition();
        int midRingChoice = midRing.getSelectedItemPosition();
        int rightRingChoice = rightRing.getSelectedItemPosition();

        int leftGroundChoice = leftGround.getSelectedItemPosition();
        int midGroundChoice = midGround.getSelectedItemPosition();
        int rightGroundChoice = rightGround.getSelectedItemPosition();

        int reflectorChoice = reflector.getSelectedItemPosition();

        intent.putExtra(PLUGBOARD, plugboardText);

        intent.putExtra("leftRotorChoice", leftRotorChoice);
        intent.putExtra(MIDROTOR, midRotorChoice);
        intent.putExtra(RIGHTROTOR, rightRotorChoice);

        intent.putExtra(LEFTRING, leftRingChoice);
        intent.putExtra(MIDRING, midRingChoice);
        intent.putExtra(RIGHTRING, rightRingChoice);

        intent.putExtra(LEFTGROUND, leftGroundChoice);
        intent.putExtra(MIDGROUND, midGroundChoice);
        intent.putExtra(RIGHTGROUND, rightGroundChoice);

        intent.putExtra(REFLECTOR, reflectorChoice);

        startActivity(intent);
    }
}
