package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.adapter.OrderSureListViewAdapter;
import com.aboluo.model.AddressDefaultBean;
import com.aboluo.model.AddressInfoBean;
import com.aboluo.model.BaseModel;
import com.aboluo.model.MakerOrderIntergralBean;
import com.aboluo.model.OrderInfoBean;
import com.aboluo.model.ShopCarBean.ResultBean.GoodsShoppingCartListBean;
import com.aboluo.widget.MyListview;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by cj34920 on 2016/11/17.
 * 下单确认界面，选择代金券或者积分
 */
public class MakeOrderActivity extends Activity implements View.OnClickListener {
    private OrderSureListViewAdapter orderSureListViewAdapter;
    private ArrayList<GoodsShoppingCartListBean> goodsShoppingCartListBean;
    private MyListview OrderList;
    private TextView txt_allmoney, goods_smallallmoeny, txt_goods_allnum;
    private TextView address_detailaddress, address_phone, address_name;
    private Volley volley;
    private String url;
    private String groupBuyUrl;
    private static String APPToken;
    private StringRequest requestlist;
    private RequestQueue requestQueue;
    private Gson gson;
    private AddressDefaultBean addressDefaultBean;
    private SweetAlertDialog pdialog;
    private EditText edit_remark;
    private Button Submit_Order;
    private int requsetcode = 1;
    private static String moeny;
    private static String oldmoeny;
    private TextView order_yunfei, tv_order_tishi;
    private double yunfei = 0.0;
    private int choose_address_requestcode = 2;
    private int AddressId = 0;
    private RelativeLayout change_make_sure_location;
    private String MemberId;
    private String payfrom; //从哪边支付
    private int OnePurchaseId; //一元夺宝购的场次
    private ImageView all_makeorder_text_back;
    private RelativeLayout rl_makeorder_usecoupons, rl_makerorder_jifeng;
    private int MakeOrderRequestCode = 3;
    private CheckBox ck_makerorder_jifeng;
    private String CouponId; //代金券的id
    private String Remark; //代金券的解释说明
    private String CouponMoney;//代金券抵用金额
    private TextView tv_makeorder_showcoupons, tv_makerorder_isuserintegral, tv_makerorder_IntegralCount, tv_makerorder_IntegralPrice;
    private String discountType = "0"; //代表不使用优惠券和积分，1代表使用积分，2代表使用优惠券
    private MakerOrderIntergralBean makerOrderIntergralBean;
    private LinearLayout ll_makerorder_jifeng, order_Line_tishi;
    private int SeckillId; // 秒杀的商品的id
    private boolean isFirst = true;
    private int TeamBuyId;//拼团购的场次

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_makeorder);
        OrderList = (MyListview) findViewById(R.id.orderlist);
        MemberId = CommonUtils.GetMemberId(MakeOrderActivity.this);
        init();
        orderSureListViewAdapter = new OrderSureListViewAdapter(goodsShoppingCartListBean, this);
        OrderList.setAdapter(orderSureListViewAdapter);
        for (int i = 0; i < goodsShoppingCartListBean.size(); i++) {
            yunfei = yunfei + goodsShoppingCartListBean.get(i).getYunfei();
        }
        change_make_sure_location.setOnClickListener(this);
        all_makeorder_text_back.setOnClickListener(this);
        rl_makeorder_usecoupons.setOnClickListener(this);
        rl_makerorder_jifeng.setOnClickListener(this);
        ck_makerorder_jifeng.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub 
                if (isChecked) {
                    discountType = "1";
                    double result = Double.valueOf(moeny) - makerOrderIntergralBean
                            .getResult().getIntegralPrice();
                    txt_allmoney.setText(String.valueOf(result));
                    rl_makeorder_usecoupons.setClickable(false);
                    Log.i("MakeOrderActivity", "选择积分了，积分低用金额"
                            + String.valueOf(makerOrderIntergralBean
                            .getResult().getIntegralPrice()) + "总金额为" + String.valueOf(result));
                } else {
                    txt_allmoney.setText(moeny);
                    rl_makeorder_usecoupons.setClickable(true);
                }
            }
        });
    }

    private void init() {
        AddressId = 0;
        txt_allmoney = (TextView) findViewById(R.id.txt_allmoney);
        ck_makerorder_jifeng = (CheckBox) findViewById(R.id.ck_makerorder_jifeng);
        rl_makeorder_usecoupons = (RelativeLayout) findViewById(R.id.rl_makeorder_usecoupons);
        rl_makerorder_jifeng = (RelativeLayout) findViewById(R.id.rl_makerorder_jifeng);
        all_makeorder_text_back = (ImageView) findViewById(R.id.all_makeorder_text_back);
        goods_smallallmoeny = (TextView) findViewById(R.id.goods_smallallmoeny);
        txt_goods_allnum = (TextView) findViewById(R.id.txt_goods_allnum);
        address_detailaddress = (TextView) findViewById(R.id.address_detailaddress);
        address_phone = (TextView) findViewById(R.id.address_phone);
        address_name = (TextView) findViewById(R.id.address_name);
        edit_remark = (EditText) findViewById(R.id.edit_remark);
        Submit_Order = (Button) findViewById(R.id.Submit_Order);
        order_yunfei = (TextView) findViewById(R.id.order_yunfei);
        tv_makeorder_showcoupons = (TextView) findViewById(R.id.tv_makeorder_showcoupons);
        tv_makerorder_isuserintegral = (TextView) findViewById(R.id.tv_makerorder_isuserintegral);
        tv_makerorder_IntegralCount = (TextView) findViewById(R.id.tv_makerorder_IntegralCount);
        tv_makerorder_IntegralPrice = (TextView) findViewById(R.id.tv_makerorder_IntegralPrice);
        tv_order_tishi = (TextView) findViewById(R.id.tv_order_tishi);
        ll_makerorder_jifeng = (LinearLayout) findViewById(R.id.ll_makerorder_jifeng);
        order_Line_tishi = (LinearLayout) findViewById(R.id.order_Line_tishi);
        change_make_sure_location = (RelativeLayout) findViewById(R.id.change_make_sure_location);
        requestQueue = MyApplication.getRequestQueue();
        url = CommonUtils.GetValueByKey(this, "apiurl");
        groupBuyUrl = CommonUtils.GetValueByKey(this, "apiurl3");
        APPToken = CommonUtils.GetValueByKey(this, "APPToken");
        gson = new Gson();
        pdialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pdialog.setCanceledOnTouchOutside(true);
        pdialog.setCancelable(true);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        goodsShoppingCartListBean = bundle.getParcelableArrayList("data");
        moeny = bundle.get("allmoney").toString();
        oldmoeny = bundle.get("allmoney").toString();
        payfrom = bundle.get("payfrom").toString();
        OnePurchaseId = bundle.getInt("OnePurchaseId", 0);
        SeckillId = bundle.getInt("SeckillId", 0);
        TeamBuyId = bundle.getInt("TeamBuyId", 0);
        txt_allmoney.setText(bundle.get("allmoney").toString());
//        goods_smallallmoeny.setText("￥" + bundle.get("allmoney").toString());
        txt_goods_allnum.setText("共计" + goodsShoppingCartListBean.size() + "件商品");
        Submit_Order.setOnClickListener(this);
        initData();
        if (payfrom.equals("7")) {
            order_yunfei.setText("￥0");
            return;
        }
        getIntergralAndFreight();
    }

    private void initData() {
        pdialog.setTitleText("加载中");
        pdialog.show();
        requestlist = new StringRequest(Request.Method.POST, url + "/api/Order/ReceiveDefaultMemberAddressByMemberId", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                addressDefaultBean = gson.fromJson(response, AddressDefaultBean.class);
                AddressDefaultBean addressDefaultBean2 = addressDefaultBean;
                if (addressDefaultBean.isIsSuccess()) {
                    if (addressDefaultBean.getResult().getReceiver() == null) {
                        Toast.makeText(MakeOrderActivity.this, "请先设置默认地址！", Toast.LENGTH_SHORT).show();
                    } else {
                        StringBuffer stringBuffer = new StringBuffer();
                        if (addressDefaultBean.getResult().getProvince().toString().indexOf("省") == -1) {
                        } else {
                            stringBuffer.append(addressDefaultBean.getResult().getProvince().toString());
                        }
                        stringBuffer.append(addressDefaultBean.getResult().getCity().toString());
                        stringBuffer.append(addressDefaultBean.getResult().getRegion().toString());
                        stringBuffer.append(addressDefaultBean.getResult().getStreet().toString());
                        stringBuffer.append(addressDefaultBean.getResult().getAddress().toString());
                        address_detailaddress.setText(stringBuffer.toString());
                        address_name.setText(addressDefaultBean.getResult().getReceiver().toString());
                        address_phone.setText(addressDefaultBean.getResult().getMobile().toString());
                    }
                }
//                    addressInfoBean.getResult().getMemberAddressList().get(0).get
                else {
                    Toast.makeText(MakeOrderActivity.this, "请选择收货地址！", Toast.LENGTH_SHORT).show();
                    Log.i("woaicaojingpay", addressDefaultBean.getResult().toString());
                }
                pdialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MakeOrderActivity.this, "请选择收货地址！", Toast.LENGTH_SHORT).show();
                pdialog.dismiss();

                Log.i("woaicaojingpay", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", MemberId);
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(requestlist);
    }

    /**
     * 正常下单
     */
    private void SubmitOrder() {
        final String lastmoney = txt_allmoney.getText().toString();
        List<OrderInfoBean> bean = new ArrayList<>();
        for (int i = 0; i < goodsShoppingCartListBean.size(); i++) {
            OrderInfoBean bean1 = new OrderInfoBean();
            bean1.setGoodsId(goodsShoppingCartListBean.get(i).getGoodsId());
            if (goodsShoppingCartListBean.get(i).getGoodsColor().equals("无")) {
                bean1.setGoodsColor("");
            } else {
                bean1.setGoodsColor(goodsShoppingCartListBean.get(i).getGoodsColor());
            }
            bean1.setGoodsColorId(goodsShoppingCartListBean.get(i).getGoodsColorId());
            bean1.setGoodsStandardId(goodsShoppingCartListBean.get(i).getGoodsStandardId());
            if (goodsShoppingCartListBean.get(i).getGoodsStandard().equals("无")) {
                bean1.setGoodsStandard("");
            } else {
                bean1.setGoodsStandard(goodsShoppingCartListBean.get(i).getGoodsStandard());
            }
            bean1.setGoodsQuantity(goodsShoppingCartListBean.get(i).getGoodsCount());
            bean1.setFreight(goodsShoppingCartListBean.get(i).getYunfei());
            bean.add(bean1);
        }
        if (addressDefaultBean != null || AddressId != 0) {
            final Gson gson = new Gson();
            final String Products = gson.toJson(bean);
            pdialog.setTitleText("提交中");
            pdialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "/api/Order/SubOrder", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    response = response.replace("\\", "");
                    response = response.substring(1, response.length() - 1);
                    BaseModel baseModel = new BaseModel();
                    baseModel = gson.fromJson(response, BaseModel.class);
                    if (baseModel.isIsSuccess()) {
                        Intent intent = new Intent(MakeOrderActivity.this, OrderPayActivity.class);
                        intent.putExtra("payMoney", lastmoney);
                        intent.putExtra("OrderNum", baseModel.getOrderSerialId().toString());
                        intent.putExtra("payfrom", payfrom);
                        intent.putExtra("OnePurchaseId", OnePurchaseId);
                        startActivityForResult(intent, requsetcode);
                        pdialog.dismiss();
                    } else {
                    }
                    Toast.makeText(MakeOrderActivity.this, baseModel.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    pdialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MakeOrderActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
//                    byte[] htmlBodyBytes = error.networkResponse.data;
//                    Log.i("woaicaojingeorror", new String(htmlBodyBytes));
                    pdialog.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("MemberId", MemberId);
                    map.put("TotalPrice", txt_allmoney.getText().toString());
                    if (AddressId != 0) {
                        map.put("AddressId", String.valueOf(AddressId));
                    } else {
                        map.put("AddressId", String.valueOf(addressDefaultBean.getResult().getId()));
                    }
                    map.put("Remark", edit_remark.getText().toString());
                    map.put("Products", Products);
                    map.put("OrderType", payfrom);
                    map.put("ExpressPrice", String.valueOf(makerOrderIntergralBean.getResult().getExpressPrice()));
                    switch (discountType) {
                        case "0":
                            map.put("DeductionType", "0");
                            map.put("IntegralPrice", "0");
                            map.put("IntegralCount", "0");
                            map.put("CouponPrice", "0");
                            map.put("CouponId", "0");
                            break;
                        case "1":
                            map.put("IntegralPrice", String.valueOf(makerOrderIntergralBean.getResult().getIntegralPrice()));
                            map.put("IntegralCount", String.valueOf(makerOrderIntergralBean.getResult().getIntegralCount()));
                            map.put("CouponPrice", "0");
                            map.put("CouponId", "0");
                            map.put("DeductionType", "1");
                            break;
                        case "2":
                            map.put("IntegralPrice", "0");
                            map.put("IntegralCount", "0");
                            map.put("CouponPrice", CouponMoney);
                            map.put("CouponId", CouponId);
                            map.put("DeductionType", "2");
                            break;
                    }
                    map.put("APPToken", APPToken);
                    map.put("SeckillId", String.valueOf(SeckillId));
                    String result = gson.toJson(map);
                    Log.i("MakeOrderActivity", "确认订单上传值" + result);
                    return map;
                }
            };
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(this, "请选择收货地址", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 团购下单
     */
    private void groupBuyOrder() {
        if (TeamBuyId == 0) {
            Toast.makeText(this, "当前拼团购的场次不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        if (addressDefaultBean != null || AddressId != 0) {
            pdialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, groupBuyUrl + "/api/OrderManageApi/TeamBuyOrder", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("GroupBuyOrderInfo", response);
                    BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                    if (baseModel.isIsSuccess()) {
                        Intent intent = new Intent(MakeOrderActivity.this, OrderPayActivity.class);
                        intent.putExtra("payMoney", txt_allmoney.getText().toString());
                        intent.putExtra("OrderNum", baseModel.getOrderSerialId().toString());
                        intent.putExtra("payfrom", payfrom);
                        intent.putExtra("OnePurchaseId", OnePurchaseId);
                        startActivityForResult(intent, requsetcode);
                        pdialog.dismiss();
                    } else {
                    }
                    Toast.makeText(MakeOrderActivity.this, baseModel.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    pdialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    byte[] bytes = error.networkResponse.data;
                    Log.e("GroupOrderBuyError", new String(bytes));
                    pdialog.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("TeamBuyId", String.valueOf(TeamBuyId));
                    map.put("MemberId", MemberId);
                    if (AddressId != 0) {
                        map.put("ReceiveId", String.valueOf(AddressId));
                    } else {
                        map.put("ReceiveId", String.valueOf(addressDefaultBean.getResult().getId()));
                    }
                    map.put("OrderRemark", edit_remark.getText().toString());
                    map.put("APPToken", APPToken);
                    map.put("LoginCheckToken", "123");
                    map.put("LoginPhone", "123");
                    return map;
                }
            };
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(this, "请选择收货地址", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Submit_Order:
                if (payfrom.equals("1")) {
                    SubmitOrder();
                } else if (payfrom.equals("7")) {
                    groupBuyOrder();
                } else {
                }
                break;
            case R.id.change_make_sure_location:
                isFirst = true;
                Intent intent = new Intent(MakeOrderActivity.this, ChooseAddressActivtiy.class);
                startActivityForResult(intent, choose_address_requestcode);
                break;
            case R.id.all_makeorder_text_back:
                finish();
                break;
            case R.id.rl_makeorder_usecoupons:
                isFirst = true;
                if (payfrom.equals("1"))//正常的商品
                {
                    Intent intent1 = new Intent(MakeOrderActivity.this, CouponsActivity.class);
                    intent1.putExtra("allmoney", moeny);
                    startActivityForResult(intent1, MakeOrderRequestCode);
                } else {
                    Toast.makeText(this, "当前商品不支持使用代金券", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rl_makerorder_jifeng:
                isFirst = true;
                if (payfrom.equals("1"))//正常的商品
                {
                    if (ck_makerorder_jifeng.isChecked()) {
                        ck_makerorder_jifeng.setChecked(false);
                    } else {
                        ck_makerorder_jifeng.setChecked(true);
                    }
                } else {
                    Toast.makeText(this, "当前你商品不支持使用积分", Toast.LENGTH_SHORT).show();
                }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    this.finish();
                }
                break;
            case 2:
                if (resultCode == 2) {
                    Bundle bundle = data.getExtras();
                    List<AddressInfoBean.ResultBean.MemberAddressListBean> memberAddressListBean = bundle.getParcelableArrayList("data");
                    StringBuffer stringBuffer = new StringBuffer();
                    if (memberAddressListBean.get(0).getProvince().toString().indexOf("省") == -1) {
                    } else {
                        stringBuffer.append(memberAddressListBean.get(0).getProvince().toString());
                    }
                    stringBuffer.append(memberAddressListBean.get(0).getCity().toString());
                    stringBuffer.append(memberAddressListBean.get(0).getRegion().toString());
                    stringBuffer.append(memberAddressListBean.get(0).getStreet().toString());
                    stringBuffer.append(memberAddressListBean.get(0).getAddress().toString());
                    address_detailaddress.setText(stringBuffer.toString());
                    address_name.setText(memberAddressListBean.get(0).getReceiver().toString());
                    address_phone.setText(memberAddressListBean.get(0).getMobile().toString());
                    AddressId = memberAddressListBean.get(0).getId();
                }
                break;
            case 3:
                if (resultCode == RESULT_OK) { //选择了优惠券不管是返回还是选择了
                    CouponId = data.getStringExtra("CouponId");
                    Remark = data.getStringExtra("Remark");
                    if (CouponId.equals("0")) { //直接返回没有选择
                    } else {
                        if (Remark != null) { //点击item返回
                            CouponMoney = data.getStringExtra("CouponMoney");
                            tv_makeorder_showcoupons.setText(Remark);
                            rl_makerorder_jifeng.setClickable(false);
                            discountType = "2";
                            double result = Double.valueOf(moeny)
                                    - Double.valueOf(CouponMoney);
                            Log.i("MakeOrderActivity", "选择代金券了，代金券金额"
                                    + String.valueOf(CouponMoney) + "总金额为" + String.valueOf(result));
                            txt_allmoney.setText(String.valueOf(result));
                            goods_smallallmoeny.setText("￥" + String.valueOf(result));
                        }
                    }
                } else if (resultCode == RESULT_CANCELED) {
                    if (data == null) //正常返回按钮和标题栏的返回
                    {
                    } else { //不使用优惠券
                        tv_makeorder_showcoupons.setText("请选择优惠券");
                        rl_makerorder_jifeng.setClickable(true);
                        discountType = "0";
                        txt_allmoney.setText(moeny);
                        goods_smallallmoeny.setText("￥" + moeny);
                    }
                } else {
                }

                break;
            default:
                break;
        }
    }

    private void getIntergralAndFreight() {
        rl_makerorder_jifeng.setClickable(false);
        ck_makerorder_jifeng.setClickable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url + "/api/Order/ReceiveGetIntegralInfoByMemberId", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                makerOrderIntergralBean = gson.fromJson(response, MakerOrderIntergralBean.class);
                if (makerOrderIntergralBean.isIsSuccess()) {
                    if (makerOrderIntergralBean.getResult().getIsUserIntegral() > 0) {
                        rl_makerorder_jifeng.setClickable(true);
                        tv_makerorder_IntegralCount.setText(makerOrderIntergralBean.getResult()
                                .getIntegralCount() + "");
                        tv_makerorder_IntegralPrice.setText("￥" + makerOrderIntergralBean.getResult()
                                .getIntegralPrice());
                    } else {
                        ll_makerorder_jifeng.setVisibility(View.GONE);
                        tv_makerorder_isuserintegral.setText("当前积分不可用");
                        rl_makerorder_jifeng.setClickable(false);
                    }
                    order_yunfei.setText("￥" + makerOrderIntergralBean.getResult().getExpressPrice());
                    moeny = String.valueOf(Double.valueOf(moeny)
                            + makerOrderIntergralBean.getResult().getExpressPrice());
                    goods_smallallmoeny.setText("￥" + moeny);
                    txt_allmoney.setText(moeny);
                    if (makerOrderIntergralBean.getResult().getDescription() == null ||
                            makerOrderIntergralBean.getResult().getDescription() == "") {
                        order_Line_tishi.setVisibility(View.GONE);
                    } else {
                        order_Line_tishi.setVisibility(View.VISIBLE);
                        tv_order_tishi.setText(makerOrderIntergralBean.getResult().getDescription().toString());
                    }
                } else {
                    rl_makerorder_jifeng.setClickable(false);
                    rl_makeorder_usecoupons.setClickable(false);
                    Toast.makeText(MakeOrderActivity.this, "积分和运费获取失败，请重试", Toast.LENGTH_SHORT).show();
                }
                Log.i("info", "1233333");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                rl_makerorder_jifeng.setClickable(false);
                ck_makerorder_jifeng.setClickable(false);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", MemberId);
                map.put("OrderType", payfrom);
                map.put("TotalPrice", moeny);
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    /**
     * 加载下单时提示用户多少免邮
     *
     * @param freightBase  多少免邮
     * @param currentMoney 当前总价(不包含优惠券和积分)
     */
    private void initTiShi(double freightBase, double currentMoney) {
        if (freightBase > currentMoney) {
            String surplus = String.valueOf(freightBase - currentMoney);
            order_Line_tishi.setVisibility(View.VISIBLE);
            tv_order_tishi.setText("亲，购满" + String.valueOf(freightBase) + "元，可减免邮费哦！您现在已购"
                    + String.valueOf(currentMoney) + "元，还差" + surplus + "元，可以再去逛逛哟");
        } else {
            order_Line_tishi.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFirst) {
            isFirst = false;
        } else {
            if (payfrom.equals("7")) {
                return;
            }
            moeny = oldmoeny;
            getIntergralAndFreight();
        }
    }
}
