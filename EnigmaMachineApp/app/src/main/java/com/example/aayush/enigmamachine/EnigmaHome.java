package com.example.aayush.enigmamachine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class EnigmaHome extends AppCompatActivity {

    private static final String LOG_TAG = EnigmaHome.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enigma_home);
    }

    public void onClickEncrypt(View view) {
        Log.d(LOG_TAG, "Encrypt button clicked!");
        Intent intent = new Intent(this, EncryptActivity.class);
        startActivity(intent);
    }
}
