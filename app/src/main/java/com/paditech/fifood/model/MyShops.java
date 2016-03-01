package com.paditech.fifood.model;

import java.util.List;

/**
 * Created by PaditechPC1 on 3/1/2016.
 */
public class MyShops extends BaseModel {
    public Data response;
    public class Data{
        public List <Shops> shops;
        public int total_notify;
        public int total;

    }
}
