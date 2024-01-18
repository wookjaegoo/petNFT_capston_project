package com.example.capstonrestorego;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.capstonrestorego.Fragment.HomeFragment;
import com.example.capstonrestorego.Model.UserInfo;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private static Intent intent1 = new Intent();
    private Fragment selectFragment = null;
    private Bundle bundle = new Bundle();
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationBarView navigationBarView = findViewById(R.id.bottom_navigation);
        userInfo = UserInfo.getInstance();

        Fragment homeFragment = new HomeFragment();

        if (intent1 != null) {
            bundle.putString("userinfo", userInfo.getUseradd());
            homeFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }

        navigationBarView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    Fragment homeFragment1 = new HomeFragment();
                    homeFragment1.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment1).commit();
                    return true;
                case R.id.nav_mint:
                    selectFragment = null;
                    Intent intent = new Intent(MainActivity.this, PostNftActivity.class);
                    startActivity(intent);
            }

            if (selectFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectFragment).commit();
            }

            return false;
        });
    }
}
