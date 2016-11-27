package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.XUtils.ValidateUtils;
import com.aboluo.com.address.ProvinceAddressActivity;
import com.aboluo.com.address.StreetAddressActivity;
import com.aboluo.model.AddressInfoBean;
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
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TooManyListenersException;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by CJ on 2016/10/4.
 */

public class AddAddressActivity extends Activity implements TextWatcher, View.OnClickListener {
    private Button add_address_save,update_address_save;//新增按钮，更新按钮
    private EditText edit_receive_name, edit_receive_phone, edit_receive_address, edit_receive_zipcode;
    private OptionsPickerView pvOptions;
    private RelativeLayout linelayout_location, address_street; //选择收货地址,街道
    private ArrayList<ProvinceBean22> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<IPickerViewData>>> options3Items = new ArrayList<>();
    //显示联动的地址,选择的地址,选择街道,头部表示修改还是新增默认是新增
    private TextView add_address_txtview_location, add_address_txtview_jiedao,toolbar_txt;
    private int address_id = 0, streetid = 0; //选择地址结束后，选择街道需要的id，街道的id
    private String address_name = null, streetname = null, allid = null; //选择地址结束后，地址名称，街道名称
    private TextView txt_allid, txt_region_id, txt_street_id; //都是影藏的 保存数据用的
    View vMasker;
    private RequestQueue requestQueue;
    private String url = null;
    private String APPToken = null;
    private SweetAlertDialog sweetAlertDialog;
    private int model=0;// 默认是保存模式，1代表是编辑模式
    private AddressInfoBean.ResultBean.MemberAddressListBean  memberAddressListBean;
    private Gson gson;
    private Switch swtich_isdefult;
    private String MemberId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_add_address);
        processExtraData();//从选择地址跳转过来的数据
        MemberId = CommonUtils.GetMemberId(this);
        init();
        add_address_save.setOnClickListener(this);
        update_address_save.setOnClickListener(this);
        edit_receive_name.addTextChangedListener(this);
        edit_receive_phone.addTextChangedListener(this);
        edit_receive_address.addTextChangedListener(this);
        edit_receive_zipcode.addTextChangedListener(this);
        linelayout_location.setOnClickListener(this);
        address_street.setOnClickListener(this);
    }

    private void init() {
        add_address_save = (Button) findViewById(R.id.add_address_save);
        update_address_save = (Button) findViewById(R.id.update_address_save);
        edit_receive_name = (EditText) findViewById(R.id.edit_receive_name);
        edit_receive_phone = (EditText) findViewById(R.id.edit_receive_phone);
        edit_receive_address = (EditText) findViewById(R.id.edit_receive_address);
        edit_receive_zipcode = (EditText) findViewById(R.id.edit_receive_zipcode);
        linelayout_location = (RelativeLayout) findViewById(R.id.linelayout_location);
        address_street = (RelativeLayout) findViewById(R.id.address_street);
        toolbar_txt = (TextView) findViewById(R.id.toolbar_txt);
        add_address_txtview_location = (TextView) findViewById(R.id.add_address_txtview_location);
        add_address_txtview_jiedao = (TextView) findViewById(R.id.add_address_txtview_jiedao);
        txt_street_id = (TextView) findViewById(R.id.txt_street_id);
        txt_allid = (TextView) findViewById(R.id.txt_allid);
        txt_region_id = (TextView) findViewById(R.id.txt_region_id);
        swtich_isdefult = (Switch) findViewById(R.id.swtich_isdefult);
        if (address_name == null) {
        } else {
            add_address_txtview_location.setText(address_name);
        }
        pvOptions = new OptionsPickerView(this);
        vMasker = findViewById(R.id.vMasker);
        url = CommonUtils.GetValueByKey(AddAddressActivity.this, "apiurl");
        APPToken = CommonUtils.GetValueByKey(AddAddressActivity.this, "APPToken");
        requestQueue = MyApplication.getRequestQueue();
        sweetAlertDialog= new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        sweetAlertDialog.setCancelable(false);
        Intent intent = getIntent();
       model = intent.getIntExtra("model",0);
        gson = new Gson();
        initdata(model);
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
                if (edit_receive_name.length() >=2) {
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
            update_address_save.setEnabled(true);
        } else {
            add_address_save.setEnabled(false);
            update_address_save.setEnabled(false);
        }
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
            case R.id.update_address_save:
                updateAddress();
        }
    }

    private void saveAddress() {
        final String Receiver = edit_receive_name.getText().toString();
        final String Mobile =edit_receive_phone.getText().toString();
        final String ZipCode =edit_receive_zipcode.getText().toString();
        final String name = add_address_txtview_location.getText().toString();
        final String allsid = txt_allid.getText().toString();
        final String streename = add_address_txtview_jiedao.getText().toString();
        final String streeId = txt_street_id.getText().toString();
        final String allids[] = allsid.split(";");
        final String allname[] = name.split(" ");
        final String address = edit_receive_address.getText().toString();
        String IsDefault="0";
        if(swtich_isdefult.isChecked())
        {
            IsDefault = "1";
        }else{}
        sweetAlertDialog.setTitleText("新增中......");
        sweetAlertDialog.show();
        final String finalIsDefault = IsDefault;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "/api/Order/AddOrUpdateMemeberAddress", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                BaseModel basemodel = new BaseModel();
                basemodel =gson.fromJson(response,BaseModel.class);
                if(basemodel.isIsSuccess())
                {
                    Toast.makeText(AddAddressActivity.this, basemodel.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddAddressActivity.this,AddressActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(AddAddressActivity.this, basemodel.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
                sweetAlertDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                byte[] htmlBodyBytes = error.networkResponse.data;
                Toast.makeText(AddAddressActivity.this,  error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("woaiocaojingerroe",new String(htmlBodyBytes));
                sweetAlertDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Id", "0");
                map.put("MemberId",MemberId);
                map.put("Receiver", Receiver);
                map.put("Mobile", Mobile);
                map.put("Province", allname[0]);
                map.put("ProvinceId", allids[0]);
                map.put("City", allname[1]);
                map.put("CityId", allids[1]);
                map.put("Region", allname[2]);
                map.put("RegionId", allids[2]);
                map.put("Street", streename);
                map.put("StreetId", String.valueOf(streeId));
                map.put("Address", address);
                map.put("IsDefault", finalIsDefault);
                map.put("ZipCode", ZipCode);
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void updateAddress(){
        final String Receiver = edit_receive_name.getText().toString();
        final String Mobile =edit_receive_phone.getText().toString();
        final String ZipCode =edit_receive_zipcode.getText().toString();
        final String name = add_address_txtview_location.getText().toString();
        final String allsid = txt_allid.getText().toString();
        final String streename = add_address_txtview_jiedao.getText().toString();
        final String streeId = txt_street_id.getText().toString();
        final String allids[] = allsid.split(";");
        final String allname[] = name.split(" ");
        final String address = edit_receive_address.getText().toString();
        sweetAlertDialog.setTitleText("修改中......");
        sweetAlertDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "/api/Order/AddOrUpdateMemeberAddress", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                BaseModel basemodel = new BaseModel();
                basemodel =gson.fromJson(response,BaseModel.class);
                if(basemodel.isIsSuccess())
                {
                    Toast.makeText(AddAddressActivity.this, basemodel.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddAddressActivity.this,AddressActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(AddAddressActivity.this, basemodel.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
                sweetAlertDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddAddressActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                sweetAlertDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Id", String.valueOf(model));
                map.put("MemberId", MemberId);
                map.put("Receiver", Receiver);
                map.put("Mobile", Mobile);
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
                map.put("ZipCode", ZipCode);
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);

    }
    private void initdata(final int model)
    {

        if(model != 0) {
            add_address_save.setVisibility(View.GONE);
            update_address_save.setVisibility(View.VISIBLE);
            toolbar_txt.setText("修改收货地址");
            sweetAlertDialog.setTitleText("加载中......");
            sweetAlertDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url+"/api/Order/GetMemberAddressListByMemberId", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    response = response.replace("\\","");
                    response =response.substring(1,response.length()-1);
                    Log.i("woaicaojingone",response);
                    AddressInfoBean addressInfoBean2= gson.fromJson(response, AddressInfoBean.class);
                    memberAddressListBean = addressInfoBean2.getResult().getMemberAddressList().get(0);
                    edit_receive_phone.setText(memberAddressListBean.getMobile());
                    edit_receive_address.setText(memberAddressListBean.getAddress());
                    edit_receive_name.setText(memberAddressListBean.getReceiver());
                    edit_receive_zipcode.setText(memberAddressListBean.getZipCode());
                    add_address_txtview_location.setText(memberAddressListBean.getProvince()+" "+
                    memberAddressListBean.getCity()+" "+memberAddressListBean.getRegion()+" ");
                    add_address_txtview_jiedao.setText(memberAddressListBean.getStreet());
                    txt_street_id.setText(String.valueOf(memberAddressListBean.getStreetId()));
                    txt_allid.setText(memberAddressListBean.getProvinceId()+";"+
                    memberAddressListBean.getCityId()+";"+memberAddressListBean.getRegionId());
                    txt_region_id.setText(String.valueOf(memberAddressListBean.getRegionId()));
                    sweetAlertDialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AddAddressActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    sweetAlertDialog.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                   Map<String,String> map = new HashMap<>();
                    map.put("Id",String.valueOf(model));
                    map.put("MemberId",MemberId);
                    map.put("APPToken",APPToken);
                    return  map;
                }
            };
            requestQueue.add(stringRequest);
        }else {
            update_address_save.setVisibility(View.GONE);
            add_address_save.setVisibility(View.VISIBLE);}
    }
}
