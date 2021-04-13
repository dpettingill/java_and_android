package com.example.familymapclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import Model.Event;
import Model.Person;


public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback {
    private static final String life_story = "life-story";
    private static final String family_tree_lines = "family-tree-lines";
    private static final String spouse_lines = "spouse-lines";
    private static final String father_side = "father-s-side";
    private static final String mother_side = "mother-s-side";
    private static final String male_event = "male-event";
    private static final String female_event = "female-event";
    private static final int LIFE_LINES = 0;
    private static final int FAMILY_TREE_LINES = 1;
    private static final int SPOUSE_LINES = 2;
    private static final int FATHER_SIDE = 3;
    private static final int MOTHER_SIDE = 4;
    private static final int MALE = 5;
    private static final int FEMALE = 6;
    private static final int[] marker_colors = {120, 240, 270, 30, 0, 150, 60, 90, 180, 210, 300, 330}; //color wheel pts. 0 == red, 120 == green, 240 == blue
    private static final int COLOR_GREEN_ARGB = 0xff81C784;
    private static final int COLOR_ORANGE_ARGB = 0xffF57F17;
    private static final int COLOR_YELLOW_ARGB = 0xffffff00;
    private static final int POLYGON_STROKE_WIDTH_PX = 14;


    private GoogleMap map;
    private boolean firstRun;
    private boolean eventMap = false;
    private List<Polyline> polylines = new ArrayList<Polyline>();
    private List<Marker> markers = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() instanceof MainActivity)
        {
            setHasOptionsMenu(true);
        }
        else if (getActivity() instanceof EventActivity)
        {
            eventMap = true;
            AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
            ActionBar actionBar = appCompatActivity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
        Iconify.with(new FontAwesomeModule());
        firstRun = true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
        MenuItem searchIcon = menu.findItem(R.id.searchIcon);
        searchIcon.setIcon(new IconDrawable(getContext(), FontAwesomeIcons.fa_search).colorRes(R.color.white).actionBarSize());
        MenuItem settingsIcon = menu.findItem(R.id.settingsIcon);
        settingsIcon.setIcon(new IconDrawable(getContext(), FontAwesomeIcons.fa_gear).colorRes(R.color.white).actionBarSize());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        Intent intent = null;
        switch (menuItem.getItemId())
        {
            case R.id.settingsIcon:
                intent = new Intent(getContext(), SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.searchIcon:
                intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }


    //okay so here is the problem. Currently a personId is associated with 3+ diff events
    //but if I set the tag as the event id then how will I retrieve it?
    //I can either create a new map of eventIds to events just for this or...
    //I create an object with a personId and an eventId which I then set as the tag
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        Datacache instance = Datacache.getInstance();

        addMarkers(true); //k let's draw all of our markers here :)

        if (eventMap)
        {
            for (Marker marker : markers)
            {
                MarkerTag mt = (MarkerTag) marker.getTag();
                if (mt.getEventId().equals(instance.getMyEvent().getEventID()))
                {
                    map.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition())); //on that event's marker?
                }
            }
        }

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            /** Called when the user clicks a marker. */
            public boolean onMarkerClick(Marker marker) {
                //get Person and Event from MarkerTag
                MarkerTag mt = (MarkerTag) marker.getTag();
                Person person = instance.getPersonsMap().get(mt.getPersonId()); //use the person id to get the person
                Event event = null;
                Set<Event> mySet = instance.getEventsMap().get(mt.getPersonId()); //use the person id to get the set of events
                for (Event e : mySet) { //find the right event
                    if (e.getEventID() == mt.getEventId()) {
                        event = e;
                    }
                }

                //now do stuff with it
                setText(instance, person, event);
                drawLines(instance, person, event);

                return false; //centers on this marker
            }
        });
        firstRun = false;
    }

    private void setText(Datacache instance, Person person, Event event) {
        String output;
        TextView tv = (TextView) getView().findViewById(R.id.mapTextView);
        output = person.getFirstName() + " " + person.getLastName() + '\n';
        output += event.getEventType().toUpperCase() + ": " + event.getCity() + ", " +
                event.getCountry() + " (" + event.getYear() + ")";
        if (output != null)
            tv.setText(output);
        else
            tv.setText(R.string.error_msg);
    }

    //k one at a time
    //spouse lines
    //father and mother lines -> line to the father's father (get thinner as it does so)
    //life story lines

    private void drawLines(Datacache instance, Person person, Event event)
    {
        //first erase any old lines
        for(Polyline line : polylines)
        {
            line.remove();
        }

        boolean[] settings = instance.getMapMarkerSettings();

        //spouse lines
        if (settings[SPOUSE_LINES]) { //check settings here
            if (person.getSpouseID() != null) {
                LatLng pos1 = new LatLng(event.getLatitude(), event.getLongitude());
                Person spouse = instance.getPersonsMap().get(person.getSpouseID());
                Iterator<Event> it = instance.getEventsMap().get(spouse.getPersonID()).iterator();
                Event spouseEvent = it.next();
                LatLng pos2 = new LatLng(spouseEvent.getLatitude(), spouseEvent.getLongitude());
                polylines.add(map.addPolyline(new PolylineOptions().add(pos1, pos2).color(COLOR_YELLOW_ARGB))); //yellow is for spouse lines
            }
        }

        //family tree lines
        if (settings[FAMILY_TREE_LINES]) { //check settings here
            familyTreeLines(instance, person, event, POLYGON_STROKE_WIDTH_PX);
        }

        //life story lines here
        if (settings[LIFE_LINES]) { //check settings as well
            lifeStoryLines(instance, person);
        }
    }

    private void lifeStoryLines(Datacache instance, Person person)
    {
        Set<Event> events = instance.getEventsMap().get(person.getPersonID());
        Iterator<Event> it = events.iterator();
        Event event1 = it.next();
        Event event2 = null;
        LatLng pos1 = null;
        LatLng pos2 = null;
        while (it.hasNext())
        {
            pos1 = new LatLng(event1.getLatitude(), event1.getLongitude());
            event2 = it.next();
            pos2 = new LatLng(event2.getLatitude(), event2.getLongitude());
            polylines.add(map.addPolyline(new PolylineOptions().add(pos1, pos2).color(COLOR_ORANGE_ARGB))); //orange is for life lines
            event1 = event2;
        }
    }

    private void familyTreeLines(Datacache instance, Person person, Event event, float width) {
        if (person.getFatherID() != null) {
            LatLng pos1 = new LatLng(event.getLatitude(), event.getLongitude());
            Person father = instance.getPersonsMap().get(person.getFatherID());
            Iterator<Event> it = instance.getEventsMap().get(father.getPersonID()).iterator();
            Event fatherEvent = it.next();
            LatLng pos2 = new LatLng(fatherEvent.getLatitude(), fatherEvent.getLongitude());
            polylines.add(map.addPolyline(new PolylineOptions().add(pos1, pos2).color(COLOR_GREEN_ARGB).width(width))); //green is for family tree lines
            familyTreeLines(instance, father, fatherEvent, width/2);
        }

        if (person.getMotherID() != null) {
            LatLng pos1 = new LatLng(event.getLatitude(), event.getLongitude());
            Person mother = instance.getPersonsMap().get(person.getMotherID());
            Iterator<Event> it = instance.getEventsMap().get(mother.getPersonID()).iterator();
            Event motherEvent = it.next();
            LatLng pos2 = new LatLng(motherEvent.getLatitude(), motherEvent.getLongitude());
            polylines.add(map.addPolyline(new PolylineOptions().add(pos1, pos2).color(COLOR_GREEN_ARGB).width(width))); //green is for family lines
            familyTreeLines(instance, mother, motherEvent, width/2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!firstRun) {
                boolean settingsChanged = getSharedPreferences();
                addMarkers(settingsChanged); //addMarkers and lines here
            }
    }

    private boolean getSharedPreferences() {
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
    private void addMarkers(boolean settingsChanged) {
        Datacache instance = Datacache.getInstance();
        boolean[] mapMarkerSettings = instance.getMapMarkerSettings();

        if (settingsChanged)
        {
            if (!firstRun)
                map.clear();

            if (mapMarkerSettings[FATHER_SIDE]) {
                peopleMarkers(instance, mapMarkerSettings, instance.getFatherSideMales(), instance.getFatherSideFemales());
            }

            if (mapMarkerSettings[MOTHER_SIDE]) {
                peopleMarkers(instance, mapMarkerSettings, instance.getMotherSideMales(), instance.getMotherSideFemales());
            }

            userMapMarkers(instance); //draw the user's stuff??
        }
    }

    private void peopleMarkers(Datacache instance, boolean[] mapMarkerSettings, Set<Person> Males, Set<Person> Females) {
        if (mapMarkerSettings[MALE]) {
            for (Person p : Males) {
                createEventMarkers(instance, p);
            }
        }
        if (mapMarkerSettings[FEMALE]) {
            for (Person p : Females) {
                createEventMarkers(instance, p);
            }
        }
    }

    private void createEventMarkers(Datacache instance, Person p)
    {
        for (Event e : instance.getEventsMap().get(p.getPersonID())) {
            int index = instance.getEventTypes().get(e.getEventType()); //get the appropriate color index for this marker
            if (index > marker_colors.length - 1) //if the index is too high
                index -= marker_colors.length - 1; //reset it to an in-bounds value
            LatLng position = new LatLng(e.getLatitude(), e.getLongitude()); //get pos of this marker

            MarkerOptions marker = new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(marker_colors[index])).position(position);
            Marker myMarker = map.addMarker(marker);
            myMarker.setTag(new MarkerTag(e.getPersonID(), e.getEventID())); //set tags for markers before putting them on the map
            markers.add(myMarker);
        }
    }

    private void userMapMarkers(Datacache instance)
    {
        Person user = instance.getUser();
        if (user.getGender().equals("m") && instance.getMapMarkerSettings()[MALE])
        {
            createEventMarkers(instance, user);
        }
        else if (user.getGender().equals("f") && instance.getMapMarkerSettings()[FEMALE])
        {
            createEventMarkers(instance, user);
        }
    }

    //use this for my markers tags
    public class MarkerTag
    {
        private String personId;
        private String eventId;

        public MarkerTag(String personId, String eventId)
        {
            this.eventId = eventId;
            this.personId = personId;
        }

        public String getPersonId() {
            return personId;
        }

        public String getEventId() {
            return eventId;
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
