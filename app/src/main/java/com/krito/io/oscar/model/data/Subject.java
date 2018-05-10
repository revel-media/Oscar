package com.krito.io.oscar.model.data;

/**
 * Created by Mona Abdallh on 3/19/2018.
 */

public class Subject {

    private int diverId;
    private int userId;
    private String comment;

    public int getDiverId() {
        return diverId;
    }

    public void setDiverId(int diverId) {
        this.diverId = diverId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
