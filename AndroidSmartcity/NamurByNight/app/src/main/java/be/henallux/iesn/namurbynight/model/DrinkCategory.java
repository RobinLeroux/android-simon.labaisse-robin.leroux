package be.henallux.iesn.namurbynight.model;

import java.util.ArrayList;

public class DrinkCategory {
    private String name;
    private ArrayList<Drink> drinks;

    public DrinkCategory(String name, ArrayList<Drink> drinks) {
        setName(name);
        setDrinks(drinks);
    }

    //--- Gettors ---//
    public String getName() {
        return this.name;
    }
    public ArrayList<Drink> getDrinks() {
        return this.drinks;
    }

    //--- Settors ---//
    public void setName(String name) {
        this.name = name;
    }
    public void setDrinks(ArrayList<Drink> drinks) {
        this.drinks = drinks;
    }
}
