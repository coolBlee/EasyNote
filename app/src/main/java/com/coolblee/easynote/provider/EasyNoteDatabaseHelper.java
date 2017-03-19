package com.coolblee.easynote.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.coolblee.easynote.provider.EasyNote.Notes;

/**
 * Created by Blee on 2015/12/24.
 */
public class EasyNoteDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = EasyNoteDatabaseHelper.class.getSimpleName();
    private static int DATABASE_VERSION = 1;

    public interface Tables {
        String NOTES = "notes";
    }

    private static final String DATABASE_NAME = "easynote.db";
    private static EasyNoteDatabaseHelper sSingleton = null;

    public static synchronized EasyNoteDatabaseHelper getInstence(Context context) {
        if (null == sSingleton) {
            sSingleton = new EasyNoteDatabaseHelper(context, DATABASE_NAME);
        }
        return sSingleton;
    }

    protected EasyNoteDatabaseHelper(Context context, String name) {
        super(context, name, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Tables.NOTES + " ("
                + Notes._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Notes.CREATED_TIME + " INTEGER,"
                + Notes.MODIFY_TIME + " INTEGER,"
                + Notes.FAVORITE + " INTEGER NOT NULL DEFAULT 0,"
                + Notes.BACKGROUND_COLOR + " INTEGER,"
                + Notes.TITLE + " TEXT,"
                + Notes.DETAIL + " TEXT"
                + ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Kills the table and existing data
        db.execSQL("DROP TABLE IF EXISTS " + Tables.NOTES);

        // Recreates the database with a new version
        onCreate(db);
    }
}
