package com.coolblee.easynote.main;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coolblee.easynote.R;
import com.coolblee.easynote.util.NoteLog;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MainContentFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int NOTES_LOADER_ID = 428;

    public static final int TYPE_LINEARLAYOUT = 0;
    public static final int TYPE_GRIDLAYOUT = 1;
    public static final int TYPE_STAGGERED_GRIDLAYOUT = 2;
    // TODO: Customize parameter argument names
    private static final String ARG_RECYCLERVIEW_LAYOUT_TYPE = "recyclerView_layoutManager";
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mLayoutType = TYPE_LINEARLAYOUT;
    private int mColumnCount = 3;

    private MainRecyclerViewAdapter mAdapter;

    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MainContentFragment() {
    }

    // TODO: Customize parameter initialization
    public static MainContentFragment newInstance(int layoutType, int columnCount) {
        MainContentFragment fragment = new MainContentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_RECYCLERVIEW_LAYOUT_TYPE, layoutType);
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            Bundle arguments = getArguments();
            mLayoutType = arguments.getInt(ARG_RECYCLERVIEW_LAYOUT_TYPE);
            mColumnCount = arguments.getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_content, container, false);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            // Set the adapter
            switch (mLayoutType) {
                case TYPE_LINEARLAYOUT:
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    break;
                case TYPE_GRIDLAYOUT:
                    recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                    break;
                case TYPE_STAGGERED_GRIDLAYOUT:
                    StaggeredGridLayoutManager gridLayoutManager =
                            new StaggeredGridLayoutManager(mColumnCount, StaggeredGridLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    break;
                default:
                    throw new RuntimeException("You must supply a layoutManager for the recyclerView!");
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mAdapter = new MainRecyclerViewAdapter(getContext(), null, mListener);
            } else {
                mAdapter = new MainRecyclerViewAdapter(getActivity(), null, mListener);
            }
            recyclerView.setAdapter(mAdapter);
        }

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        startLoadingNotes();
    }

    private void startLoadingNotes() {
        getLoaderManager().enableDebugLogging(true);
        getLoaderManager().restartLoader(NOTES_LOADER_ID, null, this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return new NotesLoader(getContext());
        } else {
            return new NotesLoader(getActivity());
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(null == data){
            return;
        }
        NoteLog.d("onLoadFinished()-->loader id==" + loader.getId() + " data size==" + data.getCount());
        mAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction();
    }
}
