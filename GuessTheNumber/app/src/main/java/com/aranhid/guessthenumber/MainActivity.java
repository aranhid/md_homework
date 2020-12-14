package com.aranhid.guessthenumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_LOWER_NUMBER = "com.aranhid.guessthenumber.EXTRA_LOWER_NUMBER";
    public static final String EXTRA_UPPER_NUMBER = "com.aranhid.guessthenumber.EXTRA_UPPER_NUMBER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openGameActivity(View view){
        Intent intent = new Intent(this, GameActivity.class);
        EditText lowerNumberEditText = findViewById(R.id.lowerNumber);
        EditText upperNumberEditText = findViewById(R.id.upperNumber);
        Integer lowerNumber = Integer.parseInt(lowerNumberEditText.getText().toString());
        Integer upperNumber = Integer.parseInt(upperNumberEditText.getText().toString());
        intent.putExtra(EXTRA_LOWER_NUMBER, lowerNumber);
        intent.putExtra(EXTRA_UPPER_NUMBER, upperNumber);
        startActivity(intent);
    }
}