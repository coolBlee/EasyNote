package com.coolblee.easynote.editor;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.EditText;

import com.coolblee.coolblibrary.activity.BaseActivity;
import com.coolblee.easynote.R;
import com.coolblee.easynote.provider.EasyNote;

public class EditActivity extends BaseActivity {
    private EditText mContentEditText;

    @Override
    protected void initVariables(Bundle savedInstanceState) {

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

    @Override
    protected void loadData(Bundle savedInstanceState) {

    }

    private void saveNoteContent(String content) {
        ContentValues values = new ContentValues();
        values.put(EasyNote.Notes.DETAIL, content);
        getContentResolver().insert(EasyNote.Notes.CONTENT_URI, values);
    }
}