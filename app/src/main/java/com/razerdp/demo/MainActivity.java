package com.razerdp.demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
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
                /**
                 * not done below!
                 */
                .addData(new SimplePieInfo(0.11943538617599236, getColor("FF446767")).setLabel(resourceToBitmap(R.mipmap.ic_test_1)), true)
                .addData(new SimplePieInfo(0.41780274681129415, getColor("FFFFD28C"),"测试一下~").setLabel(resourceToBitmap(R.mipmap.ic_test_2)), false)
                .addData(new SimplePieInfo(0.722165651192247, getColor("FFbb76b4")).setLabel(resourceToBitmap(R.mipmap.ic_test_3)), true)
//                .addData(new SimplePieInfo(0.11943538617599236, getColor("FF446767")), true)
//                .addData(new SimplePieInfo(0.41780274681129415, getColor("FFFFD28C")), true)
//                .addData(new SimplePieInfo(0.722165651192247, getColor("FFbb76b4")), true)
                .addData(new SimplePieInfo(0.9184314356136125, getColor("FFFFD28C"), "长文字test").setLabel(resourceToBitmap(R.mipmap.ic_test_4)), false)
                .addData(new SimplePieInfo(0.6028910840057398, getColor("ff2bbc80")).setLabel(resourceToBitmap(R.mipmap.ic_test_5)), true)
                .addData(new SimplePieInfo(0.6449620647212785, getColor("ff8be8ff")), true)
                .addData(new SimplePieInfo(0.058853315195452116, getColor("fffa734d")), true)
                .addData(new SimplePieInfo(0.6632297717331086, getColor("ff957de0")), true)
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
                .textGravity(AnimatedPieViewConfig.ABOVE)
                .interpolator(new DecelerateInterpolator());
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
