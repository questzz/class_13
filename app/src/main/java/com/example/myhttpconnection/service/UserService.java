package com.example.myhttpconnection.service;

import android.util.Log;

import com.example.myhttpconnection.models.User;
import com.example.myhttpconnection.repository.UserRepository;
import com.example.myhttpconnection.utils.HttpClient2;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class UserService implements UserRepository {

    private User user;

    @Override
    public void create(String id, String pw) {

    }

    @Override
    public User read(String id) {
        new Thread(() -> {
           HttpURLConnection conn = HttpClient2.getInstance().getConnection("/users/" + id, HttpClient2.GET);
            try {
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    user = new Gson().fromJson(reader, User.class);
                    Log.d("TAG", user.toString());

                } else {
                    Log.d("TAG", "연결실패 또는 오류");
                    Log.d("TAG", "status code : " + conn.getResponseCode());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                conn.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();

        return user;
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(String id) {

    }
}
