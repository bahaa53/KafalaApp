package com.rocca.umrah.kafala.reponse.notifications;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.io.Serializable;

public class DataDTO implements Serializable {

    @SerializedName("Info")
    private List<InfoDTO> info;

    public void setInfo(List<InfoDTO> info) {
        this.info = info;
    }

    public List<InfoDTO> getInfo() {
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