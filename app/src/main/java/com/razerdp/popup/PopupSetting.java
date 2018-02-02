package com.razerdp.popup;

import android.content.Context;
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
import razerdp.blur.PopupBlurOption;

/**
 * Created by 大灯泡 on 2017/11/29.
 */
public class PopupSetting extends BasePopupWindow {
    private SwitchCompat switchDonuts;
    private SwitchCompat switchText;
    private SwitchCompat switchTouchAnimation;
    private SwitchCompat switchDirectText;
    private SwitchCompat switchCanTouch;
    private TextInputLayout inputDuration;
    private TextInputLayout inputStartAngle;
    private TextInputLayout inputTouchScaleSize;
    private TextInputLayout inputTouchScaleUpDuration;
    private TextInputLayout inputTouchScaleDownDuration;
    private TextInputLayout inputTouchShadowRadius;
    private TextInputLayout inputTouchExpandAngle;
    private TextInputLayout inputPieRadiusScale;
    private TextInputLayout inputTextMarginLine;
    private TextInputLayout inputTextSize;
    private TextInputLayout inputTextPointRadius;
    private TextInputLayout inputTextLineStrokeWidth;
    private TextInputLayout inputTextLineTransitionLength;
    private TextInputLayout inputTextLineStartMargin;
    private TextInputLayout inputSplitAngle;
    private TextInputLayout inputFocusAlphaType;
    private AppCompatRadioButton radioFocusWithAlpha;
    private AppCompatRadioButton radioFocusWithAlphaRev;
    private AppCompatRadioButton radioFocusWithoutAlpha;
    private AppCompatRadioButton radioCapButt;
    private AppCompatRadioButton radioCapRound;
    private AppCompatRadioButton radioCapSquare;

    public Button btnOk;

    AnimatedPieViewConfig viewConfig;

    private View.OnClickListener mOnClickListener;

    public PopupSetting(Context context) {
        super(context);
        setBackPressEnable(false);
        setBlurBackgroundEnable(true, new OnBlurOptionInitListener() {
            @Override
            public void onCreateBlurOption(PopupBlurOption option) {
                option.setBlurInDuration(500).setBlurOutDuration(500);

            }
        });
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

    public void dismiss(AnimatedPieViewConfig viewConfig) {
//        setConfig(viewConfig);
        super.dismiss();
    }

   /* public void showPopupWindow(AnimatedPieViewConfig2 viewConfig) {
        if (viewConfig == null) return;
        applyConfigToView(viewConfig);
        super.showPopupWindow();
    }


    private void setConfig(AnimatedPieViewConfig2 viewConfig) {
        if (viewConfig == null) return;
        viewConfig.strokeOnly(!switchDonuts.isChecked())
                .drawDescText(switchText.isChecked())
                .touchWithAnimation(switchTouchAnimation.isChecked())
                .directText(switchDirectText.isChecked())
                .canTouch(switchCanTouch.isChecked())
                .animationDrawDuration(getTextLong(inputDuration, viewConfig.getAnimationDrawDuration()))
                .startAngle(getTextFloat(inputStartAngle, viewConfig.getStartAngle()))
                .touchScaleSize(getTextFloat(inputTouchScaleSize, viewConfig.getTouchScaleSize()))
                .touchScaleUpDuration(getTextLong(inputTouchScaleUpDuration, viewConfig.getTouchScaleUpDuration()))
                .touchScaleDownDuration(getTextLong(inputTouchScaleDownDuration, viewConfig.getTouchScaleDownDuration()))
                .touchShadowRadius(getTextFloat(inputTouchShadowRadius, viewConfig.getTouchShadowRadius()))
                .touchExpandAngle(getTextFloat(inputTouchExpandAngle, viewConfig.getTouchExpandAngle()))
                .pieRadiusScale(getTextFloat(inputPieRadiusScale, viewConfig.getPieRadiusScale()))
                .textMarginLine(getTextFloat(inputTextMarginLine, viewConfig.getTextMarginLine()))
                .textSize(getTextInt(inputTextSize, viewConfig.getTextSize()))
                .descGuidePointRadius(getTextInt(inputTextPointRadius, viewConfig.getDescGuidePointRadius()))
                .textGuideLineStrokeWidth(getTextInt(inputTextLineStrokeWidth, viewConfig.getTextGuideLineStrokeWidth()))
                .setTextLineTransitionLength(getTextInt(inputTextLineTransitionLength, viewConfig.getTextLineTransitionLength()))
                .splitAngle(getTextFloat(inputSplitAngle, viewConfig.getSplitAngle()));

        float alphaCut = getTextFloat(inputFocusAlphaType, viewConfig.getFocusAlphaCut());
        if (radioFocusWithoutAlpha.isChecked()) {
            viewConfig.focusAlphaType(AnimatedPieViewConfig2.FOCUS_WITHOUT_ALPHA, alphaCut);
        } else if (radioFocusWithAlpha.isChecked()) {
            viewConfig.focusAlphaType(AnimatedPieViewConfig2.FOCUS_WITH_ALPHA, alphaCut);
        } else if (radioFocusWithAlphaRev.isChecked()) {
            viewConfig.focusAlphaType(AnimatedPieViewConfig2.FOCUS_WITH_ALPHA_REV, alphaCut);
        }

        if (radioCapButt.isChecked()) {
            viewConfig.strokePaintCap(Paint.Cap.BUTT);
        } else if (radioCapRound.isChecked()) {
            viewConfig.strokePaintCap(Paint.Cap.ROUND);
        } else if (radioCapSquare.isChecked()) {
            viewConfig.strokePaintCap(Paint.Cap.SQUARE);
        }

    }

    private void applyConfigToView(AnimatedPieViewConfig2 viewConfig) {
        this.viewConfig = viewConfig;
        switchDonuts.setChecked(!viewConfig.isStrokeOnly());
        switchText.setChecked(viewConfig.isDrawDescText());
        switchTouchAnimation.setChecked(viewConfig.isTouchWithAnimation());
        switchDirectText.setChecked(viewConfig.isDirectText());
        switchCanTouch.setChecked(viewConfig.isCanTouch());

        setText(inputDuration, viewConfig.getAnimationDrawDuration());
        setText(inputStartAngle, viewConfig.getStartAngle());
        setText(inputTouchScaleSize, viewConfig.getTouchScaleSize());
        setText(inputTouchScaleUpDuration, viewConfig.getTouchScaleUpDuration());
        setText(inputTouchScaleDownDuration, viewConfig.getTouchScaleDownDuration());
        setText(inputTouchShadowRadius, viewConfig.getTouchShadowRadius());
        setText(inputTouchExpandAngle, viewConfig.getTouchExpandAngle());
        setText(inputPieRadiusScale, viewConfig.getPieRadiusScale());
        setText(inputTextMarginLine, viewConfig.getTextMarginLine());
        setText(inputTextSize, viewConfig.getTextSize());
        setText(inputTextPointRadius, viewConfig.getDescGuidePointRadius());
        setText(inputTextLineStrokeWidth, viewConfig.getTextGuideLineStrokeWidth());
        setText(inputTextLineTransitionLength, viewConfig.getTextLineTransitionLength());
        setText(inputTextLineStartMargin, viewConfig.getTextLineStartMargin());
        setText(inputSplitAngle, viewConfig.getSplitAngle());
        setText(inputFocusAlphaType, viewConfig.getFocusAlphaCut());

        switch (viewConfig.getFocusAlphaType()) {
            case AnimatedPieViewConfig2.FOCUS_WITH_ALPHA:
                radioFocusWithAlpha.setChecked(true);
                break;
            case AnimatedPieViewConfig2.FOCUS_WITH_ALPHA_REV:
                radioFocusWithAlphaRev.setChecked(true);
                break;
            case AnimatedPieViewConfig2.FOCUS_WITHOUT_ALPHA:
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
*/

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
