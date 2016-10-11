package com.aboluo.com;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.ValidateUtils;
import com.aboluo.model.PickerViewData;
import com.aboluo.model.ProvinceBean;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.ArrayList;

/**
 * Created by CJ on 2016/10/4.
 */

public class AddAddressActivity extends Activity implements TextWatcher {
    private Button add_address_save;
    private EditText edit_receive_name, edit_receive_phone, edit_receive_address, edit_receive_zipcode;
    private OptionsPickerView pvOptions;
    private LinearLayout linelayout_location; //选择收货地址
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<IPickerViewData>>> options3Items = new ArrayList<>();
    private TextView add_address_txtview_location;  //显示联动的地址
    View vMasker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_add_address);
        init();
        add_address_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddAddressActivity.this, "你点击我", Toast.LENGTH_SHORT).show();
            }
        });
        edit_receive_name.addTextChangedListener(this);
        edit_receive_phone.addTextChangedListener(this);
        edit_receive_address.addTextChangedListener(this);
        edit_receive_zipcode.addTextChangedListener(this);
        linelayout_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pvOptions.show();
            }
        });
        initaddress();
    }

    private void init() {
        add_address_save = (Button) findViewById(R.id.add_address_save);
        edit_receive_name = (EditText) findViewById(R.id.edit_receive_name);
        edit_receive_phone = (EditText) findViewById(R.id.edit_receive_phone);
        edit_receive_address = (EditText) findViewById(R.id.edit_receive_address);
        edit_receive_zipcode = (EditText) findViewById(R.id.edit_receive_zipcode);
        linelayout_location = (LinearLayout) findViewById(R.id.linelayout_location);
        add_address_txtview_location = (TextView) findViewById(R.id.add_address_txtview_location);
        pvOptions = new OptionsPickerView(this);
        vMasker=findViewById(R.id.vMasker);

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        boolean isok = false;
    }

    @Override
    public void afterTextChanged(Editable editable) {
        boolean isok = false;
        if (ValidateUtils.isMobileNO(edit_receive_phone.getText().toString())) {
            isok = true;
            if (ValidateUtils.Zipcode(edit_receive_zipcode.getText().toString())) {
                isok = true;
                if (edit_receive_name.length() > 2) {
                    isok = true;
                    if (edit_receive_address.length() > 0) {
                        isok = true;
                    } else {
                        isok = false;
                    }
                } else {
                    isok = false;
                }
            } else {
                isok = false;
            }
        } else {
            isok = false;
        }
        if (isok) {
            add_address_save.setEnabled(true);
        } else {
            add_address_save.setEnabled(false);
        }
    }
    private  void initaddress()
    {
        //选项1
        options1Items.add(new ProvinceBean(0,"广东","广东省，以岭南东道、广南东路得名","其他数据"));
        options1Items.add(new ProvinceBean(1,"湖南","湖南省地处中国中部、长江中游，因大部分区域处于洞庭湖以南而得名湖南","芒果TV"));
        options1Items.add(new ProvinceBean(3,"广西","嗯～～",""));

        //选项2
        ArrayList<String> options2Items_01=new ArrayList<>();
        options2Items_01.add("广州");
        options2Items_01.add("佛山");
        options2Items_01.add("东莞");
        options2Items_01.add("阳江");
        options2Items_01.add("珠海");
        ArrayList<String> options2Items_02=new ArrayList<>();
        options2Items_02.add("长沙");
        options2Items_02.add("岳阳");
        ArrayList<String> options2Items_03=new ArrayList<>();
        options2Items_03.add("桂林");
        options2Items.add(options2Items_01);
        options2Items.add(options2Items_02);
        options2Items.add(options2Items_03);

        //选项3
        ArrayList<ArrayList<IPickerViewData>> options3Items_01 = new ArrayList<>();
        ArrayList<ArrayList<IPickerViewData>> options3Items_02 = new ArrayList<>();
        ArrayList<ArrayList<IPickerViewData>> options3Items_03 = new ArrayList<>();
        ArrayList<IPickerViewData> options3Items_01_01=new ArrayList<>();
        options3Items_01_01.add(new PickerViewData("天河"));
        options3Items_01_01.add(new PickerViewData("黄埔"));
        options3Items_01_01.add(new PickerViewData("海珠"));
        options3Items_01_01.add(new PickerViewData("越秀"));
        options3Items_01.add(options3Items_01_01);
        ArrayList<IPickerViewData> options3Items_01_02=new ArrayList<>();
        options3Items_01_02.add(new PickerViewData("南海"));
        options3Items_01_02.add(new PickerViewData("高明"));
        options3Items_01_02.add(new PickerViewData("禅城"));
        options3Items_01_02.add(new PickerViewData("桂城"));
        options3Items_01.add(options3Items_01_02);
        ArrayList<IPickerViewData> options3Items_01_03=new ArrayList<>();
        options3Items_01_03.add(new PickerViewData("其他"));
        options3Items_01_03.add(new PickerViewData("常平"));
        options3Items_01_03.add(new PickerViewData("虎门"));
        options3Items_01.add(options3Items_01_03);
        ArrayList<IPickerViewData> options3Items_01_04=new ArrayList<>();
        options3Items_01_04.add(new PickerViewData("其他"));
        options3Items_01_04.add(new PickerViewData("其他"));
        options3Items_01_04.add(new PickerViewData("其他"));
        options3Items_01.add(options3Items_01_04);
        ArrayList<IPickerViewData> options3Items_01_05=new ArrayList<>();

        options3Items_01_05.add(new PickerViewData("其他1"));
        options3Items_01_05.add(new PickerViewData("其他2"));
        options3Items_01.add(options3Items_01_05);

        ArrayList<IPickerViewData> options3Items_02_01=new ArrayList<>();

        options3Items_02_01.add(new PickerViewData("长沙1"));
        options3Items_02_01.add(new PickerViewData("长沙2"));
        options3Items_02_01.add(new PickerViewData("长沙3"));
        options3Items_02_01.add(new PickerViewData("长沙4"));
        options3Items_02_01.add(new PickerViewData("长沙5"));




        options3Items_02.add(options3Items_02_01);
        ArrayList<IPickerViewData> options3Items_02_02=new ArrayList<>();

        options3Items_02_02.add(new PickerViewData("岳阳"));
        options3Items_02_02.add(new PickerViewData("岳阳1"));
        options3Items_02_02.add(new PickerViewData("岳阳2"));
        options3Items_02_02.add(new PickerViewData("岳阳3"));
        options3Items_02_02.add(new PickerViewData("岳阳4"));
        options3Items_02_02.add(new PickerViewData("岳阳5"));

        options3Items_02.add(options3Items_02_02);
        ArrayList<IPickerViewData> options3Items_03_01=new ArrayList<>();
        options3Items_03_01.add(new PickerViewData("好山水"));
        options3Items_03.add(options3Items_03_01);

        options3Items.add(options3Items_01);
        options3Items.add(options3Items_02);
        options3Items.add(options3Items_03);

        //三级联动效果
        pvOptions.setPicker(options1Items, options2Items, options3Items, true);
        //设置选择的三级单位
        pvOptions.setLabels("省", "市", "区");
        pvOptions.setTitle("选择城市");
        pvOptions.setCyclic(false, true, true);
        //设置默认选中的三级项目
        //监听确定选择按钮
        pvOptions.setSelectOptions(1, 1, 1);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()+"省 "
                        + options2Items.get(options1).get(option2)+"市 "
                        + options3Items.get(options1).get(option2).get(options3).getPickerViewText()+"区";
                add_address_txtview_location.setText(tx);
                vMasker.setVisibility(View.GONE);
            }
        });
    }
}
