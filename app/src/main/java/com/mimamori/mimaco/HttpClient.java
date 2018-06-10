package com.mimamori.mimaco;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpClient extends AsyncTask<String, Void, String> {

    String TAG = HttpClient.class.getName();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // doInBackground前処理
    }

    @Override
    protected String doInBackground(String... params) {
        String result = "";
        String method = params[0];
        String url = params[1];
        try {
            switch(method) {
                case "getUser":
                    result = getUser(url);
                    break;
                case "getAllUser":
                    result = getAllUser(url);
                    break;
                case "addPoint":
                    String point = params[2];
                    result = addPoint(url, point);
                    break;
                case "findWatchedUser":
                    String username = params[2];
                    String major = params[3];
                    String minor = params[4];
                    String latitude = params[5];
                    String longitude = params[6];
                    result = findWatchedUser(url, username, major, minor, latitude, longitude);
                    break;
            }
        } catch (Exception e) {
                Log.e(TAG, e.toString());
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // doInBackground後処理
    }


    private String getAllUser(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        Log.d(TAG, result);
        return result;
    }

    private String getUser(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        Log.d(TAG, result);
        return result;
    }

    private String addPoint(String url, String point) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType MIMEType= MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create (MIMEType,"{\"point_increment\": " + point + "}");
        Request request = new Request.Builder()
                .url(url)
                .patch(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        Log.d(TAG, result);
        return result;
    }

    private String findWatchedUser(String url, String username, String major, String minor, String latitude, String longitude) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType MIMEType= MediaType.parse("application/json; charset=utf-8");

        String json = "{\"username\": \"" + username + "\","
                + "\"major\": " + major + ","
                + "\"minor\": " + minor + ","
                + "\"latitude\": " + latitude + ","
                + "\"longitude\": " + longitude
                + "}";
        RequestBody requestBody = RequestBody.create (MIMEType, json);

        Request request = new Request.Builder()
                .url(url)
                .patch(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        Log.d(TAG, result);
        return result;
    }
}