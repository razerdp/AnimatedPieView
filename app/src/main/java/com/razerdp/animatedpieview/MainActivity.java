package com.razerdp.animatedpieview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private AnimatedPieView test;
    private Button mButton;
    private final Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mButton = findViewById(R.id.start);
        test = findViewById(R.id.test);
        AnimatedPieViewConfig config = new AnimatedPieViewConfig();
        config.setStartAngle(-1800.68f)
                .addData(new SimplePieInfo(1.0f, randomColor(), ""))
                .addData(new SimplePieInfo(18.0f, randomColor(), ""))
                .addData(new SimplePieInfo(123.0f, randomColor(), ""))
                .addData(new SimplePieInfo(87.0f, randomColor(), ""))
                .addData(new SimplePieInfo(15.0f, randomColor(), ""))
                .addData(new SimplePieInfo(55.0f, randomColor(), ""))
                .addData(new SimplePieInfo(20.0f, randomColor(), ""))
                .addData(new SimplePieInfo(90.0f, randomColor(), ""));
        test.applyConfig(config);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test.start();
            }
        });

    }

    private int randomColor() {
        int red = random.nextInt(255);
        int green = random.nextInt(255);
        int blue = random.nextInt(255);
        return Color.argb(255, red, green, blue);
    }
}
