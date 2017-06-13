package com.aboluo.model;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by cj on 2017/6/13.
 */

public class GoodsRecommend {

    /**
     * IsSuccess : true
     * Message : null
     * Result : null
     * ListResult : [{"goodsId":9656,"goodsName":"破洞九分裤宽松显瘦小直筒裤","goodsLogo":"/GoodsLogo/60a725fb77cc47968d20d6f30a9b881f.png;","goodsPicture":"/GoodsPicture/5ddba3d28f4143d5b57728ea6b6a42b0.png;/GoodsPicture/9293a9db57864b7c822ea7f152d002b5.png;/GoodsPicture/38a039c0fbf14f969978bbbee38fbff5.png;/GoodsPicture/0f5947761e0247faad361b1ffd8753cc.png;/GoodsPicture/3685b9e39b31470c95d6522b0b09df35.png;","goodsSub":"夏季薄款","goodsQuantity":500,"goodsPrice":89,"hyPrice":58},{"goodsId":9654,"goodsName":"韩版黑色破洞牛仔裤九分裤高腰弹力修身小脚裤","goodsLogo":"/GoodsLogo/85a0e54b1a90405597c68ec12d86a824.png;","goodsPicture":"/GoodsPicture/13e6a07514094fc3a0584730a72c0186.png;/GoodsPicture/6618be9ed6d14b33b64a6ece45c202c2.png;/GoodsPicture/0e6b2825b09b4bc6b9a09badb3164570.png;/GoodsPicture/e08711c7ce3c472cb95cf75535399260.png;/GoodsPicture/1120e3456e7a481ea507133544cb3685.png;","goodsSub":"显瘦铅笔裤","goodsQuantity":500,"goodsPrice":89,"hyPrice":68},{"goodsId":9653,"goodsName":"新款时尚修身薄款磨边阔腿喇叭裤","goodsLogo":"/GoodsLogo/c68dab587fb54fa1bb2c2bd509fe14a5.png;","goodsPicture":"/GoodsPicture/0130bfdec0574a4fabff6c6f21d336a3.png;/GoodsPicture/070ee7a80cdd41e79a2c534516225104.png;/GoodsPicture/d0263bdc55ae4fd5a3f8cfab92dfb5d0.png;/GoodsPicture/cc7143549ae94e9796085ef4ca3c2af4.png;/GoodsPicture/d348d8ba8b2a4d76bc294ed5f38c8e99.png;","goodsSub":"喇叭裤","goodsQuantity":500,"goodsPrice":89,"hyPrice":60},{"goodsId":9650,"goodsName":"春夏新款薄款直筒大码牛仔裤时尚潮流韩版气质","goodsLogo":"/GoodsLogo/50ee37810c6d491596e9e7ce2cdffd26.png;","goodsPicture":"/GoodsPicture/debff87448cf44e7a30d62c6a94d87f7.png;/GoodsPicture/b234a522ca394f4b8d2abd1bb5a514b2.png;/GoodsPicture/b66d7a457db94fa5af457370fa54d40e.png;/GoodsPicture/8513a905f9b24d62bf6fa5451bf8931f.png;/GoodsPicture/0bb32c2f8ff34767a41b833bb1cd4270.png;","goodsSub":"直筒裤","goodsQuantity":500,"goodsPrice":99,"hyPrice":65},{"goodsId":9644,"goodsName":"女式小脚修身显瘦新款牛仔裤","goodsLogo":"/GoodsLogo/159a93a804534875a2744a43c21e399f.png;","goodsPicture":"/GoodsPicture/bb84288423da46169ca9e258b6e2569c.png;/GoodsPicture/e7a42820860c45268d13220387186cb4.png;/GoodsPicture/ecbfe870b6474d669d16a5bfb910b2f6.png;/GoodsPicture/7dc1697c7ddb437cb8103b8f6ff1249d.png;/GoodsPicture/c856ea485d0a43ccbfeb829f781945bb.png;","goodsSub":"弹力瘦身","goodsQuantity":500,"goodsPrice":89,"hyPrice":65},{"goodsId":9641,"goodsName":"春夏薄款女式破洞牛仔九分裤","goodsLogo":"/GoodsLogo/7b13b0d85ea8476882a305da67cef328.png;","goodsPicture":"/GoodsPicture/cb3f7830dbd34047a9a43285d2429392.png;/GoodsPicture/06e45827e41b4e9cac3c7e8dc0239b0b.png;/GoodsPicture/2ccb27e9700c40f6b2f6513a527672a5.png;/GoodsPicture/31b010c194804d25b642ea1953bdb2cc.png;/GoodsPicture/a2cc27839a4b4127a96750b39dfed6b6.png;","goodsSub":"时尚小脚弹力修身显瘦","goodsQuantity":200,"goodsPrice":89,"hyPrice":58},{"goodsId":7162,"goodsName":"高腰显瘦冬款弹力加厚保暖小脚铅笔牛仔裤","goodsLogo":"/GoodsLogo/7020764b050a4f468d843bc05a3c825b.png;","goodsPicture":"/GoodsPicture/a736c00507b6426ebd48ec59bec9b86e.png;/GoodsPicture/a5ed98de26b7486ba5bf3012a7dea58e.png;/GoodsPicture/09cb5312b95c4f68aaeb6126abc74d65.png;/GoodsPicture/ce78ed966c3b4031952e9cece4abcf17.png;/GoodsPicture/6d752e4ba290480fabab955bb8175c9f.png;","goodsSub":"弹力加厚保暖","goodsQuantity":500,"goodsPrice":189,"hyPrice":129},{"goodsId":7160,"goodsName":"冬季韩版高腰刺绣加绒小脚牛仔裤","goodsLogo":"/GoodsLogo/fe65323a0da347ee8f3a35ff76e277f5.png;","goodsPicture":"/GoodsPicture/78caaee08cd946ca9e5e40ae8e1b96ae.png;/GoodsPicture/a897b19e43494b1087455d6e9f9d9578.png;/GoodsPicture/aa48c43afdda4e38a50f7139793ca6a9.png;/GoodsPicture/28dc1ca174674898bba26ba4333dcdd1.png;/GoodsPicture/970abd6a0ce94d7c84b0442b6ff036a8.png;","goodsSub":"保暖显瘦","goodsQuantity":500,"goodsPrice":159,"hyPrice":119},{"goodsId":7158,"goodsName":"加绒加厚外穿高腰小脚铅笔裤","goodsLogo":"/GoodsLogo/125fa14467434a35957db8a050ff709c.png;","goodsPicture":"/GoodsPicture/e5adb8e4a26b4a82bad09563439a8f7e.png;/GoodsPicture/a0e2090da5c24bc8bc768e197f18d9c3.png;/GoodsPicture/fbdfb978ac874d48a89d56da634932e3.png;/GoodsPicture/fb351d1e4949456c8c48c9b18b4f2b10.png;/GoodsPicture/1411125a71dc44c1a00466c4b602471b.png;","goodsSub":"加厚保暖舒适","goodsQuantity":500,"goodsPrice":180,"hyPrice":129},{"goodsId":7156,"goodsName":"冬季新款小脚高腰弹力松紧腰加厚加绒牛仔裤","goodsLogo":"/GoodsLogo/2f19b5d47ade4e6daa92b02edb6bce95.png;","goodsPicture":"/GoodsPicture/f8a6c41d732f41189b5ff87bd0fca828.png;/GoodsPicture/4bba74a2593842db9bf4009e4290b561.png;/GoodsPicture/e348f73c7d1c4ed2bbec616e8e383ca9.png;/GoodsPicture/150e1548e8a9408686d9053fd3fca9bc.png;/GoodsPicture/aac1c5bdd50e44a8ae05916ff24e86be.png;","goodsSub":"修身显瘦","goodsQuantity":500,"goodsPrice":189,"hyPrice":125}]
     */

    private boolean IsSuccess;
    private Object Message;
    private Object Result;
    private List<ListResultBean> ListResult;

    public static GoodsRecommend objectFromData(String str) {

        return new Gson().fromJson(str, GoodsRecommend.class);
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

    public Object getResult() {
        return Result;
    }

    public void setResult(Object Result) {
        this.Result = Result;
    }

    public List<ListResultBean> getListResult() {
        return ListResult;
    }

    public void setListResult(List<ListResultBean> ListResult) {
        this.ListResult = ListResult;
    }

    public static class ListResultBean {
        /**
         * goodsId : 9656
         * goodsName : 破洞九分裤宽松显瘦小直筒裤
         * goodsLogo : /GoodsLogo/60a725fb77cc47968d20d6f30a9b881f.png;
         * goodsPicture : /GoodsPicture/5ddba3d28f4143d5b57728ea6b6a42b0.png;/GoodsPicture/9293a9db57864b7c822ea7f152d002b5.png;/GoodsPicture/38a039c0fbf14f969978bbbee38fbff5.png;/GoodsPicture/0f5947761e0247faad361b1ffd8753cc.png;/GoodsPicture/3685b9e39b31470c95d6522b0b09df35.png;
         * goodsSub : 夏季薄款
         * goodsQuantity : 500
         * goodsPrice : 89
         * hyPrice : 58
         */

        private int goodsId;
        private String goodsName;
        private String goodsLogo;
        private String goodsPicture;
        private String goodsSub;
        private Double goodsQuantity;
        private Double goodsPrice;
        private Double hyPrice;

        public static ListResultBean objectFromData(String str) {

            return new Gson().fromJson(str, ListResultBean.class);
        }

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

        public Double getGoodsQuantity() {
            return goodsQuantity;
        }

        public void setGoodsQuantity(Double goodsQuantity) {
            this.goodsQuantity = goodsQuantity;
        }

        public Double getGoodsPrice() {
            return goodsPrice;
        }

        public void setGoodsPrice(Double goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public Double getHyPrice() {
            return hyPrice;
        }

        public void setHyPrice(Double hyPrice) {
            this.hyPrice = hyPrice;
        }
    }
}
