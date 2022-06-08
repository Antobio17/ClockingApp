package com.example.server;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;

import com.example.server.databinding.ActivityMainBinding;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<String> list = new ArrayList<>();
    View view;
    private ActivityMainBinding binding;
    IntentIntegrator integrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        view = binding.getRoot();

        recyclerView = findViewById(R.id.recycleView);

        integrator = new IntentIntegrator(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (resultCode != 0) {
            String contents = result.getContents();
            list.add(contents);

            ClockingAdapter adapter =
                    new ClockingAdapter(this.list, view.getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            recyclerView.setAdapter(adapter);
        }
    }

    public void openScanner(View view)
    {
        integrator.setCameraId(Camera.CameraInfo.CAMERA_FACING_FRONT);
        integrator.setPrompt("Escanee el QR");
        integrator.initiateScan();
    }
}