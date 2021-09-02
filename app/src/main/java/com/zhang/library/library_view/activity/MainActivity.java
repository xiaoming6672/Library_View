package com.zhang.library.library_view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.zhang.library.library_view.BuildConfig;
import com.zhang.library.library_view.R;
import com.zhang.library.library_view.adapter.MarqueeAdapter;
import com.zhang.library.utils.LogUtils;
import com.zhang.library.utils.context.ContextUtils;
import com.zhang.library.view.XMSlideMarqueeView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private XMSlideMarqueeView rvMarquee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtils.setDebug(BuildConfig.DEBUG);
        ContextUtils.set(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
        initData();
    }

    private void initView() {
        findViewById(R.id.iv_hexagon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "六边形图片", Toast.LENGTH_SHORT).show();

//                initMarqueeData();
            }
        });

        rvMarquee = findViewById(R.id.rv_marquee);
        rvMarquee.setAdapter(getMarqueeAdapter());
    }

    private void initData() {
        initMarqueeData();
    }

    private void initMarqueeData() {
        Runnable runnable = new Runnable() {
            public void run() {
                List<Integer> list = new ArrayList<>();

                for (int i = 0; i < 2; i++) {
                    list.add((i + 1) * 5);
                }
                getMarqueeAdapter().getDataHolder().setDataList(list);
                rvMarquee.start();
            }
        };
        rvMarquee.postDelayed(runnable, 0);
    }

    private MarqueeAdapter mMarqueeAdapter;

    public MarqueeAdapter getMarqueeAdapter() {
        if (mMarqueeAdapter == null) {
            mMarqueeAdapter = new MarqueeAdapter();
        }
        return mMarqueeAdapter;
    }
}