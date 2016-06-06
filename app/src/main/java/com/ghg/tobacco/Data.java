package com.ghg.tobacco;

import android.content.Context;

import com.alibaba.fastjson.JSONArray;
import com.ghg.tobacco.bean.Business;
import com.ghg.tobacco.bean.BusinessCompany;
import com.ghg.tobacco.bean.Car;
import com.ghg.tobacco.bean.CarForm;
import com.ghg.tobacco.bean.CheckReportDetail;
import com.ghg.tobacco.bean.Cigarette;
import com.ghg.tobacco.bean.FormData;
import com.ghg.tobacco.bean.Industry;
import com.ghg.tobacco.bean.Invoicing;
import com.ghg.tobacco.bean.Store;
import com.ghg.tobacco.bean.baidu.Entity;
import com.ghg.tobacco.bean.baidu.Point;
import com.ghg.tobacco.bean.baidu.ResponseEntity;
import com.ghg.tobacco.bean.response.ResponseBigData;
import com.ghg.tobacco.bean.response.ResponseBusiness;
import com.ghg.tobacco.bean.response.ResponseBusinessCompany;
import com.ghg.tobacco.bean.response.ResponseCar;
import com.ghg.tobacco.bean.response.ResponseCheck;
import com.ghg.tobacco.bean.response.ResponseCheckReportDetail;
import com.ghg.tobacco.bean.response.ResponseCigarette;
import com.ghg.tobacco.bean.response.ResponseCompanyAmount;
import com.ghg.tobacco.bean.response.ResponseCompanyInventory;
import com.ghg.tobacco.bean.response.ResponseIndustry;
import com.ghg.tobacco.bean.response.ResponseReport;
import com.ghg.tobacco.bean.response.ResponseReportTop5;
import com.ghg.tobacco.bean.response.ResponseStore;
import com.ghg.tobacco.bean.response.ResponseStoreForm;
import com.ghg.tobacco.bean.test.BusinessCompanyData;
import com.ghg.tobacco.bean.test.IndustryData;
import com.ghg.tobacco.utils.FileUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * Created by yicen.wang on 2016/5/17.
 */
public class Data {

    private static String getToday() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd", Locale.CHINA);
        return sdf.format(new Date());
    }

    /**
     * 稽查举报--稽查
     *
     * @param type
     * @return
     */
    public static ResponseCheck getResCheck(int type) {
        ResponseCheck result = new ResponseCheck();
        result.date = getToday();// 日期
        result.check = 332;// 稽查数
        result.fakes = 38;// 假货数
        result.mix = 14;// 串货数
        FormData formData = getCheck(type);
        result.formData = formData;
        return result;
    }

    /**
     * 稽查举报--举报
     *
     * @param type
     * @return
     */
    public static ResponseReport getResReport(int type) {
        ResponseReport result = new ResponseReport();
        result.date = getToday();// 日期
        result.report = 100;// 验真伪举报数
        FormData formData = getReport(type);
        result.formData = formData;
        return result;
    }

    /**
     * 稽查统计
     *
     * @param type
     * @return
     */
    private static FormData getCheck(int type) {
        Random random = new Random();
        FormData result = new FormData();
        String[] dataKey = {"稽查量", "假烟量", "串货量"};

        Map<String, List<Double>> data = new HashMap<>();
        List<Double> check = new ArrayList<>();
        List<Double> fakes = new ArrayList<>();
        List<Double> mix = new ArrayList<>();

        switch (type) {
            case GetStaticData.TYPE_DAY:
                result.label = Arrays.asList(GetStaticData.dayArr);
                for (int i = 0; i < result.label.size(); i++) {
                    check.add(GetStaticData.DATA_S[random.nextInt(8)]);
                    fakes.add(GetStaticData.DATA_S[random.nextInt(8)] - 1000);
                    mix.add(GetStaticData.DATA_S_ER[random.nextInt(8)]);
                }
                break;
            case GetStaticData.TYPE_MONTH:
                result.label = Arrays.asList(GetStaticData.monthArr);
                for (int i = 0; i < result.label.size(); i++) {
                    check.add(GetStaticData.DATA_M[random.nextInt(8)]);
                    fakes.add(GetStaticData.DATA_S[random.nextInt(8)]);
                    mix.add(GetStaticData.DATA_S[random.nextInt(8)] - 500);
                }
                break;
            case GetStaticData.TYPE_YEAR:
                result.label = Arrays.asList(GetStaticData.yearArr);
                for (int i = 0; i < result.label.size(); i++) {
                    check.add(GetStaticData.DATA_B_ER[random.nextInt(8)]);
                    fakes.add(GetStaticData.DATA_B[random.nextInt(8)] * 3);
                    mix.add(GetStaticData.DATA_B[random.nextInt(8)] * 2);
                }
                break;
        }
        result.space = 5;
        data.put(dataKey[0], check);
        data.put(dataKey[1], fakes);
        data.put(dataKey[2], mix);

        result.dataKey = dataKey;
        result.data = data;
        return result;
    }

    /**
     * 举报统计
     *
     * @param type
     * @return
     */
    private static FormData getReport(int type) {
        Random random = new Random();
        FormData result = new FormData();
        String[] dataKey = {"举报量"};
        Map<String, List<Double>> data = new HashMap<>();
        List<Double> report = new ArrayList<>();
        switch (type) {
            case GetStaticData.TYPE_DAY:
                result.label = Arrays.asList(GetStaticData.dayArr);
                for (int i = 0; i < result.label.size(); i++) {
                    report.add(GetStaticData.DATA_S_ER[random.nextInt(8)]);
                }
                break;
            case GetStaticData.TYPE_MONTH:
                result.label = Arrays.asList(GetStaticData.monthArr);
                for (int i = 0; i < result.label.size(); i++) {
                    report.add(GetStaticData.DATA_S[random.nextInt(8)]);
                }
                break;
            case GetStaticData.TYPE_YEAR:
                result.label = Arrays.asList(GetStaticData.yearArr);
                for (int i = 0; i < result.label.size(); i++) {
                    report.add(GetStaticData.DATA_B[random.nextInt(8)]);
                }
                break;
        }
        result.space = 2;
        data.put(dataKey[0], report);
        result.dataKey = dataKey;
        result.data = data;
        return result;
    }

    /**
     * 稽查举报--地区和商店举报Top5
     *
     * @param type
     * @param value
     * @return
     */
    public static ResponseReportTop5 getResReportTop5(int type, int value) {
        Random random = new Random();
        ResponseReportTop5 result = new ResponseReportTop5();
        FormData staArea = new FormData();
        FormData staShop = new FormData();
        String[] dataKey = {"2016年5月"};
        Map<String, List<Double>> dataArea = new HashMap<>();
        Map<String, List<Double>> dataShop = new HashMap<>();

        List<String> areas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            areas.add(GetStaticData.areaArr[random.nextInt(8)]);
        }
        List<String> shops = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            shops.add(GetStaticData.shopArr[random.nextInt(8)]);
        }
        staArea.label = areas;
        staShop.label = shops;

        List<Double> reportArea = new ArrayList<>();
        List<Double> reportShop = new ArrayList<>();

        switch (type) {
            case GetStaticData.TYPE_MONTH:
                for (int i = 0; i < 5; i++) {
                    reportArea.add(GetStaticData.DATA_S[random.nextInt(8)]);
                    reportShop.add(GetStaticData.DATA_S_ER[random.nextInt(8)]);
                }

                break;
            case GetStaticData.TYPE_YEAR:
                for (int i = 0; i < 5; i++) {
                    reportArea.add(GetStaticData.DATA_M[random.nextInt(8)]);
                    reportShop.add(GetStaticData.DATA_S[random.nextInt(8)]);
                }
                break;
        }
        dataArea.put(dataKey[0], reportArea);
        dataShop.put(dataKey[0], reportShop);

        staArea.data = dataArea;
        staArea.dataKey = dataKey;
        staShop.data = dataArea;
        staShop.dataKey = dataKey;
        result.reportArea = staArea;
        result.reportShop = staShop;

        return result;
    }

    /**
     * 稽查举报--稽查/举报详情
     *
     * @return
     */
    public static ResponseCheckReportDetail getResCheckDetail() {
        Random random = new Random();
        ResponseCheckReportDetail result = new ResponseCheckReportDetail();
        List<CheckReportDetail> detail = new ArrayList<>();// 详情

        for (int i = 0; i < 12; i++) {
            CheckReportDetail t = new CheckReportDetail();
            t.id = i;
            t.checktime = GetStaticData.timeArr[random.nextInt(8)];// 时间
            t.checkResult = random.nextInt(2) + 1;// 类型（假、串）
            t.cigaretteId = random.nextInt(8);// 香烟编号
            t.cigaretteName = GetStaticData.cigaretteArr[t.cigaretteId];// 香烟名
            t.shopId = random.nextInt(8);// 商店编号
            t.shopName = GetStaticData.shopArr[t.shopId];// 商店名称
            t.shopAddress = GetStaticData.addressArr[t.shopId];// 商店地址
            t.shopMobile = "123456";// 商店电话

            detail.add(t);
        }

        result.detail = detail;
        return result;
    }

    /**
     * 产量
     *
     * @param dateType 时间类型
     * @return
     */
    public static ResponseBigData getResProduce(int dateType) {
        ResponseBigData result = new ResponseBigData();
        result.formData = getPlanCompleteAmount(dateType);
        return result;
    }

    /**
     * 大数据--销量
     *
     * @param dateType 时间类型
     * @param type     公司类型（0：工业公司；1：商业公司；2：商户）
     * @return
     */
    public static ResponseBigData getResSale(int dateType, int type) {
        ResponseBigData result = new ResponseBigData();
        result.formData = getPlanCompleteAmount(dateType);
        return result;
    }

    /**
     * 大数据--库存
     *
     * @param dateType
     * @param type     公司类型（0：工业公司；1：商业公司；2：商户）
     * @return
     */
    public static ResponseBigData getResInventory(int dateType, int type) {
        ResponseBigData result = new ResponseBigData();
        result.formData = getInventory(type);
        return result;
    }

    /**
     * 计划完成量
     *
     * @param type
     * @return
     */
    public static FormData getPlanCompleteAmount(int type) {
        Random random = new Random();
        FormData result = new FormData();
        String[] dataKey = {"完成量", "计划量"};
        Map<String, List<Double>> data = new HashMap<>();
        List<Double> plan = new ArrayList<>();
        List<Double> complete = new ArrayList<>();

        switch (type) {
            case GetStaticData.TYPE_MONTH:
                result.label = Arrays.asList(GetStaticData.monthArr);
                for (int i = 0; i < result.label.size(); i++) {
                    plan.add(GetStaticData.DATA_S_ER[random.nextInt(8)]);
                    complete.add(plan.get(i) - 100);
                }
                break;
            case GetStaticData.TYPE_SEASON:
                result.label = Arrays.asList(GetStaticData.seasonArr);
                for (int i = 0; i < result.label.size(); i++) {
                    plan.add(GetStaticData.DATA_S[random.nextInt(8)]);
                    complete.add(plan.get(i) - 500);
                }
                break;
            case GetStaticData.TYPE_YEAR:
                result.label = Arrays.asList(GetStaticData.yearArr);
                for (int i = 0; i < result.label.size(); i++) {
                    plan.add(GetStaticData.DATA_M[random.nextInt(8)]);
                    complete.add(plan.get(i) - 1580);
                }
                break;
        }
        result.space = 5;
        data.put(dataKey[0], complete);
        data.put(dataKey[1], plan);
        result.dataKey = dataKey;
        result.data = data;

        return result;
    }

    /**
     * 库存
     *
     * @param type
     * @return
     */
    public static FormData getInventory(int type) {
        Random random = new Random();
        FormData result = new FormData();
        String[] dataKey = {"库存量"};
        Map<String, List<Double>> data = new HashMap<>();

        List<Double> inv = new ArrayList<>();

        switch (type) {
            case GetStaticData.TYPE_INDUSTRY:
                result.label = Arrays.asList(GetStaticData.monthArr);
                for (int i = 0; i < result.label.size(); i++) {
                    inv.add(GetStaticData.DATA_S_ER[random.nextInt(8)]);
                }
                break;
            case GetStaticData.TYPE_COMPANY:
                result.label = Arrays.asList(GetStaticData.monthArr);
                for (int i = 0; i < result.label.size(); i++) {
                    inv.add(GetStaticData.DATA_S_ER[random.nextInt(8)]);
                }
                break;
            case GetStaticData.TYPE_BUSINESS:
                result.label = Arrays.asList(GetStaticData.monthArr);
                for (int i = 0; i < result.label.size(); i++) {
                    inv.add(GetStaticData.DATA_S_EST[random.nextInt(8)]);
                }
                break;
        }
        result.space = 2;
        data.put(dataKey[0], inv);
        result.dataKey = dataKey;
        result.data = data;
        return result;
    }

    /**
     * 商业公司--地图数据
     *
     * @param context
     * @param type    请求数据级别
     * @param value   省份（获得城市级别）
     * @return
     */
    public static List<BusinessCompany> getResBusinessCompany(Context context, int type, String value) {
        Random random = new Random();
//        ResponseBusinessCompany result = new ResponseBusinessCompany();
        List<BusinessCompany> company = new ArrayList<>();

        BusinessCompanyData data = null;
        switch (type) {
            case Constants.Area.COUNTRY:
                BusinessCompany country = new BusinessCompany();
//                country.quantity=data.provinceList.size(); //共多少家烟草专卖局
                country.quantity = 31; //共多少家烟草专卖局
                country.planNum = 5000.0;//计划多少箱
                country.factNum = 4780.0;//实际多少箱
                country.longitude = "116.470834";
                country.latitude = "39.875466";
                company.add(country);
                break;
            case Constants.Area.PROVINCE:
//                data=getBusinessCompanyData(context);
                company = getProvinceBusinessCompanyData(context);
                for (BusinessCompany temp : company) {
//                    temp.quantity=data.cityMap.get(temp.province).size(); //共多少家烟草专卖局
                    temp.planNum = GetStaticData.DATA_S_ER[random.nextInt(8)] + 200.0;//计划多少箱
                    temp.factNum = GetStaticData.DATA_S_ER[random.nextInt(8)] + 120.0;//实际多少箱
                    temp.purchase = GetStaticData.DATA_S_ER[random.nextInt(8)] * 2.0;//进货
                    temp.sale = GetStaticData.DATA_S_ER[random.nextInt(8)] + 220.0;//销售
                    temp.inventory = GetStaticData.DATA_B[random.nextInt(8)] + 0.0;//存货
//                    company.add(temp);
                }

                break;
            case Constants.Area.CITY:

                List<BusinessCompany> companies = getCityBusinessCompanysData(context);
                int[] inTransitCar = {30, 12, 20, 18, 28, 30, 27, 24};
                if (companies != null && companies.size() != 0) {
                    for (BusinessCompany temp : companies) {
                        if (temp.province.equals(value)) {
                            temp.planNum = GetStaticData.DATA_S_EST[random.nextInt(8)] + 60.0;//计划多少箱
                            temp.factNum = GetStaticData.DATA_S_EST[random.nextInt(8)] + 53.0;//实际多少箱
                            temp.purchase = GetStaticData.DATA_S_EST[random.nextInt(8)] + 50.0;//进货
                            temp.sale = GetStaticData.DATA_S_ER[random.nextInt(8)] + 50.0;//销售
                            temp.inventory = GetStaticData.DATA_S[random.nextInt(8)] - 120.0;//存货
                            temp.inTransitCar = inTransitCar[random.nextInt(8)] - 5;//在途车辆数
                            temp.inTransitNum = inTransitCar[random.nextInt(8)] * 2;//在途数
                            company.add(temp);
                        }
                    }
                }

                break;
        }
//        result.company=company;

        return company;
    }

    /*private static BusinessCompanyData getBusinessCompanyData(Context context) {

        String provinceJsonStr = FileUtils.readAssetsFile(context, "business_company_province.json");
        String cityJsonStr = FileUtils.readAssetsFile(context, "business_company_city.json");

        BusinessCompanyData result = new BusinessCompanyData();
        List<BusinessCompany> provinceList = new ArrayList<>();
        Map<String, List<BusinessCompany>> cityMap = new HashMap<>();
        if (provinceJsonStr != null && !"".equalsIgnoreCase(provinceJsonStr.trim())) {
            provinceList = JSONArray.parseArray(provinceJsonStr, BusinessCompany.class);
            List<BusinessCompany> allCityList = JSONArray.parseArray(cityJsonStr, BusinessCompany.class);

            for (BusinessCompany temp : provinceList) {

                List<BusinessCompany> cityList = new ArrayList<>();
                if (cityMap.containsKey(temp.province)) {
                    cityList = cityMap.get(temp.province);
                }
                for (BusinessCompany t : allCityList) {
                    if (temp.province.equals(t.province)) {
                        cityList.add(t);
                    }
                }
                cityMap.put(temp.province, cityList);
            }
        }

        result.provinceList = provinceList;
        result.cityMap = cityMap;

        return result;
    }*/


    public static List<BusinessCompany> getProvinceBusinessCompanyData(Context context) {

        String provinceJsonStr = FileUtils.readAssetsFile(context, "resBusinessCompanyMapByProvince.json");

        ResponseBusinessCompany result = JSONArray.parseObject(provinceJsonStr, ResponseBusinessCompany.class);
        return result.company;
    }

    public static List<BusinessCompany> getCityBusinessCompanysData(Context context) {

        String cityJsonStr = FileUtils.readAssetsFile(context, "resBusinessCompanyMapByCity.json");

        ResponseBusinessCompany result= JSONArray.parseObject(cityJsonStr, ResponseBusinessCompany.class);


        return result.company;
    }


    /**
     * 商业/工业公司--进/销 图表数据
     *
     * @param type
     * @param isSale 0：进货；1：销售
     * @param value
     * @return
     */
    public static ResponseCompanyAmount getResBusinessCompanyAmount(int type, int isSale, String value) {
        Random random = new Random();
        ResponseCompanyAmount result = new ResponseCompanyAmount();
        FormData formData = new FormData();

        String[] dataKey = new String[2];
        switch (isSale) {
            case 0:
                dataKey[0] = "实际进货";
                dataKey[1] = "计划进货";
                break;
            case 1:
                dataKey[0] = "实际销售";
                dataKey[1] = "计划销售";
                break;
            case 2:
                dataKey[0]="实际产量";
                dataKey[1]="计划产量";
                break;

        }
        Map<String,List<Double>> data = new HashMap<>();

        List<Double> plan = new ArrayList<>();
        List<Double> fact = new ArrayList<>();

        switch(type){
            case Constants.Area.PROVINCE:
                formData.label = Arrays.asList(GetStaticData.seasonArr);
                result.plan=GetStaticData.DATA_S_ER[random.nextInt(8)]+200;
                result.fact=result.plan-90.0;

                for (int i = 0; i < formData.label.size(); i++) {
                    plan.add(GetStaticData.DATA_S_ER[random.nextInt(8)]+200);
                    fact.add(plan.get(i) - 100);
                }
                break;
            case Constants.Area.CITY:
                formData.label = Arrays.asList(GetStaticData.monthArr);
                result.plan=GetStaticData.DATA_S_ER[random.nextInt(8)];
                result.fact=result.plan-11.0;

                for (int i = 0; i < formData.label.size(); i++) {
                    plan.add(GetStaticData.DATA_S_ER[random.nextInt(8)]-11);
                    fact.add(plan.get(i) - 21);
                }
                break;
            case Constants.Area.AREA:
                formData.label = Arrays.asList(GetStaticData.dayArr);
                result.plan=GetStaticData.DATA_S_EST[random.nextInt(8)];
                result.fact=result.plan-11.0;

                for (int i = 0; i < formData.label.size(); i++) {
                    plan.add(GetStaticData.DATA_S_EST[random.nextInt(8)]-5);
                    fact.add(plan.get(i) - 6);
                }
                break;
        }
        formData.space = 5;
        data.put(dataKey[0], fact);
        data.put(dataKey[1], plan);
        formData.dataKey = dataKey;
        formData.data = data;
        result.formData = formData;

        return result;
    }

    /**
     * 商业/工业公司--存 图表数据
     *
     * @param type
     * @param value
     * @return
     */
    public static ResponseCompanyInventory getResBusinessCompanyInventory(int type, String value) {
        Random random = new Random();
        ResponseCompanyInventory result = new ResponseCompanyInventory();
        List<Invoicing> invoicing = new ArrayList<>();

        switch (type) {
            case Constants.Area.PROVINCE:
                result.inventory = (int) GetStaticData.DATA_B[random.nextInt(8)];
                for (int i = 0; i < 5; i++) {
                    Invoicing temp = new Invoicing();
                    temp.batch = "20150101000" + i % 2 + i;
                    int index = random.nextInt(8);
                    temp.purchase = (int) (GetStaticData.DATA_S_ER[index] + 200);
                    temp.produce = temp.purchase;
                    temp.sale = (int) (GetStaticData.DATA_S_ER[index] - i * 5);
                    temp.inventory = temp.produce - temp.sale;
                    invoicing.add(temp);
                }

                break;
            case Constants.Area.CITY:
                result.inventory = (int) GetStaticData.DATA_S[random.nextInt(8)];
                for (int i = 0; i < 5; i++) {
                    Invoicing temp = new Invoicing();
                    temp.batch = "20150101000" + i % 2 + i;
                    int index = random.nextInt(8);
                    temp.purchase = (int) (GetStaticData.DATA_S_ER[index] - 100);
                    temp.produce = temp.purchase;
                    temp.sale = (int) (GetStaticData.DATA_S_EST[index] + i * 5);
                    temp.inventory = temp.produce - temp.sale;
                    invoicing.add(temp);
                }
                break;
        }
        result.invoicing = invoicing;

        return result;
    }

    /**
     * 工业公司--地图数据
     *
     * @param context
     * @param type    请求数据级别
     * @param value   省份（获得城市级别）
     * @return
     */
    public static ResponseIndustry getResIndustry(Context context, int type, String value) {
        Random random = new Random();
        ResponseIndustry result = new ResponseIndustry();
        List<Industry> company = new ArrayList<>();

        IndustryData data = getIndustryData(context);
        switch (type) {
            case Constants.Area.COUNTRY:
                Industry country = new Industry();
                country.quantity = 19; //共多少家工业公司
                country.planNum = 5000.0;//计划多少箱
                country.factNum = 4780.0;//实际多少箱
                country.longitude = "116.470834";
                country.latitude = "39.875466";
                company.add(country);
                break;
            case Constants.Area.PROVINCE:

                for (Industry temp : data.list) {
                    temp.quantity = data.map.get(temp.province).size(); //共多少家烟厂
                    temp.planNum = GetStaticData.DATA_S_ER[random.nextInt(8)] + 200.0;//计划多少箱
                    temp.factNum = GetStaticData.DATA_S_ER[random.nextInt(8)] + 120.0;//实际多少箱
                    temp.produce = GetStaticData.DATA_S_ER[random.nextInt(8)] * 2.0;//产量
                    temp.sale = GetStaticData.DATA_S_ER[random.nextInt(8)] + 220.0;//销售
                    temp.inventory = GetStaticData.DATA_B[random.nextInt(8)] + 0.0;//存货
                    company.add(temp);
                }

                break;
            case Constants.Area.CITY:
                int[] inTransitCar = {30, 12, 20, 18, 28, 30, 27, 24};
                String[] values = value.split(",");
                for(String t:values){
                    for (Industry temp : data.map.get(t)) {
                        temp.planNum = GetStaticData.DATA_S_EST[random.nextInt(8)] + 60.0;//计划多少箱
                        temp.factNum = GetStaticData.DATA_S_EST[random.nextInt(8)] + 53.0;//实际多少箱
                        temp.produce = GetStaticData.DATA_S_EST[random.nextInt(8)] + 50.0;//产量
                        temp.sale = GetStaticData.DATA_S_ER[random.nextInt(8)] + 50.0;//销售
                        temp.inventory = GetStaticData.DATA_S[random.nextInt(8)] - 120.0;//存货
                        temp.inTransitCar = inTransitCar[random.nextInt(8)] - 5;//在途车辆数
                        temp.inTransitNum = inTransitCar[random.nextInt(8)] * 2;//在途数
                        company.add(temp);
                    }
                }
                break;
        }
        result.company = company;

        return result;
    }

    public static IndustryData getIndustryData(Context context) {

        String companyJsonStr = FileUtils.readAssetsFile(context, "smoke_company.json");
        String factoryJsonStr = FileUtils.readAssetsFile(context, "smoke_factory.json");

        IndustryData result = new IndustryData();
        List<Industry> list = new ArrayList<>();
        Map<String, List<Industry>> map = new HashMap<>();
        if (companyJsonStr != null && !"".equalsIgnoreCase(companyJsonStr.trim())) {
            list = JSONArray.parseArray(companyJsonStr, Industry.class);
            List<Industry> allCityList = JSONArray.parseArray(factoryJsonStr, Industry.class);

            for (Industry temp : list) {

                List<Industry> cityList = new ArrayList<>();
                if (map.containsKey(temp.province)) {
                    cityList = map.get(temp.province);
                }
                for (Industry t : allCityList) {
                    if (temp.id == t.smokeCompanyId) {
                        cityList.add(t);
                    }
                }
                map.put(temp.province, cityList);
            }
        }

        result.list = list;
        result.map = map;

        return result;
    }

    public static List<Industry> getProvinceIndustryData(Context context) {

        String provinceJsonStr = FileUtils.readAssetsFile(context, "resIndustryMapByProvince.json");

        ResponseIndustry result = JSONArray.parseObject(provinceJsonStr, ResponseIndustry.class);
        return result.company;
    }

    public static List<Industry> getCityIndustryData(Context context) {

        String cityJsonStr = FileUtils.readAssetsFile(context, "resIndustryMapByCity.json");

        ResponseIndustry result= JSONArray.parseObject(cityJsonStr, ResponseIndustry.class);


        return result.company;
    }

    /**
     * 商户--地图数据
     *
     * @param context
     * @param type    请求数据级别
     * @param value   省份（获得城市级别）
     * @return
     */
    public static ResponseBusiness getResBusiness(Context context, int type, String value) {
        Random random = new Random();
        ResponseBusiness result = new ResponseBusiness();
        List<Business> temp = new ArrayList<>();

        switch (type) {
            case Constants.Area.COUNTRY:
                Business country = new Business();
                country.num = 540; //共多少商户
                country.name = "中国";//计划多少箱
                country.longitude = "116.470834";
                country.latitude = "39.875466";
                temp.add(country);
                break;
            case Constants.Area.PROVINCE:
                String provinceJsonStr = FileUtils.readAssetsFile(context, "business_province.json");
                temp = JSONArray.parseArray(provinceJsonStr, Business.class);
                break;
            case Constants.Area.CITY:
                String cityJsonStr = FileUtils.readAssetsFile(context, "business_city.json");
                temp = JSONArray.parseArray(cityJsonStr, Business.class);
                break;
            case Constants.Area.AREA:
                //String[] jsonName = {"business_area_gz.json", "business_area_sz.json"};
                String areaJsonStr = FileUtils.readAssetsFile(context, "business_area_sz.json");
                temp = JSONArray.parseArray(areaJsonStr, Business.class);
                break;
        }
        result.businesses = temp;

        return result;
    }

    /**
     * 商户--商铺信息
     *
     * @param context
     * @param value
     * @return
     */
    public static ResponseStore getResStore(Context context, String value) {
        ResponseStore result = new ResponseStore();
        List<Store> temp = new ArrayList<>();
        String jsonStr = FileUtils.readAssetsFile(context, "t_merchant.json");
        temp = JSONArray.parseArray(jsonStr, Store.class);
        result.stores = temp;

        return result;
    }

    /**
     * 商户-- 图表数据
     *
     * @param type
     * @param value
     * @return
     */
    public static ResponseStoreForm getResStoreForm(int type, String value) {
        Random random = new Random();
        ResponseStoreForm result = new ResponseStoreForm();

        FormData formData = new FormData();
        String[] dataKey = {"进货", "出货"};
        Map<String, List<Double>> data = new HashMap<>();
        List<Double> plan = new ArrayList<>();
        List<Double> complete = new ArrayList<>();
        formData.label = Arrays.asList(GetStaticData.cigaretteArr);
        switch (type) {
            case GetStaticData.TYPE_MONTH:
                result.inventory = GetStaticData.DATA_S_EST[random.nextInt(8)];
                result.purchase = GetStaticData.DATA_S_ER[random.nextInt(8)];
                result.sale = result.purchase - result.inventory;
                for (int i = 0; i < formData.label.size(); i++) {
                    plan.add(GetStaticData.DATA_S_EST[random.nextInt(8)]);
                    complete.add(plan.get(i) - 8);
                }
                break;

            case GetStaticData.TYPE_YEAR:
                result.inventory = GetStaticData.DATA_S[random.nextInt(8)];
                result.purchase = GetStaticData.DATA_B[random.nextInt(8)];
                result.sale = result.purchase - result.inventory;
                for (int i = 0; i < formData.label.size(); i++) {
                    plan.add(GetStaticData.DATA_S[random.nextInt(8)]);
                    complete.add(plan.get(i) - 230);
                }
                break;
        }
        data.put(dataKey[0], complete);
        data.put(dataKey[1], plan);
        formData.dataKey = dataKey;
        formData.data = data;
        result.formData=formData;

        return result;
    }

    /**
     * 香烟品牌
     * @param context
     * @return
     */
    public static ResponseCigarette getResCigaretteBrand(Context context){

        ResponseCigarette result = new ResponseCigarette();
        List<Cigarette> temp = new ArrayList<>();
        String jsonStr = FileUtils.readAssetsFile(context, "cigarette_category.json");
        temp = JSONArray.parseArray(jsonStr, Cigarette.class);
        result.lists = temp;

        return result;
    }

    /**
     * 香烟规格
     * @param context
     * @return
     */
    public static ResponseCigarette getResCigaretteName(Context context){

        ResponseCigarette result = new ResponseCigarette();
        List<Cigarette> temp = new ArrayList<>();
        String jsonStr = FileUtils.readAssetsFile(context, "cigarette_content.json");
        temp = JSONArray.parseArray(jsonStr, Cigarette.class);
        result.lists = temp;
        return result;
    }

    /**
     * 地图定位/追踪
     * @return
     */
    public static ResponseEntity getResEntity(){
        ResponseEntity result = new ResponseEntity();
        List<Entity> entities = new ArrayList<>();

        double[][] t1 = {
                {114.112757,22.547306},{114.112699,22.54731},{114.1126,22.547285},{114.112582,22.547273},{114.112537,22.547248},{114.112488,22.547252},{114.112407,22.547231},{114.112331,22.547227},{114.112268,22.547223},{114.1122,22.547198},{114.112169,22.547202},{114.112079,22.547194},{114.111971,22.547173},{114.111819,22.547168},{114.111738,22.547148}
        };
        double[][] t2 = {
                {114.109398,22.546952},{114.109429,22.546956},{114.109505,22.54696},{114.109847,22.546972},{114.109982,22.546977},{114.110018,22.546977},{114.110166,22.546972},{114.110238,22.546985},{114.110345,22.546981},{114.110489,22.546989},{114.110579,22.546989},{114.110543,22.546981},{114.110705,22.546989},{114.110759,22.546985},{114.11079,22.546989}
        };
        double[][] t3 = {
                {114.110166,22.546972},{114.110238,22.546985},{114.110345,22.546981},{114.110489,22.546989},{114.110579,22.546989},{114.110543,22.546981},{114.110705,22.546989},{114.110759,22.546985},{114.11079,22.546989},{114.110813,22.546989},{114.110871,22.546993},{114.110929,22.547006},{114.111051,22.547002},{114.111163,22.546993},{114.111293,22.547002}
        };

        Entity entity1 = new Entity();
        entity1.entity_name = "entity1";
        List<Point> points1 = new ArrayList<>();

        for (int i=0;i<t1.length;i++){
            Point p = new Point();
            p.loc_time=(1442824667+i*1111)+"";
            p.location=t1[i];//经度；纬度
            p.direction=240;//方向
            p.speed=2.5;//速度
            p.radius=65;//角度
            points1.add(p);
        }
        entity1.realtimepoint=points1;

        Entity entity2 = new Entity();
        entity2.entity_name = "entity2";
        List<Point> points2 = new ArrayList<>();
        for (int i=0;i<t2.length;i++){
            Point p = new Point();
            p.loc_time=(1441564615+i*1111)+"";
            p.location=t2[i];//经度；纬度
            p.direction=240;//方向
            p.speed=2.5;//速度
            p.radius=65;//角度
            points2.add(p);
        }

        entity2.realtimepoint=points2;

        Entity entity3 = new Entity();
        entity3.entity_name = "entity3";
        List<Point> points3 = new ArrayList<>();
        for (int i=0;i<t3.length;i++){
            Point p = new Point();
            p.loc_time=(1441252345+i*1111)+"";
            p.location=t3[i];//经度；纬度
            p.direction=240;//方向
            p.speed=2.5;//速度
            p.radius=65;//角度
            points3.add(p);
        }

        entity3.realtimepoint=points3;

        entities.add(entity1);
        entities.add(entity2);
        entities.add(entity3);

        result.entities = entities;


        return result;
    }
    /**
     * 车辆信息
     * @param entityName
     * @return
     */
    public static ResponseCar getResCar(String entityName){
        Random random = new Random();
        ResponseCar result = new ResponseCar();

        String[] dep={"广州卷烟二厂","梅州卷烟厂","韶关卷烟厂","湛江卷烟厂","长沙卷烟厂"};
        String[] des={"深圳市烟草专卖局（公司）"};
        String[] driver={"张小军","李志民","孙大山"};
        String[] license={"粤BC925S","粤BXV062","粤B93D96"};

        Car car = new Car();
        car.departure=dep[random.nextInt(5)];//出发地
        car.destination=des[0];//目的地
        car.driverName=driver[random.nextInt(3)];//司机名
        car.driverMobile="13691958936";//联系电话
        car.license=license[random.nextInt(3)];//牌照
        car.entityName=entityName;//百度登记entity名称，其唯一标识 entity_name
        List<CarForm> carForms = new ArrayList<>();
        int size = random.nextInt(5)+1;
        for (int i=0;i<size;i++){
            CarForm form =new CarForm();
            form.name=GetStaticData.cigaretteArr[random.nextInt(8)];
            form.num=(random.nextInt(15)+1)*2;
            carForms.add(form);
        }
        result.form=carForms;
        result.car=car;
        return result;
    }

}
