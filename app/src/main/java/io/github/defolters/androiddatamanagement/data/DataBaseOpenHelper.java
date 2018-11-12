package io.github.defolters.androiddatamanagement.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import io.github.defolters.androiddatamanagement.Entry;

public class DataBaseOpenHelper extends SQLiteOpenHelper {
    private static DataBaseOpenHelper dataBaseOpenHelper = null;
    private static String DEBUG_TAG = "DataBaseOpenHelper";
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_TABLE_NAME = "test";
    public static final String COL_FIRST = "FIRST";
    public static final String COL_SECOND = "SECOND";
    private static final String DATABASE_TABLE_CREATE =
            "CREATE TABLE " + DATABASE_TABLE_NAME + " (" +
            COL_FIRST + " TEXT, " +
            COL_SECOND + " TEXT);";


    private DataBaseOpenHelper(Context context) {
        super(context, DATABASE_TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: make good onUpgrade() method
        Log.w(DEBUG_TAG, "Upgrading database. Existing contents will be lost. ["
                + oldVersion + "]->[" + newVersion + "]");
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_NAME);
        onCreate(db);
    }

    public static DataBaseOpenHelper getDataBaseOpenHelper(Context context) {
        synchronized (DataBaseOpenHelper.class) {
            if (dataBaseOpenHelper == null) {
                dataBaseOpenHelper = new DataBaseOpenHelper(context);
            }

            return dataBaseOpenHelper;
        }
    }

    public void addEntry(String first, String second) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_FIRST,first);
        values.put(COL_SECOND,second);
        database.insert(DATABASE_TABLE_NAME, null, values);
        database.close();

        Log.d(DEBUG_TAG, "addEntry(): " + first + " " + second);
    }

    public void addEntries(ArrayList<Entry> entries) {
        SQLiteDatabase database = getWritableDatabase();

        for (Entry entry : entries) {
            ContentValues values = new ContentValues();
            values.put(COL_FIRST, entry.getFirst());
            values.put(COL_SECOND,entry.getSecond());
            database.insert(DATABASE_TABLE_NAME, null, values);
        }

        database.close();

        Log.d(DEBUG_TAG, "addEntries()");
    }

    public void clear() {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(DATABASE_TABLE_NAME, null, null);

        database.close();
    }

    public int getCount() {
        SQLiteDatabase db = getReadableDatabase();
        int count = (int) DatabaseUtils.queryNumEntries(db, DATABASE_TABLE_NAME);
        db.close();

        Log.d(DEBUG_TAG, "getCount(): " + count);
        return count;
    }

    public ArrayList<Entry> getEntries() {
        Log.d(DEBUG_TAG, "DB getEntries:");

        ArrayList<Entry> entries = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c=database.rawQuery("SELECT * FROM "+DATABASE_TABLE_NAME, null);

        while (c.moveToNext()) {
            Entry entry = new Entry(c.getString(0), c.getString(1));
            entries.add(entry);
        }

        database.close();

        Log.d(DEBUG_TAG, "DB size:" + entries.size());
        return entries;
    }
}
