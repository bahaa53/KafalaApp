package com.rocca.umrah.kafala.activity;

import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.rocca.umrah.kafala.R;
import com.rocca.umrah.kafala.databinding.ActivitySplashLayoutBinding;
import com.rocca.umrah.kafala.service.CategoriesService;
import com.rocca.umrah.kafala.service.CitiesService;
import com.rocca.umrah.kafala.service.NationalitiesService;
import com.rocca.umrah.kafala.utils.GetSavedUserData;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashLayoutBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_layout);
        getSupportActionBar().hide();
        ////////////////////////////////////////////////////////////////////////////////
        CitiesService citiesService = new CitiesService(getApplicationContext());
        citiesService.getCities(null);
        //////////////////////////////////////////////////////////////////////////////////
        NationalitiesService nationalitiesService = new NationalitiesService(getApplicationContext());
        nationalitiesService.getNationalities(null);
        //////////////////////////////////////////////////////////////////////////////////
        CategoriesService categoriesService = new CategoriesService(getApplicationContext());
        categoriesService.getCategories(null);
        ///////////////////////////////////////////////////////////////////////////////////////
        delay();
    }

    private void delay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startNextActivity();
            }
        }, 5000);
    }

    private void startNextActivity() {
        GetSavedUserData getSavedUserData = new GetSavedUserData(getApplicationContext());
        if (getSavedUserData.getSavedUserData() == null) {
            generateIntent(MainActivity.class);
        } else {
            generateIntent(MainActivity.class);
        }
    }

    private void generateIntent(Class act) {
        Intent startRegisterActivityIntent = new Intent(this, act);
        startActivity(startRegisterActivityIntent);
        finish();
    }
}
