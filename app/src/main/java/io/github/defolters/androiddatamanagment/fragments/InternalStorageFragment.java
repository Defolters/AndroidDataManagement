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

import io.github.defolters.androiddatamanagment.FileManager;
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
                FileManager.createFileDialog(filesDir, getActivity());
            }
        });

        final Button openFile = view.findViewById(R.id.internal_open_file_button);
        openFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileManager.openFileDialog(filesDir, getActivity());
            }
        });

        final Button deleteFile = view.findViewById(R.id.internal_delete_file_button);
        deleteFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileManager.deleteFileDialog(filesDir, getActivity());
            }
        });

        final Button createCacheFile = view.findViewById(R.id.internal_create_cache_button);
        createCacheFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileManager.createFileDialog(cacheFilesDir, getActivity());
            }
        });

        final Button openCacheFile = view.findViewById(R.id.internal_open_cache_button);
        openCacheFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileManager.openFileDialog(cacheFilesDir, getActivity());
            }
        });

        final Button deleteCacheFile = view.findViewById(R.id.internal_delete_cache_button);
        deleteCacheFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileManager.deleteFileDialog(cacheFilesDir, getActivity());
            }
        });

        return view;
    }


}
