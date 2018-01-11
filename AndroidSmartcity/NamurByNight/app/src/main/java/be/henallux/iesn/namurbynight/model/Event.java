package be.henallux.iesn.namurbynight.model;

import java.util.ArrayList;

public class Event {
    private Integer id;
    private String name;
    private String description;
    private ArrayList<DrinkCategory> menu;
    private String promotion;
    private String address;
    private EventDate date;
    private Organizer organizer;

    public Event() {}
    public Event(String name) {
        this(null, name);
    }
    public Event(Integer id, String name) {
        this(id, name, null);
    }
    public Event(Integer id, String name, EventDate date) {
        setId(id);
        setName(name);
        setDate(date);
    }
    public Event(Integer id, String name, EventDate date, Organizer organiser) {
        this(id, name, null, null, null, null, date, organiser);
    }
    public Event(Integer id, String name, String description, ArrayList<DrinkCategory> menu, String promotion, String address, EventDate date, Organizer organizer) {
        setId(id);
        setName(name);
        setDescription(description);
        setMenu(menu);
        setPromotion(promotion);
        setAddress(address);
        setDate(date);
        setOrganizer(organizer);
    }

    //--- Gettors ---//
    public Integer getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getDescription() {
        return this.description;
    }
    public ArrayList<DrinkCategory> getMenu() {
        return this.menu;
    }
    public String getPromotion() {
        return this.promotion;
    }
    public String getAddress() {
        return this.address;
    }
    public EventDate getDate() {
        return this.date;
    }
    public Organizer getOrganizer() {
        return this.organizer;
    }

    //--- Settors ---//
    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setMenu(ArrayList<DrinkCategory> menu) {
        this.menu = menu;
    }
    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setDate(EventDate date) {
        this.date = date;
    }
    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    //--- ---//
    public String toString() {
        return getName();
    }
}
