package com.aboluo.model;

import java.security.PublicKey;

/**
 * Created by CJ on 2016/9/28.
 */

public class ShopCarInfo {
    private String goodsName;
    private int num;
    private int checkflag;
    private int editflag;

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    private double money;
    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getCheckflag() {
        return checkflag;
    }

    public void setCheckflag(int checkflag) {
        this.checkflag = checkflag;
    }

    public int getEditflag() {
        return editflag;
    }

    public void setEditflag(int editflag) {
        this.editflag = editflag;
    }
}
