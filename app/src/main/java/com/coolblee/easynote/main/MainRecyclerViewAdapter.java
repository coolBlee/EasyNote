package com.coolblee.easynote.main;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coolblee.easynote.R;
import com.coolblee.easynote.dummy.DummyContent.DummyItem;
import com.coolblee.easynote.main.MainContentFragment.OnListFragmentInteractionListener;


public class MainRecyclerViewAdapter extends RecyclerViewCursorAdapter<MainRecyclerViewAdapter.ViewHolder> {

    public static final int LIST_ITEM_TYPE_VERTICAL_CONTENT = 1;
    public static final int LIST_ITEM_TYPE_HORIZONTAL_CONTENT = 2;

    private final OnListFragmentInteractionListener mListener;

    public MainRecyclerViewAdapter(Context context, Cursor c, OnListFragmentInteractionListener listener) {
        super(context, c, false);
        mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        switch (getDataType(position)) {
            case LIST_ITEM_TYPE_VERTICAL_CONTENT:
                return LIST_ITEM_TYPE_VERTICAL_CONTENT;
            case LIST_ITEM_TYPE_HORIZONTAL_CONTENT:
                return LIST_ITEM_TYPE_HORIZONTAL_CONTENT;
            default:
                break;
        }
        return super.getItemViewType(position);
    }

    private int getDataType(int position) {
        //TODO coolBlee will do it later
        return 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case LIST_ITEM_TYPE_VERTICAL_CONTENT:
                //TODO coolBlee will do it later
                break;
            case LIST_ITEM_TYPE_HORIZONTAL_CONTENT:
                //TODO coolBlee will do it later
                break;
            default:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.main_note_item, parent, false);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor cursor) {
        holder.mCardView.setCardBackgroundColor(getRandomItemBgColor());
        holder.mContentView.setText(cursor.getString(NotesLoader.NOTE_DETAIL));
        bindViewListener(holder.mView, cursor.getLong(NotesLoader.NOTE_ID));
    }

    private void bindViewListener(View view, final long noteId){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(noteId);
                }
            }
        });
    }

    @Override
    protected void onContentChanged() {

    }

    private int getRandomItemBgColor() {
        return Color.rgb((int) Math.floor(Math.random() * 128) + 64,
                (int) Math.floor(Math.random() * 128) + 64,
                (int) Math.floor(Math.random() * 128) + 64);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final CardView mCardView;
        public final TextView mContentView;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mCardView = (CardView) view.findViewById(R.id.cardview);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
