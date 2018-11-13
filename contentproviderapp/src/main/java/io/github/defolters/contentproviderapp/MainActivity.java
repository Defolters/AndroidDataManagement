package io.github.defolters.contentproviderapp;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EntriesAdapter entriesAdapter;
    private static final String AUTHORITY = "io.github.defolters.androiddatamanagement.data.SimpleContentProvider";
    private static final String PATH = "/data";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + PATH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        Button loadData = findViewById(R.id.load_data);

        loadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // query all data
                Cursor cursor = getContentResolver().query(CONTENT_URI, null, null, null, null);

                if (cursor == null) {
                    Log.w("CONTENT_PROVIDER", "cursor = null");
                    return;
                }

                ArrayList<Entry> entries = new ArrayList<>();

                if (cursor.moveToFirst()) {
                    do {
                        Entry entry = new Entry(cursor.getString(0), cursor.getString(1));
                        entries.add(entry);
                    } while (cursor.moveToNext());
                    Log.w("CONTENT_PROVIDER", "Some data to show");

                } else {
                    Log.w("CONTENT_PROVIDER", "Nothing to show");
                }

                // put data into list
                entriesAdapter = new EntriesAdapter(MainActivity.this, entries);
                recyclerView.setAdapter(entriesAdapter);
                entriesAdapter.notifyDataSetChanged();

                recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,LinearLayoutManager.VERTICAL));


                cursor.close();
            }
        });


    }
}
