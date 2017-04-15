package com.example.coordinatorlayout;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private CoordinatorLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar =(Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("精品攻略");
        toolbar.setLogo(getResources().getDrawable(R.drawable.realback));
        toolbar.setTitleMarginStart(200);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(layout,"是否关闭当前界面",Snackbar.LENGTH_LONG)
                        .setAction("是的", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        }).show();
            }
        });
        setSupportActionBar(toolbar);
        layout= (CoordinatorLayout) findViewById(R.id.activity_main);
    }

    public void clickSnackBar(View view) {
        Snackbar.make(layout,toolbar.getTitle(),Snackbar.LENGTH_LONG)
                .setAction("清除", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
    }

//    public void click_finish(View view) {
//        finishAffinity();
//    }
}
