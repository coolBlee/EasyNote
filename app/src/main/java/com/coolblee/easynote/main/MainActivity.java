package com.coolblee.easynote.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.coolblee.coolblibrary.activity.BaseActivity;
import com.coolblee.easynote.R;
import com.coolblee.easynote.util.PageJumper;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MainContentFragment.OnListFragmentInteractionListener {
    private static final String TAG = "MainActivity";
    private static final String MAIN_CONTENT_FRAGMENT_TAG = "easy_note_main_fragment_tag";
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void initVariables(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != mDrawer){
            mDrawer.removeDrawerListener(mToggle);
        }
    }

    @Override
    protected void setViewsAction(Bundle savedInstanceState) {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                PageJumper.createNewNote(MainActivity.this);
            }
        });


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        addMainContent();
    }

    private void addMainContent() {
        FragmentManager manager = this.getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = manager.findFragmentByTag(MAIN_CONTENT_FRAGMENT_TAG);
        if (null == fragment) {
            fragment = getMainContentFragment();
        }
        transaction.add(R.id.main_container, fragment, MAIN_CONTENT_FRAGMENT_TAG);
        transaction.commit();
    }

    private Fragment getMainContentFragment() {
        return MainContentFragment.newInstance(MainContentFragment.TYPE_STAGGERED_GRIDLAYOUT, 2);
    }

    @Override
    public void onBackPressed() {
        if (null != mDrawer && mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_camera:
                break;
            case R.id.nav_gallery:
                break;
            case R.id.nav_slideshow:
                break;
            case R.id.nav_manage:
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_send:
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //when the list item clicked, this method will be execute
    @Override
    public void onListFragmentInteraction(long noteId) {
        Log.d(TAG, "jump to editor activity");
        PageJumper.editNote(this, noteId);
    }
}
