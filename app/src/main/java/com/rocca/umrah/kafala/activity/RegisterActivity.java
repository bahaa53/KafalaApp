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
import com.rocca.umrah.kafala.databinding.ActivityRegisterLayoutBinding;
import com.rocca.umrah.kafala.reponse.register.InfoDTO;
import com.rocca.umrah.kafala.reponse.register.RegisterRequestDTO;
import com.rocca.umrah.kafala.reponse.register.RegisterResponseDTO;
import com.rocca.umrah.kafala.utils.ConfigurationFile;
import com.rocca.umrah.kafala.utils.NetWorkConnection;
import com.rocca.umrah.kafala.webservice.ApiClient;
import com.rocca.umrah.kafala.webservice.EndPointInterfaces;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterLayoutBinding binding;
    private String choosedCity, choosedCityId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_layout);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        setSupportActionBar(binding.toolbar.toolbar);
        binding.toolbar.tvToolbarTitle.setText(getResources().getString(R.string.register_text));
        setActions();
    }

    private void setActions() {
        binding.toolbar.ivBack.setOnClickListener(v -> {
            moveToLoginAct();
        });

        binding.tvLogin.setOnClickListener(v -> {
            moveToLoginAct();
        });
        binding.btnRegister.setOnClickListener(v -> {
            validate();
        });

        binding.tvChosedCity.setOnClickListener(v -> {
            moveToCityActivity();
        });

        binding.ivArrow.setOnClickListener(v -> {
            moveToCityActivity();
        });
    }

    private void moveToCityActivity() {
        Intent intent = new Intent(this, CityActivity.class);
        startActivityForResult(intent, 2);
    }

    private void validate() {
        if ((TextUtils.isEmpty(binding.etUserName.getText().toString().trim())) ||
                (TextUtils.isEmpty(binding.etPhoneNumber.getText().toString().trim())) ||
                (TextUtils.isEmpty(binding.etPassword.getText().toString().trim())) ||
                (TextUtils.isEmpty(binding.etPasswordConfirm.getText().toString().trim())) ||
                choosedCity == null) {
            Snackbar.make(binding.container, getString(R.string.enter_all_data), Snackbar.LENGTH_LONG).show();
        } else if (binding.etPassword.getText().toString().trim().length() < 6 ||
                binding.etPasswordConfirm.getText().toString().trim().length() < 6) {
            Snackbar.make(binding.container, getResources().getString(R.string.minimum_password_length), Snackbar.LENGTH_LONG).show();
        } else if (!binding.etPassword.getText().toString().trim().equals(
                binding.etPasswordConfirm.getText().toString().trim())) {
            Snackbar.make(binding.container, getResources().getString(R.string.password_must_be_matches), Snackbar.LENGTH_LONG).show();
        } else {
            ////////////////////////////Make Request /////////////////////
            if (NetWorkConnection.isConnectingToInternet(getApplicationContext())) {
                register();
            } else {
                Snackbar.make(binding.container, "من فضلك تأكد من الاتصال بالانترنت", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private void register() {
        binding.avi.setVisibility(View.VISIBLE);
        RegisterRequestDTO registerRequestDTO = getRegisterRequest();
        final EndPointInterfaces apiService =
                ApiClient.getClient().create(EndPointInterfaces.class);

        Call<RegisterResponseDTO> call = apiService.register("application/json", registerRequestDTO);
        call.enqueue(new Callback<RegisterResponseDTO>() {
            @Override
            public void onResponse(Call<RegisterResponseDTO> call, Response<RegisterResponseDTO> response) {
                binding.avi.setVisibility(View.GONE);
                RegisterResponseDTO registerResponseDTO = response.body();
                if (registerResponseDTO.getSuccess().equals("true")) {
                    //saveUserData(registerResponseDTO.getData().getInfo());
                    Intent activateAccountIntent = new Intent(RegisterActivity.this, ActivateAccountActivity.class);
                    startActivity(activateAccountIntent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponseDTO> call, Throwable t) {
                binding.avi.setVisibility(View.GONE);
                Snackbar.make(binding.container, "المستخدم موجود من قبل", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private RegisterRequestDTO getRegisterRequest() {
        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO();
        registerRequestDTO.setRegname(binding.etUserName.getText().toString().trim());
        registerRequestDTO.setRegcity(choosedCityId);
        registerRequestDTO.setRegpassword(binding.etPassword.getText().toString().trim());
        registerRequestDTO.setRegphone(binding.etPhoneNumber.getText().toString().trim());
        return registerRequestDTO;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void moveToLoginAct() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }


    @Override
    public void onBackPressed() {
        moveToLoginAct();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (data != null && data.getStringExtra("Choosed_City") != null) {
                choosedCity = data.getStringExtra("Choosed_City");
                choosedCityId = data.getStringExtra("Choosed_City_ID");
                binding.tvChosedCity.setText(choosedCity);
            }
        }
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
}
