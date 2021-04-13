package com.example.familymapclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import Model.Event;
import Model.Person;

public class SearchActivity extends AppCompatActivity {
    private static final int FATHER_SIDE = 3;
    private static final int MOTHER_SIDE = 4;
    private static final int MALE = 5;
    private static final int FEMALE = 6;
    private static final int PERSON_VIEW_TYPE = 0;
    private static final int EVENT_VIEW_TYPE = 1;

    private List<Person> persons = new ArrayList<>();
    private  List<Event> events = new ArrayList<>();
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //k so we need to get the data that we want to display here first
        SearchView mySearchView = (SearchView) findViewById(R.id.simpleSearchView);
        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                persons.clear();
                events.clear();
                getData(query);
                recyclerView = findViewById(R.id.recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                SearchAdapter adapter = new SearchAdapter(persons, events);
                recyclerView.setAdapter(adapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    //so what was he saying?
    //you only want to filter events not people
    //ok now we won't filter out people and it will include the user
    //but we will only look at events if the right settings are on
    //still won't show the user's events though
    private void getData(String search)
    {
        Datacache instance = Datacache.getInstance();
        boolean[] settings = instance.getMapMarkerSettings();
        search = search.toLowerCase();
        checkPeople(search, instance);
        if (settings[MALE])
        {
            if (instance.getUser().getGender().equals("m")) //only checks user if events if male and male events turned on
            {
                checkUserEvents(search, instance);
            }
            if (settings[FATHER_SIDE])
            {
                checkEvents(search, instance, instance.getFatherSideMales());
            }
            if (settings[MOTHER_SIDE])
            {
                checkEvents(search, instance, instance.getFatherSideFemales());
            }
        }
        if (settings[FEMALE])
        {
            if (instance.getUser().getGender().equals("f")) //only checks the user events if female and female events turned on
            {
                checkUserEvents(search, instance);
            }
            if (settings[FATHER_SIDE])
            {
                checkEvents(search, instance, instance.getMotherSideMales());
            }
            if (settings[MOTHER_SIDE])
            {
                checkEvents(search, instance, instance.getMotherSideFemales());
            }
        }
    }

    private void checkPeople(String search, Datacache instance)
    {
        for (Person person : instance.getPersonsMap().values()) {
            if (person.getFirstName().toLowerCase().contains(search) || person.getLastName().toLowerCase().contains(search)) {
                persons.add(person); //then add to list to display
            }
        }
    }


    private void checkEvents(String search, Datacache instance, Set<Person> peopleToCheck) {
        for (Person person : peopleToCheck)
        {
            //check each event associated with that person
            for (Event event : instance.getEventsMap().get(person.getPersonID()))
            {
                //check country, city, type and year
                if (event.getCountry().toLowerCase().contains(search) || Integer.toString(event.getYear()).contains(search) ||
                    event.getCity().toLowerCase().contains(search) || event.getEventType().toLowerCase().contains(search))
                {
                    events.add(event);
                }
            }
        }
    }

    private void checkUserEvents(String search, Datacache instance) {
        //check each event associated with that person
        for (Event event : instance.getEventsMap().get(instance.getUser().getPersonID()))
        {
            //check country, city, type and year
            if (event.getCountry().toLowerCase().contains(search) || Integer.toString(event.getYear()).contains(search) ||
                    event.getCity().toLowerCase().contains(search) || event.getEventType().toLowerCase().contains(search))
            {
                events.add(event);
            }
        }
    }

    //searchResultView to get the search thing to appear
    //also will need to follow the steps in the slide to create a dependency for the recyclerView class so that I can use it

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    //in the layout file have a linear layout and then the search bar and then the recycler view

    private class SearchAdapter extends RecyclerView.Adapter<searchViewHolder>
    {
        private final List<Person> persons;
        private final List<Event> events;

        SearchAdapter(List<Person> persons, List<Event> events) {
            this.persons = persons;
            this.events = events;
        }

        @Override
        public int getItemViewType(int position) {
            return position < persons.size() ? PERSON_VIEW_TYPE : EVENT_VIEW_TYPE;
        }

        @NonNull
        @Override
        public searchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;

            if(viewType == PERSON_VIEW_TYPE) {
                view = getLayoutInflater().inflate(R.layout.person_view, parent, false);
            } else {
                view = getLayoutInflater().inflate(R.layout.event_view, parent, false);
            }

            return new searchViewHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull searchViewHolder holder, int position) {
            if(position < persons.size()) {
                holder.bind(persons.get(position));
            } else {
                holder.bind(events.get(position - persons.size())); //figure out the right person to get
            }
        }

        @Override
        public int getItemCount() {
            if (persons != null && events != null)
                return persons.size() + events.size();
            else
                return 0;
        }
    }

    private class searchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private final TextView name;
        private final TextView eventInfo;
        private final ImageView icon;

        private final int viewType;
        private Person person;
        private Event event;

        public searchViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;

            itemView.setOnClickListener(this);

            if(viewType == PERSON_VIEW_TYPE) {
                name = itemView.findViewById(R.id.personName);
                eventInfo = null;
                icon = itemView.findViewById(R.id.person_gender_icon);
            } else {
                name = itemView.findViewById(R.id.personsName);
                eventInfo = itemView.findViewById(R.id.eventInfo);
                icon = itemView.findViewById(R.id.event_icon);
            }
        }

        @Override
        public void onClick(View v) {
            Intent intent = null;
            Datacache instance = Datacache.getInstance();
            if(viewType == PERSON_VIEW_TYPE)
            {
                instance.setMyPerson(person);
                intent = new Intent(itemView.getContext(), PersonActivity.class);
            }
            else
            {
                instance.setMyEvent(event);
                intent = new Intent(itemView.getContext(), EventActivity.class);
            }
            startActivity(intent);
        }

        private void bind(Person myPerson) {
            person = myPerson;
            String personName = person.getFirstName() + " " + person.getLastName();
            name.setText(personName);
            if (person.getGender().equals("m")) {
                Drawable genderIcon = new IconDrawable(itemView.getContext(), FontAwesomeIcons.fa_male).
                        colorRes(R.color.blue).sizeDp(40);

                icon.setImageDrawable(genderIcon);
            } else {
                Drawable genderIcon = new IconDrawable(itemView.getContext(), FontAwesomeIcons.fa_female).
                        colorRes(R.color.magenta).sizeDp(40);
                icon.setImageDrawable(genderIcon);
            }
        }

        private void bind(Event myEvent) {
            Datacache instance = Datacache.getInstance();
            person = instance.getPersonsMap().get(myEvent.getPersonID());
            event = myEvent;
            String personName = person.getFirstName() + " " + person.getLastName();
            String myEventInfo = event.getEventType().toUpperCase() + ": " + event.getCity() + ", "
                    + event.getCountry() + " (" + Integer.toString(event.getYear()) + ")";
            name.setText(personName);
            eventInfo.setText(myEventInfo);
            Drawable eventIcon = new IconDrawable(itemView.getContext(), FontAwesomeIcons.fa_map_marker).
                    colorRes(R.color.black).sizeDp(40);
            icon.setImageDrawable(eventIcon);
        }
    }
}