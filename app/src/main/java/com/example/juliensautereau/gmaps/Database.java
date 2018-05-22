package com.example.juliensautereau.gmaps;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Database extends SQLiteOpenHelper {

    private static final String TABLE_POINT = "table_point";
    private static final String COL_ID = "_id";
    private static final String COL_NOM = "libelle";
    private static final String COL_PRENOM = "description";
    private static final String COL_NETUDIANT = "latitude";
    private static final String COL_PRESENT = "longitude";
    private static final String COL_IMAGE = "imageSrc";
    private static final String COL_HEURE = "affiche";

    private static final String CREATE_BDD = "CREATE TABLE IF NOT EXISTS " + TABLE_POINT + " ("
            + COL_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " + COL_NOM + " TEXT NOT NULL, "
            + COL_PRENOM + " TEXT NOT NULL, " + COL_NETUDIANT + " DOUBLE NOT NULL, " + COL_PRESENT + " DOUBLE NOT NULL, " + COL_IMAGE + " TEXT NULL, " + COL_HEURE + " INTEGER)";

    public Database(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //on crée la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // supprime et recréé la table afin de faire repartir les id de 0
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POINT + ";");
        onCreate(db);
    }
}
