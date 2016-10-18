package com.aboluo.model;

import java.util.List;

/**
 * Created by CJ on 2016/10/18.
 */

public class GoodsListInfo {

    /**
     * IsSuccess : true
     * Message : null
     * Result : {"GoodsList":[{"goodsId":3048,"goodsTypeId":13,"parentId":47,"seckillId":0,"seckillPrice":11,"isJifen":1,"jifenPrice":11,"goodsName":"11","goodsCode":null,"goodsLogo":"/GoodsLogo/3accae2a4ad04feaa2baa7195aa8fc4e.jpg","goodsPicture":"/GoodsPicture/ead168c4862b40c088d76532ec307c5b.jpg","goodsSub":null,"goodsQuantity":0,"goodsOriginalPrice":25,"goodsPrice":2565,"hyPrice":77,"yunfei":0,"goodsColor":[{"color":"11","colorImg":"/GoodsColorImg/2122ff5fd70b431bbeabcbf1597d3566.jpg;/GoodsColorImg/0cf0a90b6379469794593f487b1fa64b.jpg"}],"goodsStandards":[{"standard":"","price":2565,"hyPrice":77,"goodsOriginalPrice":25},{"standard":"","price":2565,"hyPrice":77,"goodsOriginalPrice":25}],"isList":1,"isGoodsQuantity":0,"buyQuantity":0,"isLevelDiscount":0,"comments":0,"saleCount":0,"sort":1,"isHome":0,"goodsDes":"<p>11<\/p>","addTime":"2016-10-11T01:24:13","status":0,"directly_money":34.2,"directly_rate":45,"superior_money":19,"superior_rate":25,"three_money":7.6,"three_rate":10,"factory_price":1,"aboluo_money":15.2,"aboluo_rate":20,"assign_money":76,"GoodsTypeName":"女士内衣"},{"goodsId":3047,"goodsTypeId":21,"parentId":48,"seckillId":0,"seckillPrice":11,"isJifen":0,"jifenPrice":11,"goodsName":"1011","goodsCode":"1011","goodsLogo":"/GoodsLogo/87f7222bd99d4d64b2333bb26887ee6b.jpg","goodsPicture":"/GoodsPicture/c4915713bc494558b214ceda14b64631.jpg","goodsSub":"1011","goodsQuantity":1011,"goodsOriginalPrice":1011,"goodsPrice":1011,"hyPrice":1011,"yunfei":1011,"goodsColor":[{"color":"111","colorImg":"/GoodsColorImg/cd582dd4ff07470aba0c22a6a0fbf38d.jpg"}],"goodsStandards":[{"standard":"11","price":11,"hyPrice":11,"goodsOriginalPrice":11}],"isList":1,"isGoodsQuantity":0,"buyQuantity":0,"isLevelDiscount":0,"comments":0,"saleCount":1011,"sort":1,"isHome":1,"addTime":"0001-01-01T00:00:00","status":0,"directly_money":11,"directly_rate":1,"superior_money":11,"superior_rate":1,"three_money":11,"three_rate":1,"factory_price":1,"aboluo_money":11,"aboluo_rate":1,"assign_money":1011,"GoodsTypeName":"手表手饰"}]}
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
         * goodsId : 3048
         * goodsTypeId : 13
         * parentId : 47
         * seckillId : 0
         * seckillPrice : 11
         * isJifen : 1
         * jifenPrice : 11
         * goodsName : 11
         * goodsCode : null
         * goodsLogo : /GoodsLogo/3accae2a4ad04feaa2baa7195aa8fc4e.jpg
         * goodsPicture : /GoodsPicture/ead168c4862b40c088d76532ec307c5b.jpg
         * goodsSub : null
         * goodsQuantity : 0
         * goodsOriginalPrice : 25
         * goodsPrice : 2565
         * hyPrice : 77
         * yunfei : 0
         * goodsColor : [{"color":"11","colorImg":"/GoodsColorImg/2122ff5fd70b431bbeabcbf1597d3566.jpg;/GoodsColorImg/0cf0a90b6379469794593f487b1fa64b.jpg"}]
         * goodsStandards : [{"standard":"","price":2565,"hyPrice":77,"goodsOriginalPrice":25},{"standard":"","price":2565,"hyPrice":77,"goodsOriginalPrice":25}]
         * isList : 1
         * isGoodsQuantity : 0
         * buyQuantity : 0
         * isLevelDiscount : 0
         * comments : 0
         * saleCount : 0
         * sort : 1
         * isHome : 0
         * goodsDes : <p>11</p>
         * addTime : 2016-10-11T01:24:13
         * status : 0
         * directly_money : 34.2
         * directly_rate : 45
         * superior_money : 19
         * superior_rate : 25
         * three_money : 7.6
         * three_rate : 10
         * factory_price : 1
         * aboluo_money : 15.2
         * aboluo_rate : 20
         * assign_money : 76
         * GoodsTypeName : 女士内衣
         */

        private List<GoodsListBean> GoodsList;

        public List<GoodsListBean> getGoodsList() {
            return GoodsList;
        }

        public void setGoodsList(List<GoodsListBean> GoodsList) {
            this.GoodsList = GoodsList;
        }

        public static class GoodsListBean {
            private int goodsId;
            private int goodsTypeId;
            private int parentId;
            private int seckillId;
            private int seckillPrice;
            private int isJifen;
            private int jifenPrice;
            private String goodsName;
            private Object goodsCode;
            private String goodsLogo;
            private String goodsPicture;
            private Object goodsSub;
            private int goodsQuantity;
            private int goodsOriginalPrice;
            private int goodsPrice;
            private int hyPrice;
            private int yunfei;
            private int isList;
            private int isGoodsQuantity;
            private int buyQuantity;
            private int isLevelDiscount;
            private int comments;
            private int saleCount;
            private int sort;
            private int isHome;
            private String goodsDes;
            private String addTime;
            private int status;
            private double directly_money;
            private int directly_rate;
            private int superior_money;
            private int superior_rate;
            private double three_money;
            private int three_rate;
            private int factory_price;
            private double aboluo_money;
            private int aboluo_rate;
            private int assign_money;
            private String GoodsTypeName;
            /**
             * color : 11
             * colorImg : /GoodsColorImg/2122ff5fd70b431bbeabcbf1597d3566.jpg;/GoodsColorImg/0cf0a90b6379469794593f487b1fa64b.jpg
             */

            private List<GoodsColorBean> goodsColor;
            /**
             * standard :
             * price : 2565
             * hyPrice : 77
             * goodsOriginalPrice : 25
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

            public int getSeckillPrice() {
                return seckillPrice;
            }

            public void setSeckillPrice(int seckillPrice) {
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

            public int getGoodsOriginalPrice() {
                return goodsOriginalPrice;
            }

            public void setGoodsOriginalPrice(int goodsOriginalPrice) {
                this.goodsOriginalPrice = goodsOriginalPrice;
            }

            public int getGoodsPrice() {
                return goodsPrice;
            }

            public void setGoodsPrice(int goodsPrice) {
                this.goodsPrice = goodsPrice;
            }

            public int getHyPrice() {
                return hyPrice;
            }

            public void setHyPrice(int hyPrice) {
                this.hyPrice = hyPrice;
            }

            public int getYunfei() {
                return yunfei;
            }

            public void setYunfei(int yunfei) {
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

            public String getGoodsDes() {
                return goodsDes;
            }

            public void setGoodsDes(String goodsDes) {
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

            public int getDirectly_rate() {
                return directly_rate;
            }

            public void setDirectly_rate(int directly_rate) {
                this.directly_rate = directly_rate;
            }

            public int getSuperior_money() {
                return superior_money;
            }

            public void setSuperior_money(int superior_money) {
                this.superior_money = superior_money;
            }

            public int getSuperior_rate() {
                return superior_rate;
            }

            public void setSuperior_rate(int superior_rate) {
                this.superior_rate = superior_rate;
            }

            public double getThree_money() {
                return three_money;
            }

            public void setThree_money(double three_money) {
                this.three_money = three_money;
            }

            public int getThree_rate() {
                return three_rate;
            }

            public void setThree_rate(int three_rate) {
                this.three_rate = three_rate;
            }

            public int getFactory_price() {
                return factory_price;
            }

            public void setFactory_price(int factory_price) {
                this.factory_price = factory_price;
            }

            public double getAboluo_money() {
                return aboluo_money;
            }

            public void setAboluo_money(double aboluo_money) {
                this.aboluo_money = aboluo_money;
            }

            public int getAboluo_rate() {
                return aboluo_rate;
            }

            public void setAboluo_rate(int aboluo_rate) {
                this.aboluo_rate = aboluo_rate;
            }

            public int getAssign_money() {
                return assign_money;
            }

            public void setAssign_money(int assign_money) {
                this.assign_money = assign_money;
            }

            public String getGoodsTypeName() {
                return GoodsTypeName;
            }

            public void setGoodsTypeName(String GoodsTypeName) {
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
                private int price;
                private int hyPrice;
                private int goodsOriginalPrice;

                public String getStandard() {
                    return standard;
                }

                public void setStandard(String standard) {
                    this.standard = standard;
                }

                public int getPrice() {
                    return price;
                }

                public void setPrice(int price) {
                    this.price = price;
                }

                public int getHyPrice() {
                    return hyPrice;
                }

                public void setHyPrice(int hyPrice) {
                    this.hyPrice = hyPrice;
                }

                public int getGoodsOriginalPrice() {
                    return goodsOriginalPrice;
                }

                public void setGoodsOriginalPrice(int goodsOriginalPrice) {
                    this.goodsOriginalPrice = goodsOriginalPrice;
                }
            }
        }
    }
}
