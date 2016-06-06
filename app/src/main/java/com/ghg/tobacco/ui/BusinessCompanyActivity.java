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
import com.baidu.mapapi.clusterutil.clustering.interfaces.MyClusterRendered;
import com.baidu.mapapi.map.BaiduMap;
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
import com.ghg.tobacco.bean.BusinessCompany;
import com.ghg.tobacco.custom_view.MapZoomImageView;
import com.ghg.tobacco.utils.DialogUtils;
import com.ghg.tobacco.utils.ResUtils;

import java.util.List;

/**
 * 商业公司
 */
public class BusinessCompanyActivity extends BaseMapActivity implements MyClusterRendered {
    private ImageView btnBack;
    private View container_province_buy;
    private TextView tvAddress, tvProviceCompany;
    private TextView tv_lower_buy;//查看下级专卖局
    private TextView tv_province_buy;//查看省专卖局
    private LatLng mLocationLatLng;
    private float zoom_country = 5f;
    private int zoom_provice = 8;
    private int zoom_single_provice = 10;
    private int zoom_city = 12;
    private Dialog dialog;
    //所有省份
    private List<BusinessCompany> mAllProvince;
    private List<BusinessCompany> mLowerCompanys;
    private BusinessCompany mCountryCompany;
    private ImageView zoom_in, zoom_out, btn_location;
    private MapZoomImageView zoom;
    private boolean mLocationMarkerAdded = false;
    private BusinessCompany mProviceCompany;//被选中的省份
    private String province_hunan = "430000";
    private String province_guangdong = "440000";
    private int currentType;
    public BDLocationListener myListener = new MyLocationListener();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LatLng latLng = null;
            BusinessCompany businessCompany = null;
            DialogUtils.loadingDialogDismiss(dialog);
            switch (msg.what) {
                case Constants.Area.COUNTRY:
                    addCountryMark(mCountryCompany);
                    break;
                case Constants.Area.PROVINCE:
                    addProviceMarker();
                    break;
                case Constants.Area.CITY:
                    addCityMarker();
                    break;

            }

        }
    };

    private void addCityMarker() {
        BusinessCompany businessCompany;
        LatLng latLng;
        if (mLowerCompanys != null && mLowerCompanys.size() != 0) {
            setBottomViewVisible(false, null);
            businessCompany = mLowerCompanys.get(0);
            latLng = new LatLng(Double.parseDouble(businessCompany.latitude), Double.parseDouble(businessCompany.longitude));
            setMapCenter(latLng,zoom_city);
            addBusinessCompanyMarker(mLowerCompanys, Constants.Area.CITY);
        }
    }

    private void addProviceMarker() {
        BusinessCompany businessCompany;
        LatLng latLng;
        if (mAllProvince==null||mAllProvince.size()==0){
            return;
        }
        if (mLocationLatLng!=null){
            setMapCenter(mLocationLatLng,zoom_provice);
        }else {
            businessCompany = mAllProvince.get(0);
            latLng = new LatLng(Double.parseDouble(businessCompany.latitude), Double.parseDouble(businessCompany.longitude));
            setMapCenter(latLng,zoom_provice);
        }
        addBusinessCompanyMarker(mAllProvince, Constants.Area.PROVINCE);
    }

    private RelativeLayout.LayoutParams mParams;


    @Override
    public int bindLayout() {
        return R.layout
                .activity_business_company;
    }

    @Override
    public void initView() {
//        Constants.ClusterType.ACTIVITY_TYPE = Constants.ClusterType.BUSINESS_COMPANY;
        setTitle(getResources().getString(R.string.business_company));
        setBtnMenu();
        container_province_buy = findViewId(R.id.container_province_buy);

        btnBack = findViewId(R.id.btn_back);
        mMapView = findViewId(R.id.mapView);
        tvAddress = findViewId(R.id.tv_address);
        tv_province_buy = findViewId(R.id.tv_province_buy);
        tv_lower_buy = findViewId(R.id.tv_lower_buy);
        tvProviceCompany = findViewId(R.id.tv_province_company);
        zoom_in = findViewId(R.id.zoom_in);
        zoom_out = findViewId(R.id.zoom_out);
        zoom = findViewId(R.id.zoom);
        btn_location = findViewId(R.id.btn_location);
        initMap();

        zoom.setZoomInOut(zoom_in, zoom_out);
        mParams = (RelativeLayout.LayoutParams) container_province_buy.getLayoutParams();

    }

    private void initMap() {
        mBaiduMap = mMapView.getMap();
        hideZoomControls();
        hideLogo();
        initLocation(myListener);
        mLocationClient.start();
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle bundle = marker.getExtraInfo();
                if (bundle==null){
                    return true;
                }
                BusinessCompany businessCompany = (BusinessCompany) bundle.getSerializable("businessCompany");
                if (businessCompany == null) {
                    return true;
                }
                int type = bundle.getInt("mType");
                if (type == Constants.Area.COUNTRY) {
                    if (mAllProvince != null) {
//                        addBusinessCompanyMarker(mAllProvince, Constants.Area.PROVINCE);
                        mHandler.sendEmptyMessage(Constants.Area.PROVINCE);
                    } else {
                        getAllProvince(Constants.Area.PROVINCE);
                    }
                } else if (type == Constants.Area.PROVINCE) {
                    mProviceCompany = businessCompany;
//                    setZoom(zoom_single_provice);
                    setMapCenter(new LatLng(Double.valueOf(businessCompany.latitude), Double.valueOf(businessCompany.longitude)),zoom_single_provice);
                    setBottomViewVisible(true, businessCompany);
                } else if (type == Constants.Area.CITY) {
                    Intent intent = new Intent(BusinessCompanyActivity.this, BusinessCompanyDetailActivity.class);
                    intent.putExtra("type", 2);
                    intent.putExtra("company", businessCompany);
                    startActivity(intent);

                }
                return false;
            }
        });


//        // 定义点聚合管理类ClusterManager
//        mClusterManager = new ClusterManager<MyItem>(this, mBaiduMap, this);
//        // 设置地图监听，当地图状态发生改变时，进行点聚合运算
//        mBaiduMap.setOnMapStatusChangeListener(mClusterManager);
//        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem>() {
//            @Override
//            public boolean onClusterItemClick(MyItem item,Marker marker) {
//                Bundle bundle = item.getBundle();
//                BusinessCompany businessCompany = (BusinessCompany) bundle.getSerializable("businessCompany");
//                int type = bundle.getInt("mType");
//                if (type == Constants.Area.PROVINCE) {
//                    mProviceCompany = businessCompany;
//                    setZoom(zoom_single_provice);
//                    setMapCenter(new LatLng(Double.valueOf(businessCompany.latitude), Double.valueOf(businessCompany.longitude)));
//                    setBottomViewVisible(true, businessCompany);
//                } else if (type == Constants.Area.CITY) {
//                    Intent intent = new Intent(BusinessCompanyActivity.this, BusinessCompanyDetailActivity.class);
//                    intent.putExtra("type", 2);
//                    intent.putExtra("company", businessCompany);
//                    startActivity(intent);
//
//                }
//                return true;
//            }
//        });
//        mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MyItem>() {
//            @Override
//            public boolean onClusterClick(Cluster<MyItem> cluster) {
//                BusinessCompany businessCompany = mAllProvince.get(0);
//                setZoom(zoom_provice);
//                setMapCenter(new LatLng(Double.valueOf(businessCompany.latitude), Double.valueOf(businessCompany.longitude)));
//                return true;
//            }
//        });
//
//        mBaiduMap.setOnMarkerClickListener(mClusterManager);
    }

    @Override
    public void initListener() {
        btnBack.setOnClickListener(this);
        tv_lower_buy.setOnClickListener(this);
        tv_province_buy.setOnClickListener(this);
        btn_location.setOnClickListener(this);
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
        //        getTotalProvince();
        getAllProvince(Constants.Area.COUNTRY);
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
//        mClusterManager.clearItems();
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
            case R.id.btn_menu:
                startActivity(new Intent(BusinessCompanyActivity.this, BusinessCompanyListActivity.class));
                break;
            case R.id.btn_back:

                if (currentType == Constants.Area.COUNTRY) {
                    finish();
                } else if (currentType == Constants.Area.PROVINCE) {
//                    mHandler.sendEmptyMessage(Constants.Area.COUNTRY);
                    addCountryMark(mCountryCompany);
                    setBottomViewVisible(false,null);
                }else if (currentType == Constants.Area.CITY) {
                    addProviceMarker();
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mHandler.sendEmptyMessage(Constants.Area.PROVINCE);
//                        }
//                    }).start();
                }
                break;

            case R.id.tv_lower_buy:
                getLowerCompanys(mProviceCompany.province);
                break;
            case R.id.tv_province_buy:
                //进入省级公司详情页
                Intent intent = new Intent(BusinessCompanyActivity.this, BusinessCompanyDetailActivity.class);
                intent.putExtra("company", mProviceCompany);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
            case R.id.btn_location:
                if (mLocationLatLng == null) {
                    if (!mLocationClient.isStarted()) {
                        mLocationClient.start();
                    }
                } else {
                    setMapCenter(mLocationLatLng,-1);
                    addLocationMark(mLocationLatLng);
                }
                break;
        }
    }

    private void addBusinessCompanyMarker(List<BusinessCompany> companys, int type) {
        currentType = type;
        mBaiduMap.clear();
        mLocationMarkerAdded = false;
//        if (companys == null && companys.size() == 0) {
//            Toast.makeText(BusinessCompanyActivity.this, "无数据", Toast.LENGTH_SHORT).show();
//            return;
//        }
        for (int i = 0; i < companys.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.mark_business_city, null);
            TextView tv_province = (TextView) view.findViewById(R.id.tv_province);
            TextView tv_real_plan = (TextView) view.findViewById(R.id.tv_real_plan);
            BusinessCompany businessCompany = companys.get(i);
            if (type == Constants.Area.PROVINCE) {
                tv_province.setText(businessCompany.provinceName);
            } else if (type == Constants.Area.CITY) {
                tv_province.setText(businessCompany.cityName);
            }
            LatLng latLng = new LatLng(Double.parseDouble(businessCompany.latitude), Double.parseDouble(businessCompany.longitude));
            tv_real_plan.setText(businessCompany.factNum + "/" + businessCompany.planNum);
            Bundle bundle = new Bundle();
            bundle.putInt("mType", type);
            bundle.putSerializable("businessCompany", businessCompany);
            //构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromView(view);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(latLng)
                    .icon(bitmap).anchor(0.5f,0.5f);
            //在地图上添加Marker，并显示
            Marker marker = (Marker) mBaiduMap.addOverlay(option);
            marker.setExtraInfo(bundle);
        }

//        addLocationMark();


    }

    private void addCountryMark(BusinessCompany businessCompany) {
        if (businessCompany==null){
            return;
        }
        currentType = Constants.Area.COUNTRY;
        mBaiduMap.clear();
        mLocationMarkerAdded = false;
        LatLng latLng = new LatLng(Double.parseDouble(businessCompany.latitude), Double.parseDouble(businessCompany.longitude));
        latLng = new LatLng(Double.parseDouble(mCountryCompany.latitude), Double.parseDouble(mCountryCompany.longitude));
        setMapCenter(latLng,zoom_country);
        View view = LayoutInflater.from(this).inflate(R.layout.mark_business, null);
        TextView tv_plan = (TextView) view.findViewById(R.id.tv_plan);
        tv_plan.setText(businessCompany.factNum + "/" + businessCompany.planNum);
        Bundle bundle = new Bundle();
        bundle.putInt("mType", currentType);
        bundle.putSerializable("businessCompany", businessCompany);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromView(view);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(latLng)
                .icon(bitmap).anchor(0.5f,0.5f);
        //在地图上添加Marker，并显示
        Marker marker = (Marker) mBaiduMap.addOverlay(option);
        marker.setExtraInfo(bundle);
//        addLocationMark();

    }

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
                .icon(bitmap).anchor(0.5f,0.5f);
        //在地图上添加Marker，并显示
        Marker marker = (Marker) mBaiduMap.addOverlay(option);
    }

    @Override
    public void onClusterRendered(int type) {
        setBottomViewVisible(false, null);
    }


    @Override
    public void onBeforeClusterRendered(int type) {
        if (type == Constants.Area.CITY) {
            addBusinessCompanyMarker(mAllProvince, Constants.Area.PROVINCE);

            BusinessCompany businessCompany = mAllProvince.get(0);
            LatLng latLng = new LatLng(Double.parseDouble(businessCompany.latitude), Double.parseDouble(businessCompany.longitude));
            setMapCenter(latLng,-1);
        }
    }


    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            mLocationClient.stop();
            if (bdLocation == null) {
                Toast.makeText(BusinessCompanyActivity.this, "定位失败", Toast.LENGTH_SHORT).show();
                return;
            }
//            Toast.makeText(BusinessCompanyActivity.this, bdLocation.getAddrStr(), Toast.LENGTH_SHORT).show();
            mLocationLatLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            addLocationMark(mLocationLatLng);

        }
    }


    @Override
    public void onBackPressed() {
        btnBack.performClick();
    }


    /**
     * 获取共多少家烟草专卖局
     */

    /**
     * 获取所有省份
     */
    private void getAllProvince(final int type) {
        dialog = DialogUtils.loadingDialog(this);
        dialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (type == Constants.Area.COUNTRY) {
                    List<BusinessCompany> businessCompanies = Data.getResBusinessCompany(BusinessCompanyActivity.this, Constants.Area.COUNTRY, null);
                    mCountryCompany = businessCompanies.get(0);
                    mHandler.sendEmptyMessage(Constants.Area.COUNTRY);
                } else if (type == Constants.Area.PROVINCE) {
                    mAllProvince = Data.getResBusinessCompany(BusinessCompanyActivity.this, Constants.Area.PROVINCE, null);
                    if (mAllProvince != null) {
                        mHandler.sendEmptyMessage(Constants.Area.PROVINCE);
                    }
                }

            }
        }).start();
    }

    /**
     * 获取省份下级公司
     */
    private void getLowerCompanys(String province) {
        final String provinceStr;
        if (!province.equals(province_guangdong) && !province.equals(province_hunan)) {
            provinceStr = province_guangdong;
        } else {
            provinceStr = province;
        }
        dialog = DialogUtils.loadingDialog(this);
        dialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mLowerCompanys = Data.getResBusinessCompany(BusinessCompanyActivity.this, Constants.Area.CITY, provinceStr);
                if (mLowerCompanys != null) {
                    mHandler.sendEmptyMessage(Constants.Area.CITY);
                }
            }
        }).start();
    }


    private void setBottomViewVisible(boolean isVisible, BusinessCompany businessCompany) {
        if (!isVisible) {
            if (container_province_buy.getHeight() != 0) {
                int start = container_province_buy.getHeight();
                setBottomViewVisible(start, 0);
            }
        } else {
            if (businessCompany != null) {
                tvProviceCompany.setText(businessCompany.name);
                tvAddress.setText(businessCompany.address);
            }
            if (container_province_buy.getHeight() == 0) {
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
                container_province_buy.setLayoutParams(mParams);
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
