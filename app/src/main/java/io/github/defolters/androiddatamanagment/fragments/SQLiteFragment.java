package io.github.defolters.androiddatamanagment.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import io.github.defolters.androiddatamanagment.ExpandableListView;
import io.github.defolters.androiddatamanagment.ListAdapter;
import io.github.defolters.androiddatamanagment.R;
import io.github.defolters.androiddatamanagment.data.DataBaseOpenHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class SQLiteFragment extends Fragment implements AdapterView.OnItemClickListener {

    private DataBaseOpenHelper dataBaseOpenHelper;
    private ListAdapter listAdapter;

    public SQLiteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sqlite, container, false);

        dataBaseOpenHelper = DataBaseOpenHelper.getDataBaseOpenHelper(getActivity());
        listAdapter = new ListAdapter(getActivity(), dataBaseOpenHelper.getEntries());
        final ExpandableListView listView = view.findViewById(R.id.db_list);
        listView.setExpanded(true);
        listAdapter.notifyDataSetChanged();
        listView.setAdapter(listAdapter);
//        listView.setOnItemClickListener(this);

        Button addButton = view.findViewById(R.id.add_sqlite);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Add value");

                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflater.inflate(R.layout.two_input_dialog, null);
                final EditText firstEditText = dialogView.findViewById(R.id.first_edit_text);
                final EditText secondEditText = dialogView.findViewById(R.id.second_edit_text);

                builder.setView(dialogView);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataBaseOpenHelper.addEntry(firstEditText.getText().toString(),
                                                    secondEditText.getText().toString());
                        Snackbar.make(getActivity().findViewById(android.R.id.content),
                                "value added", Snackbar.LENGTH_SHORT).show();
                        listAdapter = new ListAdapter(getActivity(), dataBaseOpenHelper.getEntries());
                        listView.setAdapter(listAdapter);
                        listAdapter.notifyDataSetChanged();
                    }
                });
                //past data into database and show data
                builder.show();
                listAdapter.notifyDataSetChanged();
            }
        });

        Button clearButton = view.findViewById(R.id.clear_sqlite);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBaseOpenHelper.clear();
                listAdapter = new ListAdapter(getActivity(), dataBaseOpenHelper.getEntries());
                listView.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();
            }
        });

        Button updateButton = view.findViewById(R.id.update_sqlite);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listAdapter = new ListAdapter(getActivity(), dataBaseOpenHelper.getEntries());
                listView.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("itemClick", "pos: "+position+" id: " + id);
    }
}
