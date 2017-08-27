package com.coolblee.easynote.main;

import com.coolblee.easynote.provider.EasyNote.Notes;

import android.content.Context;
import android.content.CursorLoader;
import android.net.Uri;

/**
 * Created by Blee on 1/17/2016.
 */
public class NotesLoader extends CursorLoader{
    private static final String[] PROJECTTION = new String[] {
            Notes._ID,
            Notes.CREATED_TIME,
            Notes.MODIFY_TIME,
            Notes.FAVORITE,
            Notes.BACKGROUND_COLOR,
            Notes.TITLE,
            Notes.DETAIL
    };

    public static final int NOTE_ID = 0;
    public static final int NOTE_CREATED_TIME = 1;
    public static final int NOTE_MODIFY_TIME = 2;
    public static final int NOTE_FAVORITE = 3;
    public static final int NOTE_BACKGROUND_COLOR = 4;
    public static final int NOTE_TITLE = 5;
    public static final int NOTE_DETAIL = 6;

    public NotesLoader(Context context) {
        super(context, Notes.CONTENT_URI, PROJECTTION, null, null, null);
    }

    public NotesLoader(Context context, Uri uri) {
        super(context, uri, PROJECTTION, null, null, null);
    }
}
