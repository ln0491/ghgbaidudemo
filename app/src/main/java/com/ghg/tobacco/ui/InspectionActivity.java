package com.ghg.tobacco.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ghg.tobacco.R;
import com.ghg.tobacco.base.BaseActivity;
import com.ghg.tobacco.fragment.InspectionFragment;
import com.ghg.tobacco.fragment.ReportFragment;
import com.ghg.tobacco.utils.UnitUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 稽查打假
 */
public class InspectionActivity extends BaseActivity{


    private ViewPager mViewPager;
    //导航
    private ImageView tabImage;
    //tab导航
    private TextView tabInspection,tabReport;
    //tab宽
    private  int tabWith;
    //屏幕的宽
    private   int screenWidth;
    @Override
    public int bindLayout() {
        return R.layout.activity_inspection;
    }

    @Override
    public void initView() {

        setBtnBack();
        setTitle(getResources().getString(R.string.title_inspection_report));
        mViewPager=findViewId(R.id.viewPager);
        tabImage=findViewId(R.id.tabImage);
        tabInspection=findViewId(R.id.tv_tab_inspection);
        tabReport=findViewId(R.id.tv_tab_report);

        Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.mipmap.tab_line);
        tabWith =bitmap.getWidth();
        screenWidth = UnitUtils.getScreenWidth(this);
        bitmap.recycle();
        bitmap=null;


        initViewPager();

    }



    @Override
    public void initListener() {
        tabReport.setOnClickListener(this);
        tabInspection.setOnClickListener(this);
    }

    @Override
    public void initData() {


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

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_tab_inspection:
                mViewPager.setCurrentItem(0);
            break;
            case R.id.tv_tab_report:
                mViewPager.setCurrentItem(1);
                break;
        }
    }


    private void initViewPager() {

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTabPostiion(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        List<Fragment>fragmetns=new ArrayList<Fragment>();
        InspectionFragment inspectionFragment=new InspectionFragment();
        ReportFragment reportFragment=new ReportFragment();
        fragmetns.add(inspectionFragment);
        fragmetns.add(reportFragment);
        MyAdapter mAdapter=new MyAdapter(getSupportFragmentManager(),fragmetns);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
        setTabPostiion(0);

    }

    /**
     * 设置tab导航条的位置
     * @param selectedPosition
     */
    private void setTabPostiion(int selectedPosition){

        LinearLayout.LayoutParams mParams= (LinearLayout.LayoutParams) tabImage.getLayoutParams();
        int leftMargin=(screenWidth/2-tabWith)/2;
        if (selectedPosition==0){
            mParams.leftMargin=leftMargin;
            tabInspection.setSelected(true);
            tabReport.setSelected(false);
        }else if (selectedPosition==1){
            leftMargin=leftMargin+screenWidth/2;
            mParams.leftMargin=leftMargin;
            tabInspection.setSelected(false);
            tabReport.setSelected(true);
        }


        tabImage.setLayoutParams(mParams);
    }



    class MyAdapter extends FragmentPagerAdapter{

        private List<Fragment>fragments;
        public MyAdapter(FragmentManager fm,List<Fragment>fragments) {
            super(fm);
            this.fragments=fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
