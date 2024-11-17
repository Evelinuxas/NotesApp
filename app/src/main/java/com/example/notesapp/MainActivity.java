package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    private ArrayList<String> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sukuriame failą, jei jis dar neegzistuoja
        createFileIfNeeded();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // Set the toolbar as the action bar

        ListView listViewNotes = findViewById(R.id.listViewNotes);
        notesList = new ArrayList<>();

        loadNotes(); // Užkraunami užrašai

        // Jei nėra užrašų, rodykite vartotojui pranešimą
        if (notesList.isEmpty()) {
            Toast.makeText(this, "No notes found", Toast.LENGTH_SHORT).show();
        }

        // Sukuriame adapterį tik po to, kai užrašai yra užkrauti
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
            Log.e("MainActivity", "Error loading notes", e); // Naudojame Log.e() klaidoms registruoti
        }
    }

    private void createFileIfNeeded() {
        try {
            // Bandome atidaryti failą
            FileInputStream fis = openFileInput("notes.txt");
            fis.close();
        } catch (Exception e) {
            // Jei failas neegzistuoja, sukuriame tuščią failą
            try {
                FileOutputStream fos = openFileOutput("notes.txt", MODE_PRIVATE);
                fos.close();  // Uždarykite, kad failas būtų sukurtas
            } catch (Exception ex) {
                Log.e("MainActivity", "Error creating file", ex);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
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
