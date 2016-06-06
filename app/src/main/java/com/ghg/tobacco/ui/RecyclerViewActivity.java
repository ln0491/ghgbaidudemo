package com.ghg.tobacco.ui;

import android.app.Dialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.ghg.tobacco.R;
import com.ghg.tobacco.adapter.RecyclerAdapter;
import com.ghg.tobacco.base.BaseActivity;
import com.ghg.tobacco.xrecyclerview.DividerItemDecoration;
import com.ghg.tobacco.xrecyclerview.ProgressStyle;
import com.ghg.tobacco.xrecyclerview.XRecyclerView;
import com.ghg.tobacco.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends BaseActivity {


    private XRecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private Dialog dialog;

    @Override
    public int bindLayout() {
        return R.layout.activity_recycler_view;
    }

    @Override
    public void initView() {
        recyclerView = findViewId(R.id.recyclerView);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);

        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);

        List<String> mDatas = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            mDatas.add("数据" + i);
        }

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));


        recyclerAdapter = new RecyclerAdapter(this, mDatas);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLoadingMoreEnabled(true);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                dialog= DialogUtils.loadingDialog(RecyclerViewActivity.this);
                recyclerView.setLoadingMoreEnabled(false);
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DialogUtils.loadingDialogDismiss(dialog);
                        recyclerAdapter.clear();
                        List<String> mDatas = new ArrayList<String>();
                        for (int i = 0; i < 20; i++) {
                            mDatas.add("数据" + i);
                        }
                        recyclerAdapter.addItemLast(mDatas);
                        recyclerAdapter.notifyDataSetChanged();
                        recyclerView.refreshComplete();
                        recyclerView.setLoadingMoreEnabled(true);
                    }
                }, 3000);
            }

            @Override
            public void onLoadMore() {
                dialog=DialogUtils.loadingDialog(RecyclerViewActivity.this);
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DialogUtils.loadingDialogDismiss(dialog);
                        recyclerView.setPullRefreshEnabled(false);
                        List<String> mDatas = new ArrayList<String>();
                        for (int i = 0; i < 10; i++) {
                            mDatas.add("onLoadMore数据" + i);
                        }
                        recyclerAdapter.addItemLast(mDatas);
                        recyclerAdapter.notifyDataSetChanged();
                        recyclerView.loadMoreComplete();
                        recyclerView.setPullRefreshEnabled(true);
                    }
                }, 3000);

            }
        });
    }

    @Override
    public void resume() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onClick(View v) {

    }
}
