package com.crown.passwordmanager;

public class DataModel {
    String NO;
    String title;
    String date;
    String userName;
    String password;

    public DataModel(String title, String date, String userName, String password,String No) {
        this.title = title;
        this.date = date;
        this.userName = userName;
        this.password = password;
        this.NO=No;
    }

    public String getNO() { return NO; }

    public void setNO(String NO) {
        this.NO = NO;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
