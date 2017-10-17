package com.wlg.Util;

/**
 * Created by LvLiangFeng on 2016/11/28.
 */
public class Contant {
//    public static final String uploadPath = "E:/upload/";
//    public static final String MeterUrl = "http://127.0.0.1:8080/meterweb/api/";

    public static final String uploadPath = "/home/meter/upload/";
    public static final String MeterUrl = "http://wlg.zjucolourlife.com:8090/meterweb/api/";



    public static final String RMS_community_URL = "http://iceapi.colourlife.com:8081/v1/community";
    public static final String RMS_community_sign = "2da55b517ee307af6e4b210b87c67118";


    public static final String RMS_orgs_sign = "02463f70ed9f4fc25e0fa936525ea8b1";
    public static final String RMS_orgs_URL = "http://iceapi.colourlife.com:8081/v1/orgs";

    public static final String RMS_building_list_sign = "2da55b517ee307af6e4b210b87c67118";
    public static final String RMS_building_list_URL = "http://iceapi.colourlife.com:8081/v1/building/list";

    public static final String RMS_building_sign = "2da55b517ee307af6e4b210b87c67118";
    public static final String RMS_building_URL = "http://iceapi.colourlife.com:8081/v1/building";



    public static final String RMSAPPID = "ICEENY00-05ED-41C7-A066-2EFB00B4B958";



    public static String getRoleTempName(String userName){
        return "ROLE_"+userName+"_TEMP";
    }


    public static final String technologyFilePath="technology/iamtechnology/";
    public static final String dataReportFilePath="dataReport/iamdataReport/";
    public static final String projectReportFilePath="projectReport/iamprojectReport/";
    public static final String contractsFilePath="contracts/iamcontracts/";
    public static final String contractsWaterPumpsFilePath="contractswaterpumps/iamcontracts/";
}
