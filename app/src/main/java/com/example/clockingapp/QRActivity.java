package com.example.clockingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.clockingapp.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRActivity extends AppCompatActivity {

    private ImageView imgQR;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        imgQR = findViewById(R.id.qrCode);

        try {
            String code = (String)getIntent().getSerializableExtra("code");
            String message = (String)getIntent().getSerializableExtra("message");
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(
                    code, BarcodeFormat.QR_CODE, 750, 750
            );
            imgQR.setImageBitmap(bitmap);

            Snackbar.make(
                    binding.getRoot(),
                    message,
                    Snackbar.LENGTH_LONG
            ).setAction("Action", null).show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}