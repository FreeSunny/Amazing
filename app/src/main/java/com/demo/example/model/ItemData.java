package com.demo.example.model;

/**
 * Created by hzsunyj on 16/12/30.
 */
public class ItemData {

    /**
     * tag 区分每一行数据
     */
    public int tag;

    /**
     * 数据类型
     */
    public int holderType;

    public String itemDesc;

    public Object data;

    public ItemData(int tag, int holderType) {
        this.tag = tag;
        this.holderType = holderType;
    }

    public ItemData(int tag, int holderType, String itemDesc) {
        this.tag = tag;
        this.holderType = holderType;
        this.itemDesc = itemDesc;
    }

    public ItemData(int tag, int holderType, Object data) {
        this.tag = tag;
        this.holderType = holderType;
        this.data = data;
    }
}
