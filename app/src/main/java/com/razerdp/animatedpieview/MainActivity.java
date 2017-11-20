package com.razerdp.animatedpieview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.callback.OnPieSelectListener;
import com.razerdp.widget.animatedpieview.data.IPieInfo;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private SwitchCompat mNoDonuts;
    private SwitchCompat mDrawText;
    private final Random random = new Random();
    private boolean noDonuts = false;
    private boolean drawText = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mButton = findViewById(R.id.start);
        mNoDonuts = findViewById(R.id.no_donuts);
        mDrawText = findViewById(R.id.draw_text);
        final AnimatedPieView mAnimatedPieView = findViewById(R.id.animatedPieView);
        AnimatedPieViewConfig config = new AnimatedPieViewConfig();
        config.setStartAngle(-90)
                .addData(new SimplePieInfo(30, getColor("FF446767")), true)
                .addData(new SimplePieInfo(18.0f, getColor("FFFFD28C")), true)
                .addData(new SimplePieInfo(123.0f, getColor("FFbb76b4")), true)
                .addData(new SimplePieInfo(87.0f, getColor("FFFFD28C")), true)
                .addData(new SimplePieInfo(15.0f, getColor("ff2bbc80")), true)
                .addData(new SimplePieInfo(55.0f, getColor("ff8be8ff")), true)
                .addData(new SimplePieInfo(30.0f, getColor("fffa734d")), true)
                .addData(new SimplePieInfo(30.0f, getColor("ff957de0")), true)
                .setDrawText(drawText)
                .setDuration(2000)
                .setInterpolator(new DecelerateInterpolator(2.5f))
                .setTextLineStrokeWidth(4)
                .setTextSize(12)
                .setPieRadiusScale(0.8f)
                .setOnPieSelectListener(new OnPieSelectListener<IPieInfo>() {
                    @Override
                    public void onSelectPie(@NonNull IPieInfo pieInfo, boolean isScaleUp) {
                        if (isScaleUp) {
                            Toast.makeText(MainActivity.this, pieInfo.getDesc(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        mAnimatedPieView.applyConfig(config);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimatedPieView.getConfig().setDrawStrokeOnly(!noDonuts).setDrawText(drawText);
                mAnimatedPieView.start();
            }
        });
        mNoDonuts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                noDonuts = isChecked;
            }
        });

        mDrawText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                drawText = isChecked;
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
