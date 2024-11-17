package com.example.notesapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DeleteNoteActivity extends AppCompatActivity {

    private Spinner spinnerNotes;
    private ArrayList<String> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);

        spinnerNotes = findViewById(R.id.spinnerNotes);
        Button buttonDelete = findViewById(R.id.buttonDelete);
        notesList = new ArrayList<>();

        loadNotes();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, notesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNotes.setAdapter(adapter);

        buttonDelete.setOnClickListener(v -> deleteNote());
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
            Toast.makeText(this, "Error loading notes", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteNote() {
        String selectedNote = (String) spinnerNotes.getSelectedItem();
        notesList.remove(selectedNote);

        try {
            FileOutputStream fos = openFileOutput("notes.txt", MODE_PRIVATE);
            for (String note : notesList) {
                fos.write((note + "\n").getBytes());
            }
            fos.close();
            Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Error deleting note", Toast.LENGTH_SHORT).show();
        }
    }
}

