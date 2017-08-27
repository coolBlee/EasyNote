package com.coolblee.easynote.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.coolblee.easynote.provider.EasyNote.Notes;

import java.util.HashMap;

/**
 * Created by Blee on 2015/12/24.
 */
public class EasyNoteProvider extends ContentProvider {
    private static final String TAG = EasyNoteProvider.class.getSimpleName();

    private EasyNoteDatabaseHelper mEasyNoteHelper;

    private static final int NOTES = 1;
    private static final int NOTE_ID = 2;
    private static final UriMatcher sUriMatcher;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // Add a pattern that routes URIs terminated with "notes" to a NOTES operation
        sUriMatcher.addURI(EasyNote.AUTHORITY, "notes", NOTES);

        // Add a pattern that routes URIs terminated with "notes" plus an integer
        // to a note ID operation
        sUriMatcher.addURI(EasyNote.AUTHORITY, "notes/#", NOTE_ID);
    }

    /**
     * A projection map used to select columns from the database
     */
    private static HashMap<String, String> sNotesProjectionMap;

    static {
        // Creates a new projection map instance. The map returns a column name
        // given a string. The two are usually equal.
        sNotesProjectionMap = new HashMap<String, String>();

        // Maps the string "_ID" to the column name "_ID"
        sNotesProjectionMap.put(Notes._ID, Notes._ID);
        sNotesProjectionMap.put(Notes.CREATED_TIME, Notes.CREATED_TIME);
        sNotesProjectionMap.put(Notes.MODIFY_TIME, Notes.MODIFY_TIME);
        sNotesProjectionMap.put(Notes.FAVORITE, Notes.FAVORITE);
        sNotesProjectionMap.put(Notes.BACKGROUND_COLOR, Notes.BACKGROUND_COLOR);
        sNotesProjectionMap.put(Notes.TITLE, Notes.TITLE);
        sNotesProjectionMap.put(Notes.DETAIL, Notes.DETAIL);
    }

    @Override
    public boolean onCreate() {
        mEasyNoteHelper = getDatabaseHelper(getContext());
        return true;
    }

    private EasyNoteDatabaseHelper getDatabaseHelper(final Context context) {
        return EasyNoteDatabaseHelper.getInstence(context);
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        // Constructs a new query builder and sets its table name
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(EasyNoteDatabaseHelper.Tables.NOTES);

        /**
         * Choose the projection and adjust the "where" clause based on URI pattern-matching.
         */
        switch (sUriMatcher.match(uri)) {
            case NOTES:
                qb.setProjectionMap(sNotesProjectionMap);
                break;
            case NOTE_ID:
                qb.setProjectionMap(sNotesProjectionMap);
                final String noteId = uri.getPathSegments().get(Notes.NOTE_ID_PATH_POSITION);
                qb.appendWhere(Notes._ID + " = " + noteId);
                break;
            default:
                throw new IllegalArgumentException("UnKnown URI " + uri);
        }

        // Opens the database object in "read" mode, since no writes need to be done.
        SQLiteDatabase db = mEasyNoteHelper.getReadableDatabase();

        /*
        * Performs the query. If no problems occur trying to read the database, then a Cursor
        * object is returned; otherwise, the cursor variable contains null. If no records were
        * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
        */
        Cursor c = qb.query(
                db,                // The database to query
                projection,        // The columns to return from the query
                selection,         // The columns for the where clause
                selectionArgs,     // The values for the where clause
                null,              // don't group the rows
                null,              // don't filter by row groups
                sortOrder          // The sort order
        );

        // Tells the Cursor what URI to watch, so it knows when its source data changes
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        /**
         * Chooses the MIME type based on the incoming URI pattern
         */
        switch (sUriMatcher.match(uri)) {
            case NOTES:

        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {

        // Validates the incoming URI. Only the full provider URI is allowed for inserts.
        if (sUriMatcher.match(uri) != NOTES) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        ContentValues  values;
        if(null != initialValues){
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }

        Long now = Long.valueOf(System.currentTimeMillis());

        if(!values.containsKey(Notes.CREATED_TIME)){
            values.put(Notes.CREATED_TIME, now);
        }

        if(!values.containsKey(Notes.MODIFY_TIME)){
            values.put(Notes.MODIFY_TIME, now);
        }

        // Opens the database object in "write" mode.
        SQLiteDatabase db = mEasyNoteHelper.getWritableDatabase();

        long rowId = db.insert(EasyNoteDatabaseHelper.Tables.NOTES, null, values);

        if(rowId > 0){
            // Creates a URI with the note ID pattern and the new row ID appended to it.
            Uri noteUri = ContentUris.withAppendedId(Notes.CONTENT_ID_URI_BASE, rowId);

            // Notifies observers registered against this provider that the data changed.
            getContext().getContentResolver().notifyChange(noteUri, null);
            return noteUri;
        }

        // If the insert didn't succeed, then the rowID is <= 0. Throws an exception.
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count;
        // Opens the database object in "write" mode.
        SQLiteDatabase qb = mEasyNoteHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {
            case NOTES:
                count = qb.delete(EasyNoteDatabaseHelper.Tables.NOTES, selection, selectionArgs);
                break;
            case NOTE_ID:
                final String noteId = uri.getPathSegments().get(Notes.NOTE_ID_PATH_POSITION);
                String finalSelection = Notes._ID + " = " + noteId;
                if(null != selection) {
                    finalSelection = finalSelection + " AND " + selection;
                }
                count = qb.delete(EasyNoteDatabaseHelper.Tables.NOTES, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count;
        // Opens the database object in "write" mode.
        SQLiteDatabase qb = mEasyNoteHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {
            case NOTES:
                count = qb.update(EasyNoteDatabaseHelper.Tables.NOTES, values, selection, selectionArgs);
                break;
            case NOTE_ID:
                final String noteId = uri.getPathSegments().get(Notes.NOTE_ID_PATH_POSITION);
                String finalSelection = Notes._ID + " = " + noteId;
                if(null != selection) {
                    finalSelection = finalSelection + " AND " + selection;
                }
                count = qb.update(EasyNoteDatabaseHelper.Tables.NOTES, values, finalSelection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }
}
