package com.example.dell.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    ViewPager viewPager;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fragmentManager=getSupportFragmentManager();
        viewPager=(ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(new adapter(fragmentManager));
    }
}

class adapter extends FragmentStatePagerAdapter {
    public adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
       if(position==0)
           fragment=new SigninFragment();
        if(position==1)
            fragment=new SignupFragment();
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        if(position==0)
            return "Sign in";
        else
            return "Sign up";

    }
}
