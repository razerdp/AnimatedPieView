package com.razerdp.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class RecyclerActivity extends AppCompatActivity implements View.OnClickListener {

    private Random random = new Random();

    private Button btnNormal;
    private RecyclerView recycler;
    private PopupSetting mPopupSetting;
    private AnimatedPieView selectedPieView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        initView();
    }

    private void initView() {
        PLog.setDebuggable(true);

        mPopupSetting = new PopupSetting(this);

        btnNormal = (Button) findViewById(R.id.btn_normal);
        btnNormal.setOnClickListener(this);
        recycler = (RecyclerView) findViewById(R.id.recycler);

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
        recycler.setItemAnimator(null);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(new InnerAdapter(configList));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_normal:
                finish();
                break;
        }
    }


    private class InnerAdapter extends RecyclerView.Adapter<InnerViewHolder> {
        private List<AnimatedPieViewConfig> mConfigs;

        public InnerAdapter(List<AnimatedPieViewConfig> configs) {
            mConfigs = configs;
        }

        @Override
        public InnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new InnerViewHolder(View.inflate(RecyclerActivity.this, R.layout.item_rv, null));
        }

        @Override
        public void onBindViewHolder(InnerViewHolder holder, int position) {
            holder.bindData(mConfigs.get(position));
        }

        @Override
        public int getItemCount() {
            return mConfigs.size();
        }
    }

    private class InnerViewHolder extends RecyclerView.ViewHolder {
        TextView tvPieDesc;
        AnimatedPieView pie;
        TextView start;
        TextView setting;

        public InnerViewHolder(View itemView) {
            super(itemView);
            this.tvPieDesc = (TextView) itemView.findViewById(R.id.tv_pie_desc);
            this.pie = (AnimatedPieView) itemView.findViewById(R.id.pie);
            this.start = (TextView) itemView.findViewById(R.id.start);
            this.setting = (TextView) itemView.findViewById(R.id.setting);
        }

        public void bindData(final AnimatedPieViewConfig config) {
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
        }
    }
}
