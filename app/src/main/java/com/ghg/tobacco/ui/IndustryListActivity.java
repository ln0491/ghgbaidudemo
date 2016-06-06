package com.ghg.tobacco.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.ghg.tobacco.Constants;
import com.ghg.tobacco.Data;
import com.ghg.tobacco.R;
import com.ghg.tobacco.adapter.IndustryListAdapter;
import com.ghg.tobacco.base.BaseActivity;
import com.ghg.tobacco.bean.Industry;
import com.ghg.tobacco.bean.test.IndustryData;
import com.ghg.tobacco.utils.DialogUtils;
import com.ghg.tobacco.utils.ResUtils;

import java.util.List;
import java.util.Random;

/**
 * 工业公司列表
 */
public class IndustryListActivity extends BaseActivity {

    private ExpandableListView mExpandableListView;
    private Dialog dialog;
    private IndustryData industryData;
    private IndustryListAdapter mAdapter;
    private ImageView btnBack;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    DialogUtils.loadingDialogDismiss(dialog);
                    if (industryData != null) {
                        List<Industry> mCompaines = industryData.list;
                        if (mCompaines != null) {
                            mAdapter = new IndustryListAdapter(IndustryListActivity.this, mCompaines, industryData.map);
                            mExpandableListView.setAdapter(mAdapter);
                        }
                    }
                    break;
            }
        }
    };


    @Override
    public int bindLayout() {
        return R.layout.activity_company_list;
    }

    @Override
    public void initView() {

//        setBtnBack();
        setTitle(ResUtils.getString(this, R.string.title_industry_list));
        ImageView btnLocation = setBtnMenu();
        btnBack=findViewId(R.id.btn_back);

        btnLocation.setImageResource(R.drawable.selector_btn_title_location);
        mExpandableListView = findViewId(R.id.expandListView);
    }

    @Override
    public void initListener() {
        btnBack.setOnClickListener(this);
        //        mExpandableListView.setOnGroupExpandListener();
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Industry industry=mAdapter.getChild(groupPosition,childPosition);
                Random random=new Random();
                industry.sale= (double)random.nextInt(800);
                industry.inventory= (double)random.nextInt(800);
                industry.produce= (double)random.nextInt(800);
                industry.inTransitCar=random.nextInt(800);
                industry.inTransitNum=random.nextInt(800*100);
                Intent intent=new Intent(IndustryListActivity.this,IndustryCompanyDetailActivity.class);
                intent.putExtra("industry",industry);
                startActivity(intent);
                return true;
            }
        });
    }

    @Override
    public void initData() {
        getIndustry();
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
        mHandler.removeMessages(2);
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
        }
    }

    private void getIndustry() {
        dialog = DialogUtils.loadingDialog(this);
        dialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                industryData = Data.getIndustryData(IndustryListActivity.this);
                mHandler.sendEmptyMessage(1);
            }
        }).start();

    }



}
