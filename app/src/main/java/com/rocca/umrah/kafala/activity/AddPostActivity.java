package com.rocca.umrah.kafala.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.google.android.material.snackbar.Snackbar;
import com.rocca.umrah.kafala.R;
import com.rocca.umrah.kafala.databinding.ActivityAddPostLayoutBinding;
import com.rocca.umrah.kafala.request.AddPostRequestDTO;
import com.rocca.umrah.kafala.utils.GetSavedUserData;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddPostActivity extends AppCompatActivity {

    private ActivityAddPostLayoutBinding binding;
    private String cityID, categoryID, nationalId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_post_layout);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        setSupportActionBar(binding.toolbar.toolbar);
        binding.toolbar.tvToolbarTitle.setText(getResources().getString(R.string.search_for_kafeel));
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
        if ((TextUtils.isEmpty(cityID) ||
                (TextUtils.isEmpty(categoryID) ||
                        (TextUtils.isEmpty(nationalId) ||
                                (TextUtils.isEmpty(binding.etPhoneNumber.getText().toString().trim())) ||
                                TextUtils.isEmpty(binding.etAdditionalInfo.getText().toString().trim()))))) {
            Snackbar.make(binding.container, getString(R.string.enter_all_data), Snackbar.LENGTH_LONG).show();
        } else {
            getAddPostRequest();
            Intent intent = new Intent(this, TermsAndConditionsActivity.class);
            intent.putExtra("ADD_POST_OBJ", getAddPostRequest());
            startActivity(intent);
        }
    }

    private AddPostRequestDTO getAddPostRequest() {
        AddPostRequestDTO addPostRequestDTO = new AddPostRequestDTO();
        GetSavedUserData getSavedUserData = new GetSavedUserData(getApplicationContext());
        addPostRequestDTO.setCity(Integer.parseInt(cityID));
        addPostRequestDTO.setCategory(Integer.parseInt(categoryID));
        addPostRequestDTO.setNationality(Integer.parseInt(nationalId));
        addPostRequestDTO.setContact(binding.etPhoneNumber.getText().toString().trim());
        addPostRequestDTO.setInfo(binding.etAdditionalInfo.getText().toString().trim());
        addPostRequestDTO.setUserId(Integer.parseInt(getSavedUserData.getSavedUserData().getId()));
        return addPostRequestDTO;
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
