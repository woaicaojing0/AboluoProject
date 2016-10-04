package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.aboluo.adapter.AddressAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJ on 2016/10/4.
 */

public class AddressActivity extends Activity {
    private ListView address_listview;
    private List<String> list;
    private LinearLayout shopcar_bottom_add_address;
    private RelativeLayout no_address_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        init();
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i + "");
        }
        List<String> list2 = new ArrayList<>();
        if(list2.size() == 0)
        {
            no_address_show.setVisibility(View.VISIBLE);
        }
        else {
            no_address_show.setVisibility(View.GONE);
            AddressAdapter addressAdapter = new AddressAdapter(list2, AddressActivity.this);
            address_listview.setAdapter(addressAdapter);
        }
        shopcar_bottom_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressActivity.this,AddAddressActivity.class);
                startActivity(intent);
            }
        });


    }

    private void init() {
        address_listview = (ListView) findViewById(R.id.address_listview);
        shopcar_bottom_add_address = (LinearLayout) findViewById(R.id.shopcar_bottom_add_address);
        no_address_show = (RelativeLayout) findViewById(R.id.no_address_show);
    }
}
