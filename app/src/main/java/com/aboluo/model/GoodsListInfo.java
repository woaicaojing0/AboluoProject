package com.aboluo.model;

import java.util.List;

/**
 * Created by CJ on 2016/10/18.
 */

public class GoodsListInfo {


    /**
     * IsSuccess : true
     * Message : null
     * Result : {"GoodsList":[{"goodsId":3048,"goodsName":"测试商品1111","goodsLogo":"/GoodsLogo/3accae2a4ad04feaa2baa7195aa8fc4e.jpg","goodsPicture":"/GoodsPicture/ead168c4862b40c088d76532ec307c5b.jpg","goodsSub":"测试商品1111","goodsQuantity":0,"goodsPrice":2565,"hyPrice":77},{"goodsId":3047,"goodsName":"阿波罗研发测试商品","goodsLogo":"/GoodsLogo/87f7222bd99d4d64b2333bb26887ee6b.jpg","goodsPicture":"/GoodsPicture/c4915713bc494558b214ceda14b64631.jpg","goodsSub":"1011","goodsQuantity":1011,"goodsPrice":1011,"hyPrice":1011},{"goodsId":3045,"goodsName":"xx ","goodsLogo":null,"goodsPicture":null,"goodsSub":null,"goodsQuantity":0,"goodsPrice":0,"hyPrice":0},{"goodsId":3044,"goodsName":"11","goodsLogo":null,"goodsPicture":null,"goodsSub":"11","goodsQuantity":11,"goodsPrice":11,"hyPrice":11},{"goodsId":3043,"goodsName":"1","goodsLogo":"/GoodsLogo/03344596e776482aaf0c00b888dd3116.jpg/GoodsLogo/e71e0f0c4ee74ee1ae269df181c0f524.jpg/GoodsLogo/ea4e273d46414994bd33b40c3b51ab29.jpg/GoodsLogo/f6d50d40db2c41b6a2dcf155d76066eb.jpg/GoodsLogo/fae5760eb1eb461b8aa4f3b512784019.jpg","goodsPicture":"/GoodsPicture/d48f9ae239ac4c2793ae6d947fc68b50.jpg/GoodsPicture/75f3e663db02468198f35eab8a0a797a.jpg/GoodsPicture/bc8d9113bb1b49388ff53bc3f3384825.jpg/GoodsPicture/11fdb31f791f457f98c922b74465472b.jpg","goodsSub":"1","goodsQuantity":11,"goodsPrice":1,"hyPrice":11},{"goodsId":3042,"goodsName":"测试商品20161009","goodsLogo":"/GoodsLogo/5327c08c878f4082b048298f29dd7057.jpg/GoodsLogo/549f541b45474c0c8b10b3af95097182.jpg/GoodsLogo/3621e273329a497f910e04d1c30cb7c4.jpg/GoodsLogo/c73133c07f894b638e2d52bacba710ec.jpg","goodsPicture":"/GoodsPicture/b780f89dd8894e96a539217f80ce4706.jpg","goodsSub":"测试","goodsQuantity":111,"goodsPrice":111,"hyPrice":111},{"goodsId":3041,"goodsName":"??????????? ?????????750ML","goodsLogo":"/GoodsLogo//uploads/goods/57df5434f11c9.png","goodsPicture":"/GoodsPicture//uploads/goods/57df543cd2b88.png","goodsSub":"","goodsQuantity":7,"goodsPrice":8000,"hyPrice":0},{"goodsId":3040,"goodsName":"???????????t? ????t? ?????","goodsLogo":"/GoodsLogo//uploads/goods/57df520a2e448.jpg","goodsPicture":"/GoodsPicture//uploads/goods/57df521165266.jpg|/uploads/goods/57df5217e8669.jpg|/uploads/goods/57df521ef0c0f.jpg|/uploads/goods/57df523f3c71b.jpg|/uploads/goods/57df52469f143.jpg|/uploads/goods/57df524de023c.jpg","goodsSub":"","goodsQuantity":300,"goodsPrice":87,"hyPrice":76},{"goodsId":3039,"goodsName":"????????????? ?????? ????????","goodsLogo":"/GoodsLogo//uploads/goods/57df4c7e863ac.jpg","goodsPicture":"/GoodsPicture//uploads/goods/57df4c85b3aaa.jpg|/uploads/goods/57df4c9828722.jpg|/uploads/goods/57df4c9e5e2b9.jpg|/uploads/goods/57df4ca5928c9.jpg","goodsSub":null,"goodsQuantity":89,"goodsPrice":185,"hyPrice":168},{"goodsId":3038,"goodsName":"?????????","goodsLogo":"/GoodsLogo//uploads/goods/57ddf7848dc7a.jpg","goodsPicture":"/GoodsPicture//uploads/goods/57ddf78de9d18.jpg|/uploads/goods/57ddf7978a0f3.jpg|/uploads/goods/57ddf79f67204.jpg|/uploads/goods/57ddf7a9c3016.jpg|/uploads/goods/57ddf7b75d484.jpg|/uploads/goods/57ddf7c188196.jpg","goodsSub":"","goodsQuantity":280,"goodsPrice":189,"hyPrice":176}]}
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
         * goodsName : 测试商品1111
         * goodsLogo : /GoodsLogo/3accae2a4ad04feaa2baa7195aa8fc4e.jpg
         * goodsPicture : /GoodsPicture/ead168c4862b40c088d76532ec307c5b.jpg
         * goodsSub : 测试商品1111
         * goodsQuantity : 0
         * goodsPrice : 2565.0
         * hyPrice : 77.0
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
            private String goodsName;
            private String goodsLogo;
            private String goodsPicture;
            private String goodsSub;
            private int goodsQuantity;
            private double goodsPrice;
            private double hyPrice;

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
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
        }
    }
}
