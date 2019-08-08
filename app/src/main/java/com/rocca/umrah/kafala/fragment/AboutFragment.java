package com.rocca.umrah.kafala.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.rocca.umrah.kafala.R;
import com.rocca.umrah.kafala.activity.LoginActivity;
import com.rocca.umrah.kafala.activity.MainActivity;
import com.rocca.umrah.kafala.databinding.FragmentAboutLayoutBinding;
import com.rocca.umrah.kafala.databinding.FragmentMainLayoutBinding;
import com.rocca.umrah.kafala.reponse.about.AboutResponseDTO;
import com.rocca.umrah.kafala.reponse.register.RegisterResponseDTO;
import com.rocca.umrah.kafala.request.LoginRequestDTO;
import com.rocca.umrah.kafala.utils.NetWorkConnection;
import com.rocca.umrah.kafala.webservice.ApiClient;
import com.rocca.umrah.kafala.webservice.EndPointInterfaces;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutFragment extends Fragment {

    private FragmentAboutLayoutBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about_layout,
                container, false);
        getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        View view = binding.getRoot();
        binding.tvAboutContent.setMovementMethod(new ScrollingMovementMethod());
        getAboutContent();
        return view;
    }

    private void getAboutContent() {
        if (NetWorkConnection.isConnectingToInternet(getActivity().getApplicationContext())) {
            aboutRequest();
        } else {
            Snackbar.make(getActivity().findViewById(R.id.drawer), "من فضلك تأكد من الاتصال بالانترنت", Snackbar.LENGTH_LONG).show();
        }
    }

    private void aboutRequest() {
        binding.avi.setVisibility(View.VISIBLE);

        final EndPointInterfaces apiService =
                ApiClient.getClient().create(EndPointInterfaces.class);

        Call<AboutResponseDTO> call = apiService.about("application/json");
        call.enqueue(new Callback<AboutResponseDTO>() {
            @Override
            public void onResponse(Call<AboutResponseDTO> call, Response<AboutResponseDTO> response) {
                binding.avi.setVisibility(View.GONE);
                AboutResponseDTO aboutResponseDTO = response.body();
                if (aboutResponseDTO.getSuccess().equals("true")) {
                    binding.tvAboutContent.setText(aboutResponseDTO.getData().getInfo().getBody());
                }
            }

            @Override
            public void onFailure(Call<AboutResponseDTO> call, Throwable t) {
                binding.avi.setVisibility(View.GONE);
            }
        });
    }

}
