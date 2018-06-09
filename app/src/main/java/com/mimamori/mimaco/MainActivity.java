package com.mimamori.mimaco;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import otoshimono.com.lost.mamorio.sdk.Mamorio;
import otoshimono.com.lost.mamorio.sdk.MamorioSDK;
import otoshimono.com.lost.mamorio.sdk.User;
import otoshimono.com.lost.mamorio.sdk.Error;


public class MainActivity extends AppCompatActivity implements MimamorioFragment.OnFragmentInteractionListener {

    private static String TAG = MainActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MimamorioFragment mimamorioFragment = new MimamorioFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentMimarioContainer, mimamorioFragment);
        transaction.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
