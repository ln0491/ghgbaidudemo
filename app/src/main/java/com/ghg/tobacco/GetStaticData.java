package com.ghg.tobacco;

/**
 * Created by yicen.wang on 2016/5/11.
 */
public class GetStaticData {
	public static final int TYPE_DAY = 0;
	public static final int TYPE_MONTH = 1;
	public static final int TYPE_SEASON = 2;
	public static final int TYPE_YEAR = 3;

    public static final int TYPE_INDUSTRY = 0;
    public static final int TYPE_COMPANY= 1;
    public static final int TYPE_BUSINESS = 2;

    public static String[] dayArr = {"6日", "7日", "8日", "9日", "10日", "11日", "12日"};
    public static String[] monthArr = {"12月", "1月", "2月", "3月", "4月", "5月"};
//        public static String[] monthArr = {"12月", "1月", "2月", "3月", "4月", "5月", "6月", "7月","8月", "9月", "10月", "11月","13月", "14月", "15月"};
    public static String[] seasonArr = {"2015Q1", "2015Q2", "2015Q3", "2015Q4", "2016Q1", "2016Q2"};
    public static String[] yearArr = {"2011年", "2012年", "2013年", "2014年", "2015年", "2016年"};
    public static String[] cigaretteArr = {"黄鹤楼（好运）", "黄鹤楼（硬彩）", "黄鹤楼（硬1916）", "白沙（软精品）",
            "白沙（和天下）", "黄金叶（小天叶）", "双喜（珍藏）", "黄金叶（天香细支）"};
    public static String[] timeArr = {"2015-10-11 12:13:00", "2016-01-10 13:14:00", "2016-02-02 10:10:00", "2015-12-13  09:50:00",
            "2015-12-01 10:20:00", "2016-04-01 09:48:00", "2016-05-01 12:13:00", "2016-03-30 11:48:00"};
    public static String[] areaArr = {"广达路", "北京路", "中医院分部", "公元前路", "农讲所","福保街道","红桂路","荣华路"};
    public static String[] shopArr = {"红卫商店", "荣丰粮油店", "荣兴记商行", "茂辉烟酒商行", "菜盛商行", "珍锦商店", "鼎顺商行", "简易好商行"};
    public static String[] addressArr = {"宝岗路59号104", "罗湖区笋岗村156栋104号", "罗湖区笋岗村117栋", "深惠路佳兆业大都汇L102号铺(布吉地铁站)",
            "罗湖区红桂路1008号宝泉庄2栋1楼", "罗湖区泥岗东路雅仕居1108", "深圳市福田区福保街道皇岗上围二村42号", "荣华路366号附近"};

    public static final double[] DATA_S_EST = {35,19,20,18,28,30,33,24};
    public static final double[] DATA_S_ER = {350,320,360,320,400,410,380,355};
    public static final double[] DATA_S = {2000,1800,1920,2210,2640,2308,2577,2477};
    public static final double[] DATA_M = {5800,6010,5980,6108,6218,6210,6326,5860};
    public static final double[] DATA_B = {11090,12080,11840,12100,11690,12304,13002,11908};
    public static final double[] DATA_B_ER = {58090,61080,59840,62100,51690,59304,58002,63908};
    public static final double[] DATA_B_EST = {260010,271080,259840,282100,271690,269000,258000,276401};


//    public static  List<BusinessCompany> businessCompanyProvinceList ;
//    public static Map<String,List<BusinessCompany>> businessCompanyCityMap ;
}
