package com.rocca.umrah.kafala.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rocca.umrah.kafala.interfaces.LoadingCategoriesNotifies;
import com.rocca.umrah.kafala.interfaces.LoadingCitiesNotifies;
import com.rocca.umrah.kafala.reponse.categoriesresponse.CategoriesResponseDTO;

import com.rocca.umrah.kafala.reponse.categoriesresponse.InfoDTO;
import com.rocca.umrah.kafala.utils.ConfigurationFile;
import com.rocca.umrah.kafala.webservice.ApiClient;
import com.rocca.umrah.kafala.webservice.EndPointInterfaces;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.lang.reflect.Type;
import java.util.List;

public class CategoriesService {

    private SharedPreferences appSharedPrefs;

    public CategoriesService(Context context) {
        appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
    }

    public void getCategories(final LoadingCategoriesNotifies loadingCategoriesNotifies) {
        final EndPointInterfaces apiService =
                ApiClient.getClient().create(EndPointInterfaces.class);

        Call<CategoriesResponseDTO> call = apiService.getAllCategories("application/json");
        call.enqueue(new Callback<CategoriesResponseDTO>() {
            @Override
            public void onResponse(Call<CategoriesResponseDTO> call, Response<CategoriesResponseDTO> response) {
                CategoriesResponseDTO categoriesResponseDTO = response.body();
                if (categoriesResponseDTO.getSuccess().equals("true")) {
                    saveCategoriesToSharedPref(categoriesResponseDTO.getData().getInfo());
                    if (loadingCategoriesNotifies != null) {
                        loadingCategoriesNotifies.onCategoryLoaded(true);
                    }
                } else {
                    if (loadingCategoriesNotifies != null) {
                        loadingCategoriesNotifies.onCategoryLoaded(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<CategoriesResponseDTO> call, Throwable t) {
                if (loadingCategoriesNotifies != null) {
                    loadingCategoriesNotifies.onCategoryLoaded(false);
                }
                Log.d("Cities Response Issues", t.getMessage());
            }
        });
    }

    private void saveCategoriesToSharedPref(List<InfoDTO> info) {

        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(info);
        prefsEditor.putString(ConfigurationFile.Constants.CATEGORIES_SAVED_TO_SHARED_PREF, json);
        prefsEditor.commit();
    }

    public List<InfoDTO> getSavedCategories() {
        Gson gson = new Gson();
        String json = appSharedPrefs.getString(ConfigurationFile.Constants.CATEGORIES_SAVED_TO_SHARED_PREF, "");
        Type type = new TypeToken<List<InfoDTO>>() {
        }.getType();
        List<InfoDTO> categories = gson.fromJson(json, type);
        return categories;
    }

    public String getIdOfSpecificCategory(String categortyName) {
        String id = "";
        for (InfoDTO city : getSavedCategories()) {
            if (city.getName().equalsIgnoreCase(categortyName)){
                id = city.getId();
            }
        }
        return id ;
    }
}
