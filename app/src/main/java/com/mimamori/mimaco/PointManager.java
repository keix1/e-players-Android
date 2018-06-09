package com.mimamori.mimaco;

import android.content.Context;
import android.graphics.Point;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PointManager {
    String TAG = PointManager.class.getName();

    PointManager() {
    }

    String baseUrl = "http://172.22.1.80";
    String sl = "/";

    public JSONArray getAllUser() {
        String appendUrl = "user";
        String queryUrl = baseUrl + sl + appendUrl;
        String result = "";
        JSONArray resultJSONArray = new JSONArray();
        HttpClient httpClient = new HttpClient();

        try {
             result = httpClient.execute(Util.getMethodName(), queryUrl).get();
             resultJSONArray = new JSONArray(result);
             Log.d("JSONPARSE", resultJSONArray.getJSONObject(0).getString("username"));

        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return resultJSONArray;
    }

    public JSONObject getUser(String username) {
        String appendUrl = "user";
        String queryUrl = baseUrl + sl + appendUrl + sl + username;
        String result = "";
        JSONObject resultJSONObject = new JSONObject();
        HttpClient httpClient = new HttpClient();

        try {
            result = httpClient.execute(Util.getMethodName(), queryUrl).get();
            resultJSONObject = new JSONObject(result);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return resultJSONObject;
    }

    public JSONObject addPoint(String username, int point) {
        String appendUrl = "user";
        String queryUrl = baseUrl + sl + appendUrl + sl + username;
        String result = "";
        JSONObject resultJSONObject = new JSONObject();
        HttpClient httpClient = new HttpClient();

        try {
            result = httpClient.execute(Util.getMethodName(), queryUrl, String.valueOf(point)).get();
            resultJSONObject = new JSONObject(result);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        return resultJSONObject;
    }
}

