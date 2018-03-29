package edu.uwf.kennethdale.scavenger_client;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.Result;

import java.io.IOException;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class QRScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ////////////////////////////////////////////////////////////////////////////////////////////
    /*SurfaceView camera;
    TextView result;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(camera.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }*/
    ////////////////////////////////////////////////////////////////////////////////////////////
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;

    @Override
    public void handleResult(Result result) {
        final String scanResult = result.getText();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scannerView.resumeCameraPreview(QRScanner.this);
            }
        });
        builder.setNeutralButton("Visit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(scanResult));
                startActivity(intent);
            }
        });
        builder.setMessage(scanResult);
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scannerView = new ZXingScannerView(this);

        setContentView(scannerView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(QRScanner.this, "Permission granted.", Toast.LENGTH_LONG).show();
            } else {
                requestPermission();
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////
        /*camera = (SurfaceView) findViewById(R.id.cameraLens);
        result = (TextView) findViewById(R.id.qrResult);

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE).build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480).build();

        camera.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(QRScanner.this,
                            new String[]{Manifest.permission.CAMERA},RequestCameraPermissionID);
                }
                try {
                    cameraSource.start(camera.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });*/
    }

    /*barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {

    }*/
    //////////////////////////////////////////////////////////////////////////////////////////
    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(QRScanner.this, CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    public void onRequestPermissionResult(int requestCode, String permission[], int grantResults[]){
        switch(requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted) {
                        Toast.makeText(QRScanner.this, "Permission granted.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(QRScanner.this, "Permission denied.", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                displayAlertMessage("Allow access for both permissions.",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                            break;
                        }
                    }
                }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkPermission()){
                if(scannerView == null){
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            }
            else{
                requestPermission();
            }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        scannerView.stopCamera();
    }

    public void displayAlertMessage(String message, DialogInterface.OnClickListener listener){
        new AlertDialog.Builder(QRScanner.this)
                .setMessage(message)
                .setPositiveButton("OK", listener)
                .setNegativeButton("Cancel", null)
                .create().show();
    }
}