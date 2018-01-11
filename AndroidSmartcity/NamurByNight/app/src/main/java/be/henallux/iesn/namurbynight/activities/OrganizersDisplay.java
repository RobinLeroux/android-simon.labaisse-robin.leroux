package be.henallux.iesn.namurbynight.activities;

import android.annotation.SuppressLint;
import android.app.ListActivity;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import be.henallux.iesn.namurbynight.DAO.OrganizerDAO;
import be.henallux.iesn.namurbynight.R;
import be.henallux.iesn.namurbynight.model.Event;
import be.henallux.iesn.namurbynight.model.EventCalendar;
import be.henallux.iesn.namurbynight.model.Organizer;
import be.henallux.iesn.namurbynight.model.OrganizerType;

import java.util.ArrayList;
import java.util.HashMap;

public class OrganizersDisplay extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    ExpandableListView expandableOrganizersListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizers_list_view);

        //Toolbar & Navigation Drawer Configurations
        configureNavigationDrawer();
        configureToolbar();

        new LoadOrganizers().execute();

        final Button eventsButton = (Button) this.findViewById(R.id.eventsButton);

        eventsButton.setOnClickListener(new View.OnClickListener()
        {
            @SuppressLint("ResourceAsColor")
            public void onClick(View v)
            {
                Intent intent = new Intent(OrganizersDisplay.this, EventsDisplay.class);
                startActivity(intent);
            }
        });

        expandableOrganizersListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(OrganizersDisplay.this, OrganizerPageDisplay.class);
                System.out.println(((EventCalendar) parent.getAdapter().getItem(groupPosition)).getEvent(childPosition).getName());
                intent.putExtra("id", ((OrganizerType) parent.getAdapter().getItem(groupPosition)).getOrganizer(childPosition).getId());
                startActivity(intent);

                return true;
            }
        });
    }

    private class LoadOrganizers extends AsyncTask<String,Void,ArrayList<OrganizerType>> {
        @Override
        protected ArrayList<OrganizerType> doInBackground(String... params) {
            OrganizerDAO organizerDAO = new OrganizerDAO();
            ArrayList<OrganizerType> organizers = new ArrayList<>();
            try {
                organizers = organizerDAO.getAllOrganizersNameByType();
            } catch(final Exception e) {
                System.out.println(e);
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(OrganizersDisplay.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
            return organizers;
        }

        @Override
        protected void onPostExecute(ArrayList<OrganizerType> organizers) {
            System.out.println(organizers);
            /*HashMap<String, ArrayList<Organizer>> listOrganizers = new HashMap<>();
            for (int i = 0; i < organizers.size(); i++) {
                listOrganizers.put(organizers.get(i).getType(), organizers.get(i).getOrganizerList());
            }

            ExpandableListAdapterOrganizers listOrganizersAdapter = new ExpandableListAdapterOrganizers(OrganizersDisplay.this, organizers, listOrganizers);
            expandableOrganizersListView.setAdapter(listOrganizersAdapter);*/
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
                    Intent intent = new Intent(OrganizersDisplay.this, EventsDisplay.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.organizersDrawer) {
                    Intent intent = new Intent(OrganizersDisplay.this, OrganizersDisplay.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }
}
