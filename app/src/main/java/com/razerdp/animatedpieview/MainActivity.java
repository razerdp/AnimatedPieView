package com.razerdp.animatedpieview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

public class MainActivity extends AppCompatActivity {

    private AnimatedPieView test;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mButton = findViewById(R.id.start);
        test = (AnimatedPieView) findViewById(R.id.test);
        AnimatedPieViewConfig config = new AnimatedPieViewConfig();
        config.setStartAngle(180)
                .addData(new SimplePieInfo(15.0f, Color.parseColor("#FFE94543"), ""))
                .addData(new SimplePieInfo(55.0f, Color.parseColor("#FFF2AE42"), ""))
                .addData(new SimplePieInfo(20.0f, Color.parseColor("#FF58B957"), ""))
                .addData(new SimplePieInfo(90.0f, Color.parseColor("#FF4284F4"), ""));
        test.applyConfig(config);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test.start();
            }
        });

    }
}
