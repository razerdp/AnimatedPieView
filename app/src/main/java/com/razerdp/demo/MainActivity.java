package com.razerdp.demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.razerdp.animatedpieview.R;
import com.razerdp.popup.PopupSetting;
import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.BasePieLegendsView;
import com.razerdp.widget.animatedpieview.DefaultCirclePieLegendsView;
import com.razerdp.widget.animatedpieview.DefaultPieLegendsView;
import com.razerdp.widget.animatedpieview.callback.OnPieLegendBindListener;
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
    private Button toViewPager;
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
        toViewPager = findViewById(R.id.btn_goto_viewpager);
        mAnimatedPieView = findViewById(R.id.animatedPieView);
        AnimatedPieViewConfig config = new AnimatedPieViewConfig();
        config.startAngle(30)
                /**
                 * not done below!
                 */
                .addData(new SimplePieInfo(61.08, getColor("1976D2"),"61.08"))

                .addData(new SimplePieInfo(38.92, getColor("64B5F6"), "38.92"))

                .animatePie(true)
                .drawText(true)
                .canTouch(true)
                .strokeMode(true)
                .strokeWidth(28)
                .duration(1000)
                .startAngle(0F)
                .textSize(32F)
                .textMargin(8)
                .splitAngle(0F)
                .interpolator(new DecelerateInterpolator())
                .autoSize(true)
                .pieRadiusRatio(1F)
                .animOnTouch(true)
                .focusAlphaType(AnimatedPieViewConfig.FOCUS_WITH_ALPHA_REV)
                .focusAlpha(100)
                .floatShadowRadius(4F)
                .floatExpandSize(4F);
        mAnimatedPieView.applyConfig(config);

        mAnimatedPieView.start();

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
                mPopupSetting
                        .setLegendsContainer((ViewGroup) findViewById(R.id.ll_legends))
                        .showPopupWindow(mAnimatedPieView.getConfig());
            }
        });

        toRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RecyclerActivity.class));
            }
        });
        toViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewPagerActivity.class));
            }
        });
    }

    private Bitmap resourceToBitmap(int resid) {
        Drawable drawable = getResources().getDrawable(resid);
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else {
            int w = drawable.getIntrinsicWidth();
            int h = drawable.getIntrinsicHeight();
            Bitmap.Config config =
                    drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                            : Bitmap.Config.RGB_565;
            Bitmap bitmap = Bitmap.createBitmap(w, h, config);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, w, h);
            drawable.draw(canvas);

            return bitmap;
        }
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
