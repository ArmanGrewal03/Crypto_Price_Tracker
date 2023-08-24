package com.example.cryptopricetracker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ADAPTER extends RecyclerView.Adapter<ADAPTER.ViewHolder> {
    private ArrayList<Model>filteredList;
    private Context context;

    private SQLiteDatabase db;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    public void addModel(Model model) {
        filteredList.add(model);
        notifyDataSetChanged();
    }

    public interface OnToggleClickListener {//Creates an interface called OnToggleClickListner with a single method
        void onToggleClick(int position, boolean isChecked);
    }
    private OnToggleClickListener onToggleClickListener;//Has an instance of the interface

    public void setOnToggleClickListener(OnToggleClickListener listener) {//Setter method for the interface
        this.onToggleClickListener = listener;
    }


    public ADAPTER(ArrayList<Model> models, Context context,SQLiteDatabase db) {
        filteredList = models;
        this.context = context;
        this.db = db;
    }

    public void setFilter(ArrayList<Model> x){
        filteredList=x;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ADAPTER.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ADAPTER.ViewHolder holder, int position) {
        Model positionModel = filteredList.get(position);
        holder.SymbolTV.setText(positionModel.getSymbol());
        holder.NameTV.setText(positionModel.getName());
        holder.RateTV.setText("$"+df2.format(positionModel.getRate()));

        holder.toggleButton.setChecked(isSymbolInTable(positionModel.getSymbol()));//THIS CHECKS if in watchlist and if true, then toggles button.



        // Set the click listener for the toggle button
        holder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//WHEN the toggle is changed,
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    // Call the interface method to handle the toggle button click
                    if (onToggleClickListener != null) {
                        onToggleClickListener.onToggleClick(adapterPosition, isChecked);
                        Toast.makeText(buttonView.getContext(), "Restart to Apply Changes", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public boolean isSymbolInTable(String symbol){
        String[] columns = {"symbol"};
        String selection = "symbol = ?";
        String[] selectionArgs = {symbol};

        Cursor cursor = db.query("watchList", columns, selection, selectionArgs, null, null, null);

        boolean symbolExists = cursor != null && cursor.moveToFirst();
        cursor.close();

        return symbolExists;

    }


    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView SymbolTV;
        TextView NameTV;
        TextView RateTV;
        ToggleButton toggleButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            SymbolTV=itemView.findViewById(R.id.TVSymbol);
            NameTV=itemView.findViewById(R.id.idTVName);
            RateTV=itemView.findViewById(R.id.idCurrencyRate);
            toggleButton = itemView.findViewById(R.id.toggleButton);

            toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//When the toggle is toggled, the inwathlist model variable is changed.
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Model model = filteredList.get(position);
                        model.setInWatchlist(isChecked);
                    }
                }
            });
        }
        }
    }

