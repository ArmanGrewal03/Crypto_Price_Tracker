package com.example.cryptopricetracker;

public class Model {
    String Symbol;
    String Name;
    Double Rate;

    private boolean inWatchlist;



    public Model(String symbol, String name, Double rate, boolean inWatchlist) {
        Symbol = symbol;
        Name = name;
        Rate = rate;
        this.inWatchlist = inWatchlist;
    }

    public String getSymbol() {
        return Symbol;
    }

    public void setSymbol(String symbol) {
        Symbol = symbol;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Double getRate() {
        return Rate;
    }

    public void setRate(Double rate) {
        Rate = rate;
    }

    public boolean isInWatchlist() {
        return inWatchlist;
    }

    public void setInWatchlist(boolean inWatchlist) {
        this.inWatchlist = inWatchlist;
    }


}
