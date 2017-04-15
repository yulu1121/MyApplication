package com.example.rececalviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<String> imageUrlList = new ArrayList<>();
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false);
//        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setItemAnimator(new SlideInUpAnimator());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
        imageUrlList.add("http://p4.qhimg.com/t01f18a5716574002ea.jpg");
        imageUrlList.add("http://img5.duitang.com/uploads/item/201407/29/20140729000849_V2Y5m.jpeg");
        imageUrlList.add("http://p4.qhimg.com/t01f18a5716574002ea.jpg");
        imageUrlList.add("http://img5.duitang.com/uploads/item/201407/29/20140729000849_V2Y5m.jpeg");
        imageUrlList.add("http://img5.duitang.com/uploads/item/201407/29/20140729000849_V2Y5m.jpeg");
        imageUrlList.add("http://p4.qhimg.com/t01f18a5716574002ea.jpg");
        imageUrlList.add("http://img5.duitang.com/uploads/item/201407/29/20140729000849_V2Y5m.jpeg");
        imageUrlList.add("http://p4.qhimg.com/t01f18a5716574002ea.jpg");
        imageUrlList.add("http://img5.duitang.com/uploads/item/201407/29/20140729000849_V2Y5m.jpeg");
        imageUrlList.add("http://p4.qhimg.com/t01f18a5716574002ea.jpg");
        imageUrlList.add("http://img5.duitang.com/uploads/item/201407/29/20140729000849_V2Y5m.jpeg");
        imageUrlList.add("http://p4.qhimg.com/t01f18a5716574002ea.jpg");
        imageUrlList.add("http://img5.duitang.com/uploads/item/201407/29/20140729000849_V2Y5m.jpeg");
        imageUrlList.add("http://p4.qhimg.com/t01f18a5716574002ea.jpg");
        imageUrlList.add("http://img5.duitang.com/uploads/item/201407/29/20140729000849_V2Y5m.jpeg");
//        adapter.notifyDataSetChanged();
    }
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        View view;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this, "haha", Toast.LENGTH_SHORT).show();
        }
    }
    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_view, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Picasso.with(MainActivity.this).load(imageUrlList.get(position)).into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return null==imageUrlList?0:imageUrlList.size();
        }
        public void remove(int position) {
            imageUrlList.remove(position);
            notifyItemRemoved(position);
        }

        public void add(String text, int position) {
            imageUrlList.add(position, text);
            notifyItemInserted(position);
        }
    }
}
