package com.e.litepal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.e.litepal.R.menu.*;

public class AddContextActivity extends AppCompatActivity {
    public static  String CONTENT="content";
    public static  String TIME="time";
    private String time;
    private EditText content;
    private TextView showTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_context);
        content =findViewById(R.id.show_content);
        showTime=findViewById(R.id.show_time);
        Intent intent =getIntent();
        time=intent.getStringExtra(TIME);//接受来自适配器的TIME
        String showContent=intent.getStringExtra(CONTENT);
        showTime.setText(time);
        this.setTitle("添加内容");
        content.setText(showContent);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_content:
                //判断当前操作是新增还是修改
                if(time!=null){
                    String inputText=content.getText().toString();//将EditText内的内容调用toString返回
                    UserInfo tickler=new UserInfo();
                    tickler.setContent(inputText);
                    tickler.updateAll("time=?",time);
                    Toast.makeText(this,"修改成功", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                }else {
                    //取得新增记录时的系统时间
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                    //创建一个时间样式
                    Date date=new Date(System.currentTimeMillis());
                    String inputText=content.getText().toString();
                    UserInfo tickler=new UserInfo();
                    tickler.setContent(inputText);
                    tickler.setTime(simpleDateFormat.format(date));
                    tickler.save();
                    Toast.makeText(this,"保存成功", Toast.LENGTH_SHORT).show();
                    finish();//操作完成结束当前活动
                    break;}

            case R.id.delete_content:

                //删除操作
                String deleteContent=content.getText().toString();
                LitePal.deleteAll(UserInfo.class,"content=?",deleteContent);
                Toast.makeText(this,"删除成功", Toast.LENGTH_SHORT).show();
                finish();

                break;

            case android.R.id.home://没有这个会报错
                onBackPressed();
                return true;
            default:
        }
        return true;
    }
}


