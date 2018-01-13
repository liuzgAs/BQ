package com.sxmoc.bq.constant;


/**
 * Created by zjb on 2016/6/12.
 */
public class Constant {
    public static String HOST = "http://api.qizhibq.com";
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
        public static final String INDEX_STARTAD = "/Index/start";
        /**
         * 商品列表
         */
        public static final String GOODS_INDEX = "/Goods/index";
        /**
         * 商品详情
         */
        public static final String GOODS_INFO = "/Goods/info";
        /**
         * 确认订单页
         */
        public static final String ORDER_CREATEORDER = "/order/createOrder";
        /**
         * 提交订单
         */
        public static final String ORDER_SUBMITORDER = "/order/submitOrder";
        /**
         * 余额获取
         */
        public static final String USER_GETBALANCE = "/user/getbalance";
        /**
         * 余额支付
         */
        public static final String PAY_BALANCEPAY = "/pay/balancePay";
        /**
         * 我的
         */
        public static final String USER_BUYERINDEX = "/User/buyerIndex";
        /**
         * 支付宝支付
         */
        public static final String PAY_ALIPAY = "/pay/alipay";
        /**
         * 测试数据上传
         */
        public static final String BUYER_SAVEDATA = "/buyer/saveData";
        /**
         * 宝宝信息填写
         */
        public static final String BUYER_ADDINFO = "/buyer/addInfo";
        /**
         * 发现
         */
        public static final String INDEX_FINDINDEX = "/Index/findIndex";
    }

    public static class IntentKey {
        public static final String TYPE = "type";
        public static final String POSITION = "position";
        public static final String PHONE = "phone";
        public static final String ID = "id";
        public static final String VALUE = "value";
        public static final String ORDER = "order";
    }

    public static class RequestResultCode {
        public static final int IMAGE_PICKER = 2029;
        public static final int KAI_SHI_CE_SHI = 2030;
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
        public static final String PAY_RECEIVER = "pay_receiver";
        public static final String WX_LOGIN = "wxLogin";
        public static final String WX_SHARE = "wxShare";
        public static final String WX_LOGIN_FAIL = "wxLoginFail";
        public static final String WX_SHARE_FAIL = "wxShareFail";
        public static final String ZHI_FU_CG = "zhiFuCG";
    }


}
