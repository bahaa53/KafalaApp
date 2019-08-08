package com.rocca.umrah.kafala.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.gson.Gson;
import com.rocca.umrah.kafala.R;
import com.rocca.umrah.kafala.adapter.CitiesAdapter;
import com.rocca.umrah.kafala.databinding.ActivityCityLayoutBinding;
import com.rocca.umrah.kafala.reponse.InfoDTO;
import com.rocca.umrah.kafala.service.CitiesService;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import java.util.ArrayList;
import java.util.List;

public class CityActivity extends AppCompatActivity {

    private ActivityCityLayoutBinding activityCityLayoutBinding;
    private List<InfoDTO> cities;
    private List<InfoDTO> filterdList;
    private CitiesAdapter citiesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCityLayoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_city_layout);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        activityCityLayoutBinding.toolbar.tvToolbarTitle.setText(getResources().getString(R.string.choose_city));
        activityCityLayoutBinding.toolbar.ivBack.setOnClickListener(v -> finish());
        cities = new ArrayList<>();
        filterdList = new ArrayList<>();
        setupRecyclerView();
        getCitiessavedIntoSharedPref();
        filterList();
    }

    private void filterList() {
        activityCityLayoutBinding.etSearchForCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String filter = s.toString();
                if (filter.isEmpty()) {
                    getCitiessavedIntoSharedPref();
                } else {
                    filterdList.clear();
                    for (InfoDTO infoDTO : cities) {
                        if (infoDTO.getName().startsWith(filter)) {
                            filterdList.add(infoDTO);
                        }
                    }

                    cities.clear();
                    cities.addAll(filterdList);
                    citiesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        activityCityLayoutBinding.rvCity.setLayoutManager(linearLayoutManager);
        citiesAdapter = new CitiesAdapter(cities, infoDTO -> {
            Intent intent = new Intent();
            intent.putExtra("Choosed_City", infoDTO.getName());
            intent.putExtra("Choosed_City_ID", infoDTO.getId());
            setResult(2, intent);
            finish();
        });
        activityCityLayoutBinding.rvCity.setAdapter(citiesAdapter);
    }

    private void getCitiessavedIntoSharedPref() {
        final CitiesService citiesService = new CitiesService(getApplicationContext());
        if (citiesService.getSavedCities() != null && !citiesService.getSavedCities().equals("")) {
            updateRecyclerViewAdapter(citiesService);
        } else {
            citiesService.getCities(isSuccess -> updateRecyclerViewAdapter(citiesService));
        }
    }

    private void updateRecyclerViewAdapter(CitiesService citiesService) {
        List<InfoDTO> savedCities = citiesService.getSavedCities();
        cities.clear();
        cities.addAll(savedCities);
        citiesAdapter.notifyDataSetChanged();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
