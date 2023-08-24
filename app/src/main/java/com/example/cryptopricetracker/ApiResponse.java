package com.example.cryptopricetracker;

import java.util.List;

public class  ApiResponse {
        private List<CryptoData> data;

        public List<CryptoData> getData() {
            return data;
        }

        public void setData(List<CryptoData> data) {
            this.data = data;
        }
    }

