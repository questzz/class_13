package com.example.myhttpconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myhttpconnection.service.TodoService;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        TodoService todoService = new TodoService();
        //Todo todo = todoService.read();
        // 화면 만드는 부분 집중
    }
}