package com.cenah.efficentlearning.zpages.student.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.cenah.efficentlearning.R;
import com.cenah.efficentlearning.ProfileFragment;
import com.cenah.efficentlearning.notificationService.ServiceNotifications;
import com.cenah.efficentlearning.zpages.student.fragments.StudentMainPageFragment;
import com.cenah.efficentlearning.zpages.student.fragments.StudentNotificationFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StudentHomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        Intent serviceIntent = new Intent(getApplicationContext(), ServiceNotifications.class);
        startService(serviceIntent);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.inflateMenu(R.menu.nav_student_items);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        fragment = new StudentMainPageFragment();
                        setFragment(fragment);
                        return true;

                    case R.id.profile:
                        fragment = new ProfileFragment();
                        setFragment(fragment);

                        return true;

                    case R.id.notificatons:
                        fragment = new StudentNotificationFragment();
                        setFragment(fragment);
                        return true;

                    default:
                        return false;

                }


            }
        });

        fragment = new StudentMainPageFragment();
        setFragment(fragment);


    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_layout, fragment);
        // if(!isSatrt) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        int backStackEntryCount = fm.getBackStackEntryCount();
        if (backStackEntryCount == 1) {
            setNavigationVisibility(true);
        }
        super.onBackPressed();
    }

    public void setNavigationVisibility(boolean visible) {
        if (bottomNavigationView.isShown() && !visible) {
            bottomNavigationView.setVisibility(View.GONE);
        } else if (!bottomNavigationView.isShown() && visible) {
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
    }


}
