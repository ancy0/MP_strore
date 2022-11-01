package com.cookandroid.task_20213024;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        GridViewAdapter adapter = new GridViewAdapter();

        //Adapter 안에 상품정보 넣기
        adapter.addItem(new StoreItem("해바라기씨", R.drawable.sunflowerseed));
        adapter.addItem(new StoreItem("호박씨", R.drawable.pumpkinseed));
        adapter.addItem(new StoreItem("건조 옥수수", R.drawable.corn));
        adapter.addItem(new StoreItem("귀리", R.drawable.oats));
        adapter.addItem(new StoreItem("쳇바퀴", R.drawable.treadmill));

        //그리드뷰에 Adapter 설정
        gridview.setAdapter(adapter);

        //회원정보 버튼 클릭
        Button btnViewUserData = (Button) findViewById(R.id.btnViewUserData);
        btnViewUserData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 로그인 여부 확인
                if(getIntent().hasExtra("userData")){
                    ArrayList<String> userData = (ArrayList<String>) getIntent().getSerializableExtra("userData");
                    //정보 보여주기
                    Log.v("test",userData.get(1)); //데이터 잘 받아왔는지 확인함
                    AlertDialog.Builder ad = new AlertDialog.Builder(ViewItemActivity.this);
                    ad.setTitle("회원정보");
                    ad.setMessage(String.format("아이디: %s %n비밀번호: %s %n이름: %s %n전화번호: %s %n주소: %s", userData.get(0), userData.get(1), userData.get(2), userData.get(3),userData.get(4)));

                    ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "확인클릭", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                    ad.show();
                }else{
                    AlertDialog.Builder dd = new AlertDialog.Builder(ViewItemActivity.this);
                    dd.setTitle("계정이 있나요?");
                    dd.setMessage("계정이 없다면 회원가입을 할 수 있어요.");

                    dd.setPositiveButton("회원가입", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "회원가입 하러가기", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                            dialog.dismiss();
                            startActivity(intent);
                        }
                    });

                    dd.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dd.show();
                }
            }
        });
    }

    class GridViewAdapter extends BaseAdapter {
        ArrayList<StoreItem> items = new ArrayList<StoreItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(StoreItem item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            final Context context = viewGroup.getContext();
            final StoreItem item = items.get(position);

            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.activity_viewitem, viewGroup, false);


                ImageView itemImg = (ImageView) convertView.findViewById(R.id.itemImg);
                TextView itemName = (TextView) convertView.findViewById(R.id.itemName);

                itemImg.setImageResource(item.getImgResId());
                itemName.setText(item.getName());

            } else {
                View view = new View(context);
                view = (View) convertView;
            }
            //뷰 객체를 반환
            return convertView;
        }
    }
}
