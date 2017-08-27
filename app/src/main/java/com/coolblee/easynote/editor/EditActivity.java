package com.coolblee.easynote.editor;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.EditText;

import com.coolblee.coolblibrary.activity.BaseActivity;
import com.coolblee.easynote.R;
import com.coolblee.easynote.main.NotesLoader;
import com.coolblee.easynote.provider.EasyNote.Notes;
import com.coolblee.easynote.util.PageJumper;

public class EditActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int NOTES_LOADER_ID = 428;

    private static final int STATE_CREAT_NEW = 1;
    private static final int STATE_EDIT = 2;
    private int mState = STATE_CREAT_NEW;

    private Uri mUri;

    private EditText mContentEditText;

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        final long noteId = getIntent().getLongExtra(PageJumper.EXTRA_NOTE_ID, -1);
        if(noteId <= 0) {
            //create new note mode
            mState = STATE_CREAT_NEW;
            mUri = Notes.CONTENT_URI;
        } else {
            //edit a exists note
            mState = STATE_EDIT;
            mUri = ContentUris.withAppendedId(Notes.CONTENT_URI, noteId);
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_edit);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mContentEditText = (EditText) this.findViewById(R.id.note_content);
    }

    @Override
    protected void setViewsAction(Bundle savedInstanceState) {
        this.findViewById(R.id.save_note).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNoteContent(mContentEditText.getText().toString());
            }
        });
    }

    private void saveNoteContent(String content) {
        ContentValues values = new ContentValues();
        values.put(Notes.DETAIL, content);
        getContentResolver().update(mUri, values, null, null);
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        if(STATE_EDIT == mState) {
            startLoadingNotes();
        }
    }

    private void startLoadingNotes() {
        getLoaderManager().enableDebugLogging(true);
        getLoaderManager().restartLoader(NOTES_LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new NotesLoader(this, mUri);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(null == data){
            return;
        }
        data.moveToFirst();
        mContentEditText.setText(data.getString(NotesLoader.NOTE_DETAIL));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}