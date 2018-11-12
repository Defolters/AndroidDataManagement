package io.github.defolters.androiddatamanagement.fragments;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

import io.github.defolters.androiddatamanagement.data.FileManager;
import io.github.defolters.androiddatamanagement.R;

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

        final TextView publicDirectoryTextView = view.findViewById(R.id.external_public_directory);
        final TextView privateDirectoryTextView = view.findViewById(R.id.external_private_directory);

        publicDirectoryTextView.setText("Location: " + getPublicDocumentsDirectory());
        privateDirectoryTextView.setText("Cache location: " + getPrivateDocumentsDirectory(getActivity()));

        Button createPublicFile = view.findViewById(R.id.external_public_create_file_button);
        Button openPublicFile = view.findViewById(R.id.external_public_open_file_button);
        Button deletePublicFile = view.findViewById(R.id.external_public_delete_file_button);

        createPublicFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExternalStorageWritable()) {
                    FileManager.createFileDialog(getPublicDocumentsDirectory(), getActivity());
                }
            }
        });

        openPublicFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExternalStorageReadable()) {
                    FileManager.openFileDialog(getPublicDocumentsDirectory(), getActivity());
                }
            }
        });

        deletePublicFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExternalStorageWritable()) {
                    FileManager.deleteFileDialog(getPublicDocumentsDirectory(), getActivity());
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
                    FileManager.createFileDialog(getPrivateDocumentsDirectory(getActivity()), getActivity());
                }
            }
        });

        openPrivateFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExternalStorageReadable()) {
                    FileManager.openFileDialog(getPrivateDocumentsDirectory(getActivity()), getActivity());
                }
            }
        });

        deletePrivateFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExternalStorageWritable()) {
                    FileManager.deleteFileDialog(getPrivateDocumentsDirectory(getActivity()), getActivity());
                }
            }
        });

        return view;
    }


    public boolean isExternalStorageWritable() {
        if (!isPermissionGranted()) {
            return false;
        }

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


    public boolean isExternalStorageReadable() {
        if (!isPermissionGranted()) {
            return false;
        }

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }


    public File getPublicDocumentsDirectory() {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS).toURI());

        if (!file.mkdirs()) {
            Log.e("PUBLIC_DIR", "Directory not created");
        }
        Log.d("PUBLIC_DIR", file.toString());

        return file;
    }


    public File getPrivateDocumentsDirectory(Context context) {
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_DOCUMENTS).toURI());
        if (!file.mkdirs()) {
            Log.e("PRIVATE_DIR", "Directory not created");
        }
        Log.d("PRIVATE_DIR", file.toString());

        return file;
    }


    /**
     * Check if permission granted and shows dialog, which request permission, if permission
     * is not granted.
     * @return true if granted, false if not granted
     */
    public boolean isPermissionGranted() {
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
            return false;
        }
        return true;
    }
}