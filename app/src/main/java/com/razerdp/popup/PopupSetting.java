package com.razerdp.popup;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.razerdp.animatedpieview.R;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;

import razerdp.basepopup.BasePopupWindow;
import razerdp.blur.PopupBlurOption;

/**
 * Created by 大灯泡 on 2017/11/29.
 */
public class PopupSetting extends BasePopupWindow {

    public SwitchCompat switchDonuts;
    public SwitchCompat switchText;
    public SwitchCompat switchTouchAnimation;
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
    public TextInputLayout inputTextLineStartMargin;
    public TextInputLayout inputSplitAngle;
    public TextInputLayout inputFocusAlphaType;
    public AppCompatRadioButton radioFocusWithAlpha;
    public AppCompatRadioButton radioFocusWithAlphaRev;
    public AppCompatRadioButton radioFocusWithoutAlpha;
    public AppCompatRadioButton textGravityAbove;
    public AppCompatRadioButton textGravityBelow;
    public AppCompatRadioButton textGravityAlign;
    public AppCompatRadioButton textGravityDystopy;
    public Button btnOk;
    public RelativeLayout popupAnima;

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
        this.switchDonuts = (SwitchCompat) findViewById(R.id.switch_donuts);
        this.switchText = (SwitchCompat) findViewById(R.id.switch_text);
        this.switchTouchAnimation = (SwitchCompat) findViewById(R.id.switch_touch_animation);
        this.switchCanTouch = (SwitchCompat) findViewById(R.id.switch_can_touch);
        this.inputDuration = (TextInputLayout) findViewById(R.id.input_duration);
        this.inputStartAngle = (TextInputLayout) findViewById(R.id.input_start_angle);
        this.inputTouchScaleSize = (TextInputLayout) findViewById(R.id.input_touch_scale_size);
        this.inputTouchScaleUpDuration = (TextInputLayout) findViewById(R.id.input_touch_scale_up_duration);
        this.inputTouchScaleDownDuration = (TextInputLayout) findViewById(R.id.input_touch_scale_down_duration);
        this.inputTouchShadowRadius = (TextInputLayout) findViewById(R.id.input_touch_shadow_radius);
        this.inputTouchExpandAngle = (TextInputLayout) findViewById(R.id.input_touch_expand_angle);
        this.inputPieRadiusScale = (TextInputLayout) findViewById(R.id.input_pie_radius_scale);
        this.inputTextMarginLine = (TextInputLayout) findViewById(R.id.input_text_margin_line);
        this.inputTextSize = (TextInputLayout) findViewById(R.id.input_text_size);
        this.inputTextPointRadius = (TextInputLayout) findViewById(R.id.input_text_point_radius);
        this.inputTextLineStrokeWidth = (TextInputLayout) findViewById(R.id.input_text_line_stroke_width);
        this.inputTextLineStartMargin = (TextInputLayout) findViewById(R.id.input_text_line_start_margin);
        this.inputSplitAngle = (TextInputLayout) findViewById(R.id.input_split_angle);
        this.inputFocusAlphaType = (TextInputLayout) findViewById(R.id.input_focus_alpha_type);
        this.radioFocusWithAlpha = (AppCompatRadioButton) findViewById(R.id.radio_focus_with_alpha);
        this.radioFocusWithAlphaRev = (AppCompatRadioButton) findViewById(R.id.radio_focus_with_alpha_rev);
        this.radioFocusWithoutAlpha = (AppCompatRadioButton) findViewById(R.id.radio_focus_without_alpha);
        this.textGravityAbove = (AppCompatRadioButton) findViewById(R.id.text_gravity_above);
        this.textGravityBelow = (AppCompatRadioButton) findViewById(R.id.text_gravity_below);
        this.textGravityAlign = (AppCompatRadioButton) findViewById(R.id.text_gravity_align);
        this.textGravityDystopy = (AppCompatRadioButton) findViewById(R.id.text_gravity_dystopy);
        this.popupAnima = (RelativeLayout) findViewById(R.id.popup_anima);

        this.btnOk = (Button) findViewById(R.id.btn_ok);

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
        setConfig(viewConfig);
        super.dismiss();
    }

    public void showPopupWindow(AnimatedPieViewConfig viewConfig) {
        if (viewConfig == null) return;
        applyConfigToView(viewConfig);
        super.showPopupWindow();
    }


    private void setConfig(AnimatedPieViewConfig viewConfig) {
        if (viewConfig == null) return;
        viewConfig.strokeMode(!switchDonuts.isChecked())
                .drawText(switchText.isChecked())
                .animOnTouch(switchTouchAnimation.isChecked())
                .canTouch(switchCanTouch.isChecked())
                .duration(getTextLong(inputDuration, viewConfig.getDuration()))
                .startAngle(getTextFloat(inputStartAngle, viewConfig.getStartAngle()))
                .floatExpandSize(getTextFloat(inputTouchScaleSize, viewConfig.getFloatExpandSize()))
                .floatUpDuration(getTextLong(inputTouchScaleUpDuration, viewConfig.getFloatUpDuration()))
                .floatDownDuration(getTextLong(inputTouchScaleDownDuration, viewConfig.getFloatDownDuration()))
                .floatShadowRadius(getTextFloat(inputTouchShadowRadius, viewConfig.getFloatShadowRadius()))
                .floatExpandAngle(getTextFloat(inputTouchExpandAngle, viewConfig.getFloatExpandAngle()))
                .pieRadiusRatio(getTextFloat(inputPieRadiusScale, viewConfig.getPieRadiusRatio()))
                .textMargin(getTextInt(inputTextMarginLine, viewConfig.getTextMargin()))
                .textSize(getTextFloat(inputTextSize, viewConfig.getTextSize()))
                .guidePointRadius(getTextInt(inputTextPointRadius, viewConfig.getGuidePointRadius()))
                .guideLineWidth(getTextInt(inputTextLineStrokeWidth, viewConfig.getGuideLineWidth()))
                .splitAngle(getTextFloat(inputSplitAngle, viewConfig.getSplitAngle()))
                .focusAlpha(getTextInt(inputFocusAlphaType, viewConfig.getFocusAlpha()));
        if (radioFocusWithoutAlpha.isChecked()) {
            viewConfig.focusAlphaType(AnimatedPieViewConfig.FOCUS_WITHOUT_ALPHA);
        } else if (radioFocusWithAlpha.isChecked()) {
            viewConfig.focusAlphaType(AnimatedPieViewConfig.FOCUS_WITH_ALPHA);
        } else if (radioFocusWithAlphaRev.isChecked()) {
            viewConfig.focusAlphaType(AnimatedPieViewConfig.FOCUS_WITH_ALPHA_REV);
        }

        if (textGravityAbove.isChecked()) {
            viewConfig.textGravity(AnimatedPieViewConfig.ABOVE);
        } else if (textGravityBelow.isChecked()) {
            viewConfig.textGravity(AnimatedPieViewConfig.BELOW);
        } else if (textGravityAlign.isChecked()) {
            viewConfig.textGravity(AnimatedPieViewConfig.ALIGN);
        } else if (textGravityDystopy.isChecked()) {
            viewConfig.textGravity(AnimatedPieViewConfig.ECTOPIC);
        }

    }

    private void applyConfigToView(AnimatedPieViewConfig viewConfig) {
        this.viewConfig = viewConfig;
        switchDonuts.setChecked(!viewConfig.isStrokeMode());
        switchText.setChecked(viewConfig.isDrawText());
        switchTouchAnimation.setChecked(viewConfig.isAnimTouch());
        switchCanTouch.setChecked(viewConfig.isCanTouch());

        setText(inputDuration, viewConfig.getDuration());
        setText(inputStartAngle, viewConfig.getStartAngle());
        setText(inputTouchScaleSize, viewConfig.getFloatExpandSize());
        setText(inputTouchScaleUpDuration, viewConfig.getFloatUpDuration());
        setText(inputTouchScaleDownDuration, viewConfig.getFloatDownDuration());
        setText(inputTouchShadowRadius, viewConfig.getFloatShadowRadius());
        setText(inputTouchExpandAngle, viewConfig.getFloatExpandAngle());
        setText(inputPieRadiusScale, viewConfig.getPieRadiusRatio());
        setText(inputTextMarginLine, viewConfig.getTextMargin());
        setText(inputTextSize, viewConfig.getTextSize());
        setText(inputTextPointRadius, viewConfig.getGuidePointRadius());
        setText(inputTextLineStrokeWidth, viewConfig.getGuideLineWidth());
        setText(inputTextLineStartMargin, viewConfig.getGuideLineMarginStart());
        setText(inputSplitAngle, viewConfig.getSplitAngle());
        setText(inputFocusAlphaType, viewConfig.getFocusAlpha());

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

        switch (viewConfig.getTextGravity()) {
            case AnimatedPieViewConfig.ABOVE:
                textGravityAbove.setChecked(true);
                break;
            case AnimatedPieViewConfig.BELOW:
                textGravityBelow.setChecked(true);
                break;
            case AnimatedPieViewConfig.ALIGN:
                textGravityAlign.setChecked(true);
                break;
            case AnimatedPieViewConfig.ECTOPIC:
                textGravityDystopy.setChecked(true);
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
