package com.aboluo.model;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by cj34920 on 2016/10/11.
 */

public class PickerViewData implements IPickerViewData{
    private String content;

    public PickerViewData(String content) {
        this.content = content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getPickerViewText() {
        return content;
    }
}
