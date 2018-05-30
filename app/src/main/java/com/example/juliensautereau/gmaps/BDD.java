package com.example.juliensautereau.gmaps;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

public class BDD {

    PointBDD pointBDD;

    static boolean initDB = false;

    public BDD(HomeActivity th) {
        //Création de la BDD point
        pointBDD = new PointBDD(th);

        //On ouvre la base de données pour écrire dedans
        pointBDD.open();

        //Remplissage manuel
        if(!initDB) {
            pointBDD.initPoint();
            initDB = true;
        }

    }

    public void initBDD(Context th) {

    }

    public BDD(FilterActivity th) {
        pointBDD = new PointBDD(th);
        pointBDD.open();
    }

    public BDD(SettingsActivity th) {
        pointBDD = new PointBDD(th);
        pointBDD.open();
    }

    public BDD(MapsActivity th) {
        pointBDD = new PointBDD(th);
        pointBDD.open();
    }

    public BDD(AddMarkerActivity th) {
        pointBDD = new PointBDD(th);
        pointBDD.open();
    }

    public ArrayList<Point> here(int i) {
        return pointBDD.getEtudiantHere(i);
    }

    public void stop() {  pointBDD.close(); }

    public void raz() {
        pointBDD.reinitDB();
    }

    public void update(String libelle, int affiche) {
        Point p = pointBDD.getEtudiantWithNumEt(libelle);
        pointBDD.updatePoint(libelle, affiche, p);
    }

    //Récupération de tous les étudiants
    public ArrayList<Point> getAllPoints() {
        return pointBDD.getEtudiantAll();
    }
}
