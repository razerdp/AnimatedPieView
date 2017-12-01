package com.razerdp.popup;

import android.content.Context;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CompoundButton;

import com.razerdp.animatedpieview.R;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;

import razerdp.basepopup.BasePopupWindow;

/**
 * Created by 大灯泡 on 2017/11/29.
 */
public class PopupSetting extends BasePopupWindow {
    public SwitchCompat switchDonuts;
    public SwitchCompat switchText;
    public SwitchCompat switchTouchAnimation;
    public SwitchCompat switchDirectText;
    public SwitchCompat switchCanTouch;
    public TextInputLayout inputDuration;
    public TextInputLayout inputStartAngle;
    public TextInputLayout inputTouchScaleSize;
    public TextInputLayout inputTouchScaleUpDuration;
    public TextInputLayout inputTouchScaleDownDuration;
    public TextInputLayout inputTouchShadowRadius;
    public TextInputLayout inputTouchExpandAngle;
    public TextInputLayout inputPieRadiusScale;
    public TextInputLayout inputTextMarginLine;
    public TextInputLayout inputTextSize;
    public TextInputLayout inputTextPointRadius;
    public TextInputLayout inputTextLineStrokeWidth;
    public TextInputLayout inputTextLineTransitionLength;
    public TextInputLayout inputTextLineStartMargin;
    public TextInputLayout inputSplitAngle;
    public TextInputLayout inputFocusAlphaType;
    public AppCompatRadioButton radioFocusWithAlpha;
    public AppCompatRadioButton radioFocusWithAlphaRev;
    public AppCompatRadioButton radioFocusWithoutAlpha;
    public AppCompatRadioButton radioCapButt;
    public AppCompatRadioButton radioCapRound;
    public AppCompatRadioButton radioCapSquare;

    public Button btnOk;

    AnimatedPieViewConfig viewConfig;

    private View.OnClickListener mOnClickListener;

    public PopupSetting(Context context) {
        super(context);
        setBackPressEnable(false);
        findView();
    }

    @Override
    protected Animation initShowAnimation() {
        return getDefaultAlphaAnimation();
    }

    @Override
    public View getClickToDismissView() {
        return null;
    }

    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.popup_setting);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }

    public View.OnClickListener getOnOkButtonClickListener() {
        return mOnClickListener;
    }

    public void setOnOkButtonClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public void showPopupWindow(AnimatedPieViewConfig viewConfig) {
        if (viewConfig == null) return;
        applyConfigToView(viewConfig);
        super.showPopupWindow();
    }

    public void dismiss(AnimatedPieViewConfig viewConfig) {
        setConfig(viewConfig);
        super.dismiss();
    }

    private void setConfig(AnimatedPieViewConfig viewConfig) {
        if (viewConfig == null) return;
        viewConfig.setDrawStrokeOnly(!switchDonuts.isChecked())
                  .setDrawText(switchText.isChecked())
                  .setTouchAnimation(switchTouchAnimation.isChecked())
                  .setDirectText(switchDirectText.isChecked())
                  .setCanTouch(switchCanTouch.isChecked())
                  .setDuration(getTextLong(inputDuration, viewConfig.getDuration()))
                  .setStartAngle(getTextFloat(inputStartAngle, viewConfig.getStartAngle()))
                  .setTouchScaleSize(getTextFloat(inputTouchScaleSize, viewConfig.getTouchScaleSize()))
                  .setTouchScaleUpDuration(getTextLong(inputTouchScaleUpDuration, viewConfig.getTouchScaleUpDuration()))
                  .setTouchScaleDownDuration(getTextLong(inputTouchScaleDownDuration, viewConfig.getTouchScaleDownDuration()))
                  .setTouchShadowRadius(getTextFloat(inputTouchShadowRadius, viewConfig.getTouchShadowRadius()))
                  .setTouchExpandAngle(getTextFloat(inputTouchExpandAngle, viewConfig.getTouchExpandAngle()))
                  .setPieRadiusScale(getTextFloat(inputPieRadiusScale, viewConfig.getPieRadiusScale()))
                  .setTextMarginLine(getTextFloat(inputTextMarginLine, viewConfig.getTextMarginLine()))
                  .setTextSize(getTextInt(inputTextSize, viewConfig.getTextSize()))
                  .setTextPointRadius(getTextInt(inputTextPointRadius, viewConfig.getTextPointRadius()))
                  .setTextLineStrokeWidth(getTextInt(inputTextLineStrokeWidth, viewConfig.getTextLineStrokeWidth()))
                  .setTextLineTransitionLength(getTextInt(inputTextLineTransitionLength, viewConfig.getTextLineTransitionLength()))
                  .setSplitAngle(getTextFloat(inputSplitAngle, viewConfig.getSplitAngle()));

        float alphaCut = getTextFloat(inputFocusAlphaType, viewConfig.getFocusAlphaCut());
        if (radioFocusWithoutAlpha.isChecked()) {
            viewConfig.setFocusAlphaType(AnimatedPieViewConfig.FOCUS_WITHOUT_ALPHA, alphaCut);
        } else if (radioFocusWithAlpha.isChecked()) {
            viewConfig.setFocusAlphaType(AnimatedPieViewConfig.FOCUS_WITH_ALPHA, alphaCut);
        } else if (radioFocusWithAlphaRev.isChecked()) {
            viewConfig.setFocusAlphaType(AnimatedPieViewConfig.FOCUS_WITH_ALPHA_REV, alphaCut);
        }

       if (radioCapButt.isChecked()) {
            viewConfig.setStrokePaintCap(Paint.Cap.BUTT);
        } else if (radioCapRound.isChecked()) {
            viewConfig.setStrokePaintCap(Paint.Cap.ROUND);
        } else if (radioCapSquare.isChecked()) {
            viewConfig.setStrokePaintCap(Paint.Cap.SQUARE);
        }

    }

    private void findView() {
        switchDonuts = (SwitchCompat) findViewById(R.id.switch_donuts);
        switchText = (SwitchCompat) findViewById(R.id.switch_text);
        switchTouchAnimation = (SwitchCompat) findViewById(R.id.switch_touch_animation);
        switchDirectText = (SwitchCompat) findViewById(R.id.switch_direct_text);
        switchCanTouch = (SwitchCompat) findViewById(R.id.switch_can_touch);
        inputDuration = (TextInputLayout) findViewById(R.id.input_duration);
        inputStartAngle = (TextInputLayout) findViewById(R.id.input_start_angle);
        inputTouchScaleSize = (TextInputLayout) findViewById(R.id.input_touch_scale_size);
        inputTouchScaleUpDuration = (TextInputLayout) findViewById(R.id.input_touch_scale_up_duration);
        inputTouchScaleDownDuration = (TextInputLayout) findViewById(R.id.input_touch_scale_down_duration);
        inputTouchShadowRadius = (TextInputLayout) findViewById(R.id.input_touch_shadow_radius);
        inputTouchExpandAngle = (TextInputLayout) findViewById(R.id.input_touch_expand_angle);
        inputPieRadiusScale = (TextInputLayout) findViewById(R.id.input_pie_radius_scale);
        inputTextMarginLine = (TextInputLayout) findViewById(R.id.input_text_margin_line);
        inputTextSize = (TextInputLayout) findViewById(R.id.input_text_size);
        inputTextPointRadius = (TextInputLayout) findViewById(R.id.input_text_point_radius);
        inputTextLineStrokeWidth = (TextInputLayout) findViewById(R.id.input_text_line_stroke_width);
        inputTextLineTransitionLength = (TextInputLayout) findViewById(R.id.input_text_line_transition_length);
        inputTextLineStartMargin = (TextInputLayout) findViewById(R.id.input_text_line_start_margin);
        inputSplitAngle = (TextInputLayout) findViewById(R.id.input_split_angle);
        inputFocusAlphaType = (TextInputLayout) findViewById(R.id.input_focus_alpha_type);
        radioFocusWithAlpha = (AppCompatRadioButton) findViewById(R.id.radio_focus_with_alpha);
        radioFocusWithAlphaRev = (AppCompatRadioButton) findViewById(R.id.radio_focus_with_alpha_rev);
        radioFocusWithoutAlpha = (AppCompatRadioButton) findViewById(R.id.radio_focus_without_alpha);
        radioCapButt = (AppCompatRadioButton) findViewById(R.id.radio_paint_cap_butt);
        radioCapRound = (AppCompatRadioButton) findViewById(R.id.radio_paint_cap_round);
        radioCapSquare = (AppCompatRadioButton) findViewById(R.id.radio_paint_cap_square);

        btnOk = (Button) findViewById(R.id.btn_ok);

        switchDonuts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchDonuts.setText(isChecked ? "饼图（pie-chat）" : "甜甜圈（ring-chat）");
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewConfig != null) {
                    dismiss(viewConfig);
                    if (mOnClickListener != null) {
                        mOnClickListener.onClick(v);
                    }
                }
            }
        });
    }

    private void applyConfigToView(AnimatedPieViewConfig viewConfig) {
        this.viewConfig = viewConfig;
        switchDonuts.setChecked(!viewConfig.isDrawStrokeOnly());
        switchText.setChecked(viewConfig.isDrawText());
        switchTouchAnimation.setChecked(viewConfig.isTouchAnimation());
        switchDirectText.setChecked(viewConfig.isDirectText());
        switchCanTouch.setChecked(viewConfig.isCanTouch());

        setText(inputDuration, viewConfig.getDuration());
        setText(inputStartAngle, viewConfig.getStartAngle());
        setText(inputTouchScaleSize, viewConfig.getTouchScaleSize());
        setText(inputTouchScaleUpDuration, viewConfig.getTouchScaleUpDuration());
        setText(inputTouchScaleDownDuration, viewConfig.getTouchScaleDownDuration());
        setText(inputTouchShadowRadius, viewConfig.getTouchShadowRadius());
        setText(inputTouchExpandAngle, viewConfig.getTouchExpandAngle());
        setText(inputPieRadiusScale, viewConfig.getPieRadiusScale());
        setText(inputTextMarginLine, viewConfig.getTextMarginLine());
        setText(inputTextSize, viewConfig.getTextSize());
        setText(inputTextPointRadius, viewConfig.getTextPointRadius());
        setText(inputTextLineStrokeWidth, viewConfig.getTextLineStrokeWidth());
        setText(inputTextLineTransitionLength, viewConfig.getTextLineTransitionLength());
        setText(inputTextLineStartMargin, viewConfig.getTextLineStartMargin());
        setText(inputSplitAngle, viewConfig.getSplitAngle());
        setText(inputFocusAlphaType, viewConfig.getFocusAlphaCut());

        switch (viewConfig.getFocusAlphaType()) {
            case AnimatedPieViewConfig.FOCUS_WITH_ALPHA:
                radioFocusWithAlpha.setChecked(true);
                break;
            case AnimatedPieViewConfig.FOCUS_WITH_ALPHA_REV:
                radioFocusWithAlphaRev.setChecked(true);
                break;
            case AnimatedPieViewConfig.FOCUS_WITHOUT_ALPHA:
                radioFocusWithoutAlpha.setChecked(true);
                break;
        }

        switch (viewConfig.getStrokePaintCap()) {
            case BUTT:
                radioCapButt.setChecked(true);
                break;
            case ROUND:
                radioCapRound.setChecked(true);
                break;
            case SQUARE:
                radioCapSquare.setChecked(true);
                break;
            default:
                break;
        }
    }


    private void setText(TextInputLayout v, Object str) {
        if (v == null) return;
        v.getEditText().setText(String.valueOf(str));
    }

    private long getTextLong(TextInputLayout v, long defaultValue) {
        try {
            long value = Long.parseLong(v.getEditText().getText().toString().trim());
            return value;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private float getTextFloat(TextInputLayout v, float defaultValue) {
        try {
            float value = Float.parseFloat(v.getEditText().getText().toString().trim());
            return value;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private int getTextInt(TextInputLayout v, int defaultValue) {
        try {
            int value = Integer.parseInt(v.getEditText().getText().toString().trim());
            return value;
        } catch (Exception e) {
            return defaultValue;
        }
    }

}
