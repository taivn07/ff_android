package com.paditech.fifood.model;

public class LoginModel extends BaseModel{

    public Data data;

    public class Data {
        public String accountId;
        public String accessToken;
        public String createdAt;
    }
}
