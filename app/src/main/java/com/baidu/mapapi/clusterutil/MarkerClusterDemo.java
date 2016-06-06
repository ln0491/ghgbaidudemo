///*
// * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
// */
//package com.baidu.mapapi.clusterutil;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.widget.Toast;
//
//import com.baidu.mapapi.clusterutil.clustering.Cluster;
//import com.baidu.mapapi.clusterutil.clustering.ClusterItem;
//import com.baidu.mapapi.clusterutil.clustering.ClusterManager;
//import com.baidu.mapapi.map.BaiduMap;
//import com.baidu.mapapi.map.BaiduMap.OnMapLoadedCallback;
//import com.baidu.mapapi.map.BitmapDescriptor;
//import com.baidu.mapapi.map.BitmapDescriptorFactory;
//import com.baidu.mapapi.map.MapStatus;
//import com.baidu.mapapi.map.MapStatusUpdateFactory;
//import com.baidu.mapapi.map.MapView;
//import com.baidu.mapapi.model.LatLng;
//import com.ghg.tobacco.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MarkerClusterDemo extends Activity implements OnMapLoadedCallback {
//
//    MapView mMapView;
//    BaiduMap mBaiduMap;
//    MapStatus ms;
//    private ClusterManager<MyItem> mClusterManager;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_marker_cluster_demo);
//        mMapView = (MapView) findViewById(R.id.bmapView);
//        ms = new MapStatus.Builder().target(new LatLng(39.914935, 116.403119)).zoom(8).build();
//        mBaiduMap = mMapView.getMap();
//        mBaiduMap.setOnMapLoadedCallback(this);
//        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
//        // 定义点聚合管理类ClusterManager
//        mClusterManager = new ClusterManager<MyItem>(this, mBaiduMap);
//        // 添加Marker点
//        addMarkers();
//        // 设置地图监听，当地图状态发生改变时，进行点聚合运算
//        mBaiduMap.setOnMapStatusChangeListener(mClusterManager);
//        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem>() {
//            @Override
//            public boolean onClusterItemClick(MyItem item) {
//                Toast.makeText(getApplicationContext(), "onClusterItemClick=", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });
//        mClusterManager.setOnClusterItemInfoWindowClickListener(new ClusterManager.OnClusterItemInfoWindowClickListener<MyItem>() {
//            @Override
//            public void onClusterItemInfoWindowClick(MyItem item) {
//                Toast.makeText(MarkerClusterDemo.this, "setOnClusterItemInfoWindowClickListener", Toast.LENGTH_SHORT).show();
//            }
//        });
//        mClusterManager.setOnClusterInfoWindowClickListener(new ClusterManager.OnClusterInfoWindowClickListener<MyItem>() {
//            @Override
//            public void onClusterInfoWindowClick(Cluster<MyItem> cluster) {
//                Toast.makeText(MarkerClusterDemo.this, "setOnClusterInfoWindowClickListener", Toast.LENGTH_SHORT).show();
//            }
//        });
//        mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MyItem>() {
//            @Override
//            public boolean onClusterClick(Cluster<MyItem> cluster) {
//                Toast.makeText(MarkerClusterDemo.this, "onClusterClick", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });
//
//        mBaiduMap.setOnMarkerClickListener(mClusterManager);
//
//    }
//
//    @Override
//    protected void onPause() {
//        mMapView.onPause();
//        super.onPause();
//    }
//
//    @Override
//    protected void onResume() {
//        mMapView.onResume();
//        super.onResume();
//    }
//
//    @Override
//    protected void onDestroy() {
//        mMapView.onDestroy();
//        super.onDestroy();
//    }
//
//    /**
//     * 向地图添加Marker点
//     */
//    public void addMarkers() {
//        // 添加Marker点
//        LatLng llA = new LatLng(39.963175, 116.400244);
//        LatLng llB = new LatLng(39.942821, 116.369199);
//        LatLng llC = new LatLng(39.939723, 116.425541);
//        LatLng llD = new LatLng(39.906965, 116.401394);
//        LatLng llE = new LatLng(39.956965, 116.331394);
//        LatLng llF = new LatLng(39.886965, 116.441394);
//        LatLng llG = new LatLng(39.996965, 116.411394);
//
//        List<MyItem> items = new ArrayList<MyItem>();
//        items.add(new MyItem(llA,"llA"));
//        items.add(new MyItem(llB,"llB"));
//        items.add(new MyItem(llC,"llC"));
//        items.add(new MyItem(llD,"llD"));
//        items.add(new MyItem(llE,"llE"));
//        items.add(new MyItem(llF,"llF"));
//        items.add(new MyItem(llG,"llG"));
//
//        mClusterManager.addItems(items);
//
//    }
//
//    /**
//     * 每个Marker点，包含Marker点坐标以及图标
//     */
//    public class MyItem implements ClusterItem {
//        private final LatLng mPosition;
//        private String infor;
//        private LayoutInflater mInflater;
//
//        public MyItem(LatLng latLng,String infor) {
//            mPosition = latLng;
//            this.infor=infor;
//            mInflater=LayoutInflater.from(MarkerClusterDemo.this);
//        }
//
//
//        @Override
//        public LatLng getPosition() {
//            return mPosition;
//        }
//
//        public String getInfor(){
//            return  infor;
//        }
//
//        @Override
//        public BitmapDescriptor getBitmapDescriptor() {
//            return BitmapDescriptorFactory
//                    .fromView(mInflater.inflate(R.layout.mark_business_city,null));
//        }
//    }
//
//    @Override
//    public void onMapLoaded() {
//        // TODO Auto-generated method stub
//        ms = new MapStatus.Builder().zoom(9).build();
//        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
//    }
//}