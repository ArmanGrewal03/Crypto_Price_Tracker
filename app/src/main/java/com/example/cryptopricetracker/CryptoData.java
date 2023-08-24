package com.example.cryptopricetracker;

public class CryptoData {
        private String name;
        private String symbol;
        private Quote quote;


        public CryptoData(String name, String symbol, double price, Quote quote, boolean inWatchlist) {
            this.name = name;
            this.symbol = symbol;
            this.quote = quote;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public Quote getQuote() {
            return quote;
        }

        public void setQuote(Quote quote) {
            this.quote = quote;
        }

        public static class Quote {
            private USD USD;

            public Quote(com.example.cryptopricetracker.CryptoData.USD USD) {
                this.USD = USD;
            }

            public com.example.cryptopricetracker.CryptoData.USD getUSD() {
                return USD;
            }

            public void setUSD(com.example.cryptopricetracker.CryptoData.USD USD) {
                this.USD = USD;
            }
        }

        public static class USD {
            private double price;

            public USD(double price) {
                this.price = price;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }
        }
    }

