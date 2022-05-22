package com.example.clockingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.clockingapp.model.AppDatabase;
import com.example.clockingapp.model.Schedule;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.clockingapp.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import java.security.Signature;
import java.util.Objects;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    public static AppDatabase db;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    public static Context context;

    public int AUTHENTICATE_ACTION = 0;

    static final Migration MIGRATION_1_1 = new Migration(1, 1) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL(Schedule.dropTable());
            database.execSQL(Schedule.createTable());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_clocking_in, R.id.navigation_schedule)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Inicialización de la Base de datos
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "clockingapp")
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_1_1)
                .build();

        // Inicialización de Biométrica
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                if (AUTHENTICATE_ACTION == 0) {
                    Snackbar.make(
                            binding.getRoot(), "¡Buenos días! Que tengas una buena jornada de trabajo :)",
                            Snackbar.LENGTH_LONG
                    ).setAction("Action", null).show();
                } else if(AUTHENTICATE_ACTION == 1) {
                    Snackbar.make(
                            binding.getRoot(), "¡Hasta mañana!",
                            Snackbar.LENGTH_LONG
                    ).setAction("Action", null).show();
                }
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                biometricPrompt.cancelAuthentication();

                Intent intent = new Intent(context, PINCodeActivity.class);
                int requestCode = 0;
                startActivityForResult(intent, requestCode);
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Identifiquese para su registro")
                .setSubtitle("Autenticación con huella dactilar para el fichaje laboral")
                .setNegativeButtonText("Cancelar")
                .build();
    }

    public void onClockingIn(View view) {
        AUTHENTICATE_ACTION = 0;
//        Intent intent = new Intent(context, PINCodeActivity.class);
//        startActivity(intent);
        biometricPrompt.authenticate(promptInfo);
    }

    public void onClockingOut(View view) {
        AUTHENTICATE_ACTION = 1;
        biometricPrompt.authenticate(promptInfo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if (AUTHENTICATE_ACTION == 0) {
                Snackbar.make(
                        binding.getRoot(),
                        "¡Buenos días! Que tengas una buena jornada de trabajo :)",
                        Snackbar.LENGTH_LONG
                ).setAction("Action", null).show();
            } else if(AUTHENTICATE_ACTION == 1) {
                Snackbar.make(
                        binding.getRoot(), "¡Hasta mañana!",
                        Snackbar.LENGTH_LONG
                ).setAction("Action", null).show();
            }
        }
    }
}