package com.example.mycustomadapter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    ArrayList<User> users;
    UserListAdapter userListAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list);

        Gson gson = new Gson();

        InputStreamReader reader = new InputStreamReader(getResources().openRawResource(R.raw.users));
        users = gson.fromJson(reader, new TypeToken<ArrayList<User>>(){}.getType());

        userListAdapter = new UserListAdapter(this, users);

        listView.setAdapter(userListAdapter);
    }

    public void onClick(View view){
        Button button = (Button) view;
        switch (button.getId()){
            case R.id.sortByName:
                Collections.sort(users, new UsersNameComparator());
                break;
            case R.id.sortByPhone:
                Collections.sort(users, new UsersPhoneComparator());
                break;
            case R.id.sortByGender:
                Collections.sort(users, new UsersGenderComparator());
                break;
        }
        userListAdapter.notifyDataSetChanged();
    }
}