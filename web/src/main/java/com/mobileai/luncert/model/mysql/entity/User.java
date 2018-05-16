package com.mobileai.luncert.model.mysql.entity;


public class User {

    private int id;
    private String account;
    private String password;
    private boolean isActive;
    private boolean isAdmin;

    public void setId(int id) { this.id = id; }

    public int getId() { return id; }

    public void setAccount(String account) { this.account = account; }

    public String getAccount() { return account; }

    public void setPassword(String password) { this.password = password; }

    public String getPassword() { return password; }

    public void setIsActive(boolean isActive) { this.isActive = isActive; }

    public boolean getIsActive() { return isActive; }

    public void setIsAdmin(boolean isAdmin) { this.isAdmin = isAdmin; }

    public boolean getIsAdmin() { return isAdmin; }

    //

    public boolean isActive() { return isActive; }

    public boolean isAdmin() { return isAdmin; }

}