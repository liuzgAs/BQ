package com.sxmoc.bq.constant;


/**
 * Created by zjb on 2016/6/12.
 */
public class Constant {
    public static String HOST = "http://api.qizhibq.com";
    public static String WEB_HOST = "http://www.qizhibq.com/api";
    /**
     * 判断数据是否有改变
     */
    public static int changeControl = 2017;
    public static String bluetooth_name = "Mind Link";
    /**
     * 微信appid
     */
    public static String WXAPPID = "wx0c219330fd1923b5";
    /**
     * 微信scrent
     */
    public static String WXSCRENT = "61ed0014ed96075598406bc7b74cf36e";
    /**
     * qq
     */
    public static String QQ_ID = "1106239952";
    public static String QQ_KEY = "HcA9s2rpKkLO2M5w";

    public static class Url {
        public static final String ABOUT =WEB_HOST+"/Article/info/type/about";
        public static final String USER =WEB_HOST+"/Article/info/type/user";
        public static final String PRODUCT =WEB_HOST+"/Article/info/type/product";
        public static final String PARTNER =WEB_HOST+"/Article/info/type/partner";
        public static final String PARTNERDES =WEB_HOST+"/Article/info/type/partnerDes";
        public static final String PRECAUTIONS =WEB_HOST+"/Article/info/type/precautions";
        /**
         * 升级
         */
        public static final String INDEX_VERSION = "/Index/version";
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
         * 验证码登录
         */
        public static final String LOGIN_SMS = "/Login/sms";
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
        /**
         * 测试历史
         */
        public static final String PRODUCT_QUERYHISTORY = "/buyer/getHistory";
        /**
         * 常见问题
         */
        public static final String FAQ = "/Faq";
        /**
         * 修改密码
         */
        public static final String USER_PWDSAVE = "/User/pwdSave";
        /**
         * 意见反馈
         */
        public static final String USER_FEEDBACK = "/User/feedback";
        /**
         * 我的订单列表
         */
        public static final String ORDER = "/order";
        /**
         * 个人信息
         */
        public static final String USER_PROFILE = "/User/profile";
        /**
         * 单图上传
         */
        public static final String RESPOND_APPIMGADD = "/Respond/appImgAdd";
        /**
         * 修改昵称和头像
         */
        public static final String USER_SVAEINFO = "/User/svaeInfo";
        /**
         * 申请合伙人前请求
         */
        public static final String USER_APPLYBEFORE = "/User/applyBefore";
        /**
         * 城市列表
         */
        public static final String INDEX_CITYLIST = "/Index/cityList";
        /**
         * 申请合伙人提交
         */
        public static final String USER_APPLY = "/User/apply";
        /**
         * 升级事业合伙人
         */
        public static final String USER_UPGRADE = "/User/upGrade";
        /**
         * 收益明细
         */
        public static final String USER_PROFITDETAILED = "/user/profitDetailed";
        /**
         * 提现请求
         */
        public static final String WITHDRAW_ADDBEFORE = "/Withdraw/addBefore";
        /**
         * 体现提交
         */
        public static final String WITHDRAW_ADDDONE = "/Withdraw/addDone";
        /**
         * 银行卡列表
         */
        public static final String BANK_CARDLIST = "/Bank/cardList";
        /**
         * 文章列表
         */
        public static final String ARTICLE = "/Article";
        /**
         * 用户中心
         */
        public static final String USER_MSG = "/User/msg";
        /**
         * 我的共享
         */
        public static final String USER_GETMYSHARE = "/user/getMyShare";
        /**
         * 我的合伙人
         */
        public static final String USER_GETMYPARTNER = "/user/getMyPartner";
        /**
         * 地址列表
         */
        public static final String USER_ADDRESS = "/User/address";
        /**
         * 地址保存
         */
        public static final String USER_SAVEADDRESS = "/User/saveAddress";
        /**
         * 删除地址
         */
        public static final String USER_DELADDRESS = "/User/delAddress";
        /**
         * 地址设为默认
         */
        public static final String USER_ADDRESSDEFAULT = "/User/addressDefault";
        /**
         * 银行卡添加前请求
         */
        public static final String BANK_CARDADDBEFORE = "/Bank/cardAddbefore";
        /**
         * 银行卡验证码
         */
        public static final String LOGIN_BINDSMS = "/Login/bindSms";
        /**
         * 银行卡添加提交
         */
        public static final String BANK_CARDADD = "/Bank/cardAdd";
        /**
         * 银行卡删除
         */
        public static final String BANK_CARDDEL = "/Bank/cardDel";
        /**
         *  转让报告
         */
        public static final String USER_TRANSFER = "/user/transfer";
        /**
         * 分享推荐
         */
        public static final String USER_SHARE = "/user/share";
        /**
         * 取消订单
         */
        public static final String ORDER_CANCELORDER = "/order/cancelOrder";
        /**
         * 确认收货
         */
        public static final String ORDER_CONFIRMORDER = "/order/confirmOrder";
        /**
         * 转让记录
         */
        public static final String USER_TRANSFERRECODE = "/user/transferRecode";
        /**
         * 0进下级共享好友
         */
        public static final String USER_GETMYSHARE1 = "/user/getMyShare1";
        /**
         * 0进下级合伙人
         */
        public static final String USER_GETMYPARTNER1 = "/user/getMyPartner1";
        /**
         * 订单详情
         */
        public static final String ORDER_GETORDERDETAIL = "/order/getOrderDetail";
        /**
         * 生成报告
         */
        public static final String TESTER_GETREPORT = "/tester/getReport";
        /**
         * 宝宝列表
         */
        public static final String TESTER_GETTESTER = "/tester/getTester";
        /**
         * 宝宝信息编辑信息获取
         */
        public static final String TESTER_TESTEREDIT = "/tester/testerEdit";
        /**
         * 评价管理
         */
        public static final String ORDER_GETEEVA = "/order/getEeva";
        /**
         * 评价前请求
         */
        public static final String ORDER_GOTOEEVA = "/order/goToEeva";
        /**
         * 订单评价提交
         */
        public static final String ORDER_ADDEEVA = "/order/addEeva";
        /**
         * 更多评价
         */
        public static final String GOODS_EVALUATEMORE = "/goods/evaluateMore";
        /**
         * 我的报告明细
         */
        public static final String USER_REPORTDETAILED = "/user/reportDetailed";
        /**
         * 获取蓝牙设备
         */
        public static final String USER_GETBLUETOOTH = "/user/getBluetooth";
        /**
         * 提现记录
         */
        public static final String WITHDRAW_GETWITHDRAW = "/Withdraw/getWithdraw";
        /**
         * 微信支付
         */
        public static final String PAY_WXPAY = "/pay/wxpay";
        /**
         * 提醒发货
         */
        public static final String ORDER_REMIND = "/order/remind";
        /**
         * 脑力档案切换
         */
        public static final String BUYER_DELONEHISTORY = "/buyer/delOneHistory";
        /**
         * 转让报告前提示
         */
        public static final String USER_TRANSFERTIPS = "/user/transferTips";
    }

    public static class IntentKey {
        public static final String TYPE = "type";
        public static final String POSITION = "position";
        public static final String PHONE = "phone";
        public static final String ID = "id";
        public static final String VALUE = "value";
        public static final String ORDER = "order";
        public static final String URL = "url";
        public static final String TITLE = "title";
        public static final String NICKNAME = "nickName";
        public static final String BEAN = "bean";
    }

    public static class RequestResultCode {
        public static final int XIN_YONG_KA = 2023;
        public static final int address = 2027;
        public static final int IMAGE_PICKER = 2029;
        public static final int KAI_SHI_CE_SHI = 2030;
        public static final int CITY = 2033;
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
        public static final String PAY_RECEIVER = "pay_receiver";
        public static final String WX_LOGIN = "wxLogin";
        public static final String WX_SHARE = "wxShare";
        public static final String WX_LOGIN_FAIL = "wxLoginFail";
        public static final String WX_SHARE_FAIL = "wxShareFail";
        public static final String ZHI_FU_CG = "zhiFuCG";
        public static final String USERINFO = "userInfo";
        public static final String address = "address";
        public static final String SHUA_XIN_DD = "shuaXinDD";
        public static final String KAISHICESHI = "kaiShiCeShi";
        public static final String XIUGAIBAOBAO = "XiuGaiBaoBao";
        public static final String SHUA_XIN_PING_JIA = "shua_xin_ping_jia";
        public static final String TIXIAN = "tiXian";
    }


}
