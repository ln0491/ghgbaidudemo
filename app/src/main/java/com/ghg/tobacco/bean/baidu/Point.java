package com.ghg.tobacco.bean.baidu;

import java.io.Serializable;

/**
 * Created by yicen.wang on 2016/5/24.
 */
public class Point implements Serializable{

    public String loc_time;
    public double[] location;//经度；纬度
    public int direction;//方向
    public double speed;//速度
    public int radius;//角度
}
