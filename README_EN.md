AnimatedPieView
---
**AnimatedPieView is a simple and easy-to-use pie-chart view.**
 
[**Chinese Doc**](https://github.com/razerdp/AnimatedPieView/blob/master/README.md)

[**Revision Log**](https://github.com/razerdp/AnimatedPieView/blob/master/REVISION_LOG.md)

[![jcenter](https://api.bintray.com/packages/razerdp/maven/AnimatedPieView/images/download.svg)](https://bintray.com/razerdp/maven/AnimatedPieView/_latestVersion)
[![license](https://img.shields.io/badge/license-Apache--2.0-blue.svg)](https://github.com/razerdp/AnimatedPieView/blob/master/LICENSE)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-AnimatedPieView-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/6507)
[![Api](https://img.shields.io/badge/Api-14%2B-green.svg)](https://img.shields.io/badge/Api-14%2B-green.svg)
[![Author](https://img.shields.io/badge/Author-razerdp-blue.svg)](https://github.com/razerdp) 

Suggestion:
---

**Version 1.2.0 is released, it is recommended to upgrade to this version.**

**This version was completely rewritten which has better comments and documentation.**

**Welcome to submit the Bug ~ I will be the first time to solve the problem.**

**Thanks**

#### Appendix:

**Deprecated methods/New methods comparison table : [**Revision Log**](https://github.com/razerdp/AnimatedPieView/blob/master/REVISION_LOG.md)**


Develop Plan （更新日志->[日志](https://github.com/razerdp/AnimatedPieView/blob/master/UPDATE_LOG.md)）
---

  > No plan now....I'm eagerly waiting for your great suggestions.
  
  * ~~Project Optimization and Reconstruction, version 1.2.0 release~~
  * ~~Support alpha to highlight the selected pie~~ ---------- done
  * ~~Support the pies to have a space between them~~
  * ~~TextField adapt animation position changed~~
  * ~~Text Line show animation~~
  * ~~Click callback~~
  * ~~Click effect~~
  * ~~Support click for every pie~~
  * ~~Support transform between pie-chart and ring-chart~~
  * ~~Support animation drawing~~

Features:
---

| Description        | Method    |  Preview  |
| --------   | :-----   | ---- |
| animation drawing        | --      |   ![pie_animation](https://github.com/razerdp/AnimatedPieView/blob/master/art/pie_animation.gif)    |
| transform between pie-chart and ring-chart        | strokeMode(boolean)      |   ![pie_switch](https://github.com/razerdp/AnimatedPieView/blob/master/art/pie_switch.gif)    |
| space between       | splitAngle(float)      |   ![pie_split_angle](https://github.com/razerdp/AnimatedPieView/blob/master/art/pie_split_angle.gif)    |
| description text       | drawText(true)      |   ![pie_with_text](https://github.com/razerdp/AnimatedPieView/blob/master/art/pie_with_text.gif)    |
| click effect       | canTouch(true) / selectListener()    |   ![pie_click_effect](https://github.com/razerdp/AnimatedPieView/blob/master/art/pie_click_effect.gif)    |
| alpha to highlight (reverse)      | focusAlphaType(<br>AnimatedPieViewConfig.FOCUS_WITH_ALPHA_REV,150<br>)    |   ![pie_click_with_focus_alpha_type_rev](https://github.com/razerdp/AnimatedPieView/blob/master/art/pie_click_with_focus_alpha_type_rev.gif)    |
| alpha to highlight       | focusAlphaType(<br>AnimatedPieViewConfig.FOCUS_WITH_ALPHA,150<br>)    |   ![pie_click_with_focus_alpha_type](https://github.com/razerdp/AnimatedPieView/blob/master/art/pie_click_with_focus_alpha_type.gif)    |

Download:
---

Use in gradle（please replace {latestVersion} to the download badge version）

```xml
	dependencies {
	        compile 'com.github.razerdp:AnimatedPieView:{latestVersion}'
	}
```

Simple Usage
---

**step 1：Define any class to implement the IPieInfo interface（you can also use SimplePieInfo）**

```java
public class Test implements IPieInfo {
    @Override
    public float getValue() {
        // This return value will determine the percentage
        return 0.5f;
    }

    @Override
    public int getColor() {
        // This return value will determine the cur-pie color，please return @colorInt，but not @colorRes
        return Color.WHITE;
    }

    @Override
    public String getDesc() {
        // description text, may not return
        return "这是一个测试";
    }
}
```

**step 2：New AnimatedPieViewConfig and configure it**


```java
AnimatedPieView mAnimatedPieView = findViewById(R.id.animatedPieView);
AnimatedPieViewConfig config = new AnimatedPieViewConfig();
config.startAngle(-90)// Starting angle offset
      .addData(new SimplePieInfo(30, getColor("FFC5FF8C"), "这是第一段"))//Data (bean that implements the IPieInfo interface)
      .addData(new SimplePieInfo(18.0f, getColor("FFFFD28C"), "这是第二段"))
      ...(addData so on)
      .duration(2000);// draw pie animation duration
                
// The following two sentences can be replace directly 'mAnimatedPieView.start (config); '
mAnimatedPieView.applyConfig(config);
mAnimatedPieView.start();
        
```

Api:
---

```java
AnimatedPieViewConfig mConfig=mAnimatedPieView.getConfig();
mConfig.animOnTouch(true)// Animation when selected pie（default:true）
       .addData(IPieInfo info, boolean autoDesc)// Add data (bean that implements the IPieInfo interface).autoDesc：automatically add description（e.g. 8.8%）
       .floatExpandAngle(15f)// Selected pie's angle of expansion
       .floatShadowRadius(18f)// Selected pie's shadow of expansion
       .floatUpDuration(500)// Selected pie's floating animation duration
       .floatDownDuration(500)// Last selected pie's float down animation duration
       .floatExpandSize(15)// Selected pie's size of expansion(only for pie-chart,not ring-chart)
       .strokeMode(true)// Whether to draw ring-chart(default:true)
       .strokeWidth(15)// Stroke width for ring-chart 
       .duration(2500)// Animation drawing duration
       .startAngle(-90f)// Starting angle offset
       .selectListener(new OnPieSelectListener<IPieInfo>())// Click callback
       .drawText(true)// Whether to draw a text description
       .textSize(12)// Text description size
       .textMargin(8)// Margin between text and guide line
       .pieRadiusRatio(0.8f)// 甜甜圈半径占比
       .guidePointRadius(2)// 设置描述文字的开始小点的大小
       .guideLineWidth(4)// 设置描述文字的指示线宽度
       .guideLineMarginStart(8)// 设置描述文字的指示线开始距离外圆半径的大小
       .textGravity(AnimatedPieViewConfig.ABOVE)// 设置描述文字方向 【
            -AnimatedPieViewConfig.ABOVE：文字将会在导航线上方绘制
            -AnimatedPieViewConfig.BELOW：文字在导航线下方绘制
            -AnimatedPieViewConfig.ALIGN：文字与导航线对齐
            -AnimatedPieViewConfig.DYSTOPY：文字在1、2象限部分绘制在线的上方，在3、4象限绘制在线的下方
       】
       .canTouch(true)// 是否允许甜甜圈点击放大
       .splitAngle(1)// 甜甜圈间隙角度
       .focusAlphaType(AnimatedPieViewConfig.FOCUS_WITH_ALPHA_REV,150)// 焦点甜甜圈的alpha表现形态及alpha削减值
       .focusAlpha(150);// 选中的/或者非选中的甜甜圈的alpha值（跟focusAlphaType挂钩）
```

---

交流群：590777418
---

因为目前还有朋友圈项目，建立了一个交流群，出于懒得管理那么多，所以如果有想法或者优化建议或者其他问题，欢迎加入“朋友圈交流群”

![](https://github.com/razerdp/FriendCircle/blob/master/qqgroup.png)

打赏（看在我那么努力维护的份上。。。给个零食呗~）
---

| 微信 |支付宝 | 
| ---- | ---- | 
| ![](https://github.com/razerdp/FriendCircle/blob/master/wechat.png)      | ![](https://github.com/razerdp/FriendCircle/blob/master/alipay.png) |


控件思路【按思路顺序更新】
---

 * [自定义控件——弄个甜甜圈吧（1）： 起源](http://www.jianshu.com/p/b2a2d82e107e)
 * [自定义控件——弄个甜甜圈吧（2）： 搭建](http://www.jianshu.com/p/562c525ff927)
 * [自定义控件——弄个甜甜圈吧（3）： 动画篇【生长动画】](http://www.jianshu.com/p/f7842a97cb3e)
 * 编写中...

LICENSE
---

Apache-2.0
   
   

   



