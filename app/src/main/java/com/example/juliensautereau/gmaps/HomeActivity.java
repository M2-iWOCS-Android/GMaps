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

    protected void onDestroy() {
        super.onDestroy();
        bdd.stop();
    }

    public void onBackPressed() {
        bdd.stop();
        this.finish();
    }

    // On accède à la map
    public void accesMap(View v) {
        bdd.stop();
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }

    // On quitte l'application
    public void quitApp(View v) {
        bdd.stop();
        finishAffinity();
    }

    // On accède aux paramètres
    public void accesSettings(View v) {
        bdd.stop();
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }


    // On accède aux filtrages
    public void accesFilter(View v) {
        Intent i = new Intent(this, FilterActivity.class);
        startActivity(i);

        bdd.stop();
        bdd = new BDD(this);

        String message = "";
        String presence = "";
        String heure = "";

        for(Point point : bdd.getAllPoints()) {

            if(point.getAffiche() == 1) {
                presence = "Afficher";
            }
            else
            {
                presence = "Ne pas afficher";
            }

            message += point.getLibelle() + " / " + point.getDescription() + " / " + " ( " + presence + " ) \n";
        }

        System.out.println(message.toString());

        bdd.stop();
    }
}
