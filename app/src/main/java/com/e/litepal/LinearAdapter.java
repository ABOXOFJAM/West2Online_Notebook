package com.e.litepal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class LinearAdapter extends RecyclerView.Adapter<LinearAdapter.ViewHolder> {
    private List<Map<String,String>> mcontext;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View ticklerView;
        TextView contentText;
        TextView showTime;
        public ViewHolder(@NonNull View itemView) {//item的控件声明
            super(itemView);
            ticklerView=itemView;
            contentText=itemView.findViewById(R.id.tv_1);
            showTime=itemView.findViewById(R.id.tm_tv);
        }
    }
    public LinearAdapter(List<Map<String,String>> context){
        this.mcontext=context;//传入表
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        //ViewHolder的创建，即绑定特点的xml
        //将layout_recyler_item绑定到mcontext上
        //需要在这里添加点击事件

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recyler_item,parent,false);
        //这是RecyclerView的每一项
        final ViewHolder holder =new ViewHolder(view);//定义一个ViewHolder实现点击事件
        holder.ticklerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position =holder.getAdapterPosition();
                String content=mcontext.get(position).get("content");//通过Position来找内容
                String time=mcontext.get(position).get("time");
                Intent intent = new Intent(parent.getContext(),AddContextActivity.class);
                intent.putExtra(AddContextActivity.CONTENT,content);//传输content到CONTENT中
                intent.putExtra(AddContextActivity.TIME,time);
                parent.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.contentText.setText(mcontext.get(position).get("content"));
        holder.showTime.setText(mcontext.get(position).get("time"));
}
    @Override
    public int getItemCount() {
        //整个表的长度
        return mcontext.size();
    }
}
