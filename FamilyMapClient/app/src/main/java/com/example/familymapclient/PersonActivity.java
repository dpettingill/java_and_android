package com.example.familymapclient;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import Model.Event;
import Model.Person;

public class PersonActivity extends AppCompatActivity {
    private static final int FATHER_SIDE = 3;
    private static final int MOTHER_SIDE = 4;
    private static final int MALE = 5;
    private static final int FEMALE = 6;

    private List<String> listDataHeader = new ArrayList<String>();
    private HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();
    private List<String> eventIds = new ArrayList<>();
    private List<String> personIds = new ArrayList<>();
    private Person currentPerson = null;

    private ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Datacache instance = Datacache.getInstance();

        TextView tv = (TextView) findViewById(R.id.person_first_name);
        tv.setText(instance.getMyPerson().getFirstName());

        TextView tv2 = (TextView) findViewById(R.id.person_last_name);
        tv2.setText(instance.getMyPerson().getLastName());

        String male = "Male", female = "Female";

        TextView tv3 = (TextView) findViewById(R.id.person_gender);
        if (instance.getMyPerson().getGender().equals("m"))
            tv3.setText(male);
        else
            tv3.setText(female);

        ExpandableListView expListView = (ExpandableListView) findViewById(R.id.lvExp);
        prepareListData(instance);
        ExpandableListAdapter listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter); // setting list adapter

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Intent intent = null;
                if (groupPosition == 0) //event clicked
                {
                    for (Event e : instance.getEventsMap().get(currentPerson.getPersonID()))
                    {
                        if (e.getEventID().equals(eventIds.get(childPosition)))
                            instance.setMyEvent(e);
                    }
                    intent = new Intent(getApplicationContext(), EventActivity.class);
                }
                else
                {
                    instance.setMyPerson(instance.getPersonsMap().get(personIds.get(childPosition)));
                    intent = new Intent(getApplicationContext(), PersonActivity.class);
                }
                startActivity(intent);
                return false;
            }
        });
    }

    private boolean checkPersonAgainstSettings(Datacache instance)
    {
        if (!instance.getMapMarkerSettings()[MALE] && currentPerson.getGender().equals("m")) {
            return false;
        }
        else if (!instance.getMapMarkerSettings()[FEMALE] && currentPerson.getGender().equals("f")) {
            return false;
        }
        else if (!instance.getMapMarkerSettings()[FATHER_SIDE] &&
                (instance.getFatherSideMales().contains(currentPerson) ||
                        (instance.getFatherSideFemales().contains(currentPerson)))) {
            return false;
        }
        else if (!instance.getMapMarkerSettings()[MOTHER_SIDE] &&
                (instance.getMotherSideMales().contains(currentPerson) ||
                        (instance.getMotherSideFemales().contains(currentPerson)))) {
            return false;
        }
        else {
            return true;
        }
    }


    private void prepareListData(Datacache instance)
    {
        listDataHeader.add("Life Events");
        listDataHeader.add("Family");

        currentPerson = instance.getMyPerson();
        //get persons event data here
        List<String> events = new ArrayList<String>();
        for (Event e : instance.getEventsMap().get(currentPerson.getPersonID())) //loop through each event assoc w/person
        {
            if (checkPersonAgainstSettings(instance)) {
                String eventInfo = e.getEventType().toUpperCase() + ": " + e.getCity() + ", " + e.getCountry()
                        + " (" + Integer.toString(e.getYear()) + ")";
                events.add(eventInfo);
                eventIds.add(e.getEventID());
            }
        }

        //get persons family data here
        List<String> family = new ArrayList<String>();
        if (currentPerson.getFatherID() != null)
        {
            Person father = instance.getPersonsMap().get(currentPerson.getFatherID());
            String fatherInfo = father.getFirstName() + " " + father.getLastName() + " (Father)";
            family.add(fatherInfo);
            personIds.add(father.getPersonID());
        }
        if (currentPerson.getMotherID() != null)
        {
            Person mother = instance.getPersonsMap().get(currentPerson.getMotherID());
            String motherInfo = mother.getFirstName() + " " + mother.getLastName() + " (Mother)";
            family.add(motherInfo);
            personIds.add(mother.getPersonID());
        }
        if (currentPerson.getSpouseID() != null)
        {
            Person spouse = instance.getPersonsMap().get(currentPerson.getSpouseID());
            String spouseInfo = spouse.getFirstName() + " " + spouse.getLastName() + " (Spouse)";
            family.add(spouseInfo);
            personIds.add(spouse.getPersonID());
        }
        Person child = findChild(instance, currentPerson);
        if (child != null)
        {
            String childInfo = child.getFirstName() + " " + child.getLastName() + " (Child)";
            family.add(childInfo);
            personIds.add(child.getPersonID());
        }

        //add these to our map
        listDataChild.put(listDataHeader.get(0), events);
        listDataChild.put(listDataHeader.get(1), family);
    }

    private Person findChild(Datacache instance, Person person)
    {
        for (Person child : instance.getPersonsMap().values()) {
            if (person.getGender().equals("m") && child.getFatherID() != null) {
                if (child.getFatherID().equals(person.getPersonID())) {
                    return child;
                }
            } else if (person.getGender().equals("f") && child.getMotherID() != null) {
                if (child.getMotherID().equals(person.getPersonID())) {
                    return child;
                }
            }
        }
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Context _context;
        private List<String> _listDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<String, List<String>> _listDataChild;

        public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                     HashMap<String, List<String>> listChildData) {
            this._context = context;
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            final String childText = (String) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_item, null);
            }

            TextView txtListChild = (TextView) convertView
                    .findViewById(R.id.lblListItem);

            icon = (ImageView) convertView.findViewById(R.id.icon_persons);

            Drawable genderIcon = null;
            if (groupPosition == 0) //events
            {
                //can add color here easily by using eventIds - getting the eventType and then looking at the color associated with that type
                genderIcon = new IconDrawable(convertView.getContext(), FontAwesomeIcons.fa_map_marker).
                        colorRes(R.color.black).sizeDp(40);
            }
            else
            {
                Datacache instance = Datacache.getInstance();
                Person person = instance.getPersonsMap().get(personIds.get(childPosition)); //this is the person we are outputting to the screen
                if (person.getGender().equals("m")) {
                    genderIcon = new IconDrawable(convertView.getContext(), FontAwesomeIcons.fa_male).
                        colorRes(R.color.blue).sizeDp(40);
                } else {
                     genderIcon = new IconDrawable(convertView.getContext(), FontAwesomeIcons.fa_female).
                            colorRes(R.color.magenta).sizeDp(40);
                }
            }

            icon.setImageDrawable(genderIcon);
            txtListChild.setText(childText);
            return convertView;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String headerTitle = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_group, null);
            }

            TextView lblListHeader = (TextView) convertView
                    .findViewById(R.id.lblListHeader);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle);

            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this._listDataHeader.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return this._listDataHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }



        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

}