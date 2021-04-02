package com.example.familymapclient;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import Model.AuthToken;
import Model.Event;
import Model.Person;

public class Datacache {
    private static Datacache instance;

    private String authToken;
    private Person user;
    private Map<String, Person> personsMap = new HashMap<>();
    private Map<String, Set<Event>> eventsMap = new HashMap<>();

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
    private boolean[] MapMarkerSettings = new boolean[7]; //stores our map marker settings

    private Datacache() {}

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
}
