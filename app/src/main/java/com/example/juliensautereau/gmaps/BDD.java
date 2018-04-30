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

        //Création des points
        // Point pt1 = new Point("Université Le Havre", "lieu", 0.23,0);
        /*Etudiant et1 = new Etudiant("BRUN", "Raphael", "4145f2a3e2780");
        Etudiant et2 = new Etudiant("GRENET", "Ronan", "448570a3e2780");
        Etudiant et3 = new Etudiant("SAUTEREAU", "Julien", "47a7e425f4380");
        Etudiant et4 = new Etudiant("PRED'HOMME", "Baptiste", "4430aba5c3f80");
        Etudiant et5 = new Etudiant("DEON", "Emilien", "4424d023e2780");
         Etudiant et6 = new Etudiant("DAURAT", "Corentin", "47674d2713b80");*/

        //On ouvre la base de données pour écrire dedans
        pointBDD.open();

        //Remplissage manuel
        if(!initDB) {
            pointBDD.initPoint();
            initDB = true;
        }

        /*pointBDD.insertEtudiant(et2);
        pointBDD.insertEtudiant(et3);
        pointBDD.insertEtudiant(et4);
        pointBDD.insertEtudiant(et5);
        pointBDD.insertEtudiant(et6);*/

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
    /*
    public String detectedWithNFC(String s) {
        return etudiantBdd.getEtudiantInfos(s);
    }
    */

    public void update(String libelle, int affiche) {
        Point p = pointBDD.getEtudiantWithNumEt(libelle);
        pointBDD.updatePoint(libelle, affiche, p);
    }

    //Récupération de tous les étudiants
    public ArrayList<Point> getAllPoints() {
        return pointBDD.getEtudiantAll();
    }

    //Check Etudiant
    //public boolean checkEtudiant(String s) { return etudiantBdd.existeEtudiant(s); }

    // Ajout Etudiant
    //public long ajoutEtudiant(Etudiant etu) { return etudiantBdd.insertEtudiant(etu); }

}
