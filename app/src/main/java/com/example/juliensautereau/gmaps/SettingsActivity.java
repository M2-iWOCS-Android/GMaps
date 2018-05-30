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

    @Override
    protected void onSaveInstanceState ( Bundle bundle ) {
        super.onSaveInstanceState(bundle);
    }

    @Override
    protected void onRestoreInstanceState ( Bundle bundle ) {
        super.onRestoreInstanceState(bundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bdd.stop();
    }

    public void clearDB(View v) {

        bdd.raz();
        Toast.makeText(this, "Base de données nettoyée intégralement", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        bdd.stop();
        this.finish();
    }
}
