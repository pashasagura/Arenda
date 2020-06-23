package com.example.arenda.pogo;

import java.util.List;

public class UserProfile {
    private String name;
    private String secondName;
    private String phoneNumber;
    private String email;
    private List<String> favoritesHome;
    private List<String> myHome;

    public UserProfile() {
    }

    public UserProfile(String name, String secondName, String phoneNumber, String email, List<String> favoritesHome, List<String> myHome) {
        this.name = name;
        this.secondName = secondName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.favoritesHome = favoritesHome;
        this.myHome = myHome;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getFavoritesHome() {
        return favoritesHome;
    }

    public void setFavoritesHome(List<String> favoritesHome) {
        this.favoritesHome = favoritesHome;
    }

    public List<String> getMyHome() {
        return myHome;
    }

    public void setMyHome(List<String> myHome) {
        this.myHome = myHome;
    }
}
