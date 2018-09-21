package com.razerdp.widget.animatedpieview.data;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by 大灯泡 on 2018/7/4.
 */
public class PieOption implements Parcelable {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({NEAR_PIE, FAR_FROM_PIE})
    public @interface LabelPosition {
    }

    public static final int NEAR_PIE = 0x10;
    public static final int FAR_FROM_PIE = 0x11;

    Bitmap mLabelIcon;
    @LabelPosition
    int mLabelPosition = FAR_FROM_PIE;
    int mLabelPadding = 10;

    float mIconWidth;
    float mIconHeight;

    float mIconScaledWidth;
    float mIconScaledHeight;

    boolean mDefaultSelected;

    public PieOption() {
    }

    protected PieOption(Parcel in) {
        mLabelIcon = in.readParcelable(Bitmap.class.getClassLoader());
        mLabelPosition = in.readInt();
        mLabelPadding = in.readInt();
        mIconWidth = in.readFloat();
        mIconHeight = in.readFloat();
        mIconScaledWidth = in.readFloat();
        mIconScaledHeight = in.readFloat();
        mDefaultSelected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mLabelIcon, flags);
        dest.writeInt(mLabelPosition);
        dest.writeInt(mLabelPadding);
        dest.writeFloat(mIconWidth);
        dest.writeFloat(mIconHeight);
        dest.writeFloat(mIconScaledWidth);
        dest.writeFloat(mIconScaledHeight);
        dest.writeByte((byte) (mDefaultSelected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PieOption> CREATOR = new Creator<PieOption>() {
        @Override
        public PieOption createFromParcel(Parcel in) {
            return new PieOption(in);
        }

        @Override
        public PieOption[] newArray(int size) {
            return new PieOption[size];
        }
    };

    public Bitmap getLabelIcon() {
        return mLabelIcon;
    }

    public PieOption setLabelIcon(Bitmap labelIcon) {
        mLabelIcon = labelIcon;
        return this;
    }

    @LabelPosition
    public int getLabelPosition() {
        return mLabelPosition;
    }

    public PieOption setLabelPosition(@LabelPosition int labelPosition) {
        mLabelPosition = labelPosition;
        return this;
    }

    public int getLabelPadding() {
        return mLabelPadding;
    }

    public PieOption setLabelPadding(int labelPadding) {
        mLabelPadding = labelPadding;
        return this;
    }

    public boolean isDefaultSelected() {
        return mDefaultSelected;
    }

    public PieOption setDefaultSelected(boolean defaultSelected) {
        this.mDefaultSelected = defaultSelected;
        return this;
    }

    public float getIconWidth() {
        return mIconWidth;
    }

    public PieOption setIconWidth(float iconWidth) {
        mIconWidth = iconWidth;
        return this;
    }

    public float getIconHeight() {
        return mIconHeight;
    }

    public PieOption setIconHeight(float iconHeight) {
        mIconHeight = iconHeight;
        return this;
    }

    public float getIconScaledWidth() {
        return mIconScaledWidth;
    }

    public PieOption setIconScaledWidth(float iconScaledWidth) {
        mIconScaledWidth = iconScaledWidth;
        return this;
    }

    public float getIconScaledHeight() {
        return mIconScaledHeight;
    }

    public PieOption setIconScaledHeight(float iconScaledHeight) {
        mIconScaledHeight = iconScaledHeight;
        return this;
    }
}
