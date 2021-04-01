package com.example.familymapclient;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.Map;
import java.util.Set;

import Model.Event;


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

        //k let's draw all of our markers here :)
        addMarkers();

//        map.animateCamera(CameraUpdateFactory.newLatLng(sydney));



    }

//        int[] colors = {R.color.yellow, R.color.red, R.color.blue, R.color.green, R.color.black,
//                R.color.white, R.color.orange, R.color.bright_pink, R.color.azure, R.color.magenta,
//                R.color.spring_green, R.color.chartreuse, R.color.violet, R.color.cyan};
    //draws all of the markers on the map
    //creates their color based on what type of event it is
    //Okay I may want to rethink this
    public void addMarkers() {
        Datacache instance = Datacache.getInstance();
        int[] markerColors = {0, 240, 120, 30, 270, 150, 60, 90, 180, 210, 300, 330}; //color wheel pts. 0 == red, 120 == green, 240 == blue
        int index = 0;
        int iteration = 0;


        for (Map.Entry<String, Set<Event>> entry : instance.getEventsMap().entrySet()) {
            for (Event e : entry.getValue()) {
                index = instance.getEventTypes().get(e.getEventType()); //get the appropriate color index for this marker
                System.out.println(iteration);
                if (index > markerColors.length - 1) //if the index is too high
                    index -= markerColors.length - 1; //reset it to an in-bounds value
                LatLng position = new LatLng(e.getLatitude(), e.getLongitude()); //get pos of this marker
//                Drawable icon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_map_marker).
//                        colorRes(markerColors[index]).sizeDp(40);
//                icon.setAlpha(255);

////                if (iteration == 0)
////                {
//                    bitmap = Bitmap.createBitmap(icon.getIntrinsicWidth(), icon.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
//                    canvas.setBitmap(bitmap);
//                    bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
////                }
//                Bitmap bitmap = Bitmap.createBitmap(icon.getIntrinsicWidth(), icon.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);;
//                Canvas canvas = new Canvas();
//                canvas.setBitmap(bitmap);
//                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
//                icon.draw(canvas);

                MarkerOptions marker = new MarkerOptions()
                        .icon(BitmapDescriptorFactory.defaultMarker(359)).position(position).title(e.getEventType());
                map.addMarker(marker);
                iteration++;
//                map.setOnMarkerClickListener(this);
            }
        }
    }


//    /** Called when the user clicks a marker. */
//    @Override
//    public boolean onMarkerClick(final Marker marker) {
//
//        // Retrieve the data from the marker.
//        Integer clickCount = (Integer) marker.getTag();
//
//        // Check if a click count was set, then display the click count.
//        if (clickCount != null) {
//            clickCount = clickCount + 1;
//            marker.setTag(clickCount);
//            Toast.makeText(this,
//                    marker.getTitle() +
//                            " has been clicked " + clickCount + " times.",
//                    Toast.LENGTH_SHORT).show();
//        }
//
//        // Return false to indicate that we have not consumed the event and that we wish
//        // for the default behavior to occur (which is for the camera to move such that the
//        // marker is centered and for the marker's info window to open, if it has one).
//        return false;
//    }


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
