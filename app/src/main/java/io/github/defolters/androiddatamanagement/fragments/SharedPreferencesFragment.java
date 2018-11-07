package io.github.defolters.androiddatamanagement.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.github.defolters.androiddatamanagement.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SharedPreferencesFragment extends Fragment {
    private static final String PREFS_NAME = "settings";
    SharedPreferences.OnSharedPreferenceChangeListener listener;

    public SharedPreferencesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shared_preferences, container, false);

        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREFS_NAME, 0);
        final TextView helloTextView = view.findViewById(R.id.hello_text_shared_preferences);
        helloTextView.setText(getString(R.string.hello_text_shared_preferences,sharedPreferences.getString("name",getString(R.string.hello_text_shared_preferences))));

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (isAdded()) {
                    helloTextView.setText(getActivity().getSharedPreferences(PREFS_NAME, 0).getString(key,getString(R.string.hello_text_shared_preferences)));
                    Log.d("shared","isAdded() == true!!!!");
                } else {
                    Log.d("shared","isAdded() == false");
                }
            }
        };
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);

        Button setName = view.findViewById(R.id.set_name_button);
        setName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Set name");

                final LayoutInflater layoutInflater = getActivity().getLayoutInflater();
                final View dialogView = layoutInflater.inflate(R.layout.one_input_dialog, null);
                final EditText editText = dialogView.findViewById(R.id.edit_dialog);
                builder.setView(dialogView);

                builder.setPositiveButton("change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREFS_NAME, 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("name", editText.getText().toString());
                        editor.apply();
                    }
                });
                builder.show();
            }
        });

        Button deleteName = view.findViewById(R.id.delete_name_button);
        deleteName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Delete name");
                builder.setMessage("Are you sure, man?");
                builder.setPositiveButton("Yep", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("name");
                        editor.apply();
                    }
                });
                builder.setNegativeButton("No! It was mistake!", null);
                builder.show();
            }
        });
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();

        getActivity().getSharedPreferences(PREFS_NAME, 0).unregisterOnSharedPreferenceChangeListener(listener);
        Log.d("shared", "unregisterOnSharedPreferenceChangeListener");
    }

}
