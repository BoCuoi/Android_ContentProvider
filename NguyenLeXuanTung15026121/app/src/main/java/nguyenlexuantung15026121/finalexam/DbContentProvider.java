package nguyenlexuantung15026121.finalexam;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;

public class DbContentProvider extends ContentProvider {
    static final String PROVIDER_NAME = "nguyenlexuantung15026121.finalexam"; // authority argm

    static final String PRODUCTS_URL = "content://" + PROVIDER_NAME + "/products";
    static final Uri PRODUCTS_CONTENT_URI = Uri.parse(PRODUCTS_URL);

    static final String PRODUCERS_URL = "content://" + PROVIDER_NAME + "/producers";
    static final Uri PRODUCERS_CONTENT_URI = Uri.parse(PRODUCERS_URL);

    private static final int PRODUCT = 100;
    private static final int PRODUCT_ID = 101;
    private static final int PRODUCER = 200;
    private static final int PRODUCER_ID = 201;

//    private static HashMap<String, String> PRODUCTS_PROJECTION_MAP;
//    private static HashMap<String, String> PRODUCERS_PROJECTION_MAP;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "products", PRODUCT);
        uriMatcher.addURI(PROVIDER_NAME, "products/#", PRODUCT_ID);
        uriMatcher.addURI(PROVIDER_NAME, "producers", PRODUCER);
        uriMatcher.addURI(PROVIDER_NAME, "producers/#", PRODUCER_ID);
    }

    private DatabaseHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        return db != null;
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        long _id;
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case PRODUCT:
                cursor = db.query("products", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case PRODUCT_ID:
                _id = ContentUris.parseId(uri);
                cursor = db.query("products", projection, "product_id=?", new String[]{String.valueOf(_id)},
                        null, null,
                        sortOrder);
                break;
            case PRODUCER:
                cursor = db.query("producers", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case PRODUCER_ID:
                _id = ContentUris.parseId(uri);
                cursor = db.query("producers", projection, "producer_id=?", new String[]{String.valueOf(_id)},
                        null, null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unkown uri: " + uri);
        }
        db.close();
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        long rowId;
        Uri returnUri;
        switch (uriMatcher.match(uri)) {
            case PRODUCT:
                rowId = db.insert("products", null, values);
                if (rowId > 0) {
                    returnUri = ContentUris.withAppendedId(PRODUCTS_CONTENT_URI, rowId);
                } else {
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            case PRODUCER:
                rowId = db.insert("producers", null, values);
                if (rowId > 0) {
                    returnUri = ContentUris.withAppendedId(PRODUCERS_CONTENT_URI, rowId);
                } else {
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        db.close();
        // Use this on the URI passed into the function to notify any observers that the uri has changed
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows;
        switch (uriMatcher.match(uri)) {
            case PRODUCT:
                rows = db.delete("products", selection, selectionArgs);
                break;
            case PRODUCER:
                rows = db.delete("producers", selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        //Because null could delete all rows
        if (selection == null || rows != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows;
        switch (uriMatcher.match(uri)) {
            case PRODUCT:
                rows = db.update("products", values, selection, selectionArgs);
                break;
            case PRODUCER:
                rows = db.update("producers", values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        if (rows != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rows;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
