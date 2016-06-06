package com.ghg.tobacco;

/**
 * Created by yicen.wang on 2016/5/13.
 */
public class Constants {

    public class SmokeType{
        public static final int fake=1;//假
        public static final int mix=2;//串
    }

    public static class Area{
        public static final int COUNTRY = 0;
        public static final int PROVINCE= 1;
        public static final int CITY = 2;
        public static final int AREA = 3;
        public static final int STORE = 4;
    }



    public class BroadcastAction{
        public static final String GO_TO_HOME ="1";

    }


    public static class ClusterType{
        public static  int CLUSTER_TYPE;//Cluster的类型
        public static  int ACTIVITY_TYPE;//工业公司、商户、商业公司
        public static final int INDUSTRY=1;//工业公司
        public static final int BUSINESS=2;//商户
        public static final int BUSINESS_COMPANY=3;//商业公司
    }

}
