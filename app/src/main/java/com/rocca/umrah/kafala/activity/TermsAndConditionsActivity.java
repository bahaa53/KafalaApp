package com.rocca.umrah.kafala.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CompoundButton;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.rocca.umrah.kafala.R;
import com.rocca.umrah.kafala.databinding.ActivityTermsAndConditionsLayoutBinding;
import com.rocca.umrah.kafala.reponse.GenericResponseDTO;
import com.rocca.umrah.kafala.reponse.about.AboutResponseDTO;
import com.rocca.umrah.kafala.reponse.register.InfoDTO;
import com.rocca.umrah.kafala.request.AddPostRequestDTO;
import com.rocca.umrah.kafala.utils.ConfigurationFile;
import com.rocca.umrah.kafala.utils.NetWorkConnection;
import com.rocca.umrah.kafala.webservice.ApiClient;
import com.rocca.umrah.kafala.webservice.EndPointInterfaces;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TermsAndConditionsActivity extends AppCompatActivity {

    private ActivityTermsAndConditionsLayoutBinding binding;
    private AddPostRequestDTO addPostRequestDTO;
    private boolean isFromMakfool;
    private String categorId;
    private String nationalId;
    private String cityId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_terms_and_conditions_layout);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        setSupportActionBar(binding.toolbar.toolbar);
        binding.toolbar.tvToolbarTitle.setText(getResources().getString(R.string.accept_agreement));
        setActions();
        getTermsContent();
        isFromMakfool = getIntent().getBooleanExtra("IS_MAKFOOL_DATA", false);
        if (!isFromMakfool) {
            addPostRequestDTO = (AddPostRequestDTO) getIntent().getSerializableExtra("ADD_POST_OBJ");
        } else {
            getIntentData();
        }
    }

    private void getIntentData() {
        cityId = getIntent().getStringExtra("Choosed_City_ID");
        categorId = getIntent().getStringExtra("Choosed_Category_ID");
        nationalId = getIntent().getStringExtra("Choosed_Nationality_ID");
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void getTermsContent() {
        if (NetWorkConnection.isConnectingToInternet(getApplicationContext())) {
            firstTermsRequest();
            secondTermsRequest();
        } else {
            Snackbar.make(binding.container, "من فضلك تأكد من الاتصال بالانترنت", Snackbar.LENGTH_LONG).show();
        }
    }


    private void setActions() {
        binding.toolbar.ivBack.setOnClickListener(v -> {
            finish();
        });

        binding.switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (binding.switch2.isChecked()) {
                    if (!isFromMakfool) {
                        publishRequest();
                    } else {
                        updateSharedPref();
                        showSearchResults();
                    }
                }
            }
        });

        binding.switch2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (binding.switch1.isChecked()) {
                    if (!isFromMakfool) {
                        publishRequest();
                    } else {
                        updateSharedPref();
                        showSearchResults();
                    }
                }
            }

        });
    }

    private void updateSharedPref() {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.putBoolean(ConfigurationFile.Constants.SEARCH_FOR_MAKFOOL_TERMS, true);
        prefsEditor.commit();
    }

    private void showSearchResults() {
        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putExtra("Choosed_City_ID", cityId);
        intent.putExtra("Choosed_Nationality_ID", nationalId);
        intent.putExtra("Choosed_Category_ID", categorId);
        startActivity(intent);
        finish();
    }


    private void firstTermsRequest() {
        binding.avi.setVisibility(View.VISIBLE);

        final EndPointInterfaces apiService =
                ApiClient.getClient().create(EndPointInterfaces.class);

        Call<AboutResponseDTO> call = apiService.getFirstTerms("application/json");
        call.enqueue(new Callback<AboutResponseDTO>() {
            @Override
            public void onResponse(Call<AboutResponseDTO> call, Response<AboutResponseDTO> response) {
                binding.avi.setVisibility(View.GONE);
                AboutResponseDTO aboutResponseDTO = response.body();
                if (aboutResponseDTO.getSuccess().equals("true")) {
                    binding.tvTerms1.setText(aboutResponseDTO.getData().getInfo().getBody());
                }
            }

            @Override
            public void onFailure(Call<AboutResponseDTO> call, Throwable t) {
                binding.avi.setVisibility(View.GONE);
            }
        });
    }


    private void secondTermsRequest() {
        binding.avi.setVisibility(View.VISIBLE);

        final EndPointInterfaces apiService =
                ApiClient.getClient().create(EndPointInterfaces.class);

        Call<AboutResponseDTO> call = apiService.getSecondTerms("application/json");
        call.enqueue(new Callback<AboutResponseDTO>() {
            @Override
            public void onResponse(Call<AboutResponseDTO> call, Response<AboutResponseDTO> response) {
                binding.avi.setVisibility(View.GONE);
                AboutResponseDTO aboutResponseDTO = response.body();
                if (aboutResponseDTO.getSuccess().equals("true")) {
                    binding.tvTerms2.setText(aboutResponseDTO.getData().getInfo().getBody());
                }
            }

            @Override
            public void onFailure(Call<AboutResponseDTO> call, Throwable t) {
                binding.avi.setVisibility(View.GONE);
            }
        });
    }

    private void publishRequest() {
        if (NetWorkConnection.isConnectingToInternet(getApplicationContext())) {
            binding.avi.setVisibility(View.VISIBLE);

            final EndPointInterfaces apiService =
                    ApiClient.getClient().create(EndPointInterfaces.class);

            Call<GenericResponseDTO> call = apiService.addPost("application/json", addPostRequestDTO);
            call.enqueue(new Callback<GenericResponseDTO>() {
                @Override
                public void onResponse(Call<GenericResponseDTO> call, Response<GenericResponseDTO> response) {
                    binding.avi.setVisibility(View.GONE);
                    GenericResponseDTO genericResponseDTO = response.body();
                    if (genericResponseDTO.getSuccess().equals("true")) {
                        Snackbar.make(binding.container, "تمت أضافة الاعلان بنجاح", Snackbar.LENGTH_LONG).show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(TermsAndConditionsActivity.this, MyAdvActivity.class);
                                intent.putExtra("Act_Nav", true);
                                startActivity(intent);
                                finishAffinity();
                            }
                        }, 2000);

                    }
                }

                @Override
                public void onFailure(Call<GenericResponseDTO> call, Throwable t) {
                    binding.avi.setVisibility(View.GONE);
                    Snackbar.make(binding.container, "لقد حدث خطأ يرجى المحاولة لاحقا", Snackbar.LENGTH_LONG).show();
                }
            });
        } else {
            Snackbar.make(binding.container, "من فضلك تأكد من الاتصال بالانترنت", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
