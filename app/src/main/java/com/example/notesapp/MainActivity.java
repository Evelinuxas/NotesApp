package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ListView rodo užrašus
        ListView listViewNotes = findViewById(R.id.listViewNotes);
        notesList = new ArrayList<>();

        loadNotes(); // Įkeliamas notes.txt turinys į sąrašą

        // Adapteris užrašų rodymui
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesList);
        listViewNotes.setAdapter(adapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setContentView(R.layout.activity_main);

        // ListView rodo užrašus
        ListView listViewNotes = findViewById(R.id.listViewNotes);
        notesList = new ArrayList<>();

        loadNotes(); // Įkeliamas notes.txt turinys į sąrašą

        // Adapteris užrašų rodymui
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesList);
        listViewNotes.setAdapter(adapter);
    }

    private void loadNotes() {
        try {
            FileInputStream fis = openFileInput("notes.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                notesList.add(line);
            }
            reader.close();
        } catch (Exception e) {
            Log.e("MainActivity", "Error loading notes");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add_note) {
            startActivity(new Intent(this, AddNoteActivity.class));
            return true;
        } else if (item.getItemId() == R.id.menu_delete_note) {
            startActivity(new Intent(this, DeleteNoteActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

