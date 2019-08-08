package com.rocca.umrah.kafala.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.rocca.umrah.kafala.R;
import com.rocca.umrah.kafala.activity.AddPostActivity;
import com.rocca.umrah.kafala.activity.LoginActivity;
import com.rocca.umrah.kafala.activity.SearchForMakfoolActivity;
import com.rocca.umrah.kafala.activity.SearchResultsActivity;
import com.rocca.umrah.kafala.databinding.FragmentMainLayoutBinding;
import com.rocca.umrah.kafala.reponse.register.InfoDTO;
import com.rocca.umrah.kafala.utils.GetSavedUserData;

public class MainFrag extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentMainLayoutBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_layout,
                container, false);
        View view = binding.getRoot();
        GetSavedUserData getSavedUserData = new GetSavedUserData(getActivity().getApplicationContext());
        InfoDTO userdata = getSavedUserData.getSavedUserData();
        binding.containerSearchForKafeel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userdata != null) {
                    Intent intent = new Intent(getActivity(), AddPostActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finishAffinity();
                }
            }
        });

        binding.containerSearchForMakfool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchForMakfoolActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}

