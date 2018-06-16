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
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PointManager {
    String TAG = PointManager.class.getName();
    String baseUrl = "http://mimaco.herokuapp.com";
    String sl = "/";

    PointManager() {
    }

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

    public int getPoint(String username) {
        JSONObject jsonObject = getUser(username);
        int point = -1;
        try {
            point = (int)jsonObject.get("point");
        } catch(Exception e) {
            Log.e(Util.getMethodName(), e.getMessage());
        }
        return point;
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

    public JSONObject findWatchedUser(String username, int major, int minor, double latitude, double longitude) {
        String appendUrl = "user";
        String queryUrl = baseUrl + sl + appendUrl + sl + username;
        String result = "";
        JSONObject resultJSONObject = new JSONObject();
        HttpClient httpClient = new HttpClient();

        try {
            result = httpClient.execute(Util.getMethodName(), queryUrl, username, String.valueOf(major), String.valueOf(minor), String.valueOf(latitude), String.valueOf(longitude)).get();
//            resultJSONObject = new JSONObject(result);
            Log.d(TAG, result);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        return resultJSONObject;
    }
}

