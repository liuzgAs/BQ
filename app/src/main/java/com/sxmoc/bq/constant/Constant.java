package com.sxmoc.bq.constant;


/**
 * Created by zjb on 2016/6/12.
 */
public class Constant {
    public static String HOST = "http://bq.sxmoc.com/api";
    /**
     * 判断数据是否有改变
     */
    public static int changeControl = 2017;
    /**
     * 微信appid
     */
    public static String WXAPPID = "wxfef0031f5d8f3ed0";
    /**
     * 微信scrent
     */
    public static String WXSCRENT = "02d7e0ca570f95630b552bd055fdd14a";
    /**
     * qq
     */
    public static String QQ_ID = "1106239952";
    public static String QQ_KEY = "HcA9s2rpKkLO2M5w";

    public static class Url {
        /**
         * 升级
         */
        public static final String INDEX_VERSION = "";
        /**
         * 忘记密码
         */
        public static final String LOGIN_FORGET = "/Login/forget";
        /**
         * 忘记验证码请求
         */
        public static final String LOGIN_FORGETSMS = "/Login/forgetSms";
        /**
         * 注册
         */
        public static final String LOGIN_REGISTER = "/Login/register";
        /**
         * 注册发送验证码
         */
        public static final String LOGIN_REGSMS = "/Login/regSms";
        /**
         * 用户登录
         */
        public static final String LOGIN_INDEX = "/Login/index";
        /**
         * 启动广告页
         */
        public static final String INDEX_STARTAD = "&g=App&m=Index&a=startAd";
    }

    public static class IntentKey {
        public static final String TYPE = "type";
        public static final String POSITION = "position";
        public static final String PHONE = "phone";
    }

    public static class RequestResultCode {
        public static final int IMAGE_PICKER = 2029;
    }

    public static class Acache {
        public static final String APP = "app";
        public static final String USER_INFO = "userInfo";
        public static final String TOKENTIME = "tokentime";
        public static final String LOCATION = "location";
        public static final String LAT = "lat";
        public static final String LNG = "lng";
        public static final String CITY = "city";
        public static final String CITY_ID = "city_id";
        public static final String FRIST = "frist";
        public static final String ADDRESS = "address";
        public static final String STREET = "Street";
        public static final String DISTRICT = "District";
        public static final String DID = "did";
    }

    public static class BroadcastCode {
        public static final String CHE_LIANG_BIAN_JI_DIALOG = "che_liang_bian_ji_dialog";
    }


}
