AnimatedPieView
---
**AnimatedPieView is a simple and easy-to-use pie-chart view.**
 
[**Chinese Doc**](https://github.com/razerdp/AnimatedPieView/blob/master/README.md)

[![jcenter](https://api.bintray.com/packages/razerdp/maven/AnimatedPieView/images/download.svg)](https://bintray.com/razerdp/maven/AnimatedPieView/_latestVersion)
[![license](https://img.shields.io/badge/license-Apache--2.0-blue.svg)](https://github.com/razerdp/AnimatedPieView/blob/master/LICENSE)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-AnimatedPieView-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/6507)
[![Api](https://img.shields.io/badge/Api-14%2B-green.svg)](https://img.shields.io/badge/Api-14%2B-green.svg)
[![Author](https://img.shields.io/badge/Author-razerdp-blue.svg)](https://github.com/razerdp)
[![Backers on Open Collective](https://opencollective.com/AnimatedPieView/backers/badge.svg)](#backers)
[![Sponsors on Open Collective](https://opencollective.com/AnimatedPieView/sponsors/badge.svg)](#sponsors)

Suggestion:
---

**Version 1.2.0 is released, it is recommended to upgrade to this version.**

**This version was completely rewritten which has better comments and documentation.**

**Welcome to submit the Bug ~ I will be the first time to solve the problem.**

**Thanks**

### Update Log:

### 18/07/18 - ver 1.2.4
 - fix `autoSize`
 - support description label @see #[12](https://github.com/razerdp/AnimatedPieView/issues/12)

Develop Plan （Update Log->[Update Log](https://github.com/razerdp/AnimatedPieView/blob/master/UPDATE_LOG.md)）
-------------------------------------------------------------------------------------------------------------

  > No plan now....I'm eagerly waiting for your great suggestions.

  * ~~Support legends~~
  * ~~Support label for description text~~
  * ~~Project Optimization and Reconstruction, version 1.2.0 release~~
  * ~~Support alpha to highlight the selected pie~~ 
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
| description text label       | IPieInfo.PieOption    |   ![pie_option](https://github.com/razerdp/AnimatedPieView/blob/master/art/pie_option.png)    |

Download:
---

Use in gradle（please replace {latestVersion} to the download badge version）

```xml
	dependencies {
	        implementation 'com.github.razerdp:AnimatedPieView:{latestVersion}'
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
        
    @Nullable
    @Override
    public PieOption getPieOpeion() {
       // option for pie,example: descript label
       return mPieOption;
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
       .autoSize(true)// Auto fit chart radius
       .pieRadius(100)// Set chart radius
       .pieRadiusRatio(0.8f)// Chart's radius ratio for parent ViewGroup
       .guidePointRadius(2)// Chart's radius
       .guideLineWidth(4)// Text guide line stroke width
       .guideLineMarginStart(8)// Guide point margin from chart
       .textGravity(AnimatedPieViewConfig.ABOVE)// Text Gravity 【
            -AnimatedPieViewConfig.ABOVE：Text will be drawn above the guide line
            -AnimatedPieViewConfig.BELOW：Text will be drawn below the guide line
            -AnimatedPieViewConfig.ALIGN：Text will be drawn align to the guide line
            -AnimatedPieViewConfig.ECTOPIC：Text will be drawn above the line in the 1, 2 quadrants and below the line in the 3, 4 quadrants
       】
       .canTouch(true)// Whether to allow the pie click to enlarge
       .splitAngle(1)// Clearance angle
       .focusAlphaType(AnimatedPieViewConfig.FOCUS_WITH_ALPHA_REV)// Alpha change mode for selected pie
       .interpolator(new DecelerateInterpolator())// Set animation interpolator
       .focusAlpha(150) // Alpha for selected pie (depend on focusAlphaType)
       .legendsWith((ViewGroup) findViewById(R.id.ll_legends), new OnPieLegendBindListener<BasePieLegendsView>() {
                                       @Override
                                       public BasePieLegendsView onCreateLegendView(int position, IPieInfo info) {
                                           return position % 2 == 0 ?
                                                   DefaultPieLegendsView.newInstance(MainActivity.this)
                                                   : DefaultCirclePieLegendsView.newInstance(MainActivity.this);
                                       }

                                       @Override
                                       public boolean onAddView(ViewGroup parent, BasePieLegendsView view) {
                                           return false;
                                       }
                                   }); // Set a legends container to bind legends views
```

---

Api in PieOption：
---

you can decide your pie with create `PieOption`
```java
    // IPieInfo.java#getPieOption()
    @Nullable
    @Override
    public PieOption getPieOption() {
        return new PieOption()
                .setDefaultSelected(true) // Selected this pie by default
                .setIconHeight(50) // Text icon height
                .setIconWidth(50) // Text icon width
                .setIconScaledHeight(0.5f) // Text icon height scaling
                .setIconScaledWidth(0.5f) // Text icon width scaling
                .setLabelIcon(bitmap) // Text icon bitmap resource
                .setLabelPadding(5) // Icon and text distance
                .setLabelPosition(PieOption.NEAR_PIE); // Decide which position of the icon in the pie
    }
```

QQ Group：590777418
------------------

Welcome to join our QQ group for communication.

![](https://github.com/razerdp/FriendCircle/blob/master/qqgroup.png)

Buy me a coffee
--------------------------------------

| Wechat | AliPay |
| ---- | ---- | 
| ![](https://github.com/razerdp/FriendCircle/blob/master/wechat.png)      | ![](https://github.com/razerdp/FriendCircle/blob/master/alipay.png) |


Develop Passage:
----------------

 * [自定义控件——弄个甜甜圈吧（1）： 起源](http://www.jianshu.com/p/b2a2d82e107e)
 * [自定义控件——弄个甜甜圈吧（2）： 搭建](http://www.jianshu.com/p/562c525ff927)
 * [自定义控件——弄个甜甜圈吧（3）： 动画篇【生长动画】](http://www.jianshu.com/p/f7842a97cb3e)
 * 编写中...

LICENSE
---

Apache-2.0
   
   

## Contributors

This project exists thanks to all the people who contribute.
<a href="https://github.com/razerdp/AnimatedPieView/graphs/contributors"><img src="https://opencollective.com/AnimatedPieView/contributors.svg?width=890&button=false" /></a>


## Backers

Thank you to all our backers! 🙏 [[Become a backer](https://opencollective.com/AnimatedPieView#backer)]

<a href="https://opencollective.com/AnimatedPieView#backers" target="_blank"><img src="https://opencollective.com/AnimatedPieView/backers.svg?width=890"></a>


## Sponsors

Support this project by becoming a sponsor. Your logo will show up here with a link to your website. [[Become a sponsor](https://opencollective.com/AnimatedPieView#sponsor)]

<a href="https://opencollective.com/AnimatedPieView/sponsor/0/website" target="_blank"><img src="https://opencollective.com/AnimatedPieView/sponsor/0/avatar.svg"></a>
<a href="https://opencollective.com/AnimatedPieView/sponsor/1/website" target="_blank"><img src="https://opencollective.com/AnimatedPieView/sponsor/1/avatar.svg"></a>
<a href="https://opencollective.com/AnimatedPieView/sponsor/2/website" target="_blank"><img src="https://opencollective.com/AnimatedPieView/sponsor/2/avatar.svg"></a>
<a href="https://opencollective.com/AnimatedPieView/sponsor/3/website" target="_blank"><img src="https://opencollective.com/AnimatedPieView/sponsor/3/avatar.svg"></a>
<a href="https://opencollective.com/AnimatedPieView/sponsor/4/website" target="_blank"><img src="https://opencollective.com/AnimatedPieView/sponsor/4/avatar.svg"></a>
<a href="https://opencollective.com/AnimatedPieView/sponsor/5/website" target="_blank"><img src="https://opencollective.com/AnimatedPieView/sponsor/5/avatar.svg"></a>
<a href="https://opencollective.com/AnimatedPieView/sponsor/6/website" target="_blank"><img src="https://opencollective.com/AnimatedPieView/sponsor/6/avatar.svg"></a>
<a href="https://opencollective.com/AnimatedPieView/sponsor/7/website" target="_blank"><img src="https://opencollective.com/AnimatedPieView/sponsor/7/avatar.svg"></a>
<a href="https://opencollective.com/AnimatedPieView/sponsor/8/website" target="_blank"><img src="https://opencollective.com/AnimatedPieView/sponsor/8/avatar.svg"></a>
<a href="https://opencollective.com/AnimatedPieView/sponsor/9/website" target="_blank"><img src="https://opencollective.com/AnimatedPieView/sponsor/9/avatar.svg"></a>


   



