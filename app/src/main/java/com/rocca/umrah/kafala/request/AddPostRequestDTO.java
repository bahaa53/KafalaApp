package com.rocca.umrah.kafala.request;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class AddPostRequestDTO implements Serializable {

    @SerializedName("userId")
    private int userId;

    @SerializedName("category")
    private int category;

    @SerializedName("nationality")
    private int nationality;

    @SerializedName("city")
    private int city;

    @SerializedName("contact")
    private String contact;

    @SerializedName("info")
    private String info;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getCategory() {
        return category;
    }

    public void setNationality(int nationality) {
        this.nationality = nationality;
    }

    public int getNationality() {
        return nationality;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getCity() {
        return city;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContact() {
        return contact;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return
                "AddPostRequestDTO{" +
                        "userId = '" + userId + '\'' +
                        ",category = '" + category + '\'' +
                        ",nationality = '" + nationality + '\'' +
                        ",city = '" + city + '\'' +
                        ",contact = '" + contact + '\'' +
                        ",info = '" + info + '\'' +
                        "}";
    }
}