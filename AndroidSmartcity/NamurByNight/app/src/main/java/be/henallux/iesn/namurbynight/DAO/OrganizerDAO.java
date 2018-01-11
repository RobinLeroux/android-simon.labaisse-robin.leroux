package be.henallux.iesn.namurbynight.DAO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import be.henallux.iesn.namurbynight.R;
import be.henallux.iesn.namurbynight.exception.OrganizerException;
import be.henallux.iesn.namurbynight.model.Drink;
import be.henallux.iesn.namurbynight.model.DrinkCategory;
import be.henallux.iesn.namurbynight.model.Organizer;
import be.henallux.iesn.namurbynight.model.OrganizerType;

public class OrganizerDAO {
    private ApiDAO api;
    public OrganizerDAO() {
        this.api = new ApiDAO();
    }

    public Organizer getOrganizer(int id) throws OrganizerException {
        Organizer organizer;
        try {
            organizer = jsonToOrganizer(api.get("organizer/" + id));
        } catch (IOException e) {
            throw new OrganizerException("Internet"/*getString(R.string.internetConnection)*/);
        } catch (Exception e) {
            throw new OrganizerException("Erreur en essayant de récupérer les données pour l'organisateur");
        }
        return organizer;
    }

    public ArrayList<Organizer> getAllOrganizers() throws OrganizerException {
        ArrayList<Organizer> organizers;
        try {
            organizers = jsonToOrganizers(api.get("organizer"));
        } catch (IOException e) {
            throw new OrganizerException("Problème avec votre connexion internet");
        } catch (Exception e) {
            throw new OrganizerException("Erreur en essayant de récupérer les données pour les organisateurs");
        }
        return organizers;
    }

    public ArrayList<OrganizerType> getAllOrganizersNameByType() throws OrganizerException {
        ArrayList<OrganizerType> organizers;
        try {
            organizers = jsonToOrganizersNameByType(api.get("organizersName"));
        } catch (IOException e) {
            throw new OrganizerException("Problème avec votre connexion internet");
        } catch (Exception e) {
            throw new OrganizerException("Erreur en essayant de récupérer les données pour les organisateurs");
        }
        return organizers;
    }

    private Organizer jsonToOrganizer(String stringJSON) throws Exception {
        JSONObject jsonOrganizer = new JSONObject(stringJSON);
        ArrayList<DrinkCategory> menu;

        JSONArray jsonMenu = jsonOrganizer.getJSONArray("menu");
        if (jsonMenu.length() > 0) {
            JSONObject jsonDrinkCategory;
            JSONArray jsonDrinks;
            JSONObject jsonDrink;

            menu = new ArrayList<>();
            for (int i = 0; i < jsonMenu.length(); i++) {
                jsonDrinkCategory = jsonMenu.getJSONObject(i);
                jsonDrinks = jsonDrinkCategory.getJSONArray("drinks");

                ArrayList<Drink> drinks = new ArrayList<>();
                for (int j = 0; j < jsonDrinks.length(); j++) {
                    jsonDrink = jsonDrinks.getJSONObject(j);
                    drinks.add(new Drink(jsonDrink.getString("name"), jsonDrink.getDouble("price")));
                }
                menu.add(new DrinkCategory(jsonDrinkCategory.getString("name"), drinks));
            }
        } else {
            menu = null;
        }

        return new Organizer(
                jsonOrganizer.getInt("id"),
                jsonOrganizer.getString("name"),
                jsonOrganizer.getString("type"),
                jsonOrganizer.getString("description"),
                menu,
                jsonOrganizer.getString("promotion"),
                jsonOrganizer.getString("facebookLink"),
                jsonOrganizer.getString("address"),
                jsonOrganizer.getString("phoneNumber")
        );
    }

    private ArrayList<Organizer> jsonToOrganizers(String stringJSON) throws Exception {
        JSONArray jsonOrganizers = new JSONArray(stringJSON);
        ArrayList<Organizer> organizers = new ArrayList<>();
        for (int i = 0; i < jsonOrganizers.length(); i++) {
            organizers.add(jsonToOrganizer(jsonOrganizers.getString(i)));
        }
        return organizers;
    }

    private ArrayList<OrganizerType> jsonToOrganizersNameByType(String stringJSON) throws Exception {
        JSONArray jsonOrganizersType = new JSONArray(stringJSON);
        JSONObject jsonOrganizerType;
        JSONArray jsonOrganizers;
        ArrayList<OrganizerType> organizerType = new ArrayList<>();
        ArrayList<Organizer> organizers;

        for (int j = 0; j < jsonOrganizersType.length(); j++) {
            jsonOrganizerType = jsonOrganizersType.getJSONObject(j);
            jsonOrganizers = jsonOrganizerType.getJSONArray("organizers");
            organizers = new ArrayList<>();
            for (int i = 0; i < jsonOrganizers.length(); i++) {
                organizers.add(new Organizer(jsonOrganizers.getJSONObject(i).getInt("id"), jsonOrganizers.getJSONObject(i).getString("name")));
            }
            organizerType.add(new OrganizerType(jsonOrganizerType.getString("type"), organizers));
        }
        return organizerType;
    }
}
