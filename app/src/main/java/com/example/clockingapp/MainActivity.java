package com.example.clockingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.clockingapp.model.AppDatabase;
import com.example.clockingapp.model.Schedule;
import com.example.clockingapp.model.ScheduleDao;
import com.example.clockingapp.model.Worker;
import com.example.clockingapp.model.WorkerDao;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
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

    static final Migration MIGRATION_5 = new Migration(4, 5) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL(Schedule.dropTable());
            database.execSQL(Schedule.createTable());
            database.execSQL(Worker.dropTable());
            database.execSQL(Worker.createTable());
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

        // Inicializaci??n de la Base de datos
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "clockingapp")
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_5)
                .build();

        this._initBBDD(db);

        // Inicializaci??n de Biom??trica
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

                biometricPrompt.cancelAuthentication();

                Intent intent = new Intent(context, PINCodeActivity.class);
                int requestCode = 0;
                startActivityForResult(intent, requestCode);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

                WorkerDao workerDao = db.workerDao();

                Worker worker = workerDao.findOneByCode(1997);
                _registerClocking(worker);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                biometricPrompt.cancelAuthentication();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Identifiquese para su registro")
                .setSubtitle("Autenticaci??n con huella dactilar para el fichaje laboral")
                .setNegativeButtonText("Cancelar")
                .build();
    }

    public void onClockingIn(View view) {
        AUTHENTICATE_ACTION = 0;
        biometricPrompt.authenticate(promptInfo);
    }

    public void onClockingOut(View view) {
        AUTHENTICATE_ACTION = 1;
        biometricPrompt.authenticate(promptInfo);
    }

    public void selectDayLeft(View view) {

    }

    public void selectDayRight(View view) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        WorkerDao workerDao = db.workerDao();

        Worker worker = workerDao.findOneByCode(resultCode);
        if (worker != null) {
            _registerClocking(worker);
        } else if (resultCode != RESULT_OK) {
            Snackbar.make(
                    binding.getRoot(),
                    "Trabajador no identificado",
                    Snackbar.LENGTH_LONG
            ).setAction("Action", null).show();
        }
    }

    /**
     *
     */
    private void _registerClocking(Worker worker) {
        ScheduleDao scheduleDao = db.scheduleDao();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
        Date date = calendar.getTime();
        String pattern = "dd-MM-yyyy HH:mm:ss";

        if (AUTHENTICATE_ACTION == 0) {
            Schedule schedule = scheduleDao.findLast();
            Integer id = schedule != null ? schedule.getId() + 1 : 1;

            String pattern2 = "dd-MM-yyyy",
                    dayStart = (new SimpleDateFormat(pattern2, new Locale("es", "ES"))).format(date) + " 00:00:00",
                    dayEnd = (new SimpleDateFormat(pattern2, new Locale("es", "ES"))).format(date) + " 23:59:59";
            schedule = scheduleDao.findLastChecking((Integer) worker.getId());

            if (schedule == null || !schedule.getCheckingIn().split("\\s+")[0].equals(dayStart.split("\\s+")[0])) {
                scheduleDao.insertSchedules(new Schedule(
                        id,
                        worker.getId(),
                        (new SimpleDateFormat(pattern, new Locale("es", "ES"))).format(date),
                        null,
                        (new SimpleDateFormat("EE", new Locale("es", "ES"))).format(date)
                ));

                Intent intent = new Intent(context, QRActivity.class);
                intent.putExtra(
                        "code",
                        worker.getWorker() + " entra el " +
                                (new SimpleDateFormat(pattern, new Locale("es", "ES"))).format(date)
                );
                intent.putExtra(
                        "message",
                        "??Buenos d??as! Que tengas una buena jornada de trabajo :). No te " +
                                "olvides de registrar el c??digo QR en el tel??fono de la entrada"
                );
                startActivity(intent);

            } else {
                Snackbar.make(
                        binding.getRoot(),
                        "No puedes registrar dos entradas en un mismo d??a.",
                        Snackbar.LENGTH_LONG
                ).setAction("Action", null).show();
            }
        } else if (AUTHENTICATE_ACTION == 1) {
            Schedule schedule = scheduleDao.findLastCheckingInByWorker(worker.getId());

            if (schedule != null) {
                schedule.setCheckingOut((new SimpleDateFormat(pattern, new Locale("es", "ES"))).format(date));
                scheduleDao.updateSchedules(schedule);

                Intent intent = new Intent(context, QRActivity.class);
                intent.putExtra(
                        "code",
                        worker.getWorker() + " sale el " +
                                (new SimpleDateFormat(pattern, new Locale("es", "ES"))).format(date)
                );
                intent.putExtra(
                        "message",
                        "??Hasta ma??ana!. No te " +
                                "olvides de registrar el c??digo QR en el tel??fono de la entrada"
                );
                startActivity(intent);
            } else {
                Snackbar.make(
                        binding.getRoot(), "No se puede registrar la salida.",
                        Snackbar.LENGTH_LONG
                ).setAction("Action", null).show();
            }
        }
    }

    private void _initBBDD(AppDatabase db) {
        WorkerDao workerDao = db.workerDao();

        workerDao.insertWorkers(
                new Worker(1, "Antonio", 1997)
        );
    }
}