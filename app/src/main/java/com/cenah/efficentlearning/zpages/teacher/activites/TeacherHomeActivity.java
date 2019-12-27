package com.cenah.efficentlearning.zpages.teacher.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.cenah.efficentlearning.R;
import com.cenah.efficentlearning.ProfileFragment;
import com.cenah.efficentlearning.zpages.teacher.fragments.TeacherMainPageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TeacherHomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        bottomNavigationView.inflateMenu(R.menu.nav_admin_items);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        fragment = new TeacherMainPageFragment();
                        setFragment(fragment, false);
                        return true;

                    case R.id.profile:
                        fragment = new ProfileFragment();
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

        fragment = new TeacherMainPageFragment();
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
