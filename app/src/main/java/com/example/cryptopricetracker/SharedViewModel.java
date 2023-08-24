package com.example.cryptopricetracker;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

    private final MutableLiveData<String> addedCoinSymbol = new MutableLiveData<>();

    public void setAddedCoinSymbol(String symbol) {
        addedCoinSymbol.setValue(symbol);
    }

    public LiveData<String> getAddedCoinSymbol() {
        return addedCoinSymbol;
    }
}
