package com.aboluo.model;

import java.util.List;

/**
 * Created by CJ on 2016/11/6.
 */

public class AddressInfoBean {

    /**
     * IsSuccess : true
     * Message : null
     * Result : {"MemberAddressList":[{"Id":7,"MemberId":1,"Receiver":"曹晶","Mobile":"18360733212","Province":"河北省","ProvinceId":130000,"City":"承德市","CityId":130800,"Region":"宽城满族自治县","RegionId":130827,"Street":"东黄花川乡","StreetId":0,"Address":"河北省承德市宽城满族自治县","ZipCode":"225500","IsDefault":0},{"Id":8,"MemberId":1,"Receiver":"曹晶","Mobile":"18360733212","Province":"山西省","ProvinceId":140000,"City":"临汾市","CityId":141000,"Region":"大宁县","RegionId":141030,"Street":"徐家垛乡","StreetId":141030,"Address":"山西省临汾市大宁县","ZipCode":"225500","IsDefault":0},{"Id":9,"MemberId":1,"Receiver":"曹晶","Mobile":"18360733212","Province":"山西省","ProvinceId":140000,"City":"临汾市","CityId":141000,"Region":"大宁县","RegionId":141030,"Street":"徐家垛乡","StreetId":141030,"Address":"山西省临汾市大宁县","ZipCode":"225500","IsDefault":0},{"Id":10,"MemberId":1,"Receiver":"曹晶","Mobile":"18360733212","Province":"河北省","ProvinceId":130000,"City":"沧州市","CityId":130900,"Region":"沧县","RegionId":130921,"Street":"大官厅乡","StreetId":130921,"Address":"河北省沧州市沧县","ZipCode":"225500","IsDefault":0},{"Id":11,"MemberId":1,"Receiver":"曹晶","Mobile":"18360733212","Province":"山西省","ProvinceId":140000,"City":"吕梁市","CityId":141100,"Region":"临县","RegionId":141124,"Street":"城庄镇","StreetId":141124102,"Address":"山西省吕梁市临县","ZipCode":"225500","IsDefault":0}]}
     * ListResult : null
     */

    private boolean IsSuccess;
    private Object Message;
    private ResultBean Result;
    private Object ListResult;

    public boolean isIsSuccess() {
        return IsSuccess;
    }

    public void setIsSuccess(boolean IsSuccess) {
        this.IsSuccess = IsSuccess;
    }

    public Object getMessage() {
        return Message;
    }

    public void setMessage(Object Message) {
        this.Message = Message;
    }

    public ResultBean getResult() {
        return Result;
    }

    public void setResult(ResultBean Result) {
        this.Result = Result;
    }

    public Object getListResult() {
        return ListResult;
    }

    public void setListResult(Object ListResult) {
        this.ListResult = ListResult;
    }

    public static class ResultBean {
        /**
         * Id : 7
         * MemberId : 1
         * Receiver : 曹晶
         * Mobile : 18360733212
         * Province : 河北省
         * ProvinceId : 130000
         * City : 承德市
         * CityId : 130800
         * Region : 宽城满族自治县
         * RegionId : 130827
         * Street : 东黄花川乡
         * StreetId : 0
         * Address : 河北省承德市宽城满族自治县
         * ZipCode : 225500
         * IsDefault : 0
         */

        private List<MemberAddressListBean> MemberAddressList;

        public List<MemberAddressListBean> getMemberAddressList() {
            return MemberAddressList;
        }

        public void setMemberAddressList(List<MemberAddressListBean> MemberAddressList) {
            this.MemberAddressList = MemberAddressList;
        }

        public static class MemberAddressListBean {
            private int Id;
            private int MemberId;
            private String Receiver;
            private String Mobile;
            private String Province;
            private int ProvinceId;
            private String City;
            private int CityId;
            private String Region;
            private int RegionId;
            private String Street;
            private int StreetId;
            private String Address;
            private String ZipCode;
            private int IsDefault;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public int getMemberId() {
                return MemberId;
            }

            public void setMemberId(int MemberId) {
                this.MemberId = MemberId;
            }

            public String getReceiver() {
                return Receiver;
            }

            public void setReceiver(String Receiver) {
                this.Receiver = Receiver;
            }

            public String getMobile() {
                return Mobile;
            }

            public void setMobile(String Mobile) {
                this.Mobile = Mobile;
            }

            public String getProvince() {
                return Province;
            }

            public void setProvince(String Province) {
                this.Province = Province;
            }

            public int getProvinceId() {
                return ProvinceId;
            }

            public void setProvinceId(int ProvinceId) {
                this.ProvinceId = ProvinceId;
            }

            public String getCity() {
                return City;
            }

            public void setCity(String City) {
                this.City = City;
            }

            public int getCityId() {
                return CityId;
            }

            public void setCityId(int CityId) {
                this.CityId = CityId;
            }

            public String getRegion() {
                return Region;
            }

            public void setRegion(String Region) {
                this.Region = Region;
            }

            public int getRegionId() {
                return RegionId;
            }

            public void setRegionId(int RegionId) {
                this.RegionId = RegionId;
            }

            public String getStreet() {
                return Street;
            }

            public void setStreet(String Street) {
                this.Street = Street;
            }

            public int getStreetId() {
                return StreetId;
            }

            public void setStreetId(int StreetId) {
                this.StreetId = StreetId;
            }

            public String getAddress() {
                return Address;
            }

            public void setAddress(String Address) {
                this.Address = Address;
            }

            public String getZipCode() {
                return ZipCode;
            }

            public void setZipCode(String ZipCode) {
                this.ZipCode = ZipCode;
            }

            public int getIsDefault() {
                return IsDefault;
            }

            public void setIsDefault(int IsDefault) {
                this.IsDefault = IsDefault;
            }
        }
    }
}
