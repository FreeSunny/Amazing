package com.demo.example.model;

import com.demo.example.adpter.EnterDelegate;

/**
 * Created by hzsunyj on 2017/10/17.
 */

public class EnterData extends ItemData {

    public static final int DEFAULT_TAG = 0;

    public Class clazz;

    public EnterData(String desc, Class clazz) {
        super(DEFAULT_TAG, EnterDelegate.TEXT_HOLDER, desc);
        this.clazz = clazz;
    }
}
