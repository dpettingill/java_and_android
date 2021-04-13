package com.example.familymapclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
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

    private void getData(String search)
    {
        Datacache instance = Datacache.getInstance();
        boolean[] settings = instance.getMapMarkerSettings();
        search = search.toLowerCase();
        if (settings[MALE])
        {
            if (settings[FATHER_SIDE])
            {
                checkPeopleAndEvents(search, instance, instance.getFatherSideMales());
            }
            if (settings[MOTHER_SIDE])
            {
                checkPeopleAndEvents(search, instance, instance.getFatherSideFemales());
            }
        }
        if (settings[FEMALE])
        {
            if (settings[FATHER_SIDE])
            {
                checkPeopleAndEvents(search, instance, instance.getMotherSideMales());
            }
            if (settings[MOTHER_SIDE])
            {
                checkPeopleAndEvents(search, instance, instance.getMotherSideFemales());
            }
        }
    }

    private void checkPeopleAndEvents(String search, Datacache instance, Set<Person> peopleToCheck) {
        for (Person person : peopleToCheck)
        {
            if (person.getFirstName().toLowerCase().contains(search) || person.getLastName().toLowerCase().contains(search))
            {
                persons.add(person); //then add to list to display
            }
            //also check each event associated with that person
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
        //override these 3 functions
        //ignore viewType unless you support multiple types of views
        //onCreateViewHolder(ViewGroup, int viewType) {}
        //get item at position and pass it to 'bind' method in ViewHolder
        //onBindViewHolder(VH holder, int postition) {}
        //return the total number of items to be displayed
        //getItemCount() {}
    }

    private class searchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private final TextView name;
        private final TextView eventInfo;

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
            } else {
                name = itemView.findViewById(R.id.personsName);
                eventInfo = itemView.findViewById(R.id.eventInfo);
            }
        }

        @Override
        public void onClick(View v) {
            Intent intent = null;
            Datacache instance = Datacache.getInstance();
            if(viewType == PERSON_VIEW_TYPE)
            {

            }
            else
            {
                instance.setMyEvent(event);
                intent = new Intent(itemView.getContext(), EventActivity.class);
                startActivity(intent);
            }
        }

        private void bind(Person myPerson) {
            person = myPerson;
            String personName = person.getFirstName() + " " + person.getLastName();
            name.setText(personName);
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
        }


        //provides a constructor that does that following
        //inflates the view for the item to be displayed and passes it on to its parent constructor
        //sets itself as onClick listener so that the items can be clickable
        //gets references to the UI items that will need to be manipulated to interact with the views

        //uses an itemView from parent class that can be used to interact with views
    }
}