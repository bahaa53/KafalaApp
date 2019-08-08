package com.rocca.umrah.kafala.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.google.android.material.snackbar.Snackbar;
import com.rocca.umrah.kafala.R;
import com.rocca.umrah.kafala.databinding.ActivityAddPostLayoutBinding;
import com.rocca.umrah.kafala.databinding.ActivityAdvDetailsLayoutBinding;
import com.rocca.umrah.kafala.dialog.DeleteAdvDialog;
import com.rocca.umrah.kafala.dialog.UpdatesDialog;
import com.rocca.umrah.kafala.interfaces.OnAdvDeleted;
import com.rocca.umrah.kafala.reponse.GenericResponseDTO;
import com.rocca.umrah.kafala.reponse.myadvresponse.InfoDTO;
import com.rocca.umrah.kafala.request.AddPostRequestDTO;
import com.rocca.umrah.kafala.request.EditPostRequest;
import com.rocca.umrah.kafala.request.UpdatePostDate;
import com.rocca.umrah.kafala.service.CategoriesService;
import com.rocca.umrah.kafala.service.CitiesService;
import com.rocca.umrah.kafala.service.NationalitiesService;
import com.rocca.umrah.kafala.utils.GetSavedUserData;
import com.rocca.umrah.kafala.utils.NetWorkConnection;
import com.rocca.umrah.kafala.webservice.ApiClient;
import com.rocca.umrah.kafala.webservice.EndPointInterfaces;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddDetailsActivity extends AppCompatActivity {
    private ActivityAdvDetailsLayoutBinding binding;
    private String cityID, categoryID, nationalId;
    private InfoDTO infoDTO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_adv_details_layout);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        setSupportActionBar(binding.toolbar.toolbar);
        binding.toolbar.tvToolbarTitle.setText(getResources().getString(R.string.edit_post));
        setActions();
        infoDTO = (InfoDTO) getIntent().getSerializableExtra("MY_ADV_OBJ");
        setDataToUi();
        setIds();
    }

    private void setIds() {
        NationalitiesService nationalitiesService = new NationalitiesService(getApplicationContext());
        nationalId = nationalitiesService.getIdOfSpecificNationality(infoDTO.getNationalityName());

        CategoriesService categoriesService = new CategoriesService(getApplicationContext());
        categoryID = categoriesService.getIdOfSpecificCategory(infoDTO.getCategoryName());

        CitiesService citiesService = new CitiesService(getApplicationContext());
        cityID = citiesService.getIdOfSpecificCity(infoDTO.getCityName());
    }

    private void setDataToUi() {
        binding.tvChoosedCategory.setText(infoDTO.getCategoryName());
        binding.tvChosedCity.setText(infoDTO.getCityName());
        binding.tvChoosedNationality.setText(infoDTO.getNationalityName());
        binding.etPhoneNumber.setText(infoDTO.getContact());
        binding.etAdditionalInfo.setText(infoDTO.getInfo());
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

        binding.editAdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePostDate();
            }
        });

        binding.deleteAdvContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteAdvDialog deleteAdvDialog = new DeleteAdvDialog(AddDetailsActivity.this, new OnAdvDeleted() {
                    @Override
                    public void notifywhenAdvDeleted(String deletedReason) {
                        deletePost();
                    }
                });

                deleteAdvDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                deleteAdvDialog.setCancelable(true);
                deleteAdvDialog.show();
            }
        });
    }

    private void deletePost() {
        if (NetWorkConnection.isConnectingToInternet(getApplicationContext())) {

            binding.avi.setVisibility(View.VISIBLE);
            final EndPointInterfaces apiService =
                    ApiClient.getClient().create(EndPointInterfaces.class);

            Call<GenericResponseDTO> call = apiService.deletePost("application/json", Integer.parseInt(infoDTO.getPostID()));
            call.enqueue(new Callback<GenericResponseDTO>() {
                @Override
                public void onResponse(Call<GenericResponseDTO> call, Response<GenericResponseDTO> response) {
                    binding.avi.setVisibility(View.GONE);
                    GenericResponseDTO genericResponseDTO = response.body();
                    if (genericResponseDTO.getSuccess().equals("true")) {
                        Snackbar.make(binding.container, "تم حذف الاعلان بنجاح", Snackbar.LENGTH_LONG).show();
                        delay();
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

    private void delay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }

    private void updatePostDate() {
        if (NetWorkConnection.isConnectingToInternet(getApplicationContext())) {

            UpdatePostDate updatePostDate = new UpdatePostDate();
            updatePostDate.setPostId(Integer.parseInt(infoDTO.getPostID()));
            binding.avi.setVisibility(View.VISIBLE);
            final EndPointInterfaces apiService =
                    ApiClient.getClient().create(EndPointInterfaces.class);

            Call<GenericResponseDTO> call = apiService.updatePostDate("application/json", updatePostDate);
            call.enqueue(new Callback<GenericResponseDTO>() {
                @Override
                public void onResponse(Call<GenericResponseDTO> call, Response<GenericResponseDTO> response) {
                    binding.avi.setVisibility(View.GONE);
                    GenericResponseDTO genericResponseDTO = response.body();
                    if (genericResponseDTO.getSuccess().equals("true")) {
                        UpdatesDialog updatesDialog = new UpdatesDialog(AddDetailsActivity.this);
                        updatesDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        updatesDialog.setCancelable(false);
                        updatesDialog.show();

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

    private void validate() {
        if ((TextUtils.isEmpty(cityID) ||
                (TextUtils.isEmpty(categoryID) ||
                        (TextUtils.isEmpty(nationalId) ||
                                (TextUtils.isEmpty(binding.etPhoneNumber.getText().toString().trim())) ||
                                TextUtils.isEmpty(binding.etAdditionalInfo.getText().toString().trim()))))) {
            Snackbar.make(binding.container, getString(R.string.enter_all_data), Snackbar.LENGTH_LONG).show();
        } else {
            editPost();
        }
    }

    private EditPostRequest getAddPostRequest() {
        EditPostRequest addPostRequestDTO = new EditPostRequest();
        addPostRequestDTO.setCity(Integer.parseInt(cityID));
        addPostRequestDTO.setCategory(Integer.parseInt(categoryID));
        addPostRequestDTO.setNationality(Integer.parseInt(nationalId));
        addPostRequestDTO.setContact(binding.etPhoneNumber.getText().toString().trim());
        addPostRequestDTO.setInfo(binding.etAdditionalInfo.getText().toString().trim());
        addPostRequestDTO.setPostId(Integer.parseInt(infoDTO.getPostID()));
        return addPostRequestDTO;
    }

    private void editPost() {
        if (NetWorkConnection.isConnectingToInternet(getApplicationContext())) {
            binding.avi.setVisibility(View.VISIBLE);

            final EndPointInterfaces apiService =
                    ApiClient.getClient().create(EndPointInterfaces.class);

            Call<GenericResponseDTO> call = apiService.updatePost("application/json", getAddPostRequest());
            call.enqueue(new Callback<GenericResponseDTO>() {
                @Override
                public void onResponse(Call<GenericResponseDTO> call, Response<GenericResponseDTO> response) {
                    binding.avi.setVisibility(View.GONE);
                    GenericResponseDTO genericResponseDTO = response.body();
                    if (genericResponseDTO.getSuccess().equals("true")) {
                        Snackbar.make(binding.container, "تمت تعديل بيانات الاعلان بنجاح", Snackbar.LENGTH_LONG).show();
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
