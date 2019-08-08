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
import com.rocca.umrah.kafala.databinding.ActivityLoginLayoutBinding;
import com.rocca.umrah.kafala.reponse.register.InfoDTO;
import com.rocca.umrah.kafala.reponse.register.RegisterResponseDTO;
import com.rocca.umrah.kafala.request.LoginRequestDTO;
import com.rocca.umrah.kafala.utils.ConfigurationFile;
import com.rocca.umrah.kafala.utils.NetWorkConnection;
import com.rocca.umrah.kafala.webservice.ApiClient;
import com.rocca.umrah.kafala.webservice.EndPointInterfaces;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginLayoutBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_layout);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        setSupportActionBar(binding.toolbar.toolbar);
        binding.toolbar.tvToolbarTitle.setText(getResources().getString(R.string.مخلهى));
        setActions();
    }

    private void setActions() {
        binding.toolbar.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.tvRegisterNewAccount.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

        binding.tvGetPassword.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        binding.tvGetPassword.setOnClickListener(v -> {
            Intent resorePasswordIntent = new Intent(LoginActivity.this, RestorePasswordActivity.class);
            startActivity(resorePasswordIntent);
        });

        binding.btnLogin.setOnClickListener(v -> {
            validate();
        });
    }

    private void validate() {
        if ((TextUtils.isEmpty(binding.etPassword.getText().toString().trim())) ||
                (TextUtils.isEmpty(binding.etPhoneNumber.getText().toString().trim()))) {
            Snackbar.make(binding.container, getString(R.string.enter_all_data), Snackbar.LENGTH_LONG).show();
        } else {
            ////////////////////////////Make Request /////////////////////
            if (NetWorkConnection.isConnectingToInternet(getApplicationContext())) {
                login();
            } else {
                Snackbar.make(binding.container, "من فضلك تأكد من الاتصال بالانترنت", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private void login() {
        binding.avi.setVisibility(View.VISIBLE);
        LoginRequestDTO loginRequestDTO = getLoginRequest();
        final EndPointInterfaces apiService =
                ApiClient.getClient().create(EndPointInterfaces.class);

        Call<RegisterResponseDTO> call = apiService.login("application/json", loginRequestDTO);
        call.enqueue(new Callback<RegisterResponseDTO>() {
            @Override
            public void onResponse(Call<RegisterResponseDTO> call, Response<RegisterResponseDTO> response) {
                binding.avi.setVisibility(View.GONE);
                RegisterResponseDTO registerResponseDTO = response.body();
                if (registerResponseDTO.getSuccess().equals("true")) {
                    saveUserData(registerResponseDTO.getData().getInfo());
                    Intent activateAccountIntent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(activateAccountIntent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponseDTO> call, Throwable t) {
                binding.avi.setVisibility(View.GONE);
                Snackbar.make(binding.container, "رقم جوال او كلمة مرور خاطئة", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private LoginRequestDTO getLoginRequest() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setPassword(binding.etPassword.getText().toString().trim());
        loginRequestDTO.setPhone(binding.etPhoneNumber.getText().toString().trim());
        return loginRequestDTO;
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
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
