package com.example.frgment_activity_communication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 *
 * Created by Administrator on 2017/2/9.
 */

public class MyFragment extends Fragment {
    private Context context;
    public static final String TYPE = "type";
    private IFragment2Activity callBack;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    public static MyFragment newInstance(String type){
        MyFragment myFragment = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TYPE,type);
        myFragment.setArguments(bundle);
        return myFragment;
    }
    public void setValue(String value){
        Log.e("xxx",value);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActivity() instanceof IFragment2Activity){
                callBack = (IFragment2Activity) getActivity();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(context);
        String string = getArguments().getString(TYPE);
        textView.setText(string);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.setActivityValue("这是Fragment发送的消息");
            }
        });
        return textView;
    }
    public interface IFragment2Activity{
        void setActivityValue(String value);

    }
}
