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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ScheduleViewModel scheduleViewModel =
                new ViewModelProvider(this).get(ScheduleViewModel.class);

        binding = FragmentScheduleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSchedule;

        scheduleRecyclerView = root.findViewById(R.id.scheduleRecyclerView);

        // Obtener día de la semana actual
        String weekDay = "";
        switch (Calendar.DAY_OF_WEEK){
            case 1: weekDay = "Domingo";
                break;
            case 2: weekDay = "Lunes";
                break;
            case 3: weekDay = "Martes";
                break;
            case 4: weekDay = "Miercoles";
                break;
            case 5: weekDay = "Jueves";
                break;
            case 6: weekDay = "Viernes";
                break;
            case 7: weekDay = "Sábado";
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
                new ScheduleAdapter(this.schedules, root.getContext());
        scheduleRecyclerView.setHasFixedSize(true);
        scheduleRecyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        scheduleRecyclerView.setAdapter(scheduleAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}