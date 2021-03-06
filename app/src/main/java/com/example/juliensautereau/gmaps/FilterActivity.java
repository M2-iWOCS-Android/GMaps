package com.example.juliensautereau.gmaps;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity {

    ArrayList<String> col1 = new ArrayList<String>();
    ArrayList<Boolean> col2 = new ArrayList<Boolean>();

    BDD b;
    ToggleButton tv2[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        refresh();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return true;
    }

    //gère le click sur une action de l'ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_register:
                updateDB();
                return true;
            case R.id.action_update:
                refresh();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void afficherMessage(String s) {

        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        b.stop();
    }

    // On met à jour l'affichage des markers
    public void updateDB() {

        for(int i = 0; i < tv2.length; i++) {

            if(col2.get(i) != tv2[i].isChecked()) {

                System.out.println(col1.get(i).toString() + " " + tv2[i].isChecked());
                int affichage = (tv2[i].isChecked()) ? 1:0;

                b.update(col1.get(i).toString(), affichage);
            }
        }

        afficherMessage("Mise à jour des markers effectuée avec succès");

        refresh();
    }

    public void refresh() {

        col1.clear();
        col2.clear();

        // On vide avant ajout de nouvelles données
        b = new BDD(this);

        boolean presence = false;

        for (Point point : b.getAllPoints()) {

            if (point.getAffiche() == 1) {
                presence = true;
            } else {
                presence = false;
            }

            col1.add(point.getLibelle());
            col2.add(presence);
        }


        TableLayout table = (TableLayout) findViewById(R.id.idTable); // on prend le tableau défini dans le layout
        TableRow row; // création d'un élément : ligne
        TextView tv1;

        System.out.println("Taille : " + col1.size());

        tv2 = new ToggleButton[col1.size()]; // création des cellules

        // On vide le tableau
        table.removeAllViews();

        // pour chaque ligne
        for (int i = 0; i < col1.size(); i++) {

            row = new TableRow(this); // création d'une nouvelle ligne

            tv1 = new TextView(this); // création cellule
            tv1.setTextColor(Color.WHITE); // ajout de couleur
            tv1.setText(col1.get(i)); // ajout du texte
            tv1.setGravity(Gravity.CENTER); // centrage dans la cellule

            // adaptation de la largeur de colonne à l'écran :
            tv1.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            // idem 2ème cellule
            tv2[i] = new ToggleButton(this);

            // par défaut
            tv2[i].setChecked(true);

            // on met à jour par rapport à notre tableau
            tv2[i].setChecked(col2.get(i));
            tv2[i].setGravity(Gravity.CENTER);
            tv2[i].setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            // ajout des cellules à la ligne
            row.addView(tv1);
            row.addView(tv2[i]);

            // ajout de la ligne au tableau
            table.addView(row);
        }
    }
}
