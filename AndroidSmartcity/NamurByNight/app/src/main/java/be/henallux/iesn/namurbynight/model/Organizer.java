package be.henallux.iesn.namurbynight.model;

import java.util.ArrayList;

public class Organizer {
    private Integer id;
    private String name;
    private String type;
    private String description;
    private ArrayList<DrinkCategory> menu;
    private String promotion;
    private String facebookLink;
    private String address;
    private String phoneNumber;

    public Organizer() {}
    public Organizer(String name) {
        this(null, name);
    }
    public Organizer(Integer id, String name) {
        this(id, name, null);
    }
    public Organizer(Integer id, String name, String type) {
        this(id, name, type, null, null, null, null, null, null);
    }
    public Organizer(Integer id, String name, String type, String description, ArrayList<DrinkCategory> menu, String promotion, String facebookLink, String address, String phoneNumber) {
        setId(id);
        setName(name);
        setType(type);
        setDescription(description);
        setMenu(menu);
        setPromotion(promotion);
        setFacebookLink(facebookLink);
        setAddress(address);
        setPhoneNumber(phoneNumber);
    }

    //--- Gettors ---//
    public Integer getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getType() {
        return this.type;
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
    public String getFacebookLink() {
        return this.facebookLink;
    }
    public String getAddress() {
        return this.address;
    }
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    //--- Settors ---//
    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setType(String type) {
        this.type = type;
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
    public void setFacebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String toString() {
        return this.getName();
    }
}
