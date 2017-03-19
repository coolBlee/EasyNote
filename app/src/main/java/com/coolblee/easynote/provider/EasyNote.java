package com.coolblee.easynote.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Blee on 1/5/2016.
 */
public final class EasyNote {
    //same as defined in AndroidManifest
    public static final String AUTHORITY = "com.coolblee.easynote";

    private EasyNote() {
    }

    public static final class Notes implements BaseColumns, NotesColumns {
        private Notes() {
        }

        /**
         * The scheme part for this provider's URI
         */
        private static final String SCHEME = "content://";

        /**
         * Path part for the Notes URI
         */
        private static final String PATH_NOTES = "/notes";

        /**
         * Path part for the Note ID URI
         */
        private static final String PATH_NOTE_ID = "/notes/";

        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_NOTES);

        /**
         * The content URI base for a single note. Callers must
         * append a numeric note id to this Uri to retrieve a note
         */
        public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + AUTHORITY + PATH_NOTE_ID);
    }

    protected interface NotesColumns {
        String CREATED_TIME = "created_time";
        String MODIFY_TIME = "modify_time";
        String FAVORITE = "favorite";
        String BACKGROUND_COLOR = "background_color";
        String TITLE = "title";
        String DETAIL = "detail";
    }
}
