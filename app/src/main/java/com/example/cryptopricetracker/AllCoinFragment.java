package com.example.cryptopricetracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllCoinFragment extends Fragment {

    private ArrayList<Model> Models;
    private ADAPTER adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_coin, container, false);

        DatabaseHelper databaseHelper = new DatabaseHelper(requireContext());
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        RecyclerView RV = rootView.findViewById(R.id.recyclerView);
        Models = new ArrayList<>();
        adapter = new ADAPTER(Models, requireContext(),db);
        RV.setLayoutManager(new LinearLayoutManager(requireContext()));
        RV.setAdapter(adapter);


        adapter.setOnToggleClickListener(new ADAPTER.OnToggleClickListener() {
            @Override
            public void onToggleClick(int position, boolean isChecked) {
                String tableName = "watchList";
                String whereClause = "symbol = ?";
                Model model = Models.get(position);
                model.setInWatchlist(isChecked);



                if(isChecked==true){
                    ContentValues values = new ContentValues();
                    values.put("symbol",model.getSymbol());
                    values.put("name",model.getName());
                    values.put("rate",model.getRate());
                    db.insert("watchList",null,values);

                }

                else{
                    String[] whereArgs = new String[]{model.getSymbol()};
                    db.delete(tableName, whereClause, whereArgs);
                }
                WatchListFrag watchListFragment = (WatchListFrag) getParentFragmentManager().findFragmentByTag("watchlist_fragment_tag");
                if (watchListFragment != null) {
                    watchListFragment.adapter2.notifyDataSetChanged();
                }
            }
        });
        setUPRV();




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

    interface RefreshInterface{
        public void refreshAdapterFragmentB();
    }
    public void filter(String search){
        ArrayList<Model>filteredList = new ArrayList<>();
        for(Model model:Models){
            if(model.getName().toLowerCase().contains(search.toLowerCase())||model.getSymbol().toLowerCase().contains(search.toLowerCase())){
                filteredList.add(model);
            }
            adapter.setFilter(filteredList);
        }
    }

    private void setUPRV() {
        CryptoService cryptoService = RetroFitClient.getRetrofitInstance().create(CryptoService.class);
        Call<ApiResponse> call = cryptoService.getCryptoData();

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    List<CryptoData> cryptoDataList = apiResponse.getData();

                    for (CryptoData cryptoData : cryptoDataList) {
                        // Extract the price from the nested USD object within the quote object
                        double price = cryptoData.getQuote().getUSD().getPrice();

                        // Create a new Model instance with the required data
                        Model model = new Model(cryptoData.getName(), cryptoData.getSymbol(), price,false);
                        Models.add(model);
                    }


                    // Notify the adapter that data has changed
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(requireContext(), "Failed To Get Data!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("API_CALL_ERROR", "Failed to fetch cryptocurrency data", t);
                Toast.makeText(requireContext(), "Failed To Get Data!!!", Toast.LENGTH_SHORT).show();
            }
        });


    }

}


