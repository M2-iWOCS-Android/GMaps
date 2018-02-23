package com.example.juliensautereau.gmaps;

import android.database.MatrixCursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        final ArrayList<String> col1 = new ArrayList<String>();
        final ArrayList<Boolean> col2 = new ArrayList<Boolean>();

        // On vide avant ajout de nouvelles données
        col1.removeAll(col1);
        col2.removeAll(col2);

        BDD b = new BDD(this);

        String message = "";
        boolean presence = false;
        String heure = "";

        for(Point point : b.getAllPoints()) {

            if(point.getAffiche() == 1) {
                presence = true;
            }
            else
            {
                presence = false;
            }

            message += point.getLibelle() + " / " + point.getDescription() + " / " + " ( " + presence + " ) \n";
            col1.add(point.getLibelle());
            col2.add(presence);

        }

        TableLayout table = (TableLayout) findViewById(R.id.idTable); // on prend le tableau défini dans le layout
        TableRow row; // création d'un élément : ligne
        TextView tv1;
        ToggleButton tv2; // création des cellules

        // pour chaque ligne
        for(int i=0;i<col1.size();i++) {

            // On vire toutes les vues
            table.removeAllViews();

            row = new TableRow(this); // création d'une nouvelle ligne

            tv1 = new TextView(this); // création cellule
            tv1.setTextColor(Color.WHITE); // ajout de couleur
            tv1.setText(col1.get(i)); // ajout du texte
            tv1.setGravity(Gravity.CENTER); // centrage dans la cellule

            // adaptation de la largeur de colonne à l'écran :
            tv1.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );

            // idem 2ème cellule
            tv2 = new ToggleButton(this);

            // par défaut
            tv2.setChecked(true);

            // on met à jour par rapport à notre tableau
            tv2.setChecked(col2.get(i));
            tv2.setGravity(Gravity.CENTER);
            tv2.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );

            // ajout des cellules à la ligne
            row.addView(tv1);
            row.addView(tv2);

            // ajout de la ligne au tableau
            table.addView(row);
        }
    }
}
