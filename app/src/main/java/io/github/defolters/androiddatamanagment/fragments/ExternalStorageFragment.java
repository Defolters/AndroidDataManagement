package io.github.defolters.androiddatamanagment.fragments;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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

import io.github.defolters.androiddatamanagment.FileManager;
import io.github.defolters.androiddatamanagment.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExternalStorageFragment extends Fragment {


    public ExternalStorageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_external_storage, container, false);

        Button createPublicFile = view.findViewById(R.id.external_public_create_file_button);
        Button openPublicFile = view.findViewById(R.id.external_public_open_file_button);
        Button deletePublicFile = view.findViewById(R.id.external_public_delete_file_button);

        createPublicFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExternalStorageWritable()) {
                    FileManager.createFileDialog(getPublicDocumentsDirectory("doc"), getActivity());
                }
            }
        });

        openPublicFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExternalStorageReadable()) {
                    FileManager.openFileDialog(getPublicDocumentsDirectory("doc"), getActivity());
                }
            }
        });

        deletePublicFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExternalStorageWritable()) {
                    FileManager.deleteFileDialog(getPublicDocumentsDirectory("doc"), getActivity());
                }
            }
        });

        Button createPrivateFile = view.findViewById(R.id.external_private_create_file_button);
        Button openPrivateFile = view.findViewById(R.id.external_private_open_file_button);
        Button deletePrivateFile = view.findViewById(R.id.external_private_delete_file_button);

        createPrivateFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExternalStorageWritable()) {
                    FileManager.createFileDialog(getPrivateDocumentsDirectory("doc", getActivity()), getActivity());
                }
            }
        });

        openPrivateFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExternalStorageReadable()) {
                    FileManager.openFileDialog(getPrivateDocumentsDirectory("doc", getActivity()), getActivity());
                }
            }
        });

        deletePrivateFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExternalStorageWritable()) {
                    FileManager.deleteFileDialog(getPrivateDocumentsDirectory("doc", getActivity()), getActivity());
                }
            }
        });

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            AlertDialog.Builder content = new AlertDialog.Builder(getActivity());
            content.setTitle("Warning");
            content.setMessage("Please, allow app to work with external storage in order to work correctly");
            content.setPositiveButton("I agree", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                }
            });

            content.show();
        }

        return view;
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public File getPublicDocumentsDirectory(String folderName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), folderName);
        if (!file.mkdirs()) {
            Log.e("PUBLIC_DIR", "Directory not created");
        }
        Log.d("PUBLIC_DIR", file.toString());

        return file;
    }

    public File getPrivateDocumentsDirectory(String folderName, Context context) {
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_DOCUMENTS).toURI());
        if (!file.mkdirs()) {
            Log.e("PRIVATE_DIR", "Directory not created");
        }
        Log.d("PRIVATE_DIR", file.toString());

        return file;
    }
}