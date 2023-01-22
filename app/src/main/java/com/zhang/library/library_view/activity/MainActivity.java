package com.zhang.library.library_view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhang.library.library_view.BuildConfig;
import com.zhang.library.library_view.R;
import com.zhang.library.library_view.adapter.MarqueeAdapter;
import com.zhang.library.utils.LogUtils;
import com.zhang.library.utils.context.ContextUtils;
import com.zhang.library.view.XMAutoSizeTextView;
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

        findViewById(R.id.tv_stroke_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
            }
        });

        findViewById(R.id.tv_stroke_select).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                View view = findViewById(R.id.tv_stroke_enable);
                view.setEnabled(!view.isEnabled());
                return true;

            }
        });

        findViewById(R.id.tv_stroke_enable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String text = "<font color='#FF0000'>Html</font>测试";
//                ((TextView) findViewById(R.id.tv_stroke_enable)).setText(Html.fromHtml(text));

                SpannableString span = new SpannableString("Spannable");
                span.setSpan(new ForegroundColorSpan(Color.BLUE), 0, span.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append(span).append("测试");
                ((TextView) findViewById(R.id.tv_stroke_enable)).setText(span);

//                ((TextView) findViewById(R.id.tv_stroke_enable)).setText("builder");
            }
        });

        rvMarquee = findViewById(R.id.rv_marquee);
        rvMarquee.setAdapter(getMarqueeAdapter());
        rvMarquee.setTouchSupported(true);

        final XMAutoSizeTextView tvAutoSize = findViewById(R.id.tv_auto_size);
        tvAutoSize.setText("奥迪虎丘我弄按时都会去外地阿是第几阿朵我hi去黄");
        tvAutoSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAutoSize.setText("哀思和阿");
//                tvAutoSize.setText("哀思和阿搜下昂实行按照西欧啊那矿务局付水泥厂饭局");
            }
        });
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