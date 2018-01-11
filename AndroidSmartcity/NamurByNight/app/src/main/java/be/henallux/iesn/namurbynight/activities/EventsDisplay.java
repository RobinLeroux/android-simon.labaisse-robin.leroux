package be.henallux.iesn.namurbynight.activities;

import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import be.henallux.iesn.namurbynight.DAO.EventDAO;
import be.henallux.iesn.namurbynight.R;
import be.henallux.iesn.namurbynight.model.Event;
import be.henallux.iesn.namurbynight.model.EventCalendar;

import java.util.ArrayList;
import java.util.HashMap;

public class EventsDisplay extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    ExpandableListView expandableEventsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_list_view);
        expandableEventsListView = findViewById(R.id.expandableListViewEvents);

        //Toolbar & Navigation Drawer Configurations
        configureNavigationDrawer();
        configureToolbar();

        //ID de l'organisateur souhaité
        Integer id;
        if (this.getIntent().getExtras() != null) {
            Bundle bundle = this.getIntent().getExtras();
            id = bundle.getInt("id");
        } else {
            id = null;
        }
        new LoadEvents().execute(id);

        final Button eventsButton = (Button) this.findViewById(R.id.eventsButton);
        final Button organizersButton = (Button) this.findViewById(R.id.organizersButton);

        organizersButton.setOnClickListener(new View.OnClickListener()
        {
            @SuppressLint("ResourceAsColor")
            public void onClick(View v)
            {
                Intent intent = new Intent(EventsDisplay.this, OrganizersDisplay.class);
                startActivity(intent);
            }
        });

        expandableEventsListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(EventsDisplay.this, EventPageDisplay.class);
                System.out.println(((EventCalendar) parent.getAdapter().getItem(groupPosition)).getEvent(childPosition).getName());
                intent.putExtra("id", ((EventCalendar) parent.getAdapter().getItem(groupPosition)).getEvent(childPosition).getId());
                startActivity(intent);

                return true;
            }
        });
    }

    private class LoadEvents extends AsyncTask<Integer,Void,ArrayList<EventCalendar>> {
        @Override
        protected ArrayList<EventCalendar> doInBackground(Integer... params) {
            EventDAO eventDAO = new EventDAO();
            ArrayList<EventCalendar> calendar = new ArrayList<>();
            try {
                if (params[0] == null) {
                    calendar = eventDAO.getWeeklyEvents();
                } else {
                    calendar = eventDAO.getEventFromOrganizer(params[0]);
                }
            } catch (final Exception e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(EventsDisplay.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
            return calendar;
        }

        @Override
        protected void onPostExecute(ArrayList<EventCalendar> eventCalendar) {
            HashMap<String, ArrayList<Event>> listEvents = new HashMap<>();
            for (int i = 0; i < eventCalendar.size(); i++) {
                listEvents.put(eventCalendar.get(i).getDate(), eventCalendar.get(i).getEvents());
            }

            ExpandableListAdapterEvents listEventsAdapter = new ExpandableListAdapterEvents(EventsDisplay.this, eventCalendar, listEvents);
            expandableEventsListView.setAdapter(listEventsAdapter);
        }
    }

    // Inflater Drawer
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.empty_menu, menu);
        return true;
    }

    // Gestion de la Toolbar
    private void configureToolbar() {
        Toolbar toolbar = EventsDisplay.this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name)
        {
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

    // Gestion de l'item sélectionné dans le Drawer
    private void configureNavigationDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.navigation);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int itemId = menuItem.getItemId();

                if (itemId == R.id.eventsDrawer) {
                    Intent intent = new Intent(EventsDisplay.this, EventsDisplay.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.organizersDrawer) {
                    Intent intent = new Intent(EventsDisplay.this, OrganizersDisplay.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }
}
