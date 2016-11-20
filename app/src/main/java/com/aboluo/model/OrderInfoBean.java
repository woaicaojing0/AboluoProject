package com.aboluo.model;

import java.util.List;

/**
 * Created by CJ on 2016/11/20.
 */

public class OrderInfoBean {
        private int GoodsId;
        private int GoodsColorId;
        private int GoodsStandardId;
        private String GoodsStandard;
        private String GoodsColor;
        private int GoodsQuantity;
        private int Freight;
        public int getGoodsId() {
            return GoodsId;
        }

        public void setGoodsId(int goodsId) {
            GoodsId = goodsId;
        }

        public int getGoodsColorId() {
            return GoodsColorId;
        }

        public void setGoodsColorId(int goodsColorId) {
            GoodsColorId = goodsColorId;
        }

        public int getGoodsStandardId() {
            return GoodsStandardId;
        }

        public void setGoodsStandardId(int goodsStandardId) {
            GoodsStandardId = goodsStandardId;
        }

        public String getGoodsStandard() {
            return GoodsStandard;
        }

        public void setGoodsStandard(String goodsStandard) {
            GoodsStandard = goodsStandard;
        }

        public String getGoodsColor() {
            return GoodsColor;
        }

        public void setGoodsColor(String goodsColor) {
            GoodsColor = goodsColor;
        }

        public int getGoodsQuantity() {
            return GoodsQuantity;
        }

        public void setGoodsQuantity(int goodsQuantity) {
            GoodsQuantity = goodsQuantity;
        }

        public int getFreight() {
            return Freight;
        }

        public void setFreight(int freight) {
            Freight = freight;
        }

}
