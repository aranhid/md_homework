package com.aranhid.guessthenumber;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    Integer lowerNumber = 0;
    Integer upperNumber = 0;
    Integer averageNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        lowerNumber = intent.getIntExtra(MainActivity.EXTRA_LOWER_NUMBER, 0);
        upperNumber = intent.getIntExtra(MainActivity.EXTRA_UPPER_NUMBER, 0);

        Game();
    }

    private void Game() {
        TextView questionTextView = findViewById(R.id.questionTextView);

        if(upperNumber - lowerNumber >= 2) {
            averageNumber = (upperNumber + lowerNumber) / 2;
            questionTextView.setText(getString(R.string.more, averageNumber));
        }
        else {
            questionTextView.setText(getString(R.string.is_this_number, upperNumber));
        }
    }

    private void gameOver(Integer number) {
        TextView questionTextView = findViewById(R.id.questionTextView);
        questionTextView.setText(getString(R.string.number, number));

        LinearLayout linearLayout = findViewById(R.id.buttonLayout);
        linearLayout.setVisibility(View.INVISIBLE);
    }

    public void onClick(View view) {
        Button button = (Button) view;
        if (upperNumber - lowerNumber >= 2) {
            switch (button.getId()) {
                case R.id.noButton:
                    upperNumber = averageNumber;
                    break;
                case R.id.yesButton:
                    lowerNumber = averageNumber;
                    break;
            }
            Game();
        }
        else {
            switch (button.getId()) {
                case R.id.noButton:
                    gameOver(lowerNumber);
                    break;
                case R.id.yesButton:
                    gameOver(upperNumber);
                    break;
            }
        }
    }
}
