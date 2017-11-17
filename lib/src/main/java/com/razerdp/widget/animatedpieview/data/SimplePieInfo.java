package com.razerdp.widget.animatedpieview.data;

/**
 * Created by 大灯泡 on 2017/11/7.
 */

public class SimplePieInfo implements IPieInfo {
    private float value;
    private int color;
    private String desc;

    public SimplePieInfo() {
    }

    public SimplePieInfo(float value, int color) {
        this(value, color, "");
    }

    public SimplePieInfo(float value, int color, String desc) {
        this.value = value;
        this.color = color;
        this.desc = desc;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public float getValue() {
        return value;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
