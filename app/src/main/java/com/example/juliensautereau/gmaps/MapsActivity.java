package com.example.juliensautereau.gmaps;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private BDD bdd;

    private GoogleMap mMap;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // On centre la caméra sur Le Havre
        LatLng lh = new LatLng(49.4938, 0.1077);

        for(Point point : bdd.getAllPoints()) {

            if(point.getAffiche() == 1) {

                LatLng newP = new LatLng(point.getLatitude(), point.getLongitude());
                if(point.getImageSrc().equals("")) {
                    mMap.addMarker(new MarkerOptions().position(newP).title(point.getLibelle()).snippet(point.getDescription()));
                }
                else
                {
                    Bitmap bmp = BitmapFactory.decodeFile(point.getImageSrc());
                    int width = bmp.getWidth();
                    int height = bmp.getHeight();
                    float scaleWidth = ((float) 150) / width;
                    float scaleHeight = ((float) 150) / height;
                    // CREATE A MATRIX FOR THE MANIPULATION
                    Matrix matrix = new Matrix();

                    // RESIZE THE BIT MAP
                    matrix.postScale(scaleWidth, scaleHeight);
                    matrix.postRotate(90);

                    Bitmap resizedBitmap = Bitmap.createBitmap(
                            bmp, 0, 0, width, height, matrix, false);

                    bmp.recycle();

                    mMap.addMarker(new MarkerOptions().position(newP).title(point.getLibelle()).snippet(point.getDescription()).icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap)).anchor(0.5f, 0.5f));
                }
            }
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lh));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lh, 10));

        if(mMap != null) {

            mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                   @Override
                   public void onMapLongClick (final LatLng latLng){

                       if (marker != null) {
                           marker.remove();
                       }

                       MarkerOptions options = new MarkerOptions()
                               .title("<UNKNOWN NAME>")
                               .position(new LatLng(latLng.latitude,
                                       latLng.longitude));

                       marker = mMap.addMarker(options);

                       new AlertDialog.Builder(MapsActivity.this)
                               .setTitle("INFO : Demande d'ajout")
                               .setMessage("Souhaitez-vous ajouter ce marker ?")
                               .setCancelable(false)
                               .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {
                                       // Whatever...
                                       Toast.makeText(MapsActivity.this, "Redirection vers l'ajout du marker", Toast.LENGTH_LONG).show();
                                       Intent i = new Intent(MapsActivity.this, AddMarkerActivity.class);
                                       i.putExtra("coordX", latLng.latitude);
                                       i.putExtra("coordY", latLng.longitude);
                                       startActivity(i);
                                       MapsActivity.this.finish();
                                   }
                               })
                               .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {
                                       marker.remove();
                                   }
                               }).show();
                   }
               });
        }
    }

    @Override
    public void onBackPressed() {
        bdd.stop();
        this.finish();
    }
}
