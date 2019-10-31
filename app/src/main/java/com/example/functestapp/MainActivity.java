package com.example.functestapp;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.functestapp.Fragment_First;
import com.example.functestapp.Fragment_Second;
import com.example.functestapp.Fragment_Third;

import com.example.functestapp.WebCommunication;
/**
 * Create by Yin on 2019/10/11
 */

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private Fragment_Home Home;
    private Fragment_Third Third;
    private Fragment_Second Second;
    private Fragment_First First;
    private Fragment[] fragments;
    private int currentFragment = 0;

    private FrameLayout mainFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        Home = new Fragment_Home();
        First = new Fragment_First();
        Second = new Fragment_Second();
        Third = new Fragment_Third();
        fragments = new Fragment[]{Home,First,Second,Third};
        mainFrame = (FrameLayout) findViewById(R.id.mainframe);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.mainframe, fragments[0]);
        transaction.show(fragments[0]).commitAllowingStateLoss();

//        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_Home:
                    if(currentFragment != 0){
                        switchFragment(currentFragment,0);
                        currentFragment = 0;
                    }
                    return true;
                case R.id.navigation_First:
                    if(currentFragment != 1){
                        switchFragment(currentFragment,1);
                        currentFragment = 1;
                    }
                    return true;
                case R.id.navigation_Second:
                    if(currentFragment != 2){
                        switchFragment(currentFragment,2);
                        currentFragment = 2;
                    }
                    return true;
                case R.id.navigation_Third:
                    if(currentFragment != 3){
                        switchFragment(currentFragment,3);
                        currentFragment = 3;
                    }
                    return true;
            }
            return false;
        }
    };

    private void switchFragment(int currentFragment, int index){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[currentFragment]);
        if (fragments[index].isAdded() == false) {
            transaction.add(R.id.mainframe, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }
}
