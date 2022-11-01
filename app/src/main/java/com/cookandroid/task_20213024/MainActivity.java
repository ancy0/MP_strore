package com.cookandroid.task_20213024;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText editId, editPwd;

    private static final String AllUserId = "allUserID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editId = (EditText) findViewById(R.id.loginId);
        editPwd = (EditText) findViewById(R.id.loginPwd);

        //저장되어 있는 데이터 가져오기
        ArrayList<String> checkIdList = getStringArrayPref(getApplicationContext(),AllUserId);

        //로그인 버튼
        Button btnLoginActivity = (Button) findViewById(R.id.loginBtn);
        btnLoginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 아이디 존재 확인
                String inputId = editId.getText().toString().trim();
                if(!checkIdList.contains(inputId)) {
                    Toast.makeText(getApplicationContext(), "존재하지 않는 아이디입니다.", Toast.LENGTH_SHORT).show();
                    editId.setText(null);
                    editPwd.setText(null);
                    return;
                }
                String inputPwd = editPwd.getText().toString().trim();
                ArrayList<String> userDataList = getStringArrayPref(getApplicationContext(),inputId);
                Log.v("test",userDataList.get(1));
                if(inputPwd.equals(userDataList.get(1))){
                    //로그인 성공 -> 페이지 이동
                    Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ViewItemActivity.class);
                    intent.putExtra("isLogin", true);
                    intent.putExtra("userData",userDataList);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                    editPwd.setText(null);
                }

            }
        });

        // 회원가입 버튼 -> 회원가입 페이지로 이동
        Button btnJoinActivity = (Button) findViewById(R.id.btnJoinActivity);
        btnJoinActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });

        // 로그인 없이 상품둘러보기
        Button btnViewItemActivity = (Button) findViewById(R.id.btnViewItemActivity);
        btnViewItemActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewItemActivity.class);
                intent.putExtra("isLogin", false);
                startActivity(intent);
            }
        });

    }
    private ArrayList getStringArrayPref(Context context, String key) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(key, null);
        ArrayList urls = new ArrayList();

        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);

                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }
}