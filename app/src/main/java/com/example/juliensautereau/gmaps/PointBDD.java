package com.example.juliensautereau.gmaps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PointBDD {


    private static final int VERSION_BDD = 1;

    private static final String TABLE_POINT = "table_point";
    private static final String COL_ID = "_id";
    private static final int NUM_COL_ID = 0;

    private static final String COL_LIBELLE = "libelle";
    private static final int NUM_COL_LIBELLE = 1;

    private static final String COL_DESCRIPTION = "description";
    private static final int NUM_COL_DESCRIPTION = 2;

    private static final String COL_LATITUDE = "latitude";
    private static final int NUM_COL_LATITUDE = 3;

    private static final String COL_LONGITUDE = "longitude";
    private static final int NUM_COL_LONGITUDE = 4;

    private static final String COL_IMAGE = "imageSrc";
    private static final int NUM_COL_IMAGE = 5;

    private static final String COL_AFFICHE = "affiche";
    private static final int NUM_COL_AFFICHE = 6;

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
        values.put(COL_LIBELLE, point.getLibelle());
        values.put(COL_DESCRIPTION, point.getDescription());
        values.put(COL_LATITUDE, point.getLatitude());
        values.put(COL_LONGITUDE, point.getLongitude());
        values.put(COL_IMAGE, point.getImageSrc());
        values.put(COL_AFFICHE, point.getAffiche());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_POINT, null, values);
    }

    public int updatePoint(String libelle, int affiche, Point point){
        ContentValues values = new ContentValues();
        values.put(COL_LIBELLE, point.getLibelle());
        values.put(COL_DESCRIPTION, point.getDescription());
        values.put(COL_LATITUDE, point.getLatitude());
        values.put(COL_IMAGE, point.getImageSrc());
        values.put(COL_LONGITUDE, point.getLongitude());
        values.put(COL_AFFICHE, affiche);

        return bdd.update(TABLE_POINT, values, COL_LIBELLE + " LIKE \"" + libelle +"\"", null);
    }

    public void reinitDB() {

        this.open();

        // on supprime tous les points inscrits en base de données
        for(Point point : getPointAll()) {

            this.removePointWithID(point.getId());
        }

        // on rajoute les valeurs initiales
        this.initPoint();

        this.close();
    }

    public void initPoint() {
        //Création des points
        if ( !existePoint("Université Le Havre") ) {
            Point pt1 = new Point("Université Le Havre", "Lieu d'étude universitaire", 49.4964477, 0.12827249999998003, "");
            insertPoint(pt1);
        }
    }

    public int removePointWithID(int id){
        //Suppression d'un point de la BDD grâce à l'ID
        return bdd.delete(TABLE_POINT, COL_ID + " = " +id, null);
    }

    public Point getPointWithLibelle(String libelle){
        //Récupère dans un Cursor les valeurs correspondant à un point contenu dans la BDD (ici on sélectionne le point grâce à son libellé)
        Cursor c = bdd.query(TABLE_POINT, new String[] {"*"}, COL_LIBELLE + " LIKE \"" + libelle +"\"", null, null, null, null);
        return cursorToPoint(c);
    }

    //retourne tous les points
    public ArrayList<Point> getPointAll(){
        //Récupère dans un Cursor les valeurs correspondant à tous les points contenus dans la BDD
        Cursor c = bdd.query(TABLE_POINT, new String[] {"*"}, null, null, null, null, null);
        return cursorToPoints(c);
    }

    public void raz() {
        for(Point point : getPointAll()) {
            ContentValues values = new ContentValues();
            values.put(COL_LIBELLE, point.getLibelle());
            values.put(COL_DESCRIPTION, point.getDescription());
            values.put(COL_LATITUDE, point.getLatitude());
            values.put(COL_LONGITUDE, point.getLongitude());
            values.put(COL_IMAGE, point.getImageSrc());
            values.put(COL_AFFICHE, point.getAffiche());
            bdd.update(TABLE_POINT, values, COL_LIBELLE + " LIKE \"" + point.getLibelle()+"\"", null);
        }
    }


    private ArrayList<Point> cursorToPoints(Cursor c) {
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        ArrayList<Point> listE = new ArrayList<Point>();
        //Sinon on se place sur le premier élément
        c.moveToFirst();

        while(!c.isAfterLast()) {


            //On crée un point
            Point point = new Point();
            //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
            point.setId(c.getInt(NUM_COL_ID));
            point.setLibelle(c.getString(NUM_COL_LIBELLE));
            point.setDescription(c.getString(NUM_COL_DESCRIPTION));
            point.setLatitude(c.getFloat(NUM_COL_LATITUDE));
            point.setLongitude(c.getFloat(NUM_COL_LONGITUDE));
            point.setImageSrc(c.getString(NUM_COL_IMAGE));
            point.setAffiche(c.getInt(NUM_COL_AFFICHE));

            listE.add(point);
            c.moveToNext();
        }



        //On ferme le cursor
        c.close();

        if(listE.isEmpty()) {
            Point bidon = new Point();
            listE.add(bidon);
        }

        //On retourne le point
        return listE;
    }

    public boolean existePoint(String libelle) {

        Cursor c = bdd.query(TABLE_POINT, new String[] {"*"}, COL_LIBELLE + " LIKE \"" + libelle +"\"", null, null, null, null);
        if(c.getCount() > 0) {
            return true;
        }
        else
        {
            return false;
        }
    }

    public String getPointInfos(String libelle) {
        Cursor c = bdd.query(TABLE_POINT, new String[] {"*"}, COL_LIBELLE + " LIKE \"" + libelle +"\"", null, null, null, null);
        Point e = cursorToPoint(c);

        Date cDate = new Date();
        String date = new SimpleDateFormat("dd-MM-yyyy").format(cDate);
        String time = new SimpleDateFormat("HH:mm:ss").format(cDate);

        return "";
    }

    //Cette méthode permet de convertir un cursor en un point
    private Point cursorToPoint(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On crée un point
        Point point = new Point();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        point.setId(c.getInt(NUM_COL_ID));
        point.setLibelle(c.getString(NUM_COL_LIBELLE));
        point.setDescription(c.getString(NUM_COL_DESCRIPTION));
        point.setLatitude(c.getFloat(NUM_COL_LATITUDE));
        point.setLongitude(c.getFloat(NUM_COL_LONGITUDE));
        point.setImageSrc(c.getString(NUM_COL_IMAGE));
        point.setAffiche(c.getInt(NUM_COL_AFFICHE));

        //On ferme le cursor
        c.close();

        //On retourne le point
        return point;
    }

}
