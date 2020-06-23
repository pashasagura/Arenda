package com.example.arenda.pogo;

import java.util.List;

public class Home {
    private String type;
    private String address;
    private String area;
    private String room;
    private String flor;
    private String sq;
    private String description;
    private String price;
    private List<String> photoUrl;
    private String date;
    private String email;
    private String phoneNumber;
    private String name;
    private String ID;

    public Home() {
    }

    public Home(String type, String address, String area, String room, String flor, String sq, String description, String price, List<String> photoUrl, String date, String email, String phoneNumber, String name, String ID) {
        this.type = type;
        this.address = address;
        this.area = area;
        this.room = room;
        this.flor = flor;
        this.sq = sq;
        this.description = description;
        this.price = price;
        this.photoUrl = photoUrl;
        this.date = date;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getFlor() {
        return flor;
    }

    public void setFlor(String flor) {
        this.flor = flor;
    }

    public String getSq() {
        return sq;
    }

    public void setSq(String sq) {
        this.sq = sq;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(List<String> photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
