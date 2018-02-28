package com.razerdp.demo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.razerdp.animatedpieview.R;
import com.razerdp.popup.PopupSetting;
import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.callback.OnPieSelectListener;
import com.razerdp.widget.animatedpieview.data.IPieInfo;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;
import com.razerdp.widget.animatedpieview.utils.PLog;

import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final Random random = new Random();
    private AnimatedPieView mAnimatedPieView;
    private Button start;
    private Button setting;
    private Button toRecycler;
    private PopupSetting mPopupSetting;
    private TextView desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        PLog.setDebuggable(true);
        mPopupSetting = new PopupSetting(this);
        start = findViewById(R.id.start);
        setting = findViewById(R.id.setting);
        desc = findViewById(R.id.tv_desc);
        toRecycler = findViewById(R.id.btn_goto_recycler);
        mAnimatedPieView = findViewById(R.id.animatedPieView);
        AnimatedPieViewConfig config = new AnimatedPieViewConfig();
        config.startAngle(0.9224089f)
                .addData(new SimplePieInfo(0.11943538617599236, getColor("FF446767")), true)
                .addData(new SimplePieInfo(0.41780274681129415, getColor("FFFFD28C")), true)
                .addData(new SimplePieInfo(0.722165651192247, getColor("FFbb76b4")), true)
                .addData(new SimplePieInfo(0.9184314356136125, getColor("FFFFD28C"), "长文字test"), false)
                .addData(new SimplePieInfo(0.6028910840057398, getColor("ff2bbc80")), true)
                .addData(new SimplePieInfo(0.6449620647212785, getColor("ff8be8ff")), true)
                .addData(new SimplePieInfo(0.058853315195452116, getColor("fffa734d")), true)
                .addData(new SimplePieInfo(0.6632297717331086, getColor("ff957de0")), true)
                .addData(new SimplePieInfo(0.8226830459369171, getColor("FF446767")), true)
                .splitAngle(0.9649368f)
                .selectListener(new OnPieSelectListener() {
                    @Override
                    public void onSelectPie(@NonNull IPieInfo pieInfo, boolean isFloatUp) {
                        desc.setText(String.format(Locale.getDefault(),
                                "touch pie >>> {\n  value = %s;\n  color = %d;\n  desc = %s;\n  isFloatUp = %s;\n }",
                                pieInfo.getValue(), pieInfo.getColor(), pieInfo.getDesc(), isFloatUp));
                    }
                })
                .drawText(true)
                .duration(1200)
                .textSize(26)
                .focusAlphaType(AnimatedPieViewConfig.FOCUS_WITH_ALPHA)
                .textGravity(AnimatedPieViewConfig.ABOVE);
        mAnimatedPieView.applyConfig(config);

        mPopupSetting.setOnOkButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimatedPieView.start();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimatedPieView.start();
            }
        });


        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupSetting.showPopupWindow(mAnimatedPieView.getConfig());
            }
        });

        toRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RecyclerActivity.class));
            }
        });

    }

    private int randomColor() {
        int red = random.nextInt(255);
        int green = random.nextInt(255);
        int blue = random.nextInt(255);
        return Color.argb(255, red, green, blue);
    }

    private int getColor(String colorStr) {
        if (TextUtils.isEmpty(colorStr)) return Color.BLACK;
        if (!colorStr.startsWith("#")) colorStr = "#" + colorStr;
        return Color.parseColor(colorStr);
    }
}
