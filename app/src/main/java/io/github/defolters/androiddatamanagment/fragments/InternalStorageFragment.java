package io.github.defolters.androiddatamanagment.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;

import io.github.defolters.androiddatamanagment.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InternalStorageFragment extends Fragment {

    public InternalStorageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_internal_storage, container, false);

        final TextView filesPath = view.findViewById(R.id.internal_files_path);
        final File filesDir = getActivity().getFilesDir();
        filesPath.setText("Files location: "+ filesDir);

        final TextView cacheFilesPath = view.findViewById(R.id.internal_cache_path);
        final File cacheFilesDir = getActivity().getCacheDir();
        cacheFilesPath.setText("Cache location: "+ cacheFilesDir);

        final Button createFile = view.findViewById(R.id.internal_create_file_button);
        createFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFileDialog(filesDir);
            }
        });

        final Button openFile = view.findViewById(R.id.internal_open_file_button);
        openFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileDialog(filesDir);
            }
        });

        final Button deleteFile = view.findViewById(R.id.internal_delete_file_button);
        deleteFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFileDialog(filesDir);
            }
        });

        final Button createCacheFile = view.findViewById(R.id.internal_create_cache_button);
        createCacheFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFileDialog(cacheFilesDir);
            }
        });

        final Button openCacheFile = view.findViewById(R.id.internal_open_cache_button);
        openCacheFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileDialog(cacheFilesDir);
            }
        });

        final Button deleteCacheFile = view.findViewById(R.id.internal_delete_cache_button);
        deleteCacheFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFileDialog(cacheFilesDir);
            }
        });

        return view;
    }

    private void createFileDialog(final File path) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Create file");

        final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                Snackbar.make(getActivity().findViewById(android.R.id.content),
                        fileName.getText().toString() + ".txt is created",
                        Snackbar.LENGTH_LONG).show();
            }
        });

        alertDialog.show();
    }

    private void openFileDialog(final File path) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
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

                    AlertDialog.Builder content = new AlertDialog.Builder(getActivity());
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

    private void deleteFileDialog(final File path) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
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
                    Snackbar.make(getActivity().findViewById(android.R.id.content),
                            files[which] + " deleted",
                            Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(getActivity().findViewById(android.R.id.content),
                            files[which] + " not deleted",
                            Snackbar.LENGTH_LONG).show();
                }

            }
        });
        alertDialog.show();
    }

    private void createFile(final File path, String name, String text) {
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
