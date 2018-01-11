package be.henallux.iesn.namurbynight.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import be.henallux.iesn.namurbynight.DAO.OrganizerDAO;
import be.henallux.iesn.namurbynight.R;
import be.henallux.iesn.namurbynight.model.Drink;
import be.henallux.iesn.namurbynight.model.Organizer;

import java.util.ArrayList;
import java.util.HashMap;

public class OrganizerPageDisplay extends Activity{
    Organizer organizer;

    TextView organizerType;
    TextView organizerDescription;
    TextView organizerPromotions;
    TextView organizerContact;
    Button organizerEvent;
    ExpandableListView expandableMenuListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizer_page);

        Bundle bundle = this.getIntent().getExtras();
        int id = bundle.getInt("id");

        new LoadOrganizer().execute(id);

        organizerType = findViewById(R.id.organizerType);
        organizerDescription = findViewById(R.id.organizerDescription);
        organizerPromotions = findViewById(R.id.organizerPromotions);
        organizerContact = findViewById(R.id.organizerContact);
        organizerEvent = findViewById(R.id.organizerEvent);

        expandableMenuListView = findViewById(R.id.expandableListViewEventMenu);

        organizerEvent.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(OrganizerPageDisplay.this, EventsDisplay.class);
                intent.putExtra("id", organizer.getId());
                startActivity(intent);
            }
        });
    }

    private class LoadOrganizer extends AsyncTask<Integer,Void,Organizer> {
        @Override
        protected Organizer doInBackground(Integer... params) {
            OrganizerDAO organizerDAO = new OrganizerDAO();
            Organizer organizer = new Organizer();
            try {
                organizer = organizerDAO.getOrganizer(params[0]);
            } catch(Exception e) {
                System.out.println(e);
            }
            return organizer;
        }

        @Override
        protected void onPostExecute(Organizer organizer) {
            System.out.println(organizer);
            String str;

            organizerType.setText(organizer.getType());
            organizerDescription.setText(organizer.getDescription());

            str = (organizer.getPromotion() != "null") ? organizer.getPromotion() : getString(R.string.noPromotion);
            organizerPromotions.setText(str);
            if (organizer.getAddress() != "null" && organizer.getPhoneNumber() != "null") {
                if (organizer.getAddress() != "null") {
                    str = organizer.getAddress() + "\n";
                } else {
                    str = "";
                }
                if (organizer.getPhoneNumber() != "null") {
                    str += organizer.getPhoneNumber();
                }
            } else {
                str = getString(R.string.noContact);
            }
            organizerContact.setText(str);

            if (organizer.getMenu() != null) {
                HashMap<String, ArrayList<Drink>> listDrinks = new HashMap<>();
                for (int i = 0; i < organizer.getMenu().size(); i++) {
                    listDrinks.put(organizer.getMenu().get(i).getName(), organizer.getMenu().get(i).getDrinks());
                }

                final ExpandableListAdapterMenu listDrinksAdapter = new ExpandableListAdapterMenu(OrganizerPageDisplay.this, organizer.getMenu(), listDrinks);
                expandableMenuListView.setAdapter(listDrinksAdapter);
            }
        }
    }
}
