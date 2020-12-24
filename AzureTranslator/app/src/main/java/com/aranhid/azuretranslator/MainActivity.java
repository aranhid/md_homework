package com.aranhid.azuretranslator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "mytag";

    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(AzureTranslationApi.API_URL)
            .build();

    Gson gson = new Gson();

    AzureTranslationApi api = retrofit.create(AzureTranslationApi.class);
    ArrayList<Language> languages = new ArrayList<>();
    ArrayList<TranslatedText> translatedTexts = new ArrayList<>();

    Spinner spinner;
    ArrayAdapter<Language> adapter;

    EditText inputText;
    TextView translatedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = findViewById(R.id.editText);
        translatedText = findViewById(R.id.translatedTextView);

        spinner = findViewById(R.id.spinner);
        adapter = new ArrayAdapter<Language>(this, android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        String lastUpdate = loadFromSharedPreferences("lastUpdate");

        if (lastUpdate.isEmpty()) {
            Log.d(TAG, "onCreate: load languages from server");
            Call<LanguagesResponse> call = api.getLanguages();
            call.enqueue(new LanguagesCallback());
        }
        else {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date currentDate = new Date();
            Date lastUpdateDate = new Date();

            try {
                lastUpdateDate = formatter.parse(lastUpdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            formatter = new SimpleDateFormat("HH:mm:ss");
            Date diff = new Date();

            try {
                diff = formatter.parse("24:00:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (currentDate.getTime() - lastUpdateDate.getTime() > diff.getTime()) {
                Log.d(TAG, "onCreate: load languages from server");
                Call<LanguagesResponse> call = api.getLanguages();
                call.enqueue(new LanguagesCallback());
            } else {
                Log.d(TAG, "onCreate: load languages from SharedPreferences");
                languages.clear();
                languages.addAll(gson.fromJson(loadFromSharedPreferences("languages"), new TypeToken<ArrayList<Language>>(){}.getType()));
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void onClick(View view) {
        String text = inputText.getText().toString();
        ArrayList<TranslateRequstItem> requstItems = new ArrayList<>();
        requstItems.add(new TranslateRequstItem(text));
        String toLang = spinner.getSelectedItem().toString();
        String shortLangName = "";

        for (Language language : languages) {
            if (language.nativeName == toLang) {
                shortLangName = language.shortName;
            }
        }

        String body = gson.toJson(requstItems);
        Call<List<TranslatorResponse>> translatedText = api.translate(body, shortLangName);
        translatedText.enqueue(new TranslatedTextCallback());
    }

    private void saveToSharedPreferences(String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private String loadFromSharedPreferences(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        return  sharedPreferences.getString(key, "");
    }

    class LanguagesCallback implements Callback<LanguagesResponse> {

        @Override
        public void onResponse(Call<LanguagesResponse> call, Response<LanguagesResponse> response) {
            if (response.isSuccessful()) {
                LanguagesResponse languagesResponse = response.body();
                languages.clear();
                languages.addAll(languagesResponse.getLanguagesList());
                adapter.notifyDataSetChanged();

                saveToSharedPreferences("languages", gson.toJson(languages));

                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                saveToSharedPreferences("lastUpdate", formatter.format(date));
            } else {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT);
                Log.d(TAG, "onResponse: " + response.code());
                Log.d(TAG, "onResponse: " + response.errorBody());
            }
        }

        @Override
        public void onFailure(Call<LanguagesResponse> call, Throwable t) {
            Log.d(TAG, "onFailure: " + t.toString());
            Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT);
        }
    }

    class TranslatedTextCallback implements Callback<List<TranslatorResponse>> {

        @Override
        public void onResponse(Call<List<TranslatorResponse>> call, Response<List<TranslatorResponse>> response) {
            if (response.isSuccessful()) {
                List<TranslatorResponse> test = response.body();
                translatedTexts = test.get(0).translations;
                StringBuilder stringBuilder = new StringBuilder();
                for (TranslatedText row : translatedTexts) {
                    stringBuilder.append(row.toString() + '\n');
                }
                translatedText.setText(stringBuilder);
            }
            else {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT);
                Log.d(TAG, "onResponse: " + response.code());
                Log.d(TAG, "onResponse: " + response.raw());
            }
        }

        @Override
        public void onFailure(Call<List<TranslatorResponse>> call, Throwable t) {
            Log.d(TAG, "onFailure: " + t.toString());
            Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT);
        }
    }
}