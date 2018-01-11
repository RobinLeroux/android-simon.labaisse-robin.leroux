package be.henallux.iesn.namurbynight.model;

import java.util.ArrayList;

public class OrganizerType {
    private String type;
    private ArrayList<Organizer> organizers;

    public OrganizerType(String type, ArrayList<Organizer> organizers) {
        setType(type);
        setOrganizers(organizers);
    }

    //--- Gettors ---//
    public String getType() {
        return this.type;
    }
    public ArrayList<Organizer> getOrganizerList() { return this.organizers; }
    public Organizer getOrganizer(int id) {
        return (this.getOrganizerList()).get(id);
    }

    //--- Settors ---//
    public void setType(String type) {
        this.type = type;
    }
    public void setOrganizers(ArrayList<Organizer> organizers) {
        this.organizers = organizers;
    }

    public String toString() {
        return getType();
    }
}
