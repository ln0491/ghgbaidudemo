package com.ghg.tobacco.ui;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.clusterutil.clustering.Cluster;
import com.baidu.mapapi.clusterutil.clustering.ClusterManager;
import com.baidu.mapapi.clusterutil.clustering.interfaces.MyClusterRendered;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.ghg.tobacco.Constants;
import com.ghg.tobacco.Data;
import com.ghg.tobacco.R;
import com.ghg.tobacco.base.BaseMapActivity;
import com.ghg.tobacco.bean.Industry;
import com.ghg.tobacco.bean.response.ResponseIndustry;
import com.ghg.tobacco.custom_view.MapZoomImageView;
import com.ghg.tobacco.utils.DialogUtils;
import com.ghg.tobacco.utils.ResUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 工业公司
 */
public class IndustryCompanyActivity2 extends BaseMapActivity implements MyClusterRendered {
    private ImageView btnBack;
    private LatLng mLocationLatLng;
    private float zoom_country = 3.5f;
    private int zoom_provice = 8;
    private int zoom_city = 10;
    private Dialog dialog;
    private ImageView zoom_in, zoom_out, btn_location;
    private MapZoomImageView zoom;
    private View container_industry;
    private ResponseIndustry responseIndustry;
    private Industry countryIndustry;
    private TextView tv_industry_name, tv_produce_plan;
    private View container_detail;
    private List<Industry> mIndustries_province;
    private RelativeLayout.LayoutParams mParams;
    private Industry mIndustry;

    public BDLocationListener myListener = new MyLocationListener();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Industry industry=null;
            LatLng latLng=null;
            DialogUtils.loadingDialogDismiss(dialog);
            List<Industry> industries = responseIndustry.company;
            if (industries == null || industries.size() == 0) {
                return;
            }
            switch (msg.what) {
                case Constants.Area.PROVINCE:
                    mIndustries_province = industries;
                    addProvinceMark(industries, Constants.Area.PROVINCE);
//                    setZoom(zoom_country);
                    latLng=new LatLng(Double.parseDouble(countryIndustry.latitude),Double.parseDouble(countryIndustry.longitude));
                    setMapCenter(latLng,zoom_country);
                    break;
                case Constants.Area.CITY:
                    addProvinceMark(industries, Constants.Area.CITY);
                    setZoom(zoom_city);
                    industry=industries.get(0);
                    latLng=new LatLng(Double.parseDouble(industry.latitude),Double.parseDouble(industry.longitude));
                    setMapCenter(latLng,zoom_city);
                    break;

            }

        }
    };


    @Override
    public int bindLayout() {
        return R.layout
                .activity_industry_company;
    }

    @Override
    public void initView() {

        Constants.ClusterType.ACTIVITY_TYPE = Constants.ClusterType.INDUSTRY;
        setTitle(getResources().getString(R.string.industrial_company));
        setBtnMenu();

        btnBack = findViewId(R.id.btn_back);
        mMapView = findViewId(R.id.mapView);
        zoom_in = findViewId(R.id.zoom_in);
        zoom_out = findViewId(R.id.zoom_out);
        zoom = findViewId(R.id.zoom);
        btn_location = findViewId(R.id.btn_location);
        container_industry = findViewId(R.id.container_industry);
        tv_produce_plan = findViewId(R.id.tv_produce_plan);
        tv_industry_name = findViewId(R.id.tv_industry_name);
        container_detail = findViewId(R.id.container_detail);
        mParams = (RelativeLayout.LayoutParams) container_industry.getLayoutParams();
        initMap();
        zoom.setZoomInOut(zoom_in, zoom_out);
    }

    private void initMap() {
        mBaiduMap = mMapView.getMap();
        hideZoomControls();
        hideLogo();

        initLocation(myListener);
        mLocationClient.start();

        // 定义点聚合管理类ClusterManager
        mClusterManager = new ClusterManager<MyItem>(this, mBaiduMap, this);
        // 设置地图监听，当地图状态发生改变时，进行点聚合运算
        mBaiduMap.setOnMapStatusChangeListener(mClusterManager);
        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem>() {
            @Override
            public boolean onClusterItemClick(MyItem item,Marker marker) {
                Bundle bundle = item.getBundle();
                Industry industry = (Industry) bundle.getSerializable("industry");
                int type = bundle.getInt("mType");
                if (type == Constants.Area.PROVINCE) {
                    getIndustry(Constants.Area.CITY, industry.province);
                } else if (type == Constants.Area.CITY) {
                    mIndustry=industry;
                    setBottomViewVisible(true, industry);
                }
                return true;
            }
        });
        mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MyItem>() {
            @Override
            public boolean onClusterClick(Cluster<MyItem> cluster) {
                Industry industry = mIndustries_province.get(0);
                LatLng latLng=new LatLng(Double.parseDouble(industry.latitude),Double.valueOf(industry.longitude));
//                Toast.makeText(IndustryCompanyActivity2.this, "industry=="+industry.name+" latitude= "+industry.latitude+" longitude="+industry.longitude, Toast.LENGTH_SHORT).show();
                setMapCenter(latLng,zoom_provice);
                setBottomViewVisible(false, null);
//                setZoom(zoom_provice);
                return true;
            }
        });

        mBaiduMap.setOnMarkerClickListener(mClusterManager);


    }

    @Override
    public void initListener() {
        btnBack.setOnClickListener(this);
        btn_location.setOnClickListener(this);
        container_detail.setOnClickListener(this);
        registerReceiver(mBroadcastReceiver, new IntentFilter(Constants.BroadcastAction.GO_TO_HOME));
        zoom.setOnClickListener(new MapZoomImageView.OnClickListener() {
            @Override
            public void zoomInClick() {
                zoomIn();
            }

            @Override
            public void zoomOutClick() {
                zoomOut();
            }
        });
    }

    @Override
    public void initData() {
        getIndustry(Constants.Area.PROVINCE, null);
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
        mClusterManager.clearItems();
        if (mBroadcastReceiver != null) {
            unregisterReceiver(mBroadcastReceiver);
        }
        mHandler.removeMessages(Constants.Area.COUNTRY);
        mHandler.removeMessages(Constants.Area.PROVINCE);
        mHandler.removeMessages(Constants.Area.CITY);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_back:
                finish();
                break;


            case R.id.btn_location:
                if (mLocationLatLng != null) {
                    setMapCenter(mLocationLatLng,-1);
                } else {
                    if (!mLocationClient.isStarted()) {
                        mLocationClient.start();
                    }
                }
                break;
            case R.id.btn_menu:
                startActivity(new Intent(IndustryCompanyActivity2.this, IndustryListActivity.class));
                break;
            case R.id.container_detail:
                Intent intent = new Intent(IndustryCompanyActivity2.this, IndustryCompanyDetailActivity.class);
                intent.putExtra("industry", mIndustry);
                startActivity(intent);
                break;
        }
    }


    private boolean mLocationMarkerAdded = false;

    private void addLocationMark(LatLng latLng) {
        if (latLng == null || mLocationMarkerAdded)
            return;
        mLocationMarkerAdded = true;
        //定义Maker坐标点
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_geo);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(latLng)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        Marker marker = (Marker) mBaiduMap.addOverlay(option);
    }

    @Override
    public void onClusterRendered(int type) {

    }
    @Override
    public void onBeforeClusterRendered(int type) {

        if (type == Constants.Area.CITY) {
            addProvinceMark(mIndustries_province, Constants.Area.PROVINCE);
            setZoom(zoom_provice);

            Industry industry=mIndustries_province.get(0);
            LatLng latLng=new LatLng(Double.parseDouble(industry.latitude),Double.parseDouble(industry.longitude));
            setMapCenter(latLng,-1);
        }
    }


    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            mLocationClient.stop();
            if (bdLocation == null) {
                Toast.makeText(IndustryCompanyActivity2.this, "定位失败", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(IndustryCompanyActivity2.this, bdLocation.getAddrStr(), Toast.LENGTH_SHORT).show();
            mLocationLatLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            addLocationMark(mLocationLatLng);

        }
    }


    @Override
    public void onBackPressed() {
        btnBack.performClick();
        //super.onBackPressed();
    }


    private void getIndustry(final int type, final String value) {
        dialog = DialogUtils.loadingDialog(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (type==Constants.Area.PROVINCE){
                    ResponseIndustry responseCountryIndustry = Data.getResIndustry(IndustryCompanyActivity2.this, Constants.Area.COUNTRY, value);
                    countryIndustry=responseCountryIndustry.company.get(0);
                }
                responseIndustry = Data.getResIndustry(IndustryCompanyActivity2.this, type, value);
                mHandler.sendEmptyMessage(type);
            }
        }).start();
    }




    /**
     * 添加marker标记
     *
     * @param industries
     */
    private void addProvinceMark(List<Industry> industries, int type) {
        Constants.ClusterType.CLUSTER_TYPE = type;
        List<MyItem> items = new ArrayList<>();
        for (int i = 0; i < industries.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.industry_province_mark, null);
            TextView tv_province_account = (TextView) view.findViewById(R.id.tv_province_account);
            TextView tv_plan = (TextView) view.findViewById(R.id.tv_plan);
            TextView tv_province = (TextView) view.findViewById(R.id.tv_province);
            Industry industry = industries.get(i);
            tv_plan.setText(String.format(ResUtils.getString(this, R.string.million_boxes), industry.planNum));
            if (type == Constants.Area.CITY) {
                tv_province_account.setVisibility(View.GONE);
                tv_province.setText(industry.name);
            } else {
                tv_province.setText(industry.provinceName);
                tv_province_account.setText(String.format(ResUtils.getString(this, R.string.industry__province_account), industry.quantity));
            }

            LatLng latLng = new LatLng(Double.parseDouble(industry.latitude), Double.parseDouble(industry.longitude));
            Bundle bundle = new Bundle();
            bundle.putInt("mType", type);
            bundle.putSerializable("industry", industry);
            MyItem item = new MyItem(view, latLng, bundle);
            items.add(item);
        }

        mClusterManager.setlusterSize(items.size());
        mClusterManager.initIndustryData(countryIndustry.planNum+"万箱");
        addMarkers(items);
        addLocationMark(mLocationLatLng);
        setBottomViewVisible(false, null);

    }

    /**
     * 隐藏或显示详情
     *
     * @param isVisible
     */
    private void setBottomViewVisible(boolean isVisible, Industry industry) {

        if (!isVisible) {
            if (container_industry.getHeight() != 0) {
                int start = container_industry.getHeight();
                setBottomViewVisible(start, 0);
            }
        } else {
            if (industry != null) {
                tv_industry_name.setText(industry.name);
                tv_produce_plan.setText(String.format(ResUtils.getString(this, R.string.industry_product_account), industry.produce)
                        + "  " + String.format(ResUtils.getString(this, R.string.industry_plan_account), industry.planNum));
            }
            if (container_industry.getHeight() == 0) {
                int end = (int) ResUtils.getDimension(this, R.dimen.height_industry_map_bottomView);
                setBottomViewVisible(0, end);
            }
        }
    }

    private void setBottomViewVisible(int start, int end) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                mParams.height = value;
                container_industry.setLayoutParams(mParams);
            }
        });
        animator.start();
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action)) {
                if (action.equals(Constants.BroadcastAction.GO_TO_HOME)) {
                    finish();
                }
            }
        }
    };
}
