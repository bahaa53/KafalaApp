package com.rocca.umrah.kafala.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.snackbar.Snackbar;
import com.rocca.umrah.kafala.R;
import com.rocca.umrah.kafala.databinding.ActivityMainBinding;
import com.rocca.umrah.kafala.fragment.AboutFragment;
import com.rocca.umrah.kafala.fragment.BankAccountFragment;
import com.rocca.umrah.kafala.fragment.CallUsFragment;
import com.rocca.umrah.kafala.fragment.MainFrag;
import com.rocca.umrah.kafala.reponse.register.InfoDTO;
import com.rocca.umrah.kafala.utils.GetSavedUserData;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Fragment fragment;
    private boolean doubleBackToExitPressedOnce;
    private InfoDTO userdata;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        setSupportActionBar(binding.toolbar.toolbar);
        GetSavedUserData getSavedUserData = new GetSavedUserData(getApplicationContext());
        userdata = getSavedUserData.getSavedUserData();
        setUserData();
        setDefaultFragment();
        setNavigationViewListener();
        setupPopUpMenu();
    }

    private void setupPopUpMenu() {
        binding.toolbar.ivSettings.setOnClickListener(v -> {
            if (userdata != null) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, binding.toolbar.ivSettings);
                popupMenu.getMenuInflater().inflate(R.menu.setting_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.item_user_profile: {
                            moveToProfileActivity();
                            return true;
                        }

                        case R.id.item_notifications: {
                            moveToNotificationAct();
                            return true;
                        }

                    }
                    return true;
                });

                popupMenu.show();
            } else {
                moveToLoginActivity();
            }
        });
    }

    private void moveToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void moveToNotificationAct() {
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }

    private void moveToProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }

    private void setUserData() {
        if (userdata != null) {
            binding.toolbar.tvUserName.setText(userdata.getName());
        } else {
            binding.toolbar.ivSettings.setImageResource(R.drawable.ic_input_black_24dp);
            binding.toolbar.tvUserName.setText("دخول");
        }
    }

    private void setNavigationViewListener() {
        binding.navigationView.setNavigationItemSelectedListener(menuItem -> {
            menuItem.setChecked(true);
            //Closing drawer on item click
            binding.drawer.closeDrawers();
            switch (menuItem.getItemId()) {
                case R.id.home: {
                    moveToMainFragment();
                    return true;
                }
                case R.id.about: {
                    fragment = new AboutFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame, fragment);
                    fragmentTransaction.commit();
                    return true;
                }
                case R.id.bankAccounts: {
                    fragment = new BankAccountFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame, fragment);
                    fragmentTransaction.commit();
                    return true;
                }
                case R.id.callUs: {
                    fragment = new CallUsFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame, fragment);
                    fragmentTransaction.commit();
                    return true;
                }

                case R.id.kafeel: {
                    if (userdata != null) {
                        Intent intent = new Intent(this, AddPostActivity.class);
                        startActivity(intent);
                    } else {
                        moveToLoginActivity();
                    }
                    return true;
                }

                case R.id.makfool: {
                    Intent intent = new Intent(this, SearchForMakfoolActivity.class);
                    startActivity(intent);
                    return true;
                }


            }

            return false;
        });
        setDrawerLayout();
    }

    private void setDrawerLayout() {

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, binding.drawer, binding.toolbar.toolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {

                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        binding.drawer.setDrawerListener(actionBarDrawerToggle);


        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    private void setDefaultFragment() {
        if (fragment == null) {
            moveToMainFragment();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void moveToMainFragment() {
        fragment = new MainFrag();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item != null && item.getItemId() == android.R.id.home) {
            if (binding.drawer.isDrawerOpen(Gravity.RIGHT)) {
                binding.drawer.closeDrawer(Gravity.RIGHT);
            } else {
                binding.drawer.openDrawer(Gravity.RIGHT);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Snackbar.make(binding.drawer, "من فضلك أضغط مرة اخرى للخروج", Snackbar.LENGTH_LONG).show();
        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }
}
