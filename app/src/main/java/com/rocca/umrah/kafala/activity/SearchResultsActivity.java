package com.rocca.umrah.kafala.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.rocca.umrah.kafala.R;
import com.rocca.umrah.kafala.adapter.CitiesAdapter;
import com.rocca.umrah.kafala.adapter.SearchResultAdapter;
import com.rocca.umrah.kafala.databinding.ActivityCityLayoutBinding;
import com.rocca.umrah.kafala.databinding.ActivitySearchResultLayoutBinding;
import com.rocca.umrah.kafala.fragment.BottomSheetFragment;
import com.rocca.umrah.kafala.interfaces.OnBottomSheetClicked;
import com.rocca.umrah.kafala.interfaces.OnPhoneCallClicked;
import com.rocca.umrah.kafala.reponse.InfoDTO;
import com.rocca.umrah.kafala.reponse.adverismentresponse.SearchResultResponseDTO;
import com.rocca.umrah.kafala.reponse.register.RegisterRequestDTO;
import com.rocca.umrah.kafala.reponse.register.RegisterResponseDTO;
import com.rocca.umrah.kafala.service.CitiesService;
import com.rocca.umrah.kafala.utils.NetWorkConnection;
import com.rocca.umrah.kafala.webservice.ApiClient;
import com.rocca.umrah.kafala.webservice.EndPointInterfaces;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {

    private ActivitySearchResultLayoutBinding binding;
    private List<SearchResultResponseDTO> results;
    private SearchResultAdapter searchResultAdapter;
    private String categorId;
    private String nationalId;
    private String cityId;
    private boolean isAsc;
    private Integer cityQuery;
    private Integer categoryQuery;
    private Integer nationalQuery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_result_layout);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        binding.toolbar.tvToolbarTitle.setText(getResources().getString(R.string.search_results));
        binding.toolbar.ivBack.setOnClickListener(v -> finish());
        binding.toolbar.ivSort.setVisibility(View.VISIBLE);
        binding.toolbar.ivSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment bottomSheetFragment = BottomSheetFragment.getInstance(new OnBottomSheetClicked() {
                    @Override
                    public void sortType(boolean isRecent) {
                        if (isRecent && !isAsc) {
                            Collections.reverse(results);
                            isAsc = true;
                        } else if (!isRecent && isAsc) {
                            Collections.reverse(results);
                            isAsc = false;
                        }
                        searchResultAdapter.notifyDataSetChanged();
                    }
                });
                bottomSheetFragment.show(getSupportFragmentManager(), "Custom Bottom Sheet");
            }
        });
        results = new ArrayList<>();
        setupRecyclerView();
        getIntentData();
    }

    private void getIntentData() {
        cityId = getIntent().getStringExtra("Choosed_City_ID");
        categorId = getIntent().getStringExtra("Choosed_Category_ID");
        nationalId = getIntent().getStringExtra("Choosed_Nationality_ID");
        if (cityId == null && nationalId == null && categorId == null) {
            getAllAdv();
        } else {
            if (cityId != null) {
                cityQuery = Integer.parseInt(cityId);
            }

            if (categorId != null) {
                categoryQuery = Integer.parseInt(categorId);
            }

            if (nationalId != null) {
                nationalQuery = Integer.parseInt(nationalId);
            }
            search();
        }
    }

    private void getAllAdv() {
        if (NetWorkConnection.isConnectingToInternet(getApplicationContext())) {
            getAlAdv();
        } else {
            Snackbar.make(binding.container, "من فضلك تأكد من الاتصال بالانترنت", Snackbar.LENGTH_LONG).show();
        }
    }

    private void search() {
        if (NetWorkConnection.isConnectingToInternet(getApplicationContext())) {
            searchRequest();
        } else {
            Snackbar.make(binding.container, "من فضلك تأكد من الاتصال بالانترنت", Snackbar.LENGTH_LONG).show();
        }
    }

    private void searchRequest() {
        binding.avi.setVisibility(View.VISIBLE);

        final EndPointInterfaces apiService =
                ApiClient.getClient().create(EndPointInterfaces.class);

        Call<List<SearchResultResponseDTO>> call = apiService.searchForAdvertisments("application/json", categoryQuery, nationalQuery, cityQuery);
        call.enqueue(new Callback<List<SearchResultResponseDTO>>() {
            @Override
            public void onResponse(Call<List<SearchResultResponseDTO>> call, Response<List<SearchResultResponseDTO>> response) {
                binding.avi.setVisibility(View.GONE);
                List<SearchResultResponseDTO> results2 = response.body();
                if (results2.size() > 0) {
                    binding.searchResults.setVisibility(View.VISIBLE);
                    binding.tvNoResult.setVisibility(View.GONE);
                    results.addAll(results2);
                    Collections.reverse(results);
                    isAsc = true;
                    searchResultAdapter.notifyDataSetChanged();
                } else {
                    binding.searchResults.setVisibility(View.GONE);
                    binding.tvNoResult.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<SearchResultResponseDTO>> call, Throwable t) {
                binding.avi.setVisibility(View.GONE);
                binding.searchResults.setVisibility(View.GONE);
                binding.tvNoResult.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getAlAdv() {
        binding.avi.setVisibility(View.VISIBLE);

        final EndPointInterfaces apiService =
                ApiClient.getClient().create(EndPointInterfaces.class);

        Call<List<SearchResultResponseDTO>> call = apiService.getAllAdvertisments("application/json");
        call.enqueue(new Callback<List<SearchResultResponseDTO>>() {
            @Override
            public void onResponse(Call<List<SearchResultResponseDTO>> call, Response<List<SearchResultResponseDTO>> response) {
                binding.avi.setVisibility(View.GONE);
                List<SearchResultResponseDTO> results2 = response.body();
                if (results2.size() > 0) {
                    binding.searchResults.setVisibility(View.VISIBLE);
                    binding.tvNoResult.setVisibility(View.GONE);
                    results.addAll(results2);
                    Collections.reverse(results);
                    isAsc = true;
                    searchResultAdapter.notifyDataSetChanged();
                } else {
                    binding.searchResults.setVisibility(View.GONE);
                    binding.tvNoResult.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<SearchResultResponseDTO>> call, Throwable t) {
                binding.avi.setVisibility(View.GONE);
                binding.searchResults.setVisibility(View.GONE);
                binding.tvNoResult.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.searchResults.setLayoutManager(linearLayoutManager);
        searchResultAdapter = new SearchResultAdapter(results, phoneNumber -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null));
            startActivity(intent);
        });
        binding.searchResults.setAdapter(searchResultAdapter);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
