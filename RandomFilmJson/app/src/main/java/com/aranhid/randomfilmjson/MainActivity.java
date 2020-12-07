package com.aranhid.randomfilmjson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "FilmsJson";

    TextView movieTitle;
    ImageView moviePoster;
    TextView movieDescription;
    TextView movieRating;
    TextView movieYear;

    Button button;
    ArrayList<Film> films;
    Resources resources;
    Integer iterator = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //filmsView = findViewById(R.id.filmsView);
        button = findViewById(R.id.button);
        resources = getResources();
        movieTitle = findViewById(R.id.movieTitle);
        moviePoster = findViewById(R.id.moviePoster);
        movieDescription = findViewById(R.id.movieDescription);
        movieRating = findViewById(R.id.movieRating);
        movieYear = findViewById(R.id.movieYear);

        try {
            InputStreamReader reader = new InputStreamReader(getAssets().open("movies.json"));
            Gson gson = new Gson();
            films = gson.fromJson(reader, new TypeToken<ArrayList<Film>>(){}.getType());
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View view) throws IOException {
        if (iterator == 0)
        {
            button.setText(R.string.button_text);
            Collections.shuffle(films);
        }
        if (iterator < films.size())
        {
            Film film = films.get(iterator);

            InputStream inputStream = getAssets().open(film.imagePath);
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            moviePoster.setImageDrawable(drawable);

            movieTitle.setText(film.title);
            movieDescription.setText(film.description);
            movieYear.setText(resources.getString(R.string.year, film.year));
            movieRating.setText(resources.getString(R.string.rating, film.rating));

            iterator++;
        }
        else
        {
            movieTitle.setText(R.string.end_of_filmsList);
            moviePoster.setImageDrawable(null);
            movieRating.setText(null);
            movieYear.setText(null);
            movieDescription.setText(null);
            button.setText(R.string.discard);
            iterator = 0;
        }
    }
}