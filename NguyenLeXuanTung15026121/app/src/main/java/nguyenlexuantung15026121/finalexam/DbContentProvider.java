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
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case PRODUCT:
                cursor = db.query("products", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case PRODUCT_ID:
                long _id = ContentUris.parseId(uri);
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
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
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

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
