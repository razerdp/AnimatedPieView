package com.razerdp.widget.animatedpieview.callback;

import com.razerdp.widget.animatedpieview.BasePieLegendsView;
import com.razerdp.widget.animatedpieview.data.IPieInfo;

/**
 * Created by 大灯泡 on 2018/9/26.
 */
public interface OnPieLegendBindListener<V extends BasePieLegendsView> {

    V onCreateLegendView(IPieInfo info);

}
