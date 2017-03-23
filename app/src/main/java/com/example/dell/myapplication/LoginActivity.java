package com.example.dell.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

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
/* /*added on 23/03/2017
 **intent to sign up activity

    TextView signUpScreen = (TextView) findViewById(R.id.signup);

// Listening to register new account link
        signUpScreen.setOnClickListener(new View.OnClickListener() {

public void onClick(View v) {
        // Switching to Register screen
        Intent i = new Intent(getApplicationContext(), SignupFragment.class);
        startActivity(i);
        }
        });
/* end */
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
