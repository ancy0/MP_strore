package com.cookandroid.task_20213024;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class JoinActivity extends AppCompatActivity {
    EditText editId, editPwd, editName, editAdress, editPNum;
    RadioGroup terms;
    RadioButton accept, disagree;

    boolean isAccept, isCheckPwd;
    boolean isCheckedID = false;

    ArrayList<String> idList = new ArrayList<>();
    ArrayList<String> userData = new ArrayList<>();
    private static final String AllUserId = "allUserID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        // 비밀번호 -> 숫자, 영어 가능(6~15자), 특수문자 제외
        final String PwdPattern = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{6,15}$";
        Pattern pwdPt = Pattern.compile(PwdPattern);

        editId = (EditText) findViewById(R.id.id);
        editPwd = (EditText) findViewById(R.id.pwd);
        editName = (EditText) findViewById(R.id.name);
        editPNum = (EditText) findViewById(R.id.pNum);
        editAdress = (EditText) findViewById(R.id.adress);
        terms = (RadioGroup) findViewById(R.id.terms);
        accept = (RadioButton) findViewById(R.id.accept);
        disagree = (RadioButton) findViewById(R.id.disagree);

        terms.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (accept.isChecked()) {
                    isAccept = true;
                } else if (disagree.isChecked()) {
                    isAccept = false;
                }
            }
        });

        editId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            // 아이디 입력값 변화 있으면 중복확인 여부 다시 false로
            @Override
            public void afterTextChanged(Editable editable) {
                isCheckedID = false;
            }
        });

        // 아이디 중복 확인
        Button btnCheckId = (Button) findViewById(R.id.btnCheckId);
        btnCheckId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputId = editId.getText().toString().trim();
                // 중복확인 후 입력한 아이디 수정한다면?
                if (isCheckedID) {
                    return; //이미 중복 확인됨.
                }

                // 아이디 입력 확인
                if(TextUtils.isEmpty(inputId)){
                    Toast.makeText(getApplicationContext(),"아이디를 입력해주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }

                // 제대로 입력되어 있으면 아이디 중복 확인
                ArrayList<String> list = getStringArrayPref(getApplicationContext(),AllUserId);
                if(list.contains(inputId)){
                    Toast.makeText(getApplicationContext(),"이미 존재하는 아이디입니다.",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    isCheckedID = true;
                    Toast.makeText(getApplicationContext(),"사용가능한 아이디입니다.",Toast.LENGTH_SHORT).show();
                }

            }        });

        // 회원가입 완료 버튼 클릭
        Button btnJoinSuccess = (Button) findViewById(R.id.btnJoinSuccess);
        btnJoinSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userId = editId.getText().toString().trim();
                String userPwd = editPwd.getText().toString().trim();
                String userName = editName.getText().toString().trim();
                String userPNum = editPNum.getText().toString().trim();
                String userAdress = editAdress.getText().toString().trim();

                //중복 체크 여부 확인
                if(!isCheckedID){
                    Toast.makeText(getApplicationContext(),"아이디 중복 확인 해주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }

                //회원가입 정보 입력확인, 비밀번호 조건 확인
                if(TextUtils.isEmpty(userPwd)||TextUtils.isEmpty(userName)||TextUtils.isEmpty(userPNum)||TextUtils.isEmpty(userAdress)){
                    Toast.makeText(getApplicationContext(),"모두 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    // 비밀번호 조건-영어, 숫자 (6~15), 특수문자 불가능
                    isCheckPwd = pwdPt.matcher(userPwd).matches();
                    if(!isCheckPwd){
                        Toast.makeText(getApplicationContext(),"비밀번호는 영어,숫자만으로 6~15자까지 가능합니다.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                // 약관 동의 확인 -> 확인되면 저장(아이디만, 모두) -> 로그인 페이지로 이동
                if(isAccept){
                    idList.add(userId);
                    userData.add(userId);
                    userData.add(userPwd);
                    userData.add(userName);
                    userData.add(userPNum);
                    userData.add(userAdress);
                    setStringArrayPref(getApplicationContext(),AllUserId, idList);
                    setStringArrayPref(getApplicationContext(),userId, userData);

                    Toast.makeText(getApplicationContext(),"회원가입 완료!",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"약관을 동의해주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }



    private void setStringArrayPref(Context context, String key, ArrayList<String> values) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray a = new JSONArray();

        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }

        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }

        editor.apply();
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