package com.rocca.umrah.kafala.fragment;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.rocca.umrah.kafala.R;
import com.rocca.umrah.kafala.databinding.FragmentSortByLayoutBinding;
import com.rocca.umrah.kafala.interfaces.OnBottomSheetClicked;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private FragmentSortByLayoutBinding binding;
    private static OnBottomSheetClicked onBottomSheetClicked;

    public static BottomSheetFragment getInstance(OnBottomSheetClicked onBottomSheetClicked) {
        BottomSheetFragment.onBottomSheetClicked = onBottomSheetClicked;
        return new BottomSheetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sort_by_layout,
                container, false);
        getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        View view = binding.getRoot();
        binding.tvRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBottomSheetClicked.sortType(true);
                BottomSheetFragment.this.dismiss();
            }
        });

        binding.tvOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBottomSheetClicked.sortType(false);
                BottomSheetFragment.this.dismiss();
            }
        });
        return view;
    }
}
