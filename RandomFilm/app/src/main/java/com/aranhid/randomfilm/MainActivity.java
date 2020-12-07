package com.aranhid.randomfilm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView filmsView;
    Button button;
    List<String> randomFilms;
    Resources resources;
    Integer iterator = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        filmsView = findViewById(R.id.filmsView);
        button = findViewById(R.id.button);
        resources = getResources();
        String[] films = resources.getStringArray(R.array.films_list);
        randomFilms = Arrays.asList(films);
    }

    public void onClick(View view) {
        if (iterator == 0)
        {
            button.setText(R.string.button_text);
            Collections.shuffle(randomFilms);
        }
        if (iterator < randomFilms.size())
        {
            filmsView.setText((randomFilms.get(iterator)));
            iterator++;
        }
        else
        {
            filmsView.setText(R.string.end_of_filmsList);
            button.setText(R.string.discard);
            iterator = 0;
        }
    }
}