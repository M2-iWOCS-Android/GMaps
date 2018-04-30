package com.example.juliensautereau.gmaps;

import android.content.ContentValues;

public class Point {

    private int id;
    private String libelle;
    private String description;
    private double latitude;
    private double longitude;
    private String imageSrc;
    private int affiche;

    public Point(){
        this("", "", 0, 0, "");
    }

    public Point(ContentValues cv) {
        this.libelle = (String)cv.get("libelle");
        this.description = (String)cv.get("description");
        this.latitude = (double)cv.get("latitude");
        this.longitude = (double)cv.get("longitude");
        this.imageSrc = (String)cv.get("imageSrc");
        this.affiche = 1;
    }

    public Point(String libelle, String description, double latitude, double longitude, String imageSrc){
        this.libelle = libelle;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageSrc = imageSrc;
        this.affiche = 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getImageSrc() { return imageSrc; }

    public void setImageSrc(String imageSrc) { this.imageSrc = imageSrc; }

    public int getAffiche() { return this.affiche; }

    public void setAffiche(int affiche) { this.affiche = affiche; }

    public String toString(){
        return "Point : " + libelle + " : " + description + " " + " : Coordonnées - " + latitude + " // " + longitude + " + " + imageSrc + " (" + affiche + " )";
        //return "ID : "+id+"\nISBN : "+isbn+"\nTitre : "+titre;
    }
}
