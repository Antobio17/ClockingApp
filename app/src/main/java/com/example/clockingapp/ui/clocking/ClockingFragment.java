package com.example.clockingapp.ui.clocking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.clockingapp.databinding.FragmentClockingBinding;

public class ClockingFragment extends Fragment {

    private FragmentClockingBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ClockingViewModel clockingViewModel =
                new ViewModelProvider(this).get(ClockingViewModel.class);

        binding = FragmentClockingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textClocking;
        textView.setText("¡Registrar tus fichajes!");
        clockingViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}