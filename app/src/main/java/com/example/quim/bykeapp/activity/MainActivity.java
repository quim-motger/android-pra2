package com.example.quim.bykeapp.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.quim.bykeapp.R;
import com.example.quim.bykeapp.entity.Bike;
import com.example.quim.bykeapp.fragment.AddBikeFragment;
import com.example.quim.bykeapp.fragment.ItemFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ItemFragment.OnListFragmentInteractionListener,
        AddBikeFragment.AddBikeListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private String bikeId;
    private String bikeDescription;

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String username = sharedPref.getString("username", "");
        setTitle(username);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Default fragment - bike list
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment fragment = new ItemFragment();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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
        Log.d(TAG, "Starting fragment generation");
        int id = item.getItemId();

        Fragment fragment = null;
        if (id == R.id.nav_new_bike) {
            fragment = new AddBikeFragment();
        } else if (id == R.id.nav_bikes) {
            fragment = new ItemFragment();
        } else if (id == R.id.nav_maps) {
            //TODO
        } else if (id == R.id.nav_logout) {
            //TODO
        }

        updateFragment(fragment);

        Log.d(TAG, "Done");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(Bike bike) {
        //fill with code
    }

    @Override
    public void onAddedBikeListener(Bike bike) {
        Log.d(TAG, "New item: " + bike.getId() + " (id), " + bike.getContent() + " (content)");
        bikeId = bike.getId();
        bikeDescription = bike.getContent();
        ItemFragment fragment = new ItemFragment();
        updateFragment(fragment);
        navigationView.setCheckedItem(R.id.nav_bikes);
    }

    private void updateFragment(Fragment fragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }

    @Override
    public void onCreatedView() {
        if (bikeId != null) {
            ItemFragment fragment = (ItemFragment) getFragmentManager().findFragmentById(R.id.content_frame);
            fragment.addItem(new Bike(bikeId, bikeDescription));
        }
    }
}
