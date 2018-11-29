package com.example.quim.bykeapp.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.example.quim.bykeapp.entity.Bike;
import com.example.quim.bykeapp.fragment.ItemFragment;
import com.example.quim.bykeapp.R;

@Deprecated
public class BikeSharingListActivity  extends FragmentActivity implements ItemFragment.OnListFragmentInteractionListener {

    private final static Integer REQUEST_CODE = 1;
    private final static String TAG = BikeSharingListActivity.class.getSimpleName();

    private ItemFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_sharing_list);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BikeSharingListActivity.this, AddBikeSharingActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            String id = data.getStringExtra(getString(R.string.id));
            String content = data.getStringExtra(getString(R.string.content));
            Log.d(TAG, "New item: " + id + " (id), " + content + " (content)");
            Bike newBike = new Bike(id, content);

            fragment.addItem(newBike);
        }
    }

    /**
     * See https://developer.android.com/training/basics/fragments/communicating for Fragment
     * communication pattern
     * @param fragment
     */
    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof ItemFragment) {
            this.fragment = (ItemFragment) fragment;
        }
    }

    @Override
    public void onListFragmentInteraction(Bike bike) {
        //fill with code
    }
}
