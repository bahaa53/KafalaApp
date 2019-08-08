package com.rocca.umrah.kafala.request;

import com.google.gson.annotations.SerializedName;

public class EditPostRequest {

    @SerializedName("postId")
    private int postId;


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


    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
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
}
