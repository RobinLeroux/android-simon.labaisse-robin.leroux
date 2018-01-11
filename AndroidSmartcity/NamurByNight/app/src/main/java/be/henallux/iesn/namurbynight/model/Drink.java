package be.henallux.iesn.namurbynight.model;

public class Drink {
    private String name;
    private Double price;

    public Drink(String name, Double price) {
        setName(name);
        setPrice(price);
    }

    //--- Gettors ---//
    public String getName() {
        return this.name;
    }
    public Double getPrice() {
        return this.price;
    }

    //--- Settors ---//
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    //--- ---//
    public String toString() {
        return this.getName() + " - " + this.getPrice() + " €";
    }
}
