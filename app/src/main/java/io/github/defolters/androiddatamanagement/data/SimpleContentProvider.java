package io.github.defolters.androiddatamanagement.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class SimpleContentProvider extends ContentProvider {
    private DataBaseOpenHelper dataBaseOpenHelper;
    public static final String AUTHORITY = "io.github.defolters.androiddatamanagement.data.SimpleContentProvider";
    public static final String PATH = "/data";
    public static final int DATA = 1;
    public static final int DATA_ID = 2;

    private static final String DATA_BASE_PATH = "/data";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + PATH);

    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/cp-data";
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/cp-data";

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, PATH, DATA);
        sURIMatcher.addURI(AUTHORITY, PATH + "/#", DATA_ID);
    }

    @Override
    public boolean onCreate() {
        dataBaseOpenHelper = DataBaseOpenHelper.getDataBaseOpenHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = dataBaseOpenHelper.getReadableDatabase();
        Cursor cursor = null;

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case DATA:
                cursor = database.query(DataBaseOpenHelper.DATABASE_TABLE_NAME, projection, selection,selectionArgs,null,null,null);
                break;
            case DATA_ID:
            // TODO: wrong query
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
