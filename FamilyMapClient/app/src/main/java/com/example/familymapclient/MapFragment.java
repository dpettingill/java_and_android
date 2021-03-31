package com.example.familymapclient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback {
    private GoogleMap map;

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, container, savedInstanceState);
        View view = layoutInflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        map.animateCamera(CameraUpdateFactory.newLatLng(sydney));

        //k let's draw all of our markers here :)
        
    }

    @Override
    public void onMapLoaded() {

    }
}

// functions will need to implement this fragment
//        GoogleMap.clear()  //will use to clear to wipe everything off and just put back the things which you need for new settings
//        GoogleMap.addMarker(…)
//        GoogleMap.addPolyLine(…)
//        GoogleMap.moveCamera(…)
//        GoogleMap.animateCamera(…)
//        GoogleMap.setMapType(…)
//        GoogleMap.setOnMarkerClickListener(Marker) //need one listener for all of the markers(pass in the marker that was clicked)
//        Marker.setTag(…) //set tags for markers before putting them on the map
//        Marker.getTag(…) //when you get the marker in the listener call this on that marker to know which marker it is
