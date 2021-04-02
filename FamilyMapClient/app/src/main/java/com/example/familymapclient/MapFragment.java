package com.example.familymapclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.fonts.Font;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.util.Map;
import java.util.Set;

import Model.Event;

import static android.widget.Toast.LENGTH_SHORT;


public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback {
    private static final String life_story = "life-story";
    private static final String family_tree_lines = "family-tree-lines";
    private static final String spouse_lines = "spouse-lines";
    private static final String father_side = "father-s-side";
    private static final String mother_side = "mother-s-side";
    private static final String male_event = "male-event";
    private static final String female_event = "female-event";

    private GoogleMap map;
    private boolean firstRun;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() instanceof MainActivity)
        {
            setHasOptionsMenu(true);
        }
        Iconify.with(new FontAwesomeModule());
        firstRun = true;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, container, savedInstanceState);
        View view = layoutInflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    //make this into an override
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
        MenuItem settingsIcon = menu.findItem(R.id.settingsIcon);
        settingsIcon.setIcon(new IconDrawable(getContext(), FontAwesomeIcons.fa_gear).colorRes(R.color.white).actionBarSize());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.settingsIcon:
                Intent intent = new Intent(getContext(), SettingsActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        addMarkers(true); //k let's draw all of our markers here :)

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            /** Called when the user clicks a marker. */
            public boolean onMarkerClick(Marker marker) {
                // Check if a click count was set, then display the click count.
                Toast.makeText(getActivity(),
                        marker.getTitle() +
                                " has been clicked ",
                        LENGTH_SHORT).show();
                // Return false to indicate that we have not consumed the event and that we wish
                // for the default behavior to occur (which is for the camera to move such that the
                // marker is centered and for the marker's info window to open, if it has one).
                return false;
            }
        });
        firstRun = false;
    }

    //dilema
    //so here's the thing is that I save the sharedPreferences thing from the settings page
    //but will those persist? And if not, do I just not check them the first time that the user
    //opens the map? Will that be a problem? Seems like I need those settings to persist
    //let's see what is breaking our thing rn though
    //so here's the real dilema...I am trying to add markers to a map that hasn't loaded yet
    //how am I going to deal with that?
    //so I could maybe check if this is the first time through or not, bc when I come back the map
    // will already have been loaded but will need to be cleared
    @Override
    public void onResume() {
        super.onResume();
        if (!firstRun) {
                boolean settingsChanged = getSharedPreferences();
                addMarkers(settingsChanged); //addMarkers and lines here
            }
    }

    public boolean getSharedPreferences() {
        Datacache instance = Datacache.getInstance();
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean[] mapMarkerSettings = instance.getMapMarkerSettings();
        boolean[] newMapMarkerSettings = new boolean[7];
        boolean settingsChanged = false;

        newMapMarkerSettings[0] = sharedPreferences.getBoolean(life_story, true);
        newMapMarkerSettings[1] = sharedPreferences.getBoolean(family_tree_lines, true);
        newMapMarkerSettings[2] = sharedPreferences.getBoolean(spouse_lines, true);
        newMapMarkerSettings[3] = sharedPreferences.getBoolean(father_side, true);
        newMapMarkerSettings[4] = sharedPreferences.getBoolean(mother_side, true);
        newMapMarkerSettings[5] = sharedPreferences.getBoolean(male_event, true);
        newMapMarkerSettings[6] = sharedPreferences.getBoolean(female_event, true);

        int i = 0;
        for (boolean b : newMapMarkerSettings)
        {
            if (b != mapMarkerSettings[i])
            {
                settingsChanged = true; //flag that a setting has changed
                mapMarkerSettings[i] = b; //update that setting
            }
            i++;
        }
        instance.setMapMarkerSettings(mapMarkerSettings); //cache these new settings
        return settingsChanged;
    }

//        int[] colors = {R.color.yellow, R.color.red, R.color.blue, R.color.green, R.color.black,
//                R.color.white, R.color.orange, R.color.bright_pink, R.color.azure, R.color.magenta,
//                R.color.spring_green, R.color.chartreuse, R.color.violet, R.color.cyan};
    //draws all of the markers on the map
    //creates their color based on what type of event it is
    //Okay I may want to rethink this
    public void addMarkers(boolean settingsChanged) {
        Datacache instance = Datacache.getInstance();
        boolean[] mapMarkerSettings = instance.getMapMarkerSettings();
        int[] markerColors = {120, 240, 270, 30, 0, 150, 60, 90, 180, 210, 300, 330}; //color wheel pts. 0 == red, 120 == green, 240 == blue
        int index = 0;

        if (settingsChanged)
        {
            if (!firstRun)
                map.clear();
            for (Map.Entry<String, Set<Event>> entry : instance.getEventsMap().entrySet()) {
                for (Event e : entry.getValue()) {
                    index = instance.getEventTypes().get(e.getEventType()); //get the appropriate color index for this marker
                    if (index > markerColors.length - 1) //if the index is too high
                        index -= markerColors.length - 1; //reset it to an in-bounds value
                    LatLng position = new LatLng(e.getLatitude(), e.getLongitude()); //get pos of this marker

                    MarkerOptions marker = new MarkerOptions()
                            .icon(BitmapDescriptorFactory.defaultMarker(markerColors[index])).position(position).title(e.getEventType());
                    Marker myMarker = map.addMarker(marker);
                    myMarker.setTag(e.getPersonID()); //set tags for markers before putting them on the map
                }
            }
        }
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
