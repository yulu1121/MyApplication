package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.selfview.SwipLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private List<Integer> integers  = new ArrayList<>();
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.my_listView);
    }

    private void initData(){
        for (int i = 0; i < 20 ; i++) {
            integers.add(i);
        }
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
    }
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return null==integers?0:integers.size();
        }

        @Override
        public Object getItem(int position) {
            return integers.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            View view = convertView;
            final ViewHolder viewHolder;
            if(null==view){
                view = LayoutInflater.from(MainActivity.this).inflate(R.layout.swiplayout_item,null);
                viewHolder = new ViewHolder(view);
                SwipLayout.addSwipeView(viewHolder.swipLayout);
            }else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.idText.setText(String.valueOf(integers.get(position)));
            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    integers.remove(position);
                    adapter.notifyDataSetChanged();
                    SwipLayout.removeSwipeView(viewHolder.swipLayout);
                }
            });
            return view;
        }
        class ViewHolder {
            TextView idText;
            SwipLayout swipLayout;
            ImageView edit;
            ImageView delete;
             ViewHolder(View view){
                idText = (TextView) view.findViewById(R.id.name);
                edit = (ImageView) view.findViewById(R.id.edit);
                delete = (ImageView) view.findViewById(R.id.delete);
                swipLayout = (SwipLayout) view.findViewById(R.id.my_swiplayout);
                view.setTag(this);
            }
        }
    }
}
