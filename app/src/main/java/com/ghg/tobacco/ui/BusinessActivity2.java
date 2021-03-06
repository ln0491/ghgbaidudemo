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
import com.ghg.tobacco.bean.Business;
import com.ghg.tobacco.bean.Store;
import com.ghg.tobacco.bean.response.ResponseBusiness;
import com.ghg.tobacco.bean.response.ResponseStore;
import com.ghg.tobacco.custom_view.MapZoomImageView;
import com.ghg.tobacco.utils.DialogUtils;
import com.ghg.tobacco.utils.ResUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 商户
 */
public class BusinessActivity2 extends BaseMapActivity implements MyClusterRendered {

    private ImageView btnBack;
    private ImageView zoom_in;
    private ImageView zoom_out;
    private MapZoomImageView zoom;
    private ImageView btn_location;
    private View container_business;
    private RelativeLayout.LayoutParams mParams;
    private View container_detail;
    private TextView tv_business_name;
    private TextView tv_shop_address;
    private ResponseBusiness responseBusiness;
    private Dialog loadingDialog;
    private LatLng mLocationLatLng;
    private int currentType;
    private float zoom_country = 3.5f;
    private int zoom_provice = 8;
    private int zoom_city = 10;
    private int zoom_AREA = 12;
    private int zoom_STORE = 16;
    private Business mCountryBusiness;
    private List<Business> mProvinceBusinesses;
    private List<Business> mCityBusinesses;
    private List<Business> mAreaBusinesses;
    private List<Store> stores;
    private Store mStore;//当前点击的商店
    public BDLocationListener myListener = new MyLocationListener();
    private Marker lastSelectedStoreMark;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case Constants.Area.COUNTRY:
                case Constants.Area.PROVINCE:
                case Constants.Area.CITY:
                case Constants.Area.AREA:
                    DialogUtils.loadingDialogDismiss(loadingDialog);
                    List<Business> businesses = responseBusiness.businesses;
                    addBusinessMark(businesses, msg.what);
                    if (msg.what==Constants.Area.PROVINCE){
                        mProvinceBusinesses=businesses;
                        setZoom(zoom_country);
                    }else if (msg.what==Constants.Area.CITY){
                        mCityBusinesses=businesses;
                        setZoom(zoom_city);
                    }else if (msg.what==Constants.Area.AREA){
                        mAreaBusinesses=businesses;
                        setZoom(zoom_AREA);
                    }

                    Business business = businesses.get(0);
                    LatLng latLng = new LatLng(Double.parseDouble(business.latitude), Double.parseDouble(business.longitude));
                    setMapCenter(latLng,-1);
                    break;

                case Constants.Area.STORE:
                    DialogUtils.loadingDialogDismiss(loadingDialog);
                    addStoreMark(stores);
                    break;
            }
        }
    };


    @Override
    public int bindLayout() {
        return R.layout.activity_business;
    }

    @Override
    public void initView() {
        Constants.ClusterType.ACTIVITY_TYPE=Constants.ClusterType.BUSINESS;
        setTitle(getResources().getString(R.string.tab_business));
        setBtnMenu();

        btnBack = findViewId(R.id.btn_back);
        mMapView = findViewId(R.id.mapView);
        zoom_in = findViewId(R.id.zoom_in);
        zoom_out = findViewId(R.id.zoom_out);
        zoom = findViewId(R.id.zoom);
        btn_location = findViewId(R.id.btn_location);
        container_business = findViewId(R.id.container_business);
        tv_business_name = findViewId(R.id.tv_business_name);
        tv_shop_address = findViewId(R.id.tv_shop_address);
        container_detail = findViewId(R.id.container_detail);
        mParams = (RelativeLayout.LayoutParams) container_business.getLayoutParams();
        initMap();
        zoom.setZoomInOut(zoom_in, zoom_out);
    }

    private void initMap() {

        mBaiduMap = mMapView.getMap();
        initLocation(myListener);
        mLocationClient.start();
        hideZoomControls();
        hideLogo();


        // 定义点聚合管理类ClusterManager
        mClusterManager = new ClusterManager<MyItem>(this, mBaiduMap, this);
        // 设置地图监听，当地图状态发生改变时，进行点聚合运算
        mBaiduMap.setOnMapStatusChangeListener(mClusterManager);
        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem>() {
            @Override
            public boolean onClusterItemClick(MyItem item,Marker marker) {
                Bundle bundle = item.getBundle();
                Business business;
                int type = bundle.getInt("mType");
                if (type == Constants.Area.PROVINCE) {
                     business = (Business) bundle.getSerializable("business");
                    getResBusiness(Constants.Area.CITY,null);

                } else if (type == Constants.Area.CITY) {
                    business = (Business) bundle.getSerializable("business");
                    getResBusiness(Constants.Area.AREA,null);
                } else if (type == Constants.Area.AREA) {
                    business = (Business) bundle.getSerializable("business");
//                    getResBusiness(Constants.Area.STORE,null);
                    getResStore();
                }else if (type == Constants.Area.STORE) {
                    if (lastSelectedStoreMark!=null){
                        lastSelectedStoreMark.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.store_location));
                    }
                    lastSelectedStoreMark=marker;
                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.location));
                    mStore= (Store) bundle.getSerializable("store");
                    setBottomViewVisible(true,mStore);
                }
                return true;
            }
        });
        mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MyItem>() {
            @Override
            public boolean onClusterClick(Cluster<MyItem> cluster) {
                setZoom(zoom_provice);
                return true;
            }
        });

        mBaiduMap.setOnMarkerClickListener(mClusterManager);


    }

    @Override
    public void initListener() {
        registerReceiver(mBroadcastReceiver, new IntentFilter(Constants.BroadcastAction.GO_TO_HOME));
        btn_location.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        container_detail.setOnClickListener(this);
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
        getResBusiness(Constants.Area.PROVINCE, null);
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

        if (mBroadcastReceiver!=null){
            unregisterReceiver(mBroadcastReceiver);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
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
            case R.id.container_detail:
                intent=new Intent(BusinessActivity2.this,BusinessDetailActivity.class);
                intent.putExtra("store",mStore);
                startActivity(intent);
                break;
            case R.id.btn_menu:
                intent=new Intent(BusinessActivity2.this,BusinessListActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBeforeClusterRendered(int type) {
        LatLng latLng=null;
        Business business=null;
        if (type == Constants.Area.CITY) {
            addBusinessMark(mProvinceBusinesses, Constants.Area.PROVINCE);
            business = mProvinceBusinesses.get(0);
            latLng=new LatLng(Double.parseDouble(business.latitude),Double.parseDouble(business.longitude));
            setMapCenter(latLng,-1);
        }else if (type == Constants.Area.AREA) {
            addBusinessMark(mCityBusinesses, Constants.Area.CITY);
            business = mCityBusinesses.get(0);
            latLng=new LatLng(Double.parseDouble(business.latitude),Double.parseDouble(business.longitude));
            setMapCenter(latLng,-1);
        }else if (type == Constants.Area.STORE) {
            addBusinessMark(mAreaBusinesses, Constants.Area.CITY);
            setBottomViewVisible(false,null);
            business = mAreaBusinesses.get(0);
            latLng=new LatLng(Double.parseDouble(business.latitude),Double.parseDouble(business.longitude));
            setMapCenter(latLng,-1);
        }
    }

    @Override
    public void onClusterRendered(int type) {

    }


    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            mLocationClient.stop();
            if (bdLocation == null) {
                Toast.makeText(BusinessActivity2.this, "定位失败", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(BusinessActivity2.this, bdLocation.getAddrStr(), Toast.LENGTH_SHORT).show();
            mLocationLatLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            addLocationMark(mLocationLatLng);

        }
    }

    /**
     * 添加marker
     */
    private void addBusinessMark(List<Business> businesses, int type) {
        List<MyItem> items = new ArrayList<>();
        Constants.ClusterType.CLUSTER_TYPE = type;
        currentType = type;
        mBaiduMap.clear();
        mLocationMarkerAdded=false;

        for (int i = 0; i < businesses.size(); i++) {
            View view = LayoutInflater.from(BusinessActivity2.this).inflate(R.layout.business_mark, null);
            TextView tv_province = (TextView) view.findViewById(R.id.tv_province);
            TextView tv_business_account = (TextView) view.findViewById(R.id.tv_business_account);

            Business business = businesses.get(i);
            if (type==Constants.Area.AREA){
                 tv_business_account.setText(String.valueOf(business.num));
            }else {
                 tv_business_account.setText(business.num + "万");
            }
            tv_province.setText(business.name);
            LatLng latLng = new LatLng(Double.parseDouble(business.latitude), Double.parseDouble(business.longitude));
            Bundle bundle = new Bundle();
            bundle.putInt("mType", type);
            bundle.putSerializable("business", business);
            MyItem item = new MyItem(view, latLng, bundle);
            items.add(item);
        }
        addLocationMark(mLocationLatLng);
        mClusterManager.setlusterSize(items.size());
        mClusterManager.initBusinessData(mCountryBusiness.num+"万");
        addMarkers(items);

    }


    /**
     * 添加国省市区marker
     */
    private void addStoreMark(List<Store> stores) {
        List<MyItem> items = new ArrayList<>();
        Constants.ClusterType.CLUSTER_TYPE = Constants.Area.STORE;
        LatLng latLngCenter = null;
        for (int i = 0; i < stores.size(); i++) {
            Store store = stores.get(i);
            LatLng latLng = new LatLng(Double.parseDouble(store.latitude), Double.parseDouble(store.longitude));
            if (i == 0) {
                latLngCenter = latLng;
            }
            Bundle bundle = new Bundle();
            bundle.putInt("mType", Constants.Area.STORE);
            bundle.putSerializable("store", store);
            MyItem item = new MyItem(R.mipmap.store_location, latLng, bundle);
            items.add(item);
        }

        addLocationMark(mLocationLatLng);
        mClusterManager.setlusterSize(items.size());
        addMarkers(items);
        setZoom(zoom_STORE);
        setMapCenter(latLngCenter,-1);

    }

    private boolean mLocationMarkerAdded;

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

    private void getResBusiness(final int type, final String value) {
        loadingDialog = DialogUtils.loadingDialog(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (type==Constants.Area.PROVINCE){
                    mCountryBusiness=Data.getResBusiness(BusinessActivity2.this, type, value).businesses.get(0);
                }
                responseBusiness = Data.getResBusiness(BusinessActivity2.this, type, value);
                mHandler.sendEmptyMessage(type);
            }
        }).start();
    }

    private void getResStore() {
        loadingDialog = DialogUtils.loadingDialog(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ResponseStore responseStore = Data.getResStore(BusinessActivity2.this, null);
                stores = responseStore.stores;
                mHandler.sendEmptyMessage(Constants.Area.STORE);
            }
        }).start();

    }

    /**
     * 隐藏或显示详情
     *
     * @param isVisible
     */
    private void setBottomViewVisible(boolean isVisible, Store store) {

        if (!isVisible) {
            if (container_business.getHeight() != 0) {
                int start = container_business.getHeight();
                setBottomViewVisible(start, 0);
            }
        } else {
            if (store != null) {
                tv_business_name.setText(store.name);
                tv_shop_address.setText(store.address);
                //                tv_produce_plan.setText(String.format(ResUtils.getString(this, R.string.industry_product_account), industry.produce)
                //                        + "  " + String.format(ResUtils.getString(this, R.string.industry_plan_account), industry.planNum));
            }
            if (container_business.getHeight() == 0) {
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
                container_business.setLayoutParams(mParams);
            }
        });
        animator.start();
    }

    @Override
    public void onBackPressed() {
        //        super.onBackPressed();
        btnBack.performClick();
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
