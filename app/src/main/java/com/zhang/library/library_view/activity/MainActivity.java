package com.zhang.library.library_view.activity;

import android.os.Bundle;

import com.zhang.library.library_view.BuildConfig;
import com.zhang.library.library_view.R;
import com.zhang.library.library_view.adapter.TestAdapter;
import com.zhang.library.library_view.model.TestModel;
import com.zhang.library.utils.LogUtils;
import com.zhang.library.utils.context.ContextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TestAdapter mAdapter;

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


        RecyclerView rv_content = findViewById(R.id.rv_content);

        rv_content.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LogUtils.debug(TAG, "RecyclerView.OnScrollListener>>>onScrollStateChanged()>>>newState = %d", newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LogUtils.debug(TAG,
                        "RecyclerView.OnScrollListener>>>onScrolled()>>>scrollState = %d,dx = %d, dy = %d",
                        recyclerView.getScrollState(), dx, dy);
            }
        });

        rv_content.setAdapter(getAdapter());
    }

    private void initData() {
        int size = new Random().nextInt(20) + 20;

        List<TestModel> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(new TestModel());
        }
        getAdapter().getDataHolder().setDataList(list);
    }

    private TestAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new TestAdapter();
        }
        return mAdapter;
    }
}