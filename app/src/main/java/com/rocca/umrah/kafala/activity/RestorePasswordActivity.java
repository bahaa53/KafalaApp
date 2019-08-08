package com.rocca.umrah.kafala.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.rocca.umrah.kafala.R;
import com.rocca.umrah.kafala.databinding.ActivityRestorePasswordLayoutBinding;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RestorePasswordActivity extends AppCompatActivity {

    private ActivityRestorePasswordLayoutBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_restore_password_layout);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        setSupportActionBar(binding.toolbar.toolbar);
        binding.toolbar.tvToolbarTitle.setText(getResources().getString(R.string.restore_password_title));
        binding.toolbar.ivBack.setOnClickListener(v -> {
            finish();
        });

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
