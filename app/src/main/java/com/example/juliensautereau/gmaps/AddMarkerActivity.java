package com.example.juliensautereau.gmaps;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddMarkerActivity extends AppCompatActivity {

    private final static int REQUEST_CAMERA = 1102;
    public static final String CAMERA_IMAGES_DIR = "Markers";
    String mOutputFilePath;

    private double latitude = 0;
    private double longitude = 0;

    EditText libelleMarker, descriptionMarker;

    TextView pathImage;

    BDD bdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        bdd = new BDD(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_marker);

        pathImage = findViewById(R.id.path_image);
        libelleMarker = findViewById(R.id.libelle_marker);
        descriptionMarker = findViewById(R.id.description_marker);

        Bundle bundle = getIntent().getExtras();

        latitude = bundle.getDouble("coordX");
        longitude = bundle.getDouble("coordY");

        System.out.println(latitude + " : " + longitude);
    }

    protected void onDestroy() {
        super.onDestroy();
        bdd.stop();
    }

    public void onBackPressed() {
        //TODO Bloquer le bouton Retour en arrière
    }

    public File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), CAMERA_IMAGES_DIR + "/");
        if (!storageDir.exists())
            storageDir.mkdir();
        return new File(storageDir, imageFileName + ".jpg");
    }

    public void takePhoto(View v) {
        Intent intent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            Uri photoURI = FileProvider.getUriForFile(this,
                    getPackageName() + ".fileprovider",
                    photoFile);
            mOutputFilePath = photoFile.getAbsolutePath();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(intent, REQUEST_CAMERA);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {

            onCaptureImageResult();
        }
    }


    private void onCaptureImageResult() {
        if (mOutputFilePath != null) {
            File f = new File(mOutputFilePath);
            try {
                //System.out.println("Etape 1" + mOutputFilePath);
                pathImage.setText(mOutputFilePath);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Erreur image " + e.getMessage());
            }
        }
    }

    public static void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    public void quitAdd(View v) {

        if(!pathImage.getText().toString().equals("")) {

            File file = new File(pathImage.getText().toString());
            if (file.exists())
            {
                file.delete();
            }

        }

        this.finish();
    }

    public void addMarker(View v) {

        // On vérifie chaque champs obligatoire
        if(libelleMarker.getText().toString().equals("")) {
            Toast.makeText(AddMarkerActivity.this, "Le libelle n'est pas renseigné", Toast.LENGTH_LONG).show();
        }
        else if(descriptionMarker.getText().toString().equals("")) {
            Toast.makeText(AddMarkerActivity.this, "La description n'est pas correcte", Toast.LENGTH_LONG).show();
        }
        else
        {
            System.out.println(pathImage.getText().toString());
            if(pathImage.getText().toString().equals("aucun")) {

                Point pt2 = new Point(libelleMarker.getText().toString(), descriptionMarker.getText().toString(), latitude, longitude, "");
                bdd.pointBDD.insertPoint(pt2);
                Toast.makeText(AddMarkerActivity.this, "Ajout du marker réussi", Toast.LENGTH_LONG).show();
                Intent i = new Intent(AddMarkerActivity.this, HomeActivity.class);
                startActivity(i);
                AddMarkerActivity.this.finish();
            }
            else
            {
                Point pt1 = new Point(libelleMarker.getText().toString(), descriptionMarker.getText().toString(), latitude, longitude, pathImage.getText().toString());
                bdd.pointBDD.insertPoint(pt1);
                Toast.makeText(AddMarkerActivity.this, "Ajout du marker réussi", Toast.LENGTH_LONG).show();
                Intent i = new Intent(AddMarkerActivity.this, HomeActivity.class);
                startActivity(i);
                AddMarkerActivity.this.finish();
            }
        }
    }
}
