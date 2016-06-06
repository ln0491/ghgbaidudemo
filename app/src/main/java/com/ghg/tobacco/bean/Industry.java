package com.ghg.tobacco.bean;

import java.io.Serializable;

/**
 * Created by yicen.wang on 2016/5/23.
 */
public class Industry implements Serializable{
    public Integer id;//主键
    public String name;//名称
    public String province;//省
    public String longitude;//经度
    public String latitude;//纬度
    public String tell;//电话
    public String fax;//传真
    public String url;//网址
    public String postcode;//邮编
    public String address;//地址

    public Double planNum;//计划数量
    public Double factNum;//实际数量

    public Double produce;//产量
    public Double sale;//销售
    public Double inventory;//存货
    public Integer inTransitCar;//在途车辆数
    public Integer inTransitNum;//在途数

    public Integer quantity;//共多少家工业公司/烟厂

    public String provinceName;//省

    public Integer smokeCompanyId;//工业公司ID
}
