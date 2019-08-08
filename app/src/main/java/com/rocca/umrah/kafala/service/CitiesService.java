package com.rocca.umrah.kafala.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rocca.umrah.kafala.interfaces.LoadingCitiesNotifies;
import com.rocca.umrah.kafala.reponse.CitiesResponseDTO;
import com.rocca.umrah.kafala.reponse.InfoDTO;
import com.rocca.umrah.kafala.utils.ConfigurationFile;
import com.rocca.umrah.kafala.webservice.ApiClient;
import com.rocca.umrah.kafala.webservice.EndPointInterfaces;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.lang.reflect.Type;
import java.util.List;

public class CitiesService {

    private SharedPreferences appSharedPrefs;

    public CitiesService(Context context) {
        appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
    }

    public void getCities(final LoadingCitiesNotifies loadingCitiesNotifies) {
        final EndPointInterfaces apiService =
                ApiClient.getClient().create(EndPointInterfaces.class);

        Call<CitiesResponseDTO> call = apiService.getAllCities("application/json");
        call.enqueue(new Callback<CitiesResponseDTO>() {
            @Override
            public void onResponse(Call<CitiesResponseDTO> call, Response<CitiesResponseDTO> response) {
                CitiesResponseDTO citiesResponseDTO = response.body();
                if (citiesResponseDTO.getSuccess().equals("true")) {
                    saveCitiesToSharedPreferences(citiesResponseDTO.getData().getInfo());
                    if (loadingCitiesNotifies != null) {
                        loadingCitiesNotifies.onCitiesLoaded(true);
                    }
                } else {
                    if (loadingCitiesNotifies != null) {
                        loadingCitiesNotifies.onCitiesLoaded(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<CitiesResponseDTO> call, Throwable t) {
                if (loadingCitiesNotifies != null) {
                    loadingCitiesNotifies.onCitiesLoaded(false);
                }
                Log.d("Cities Response Issues", t.getMessage());
            }
        });
    }

    private void saveCitiesToSharedPreferences(List<InfoDTO> info) {

        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(info);
        prefsEditor.putString(ConfigurationFile.Constants.CITIES_SAVED_TO_SHARED_PREF, json);
        prefsEditor.commit();
    }

    public List<InfoDTO> getSavedCities() {
        Gson gson = new Gson();
        String json = appSharedPrefs.getString(ConfigurationFile.Constants.CITIES_SAVED_TO_SHARED_PREF, "");
        Type type = new TypeToken<List<InfoDTO>>() {
        }.getType();
        List<InfoDTO> cities = gson.fromJson(json, type);
        return cities;
    }

    public String getIdOfSpecificCity(String cityName) {
        String id = "";
        for (InfoDTO city : getSavedCities()) {
            if (city.getName().equalsIgnoreCase(cityName)){
                id = city.getId();
            }
        }
        return id ;
    }

    public String getCityName(String id) {
        String name = "";
        for (InfoDTO city : getSavedCities()) {
            if (city.getId().equalsIgnoreCase(id)) {
                name = city.getName();
            }
        }
        return name;
    }

}
