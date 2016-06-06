package com.ghg.tobacco.bean;

import java.util.List;
import java.util.Map;

/**
 * Created by yicen.wang on 2016/5/17.
 */
public class FormData {

    public int space;//Y轴间隙数
    public String[] dataKey;//Y数据格式
    public List<String> label;//X轴数据
    public Map<String,List<Double>> data;//Y轴数据
}
