package com.aranhid.azuredictors;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    DictorAdapter dictorAdapter;
    ArrayList<Dictor> dictors = new ArrayList<>();
    String key = "";

    Gson gson = new Gson();

    Retrofit retrofit = new Retrofit.Builder()
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .baseUrl(AzureDictorsAPI.API_URL)
                            .build();

    AzureDictorsAPI api = retrofit.create(AzureDictorsAPI.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dictorAdapter = new DictorAdapter(this, dictors);

        Call<String> call = api.getToken();
        call.enqueue(new TokenCallback());

        ListView listView = findViewById(R.id.dictorList);
        listView.setAdapter(dictorAdapter);
    }

    public void  onDictorClick(View view) {
        Call<ArrayList<Dictor>> dictorCall = api.getDictors("https://eastasia.tts.speech.microsoft.com/cognitiveservices/voices/list", "Bearer " + key);
        dictorCall.enqueue(new DictorCallback());
    }

    class TokenCallback implements Callback<String> {

        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            if (response.isSuccessful()) {
                key = response.body();
                Log.d("mytag", "response: " + response.body());
            } else
                Log.d("mytag", "error " + response.code());
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
            Log.d("mytag", "error " + t.getLocalizedMessage());

        }
    }

    class DictorCallback implements Callback<ArrayList<Dictor>> {

        @Override
        public void onResponse(Call<ArrayList<Dictor>> call, Response<ArrayList<Dictor>> response) {
            if (response.isSuccessful()) {
                Log.d("mytag", "onResponse: " + response.body());
                dictors.clear();
                dictors.addAll(response.body());
                dictorAdapter.notifyDataSetChanged();
            }
            else {
                Log.d("mytag", "onResponse error: " + response.code());
            }
        }

        @Override
        public void onFailure(Call<ArrayList<Dictor>> call, Throwable t) {
            Log.d("mytag", "onFailure: " + t.getLocalizedMessage());
        }
    }
}