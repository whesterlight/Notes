package com.example.notesstorage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DeleteNoteActivity {

    public static void removeSelectedNote(final Context context, final ArrayList<String> listNoteItems,
                                          final ArrayAdapter<String> adapter, final ListView lvNotes,
                                          final int position) {
        showDeleteConfirmationDialog(context, listNoteItems, adapter, lvNotes, position);
    }

    private static void saveNotesToExternalStorage(Context context, ArrayList<String> listNoteItems) {
        try {
            File externalFile = new File(context.getExternalFilesDir(null), "NotesFile");
            FileWriter writer = new FileWriter(externalFile);

            for (String note : listNoteItems) {
                writer.append(note).append("\n");
            }
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void showDeleteConfirmationDialog(final Context context,
                                                     final ArrayList<String> listNoteItems,
                                                     final ArrayAdapter<String> adapter,
                                                     final ListView lvNotes,
                                                     final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listNoteItems.remove(position);
                        adapter.notifyDataSetChanged();
                        saveNotesToExternalStorage(context, listNoteItems);
                        lvNotes.setChoiceMode(ListView.CHOICE_MODE_NONE);
                        Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked Cancel button
                        lvNotes.setItemChecked(position, false);
                        lvNotes.setChoiceMode(ListView.CHOICE_MODE_NONE);
                        Toast.makeText(context, "Delete canceled", Toast.LENGTH_SHORT).show();
                    }
                })
                .create()
                .show();
    }
}