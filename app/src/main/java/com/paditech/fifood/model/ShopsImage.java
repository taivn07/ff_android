package com.paditech.fifood.model;

import java.util.List;

/**
 * Created by PaditechPC1 on 2/25/2016.
 */
public class ShopsImage extends BaseModel {
    public StoreImage response;
    public class StoreImage{
        private int count;
        public List<Images> images;


    }
}
