package com.coolblee.easynote.editor;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.coolblee.easynote.R;
import com.coolblee.easynote.provider.EasyNote;

public class EditActivity extends AppCompatActivity {
    private EditText mContentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initViews();
        initListener();
    }

    private void initViews() {
        mContentEditText = (EditText) this.findViewById(R.id.note_content);
    }

    private void initListener() {
        this.findViewById(R.id.save_note).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNoteContent(mContentEditText.getText().toString());
            }
        });
    }

    private void saveNoteContent(String content) {
        ContentValues values = new ContentValues();
        values.put(EasyNote.Notes.DETAIL, content);
        getContentResolver().insert(EasyNote.Notes.CONTENT_URI, values);
    }
}