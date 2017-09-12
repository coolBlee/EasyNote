package com.coolblee.easynote.editor;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Loader;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
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

    private EditText mTileEditText, mContentEditText;
    private TypedArray mBgColors;
    private int mChoosedColor;

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        mBgColors = this.getResources().obtainTypedArray(R.array.bg_colors);
        mChoosedColor = mBgColors.getColor(0, Color.WHITE);

        final long noteId = getIntent().getLongExtra(PageJumper.EXTRA_NOTE_ID, -1);
        if (noteId <= 0) {
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
        mTileEditText = (EditText) this.findViewById(R.id.note_title);
        mContentEditText = (EditText) this.findViewById(R.id.note_content);
    }

    @Override
    protected void setViewsAction(Bundle savedInstanceState) {

    }

    private void saveNoteContent() {
        ContentValues values = new ContentValues();
        values.put(Notes.TITLE, mTileEditText.getText().toString());
        values.put(Notes.DETAIL, mContentEditText.getText().toString());
        values.put(Notes.BACKGROUND_COLOR, mChoosedColor);
        if(STATE_CREAT_NEW == mState){
            getContentResolver().insert(mUri, values);
        } else if(STATE_EDIT == mState){
            getContentResolver().update(mUri, values, null, null);
        }
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        if (STATE_EDIT == mState) {
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
        if (null == data) {
            return;
        }
        data.moveToFirst();
        mTileEditText.setText(data.getString(NotesLoader.NOTE_TITLE));
        mContentEditText.setText(data.getString(NotesLoader.NOTE_DETAIL));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.complete){
            saveNoteContent();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != mBgColors) {
            mBgColors.recycle();
        }
    }
}