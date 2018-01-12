package com.sxmoc.bq.model;

/**
 * Created by zhangjiebo on 2017/12/25/025.
 *
 * @author ZhangJieBo
 */

public class PayAlipay {
    /**
     * status : 1
     * info : success
     * orderinfo : alipay_sdk=alipay-sdk-php-20161101&app_id=2017102609533302&biz_content=%7B%22body%22%3A%22%E7%94%A8%E6%88%B7ID%E4%B8%BA6%E4%B8%8B%E5%8D%95%EF%BC%8C%E6%80%BB%E9%87%91%E9%A2%9D%E4%B8%BA20%E5%85%83%22%2C%22subject%22%3A+%22%E5%9C%A8%E7%BA%BF%E6%94%AF%E4%BB%98%22%2C%22out_trade_no%22%3A+%222017122515543240%22%2C%22timeout_express%22%3A+%2230m%22%2C%22total_amount%22%3A+%2220%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=https%3A%2F%2Fwww.haoche666.com%2Fapi%2Fnotify%2Falipay.html&sign_type=RSA2&timestamp=2017-12-25+15%3A54%3A32&version=1.0&sign=E6ztn9zfJ4QKCmaMIWCKNk9BV8SYiYhyusJEQuft%2B%2BzG3GmVZKmz11yU6qoQjOru0UUhwneaiTaoNuwDHEadm%2Bc%2B2phswYZ5deYHl0ro%2B0Qz7299s17F6sL9E7YlopPN2NekZ5e8yRmjN8nhM%2FOmCpue2ZAqjFEiNdax2UuxNqbcjNiL%2BbI3%2FFAyHzbe%2FX5jp0qCPO33fnffv8mruxx1Se09p4v3uj8OzdZld86Nytz0Kr1mXNCJxRwLABXnmFkG%2F3V1kyj%2FA6BjIgGdJjmmbCaTv3ik%2FvHH%2F4vNwEg%2FbhAbsoA1XMUA8UMAWFql2OdOzwuglZvWjCoLClWnwDpKPw%3D%3D
     * orderinfo : alipay_sdk=alipay-sdk-php-20161101&app_id=2017070407644871&biz_content=%7B%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22subject%22%3A+%22App%E6%94%AF%E4%BB%98%E6%B5%8B%E8%AF%95%22%2C%22out_trade_no%22%3A+%22AO201709271735421%22%2C%22timeout_express%22%3A+%2230m%22%2C%22total_amount%22%3A+%220.01%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fapi.jlzquan.com%2FApp%2FRespond%2Falipay.html&sign_type=RSA2&timestamp=2017-09-27+17%3A35%3A43&version=1.0&sign=IcvBLkEjIo8Z6uXv%2FZjQ%2Fa9QC1FafAMBwhW1HoZLay09SEwbLl2NPv9phjPdQ8GBKh155lRLNL%2B3aepcvGsfY3cYPWPXBufgvPRaQ08ODztIXLM6SyuIXAtezCh6WoJTv%2B9xqM0kqQATuIy%2FH7Cndlejuu2DWMAJW1KU9zHWaAhzzVi0LFKZvwySgaQ7t43jBrHV1rFXQ9aU7Hng%2FSbc%2BtL8Kao13qR1f2F724pKGC60O8KWWjJvYQQRlDsWYrT%2FkEH9ufLJmDRDER0943Hdh6LoK11auzBkC132nqleyVv3kNu%2FPLmFIyVznRD6Kl8AdS1TE8loYLaNRKdDRRJLBQ%3D%3D
     */

    private int status;
    private String info;
    private String orderinfo;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getOrderinfo() {
        return orderinfo;
    }

    public void setOrderinfo(String orderinfo) {
        this.orderinfo = orderinfo;
    }
}
