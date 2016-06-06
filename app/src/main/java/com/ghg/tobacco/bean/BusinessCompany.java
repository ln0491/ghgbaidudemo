package com.ghg.tobacco.bean;


import java.io.Serializable;

/**
 * Created by yicen.wang on 2016/5/16.
 */
public class BusinessCompany implements Serializable{
    public int id;//主键
    public int type;
    public String name;//名称
    public String province;//省
    public String city;//市
    public String longitude;//经度
    public String latitude;//纬度
    public String tell;//电话
    public String fax;//传真
    public String url;//网址
    public String address;//地址
    public String postcode;//邮政编码
    public double planNum;//计划数量
    public double factNum;//实际数量

    public double purchase;//进货
    public double sale;//销售
    public double inventory;//存货
    public int inTransitCar;//在途车辆数
    public int inTransitNum;//在途数

    public int quantity;//共多少家烟草专卖局

    public String provinceName;//省
    public String cityName;//市

    @Override
    public String toString() {
        return "BusinessCompany{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", tell='" + tell + '\'' +
                ", fax='" + fax + '\'' +
                ", url='" + url + '\'' +
                ", address='" + address + '\'' +
                ", postcode='" + postcode + '\'' +
                ", planNum=" + planNum +
                ", factNum=" + factNum +
                ", purchase=" + purchase +
                ", sale=" + sale +
                ", inventory=" + inventory +
                ", inTransitCar=" + inTransitCar +
                ", inTransitNum=" + inTransitNum +
                ", quantity=" + quantity +
                ", provinceName='" + provinceName + '\'' +
                ", cityName='" + cityName + '\'' +
                '}';
    }
}
