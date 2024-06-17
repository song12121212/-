package com.example.androidfinal_project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivityMain2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // FrameLayout에 StartFragment 추가
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new StartFragment())
                    .commit();
        }

        // BottomNavigationView 설정
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            // 클릭된 아이템에 따라 대응하는 Fragment를 선택
            int itemId = item.getItemId();
            if (itemId == R.id.start) {
                selectedFragment = new StartFragment();
            } else if (itemId == R.id.diary) {
                selectedFragment = new FragmentData();
            } else if (itemId == R.id.history) {
                selectedFragment = new FragmentHistory();
            } else if (itemId == R.id.timer) {
                selectedFragment = new FragmentMap();
            }

            // 선택된 Fragment를 화면에 표시
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, selectedFragment)
                        .commit();
                return true;
            }
            return false;
        });
    }
}