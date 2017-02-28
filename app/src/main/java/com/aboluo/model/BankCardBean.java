package com.aboluo.model;

import com.google.gson.Gson;

/**
 * Created by CJ on 2017/2/28.
 */

public class BankCardBean
{

    /**
     * status : 0
     * msg : ok
     * result : {"bankcard":"6212261202011594349","name":"牡丹卡普卡","province":"浙江","city":"杭州","type":"借记卡","len":"19","bank":"中国工商银行","logo":"http://www.jisuapi.com/api/bankcard/upload/80/2.png","tel":"95588","website":"http://www.icbc.com.cn","iscorrect":"0"}
     */

    private String status;
    private String msg;
    private ResultBean result;

    public static BankCardBean objectFromData(String str) {

        return new Gson().fromJson(str, BankCardBean.class);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * bankcard : 6212261202011594349
         * name : 牡丹卡普卡
         * province : 浙江
         * city : 杭州
         * type : 借记卡
         * len : 19
         * bank : 中国工商银行
         * logo : http://www.jisuapi.com/api/bankcard/upload/80/2.png
         * tel : 95588
         * website : http://www.icbc.com.cn
         * iscorrect : 0
         */

        private String bankcard;
        private String name;
        private String province;
        private String city;
        private String type;
        private String len;
        private String bank;
        private String logo;
        private String tel;
        private String website;
        private String iscorrect;

        public static ResultBean objectFromData(String str) {

            return new Gson().fromJson(str, ResultBean.class);
        }

        public String getBankcard() {
            return bankcard;
        }

        public void setBankcard(String bankcard) {
            this.bankcard = bankcard;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLen() {
            return len;
        }

        public void setLen(String len) {
            this.len = len;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getIscorrect() {
            return iscorrect;
        }

        public void setIscorrect(String iscorrect) {
            this.iscorrect = iscorrect;
        }
    }
}
