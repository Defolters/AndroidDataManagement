package io.github.defolters.androiddatamanagment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataBaseOpenHelper extends SQLiteOpenHelper {
    private static DataBaseOpenHelper dataBaseOpenHelper = null;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE_NAME = "test";
    private static final String DATABASE_TABLE_CREATE =
            "CREATE TABLE " + DATABASE_TABLE_NAME + " (" +
            "FIRST TEXT, " +
            "SECOND TEXT" +
            ");";


    private DataBaseOpenHelper(Context context) {
        super(context, DATABASE_TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

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
        values.put("FIRST",first);
        values.put("SECOND",second);
        database.insert(DATABASE_TABLE_NAME, null, values);
        database.close();

        Log.d("TEST DATA", "addEntry(): " + first + " " + second);
    }

    public void addEntries(ArrayList<Entry> entries) {
        SQLiteDatabase database = getWritableDatabase();

        for (Entry entry : entries) {
            ContentValues values = new ContentValues();
            values.put("FIRST", entry.getFirst());
            values.put("SECOND",entry.getSecond());
            database.insert(DATABASE_TABLE_NAME, null, values);
        }

        database.close();

        Log.d("DATABASE", "addEntries()");
    }

    public void clear() {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(DATABASE_TABLE_NAME, null, null);
//        database.execSQL("DELETE FROM " + DATABASE_TABLE_NAME);
//        database.execSQL("VACUUM");
        database.close();
    }

    public int getCount() {
        SQLiteDatabase db = getReadableDatabase();
        int count = (int) DatabaseUtils.queryNumEntries(db, DATABASE_TABLE_NAME);
        db.close();

        Log.d("TEST DATA", "getCount(): " + count);
        return count;
//
//        final String regionQuery = "select Count(*) as count from test";
//        Cursor cur = null;
//        int result = 0;
//        if (db != null) {
//            try {
//                cur = db.rawQuery(regionQuery, null);
//                cur.moveToFirst();
//                result = cur.getInt(cur.getColumnIndexOrThrow("count"));
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            } finally {
//                if (cur != null) {
//                    cur.close();
//                }
//                db.close();
//            }
//        }
//        return result;
    }

    public ArrayList<Entry> getEntries() {
        Log.d("TEST_DATA", "DB getEntries:");

        ArrayList<Entry> entries = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c=database.rawQuery("SELECT * FROM "+DATABASE_TABLE_NAME, null);

        while (c.moveToNext()) {
            Entry entry = new Entry(c.getString(0), c.getString(1));
            entries.add(entry);
        }

        database.close();


        Log.d("TEST_DATA", "DB size:" + entries.size());
        return entries;
    }
}
