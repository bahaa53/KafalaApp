package com.rocca.umrah.kafala.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rocca.umrah.kafala.interfaces.LoadingCitiesNotifies;

import com.rocca.umrah.kafala.interfaces.LoadingNationalitiesNotifies;
import com.rocca.umrah.kafala.reponse.nationalitiesResponse.InfoDTO;
import com.rocca.umrah.kafala.reponse.nationalitiesResponse.NationalitiesResponseDTO;
import com.rocca.umrah.kafala.utils.ConfigurationFile;
import com.rocca.umrah.kafala.webservice.ApiClient;
import com.rocca.umrah.kafala.webservice.EndPointInterfaces;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.lang.reflect.Type;
import java.util.List;

public class NationalitiesService {

    private SharedPreferences appSharedPrefs;

    public NationalitiesService(Context context) {
        appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
    }

    public void getNationalities(final LoadingNationalitiesNotifies loadingNationalitiesNotifies) {
        final EndPointInterfaces apiService =
                ApiClient.getClient().create(EndPointInterfaces.class);

        Call<NationalitiesResponseDTO> call = apiService.getAllNationalities("application/json");
        call.enqueue(new Callback<NationalitiesResponseDTO>() {
            @Override
            public void onResponse(Call<NationalitiesResponseDTO> call, Response<NationalitiesResponseDTO> response) {
                NationalitiesResponseDTO nationalitiesResponseDTO = response.body();
                if (nationalitiesResponseDTO.getSuccess().equals("true")) {
                    saveNationalitesToSharedPref(nationalitiesResponseDTO.getData().getInfo());
                    if (loadingNationalitiesNotifies != null) {
                        loadingNationalitiesNotifies.onNationalityLoaded(true);
                    }
                } else {
                    if (loadingNationalitiesNotifies != null) {
                        loadingNationalitiesNotifies.onNationalityLoaded(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<NationalitiesResponseDTO> call, Throwable t) {
                if (loadingNationalitiesNotifies != null) {
                    loadingNationalitiesNotifies.onNationalityLoaded(false);
                }
                Log.d("Cities Response Issues", t.getMessage());
            }
        });
    }

    private void saveNationalitesToSharedPref(List<InfoDTO> info) {

        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(info);
        prefsEditor.putString(ConfigurationFile.Constants.NATIONALITES_SAVED_TO_SHARED_PREF, json);
        prefsEditor.commit();
    }

    public List<InfoDTO> getSavedNationalities() {
        Gson gson = new Gson();
        String json = appSharedPrefs.getString(ConfigurationFile.Constants.NATIONALITES_SAVED_TO_SHARED_PREF, "");
        Type type = new TypeToken<List<InfoDTO>>() {
        }.getType();
        List<InfoDTO> nationalities = gson.fromJson(json, type);
        return nationalities;
    }

    public String getIdOfSpecificNationality(String categortyName) {
        String id = "";
        for (InfoDTO city : getSavedNationalities()) {
            if (city.getName().equalsIgnoreCase(categortyName)) {
                id = city.getId();
            }
        }
        return id;
    }
}
