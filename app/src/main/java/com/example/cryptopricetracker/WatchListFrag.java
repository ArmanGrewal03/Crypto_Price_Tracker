package com.example.cryptopricetracker;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class WatchListFrag extends Fragment {

    private ArrayList<Model> WatchListModels;
    com.example.cryptopricetracker.adapter2 adapter2;
    private DatabaseHelper databaseHelper;

    public void refreshAdapter(){
        adapter2.notifyDataSetChanged();
    }
    public WatchListFrag() {
        // Empty constructor is required for fragments.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_watch_list, container, false);

        databaseHelper = new DatabaseHelper(requireContext());


        RecyclerView RV = rootView.findViewById(R.id.recyclerViewWatcher);
        WatchListModels = new ArrayList<>();
        adapter2 = new adapter2(WatchListModels, requireContext(),databaseHelper.getReadableDatabase());
        RV.setLayoutManager(new LinearLayoutManager(requireContext()));
        RV.setAdapter(adapter2);




        setUPRV2();

        EditText SearchBar = getActivity().findViewById(R.id.idSearchBar);
        SearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
        return rootView;
    }

    public void filter(String search){
        ArrayList<Model>filteredList = new ArrayList<>();
        for(Model model:WatchListModels){
            if(model.getName().toLowerCase().contains(search.toLowerCase())||model.getSymbol().toLowerCase().contains(search.toLowerCase())){
                filteredList.add(model);
            }
            adapter2.setFilter(filteredList);
        }
    }
    public void setUPRV2() {
        // Retrieve data from the database using the existing db instance
        List<Model> dataList = getAllData();
        WatchListModels.addAll(dataList);

        // Notify the adapter that data has changed
        adapter2.notifyDataSetChanged();
    }

    public List<Model> getAllData() {
        List<Model> dataList = new ArrayList<>();

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] columns = {"symbol", "name", "rate"};
        Cursor cursor = db.query("watchList", columns, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String symbol = cursor.getString(cursor.getColumnIndex("symbol"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") double rate = cursor.getDouble(cursor.getColumnIndex("rate"));

                Model model = new Model(name, symbol, rate,true);
                dataList.add(model);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return dataList;
    }
}