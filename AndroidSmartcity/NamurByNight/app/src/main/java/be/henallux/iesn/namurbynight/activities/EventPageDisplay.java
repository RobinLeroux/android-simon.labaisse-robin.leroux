package be.henallux.iesn.namurbynight.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import be.henallux.iesn.namurbynight.DAO.EventDAO;
import be.henallux.iesn.namurbynight.R;
import be.henallux.iesn.namurbynight.model.Drink;
import be.henallux.iesn.namurbynight.model.Event;

import java.util.ArrayList;
import java.util.HashMap;

public class EventPageDisplay extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    TextView eventDescription;
    TextView eventPromotions;
    TextView eventInfos;
    TextView eventOrganizer;
    ExpandableListView expandableMenuListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_page);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            //Toolbar & Navigation Drawer Configurations
            configureNavigationDrawer();
            configureToolbar();

            int id = bundle.getInt("id");
            String name = bundle.getString("name");

            new LoadOrganizer().execute(id);

            eventDescription = findViewById(R.id.eventDescription);
            eventPromotions = findViewById(R.id.eventPromotions);
            eventInfos = findViewById(R.id.eventInfos);
            eventOrganizer = findViewById(R.id.eventOrganizer);

            expandableMenuListView = findViewById(R.id.expandableListViewEventMenu);
        } else {
            Toast.makeText(EventPageDisplay.this, getString(R.string.idNotFound), Toast.LENGTH_LONG).show();
        }
    }

    private class LoadOrganizer extends AsyncTask<Integer,Void,Event> {
        @Override
        protected Event doInBackground(Integer... params) {
            EventDAO eventDAO = new EventDAO();
            Event event = new Event();
            try {
                event = eventDAO.getEvent(params[0]);
            } catch(Exception e) {
                System.out.println(e);
            }
            return event;
        }

        @Override
        protected void onPostExecute(Event event) {
            String str = (event.getPromotion() != "null") ? event.getPromotion() : getString(R.string.noPromotion);

            eventDescription.setText(event.getDescription());
            eventPromotions.setText(str);
            eventInfos.setText(event.getAddress() + "\n" + event.getDate().toString());
            eventOrganizer.setText(event.getOrganizer().getName());

            HashMap<String, ArrayList<Drink>> listDrinks = new HashMap<>();
            for (int i = 0; i < event.getMenu().size(); i++) {
                listDrinks.put(event.getMenu().get(i).getName(), event.getMenu().get(i).getDrinks());
            }

            ExpandableListAdapterMenu listDrinksAdapter = new ExpandableListAdapterMenu(EventPageDisplay.this, event.getMenu(), listDrinks);
            expandableMenuListView.setAdapter(listDrinksAdapter);
        }
    }

    //Inflate Drawer
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.empty_menu, menu);
        return true;
    }

    //Gestion de la Toolbar
    private void configureToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view)
            {
                supportInvalidateOptionsMenu();
            }
            public void onDrawerOpened(View drawerView)
            {
                supportInvalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    private void configureNavigationDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.navigation);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int itemId = menuItem.getItemId();

                if (itemId == R.id.eventsDrawer) {
                    Intent intent = new Intent(EventPageDisplay.this, EventsDisplay.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.organizersDrawer) {
                    Intent intent = new Intent(EventPageDisplay.this, OrganizersDisplay.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }
}
