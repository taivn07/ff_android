package com.paditech.fifood.model;

import java.util.List;

public class ListStores extends BaseModel {
    public Data response;
    public class Data{
        public List <Shops> shops;
        public int total;

    }
}
