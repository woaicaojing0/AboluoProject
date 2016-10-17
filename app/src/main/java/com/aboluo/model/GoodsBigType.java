package com.aboluo.model;

import java.util.List;

/**
 * Created by CJ on 2016/10/17.
 */

public class GoodsBigType {

    /**
     * IsSuccess : true
     * Message : null
     * Result : {"GoodsTypeList":[{"goodsTypeId":47,"goodsTypeName":"服装","parentId":0,"sort":1,"typeLevel":0,"goodsTypeSub":"服装","goodsTypeImg":"/GoodsTypeImg//uploads/goodsType/fuzhuangxiefu.jpg","goodsAdsImg":"/GoodsAdsImg//uploads/goodsType/fuzhuangxiefu.jpg","isShow":1,"isList":0,"flag":1},{"goodsTypeId":48,"goodsTypeName":"箱包配饰","parentId":0,"sort":2,"typeLevel":0,"goodsTypeSub":"箱包配饰","goodsTypeImg":"/GoodsTypeImg//uploads/goodsType/xiangbaopeishi.jpg","goodsAdsImg":"/GoodsAdsImg//uploads/goodsType/xiangbaopeishi.jpg","isShow":1,"isList":0,"flag":1},{"goodsTypeId":49,"goodsTypeName":"母婴文体","parentId":0,"sort":3,"typeLevel":0,"goodsTypeSub":"母婴文体","goodsTypeImg":"/GoodsTypeImg//uploads/goodsType/muyingwenti.jpg","goodsAdsImg":"/GoodsAdsImg//uploads/goodsType/muyingwenti.jpg","isShow":1,"isList":0,"flag":1},{"goodsTypeId":50,"goodsTypeName":"数码电器","parentId":0,"sort":4,"typeLevel":0,"goodsTypeSub":"数码电器","goodsTypeImg":"/GoodsTypeImg//uploads/goodsType/shumadianqi.jpg","goodsAdsImg":"/GoodsAdsImg//uploads/goodsType/shumadianqi.jpg","isShow":1,"isList":0,"flag":1},{"goodsTypeId":51,"goodsTypeName":"休闲食品","parentId":0,"sort":5,"typeLevel":0,"goodsTypeSub":"休闲食品","goodsTypeImg":"/GoodsTypeImg//uploads/goodsType/xiuxianshipin.jpg","goodsAdsImg":"/GoodsAdsImg//uploads/goodsType/xiuxianshipin.jpg","isShow":1,"isList":0,"flag":1},{"goodsTypeId":52,"goodsTypeName":"家居家纺","parentId":0,"sort":6,"typeLevel":0,"goodsTypeSub":"家居家纺","goodsTypeImg":"/GoodsTypeImg//uploads/goodsType/jiajujiafang.jpg","goodsAdsImg":"/GoodsAdsImg//uploads/goodsType/jiajujiafang.jpg","isShow":1,"isList":0,"flag":1},{"goodsTypeId":53,"goodsTypeName":"美妆日化","parentId":0,"sort":7,"typeLevel":0,"goodsTypeSub":"美妆日化","goodsTypeImg":"/GoodsTypeImg//uploads/goodsType/meizhuangrihua.jpg","goodsAdsImg":"/GoodsAdsImg//uploads/goodsType/meizhuangrihua.jpg","isShow":1,"isList":0,"flag":1},{"goodsTypeId":54,"goodsTypeName":"其他百货","parentId":0,"sort":8,"typeLevel":0,"goodsTypeSub":"其他百货","goodsTypeImg":"/GoodsTypeImg//uploads/goodsType/baihuo.jpg","goodsAdsImg":"/GoodsAdsImg//uploads/goodsType/baihuo.jpg","isShow":1,"isList":0,"flag":1},{"goodsTypeId":55,"goodsTypeName":"合伙人商品","parentId":0,"sort":10,"typeLevel":0,"goodsTypeSub":"合伙人商品","goodsTypeImg":"/GoodsTypeImg//uploads/goodsType/5779d43731afd.jpg","goodsAdsImg":"/GoodsAdsImg//uploads/goodsType/5779d43731afd.jpg","isShow":1,"isList":0,"flag":1},{"goodsTypeId":73,"goodsTypeName":"鞋帽","parentId":0,"sort":11,"typeLevel":0,"goodsTypeSub":"鞋帽","goodsTypeImg":"/GoodsTypeImg//uploads/goodsType/xiemao.jpg","goodsAdsImg":"/GoodsAdsImg//uploads/goodsType/xiemao.jpg","isShow":1,"isList":0,"flag":1},{"goodsTypeId":79,"goodsTypeName":"xx","parentId":0,"sort":1,"typeLevel":0,"goodsTypeSub":"xxx","goodsTypeImg":"/GoodsTypeImg/d26fe1d704bb4c5bab5f2b81710c2bfd.jpg","goodsAdsImg":"/GoodsAdsImg/3bc4a1a2ba71492d8c9cf147e5891178.jpg","isShow":1,"isList":0,"flag":1}]}
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
         * goodsTypeId : 47
         * goodsTypeName : 服装
         * parentId : 0
         * sort : 1
         * typeLevel : 0
         * goodsTypeSub : 服装
         * goodsTypeImg : /GoodsTypeImg//uploads/goodsType/fuzhuangxiefu.jpg
         * goodsAdsImg : /GoodsAdsImg//uploads/goodsType/fuzhuangxiefu.jpg
         * isShow : 1
         * isList : 0
         * flag : 1
         */

        private List<GoodsTypeListBean> GoodsTypeList;

        public List<GoodsTypeListBean> getGoodsTypeList() {
            return GoodsTypeList;
        }

        public void setGoodsTypeList(List<GoodsTypeListBean> GoodsTypeList) {
            this.GoodsTypeList = GoodsTypeList;
        }

        public static class GoodsTypeListBean {
            private int goodsTypeId;
            private String goodsTypeName;
            private int parentId;
            private int sort;
            private int typeLevel;
            private String goodsTypeSub;
            private String goodsTypeImg;
            private String goodsAdsImg;
            private int isShow;
            private int isList;
            private int flag;

            public int getGoodsTypeId() {
                return goodsTypeId;
            }

            public void setGoodsTypeId(int goodsTypeId) {
                this.goodsTypeId = goodsTypeId;
            }

            public String getGoodsTypeName() {
                return goodsTypeName;
            }

            public void setGoodsTypeName(String goodsTypeName) {
                this.goodsTypeName = goodsTypeName;
            }

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getTypeLevel() {
                return typeLevel;
            }

            public void setTypeLevel(int typeLevel) {
                this.typeLevel = typeLevel;
            }

            public String getGoodsTypeSub() {
                return goodsTypeSub;
            }

            public void setGoodsTypeSub(String goodsTypeSub) {
                this.goodsTypeSub = goodsTypeSub;
            }

            public String getGoodsTypeImg() {
                return goodsTypeImg;
            }

            public void setGoodsTypeImg(String goodsTypeImg) {
                this.goodsTypeImg = goodsTypeImg;
            }

            public String getGoodsAdsImg() {
                return goodsAdsImg;
            }

            public void setGoodsAdsImg(String goodsAdsImg) {
                this.goodsAdsImg = goodsAdsImg;
            }

            public int getIsShow() {
                return isShow;
            }

            public void setIsShow(int isShow) {
                this.isShow = isShow;
            }

            public int getIsList() {
                return isList;
            }

            public void setIsList(int isList) {
                this.isList = isList;
            }

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }
        }
    }
}
