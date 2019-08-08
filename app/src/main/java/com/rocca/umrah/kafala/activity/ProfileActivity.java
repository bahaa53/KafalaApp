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
import com.google.gson.JsonElement;
import com.rocca.umrah.kafala.R;
import com.rocca.umrah.kafala.databinding.ActivityLoginLayoutBinding;
import com.rocca.umrah.kafala.databinding.ActivityProfileLayoutBinding;
import com.rocca.umrah.kafala.reponse.edituserprofile.EditUserProfileRequestDTO;
import com.rocca.umrah.kafala.reponse.register.InfoDTO;
import com.rocca.umrah.kafala.reponse.register.RegisterResponseDTO;
import com.rocca.umrah.kafala.request.LoginRequestDTO;
import com.rocca.umrah.kafala.service.CitiesService;
import com.rocca.umrah.kafala.utils.ConfigurationFile;
import com.rocca.umrah.kafala.utils.GetSavedUserData;
import com.rocca.umrah.kafala.utils.NetWorkConnection;
import com.rocca.umrah.kafala.webservice.ApiClient;
import com.rocca.umrah.kafala.webservice.EndPointInterfaces;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileLayoutBinding binding;
    private String userId;
    private InfoDTO userdata;
    private String choosedCityId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_layout);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        setSupportActionBar(binding.toolbar.toolbar);
        binding.toolbar.tvToolbarTitle.setText(getResources().getString(R.string.personal_info_title));
        setActions();
        setUserDate();
    }

    private void setUserDate() {
        GetSavedUserData getSavedUserData = new GetSavedUserData(getApplicationContext());
        CitiesService citiesService = new CitiesService(getApplicationContext());
        userdata = getSavedUserData.getSavedUserData();
        binding.etUserName.setText(userdata.getName());
        binding.etPhoneNumber.setText(userdata.getPhone());
        binding.tvChosedCity.setText(citiesService.getCityName(userdata.getCity()));
        choosedCityId = userdata.getCity();
        userId = userdata.getId();
        System.out.println("user password :" + userdata.getPassword());
    }

    private void setActions() {
        binding.toolbar.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToMainAct();
            }
        });

        binding.cityContainer.setOnClickListener(v -> {
            Intent intent = new Intent(this, CityActivity.class);
            startActivityForResult(intent, 2);
        });

        binding.btnSaveChanges.setOnClickListener(v -> {
            validate();
        });

        binding.changePasswordContainer.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChangePasswordActivity.class);
            startActivity(intent);
        });

        binding.logoutContainer.setOnClickListener(v -> {

            boolean terms = isTermsSearchForMakfoolDisplayedBefore();
            GetSavedUserData getSavedUserData = new GetSavedUserData(getApplicationContext());
            getSavedUserData.clearSharedPref();
            updateSharedPref(terms);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finishAffinity();

        });

        binding.myAdvContainer.setOnClickListener(v -> {
            Intent intent = new Intent(this, MyAdvActivity.class);
            startActivity(intent);
        });

    }

    private void updateSharedPref(boolean terms) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.putBoolean(ConfigurationFile.Constants.SEARCH_FOR_MAKFOOL_TERMS, terms);
        prefsEditor.commit();
    }

    public boolean isTermsSearchForMakfoolDisplayedBefore() {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean json = appSharedPrefs.getBoolean(ConfigurationFile.Constants.SEARCH_FOR_MAKFOOL_TERMS, false);
        return json;
    }

    private void validate() {

        if ((TextUtils.isEmpty(binding.etUserName.getText().toString().trim())) ||
                (TextUtils.isEmpty(binding.etPhoneNumber.getText().toString().trim())) ||
                (TextUtils.isEmpty(binding.tvChosedCity.getText().toString().trim()))) {
            Snackbar.make(binding.container, getString(R.string.enter_all_data), Snackbar.LENGTH_LONG).show();
        } else {
            if (NetWorkConnection.isConnectingToInternet(getApplicationContext())) {
                editProfileDate();
            } else {
                Snackbar.make(binding.container, "من فضلك تأكد من الاتصال بالانترنت", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private void editProfileDate() {
        binding.avi.setVisibility(View.VISIBLE);
        final EndPointInterfaces apiService =
                ApiClient.getClient().create(EndPointInterfaces.class);
        EditUserProfileRequestDTO editUserProfileRequestDTO = new EditUserProfileRequestDTO();
        editUserProfileRequestDTO.setCity(choosedCityId);
        editUserProfileRequestDTO.setPhone(binding.etPhoneNumber.getText().toString().trim());
        editUserProfileRequestDTO.setName(binding.etUserName.getText().toString().trim());
        editUserProfileRequestDTO.setUserId(userId);


        Call<JsonElement> call = apiService.editUserprofile("application/json", editUserProfileRequestDTO);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                binding.avi.setVisibility(View.GONE);
                updateUi();
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                binding.avi.setVisibility(View.GONE);
                updateUi();
            }
        });
    }

    private void updateUi() {
        Snackbar.make(binding.container, "تم تعديل البيانات بنجاح", Snackbar.LENGTH_LONG).show();
        userdata.setName(binding.etUserName.getText().toString().trim());
        userdata.setPhone(binding.etPhoneNumber.getText().toString().trim());
        userdata.setCity(binding.tvChosedCity.getText().toString().trim());
        saveUserData(userdata);
    }

    private void saveUserData(InfoDTO info) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(info);
        prefsEditor.putString(ConfigurationFile.Constants.USER_SAVED_TO_SHARED_PREF, json);
        prefsEditor.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveToMainAct();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (data != null && data.getStringExtra("Choosed_City") != null) {
                String selectedCity = data.getStringExtra("Choosed_City");
                choosedCityId = data.getStringExtra("Choosed_City_ID");
                binding.tvChosedCity.setText(selectedCity);
            }
        }
    }

    private void moveToMainAct() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
