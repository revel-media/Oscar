package com.krito.io.oscar.model.sqlite;

/**
 * Created by Mona Abdallh on 3/20/2018.
 */

public class UserSchema {

    public class Table{
        public static final String NAME = "customer";

        public static final String CRETAE_USER_TABLE = "CREATE TABLE " + NAME +
                "(id INTEGER auto increment, " +
                Colms.USERNAME + " TEXT NOT NULL, " +
                Colms.ADDRESS + " TEXT NOT NULL, " +
                Colms.PHONE_NUMBER + " TEXT NOT NULL, " +
                Colms.EMAIL + " TEXT NOT NULL, " +
                Colms.PASSWORD + " TEXT NOT NULL, " +
                Colms.WALLET_MONEY + " TEXT NOT NULL"
                + ")";
    }

    public class Colms{
        public static final String USERNAME = "username";
        public static final String ADDRESS = "address";
        public static final String PHONE_NUMBER = "phone_number";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String WALLET_MONEY = "wallet_money";
    }
}
