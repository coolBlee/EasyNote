package com.coolblee.easynote.util;

import android.content.Context;
import android.content.Intent;

import com.coolblee.easynote.editor.EditActivity;

/**
 * Created by Blee on 2015/12/20.
 */
public class PageJumper {

    public static final String EXTRA_NOTE_ID = "com.coolblee.easynote.NOTE_ID";

    public static void editNote(Context context, long noteId) {
        Intent intent = new Intent();
        intent.setClass(context, EditActivity.class);
        intent.putExtra(EXTRA_NOTE_ID, noteId);
        startActivity(context, intent);
    }

    public static void createNewNote(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, EditActivity.class);
        startActivity(context, intent);
    }

    private static void startActivity(Context context, Intent intent) {
        context.startActivity(intent);
    }
}
