package com.example.clockingapp.ui.clocking;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ClockingViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ClockingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("¡Hola de nuevo! No te olvides de fichar tus entradas y salidas");
    }

    public LiveData<String> getText() {
        return mText;
    }
}