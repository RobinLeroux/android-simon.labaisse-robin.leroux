package be.henallux.iesn.namurbynight.model;

import java.util.ArrayList;

public class User {
    private int id;
    private String login;
    private String password;
    private ArrayList<Organizer> favoriteOrganizers;
    private ArrayList<Event> futurEvents;

    public User(int id, String login, String password, ArrayList<Organizer> favoriteOrganizer, ArrayList<Event> futurEvent) {

    }

    //--- Gettors ---//
    public int getId() {
        return this.id;
    }
    public String getLogin() {
        return this.login;
    }
    public ArrayList<Organizer> getFavoriteOrganizers() {
        return this.favoriteOrganizers;
    }
    public ArrayList<Event> getFuturEvents() {
        return this.futurEvents;
    }

    //--- Settors ---//
    public void setId(int id) {
        this.id = id;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public void setFavoriteOrganizers(ArrayList<Organizer> favoriteOrganizers) {
        this.favoriteOrganizers = favoriteOrganizers;
    }
    public void setFuturEvents(ArrayList<Event> futurEvents) {
        this.futurEvents = futurEvents;
    }
}
