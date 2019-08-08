package com.rocca.umrah.kafala.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;

public class GetSavedUserData {

    private SharedPreferences appSharedPrefs;

    public GetSavedUserData(Context context) {
        appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
    }

    public com.rocca.umrah.kafala.reponse.register.InfoDTO getSavedUserData() {
        Gson gson = new Gson();
        String json = appSharedPrefs.getString(ConfigurationFile.Constants.USER_SAVED_TO_SHARED_PREF, "");
        if (!json.equals("")) {
            com.rocca.umrah.kafala.reponse.register.InfoDTO savedUserData = gson.fromJson(json, com.rocca.umrah.kafala.reponse.register.InfoDTO.class);
            return savedUserData;
        } else {
            return null;
        }
    }

    public void clearSharedPref() {
        appSharedPrefs.edit().clear().commit();
    }
}
