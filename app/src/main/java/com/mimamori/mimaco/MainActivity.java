package com.mimamori.mimaco;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import otoshimono.com.lost.mamorio.sdk.MamorioSDK;
import otoshimono.com.lost.mamorio.sdk.User;
import otoshimono.com.lost.mamorio.sdk.Error;
import otoshimono.com.lost.mamorio.sdk.Mamorio;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "mimaco!";
    private KEYS keys = new KEYS();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.i("LifeCycle", "onStart");

        MamorioSDK.setUp(getBaseContext(), "APP_TOKEN");
        User.signIn(keys.getUser(), keys.getPassword(), new User.UserCallback() {
            @Override
            public void onSuccess(User user) {
                //アカウント登録成功時の処理
                Log.d(TAG,"ユーザーのサインインに成功しました");
            }

            @Override
            public void onError(Error error) {
                //アカウント登録失敗時の処理
                Log.d(TAG,"ユーザー登録に失敗しました。エラーメッセージ：" + error.getMessage());
            }
        });


    }

}
