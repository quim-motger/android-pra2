package com.example.quim.bykeapp.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quim.bykeapp.R;
import com.example.quim.bykeapp.controller.DibaAPI;
import com.example.quim.bykeapp.entity.Cities;
import com.example.quim.bykeapp.entity.Element;
import com.example.quim.bykeapp.service.RetrofitFactory;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private OnFragmentInteractionListener mListener;

    private View mProgressView;
    private View mMapView;

    private GoogleMap map;

    public MapsFragment() {
        // Required empty public constructor
    }

    public static MapsFragment newInstance() {
        MapsFragment fragment = new MapsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_maps, container, false);
        FragmentManager fm = getChildFragmentManager();
        MapFragment mapFragment = (MapFragment)
                fm.findFragmentById(R.id.theMap);
        mapFragment.getMapAsync(this);
        mProgressView = v.findViewById(R.id.maps_progress);
        mMapView = v.findViewById(R.id.theMap);
        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        showProgress(true);

        LatLng latLng = new LatLng(41.72627399,1.82074197);
//        MarkerOptions option = new MarkerOptions();
//        option.position(latLng).title("headquarters");
//        map.addMarker(option);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,8));

        DibaAPI dibaAPI = RetrofitFactory.getDibaAPI();
        Call<Cities> callCitiesList = dibaAPI.cities( "1" , "70" );
        callCitiesList.enqueue( new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                Cities cities = response.body();
                for (Element element: cities.getElements()) {
                    String localitzacio =
                            element.getGrupAjuntament().getLocalitzacio();
                    String latlong[] = localitzacio.split(",");
                    double latitude = Double.valueOf(latlong[0]);
                    double longitude = Double.valueOf(latlong[1]);
                    LatLng latLng = new LatLng(latitude, longitude);
                    MarkerOptions option = new MarkerOptions();
                    option.position(latLng).title(element.getMunicipiNomCurt());
                    map.addMarker(option);
                }
                showProgress( false );
            }

            @Override
            public void onFailure(Call<Cities> call, Throwable t) {
                Log.d("TDDM_TAG", "onFAILURE!!!!: msg:" + t.getMessage());
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mMapView.setVisibility(show ? View.GONE : View.VISIBLE);
            mMapView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mMapView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mMapView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
