package io.github.defolters.androiddatamanagement.data;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;

import io.github.defolters.androiddatamanagement.R;

public class FileManager {

    public static void createFileDialog(final File path, final Activity activity) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setTitle("Create file");

        final LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.two_input_dialog, null);

        final TextView fileNameTextView = dialogView.findViewById(R.id.first_text_dialog);
        fileNameTextView.setText("File name");

        final TextView textTextView = dialogView.findViewById(R.id.second_text_dialog);
        textTextView.setText("Enter text");

        final EditText fileName = dialogView.findViewById(R.id.first_edit_text);
        final EditText fileText = dialogView.findViewById(R.id.second_edit_text);

        alertDialog.setView(dialogView);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                createFile(path, fileName.getText().toString(), fileText.getText().toString());
                Snackbar.make(activity.findViewById(android.R.id.content),
                        fileName.getText().toString() + ".txt is created",
                        Snackbar.LENGTH_LONG).show();
            }
        });

        alertDialog.show();
    }

    public static void openFileDialog(final File path, final Activity activity) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setTitle("Select file");

        final String[] files = path.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name.endsWith(".txt")) {
                    return true;
                }
                return false;
            }
        });

        alertDialog.setItems(files, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    FileInputStream fis = new FileInputStream(new File(path, files[which]));
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader bufferedReader = new BufferedReader(isr);
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    bufferedReader.close();
                    isr.close();
                    fis.close();

                    AlertDialog.Builder content = new AlertDialog.Builder(activity);
                    content.setTitle(files[which]);
                    content.setMessage(stringBuilder);
                    content.setPositiveButton("Nice", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    content.show();
                }
                catch (IOException ex) {
                    Log.e("bug", ex.toString());

                }
            }
        });
        alertDialog.show();
    }

    public static void deleteFileDialog(final File path, final Activity activity) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setTitle("Select file");

        final String[] files = path.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name.endsWith(".txt")) {
                    return true;
                }
                return false;
            }
        });

        alertDialog.setItems(files, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                File file = new File(path,files[which]);
                if (file.delete()) {
                    Snackbar.make(activity.findViewById(android.R.id.content),
                            files[which] + " deleted",
                            Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(activity.findViewById(android.R.id.content),
                            files[which] + " not deleted",
                            Snackbar.LENGTH_LONG).show();
                }

            }
        });
        alertDialog.show();
    }

    public static void createFile(final File path, String name, String text) {
        try {
            File file = new File(path, name+".txt");
            FileWriter writer = new FileWriter(file);
            writer.append(text);
            writer.flush();
            writer.close();
        }
        catch (IOException ex) {

        }
    }

}
