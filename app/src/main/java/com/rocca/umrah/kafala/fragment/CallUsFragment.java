package com.rocca.umrah.kafala.fragment;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
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
import com.rocca.umrah.kafala.databinding.FragmentAboutLayoutBinding;
import com.rocca.umrah.kafala.reponse.about.AboutResponseDTO;
import com.rocca.umrah.kafala.utils.NetWorkConnection;
import com.rocca.umrah.kafala.webservice.ApiClient;
import com.rocca.umrah.kafala.webservice.EndPointInterfaces;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallUsFragment extends Fragment {

    private FragmentAboutLayoutBinding binding;
    private   View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about_layout,
                container, false);
        getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
         view = binding.getRoot();
        binding.tvAboutContent.setMovementMethod(new ScrollingMovementMethod());
        binding.tvWhoAreWe.setText(getResources().getString(R.string.call_us));
        getCallUsContent();
        return view;
    }

    private void getCallUsContent() {
        if (NetWorkConnection.isConnectingToInternet(getActivity().getApplicationContext())) {
            callUsRequest();
        } else {
            Snackbar.make(getActivity().findViewById(R.id.drawer), "من فضلك تأكد من الاتصال بالانترنت", Snackbar.LENGTH_LONG).show();
        }
    }

    private void callUsRequest() {
        binding.avi.setVisibility(View.VISIBLE);

        final EndPointInterfaces apiService =
                ApiClient.getClient().create(EndPointInterfaces.class);

        Call<AboutResponseDTO> call = apiService.callus("application/json");
        call.enqueue(new Callback<AboutResponseDTO>() {
            @Override
            public void onResponse(Call<AboutResponseDTO> call, Response<AboutResponseDTO> response) {
                binding.avi.setVisibility(View.GONE);
                AboutResponseDTO aboutResponseDTO = response.body();
                System.out.println("call us content :" + new Gson().toJson(aboutResponseDTO));
                if (aboutResponseDTO.getSuccess().equals("true")) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                        binding.tvAboutContent.setText(Html.fromHtml(aboutResponseDTO.getData().getInfo().getBody(), Html.FROM_HTML_MODE_LEGACY));
                } else {
                    binding.tvAboutContent.setText(Html.fromHtml(aboutResponseDTO.getData().getInfo().getBody()));
                }
            }

            @Override
            public void onFailure(Call<AboutResponseDTO> call, Throwable t) {
                binding.avi.setVisibility(View.GONE);
            }
        });
    }
}
