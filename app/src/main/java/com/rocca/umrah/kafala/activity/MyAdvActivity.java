package com.rocca.umrah.kafala.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.snackbar.Snackbar;
import com.rocca.umrah.kafala.R;
import com.rocca.umrah.kafala.adapter.MyAdvAdapter;
import com.rocca.umrah.kafala.databinding.ActivitySearchResultLayoutBinding;

import com.rocca.umrah.kafala.reponse.myadvresponse.InfoDTO;
import com.rocca.umrah.kafala.reponse.myadvresponse.MyAdvResponseDTO;
import com.rocca.umrah.kafala.utils.GetSavedUserData;
import com.rocca.umrah.kafala.utils.NetWorkConnection;
import com.rocca.umrah.kafala.webservice.ApiClient;
import com.rocca.umrah.kafala.webservice.EndPointInterfaces;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import java.util.ArrayList;
import java.util.List;

public class MyAdvActivity extends AppCompatActivity {

    private ActivitySearchResultLayoutBinding binding;
    private List<InfoDTO> results;
    private MyAdvAdapter searchResultAdapter;
    private boolean isFromMyAdvs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_result_layout);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        binding.toolbar.tvToolbarTitle.setText("أعلاناتى");
        isFromMyAdvs = getIntent().getBooleanExtra("Act_Nav", false);
        binding.toolbar.ivBack.setOnClickListener(v -> {
            if (!isFromMyAdvs) {
                finish();
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        results = new ArrayList<>();
        setupRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyAdvertisments();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!isFromMyAdvs) {
            finish();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private Integer getUserId() {
        GetSavedUserData getSavedUserData = new GetSavedUserData(getApplicationContext());
        com.rocca.umrah.kafala.reponse.register.InfoDTO userdata = getSavedUserData.getSavedUserData();
        return Integer.parseInt(userdata.getId());
    }

    private void getMyAdvertisments() {
        if (NetWorkConnection.isConnectingToInternet(getApplicationContext())) {
            getMyAdv();
        } else {
            Snackbar.make(binding.container, "من فضلك تأكد من الاتصال بالانترنت", Snackbar.LENGTH_LONG).show();
        }
    }

    private void getMyAdv() {
        binding.avi.setVisibility(View.VISIBLE);

        final EndPointInterfaces apiService =
                ApiClient.getClient().create(EndPointInterfaces.class);

        Call<MyAdvResponseDTO> call = apiService.myAdv("application/json", getUserId());
        call.enqueue(new Callback<MyAdvResponseDTO>() {
            @Override
            public void onResponse(Call<MyAdvResponseDTO> call, Response<MyAdvResponseDTO> response) {
                binding.avi.setVisibility(View.GONE);
                MyAdvResponseDTO responseDTO = response.body();
                if (responseDTO.getData().getInfo().size() > 0) {
                    binding.searchResults.setVisibility(View.VISIBLE);
                    binding.tvNoResult.setVisibility(View.GONE);
                    results.clear();
                    results.addAll(responseDTO.getData().getInfo());
                    searchResultAdapter.notifyDataSetChanged();
                } else {
                    binding.searchResults.setVisibility(View.GONE);
                    binding.tvNoResult.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<MyAdvResponseDTO> call, Throwable t) {
                binding.avi.setVisibility(View.GONE);
                binding.searchResults.setVisibility(View.GONE);
                binding.tvNoResult.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.searchResults.setLayoutManager(linearLayoutManager);
        searchResultAdapter = new MyAdvAdapter(results, infoDTO -> {

            Intent intent = new Intent(this, AddDetailsActivity.class);
            intent.putExtra("MY_ADV_OBJ", infoDTO);
            startActivity(intent);

        });
        binding.searchResults.setAdapter(searchResultAdapter);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}