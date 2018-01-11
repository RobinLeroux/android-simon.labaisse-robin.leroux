package be.henallux.iesn.namurbynight.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import be.henallux.iesn.namurbynight.R;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Intent intent = new Intent(Home.this, EventsDisplay.class);
        startActivity(intent);
    }
    //Log.d("EVENTS", "Value: " + listEvents);
}
