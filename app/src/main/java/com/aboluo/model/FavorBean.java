package com.aboluo.model;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by CJ on 2016/12/9.
 */

public class FavorBean {

    /**
     * IsSuccess : true
     * Message : null
     * Result : [{"CollectId":9,"goodsId":3057,"goodsTypeId":0,"parentId":0,"seckillId":0,"seckillPrice":0,"isJifen":0,"jifenPrice":0,"goodsName":"谢谢","goodsCode":null,"goodsLogo":null,"goodsPicture":null,"goodsSub":null,"goodsQuantity":0,"goodsOriginalPrice":0,"goodsPrice":0,"hyPrice":0,"yunfei":0,"isList":0,"isGoodsQuantity":0,"buyQuantity":0,"isLevelDiscount":0,"comments":0,"saleCount":0,"sort":0,"isHome":0,"goodsDes":null,"addTime":"0001-01-01T00:00:00","status":0,"directly_money":0,"directly_rate":0,"superior_money":0,"superior_rate":0,"three_money":0,"three_rate":0,"factory_price":0,"aboluo_money":0,"aboluo_rate":0,"assign_money":0,"flag":0,"goodsBrandId":0,"GoodsTypeName":null,"GoodsBrandName":null,"goodsColor":null,"goodsStandards":null,"goodsStandardList":null,"goodsColorStandardList":null},{"CollectId":8,"goodsId":3058,"goodsTypeId":0,"parentId":0,"seckillId":0,"seckillPrice":0,"isJifen":0,"jifenPrice":0,"goodsName":"研发测试","goodsCode":null,"goodsLogo":"/GoodsLogo/e7eec70dca2047dbba1d9ccf00d05643.jpg;","goodsPicture":"/GoodsPicture/2db9d069a0004f6e8c0fb2e80db66762.jpg;/GoodsPicture/6bd6491c4dee489ba1c86abec1ff7adb.jpg;","goodsSub":"研发测试","goodsQuantity":123,"goodsOriginalPrice":0,"goodsPrice":300,"hyPrice":200,"yunfei":0,"isList":0,"isGoodsQuantity":0,"buyQuantity":0,"isLevelDiscount":0,"comments":0,"saleCount":0,"sort":0,"isHome":0,"goodsDes":null,"addTime":"0001-01-01T00:00:00","status":0,"directly_money":0,"directly_rate":0,"superior_money":0,"superior_rate":0,"three_money":0,"three_rate":0,"factory_price":0,"aboluo_money":0,"aboluo_rate":0,"assign_money":0,"flag":0,"goodsBrandId":0,"GoodsTypeName":null,"GoodsBrandName":null,"goodsColor":null,"goodsStandards":null,"goodsStandardList":null,"goodsColorStandardList":null},{"CollectId":7,"goodsId":3067,"goodsTypeId":0,"parentId":0,"seckillId":0,"seckillPrice":0,"isJifen":0,"jifenPrice":0,"goodsName":"20161115测试","goodsCode":null,"goodsLogo":null,"goodsPicture":null,"goodsSub":"20161115测试","goodsQuantity":97,"goodsOriginalPrice":0,"goodsPrice":300,"hyPrice":250,"yunfei":0,"isList":0,"isGoodsQuantity":0,"buyQuantity":0,"isLevelDiscount":0,"comments":0,"saleCount":0,"sort":0,"isHome":0,"goodsDes":null,"addTime":"0001-01-01T00:00:00","status":0,"directly_money":0,"directly_rate":0,"superior_money":0,"superior_rate":0,"three_money":0,"three_rate":0,"factory_price":0,"aboluo_money":0,"aboluo_rate":0,"assign_money":0,"flag":0,"goodsBrandId":0,"GoodsTypeName":null,"GoodsBrandName":null,"goodsColor":null,"goodsStandards":null,"goodsStandardList":null,"goodsColorStandardList":null}]
     * ListResult : null
     */

    private boolean IsSuccess;
    private Object Message;
    private Object ListResult;
    /**
     * CollectId : 9
     * goodsId : 3057
     * goodsTypeId : 0
     * parentId : 0
     * seckillId : 0
     * seckillPrice : 0.0
     * isJifen : 0
     * jifenPrice : 0
     * goodsName : 谢谢
     * goodsCode : null
     * goodsLogo : null
     * goodsPicture : null
     * goodsSub : null
     * goodsQuantity : 0
     * goodsOriginalPrice : 0.0
     * goodsPrice : 0.0
     * hyPrice : 0.0
     * yunfei : 0.0
     * isList : 0
     * isGoodsQuantity : 0
     * buyQuantity : 0
     * isLevelDiscount : 0
     * comments : 0
     * saleCount : 0
     * sort : 0
     * isHome : 0
     * goodsDes : null
     * addTime : 0001-01-01T00:00:00
     * status : 0
     * directly_money : 0.0
     * directly_rate : 0.0
     * superior_money : 0.0
     * superior_rate : 0.0
     * three_money : 0.0
     * three_rate : 0.0
     * factory_price : 0.0
     * aboluo_money : 0.0
     * aboluo_rate : 0.0
     * assign_money : 0.0
     * flag : 0
     * goodsBrandId : 0
     * GoodsTypeName : null
     * GoodsBrandName : null
     * goodsColor : null
     * goodsStandards : null
     * goodsStandardList : null
     * goodsColorStandardList : null
     */

    private List<ResultBean> Result;

    public static FavorBean objectFromData(String str) {

        return new Gson().fromJson(str, FavorBean.class);
    }

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

    public Object getListResult() {
        return ListResult;
    }

    public void setListResult(Object ListResult) {
        this.ListResult = ListResult;
    }

    public List<ResultBean> getResult() {
        return Result;
    }

    public void setResult(List<ResultBean> Result) {
        this.Result = Result;
    }

    public static class ResultBean {
        private int CollectId;
        private int goodsId;
        private int goodsTypeId;
        private int parentId;
        private int seckillId;
        private double seckillPrice;
        private int isJifen;
        private int jifenPrice;
        private String goodsName;
        private Object goodsCode;
        private Object goodsLogo;
        private Object goodsPicture;
        private Object goodsSub;
        private int goodsQuantity;
        private double goodsOriginalPrice;
        private double goodsPrice;
        private double hyPrice;
        private double yunfei;
        private int isList;
        private int isGoodsQuantity;
        private int buyQuantity;
        private int isLevelDiscount;
        private int comments;
        private int saleCount;
        private int sort;
        private int isHome;
        private Object goodsDes;
        private String addTime;
        private int status;
        private double directly_money;
        private double directly_rate;
        private double superior_money;
        private double superior_rate;
        private double three_money;
        private double three_rate;
        private double factory_price;
        private double aboluo_money;
        private double aboluo_rate;
        private double assign_money;
        private int flag;
        private int goodsBrandId;
        private Object GoodsTypeName;
        private Object GoodsBrandName;
        private Object goodsColor;
        private Object goodsStandards;
        private Object goodsStandardList;
        private Object goodsColorStandardList;

        public static ResultBean objectFromData(String str) {

            return new Gson().fromJson(str, ResultBean.class);
        }

        public int getCollectId() {
            return CollectId;
        }

        public void setCollectId(int CollectId) {
            this.CollectId = CollectId;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public int getGoodsTypeId() {
            return goodsTypeId;
        }

        public void setGoodsTypeId(int goodsTypeId) {
            this.goodsTypeId = goodsTypeId;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public int getSeckillId() {
            return seckillId;
        }

        public void setSeckillId(int seckillId) {
            this.seckillId = seckillId;
        }

        public double getSeckillPrice() {
            return seckillPrice;
        }

        public void setSeckillPrice(double seckillPrice) {
            this.seckillPrice = seckillPrice;
        }

        public int getIsJifen() {
            return isJifen;
        }

        public void setIsJifen(int isJifen) {
            this.isJifen = isJifen;
        }

        public int getJifenPrice() {
            return jifenPrice;
        }

        public void setJifenPrice(int jifenPrice) {
            this.jifenPrice = jifenPrice;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public Object getGoodsCode() {
            return goodsCode;
        }

        public void setGoodsCode(Object goodsCode) {
            this.goodsCode = goodsCode;
        }

        public Object getGoodsLogo() {
            return goodsLogo;
        }

        public void setGoodsLogo(Object goodsLogo) {
            this.goodsLogo = goodsLogo;
        }

        public Object getGoodsPicture() {
            return goodsPicture;
        }

        public void setGoodsPicture(Object goodsPicture) {
            this.goodsPicture = goodsPicture;
        }

        public Object getGoodsSub() {
            return goodsSub;
        }

        public void setGoodsSub(Object goodsSub) {
            this.goodsSub = goodsSub;
        }

        public int getGoodsQuantity() {
            return goodsQuantity;
        }

        public void setGoodsQuantity(int goodsQuantity) {
            this.goodsQuantity = goodsQuantity;
        }

        public double getGoodsOriginalPrice() {
            return goodsOriginalPrice;
        }

        public void setGoodsOriginalPrice(double goodsOriginalPrice) {
            this.goodsOriginalPrice = goodsOriginalPrice;
        }

        public double getGoodsPrice() {
            return goodsPrice;
        }

        public void setGoodsPrice(double goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public double getHyPrice() {
            return hyPrice;
        }

        public void setHyPrice(double hyPrice) {
            this.hyPrice = hyPrice;
        }

        public double getYunfei() {
            return yunfei;
        }

        public void setYunfei(double yunfei) {
            this.yunfei = yunfei;
        }

        public int getIsList() {
            return isList;
        }

        public void setIsList(int isList) {
            this.isList = isList;
        }

        public int getIsGoodsQuantity() {
            return isGoodsQuantity;
        }

        public void setIsGoodsQuantity(int isGoodsQuantity) {
            this.isGoodsQuantity = isGoodsQuantity;
        }

        public int getBuyQuantity() {
            return buyQuantity;
        }

        public void setBuyQuantity(int buyQuantity) {
            this.buyQuantity = buyQuantity;
        }

        public int getIsLevelDiscount() {
            return isLevelDiscount;
        }

        public void setIsLevelDiscount(int isLevelDiscount) {
            this.isLevelDiscount = isLevelDiscount;
        }

        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }

        public int getSaleCount() {
            return saleCount;
        }

        public void setSaleCount(int saleCount) {
            this.saleCount = saleCount;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getIsHome() {
            return isHome;
        }

        public void setIsHome(int isHome) {
            this.isHome = isHome;
        }

        public Object getGoodsDes() {
            return goodsDes;
        }

        public void setGoodsDes(Object goodsDes) {
            this.goodsDes = goodsDes;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public double getDirectly_money() {
            return directly_money;
        }

        public void setDirectly_money(double directly_money) {
            this.directly_money = directly_money;
        }

        public double getDirectly_rate() {
            return directly_rate;
        }

        public void setDirectly_rate(double directly_rate) {
            this.directly_rate = directly_rate;
        }

        public double getSuperior_money() {
            return superior_money;
        }

        public void setSuperior_money(double superior_money) {
            this.superior_money = superior_money;
        }

        public double getSuperior_rate() {
            return superior_rate;
        }

        public void setSuperior_rate(double superior_rate) {
            this.superior_rate = superior_rate;
        }

        public double getThree_money() {
            return three_money;
        }

        public void setThree_money(double three_money) {
            this.three_money = three_money;
        }

        public double getThree_rate() {
            return three_rate;
        }

        public void setThree_rate(double three_rate) {
            this.three_rate = three_rate;
        }

        public double getFactory_price() {
            return factory_price;
        }

        public void setFactory_price(double factory_price) {
            this.factory_price = factory_price;
        }

        public double getAboluo_money() {
            return aboluo_money;
        }

        public void setAboluo_money(double aboluo_money) {
            this.aboluo_money = aboluo_money;
        }

        public double getAboluo_rate() {
            return aboluo_rate;
        }

        public void setAboluo_rate(double aboluo_rate) {
            this.aboluo_rate = aboluo_rate;
        }

        public double getAssign_money() {
            return assign_money;
        }

        public void setAssign_money(double assign_money) {
            this.assign_money = assign_money;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public int getGoodsBrandId() {
            return goodsBrandId;
        }

        public void setGoodsBrandId(int goodsBrandId) {
            this.goodsBrandId = goodsBrandId;
        }

        public Object getGoodsTypeName() {
            return GoodsTypeName;
        }

        public void setGoodsTypeName(Object GoodsTypeName) {
            this.GoodsTypeName = GoodsTypeName;
        }

        public Object getGoodsBrandName() {
            return GoodsBrandName;
        }

        public void setGoodsBrandName(Object GoodsBrandName) {
            this.GoodsBrandName = GoodsBrandName;
        }

        public Object getGoodsColor() {
            return goodsColor;
        }

        public void setGoodsColor(Object goodsColor) {
            this.goodsColor = goodsColor;
        }

        public Object getGoodsStandards() {
            return goodsStandards;
        }

        public void setGoodsStandards(Object goodsStandards) {
            this.goodsStandards = goodsStandards;
        }

        public Object getGoodsStandardList() {
            return goodsStandardList;
        }

        public void setGoodsStandardList(Object goodsStandardList) {
            this.goodsStandardList = goodsStandardList;
        }

        public Object getGoodsColorStandardList() {
            return goodsColorStandardList;
        }

        public void setGoodsColorStandardList(Object goodsColorStandardList) {
            this.goodsColorStandardList = goodsColorStandardList;
        }
    }
}
