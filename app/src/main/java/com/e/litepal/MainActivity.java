package com.e.litepal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private List<Map<String,String>> contentList=new ArrayList<Map<String, String>>();
    private TextView textView;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.tv_nowords);
        imageView=findViewById(R.id.im_d);
        imageView.getBackground().setAlpha(50);
        this.setTitle("备忘录");

    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.add,menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        Intent intent = new Intent(MainActivity.this,AddContextActivity.class);
        startActivity(intent);
        Toast.makeText(this,"开始创建",Toast.LENGTH_SHORT).show();
        return true;
    }
    @Override
    protected void onStart() {//每次活动有不可见变可见时调用
        super.onStart();
        contentList.clear();//清空list子项数据，实现刷新list
        initContent();//初始化
        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.rcv_1);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        LinearAdapter adapter=new LinearAdapter(contentList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if(contentList.size()==0){
            textView.setVisibility(textView.VISIBLE);
            imageView.setVisibility(imageView.VISIBLE);
        }
        else {
            textView.setVisibility(textView.GONE);
            imageView.setVisibility(imageView.GONE);
        }
    }
    public void initContent(){//创建
        List<UserInfo> ticklers= LitePal.order("id desc").find(UserInfo.class);
        for(UserInfo tickler:ticklers){
            String content=tickler.getContent();
            String time=tickler.getTime();
            Map<String,String> map=new HashMap<String, String>();
            map.put("content",content);
            map.put("time",time);
            contentList.add(map);
        }
    }
}

