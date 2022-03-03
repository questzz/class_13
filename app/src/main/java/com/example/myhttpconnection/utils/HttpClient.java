package com.example.myhttpconnection.utils;

import android.util.Log;

import com.example.myhttpconnection.models.request.ReqPost;
import com.example.myhttpconnection.models.response.ResPost;
import com.example.myhttpconnection.models.response.ResTodo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HttpClient {

    private static HttpClient httpClient;

    private static final String baseURL = "https://jsonplaceholder.typicode.com";
    private HttpURLConnection conn;
    public final static String GET = "GET";
    public final static String POST = "POST";
    public final static String PUT = "PUT";
    public final static String DELETE = "DELETE";

    // 싱글톤 패턴
    private HttpClient() {
    }

    public static HttpClient getInstance() {
        if (httpClient == null) {
            httpClient = new HttpClient();
        }
        return httpClient;
    }

    // todos
    public ArrayList<ResTodo> todos(String path) throws Exception {
        ArrayList<ResTodo> resTodos = new ArrayList<>();
        URL url = new URL(baseURL + path);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(GET);
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            Type listType = new TypeToken<ArrayList<ResTodo>>() {
            }.getType();
            resTodos = new Gson().fromJson(reader, listType);
        }
        conn.disconnect();
        return resTodos;
    }

    public void posts(String path) throws Exception {

        URL url = new URL(baseURL + path);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(POST);
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoInput(true);
        conn.setDoInput(true);
        conn.setConnectTimeout(3000);

        OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
        ReqPost reqPost = new ReqPost("하이", "응답확인", 1);
        String jsonObject = new Gson().toJson(reqPost);
        writer.write(jsonObject);
        writer.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        ResPost resPost = new Gson().fromJson(reader, ResPost.class);
        Log.d("TAG", resPost.toString());
        Log.d("TAG", conn.getResponseCode() + "");
        conn.disconnect();
    }

}
