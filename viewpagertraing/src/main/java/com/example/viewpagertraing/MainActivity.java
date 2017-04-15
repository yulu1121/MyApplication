package com.example.viewpagertraing;

import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.viewpagertraing.utils.ImageLoader;
import com.example.viewpagertraing.utils.ImageLoadering;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String IMAGE_URLONE="http://www.1688wan.com/allimgs/img_iapp/201612/_1483101604058.jpg";
    public static final String IMAGE_URLTWO="http://www.1688wan.com/allimgs/img_iapp/201612/_1482480933498.jpg";
    public static final String IMAGE_URLTHREE="http://www.1688wan.com/allimgs/img_iapp/201612/_1481885008504.jpg";
    private ViewPager viewPager;
    private LayoutInflater inflater;
    private List<View> list;
    private List<String> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        inflater = LayoutInflater.from(this);
        viewPager = (ViewPager) findViewById(R.id.viewPager_training);
        imageList = new ArrayList<>();
        imageList.add(IMAGE_URLONE);
        imageList.add(IMAGE_URLTWO);
        imageList.add(IMAGE_URLTHREE);
        initViewPager(imageList);
        viewPager.setPageMargin(5);
        viewPager.setAdapter(new ViewPagerAdapter());
    }
    //初始化ViewPager
    private void initViewPager(List<String> listing){
        list = new ArrayList<>();
        for(String url:listing){
            View view = inflater.inflate(R.layout.image_item,null);
            final ImageView imageView = (ImageView) view.findViewById(R.id.image);
            imageView.setImageResource(R.mipmap.ic_launcher);
            imageView.setTag(url);
            ImageLoader.loadImage(url, new ImageLoader.ImageListener() {
                @Override
                public void ImageComplete(Bitmap bitMap, String Url) {
                    if(Url.equals(imageView.getTag())){
                        imageView.setImageBitmap(bitMap);
                    }
                }
            });
            list.add(view);
        }
    }
    //设置适配器
    class ViewPagerAdapter extends PagerAdapter{
        @Override
        public float getPageWidth(int position) {
            return 0.5f;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
        }

        @Override
        public int getCount() {
            return null==list?0:list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }
}
