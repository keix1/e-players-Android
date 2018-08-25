package com.mimamori.mimaco;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;


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

    public JSONObject findWatchedUser(final String username, final int major, final int minor, final double latitude, final double longitude) {
        Log.d(TAG, "findWatchedUser");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String appendUrl = "user";
                    String queryUrl = baseUrl + sl + appendUrl + sl + username;
                    JSONObject resultJSONObject = new JSONObject();

                    HttpClient httpClient = new HttpClient();
                    String result = "";
                    result = httpClient.execute("findWatchedUser", queryUrl, username, String.valueOf(major), String.valueOf(minor), String.valueOf(latitude), String.valueOf(longitude)).get();
//            resultJSONObject = new JSONObject(result);
                    Log.d(TAG, result);
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }
        }).start();

        return new JSONObject();
    }
}

