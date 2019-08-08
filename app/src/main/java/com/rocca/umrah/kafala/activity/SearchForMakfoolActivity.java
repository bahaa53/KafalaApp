package com.rocca.umrah.kafala.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.rocca.umrah.kafala.R;
import com.rocca.umrah.kafala.databinding.ActivityAddPostLayoutBinding;
import com.rocca.umrah.kafala.databinding.ActivitySearchForMakfoolLayoutBinding;
import com.rocca.umrah.kafala.request.AddPostRequestDTO;
import com.rocca.umrah.kafala.utils.ConfigurationFile;
import com.rocca.umrah.kafala.utils.GetSavedUserData;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SearchForMakfoolActivity extends AppCompatActivity {
    private ActivitySearchForMakfoolLayoutBinding binding;
    private String cityID, categoryID, nationalId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_for_makfool_layout);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        setSupportActionBar(binding.toolbar.toolbar);
        binding.toolbar.tvToolbarTitle.setText("البحث عن مكفول");
        setActions();
    }

    private void setActions() {
        binding.toolbar.ivBack.setOnClickListener(v -> {
            finish();
        });

        binding.cityContainer.setOnClickListener(v -> {
            Intent intent = new Intent(this, CityActivity.class);
            startActivityForResult(intent, 2);
        });

        binding.nationalityContainer.setOnClickListener(v -> {
            Intent intent = new Intent(this, NationalitiesActivity.class);
            startActivityForResult(intent, 4);
        });

        binding.categoryContainer.setOnClickListener(v -> {
            Intent intent = new Intent(this, CategoriesActivity.class);
            startActivityForResult(intent, 3);
        });
        binding.btnSaveChanges.setOnClickListener(v -> {
            validate();
        });
    }

    private void validate() {
        if (!isTermsSearchForMakfoolDisplayedBefore()) {

            Intent intent = new Intent(this, TermsAndConditionsActivity.class);
            intent.putExtra("Choosed_City_ID", cityID);
            intent.putExtra("Choosed_Nationality_ID", nationalId);
            intent.putExtra("Choosed_Category_ID", categoryID);
            intent.putExtra("IS_MAKFOOL_DATA", true);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, SearchResultsActivity.class);
            intent.putExtra("Choosed_City_ID", cityID);
            intent.putExtra("Choosed_Nationality_ID", nationalId);
            intent.putExtra("Choosed_Category_ID", categoryID);
            startActivity(intent);
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (data != null) {
                String choosedCity = data.getStringExtra("Choosed_City");
                binding.tvChosedCity.setText(choosedCity);
                cityID = data.getStringExtra("Choosed_City_ID");
            }
        } else if (requestCode == 3) {
            if (data != null) {
                String choosed_category = data.getStringExtra("Choosed_Category");
                binding.tvChoosedCategory.setText(choosed_category);
                categoryID = data.getStringExtra("Choosed_Category_ID");
            }
        } else if (requestCode == 4) {
            if (data != null) {
                String choosednationality = data.getStringExtra("Choosed_Nationality");
                binding.tvChoosedNationality.setText(choosednationality);
                nationalId = data.getStringExtra("Choosed_Nationality_ID");
            }
        }
    }

    public boolean isTermsSearchForMakfoolDisplayedBefore() {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean json = appSharedPrefs.getBoolean(ConfigurationFile.Constants.SEARCH_FOR_MAKFOOL_TERMS, false);
        return json;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}