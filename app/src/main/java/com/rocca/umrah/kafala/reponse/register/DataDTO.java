package com.rocca.umrah.kafala.reponse.register;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class DataDTO implements Serializable {

    @SerializedName("Info")
    private InfoDTO info;

    public void setInfo(InfoDTO info) {
        this.info = info;
    }

    public InfoDTO getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return
                "DataDTO{" +
                        "info = '" + info + '\'' +
                        "}";
    }
}