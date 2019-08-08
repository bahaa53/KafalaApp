package com.rocca.umrah.kafala.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.rocca.umrah.kafala.R;
import com.rocca.umrah.kafala.databinding.ActivityAccountActivationLayoutBinding;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivateAccountActivity extends AppCompatActivity {

    private ActivityAccountActivationLayoutBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_account_activation_layout);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        setSupportActionBar(binding.toolbar.toolbar);
        binding.toolbar.tvToolbarTitle.setText(getResources().getString(R.string.account_activation));
        binding.btnSkip.setOnClickListener(v -> {
            Intent activateAccountIntent = new Intent(ActivateAccountActivity.this, LoginActivity.class);
            startActivity(activateAccountIntent);
            finish();
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
