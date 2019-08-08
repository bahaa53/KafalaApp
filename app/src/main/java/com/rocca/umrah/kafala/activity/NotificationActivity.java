package com.rocca.umrah.kafala.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.rocca.umrah.kafala.R;
import com.rocca.umrah.kafala.adapter.MyAdvAdapter;
import com.rocca.umrah.kafala.adapter.NotificationAdapter;
import com.rocca.umrah.kafala.databinding.ActivityNotificationLayoutBinding;
import com.rocca.umrah.kafala.databinding.ActivitySearchResultLayoutBinding;
import com.rocca.umrah.kafala.reponse.about.AboutResponseDTO;
import com.rocca.umrah.kafala.reponse.notifications.InfoDTO;
import com.rocca.umrah.kafala.reponse.myadvresponse.MyAdvResponseDTO;
import com.rocca.umrah.kafala.reponse.notifications.NotificationResponseDTO;
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

public class NotificationActivity extends AppCompatActivity {

    private ActivitySearchResultLayoutBinding binding;
    private List<InfoDTO> results;
    private NotificationAdapter searchResultAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_result_layout);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        binding.toolbar.tvToolbarTitle.setText("الأشعارات");
        binding.toolbar.ivBack.setOnClickListener(v -> finish());
        results = new ArrayList<>();
        setupRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNotifications();
    }


    private void getNotifications() {
        if (NetWorkConnection.isConnectingToInternet(getApplicationContext())) {
            getNotificationsApi();
        } else {
            Snackbar.make(binding.container, "من فضلك تأكد من الاتصال بالانترنت", Snackbar.LENGTH_LONG).show();
        }
    }

    private void getNotificationsApi() {
        binding.avi.setVisibility(View.VISIBLE);

        final EndPointInterfaces apiService =
                ApiClient.getClient().create(EndPointInterfaces.class);

        Call<NotificationResponseDTO> call = apiService.getAllNotification("application/json");
        call.enqueue(new Callback<NotificationResponseDTO>() {
            @Override
            public void onResponse(Call<NotificationResponseDTO> call, Response<NotificationResponseDTO> response) {
                binding.avi.setVisibility(View.GONE);
                NotificationResponseDTO responseDTO = response.body();
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
            public void onFailure(Call<NotificationResponseDTO> call, Throwable t) {
                binding.avi.setVisibility(View.GONE);
                binding.searchResults.setVisibility(View.GONE);
                binding.tvNoResult.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.searchResults.setLayoutManager(linearLayoutManager);
        searchResultAdapter = new NotificationAdapter(results);
        binding.searchResults.setAdapter(searchResultAdapter);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
