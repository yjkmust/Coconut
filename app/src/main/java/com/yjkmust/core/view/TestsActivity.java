package com.yjkmust.core.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.yjkmust.coconut.R;

public class TestsActivity extends AppCompatActivity {
    private FragmentTransaction ft;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);
        initFragment();
        FragmentManager fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment).commit();
    }
    private void initFragment(){
         fragment = TestFragment.newIntent();
    }

}
