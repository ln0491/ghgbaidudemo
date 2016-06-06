package com.baidu.mapapi.clusterutil.clustering.view;

/**
 * @author $Author
 * @version $Rev$
 * @time 2016/5/31 10:16
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */

import android.os.Bundle;
import android.view.View;

import com.baidu.mapapi.clusterutil.clustering.ClusterItem;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.model.LatLng;

/**
 * 每个Marker点，包含Marker点坐标以及图标
 */
public class MyItem implements ClusterItem {
    private final LatLng mPosition;
    private View view;
    private Bundle bundle;

    public MyItem(LatLng latLng,Bundle bundle,View view) {
        mPosition = latLng;
        this.bundle=bundle;
        this.view=view;
    }


    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    public Bundle getInfor(){
        return  bundle;
    }

    @Override
    public BitmapDescriptor getBitmapDescriptor() {
        return BitmapDescriptorFactory
                .fromView(view);
    }
}

