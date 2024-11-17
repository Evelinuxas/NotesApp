package com.example.notesapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import android.util.Log;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextName, editTextContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextName = findViewById(R.id.editTextName);
        editTextContent = findViewById(R.id.editTextContent);
        Button buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(v -> saveNote());  // Naudojame lambda išraišką vietoj anoniminio klasės objekto

    }

    private void saveNote() {
        String name = editTextName.getText().toString().trim();
        String content = editTextContent.getText().toString().trim();

        if (name.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            FileOutputStream fos = openFileOutput("notes.txt", MODE_APPEND);
            fos.write((name + ": " + content + "\n").getBytes());
            fos.close();
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Log.e("AddNoteActivity", "Error saving note", e);  // Naudojame Log.e() klaidoms registruoti
        }
    }
}
