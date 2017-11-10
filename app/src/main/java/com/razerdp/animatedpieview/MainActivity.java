package com.razerdp.animatedpieview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.CompoundButton;

import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private AnimatedPieView test;
    private Button mButton;
    private SwitchCompat mNoDonuts;
    private final Random random = new Random();
    private boolean noDonuts = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mButton = findViewById(R.id.start);
        mNoDonuts = findViewById(R.id.no_donuts);
        test = findViewById(R.id.test);
        AnimatedPieViewConfig config = new AnimatedPieViewConfig();
        config.setStartAngle(-90)
                .addData(new SimplePieInfo(30, getColor("FFC5FF8C"), "这是第一段"))
                .addData(new SimplePieInfo(18.0f, getColor("FFFFD28C"), "这是第二段"))
                .addData(new SimplePieInfo(123.0f, getColor("FFbb76b4"), "这是第三段"))
                .addData(new SimplePieInfo(87.0f, getColor("FFFFD28C"), "这是第四段"))
                .addData(new SimplePieInfo(15.0f, getColor("ff2bbc80"), "这是第五段"))
                .addData(new SimplePieInfo(55.0f, getColor("ff8be8ff"), "这是第六段"))
                .addData(new SimplePieInfo(30.0f, getColor("fffa734d"), "这是第七段"))
                .addData(new SimplePieInfo(30.0f, getColor("ff957de0"), "这是第八段"))
                .setDuration(2000)
                .setInterpolator(new DecelerateInterpolator(2.5f));
        test.applyConfig(config);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test.getConfig().setDrawStrokeOnly(!noDonuts);
                test.start();
            }
        });
        mNoDonuts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                noDonuts = isChecked;
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
