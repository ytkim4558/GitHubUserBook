package com.example.ytkim.githubuserbook;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    TabHost tabHost;
    public List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userList = new ArrayList<>();

        initializeTab();
    }

    void initializeTab() {
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("1").setContent(R.id.tab1).setIndicator("User");

        TabHost.TabSpec tab2 = tabHost.newTabSpec("2").setContent(R.id.tab2).setIndicator("Like User");

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
    }

    //타이틀바 맨 우측에 나타나는 버튼
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        // 검색 버튼 클릭했을 때 searchview 길이 꽉차게 늘려주기
        SearchView searchView = (SearchView)menu.findItem(R.id.action_search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        // 검색 버튼 클릭했을 때 searchview 에 힌트 추가
        searchView.setQueryHint("사람이름으로 검색");
        //검색 리스너 추가
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            //검색어 입력시 이벤트 제어
            @Override
            public boolean onQueryTextChange(String s) {
//                Toast.makeText(getApplicationContext(), "입력중입니다.", Toast.LENGTH_SHORT).show();
                return false;
            }
            //검색어 완료시 이벤트 제어 --> 간단하게 Toast 메세지 출력으로
            @Override
            public boolean onQueryTextSubmit(String s) {
//                Toast.makeText(getApplicationContext(), "검색을 완료했습니다.", Toast.LENGTH_SHORT).show();
                userSearchRequest(s);
                return false;
            }
        });

        return true;
    }

    void userSearchRequest(final String userName) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.github.com/search/users";

        // https url 요청하기 위한 작업

        //유저 정보를 Github 서버에 요청한다.
        final StringRequest strReq = new StringRequest(Request.Method.GET, "https://api.github.com/search/users?q=" + userName,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response);
                        //Calling method parseUserList to parse the json response
                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            String userList = jsonObject.getString("items");
                            parseUserList(new JSONArray(userList));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Hiding the progressbar
//                        mSwipeRefresh.setRefreshing(false);
//                        if (progressBar.getVisibility() == View.VISIBLE) {
//                            progressBar.setVisibility(View.GONE);
//                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

//                        progressBar.setVisibility(View.GONE);
                        //If an error occurs that means end of the list has reached
                        if (getContext() != null) {
                            if (error instanceof TimeoutError) {
                                Log.e(TAG, "Login Error: 서버가 응답하지 않습니다." + error.getMessage());
                                VolleyLog.e(TAG, error.getMessage());
                                showAlertDialogMessage(error.getMessage());
                            } else if (error instanceof ServerError) {
                                Log.e(TAG, "서버 에러래" + error.getMessage());
                                VolleyLog.e(TAG, error.getMessage());
                                showAlertDialogMessage("서버 에러" + error.getMessage());
                            } else {
                                Log.e(TAG, error.getMessage());
                                VolleyLog.e(TAG, error.getMessage());
                                showAlertDialogMessage(error.getMessage());
                            }
                        }
                    }
                });
        queue.add(strReq);
    }

    void showAlertDialogMessage(String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();   // 닫기
            }
        });
        alert.setMessage(message);
        alert.show();
    }

    void parseUserList(JSONArray userListJSONArray) {
        for (int i = 0; i < userListJSONArray.length(); ++i) {
            //유저 객체 생성
            User user = null;
            JSONObject jsonObject = null;
            try {
                // json 가져옴
                jsonObject = userListJSONArray.getJSONObject(i);
                user = new User();
                user.avatarURL = jsonObject.getString("avatar_url");
                user.login = jsonObject.getString("login");
                user.id = jsonObject.getInt("id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // user 객체를 리스트에 삽입
            if (user != null) {
                userList.add(user);
            }
        }
    }
}
