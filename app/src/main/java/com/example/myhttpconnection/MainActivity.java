package com.example.myhttpconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.myhttpconnection.models.Person;
import com.example.myhttpconnection.models.User;
import com.example.myhttpconnection.models.response.ResTodo;
import com.example.myhttpconnection.service.UserService;
import com.example.myhttpconnection.utils.HttpClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "TAG";
    ArrayList<Person> personArrayList;
    ArrayList<ResTodo> todos;
    TextView textView;
    HttpClient httpClient;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserService userService = new UserService();
        user = userService.read("10");

    }



    private void requestTodos() {
        new Thread(() -> {
            try {
                todos = httpClient.todos("/todos");
                Log.d(TAG, "todos : " + todos.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

    private void savePost() {
        new Thread(() -> {
            try {
                httpClient.posts("/posts");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    /**
     * JSON Object
     * JSON Array
     */
    private void testCodeForJson() {
        // 1. 하나의 jsonObject 만들기
        // 2. JSON Array 만들기

        // JSON Object 형태로 만들어 주는 녀석
        // { key : value }
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        try {
            jsonObject.put("이름", "홍길동");
            jsonObject.put("나이", 30);
            jsonObject.put("직업", "개발자");
            jsonObject.put("취미", "게임");
            jsonObject.put("결혼여부", false);


            jsonArray.put(jsonObject);
            jsonArray.put(jsonObject);
            jsonArray.put(jsonObject);
//            Log.d(TAG, jsonArray.toString());


            // 사용해 보기
            // 1. JSON OBJECT 파싱 하기
//            Log.d(TAG, jsonObject.toString());
//            Person person = new Gson().fromJson(jsonObject.toString(), Person.class);
//            Log.d(TAG, person.get이름());
//            Log.d(TAG, person.get나이() + "");

            // 2. JSON Array 파싱 하기 !
//            Person[] people = new Gson().fromJson(jsonArray.toString(), Person[].class);
//            Log.d(TAG, Arrays.toString(people));

            // 3. ArrayList 로 파싱하는 방법
            Type listType = new TypeToken<ArrayList<Person>>(){}.getType();
            personArrayList = new Gson().fromJson(jsonArray.toString(), listType);
            Log.d(TAG, personArrayList.get(0).toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    /**
     * 자바, 안드로이에서 통신프로토을을 활용해서
     * 서버측에 데이터를 요청해서 응답 (HTTP)
     */
    private void httpConnectionTestCode() {

        // 익명클래스로 쓰래드 만들기
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 1. 준비물 URL 객체 필요
                String baseUrl = "https://jsonplaceholder.typicode.com/";
                String endPoint = "todos/1";
                try {
                    // Http Request Message 생성 (시작줄 + http header)
                    URL url = new URL(baseUrl + endPoint);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET"); // GET, POST, PUT, DELETE

                    // 결과값 받기
                    // 1. 응답 코드 받기
                    // 상태코드 1xx(진행중), 2xx(성공), 3xx(리다렉트), 4xx(실패: 잘못된요청), 500(연산오류)
                    // GET 성공 ---> 200 호출
                    // POST 성공 ---> 201 호출
                    Log.d("TAG", "상태코드 받아 보기 " + conn.getResponseCode());

                    // 응답 바디 데이터 받아 보기
                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(conn.getInputStream())
                        );

                        // Dto
                        ResTodo todo = new Gson().fromJson(reader, ResTodo.class);
                        Log.d(TAG, todo.toString()); // -> 주소값 :: --> 오버라이드 처리
                        Log.d(TAG, todo.getTitle());
                        Log.d(TAG, todo.getBody());

//                        String line = null;
//                        StringBuffer sb = new StringBuffer();
//                        while ( (line = reader.readLine()) != null  ) {
//                            sb.append(line +"\n");
//                        }
//                        Log.d(TAG, sb.toString());
//                        String responseResult = sb.toString();
//                        String key = responseResult.substring(3, 10);
//                        String value = responseResult.substring(11, 14);
//                        Log.d(TAG, "key : " + key);
//                        Log.d(TAG, "value : " + value);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}










