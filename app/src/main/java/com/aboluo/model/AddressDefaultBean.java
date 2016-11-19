package com.aboluo.model;

/**
 * Created by CJ on 2016/11/20.
 */

public class AddressDefaultBean {

    /**
     * IsSuccess : true
     * Message : null
     * Result : {"Id":15,"MemberId":1,"Receiver":"曹晶","Mobile":"18360733212","Province":"北京","ProvinceId":110000,"City":"北京市","CityId":110100,"Region":"东城区","RegionId":110101,"Street":"东花市街道","StreetId":110101013,"Address":"淮阴工学院","ZipCode":"225500","IsDefault":1}
     * ListResult : null
     */

    private boolean IsSuccess;
    private Object Message;
    /**
     * Id : 15
     * MemberId : 1
     * Receiver : 曹晶
     * Mobile : 18360733212
     * Province : 北京
     * ProvinceId : 110000
     * City : 北京市
     * CityId : 110100
     * Region : 东城区
     * RegionId : 110101
     * Street : 东花市街道
     * StreetId : 110101013
     * Address : 淮阴工学院
     * ZipCode : 225500
     * IsDefault : 1
     */

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
