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
import com.rocca.umrah.kafala.adapter.CategoryAdapter;
import com.rocca.umrah.kafala.adapter.CitiesAdapter;
import com.rocca.umrah.kafala.databinding.ActivityCityLayoutBinding;

import com.rocca.umrah.kafala.reponse.categoriesresponse.InfoDTO;
import com.rocca.umrah.kafala.service.CategoriesService;
import com.rocca.umrah.kafala.service.CitiesService;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {

    private ActivityCityLayoutBinding activityCityLayoutBinding;
    private List<InfoDTO> categories;
    private List<InfoDTO> filterdList;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCityLayoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_city_layout);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        activityCityLayoutBinding.toolbar.tvToolbarTitle.setText(getResources().getString(R.string.choose_category));
        activityCityLayoutBinding.etSearchForCity.setHint("أبحث عن مهنة");
        activityCityLayoutBinding.toolbar.ivBack.setOnClickListener(v -> finish());
        categories = new ArrayList<>();
        filterdList = new ArrayList<>();
        setupRecyclerView();
        getCategoriessavedIntoSharedPref();
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
                    getCategoriessavedIntoSharedPref();
                } else {
                    filterdList.clear();
                    for (InfoDTO infoDTO : categories) {
                        if (infoDTO.getName().startsWith(filter)) {
                            System.out.println("filtered city :" + infoDTO.getName());
                            filterdList.add(infoDTO);
                        }
                    }

                    categories.clear();
                    categories.addAll(filterdList);
                    categoryAdapter.notifyDataSetChanged();
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
        categoryAdapter = new CategoryAdapter(categories, infoDTO -> {
            Intent intent = new Intent();
            intent.putExtra("Choosed_Category", infoDTO.getName());
            intent.putExtra("Choosed_Category_ID", infoDTO.getId());
            setResult(3, intent);
            finish();
        });
        activityCityLayoutBinding.rvCity.setAdapter(categoryAdapter);
    }

    private void getCategoriessavedIntoSharedPref() {
        final CategoriesService categoriesService = new CategoriesService(getApplicationContext());
        if (categoriesService.getSavedCategories() != null && !categoriesService.getSavedCategories().equals("")) {
            updateRecyclerViewAdapter(categoriesService);
        } else {
            categoriesService.getCategories(isSuccess -> updateRecyclerViewAdapter(categoriesService));
        }
    }

    private void updateRecyclerViewAdapter(CategoriesService categoriesService) {
        List<InfoDTO> savedCategories = categoriesService.getSavedCategories();
        categories.clear();
        categories.addAll(savedCategories);
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}