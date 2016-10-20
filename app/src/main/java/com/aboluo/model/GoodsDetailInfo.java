package com.aboluo.model;

import java.util.List;

/**
 * Created by cj34920 on 2016/10/20.
 */

public class GoodsDetailInfo {

    /**
     * IsSuccess : true
     * Message : null
     * Result : {"GoodsInfo":{"goodsId":3047,"goodsTypeId":21,"parentId":48,"seckillId":0,"seckillPrice":11,"isJifen":0,"jifenPrice":11,"goodsName":"阿波罗研发测试商品","goodsCode":"1011","goodsLogo":"/GoodsLogo/87f7222bd99d4d64b2333bb26887ee6b.jpg","goodsPicture":"/GoodsPicture/c4915713bc494558b214ceda14b64631.jpg","goodsSub":"1011","goodsQuantity":1011,"goodsOriginalPrice":1011,"goodsPrice":1011,"hyPrice":1011,"yunfei":1011,"goodsColor":[{"color":"111","colorImg":"/GoodsColorImg/cd582dd4ff07470aba0c22a6a0fbf38d.jpg"}],"goodsStandards":[{"standard":"11","price":11,"hyPrice":11,"goodsOriginalPrice":11}],"isList":1,"isGoodsQuantity":0,"buyQuantity":0,"isLevelDiscount":0,"comments":0,"saleCount":1011,"sort":1,"isHome":1,"goodsDes":null,"addTime":"0001-01-01T00:00:00","status":0,"directly_money":10.1,"directly_rate":1,"superior_money":10.1,"superior_rate":1,"three_money":10.1,"three_rate":1,"factory_price":1,"aboluo_money":10.1,"aboluo_rate":1,"assign_money":1010,"GoodsTypeName":null}}
     * ListResult : null
     */

    private boolean IsSuccess;
    private Object Message;
    /**
     * GoodsInfo : {"goodsId":3047,"goodsTypeId":21,"parentId":48,"seckillId":0,"seckillPrice":11,"isJifen":0,"jifenPrice":11,"goodsName":"阿波罗研发测试商品","goodsCode":"1011","goodsLogo":"/GoodsLogo/87f7222bd99d4d64b2333bb26887ee6b.jpg","goodsPicture":"/GoodsPicture/c4915713bc494558b214ceda14b64631.jpg","goodsSub":"1011","goodsQuantity":1011,"goodsOriginalPrice":1011,"goodsPrice":1011,"hyPrice":1011,"yunfei":1011,"goodsColor":[{"color":"111","colorImg":"/GoodsColorImg/cd582dd4ff07470aba0c22a6a0fbf38d.jpg"}],"goodsStandards":[{"standard":"11","price":11,"hyPrice":11,"goodsOriginalPrice":11}],"isList":1,"isGoodsQuantity":0,"buyQuantity":0,"isLevelDiscount":0,"comments":0,"saleCount":1011,"sort":1,"isHome":1,"goodsDes":null,"addTime":"0001-01-01T00:00:00","status":0,"directly_money":10.1,"directly_rate":1,"superior_money":10.1,"superior_rate":1,"three_money":10.1,"three_rate":1,"factory_price":1,"aboluo_money":10.1,"aboluo_rate":1,"assign_money":1010,"GoodsTypeName":null}
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
        /**
         * goodsId : 3047
         * goodsTypeId : 21
         * parentId : 48
         * seckillId : 0
         * seckillPrice : 11.0
         * isJifen : 0
         * jifenPrice : 11
         * goodsName : 阿波罗研发测试商品
         * goodsCode : 1011
         * goodsLogo : /GoodsLogo/87f7222bd99d4d64b2333bb26887ee6b.jpg
         * goodsPicture : /GoodsPicture/c4915713bc494558b214ceda14b64631.jpg
         * goodsSub : 1011
         * goodsQuantity : 1011
         * goodsOriginalPrice : 1011.0
         * goodsPrice : 1011.0
         * hyPrice : 1011.0
         * yunfei : 1011.0
         * goodsColor : [{"color":"111","colorImg":"/GoodsColorImg/cd582dd4ff07470aba0c22a6a0fbf38d.jpg"}]
         * goodsStandards : [{"standard":"11","price":11,"hyPrice":11,"goodsOriginalPrice":11}]
         * isList : 1
         * isGoodsQuantity : 0
         * buyQuantity : 0
         * isLevelDiscount : 0
         * comments : 0
         * saleCount : 1011
         * sort : 1
         * isHome : 1
         * goodsDes : null
         * addTime : 0001-01-01T00:00:00
         * status : 0
         * directly_money : 10.1
         * directly_rate : 1.0
         * superior_money : 10.1
         * superior_rate : 1.0
         * three_money : 10.1
         * three_rate : 1.0
         * factory_price : 1.0
         * aboluo_money : 10.1
         * aboluo_rate : 1.0
         * assign_money : 1010.0
         * GoodsTypeName : null
         */

        private GoodsInfoBean GoodsInfo;

        public GoodsInfoBean getGoodsInfo() {
            return GoodsInfo;
        }

        public void setGoodsInfo(GoodsInfoBean GoodsInfo) {
            this.GoodsInfo = GoodsInfo;
        }

        public static class GoodsInfoBean {
            private int goodsId;
            private int goodsTypeId;
            private int parentId;
            private int seckillId;
            private double seckillPrice;
            private int isJifen;
            private int jifenPrice;
            private String goodsName;
            private String goodsCode;
            private String goodsLogo;
            private String goodsPicture;
            private String goodsSub;
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
            private Object GoodsTypeName;
            /**
             * color : 111
             * colorImg : /GoodsColorImg/cd582dd4ff07470aba0c22a6a0fbf38d.jpg
             */

            private List<GoodsColorBean> goodsColor;
            /**
             * standard : 11
             * price : 11.0
             * hyPrice : 11.0
             * goodsOriginalPrice : 11.0
             */

            private List<GoodsStandardsBean> goodsStandards;

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

            public String getGoodsCode() {
                return goodsCode;
            }

            public void setGoodsCode(String goodsCode) {
                this.goodsCode = goodsCode;
            }

            public String getGoodsLogo() {
                return goodsLogo;
            }

            public void setGoodsLogo(String goodsLogo) {
                this.goodsLogo = goodsLogo;
            }

            public String getGoodsPicture() {
                return goodsPicture;
            }

            public void setGoodsPicture(String goodsPicture) {
                this.goodsPicture = goodsPicture;
            }

            public String getGoodsSub() {
                return goodsSub;
            }

            public void setGoodsSub(String goodsSub) {
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

            public Object getGoodsTypeName() {
                return GoodsTypeName;
            }

            public void setGoodsTypeName(Object GoodsTypeName) {
                this.GoodsTypeName = GoodsTypeName;
            }

            public List<GoodsColorBean> getGoodsColor() {
                return goodsColor;
            }

            public void setGoodsColor(List<GoodsColorBean> goodsColor) {
                this.goodsColor = goodsColor;
            }

            public List<GoodsStandardsBean> getGoodsStandards() {
                return goodsStandards;
            }

            public void setGoodsStandards(List<GoodsStandardsBean> goodsStandards) {
                this.goodsStandards = goodsStandards;
            }

            public static class GoodsColorBean {
                private String color;
                private String colorImg;

                public String getColor() {
                    return color;
                }

                public void setColor(String color) {
                    this.color = color;
                }

                public String getColorImg() {
                    return colorImg;
                }

                public void setColorImg(String colorImg) {
                    this.colorImg = colorImg;
                }
            }

            public static class GoodsStandardsBean {
                private String standard;
                private double price;
                private double hyPrice;
                private double goodsOriginalPrice;

                public String getStandard() {
                    return standard;
                }

                public void setStandard(String standard) {
                    this.standard = standard;
                }

                public double getPrice() {
                    return price;
                }

                public void setPrice(double price) {
                    this.price = price;
                }

                public double getHyPrice() {
                    return hyPrice;
                }

                public void setHyPrice(double hyPrice) {
                    this.hyPrice = hyPrice;
                }

                public double getGoodsOriginalPrice() {
                    return goodsOriginalPrice;
                }

                public void setGoodsOriginalPrice(double goodsOriginalPrice) {
                    this.goodsOriginalPrice = goodsOriginalPrice;
                }
            }
        }
    }
}
