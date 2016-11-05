package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.XUtils.ValidateUtils;
import com.aboluo.com.address.ProvinceAddressActivity;
import com.aboluo.com.address.StreetAddressActivity;
import com.aboluo.model.BaseModel;
import com.aboluo.model.PickerViewData;
import com.aboluo.model.ProvinceBean22;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TooManyListenersException;

/**
 * Created by CJ on 2016/10/4.
 */

public class AddAddressActivity extends Activity implements TextWatcher, View.OnClickListener {
    private Button add_address_save;
    private EditText edit_receive_name, edit_receive_phone, edit_receive_address, edit_receive_zipcode;
    private OptionsPickerView pvOptions;
    private RelativeLayout linelayout_location, address_street; //选择收货地址,街道
    private ArrayList<ProvinceBean22> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<IPickerViewData>>> options3Items = new ArrayList<>();
    //显示联动的地址,选择的地址,选择街道
    private TextView add_address_txtview_location, add_address_txtview_jiedao;
    private int address_id = 0, streetid = 0; //选择地址结束后，选择街道需要的id，街道的id
    private String address_name = null, streetname = null, allid = null; //选择地址结束后，地址名称，街道名称
    private TextView txt_allid, txt_region_id, txt_street_id; //都是影藏的 保存数据用的
    View vMasker;
    private RequestQueue requestQueue;
    private String url = null;
    private String APPToken = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_add_address);
        processExtraData();//从选择地址跳转过来的数据
        init();
        add_address_save.setOnClickListener(this);
        edit_receive_name.addTextChangedListener(this);
        edit_receive_phone.addTextChangedListener(this);
        edit_receive_address.addTextChangedListener(this);
        edit_receive_zipcode.addTextChangedListener(this);
        linelayout_location.setOnClickListener(this);
        address_street.setOnClickListener(this);
        initaddress();
    }

    private void init() {
        add_address_save = (Button) findViewById(R.id.add_address_save);
        edit_receive_name = (EditText) findViewById(R.id.edit_receive_name);
        edit_receive_phone = (EditText) findViewById(R.id.edit_receive_phone);
        edit_receive_address = (EditText) findViewById(R.id.edit_receive_address);
        edit_receive_zipcode = (EditText) findViewById(R.id.edit_receive_zipcode);
        linelayout_location = (RelativeLayout) findViewById(R.id.linelayout_location);
        address_street = (RelativeLayout) findViewById(R.id.address_street);
        add_address_txtview_location = (TextView) findViewById(R.id.add_address_txtview_location);
        add_address_txtview_jiedao = (TextView) findViewById(R.id.add_address_txtview_jiedao);
        txt_street_id = (TextView) findViewById(R.id.txt_street_id);
        txt_allid = (TextView) findViewById(R.id.txt_allid);
        txt_region_id = (TextView) findViewById(R.id.txt_region_id);
        if (address_name == null) {
        } else {
            add_address_txtview_location.setText(address_name);
        }
        pvOptions = new OptionsPickerView(this);
        vMasker = findViewById(R.id.vMasker);
        url = CommonUtils.GetValueByKey(AddAddressActivity.this, "apiurl");
        APPToken = CommonUtils.GetValueByKey(AddAddressActivity.this, "APPToken");
        requestQueue = MyApplication.getRequestQueue();
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

    private void initaddress() {
        //选项1
        options1Items.add(new ProvinceBean22(0, "广东", "广东省，以岭南东道、广南东路得名", "其他数据"));
        options1Items.add(new ProvinceBean22(1, "湖南", "湖南省地处中国中部、长江中游，因大部分区域处于洞庭湖以南而得名湖南", "芒果TV"));
        options1Items.add(new ProvinceBean22(3, "广西", "嗯～～", ""));

        //选项2
        ArrayList<String> options2Items_01 = new ArrayList<>();
        options2Items_01.add("广州");
        options2Items_01.add("佛山");
        options2Items_01.add("东莞");
        options2Items_01.add("阳江");
        options2Items_01.add("珠海");
        ArrayList<String> options2Items_02 = new ArrayList<>();
        options2Items_02.add("长沙");
        options2Items_02.add("岳阳");
        ArrayList<String> options2Items_03 = new ArrayList<>();
        options2Items_03.add("桂林");
        options2Items.add(options2Items_01);
        options2Items.add(options2Items_02);
        options2Items.add(options2Items_03);

        //选项3
        ArrayList<ArrayList<IPickerViewData>> options3Items_01 = new ArrayList<>();
        ArrayList<ArrayList<IPickerViewData>> options3Items_02 = new ArrayList<>();
        ArrayList<ArrayList<IPickerViewData>> options3Items_03 = new ArrayList<>();
        ArrayList<IPickerViewData> options3Items_01_01 = new ArrayList<>();
        options3Items_01_01.add(new PickerViewData("天河"));
        options3Items_01_01.add(new PickerViewData("黄埔"));
        options3Items_01_01.add(new PickerViewData("海珠"));
        options3Items_01_01.add(new PickerViewData("越秀"));
        options3Items_01.add(options3Items_01_01);
        ArrayList<IPickerViewData> options3Items_01_02 = new ArrayList<>();
        options3Items_01_02.add(new PickerViewData("南海"));
        options3Items_01_02.add(new PickerViewData("高明"));
        options3Items_01_02.add(new PickerViewData("禅城"));
        options3Items_01_02.add(new PickerViewData("桂城"));
        options3Items_01.add(options3Items_01_02);
        ArrayList<IPickerViewData> options3Items_01_03 = new ArrayList<>();
        options3Items_01_03.add(new PickerViewData("其他"));
        options3Items_01_03.add(new PickerViewData("常平"));
        options3Items_01_03.add(new PickerViewData("虎门"));
        options3Items_01.add(options3Items_01_03);
        ArrayList<IPickerViewData> options3Items_01_04 = new ArrayList<>();
        options3Items_01_04.add(new PickerViewData("其他"));
        options3Items_01_04.add(new PickerViewData("其他"));
        options3Items_01_04.add(new PickerViewData("其他"));
        options3Items_01.add(options3Items_01_04);
        ArrayList<IPickerViewData> options3Items_01_05 = new ArrayList<>();

        options3Items_01_05.add(new PickerViewData("其他1"));
        options3Items_01_05.add(new PickerViewData("其他2"));
        options3Items_01.add(options3Items_01_05);

        ArrayList<IPickerViewData> options3Items_02_01 = new ArrayList<>();

        options3Items_02_01.add(new PickerViewData("长沙1"));
        options3Items_02_01.add(new PickerViewData("长沙2"));
        options3Items_02_01.add(new PickerViewData("长沙3"));
        options3Items_02_01.add(new PickerViewData("长沙4"));
        options3Items_02_01.add(new PickerViewData("长沙5"));


        options3Items_02.add(options3Items_02_01);
        ArrayList<IPickerViewData> options3Items_02_02 = new ArrayList<>();

        options3Items_02_02.add(new PickerViewData("岳阳"));
        options3Items_02_02.add(new PickerViewData("岳阳1"));
        options3Items_02_02.add(new PickerViewData("岳阳2"));
        options3Items_02_02.add(new PickerViewData("岳阳3"));
        options3Items_02_02.add(new PickerViewData("岳阳4"));
        options3Items_02_02.add(new PickerViewData("岳阳5"));

        options3Items_02.add(options3Items_02_02);
        ArrayList<IPickerViewData> options3Items_03_01 = new ArrayList<>();
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
                String tx = options1Items.get(options1).getPickerViewText() + "省 "
                        + options2Items.get(options1).get(option2) + "市 "
                        + options3Items.get(options1).get(option2).get(options3).getPickerViewText() + "区";
                add_address_txtview_location.setText(tx);
                vMasker.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);//must store the new intent unless getIntent() will return the old one
        processExtraData();

    }

    private void processExtraData() {
        Intent intent = getIntent();
        address_name = intent.getStringExtra("name");
        address_id = intent.getIntExtra("id", 0);
        streetid = intent.getIntExtra("streetid", 0);
        streetname = intent.getStringExtra("streetname");
        allid = intent.getStringExtra("allid");
        //use the data received here
        if (address_name == null) {
        } else {
            add_address_txtview_location.setText(address_name);
        }
        if (address_id == 0) {
        } else {
            txt_region_id.setText(String.valueOf(address_id));
        }

        if (allid != null) {
            txt_allid.setText(allid);
//            String id[] = allid.split(";");
//            int iddss = Integer.parseInt(id[1]);
        }
        if (streetname != null) {
            add_address_txtview_jiedao.setText(streetname);
        }
        if(streetid !=0)
        {
            txt_street_id.setText(String.valueOf(streetid));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.address_street:
                String regionid = txt_region_id.getText().toString();
                if (regionid.equals("0") || regionid.equals("")) {
                    Toast.makeText(this, "请先选择地址", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(AddAddressActivity.this, StreetAddressActivity.class);
                    intent.putExtra("regionid", Integer.valueOf(regionid));
                    startActivity(intent);
                }
                break;
            case R.id.linelayout_location:
                Intent intent1 = new Intent(AddAddressActivity.this, ProvinceAddressActivity.class);
                startActivity(intent1);
                break;
            case R.id.add_address_save:
                saveAddress();
                break;
        }
    }

    private void saveAddress() {
        final String name = add_address_txtview_location.getText().toString();
        final String allsid = txt_allid.getText().toString();
        final String streename = add_address_txtview_jiedao.getText().toString();
        final String streeId = txt_street_id.getText().toString();
        final String allids[] = allsid.split(";");
        final String allname[] = name.split(" ");
        final String address = edit_receive_address.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "/api/Order/AddOrUpdateMemeberAddress", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                BaseModel basemodel = new BaseModel();
                if(basemodel.isIsSuccess())
                {
                    Toast.makeText(AddAddressActivity.this, basemodel.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddAddressActivity.this,AddressActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(AddAddressActivity.this, basemodel.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Id", "0");
                map.put("MemberId", "1");
                map.put("Receiver", "曹晶");
                map.put("Mobile", "18360733212");
                map.put("Province", allname[0]);
                map.put("ProvinceId", allids[0]);
                map.put("City", allname[1]);
                map.put("CityId", allids[1]);
                map.put("Region", allname[2]);
                map.put("RegionId", allids[2]);
                map.put("Street", streename);
                map.put("StreetId", String.valueOf(streeId));
                map.put("Address", address);
                map.put("IsDefault", "0");
                map.put("ZipCode", "225500");
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
