package be.henallux.iesn.namurbynight.model;

import java.util.ArrayList;

public class EventCalendar {
    private String date;
    private ArrayList<Event> events;

    public EventCalendar(String date, ArrayList<Event> events) {
        setDate(date);
        setEvents(events);
    }

    //--- Gettors ---//
    public String getDate() {
        return this.date;
    }
    public ArrayList<Event> getEvents() { return this.events; }
    public Event getEvent(int id) {
        return (this.getEvents()).get(id);
    }

    //--- Settors ---//
    public void setDate(String date) {
        this.date = date;
    }
    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public String toString() {
        return getDate();
    }
}
