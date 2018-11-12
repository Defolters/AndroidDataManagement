package io.github.defolters.androiddatamanagement.fragments;


import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import io.github.defolters.androiddatamanagement.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssetsFragment extends Fragment {

    public AssetsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_assets, container, false);

        final ImageView imageView = view.findViewById(R.id.assets_image);
        final Button loadButton = view.findViewById(R.id.assets_load_button);
        final TextView directoryView = view.findViewById(R.id.assets_directory);
        final Button directoryButton = view.findViewById(R.id.assets_directory_button);
        final Button hideButton = view.findViewById(R.id.assets_hide_button);


        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imageView.getDrawable() == null) {

                    imageView.setImageBitmap(getBitmapFromAssets("cat.jpg"));
                    Snackbar.make(getActivity().findViewById(android.R.id.content),
                            "cat.jpg loaded from assets", Snackbar.LENGTH_LONG).show();
                }
                else {

                        Snackbar.make(getActivity().findViewById(android.R.id.content),
                            "cat.jpg is already loaded", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        hideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(getActivity().findViewById(android.R.id.content),
                        "Image is hidden now", Snackbar.LENGTH_LONG).show();

                imageView.setImageDrawable(null);
            }
        });

        directoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssetManager assetManager = getActivity().getAssets();

                if (assetManager == null) {
                    return;
                }

                try {
                    String directory = "";
                    for (String file : assetManager.list("")) {
                        directory += file + "\n";
                    }
                    directoryView.setText(directory);
                }
                catch (IOException ex) {

                }
            }
        });

        return view;
    }

    public Bitmap getBitmapFromAssets(String fileName) {
        try {
            AssetManager assetManager = getActivity().getAssets();

            if (assetManager == null) {
                return null;
            }

            InputStream inputStream = assetManager.open(fileName);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            inputStream.close();
            return bitmap;
        }
        catch (IOException ex) {
            return null;
        }
    }

}
