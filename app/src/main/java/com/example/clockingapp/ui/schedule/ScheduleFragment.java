package com.example.clockingapp.ui.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.clockingapp.MainActivity;
import com.example.clockingapp.R;
import com.example.clockingapp.databinding.FragmentScheduleBinding;
import com.example.clockingapp.model.Schedule;
import com.example.clockingapp.model.ScheduleAdapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.TimeZone;

public class ScheduleFragment extends Fragment {

    private FragmentScheduleBinding binding;
    private RecyclerView scheduleRecyclerView;
    private List<Schedule> schedules = new ArrayList<>();
    public static Integer countSelectedDay = 0;
    public static Integer selectedDay = null;
    private TextView textView;
    private ScheduleViewModel scheduleViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        scheduleViewModel =
                new ViewModelProvider(this).get(ScheduleViewModel.class);

        binding = FragmentScheduleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        textView = binding.textSchedule;

        calculateScheduleDay(root, countSelectedDay, selectedDay);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void calculateScheduleDay(View view, Integer countSelectedDay, Integer selectedDay)
    {
        scheduleRecyclerView = view.findViewById(R.id.scheduleRecyclerView);

        // Obtener día de la semana actual
        String weekDay = "";
        if (selectedDay == null) {
            selectedDay = (Calendar.getInstance()).get(Calendar.DAY_OF_WEEK);
        }
        switch (selectedDay) {
            case Calendar.SUNDAY:
                weekDay = "Domingo";
                break;
            case Calendar.MONDAY:
                weekDay = "Lunes";
                break;
            case Calendar.TUESDAY:
                weekDay = "Martes";
                break;
            case Calendar.WEDNESDAY:
                weekDay = "Miercoles";
                break;
            case Calendar.THURSDAY:
                weekDay = "Jueves";
                break;
            case Calendar.FRIDAY:
                weekDay = "Viernes";
                break;
            case Calendar.SATURDAY:
                weekDay = "Sábado";
                break;
        }
        textView.setText(weekDay);

        scheduleViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
        Date date = calendar.getTime();
        String pattern = "dd-MM-yyyy",
                dayStart = (new SimpleDateFormat(pattern, new Locale("es", "ES"))).format(date) + " 00:00:00",
                dayEnd = (new SimpleDateFormat(pattern, new Locale("es", "ES"))).format(date) + " 23:59:59";

        schedules = MainActivity.db.scheduleDao().findAllByDate(dayStart, dayEnd);

        ScheduleAdapter scheduleAdapter =
                new ScheduleAdapter(this.schedules, view.getContext());
        scheduleRecyclerView.setHasFixedSize(true);
        scheduleRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        scheduleRecyclerView.setAdapter(scheduleAdapter);
    }
}