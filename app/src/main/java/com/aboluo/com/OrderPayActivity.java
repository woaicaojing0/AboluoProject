package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.BillUtils;
import com.aboluo.XUtils.CommonUtils;

import java.util.HashMap;
import java.util.Map;

import cn.beecloud.BCCache;
import cn.beecloud.BCPay;
import cn.beecloud.async.BCCallback;
import cn.beecloud.async.BCResult;
import cn.beecloud.entity.BCPayResult;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by CJ on 2016/11/20.
 */

public class OrderPayActivity extends Activity implements View.OnClickListener {
    private Button sure_pay;
    private String toastMsg;
    private SweetAlertDialog pdialog;
    private static final String TAG = "woaicaojingpay";
    //记录一下是否是PayPal支付
    private boolean isPayPal;
    private int  paymoney ;
    private String OrderNum;
    private int requsetcode = 1;
    private TextView txt_lastpaymoeny;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay);
        init();
        sure_pay.setOnClickListener(this);
    }

    private void init() {
        sure_pay = (Button) findViewById(R.id.sure_pay);
        txt_lastpaymoeny = (TextView) findViewById(R.id.txt_lastpaymoeny);
        pdialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pdialog.setTitleText("处理中，请稍候...");
        pdialog.setCanceledOnTouchOutside(true);
        pdialog.setCancelable(true);
        Intent intent = getIntent();
        String yuanmoney = intent.getStringExtra("payMoney");
        OrderNum = intent.getStringExtra("OrderNum");
        paymoney =Integer.valueOf(CommonUtils.yuanToFen(yuanmoney));
        txt_lastpaymoeny.setText("￥"+String.valueOf(yuanmoney));
        Log.i("woaicaojingpay","实际支付的金额为"+paymoney);
    }


    // Defines a Handler object that's attached to the UI thread.
    // 通过Handler.Callback()可消除内存泄漏警告
    private Handler mHandler = new Handler(new Handler.Callback() {
        /**
         * Callback interface you can use when instantiating a Handler to avoid
         * having to implement your own subclass of Handler.
         *
         * handleMessage() defines the operations to perform when
         * the Handler receives a new Message to process.
         */
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    Intent intent = new Intent();
                    intent.putExtra("back","OrderPay");
                    OrderPayActivity.this.setResult(requsetcode,intent);
                    Toast.makeText(OrderPayActivity.this, toastMsg, Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }
            return true;
        }
    });

    //支付结果返回入口
    BCCallback bcCallback = new BCCallback() {
        @Override
        public void done(final BCResult bcResult) {
            final BCPayResult bcPayResult = (BCPayResult) bcResult;
            //此处关闭loading界面
            pdialog.dismiss();

            //根据你自己的需求处理支付结果
            String result = bcPayResult.getResult();
            /*
              注意！
              所有支付渠道建议以服务端的状态金额为准，此处返回的RESULT_SUCCESS仅仅代表手机端支付成功
            */
            Message msg = mHandler.obtainMessage();
            //单纯的显示支付结果
            msg.what = 2;
            if (result.equals(BCPayResult.RESULT_SUCCESS)) {
                toastMsg = "用户支付成功";

                //如果是PayPal，手机端支付完成后还需要向BeeCloud服务器发送同步请求，并校验支付结果
                if (isPayPal) {
                    //如果是PayPal，detail info里面包含订单的json字符串
                    final String syncStr = bcPayResult.getDetailInfo();
                    isPayPal = false;
                    Log.w(TAG, "start to sync PayPal result to BeeCloud server...");

                    pdialog.show();
                    //由于同步过程中需要向PayPal服务器请求token，请求失败的几率比较高，此处设置了三次循环
                    BCCache.executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            int i = 0;
                            BCPayResult syncResult;
                            for (; i < 3; i++) {
                                Log.w(TAG, String.format("sync for %d time(s)", i + 1));
                                syncResult = BCPay.getInstance(OrderPayActivity.this).syncPayPalPayment(syncStr);

                                if (syncResult.getResult().equals(BCPayResult.RESULT_SUCCESS)) {
                                    Log.w(TAG, "sync succ!!!");
                                    Log.w(TAG, "this bill id can be stored for query by id: " + syncResult.getId());
                                    break;
                                } else {
                                    Log.e(TAG, "sync fail reason: " + syncResult.getErrCode() + " # " +
                                            syncResult.getErrMsg() + " # " + syncResult.getDetailInfo());
                                }
                            }

                            pdialog.dismiss();

                            //注意，如果一直失败，你需要将该json串保留起来，下次继续同步，否者在你在BeeCloud控制台看不到这笔订单
                            if (i == 3) {
                                Log.e(TAG, "BAD result!!! Sync failed for three times!!!");
                                Log.w(TAG, "please store the json string to somewhere for later sync: " + syncStr);
                            }
                        }
                    });
                }
            } else if (result.equals(BCPayResult.RESULT_CANCEL))
                toastMsg = "用户取消支付";
            else if (result.equals(BCPayResult.RESULT_FAIL)) {
                toastMsg = "支付失败, 原因: " + bcPayResult.getErrCode() +
                        " # " + bcPayResult.getErrMsg() +
                        " # " + bcPayResult.getDetailInfo();

                /**
                 * 你发布的项目中不应该出现如下错误，此处由于支付宝政策原因，
                 * 不再提供支付宝支付的测试功能，所以给出提示说明
                 */
                if (bcPayResult.getErrMsg().equals("PAY_FACTOR_NOT_SET") &&
                        bcPayResult.getDetailInfo().startsWith("支付宝参数")) {
                    toastMsg = "支付失败：由于支付宝政策原因，故不再提供支付宝支付的测试功能，给您带来的不便，敬请谅解";
                }

                /**
                 * 以下是正常流程，请按需处理失败信息
                 */
                Log.e(TAG, toastMsg);

                if (bcPayResult.getErrMsg().equals(BCPayResult.FAIL_PLUGIN_NOT_INSTALLED)) {
                    //银联需要重新安装控件
                    msg.what = 1;
                }

            } else if (result.equals(BCPayResult.RESULT_UNKNOWN)) {
                //可能出现在支付宝8000返回状态
                toastMsg = "订单状态未知";
            } else {
                toastMsg = "invalid return";
            }

            mHandler.sendMessage(msg);


            if (bcPayResult.getId() != null) {
                //你可以把这个id存到你的订单中，下次直接通过这个id查询订单
                Log.w(TAG, "bill id retrieved : " + bcPayResult.getId());
//                //根据ID查询，此处只是演示如何通过id查询订单，并非支付必要部分
//                getBillInfoByID(bcPayResult.getId());
            }

        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sure_pay:
                Map<String, String> mapOptional = new HashMap<String, String>();
                mapOptional = new HashMap<String, String>();
                mapOptional.put("客户端", "安卓");
                mapOptional.put("consumptioncode", "consumptionCode");
                mapOptional.put("money", "2");

                BCPay.getInstance(OrderPayActivity.this).reqAliPaymentAsync(
                        "阿波罗商铺",
                        paymoney,
                        OrderNum,
                        mapOptional,
                        bcCallback);
                break;
        }
    }
}