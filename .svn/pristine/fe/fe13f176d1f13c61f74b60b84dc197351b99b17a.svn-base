package com.ghg.tobacco.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ghg.tobacco.Constants;
import com.ghg.tobacco.Data;
import com.ghg.tobacco.R;
import com.ghg.tobacco.adapter.BusinessListAdapter;
import com.ghg.tobacco.base.BaseActivity;
import com.ghg.tobacco.base.BaseRecyclerAdapter;
import com.ghg.tobacco.bean.Store;
import com.ghg.tobacco.bean.response.ResponseStore;
import com.ghg.tobacco.utils.DialogUtils;
import com.ghg.tobacco.utils.ResUtils;
import com.ghg.tobacco.utils.StringUtils;
import com.ghg.tobacco.view.AreaDialog;
import com.ghg.tobacco.xrecyclerview.ProgressStyle;
import com.ghg.tobacco.xrecyclerview.XRecyclerView;

import java.util.List;

/**
 * 商户列表
 */
public class BusinessListActivity extends BaseActivity {

    private XRecyclerView mRecyclerView;
    private List<Store> mData;
    private Dialog dialog;
    private BusinessListAdapter adapter;
    private ImageView btnBack;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (mData == null) {
                        DialogUtils.loadingDialogDismiss(dialog);
                        Toast.makeText(BusinessListActivity.this, ResUtils.getString(BusinessListActivity.this, R.string.data_empty), Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        adapter = new BusinessListAdapter(BusinessListActivity.this, mData);
                        mRecyclerView.setAdapter(adapter);
                        DialogUtils.loadingDialogDismiss(dialog);
                        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position, Object model) {
                                Store store= (Store) model;
                                Intent intent=new Intent(BusinessListActivity.this,BusinessDetailActivity.class);
                                intent.putExtra("store",store);
                                startActivity(intent);
                            }

                            @Override
                            public void onItemLongClick(View view, int position, Object model) {

                            }
                        });
                    }

                    break;
                case 2:
                    if (mData == null) {
                        DialogUtils.loadingDialogDismiss(dialog);
                        Toast.makeText(BusinessListActivity.this, ResUtils.getString(BusinessListActivity.this, R.string.data_empty), Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        adapter.addItemLast(mData);
                        adapter.notifyDataSetChanged();
                        mRecyclerView.refreshComplete();
                        mRecyclerView.setLoadingMoreEnabled(true);
                    }

                    break;


            }
        }
    };

    private TextView mTvAreaChoose;
    private String[] address;
    private AreaDialog areaDialog;

    @Override
    public int bindLayout() {
        return R.layout.activity_business_list;
    }


    @Override
    public void initView() {

        btnBack=findViewId(R.id.btn_back);
        setTitle(ResUtils.getString(this, R.string.title_business_list));
        ImageView btnLocation = setBtnMenu();
        btnLocation.setImageResource(R.drawable.selector_btn_title_location);

        mTvAreaChoose = findViewId(R.id.tv_area_choose);
        mRecyclerView = findViewId(R.id.rv_detail);
    }

    @Override
    public void initListener() {
        btnBack.setOnClickListener(this);
        mTvAreaChoose.setOnClickListener(this);
    }

    @Override
    public void initData() {
        address = new String[]{"广东", "", ""};
        mTvAreaChoose.setText(address[0] + " " + address[1] + " " + address[2]);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);

        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.setLoadingMoreEnabled(false);
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.clear();
                        freshData();
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore() {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.setPullRefreshEnabled(false);
                        freshData();
                    }
                }, 1000);

            }
        });
        getData();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {
        mHandler.removeMessages(1);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_menu:
                finish();
                break;
            case R.id.btn_back:
                sendBroadcast(new Intent(Constants.BroadcastAction.GO_TO_HOME));
                finish();
                break;
            case R.id.tv_area_choose:


                areaDialog = DialogUtils.createAreaDialog(BusinessListActivity.this, new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        address[0] = StringUtils.isNotEmpty(areaDialog.getProvince()) ? areaDialog.getProvince() : "";
                        address[1] = StringUtils.isNotEmpty(areaDialog.getCity()) ? areaDialog.getCity() : "";
                        address[2] = StringUtils.isNotEmpty(areaDialog.getArea()) ? areaDialog.getArea() : "";
                        mTvAreaChoose.setText(address[0] + " " + address[1] + " " + address[2]);
                        areaDialog.dismiss();

                        mRecyclerView.refreshComplete();

                    }
                }, address);
        }
    }

    private void getData() {
        dialog = DialogUtils.loadingDialog(this);
        dialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ResponseStore res = Data.getResStore(BusinessListActivity.this,"");
                mData = res.stores;
                mHandler.sendEmptyMessage(1);
            }
        }).start();

    }
    private void freshData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ResponseStore res = Data.getResStore(BusinessListActivity.this,"");
                mData = res.stores;
                mHandler.sendEmptyMessage(2);
            }
        }).start();

    }




}
