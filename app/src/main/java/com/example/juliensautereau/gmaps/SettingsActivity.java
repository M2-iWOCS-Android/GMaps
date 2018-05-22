package com.example.juliensautereau.gmaps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    BDD bdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        bdd = new BDD(this);
    }

    protected void onDestroy() {
        super.onDestroy();
        bdd.stop();
    }

    public void clearDB(View v) {

        bdd.raz();
        Toast.makeText(this, "Base de données nettoyée intégralement", Toast.LENGTH_LONG).show();
    }

    public void onBackPressed() {
        bdd.stop();
        this.finish();
    }
}
