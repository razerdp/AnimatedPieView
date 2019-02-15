package com.razerdp.demo;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.razerdp.animatedpieview.R;
import com.razerdp.popup.PopupSetting;
import com.razerdp.utils.SimpleObjectPool;
import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.callback.OnPieSelectListener;
import com.razerdp.widget.animatedpieview.data.IPieInfo;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;
import com.razerdp.widget.animatedpieview.utils.PLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by 大灯泡 on 2019/2/15.
 */
public class ViewPagerActivity extends AppCompatActivity implements View.OnClickListener {
    private Random random = new Random();
    private Button btnNormal;
    private ViewPager viewpager;
    private PopupSetting mPopupSetting;
    private AnimatedPieView selectedPieView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        initView();
    }

    private void initView() {
        PLog.setDebuggable(true);
        mPopupSetting = new PopupSetting(this);
        btnNormal = (Button) findViewById(R.id.btn_normal);
        viewpager = (ViewPager) findViewById(R.id.viewpager);


        btnNormal.setOnClickListener(this);

        mPopupSetting.setOnOkButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPieView != null) {
                    selectedPieView.start();
                }
            }
        });

        applyAdapter();
    }

    private void applyAdapter() {
        int textGravity[] = {AnimatedPieViewConfig.ABOVE, AnimatedPieViewConfig.BELOW, AnimatedPieViewConfig.ALIGN, AnimatedPieViewConfig.ECTOPIC};
        List<AnimatedPieViewConfig> configList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            AnimatedPieViewConfig config = new AnimatedPieViewConfig();
            config.startAngle(random.nextFloat())
                    .strokeMode(random.nextBoolean());
            //random data count
            int dataCount = Math.abs(random.nextInt(10));
            if (dataCount <= 0) {
                dataCount = 3;
            }
            for (int j = 0; j < dataCount; j++) {
                config.addData(new SimplePieInfo(random.nextDouble(), randomColor()), random.nextBoolean());
            }
            //split angle
            config.splitAngle(random.nextBoolean() ? (float) (Math.random() * 2) : 0)
                    .textSize(16)
                    .drawText(random.nextBoolean())
                    .duration(Math.abs(random.nextInt(5000)))
                    .focusAlpha(random.nextBoolean() ? AnimatedPieViewConfig.FOCUS_WITH_ALPHA : AnimatedPieViewConfig.FOCUS_WITH_ALPHA_REV)
                    .textGravity(textGravity[Math.abs(random.nextInt()) % textGravity.length]);
            configList.add(config);
        }
        viewpager.setPageMargin(-dip2px(this,48));
        viewpager.setOffscreenPageLimit(3);
        viewpager.setPageTransformer(true, new PagerTransformer());
        viewpager.setAdapter(new InnerAdapter(configList));
    }

    private int randomColor() {
        int red = random.nextInt(255);
        int green = random.nextInt(255);
        int blue = random.nextInt(255);
        return Color.argb(255, red, green, blue);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private class InnerAdapter extends PagerAdapter {
        private List<AnimatedPieViewConfig> mConfigs;
        private SimpleObjectPool<View> mViewSimpleObjectPool;

        public InnerAdapter(List<AnimatedPieViewConfig> configs) {
            mConfigs = configs;
            mViewSimpleObjectPool = new SimpleObjectPool<>(View.class, 3);
        }

        @Override
        public int getCount() {
            return mConfigs.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            final AnimatedPieViewConfig config = mConfigs.get(position);
            View content = mViewSimpleObjectPool.get();
            if (content == null) {
                content = View.inflate(container.getContext(), R.layout.item_pager_pie, null);
            }
            final TextView tvPieDesc = content.findViewById(R.id.tv_pie_desc);
            final TextView start = content.findViewById(R.id.start);
            final TextView setting = content.findViewById(R.id.setting);
            final AnimatedPieView pie = content.findViewById(R.id.pie);


            setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPieView = pie;
                    mPopupSetting.showPopupWindow(config);
                }
            });
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pie.start();
                }
            });
            config.selectListener(new OnPieSelectListener<IPieInfo>() {
                @Override
                public void onSelectPie(@NonNull IPieInfo pieInfo, boolean isFloatUp) {
                    tvPieDesc.setText(String.format(Locale.getDefault(),
                            "touch pie >>> {  value = %s;  color = %d;  desc = %s;  isFloatUp = %s; }",
                            pieInfo.getValue(), pieInfo.getColor(), pieInfo.getDesc(), isFloatUp));
                }
            });
            pie.start(config);
            container.addView(content, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            return content;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            if (object instanceof View) {
                container.removeView((View) object);
                mViewSimpleObjectPool.put((View) object);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_normal:
                finish();
                break;
        }
    }
}
