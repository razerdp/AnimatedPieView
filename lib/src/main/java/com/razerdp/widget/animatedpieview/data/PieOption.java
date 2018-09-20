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

    boolean defaultSelected;

    public PieOption() {
    }

    protected PieOption(Parcel in) {
        mLabelIcon = in.readParcelable(Bitmap.class.getClassLoader());
        mLabelPosition = in.readInt();
        mLabelPadding = in.readInt();
        defaultSelected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mLabelIcon, flags);
        dest.writeInt(mLabelPosition);
        dest.writeInt(mLabelPadding);
        dest.writeByte((byte) (defaultSelected ? 1 : 0));
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
        return defaultSelected;
    }

    public PieOption setDefaultSelected(boolean defaultSelected) {
        this.defaultSelected = defaultSelected;
        return this;
    }
}
