package com.example.clockingapp.ui.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.clockingapp.databinding.FragmentScheduleBinding;

import java.util.Calendar;

public class ScheduleFragment extends Fragment {

    private FragmentScheduleBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ScheduleViewModel scheduleViewModel =
                new ViewModelProvider(this).get(ScheduleViewModel.class);

        binding = FragmentScheduleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSchedule;

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
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}