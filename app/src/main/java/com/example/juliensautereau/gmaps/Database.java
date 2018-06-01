package com.example.juliensautereau.gmaps;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    private static final String TABLE_POINT = "table_point";
    private static final String COL_ID = "_id";
    private static final String COL_LIBELLE = "libelle";
    private static final String COL_DESCRIPTION = "description";
    private static final String COL_LATITUDE = "latitude";
    private static final String COL_LONGITUDE = "longitude";
    private static final String COL_IMAGE = "imageSrc";
    private static final String COL_AFFICHE = "affiche";

    private static final String CREATE_BDD = "CREATE TABLE IF NOT EXISTS " + TABLE_POINT + " ("
        + COL_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " + COL_LIBELLE + " TEXT NOT NULL, "
        + COL_DESCRIPTION + " TEXT NOT NULL, " + COL_LATITUDE + " DOUBLE NOT NULL, " + COL_LONGITUDE + " DOUBLE NOT NULL, " + COL_IMAGE + " TEXT NULL, " + COL_AFFICHE + " INTEGER)";

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
        // supprime et recrée la table afin de faire repartir les id de 0
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POINT + ";");
        onCreate(db);
    }
}
