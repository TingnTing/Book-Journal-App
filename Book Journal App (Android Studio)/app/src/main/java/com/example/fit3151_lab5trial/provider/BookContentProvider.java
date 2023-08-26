package com.example.fit3151_lab5trial.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class BookContentProvider extends ContentProvider {

    BooksDatabase db;
    private final String tableName = "books";

    // to know the authority
    public static final String CONTENT_AUTHORITY = "fit2081.app.CHEN_TING";
    // address to access the database
    public static final Uri CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public BookContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deletionCount;


        deletionCount = db
                .getOpenHelper()
                .getWritableDatabase()
                .delete(tableName, selection, selectionArgs);

        return deletionCount;
    }

    @Override
    public String getType(Uri uri) {
        // the uri is just address to access the database, to check if it's working - URI checking
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        long rowId = db
                .getOpenHelper()  // library for android to access database
                .getWritableDatabase()  // to change info in database
                .insert(tableName, 0, values); // a container to use in another application to put in data to insert in this table

        return ContentUris.withAppendedId(CONTENT_URI, rowId); // item to contentURi with row id
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        db = BooksDatabase.getDatabase(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(tableName);
        String query = builder.buildQuery(projection, selection, null, null, sortOrder, null);

        final Cursor cursor = db
                .getOpenHelper()
                .getReadableDatabase()
                .query(query);

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int updateCount;
        updateCount = db
                .getOpenHelper()
                .getWritableDatabase()
                .update(tableName, 0, values, selection, selectionArgs);

        return updateCount;
    }
}