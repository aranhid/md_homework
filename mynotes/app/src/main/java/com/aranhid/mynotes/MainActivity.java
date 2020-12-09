package com.aranhid.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "NotesApp";

    ListView notesListView;
    TextView notesCount;

    SQLiteDatabase notesDatabase;
    SimpleCursorAdapter simpleCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        notesDatabase = databaseHelper.getWritableDatabase();

        Cursor cursor = notesDatabase.rawQuery("SELECT * FROM notes", null);

        String[] from = new String[] {"title", "text", "date"};
        int[] to = new int[] {R.id.title, R.id.text, R.id.date};
        simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.note_item, cursor, from, to, 0);

        notesListView = findViewById(R.id.notes_list);
        notesListView.setAdapter(simpleCursorAdapter);

        updateNotesCount(cursor);
    }

    public void onClick(View view) {
        EditText title = findViewById(R.id.title_input);
        EditText text = findViewById(R.id.text_input);
        DatePicker datePicker = findViewById(R.id.datePicker);
        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());

        ContentValues values = new ContentValues();
        values.put("title", String.valueOf(title.getText()));
        values.put("text", String.valueOf(text.getText()));
        values.put("date", new SimpleDateFormat("dd.MM.yyyy").format(calendar.getTime()));
        notesDatabase.insert("notes", null, values);

        Cursor cursor = notesDatabase.rawQuery("SELECT * FROM notes", null);

        simpleCursorAdapter.changeCursor(cursor);
        simpleCursorAdapter.notifyDataSetChanged();

        updateNotesCount(cursor);

        title.setText("");
        text.setText("");
    }

    private void updateNotesCount(Cursor cursor) {
        notesCount = findViewById(R.id.notes_count);
        notesCount.setText(getString(R.string.notes_count, cursor.getCount()));
    }
}