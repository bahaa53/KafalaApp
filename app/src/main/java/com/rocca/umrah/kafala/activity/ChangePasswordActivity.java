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
import com.rocca.umrah.kafala.databinding.ActivityChangePasswordLayoutBinding;
import com.rocca.umrah.kafala.reponse.GenericResponseDTO;
import com.rocca.umrah.kafala.reponse.register.RegisterResponseDTO;
import com.rocca.umrah.kafala.request.ChangePasswordRequest;
import com.rocca.umrah.kafala.request.LoginRequestDTO;
import com.rocca.umrah.kafala.utils.GetSavedUserData;
import com.rocca.umrah.kafala.utils.NetWorkConnection;
import com.rocca.umrah.kafala.webservice.ApiClient;
import com.rocca.umrah.kafala.webservice.EndPointInterfaces;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChangePasswordActivity extends AppCompatActivity {

    private ActivityChangePasswordLayoutBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password_layout);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        setSupportActionBar(binding.toolbar.toolbar);
        binding.toolbar.tvToolbarTitle.setText(getResources().getString(R.string.change_password_text));
        setActions();
    }

    private void setActions() {
        binding.toolbar.ivBack.setOnClickListener(v -> finish());

        binding.btnSaveChanges.setOnClickListener(v -> {
            validate();
        });
    }

    private void validate() {
        if (binding.etNewPassword.getText().toString().trim().length() < 6 ||
                binding.etNewPasswordConfirm.getText().toString().trim().length() < 6) {
            Snackbar.make(binding.container, getResources().getString(R.string.minimum_password_length), Snackbar.LENGTH_LONG).show();
        } else if (!binding.etNewPassword.getText().toString().trim().equals(
                binding.etNewPasswordConfirm.getText().toString().trim())) {
            Snackbar.make(binding.container, getResources().getString(R.string.password_must_be_matches), Snackbar.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(binding.etNewPassword.getText().toString()) ||
                TextUtils.isEmpty(binding.etNewPasswordConfirm.getText().toString().trim()) ||
                TextUtils.isEmpty(binding.etCurrentPassword.getText().toString().trim())) {
            Snackbar.make(binding.container, "من فضلك أدخل كل البيانات", Snackbar.LENGTH_LONG).show();
        } else {
            ////////////////////////////Make Request /////////////////////
            if (NetWorkConnection.isConnectingToInternet(getApplicationContext())) {
                chagePasswordApi();
            } else {
                Snackbar.make(binding.container, "من فضلك تأكد من الاتصال بالانترنت", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private void chagePasswordApi() {
        binding.avi.setVisibility(View.VISIBLE);
        ChangePasswordRequest changePasswordRequest = getChangePasswordRequest();
        final EndPointInterfaces apiService =
                ApiClient.getClient().create(EndPointInterfaces.class);

        Call<GenericResponseDTO> call = apiService.changePassword("application/json", changePasswordRequest);
        call.enqueue(new Callback<GenericResponseDTO>() {
            @Override
            public void onResponse(Call<GenericResponseDTO> call, Response<GenericResponseDTO> response) {
                binding.avi.setVisibility(View.GONE);
                GenericResponseDTO registerResponseDTO = response.body();
                if (registerResponseDTO.getSuccess().equals("true")) {
                    Snackbar.make(binding.container, "تم تحديث كلمة السر بنجاح", Snackbar.LENGTH_LONG).show();

                } else {
                    Snackbar.make(binding.container, "كلمة المرور القديمة خاطئة", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GenericResponseDTO> call, Throwable t) {
                binding.avi.setVisibility(View.GONE);
            }
        });
    }

    private ChangePasswordRequest getChangePasswordRequest() {

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setOldPass(binding.etCurrentPassword.getText().toString().trim());
        changePasswordRequest.setNewPass(binding.etNewPasswordConfirm.getText().toString().trim());
        changePasswordRequest.setConfirmPass(binding.etNewPasswordConfirm.getText().toString().trim());
        changePasswordRequest.setUserId(Integer.parseInt(new GetSavedUserData(getApplicationContext()).getSavedUserData().getId()));
        return changePasswordRequest;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
