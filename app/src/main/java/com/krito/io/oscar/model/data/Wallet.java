package com.krito.io.oscar.model.data;

/**
 * Created by Mona Abdallh on 3/19/2018.
 */

public class Wallet {

    private int amount;
    private long timestampOfTransaction;


    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getTimestampOfTransaction() {
        return timestampOfTransaction;
    }

    public void setTimestampOfTransaction(long timestampOfTransaction) {
        this.timestampOfTransaction = timestampOfTransaction;
    }
}
