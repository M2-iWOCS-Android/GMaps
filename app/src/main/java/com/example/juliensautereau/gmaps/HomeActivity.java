package com.example.juliensautereau.gmaps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    BDD bdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bdd = new BDD(this);
    }

    // On accède à la map
    public void accesMap(View v) {
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }

    // On quitte l'application
    public void quitApp(View v) {
        this.finish();
    }

    // On accède aux paramètres
    public void accesSettings(View v) {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }


    // On accède aux filtrages
    public void accesFilter(View v) {
        Intent i = new Intent(this, FilterActivity.class);
        startActivity(i);

        BDD b = new BDD(this);

        String message = "";
        String presence = "";
        String heure = "";

        for(Point point : b.getAllPoints()) {

            if(point.getAffiche() == 1) {
                presence = "Affiché";
            }
            else
            {
                presence = "Ne pas affiché";
            }



            message += point.getLibelle() + " / " + point.getDescription() + " / " + " ( " + presence + " ) \n";
        }

        System.out.println(message.toString());
    }
}
