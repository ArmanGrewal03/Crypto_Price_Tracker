package com.example.cryptopricetracker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class adapter2 extends RecyclerView.Adapter<adapter2.ViewHolder>{

    private ArrayList<Model>filteredList;
    private Context context;

    private SQLiteDatabase db;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    public void addModel(Model model) {
        filteredList.add(model);
        notifyDataSetChanged();
    }



    public adapter2(ArrayList<Model> models, Context context,SQLiteDatabase db) {
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
    public adapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.watchlistrow,parent,false);
        return new adapter2.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter2.ViewHolder holder, int position) {
        Model positionModel = filteredList.get(position);
        holder.SymbolTV.setText(positionModel.getSymbol());
        holder.NameTV.setText(positionModel.getName());
        holder.RateTV.setText("$"+df2.format(positionModel.getRate()));


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
        }
    }


    }



