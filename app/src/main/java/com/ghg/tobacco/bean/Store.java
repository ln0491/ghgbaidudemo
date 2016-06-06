package com.ghg.tobacco.bean;

import java.io.Serializable;

/**
 * Created by yicen.wang on 2016/5/24.
 */
public class Store implements Serializable{
    public int id;//id
    public int type;//商户类型；0：个人
    public String name;//
    public String province;//省
    public String city;//市
    public String area;//区
    public String longitude;//经度
    public String latitude;//纬度
    public String supplier;//供货单位
    public String contactName;//联系人
    public String tel;//联系电话
    public String address;//地址
    public String licence;//许可证号
    public String licenceValidStart;//证件有效期开始
    public String licenceValidEnd;//证件有效期截止
    public String licenceIssueDate;//发证日期
    public String businessScope;//经营范围
    public String pic;//图片
    public String priceType;//类型
    public String provinceName;
    public String cityName;
    public String areaName;
}
