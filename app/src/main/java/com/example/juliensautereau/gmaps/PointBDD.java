package com.example.juliensautereau.gmaps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PointBDD {


    private static final int VERSION_BDD = 1;

    private static final String TABLE_POINT = "table_point";
    private static final String COL_ID = "_id";
    private static final int NUM_COL_ID = 0;

    private static final String COL_NOM = "libelle";
    private static final int NUM_COL_NOM = 1;

    private static final String COL_PRENOM = "description";
    private static final int NUM_COL_PRENOM = 2;

    private static final String COL_NETUDIANT = "latitude";
    private static final int NUM_COL_NETUDIANT = 3;

    private static final String COL_PRESENT = "longitude";
    private static final int NUM_COL_PRESENT = 4;

    private static final String COL_HEURE = "affiche";
    private static final int NUM_COL_HEURE = 5;

    private SQLiteDatabase bdd;

    private Database maBaseSQLite;

    public PointBDD(Context context){
        //On crée la BDD et sa table
        maBaseSQLite = new Database(context, TABLE_POINT, null, VERSION_BDD);
    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void destroy() {
        maBaseSQLite.onUpgrade(bdd, VERSION_BDD, VERSION_BDD);
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insertPoint(Point point){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_NOM, point.getLibelle());
        values.put(COL_PRENOM, point.getDescription());
        values.put(COL_NETUDIANT, point.getLatitude());
        values.put(COL_PRESENT, point.getLongitude());
        values.put(COL_HEURE, point.getAffiche());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_POINT, null, values);
    }

    public int updatePoint(String libelle, int affiche, Point point){
        //La mise à jour d'un etudiant dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel étudiant on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_NOM, point.getLibelle());
        values.put(COL_PRENOM, point.getDescription());
        values.put(COL_NETUDIANT, point.getLatitude());
        values.put(COL_PRESENT, point.getLongitude());
        values.put(COL_HEURE, affiche);

        return bdd.update(TABLE_POINT, values, COL_NOM + " LIKE \"" + libelle +"\"", null);
    }

    public void reinitDB() {

        this.open();

        // on supprime tous les points inscrit en bdd
        for(Point point : getEtudiantAll()) {

            this.removePointWithID(point.getId());
        }

        // on rajoute les valeurs initiales
        this.initPoint();

        this.close();
    }

    public void initPoint() {

        Point pt1 = new Point("Université Le Havre", "Lieu d'étude universitaire", 49.4964477, 0.12827249999998003);
        insertPoint(pt1);
    }

    public int removePointWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(TABLE_POINT, COL_ID + " = " +id, null);
    }

    public Point getEtudiantWithNumEt(String NumEtudiant){
        //Récupère dans un Cursor les valeurs correspondant à un étudiant contenu dans la BDD (ici on sélectionne l'étudiant grâce à son num étudiant)
        Cursor c = bdd.query(TABLE_POINT, new String[] {"*"}, COL_NOM + " LIKE \"" + NumEtudiant +"\"", null, null, null, null);
        return cursorToEtudiant(c);
    }

    //retourne tous les étudiants
    public ArrayList<Point> getEtudiantAll(){
        //Récupère dans un Cursor les valeurs correspondant à tous les étudiants contenus dans la BDD
        Cursor c = bdd.query(TABLE_POINT, new String[] {"*"}, null, null, null, null, null);
        return cursorToEtudiants(c);
    }

    public ArrayList<Point> getEtudiantHere(int here){
        //Récupère dans un Cursor les valeurs correspondant à un étudiant contenu dans la BDD (ici on sélectionne l'étudiant si il est présent)
        Cursor c = bdd.query(TABLE_POINT, new String[] {"*"}, COL_PRESENT + " = \"" + here +"\"", null, null, null, null);
        return cursorToEtudiants(c);
    }

    public void raz() {
        for(Point point : getEtudiantAll()) {
            ContentValues values = new ContentValues();
            values.put(COL_NOM, point.getLibelle());
            values.put(COL_PRENOM, point.getDescription());
            values.put(COL_NETUDIANT, point.getLatitude());
            values.put(COL_PRESENT, point.getLongitude());
            values.put(COL_HEURE, point.getAffiche());
            bdd.update(TABLE_POINT, values, COL_NOM + " LIKE \"" + point.getLibelle()+"\"", null);
        }
    }


    private ArrayList<Point> cursorToEtudiants(Cursor c) {
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        ArrayList<Point> listE = new ArrayList<Point>();
        //Sinon on se place sur le premier élément
        c.moveToFirst();

        while(!c.isAfterLast()) {


            //On créé un étudiant
            Point point = new Point();
            //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
            point.setId(c.getInt(NUM_COL_ID));
            point.setLibelle(c.getString(NUM_COL_NOM));
            point.setDescription(c.getString(NUM_COL_PRENOM));
            point.setLatitude(c.getFloat(NUM_COL_NETUDIANT));
            point.setLongitude(c.getFloat(NUM_COL_PRESENT));
            point.setAffiche(c.getInt(NUM_COL_HEURE));

            listE.add(point);
            c.moveToNext();
        }



        //On ferme le cursor
        c.close();

        if(listE.isEmpty()) {
            Point bidon = new Point();
            listE.add(bidon);
        }

        //On retourne l'etudiant
        return listE;
    }

    public boolean existeEtudiant(String NumEtudiant) {

        Cursor c = bdd.query(TABLE_POINT, new String[] {"*"}, COL_NOM + " LIKE \"" + NumEtudiant +"\"", null, null, null, null);
        if(c.getCount() > 0) {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public String getEtudiantInfos(String NumEtudiant) {
        Cursor c = bdd.query(TABLE_POINT, new String[] {"*"}, COL_NOM + " LIKE \"" + NumEtudiant +"\"", null, null, null, null);
        Point e = cursorToEtudiant(c);
        
        Date cDate = new Date();
        String date = new SimpleDateFormat("dd-MM-yyyy").format(cDate);
        String time = new SimpleDateFormat("HH:mm:ss").format(cDate);
        
        return "";
        //return  e.getNom() + " " + e.getPrenom() + " identifié !\n le " + date + " à " + e.getHeure();
        
    }

    //Cette méthode permet de convertir un cursor en un Etudiant
    private Point cursorToEtudiant(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un étudiant
        Point point = new Point();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        point.setId(c.getInt(NUM_COL_ID));
        point.setLibelle(c.getString(NUM_COL_NOM));
        point.setDescription(c.getString(NUM_COL_PRENOM));
        point.setLatitude(c.getFloat(NUM_COL_NETUDIANT));
        point.setLongitude(c.getFloat(NUM_COL_PRESENT));
        point.setAffiche(c.getInt(NUM_COL_HEURE));

       // System.out.println("Num col heure : " + c.getString(NUM_COL_HEURE));

        //On ferme le cursor
        c.close();

        //On retourne l'etudiant
        return point;
    }

}
