package com.coolblee.easynote.util;

import android.util.Log;

/**
 * Created by Blee on 1/17/2016.
 */
public class NoteLog {
    private static final String TAG = "EasyNote";
    private static final boolean DEBUG = true;

    public static void d(String msg) {
        if (DEBUG)
            Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (DEBUG)
            Log.e(TAG, msg);
    }

    public static void w(String msg) {
        if (DEBUG)
            Log.w(TAG, msg);
    }

    public static void i(String msg) {
        if (DEBUG)
            Log.i(TAG, msg);
    }

}
