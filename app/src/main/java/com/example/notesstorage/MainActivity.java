package com.example.notesstorage;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> listNoteItems = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView lvNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.lvNotes = findViewById(R.id.lvNotes);
        this.adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.listNoteItems);
        this.lvNotes.setAdapter(adapter);

        lvNotes.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        lvNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (lvNotes.getChoiceMode() == ListView.CHOICE_MODE_NONE) {
                    return;
                }

                lvNotes.setItemChecked(position, true);
                DeleteNoteActivity.removeSelectedNote(MainActivity.this, listNoteItems, adapter, lvNotes, position);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notes_options_menu, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadNotesFromExternalStorage();
        lvNotes.setChoiceMode(ListView.CHOICE_MODE_NONE);
    }

    private void loadNotesFromExternalStorage() {
        try {
            File externalFile = new File(getExternalFilesDir(null), "NotesFile");
            BufferedReader reader = new BufferedReader(new FileReader(externalFile));
            String line;
            listNoteItems.clear();
            while ((line = reader.readLine()) != null) {
                listNoteItems.add(line);
            }
            adapter.notifyDataSetChanged();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_note:
                lvNotes.setChoiceMode(ListView.CHOICE_MODE_NONE);
                startActivity(AddNoteActivity.class);
                return true;
            case R.id.remove_note:
                Toast.makeText(getApplicationContext(), "Please select a note to remove", Toast.LENGTH_SHORT).show();
                lvNotes.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}