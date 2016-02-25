package com.paditech.fifood.model;

import java.util.List;

/**
 * Created by PaditechPC1 on 2/25/2016.
 */
public class ShopsDetail extends BaseModel {
    public Store response;
    public class Store{
        private int id;
        public String name;
        public String address;
        public String lat;
        public String longth;
        public String distance;
        public Float rating;
        public List<Comments> comments;
        public File file;
        public int file_num;
        public int comment_num;
        public String evaluation;
        public int like_num;
        public int dislike_num;

    }
}
