package com.cenah.efficentlearning.student.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.cenah.efficentlearning.R;
import com.cenah.efficentlearning.admin.fragments.AdminMainPageFragment;
import com.cenah.efficentlearning.admin.fragments.AdminProfileFragment;
import com.cenah.efficentlearning.student.fragments.StudentMainPageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StudentHomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        bottomNavigationView.inflateMenu(R.menu.nav_admin_items);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        fragment = new StudentMainPageFragment();
                        setFragment(fragment, false);
                        return true;

                    case R.id.profile:
                        fragment = new AdminProfileFragment();
                        setFragment(fragment, false);

                        return true;

              /*      case R.id.notificatons:
                        androidx.fragment.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_layout, new NotificationFragment());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        return true;*/

                    default:
                        return false;

                }


            }
        });

        fragment = new AdminMainPageFragment();
        setFragment(fragment, true);


    }

    public void setFragment(Fragment fragment,boolean isSatrt) {
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
