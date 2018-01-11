package be.henallux.iesn.namurbynight.DAO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import be.henallux.iesn.namurbynight.exception.EventException;
import be.henallux.iesn.namurbynight.model.Drink;
import be.henallux.iesn.namurbynight.model.DrinkCategory;
import be.henallux.iesn.namurbynight.model.Event;
import be.henallux.iesn.namurbynight.model.EventCalendar;
import be.henallux.iesn.namurbynight.model.EventDate;
import be.henallux.iesn.namurbynight.model.Organizer;

public class EventDAO {
    private ApiDAO api;
    public EventDAO() {
        this.api = new ApiDAO();
    }

    public Event getEvent(int id) throws EventException {
        Event event;
        try {
            event = jsonToEvent(api.get("event/" + id));
        } catch(IOException e) {
            throw new EventException("Problème avec votre connexion internet");
        } catch (Exception e) {
            throw new EventException("Erreur en essayant de récupérer les données pour l'événement");
        }
        return event;
    }
    public ArrayList<EventCalendar> getWeeklyEvents() throws EventException {
        return this.getWeeklyEvents(0);
    }
    public ArrayList<EventCalendar> getWeeklyEvents(int nbWeeksDifferenceFromNow) throws EventException {
        ArrayList<EventCalendar> events;
        try {
            events = jsonToEventCalendar(api.get("eventsWeekDifference/" + nbWeeksDifferenceFromNow));
        } catch(IOException e) {
            throw new EventException("Problème avec votre connexion internet");
        } catch (Exception e) {
            throw new EventException("Erreur en essayant de récupérer les données pour les événements");
        }
        return events;
    }

    public ArrayList<EventCalendar> getEventFromOrganizer(Integer organizerId) throws EventException {
        ArrayList<EventCalendar> events;
        try {
            events = jsonToEventCalendar(api.get("eventsOrganizer/" + organizerId));
        } catch(IOException e) {
            throw new EventException("Problème avec votre connexion internet");
        } catch (Exception e) {
            throw new EventException("Erreur en essayant de récupérer les données pour les événements");
        }
        return events;
    }

    private Event jsonToEvent(String stringJSON) throws Exception {
        JSONObject jsonEvent = new JSONObject(stringJSON);
        ArrayList<DrinkCategory> menu;
        EventDate date;
        Organizer organizer;

        JSONArray jsonMenu = jsonEvent.getJSONArray("menu");
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

        JSONObject jsonDate = jsonEvent.getJSONObject("date");
        date = new EventDate(
                jsonDate.getString("openDate"),
                jsonDate.getString("closeDate")
        );

        JSONObject jsonOrganizer = jsonEvent.getJSONObject("organizer");
        organizer = new Organizer(
            jsonOrganizer.getInt("id"),
            jsonOrganizer.getString("name")
        );

        return new Event(
                jsonEvent.getInt("id"),
                jsonEvent.getString("name"),
                jsonEvent.getString("description"),
                menu,
                jsonEvent.getString("promotion"),
                jsonEvent.getString("address"),
                date,
                organizer
        );
    }

    private ArrayList<Event> jsonToEvents(String stringJSON) throws Exception {
        ArrayList<Event> events = new ArrayList();
        JSONArray jsonArray = new JSONArray(stringJSON);
        JSONObject jsonEvent;
        JSONObject jsonEventDate;
        JSONObject jsonOrganizer;

        for (int i = 0; i < jsonArray.length(); i++) {
            jsonEvent = jsonArray.getJSONObject(i);
            jsonEventDate = jsonEvent.getJSONObject("DateEvent");
            jsonOrganizer = jsonEvent.getJSONObject("organizer");
            events.add(new Event(
                    jsonEvent.getInt("id"),
                    jsonEvent.getString("name"),
                    new EventDate(jsonEventDate.getString("openDate"), jsonEventDate.getString("closeDate")),
                    new Organizer(jsonOrganizer.getInt("id"), jsonOrganizer.getString("name"))
            ));
        }

        return events;
    }

    private ArrayList<EventCalendar> jsonToEventCalendar(String stringJSON) throws Exception {
        JSONObject data = new JSONObject(stringJSON);
        ArrayList<EventCalendar> eventCalendars = new ArrayList<>();
        ArrayList<Event> events;
        JSONArray jsonArray = data.getJSONArray("EventCalendar");
        JSONArray jsonEvents;
        JSONObject jsonEventCalendar;
        JSONObject jsonEvent;

        for (int i = 0; i < jsonArray.length(); i++) {
            jsonEventCalendar = jsonArray.getJSONObject(i);
            jsonEvents = jsonEventCalendar.getJSONArray("events");
            events = new ArrayList<>();
            for (int j = 0; j < jsonEvents.length(); j++) {
                jsonEvent = jsonEvents.getJSONObject(j);
                events.add(new Event(jsonEvent.getInt("id"), jsonEvent.getString("name")));
            }
            eventCalendars.add(new EventCalendar(jsonEventCalendar.getString("date"), events));
        }

        return eventCalendars;
    }
}
