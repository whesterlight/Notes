package com.example.notesstorage;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AddNoteActivity extends AppCompatActivity {

    EditText titleInput;
    EditText descriptionInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        titleInput = findViewById(R.id.titleinput);
        descriptionInput = findViewById(R.id.descriptioninput);
    }

    public void onBtnSaveAndCloseClick(View view) {
        String titleToAdd = titleInput.getText().toString().trim();
        String descriptionToAdd = descriptionInput.getText().toString().trim();

        if (titleToAdd.isEmpty() || descriptionToAdd.isEmpty()) {

            Toast.makeText(this, "Please fill in both title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            File externalFile = new File(getExternalFilesDir(null), "NotesFile");
            FileWriter writer = new FileWriter(externalFile, true);
            writer.append(titleToAdd).append(": ").append(descriptionToAdd).append("\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setResult(RESULT_OK);
        finish();
    }
}