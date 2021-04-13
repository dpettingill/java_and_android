package com.example.familymapclient;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import Model.Event;
import Model.Person;

@SuppressWarnings("ALL")
public class Datacache {
    private static Datacache instance;

    private String authToken;
    private Person user;
    private Event myEvent; //this is the event to focus on when a new Event Activity is created
    private Person myPerson; //used when creating a new person activity

    //maps
    private final Map<String, Person> personsMap = new HashMap<>(); //maps personIds to persons
    private final Map<String, Set<Event>> eventsMap = new HashMap<>(); //maps personIds to sets of events

    //immediate family
    private final Set<Person> immediateFamilyMales = new HashSet<>();
    private final Set<Person> immediateFamilyFemales = new HashSet<>();

    //ancestors
    private final Set<Person> fatherSideMales = new HashSet<>();
    private final Set<Person> fatherSideFemales = new HashSet<>();
    private final Set<Person> motherSideMales = new HashSet<>();
    private final Set<Person> motherSideFemales = new HashSet<>();

    //events
    private final Map<String, Integer> eventTypes = new HashMap<>();

    //settings
    private boolean[] MapMarkerSettings = {true, true, true, true, true, true, true}; //stores our map marker settings

    private Datacache() {}

    public void clearCache()
    {
        instance = null;
    }

    public static Datacache getInstance()
    {
        if (instance == null)
        {
            instance = new Datacache();
        }
        return instance;
    }

    public Map<String, Person> getPersonsMap() {
        return personsMap;
    }

    public Map<String, Set<Event>> getEventsMap() {
        return eventsMap;
    }

    public String getAuthToken() {
        return authToken;
    }

    public Person getUser() {
        return user;
    }

    public void setUser(Person user) {
        this.user = user;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Set<Person> getImmediateFamilyMales() {
        return immediateFamilyMales;
    }

    public Set<Person> getImmediateFamilyFemales() {
        return immediateFamilyFemales;
    }

    public Set<Person> getFatherSideMales() {
        return fatherSideMales;
    }

    public Set<Person> getFatherSideFemales() {
        return fatherSideFemales;
    }

    public Set<Person> getMotherSideMales() {
        return motherSideMales;
    }

    public Set<Person> getMotherSideFemales() {
        return motherSideFemales;
    }

    public Map<String, Integer> getEventTypes() {
        return eventTypes;
    }

    public boolean[] getMapMarkerSettings() {
        return MapMarkerSettings;
    }

    public void setMapMarkerSettings(boolean[] mapMarkerSettings) {
        MapMarkerSettings = mapMarkerSettings;
    }

    public Event getMyEvent() {
        return myEvent;
    }

    public void setMyEvent(Event myEvent) {
        this.myEvent = myEvent;
    }

    public Person getMyPerson() {
        return myPerson;
    }

    public void setMyPerson(Person myPerson) {
        this.myPerson = myPerson;
    }
}
