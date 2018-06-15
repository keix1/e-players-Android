package com.mimamori.mimaco;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.support.constraint.Constraints.TAG;

public class LocationManager {
    String TAG = LocationManager.class.getName();
    String baseUrl = "http://mimaco.herokuapp.com";
    String sl = "/";


    public List<List<Double>> getHeatMapTest(double latitudeSeed, double longitudeSeed) {
        List<List<Double>> resultList = new ArrayList<List<Double>>();
        int num = 10;
        int minus = -1;
        for(int i=0; i<num; i++) {
            List<Double> list = new ArrayList<>();
            list.add((latitudeSeed + Math.random()/500) * minus);
            list.add((longitudeSeed + Math.random()/500) * minus);
            resultList.add(list);
            minus *= -1;
        }
        return resultList;
    }

    public List<List<Double>> getHeatMap(String username, double latitude, double longitude) {
        String appendUrl = "user_location";
        String queryUrl = baseUrl + sl + appendUrl;
        String result = "";
        JSONObject resultJSONObject = new JSONObject();
        HttpClient httpClient = new HttpClient();
        JSONArray resultJSONArray = new JSONArray();
        List<List<Double>> resultList = new ArrayList<List<Double>>();

        try {
            result = httpClient.execute(Util.getMethodName(), queryUrl, username, String.valueOf(latitude), String.valueOf(longitude)).get();
            resultJSONArray = new JSONArray(result);
            for(int i=0; i<resultJSONArray.length(); i++) {
                resultJSONObject = resultJSONArray.getJSONObject(i);

                double resultLatitude = Double.parseDouble((String)resultJSONObject.get("latitude"));
                double resultLongitude = Double.parseDouble((String)resultJSONObject.get("longitude"));
                Log.d("getHeatMap", resultLatitude + ", " + resultLongitude);
                List<Double> list = new ArrayList<>();
                list.add(resultLatitude);
                list.add(resultLongitude);
                resultList.add(list);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return resultList;
    }
}
