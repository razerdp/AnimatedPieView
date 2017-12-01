# AnimatedPieView

#### AnimatedPieView is a simple and easy-to-use pie-chart view.

[![jcenter](https://api.bintray.com/packages/razerdp/maven/AnimatedPieView/images/download.svg)](https://bintray.com/razerdp/maven/AnimatedPieView/_latestVersion)
[![license](https://img.shields.io/badge/license-Apache--2.0-blue.svg)](https://github.com/razerdp/AnimatedPieView/blob/master/LICENSE)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-AnimatedPieView-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/6507)
[![Api](https://img.shields.io/badge/Api-14%2B-green.svg)](https://img.shields.io/badge/Api-14%2B-green.svg)
[![Author](https://img.shields.io/badge/Author-razerdp-blue.svg)](https://github.com/razerdp) 

--- 

### Developement Plan：
  > No plan now....we are looking forward to your interesting ideas on issue
  
  - Support alpha animation on touch  ---------- **done**
  - Support setting clearance angle  ---------- **done**
  - Fit textField position itself during animation  ---------- **done**
  - Support setting description and show for every paragraph  ---------- **done**
  - Click effect  ---------- **done**
  - Click callback  ---------- **done**
  - Support transform between pie-chat and ring-chat  ---------- **done**
  - Support animation draw  ---------- **done**

### Developement Detail Log（Chinese language only）

[UPDATE_LOG](https://github.com/razerdp/AnimatedPieView/blob/master/UPDATE_LOG.md)

### Feature：
| Desc        | Method    |  Preview  |
| --------   | :-----   | ---- |
| Animation draw        | --      |   ![pie_animation](https://github.com/razerdp/AnimatedPieView/blob/master/art/pie_animation.gif)    |
| Transform pie-chat and ring-chat        | setDrawStrokeOnly(boolean)      |   ![pie_switch](https://github.com/razerdp/AnimatedPieView/blob/master/art/pie_switch.gif)    |
| clearance angle       | setSplitAngle(float)      |   ![pie_split_angle](https://github.com/razerdp/AnimatedPieView/blob/master/art/pie_split_angle.gif)    |
| Draw description text       | setDrawText(true)      |   ![pie_with_text](https://github.com/razerdp/AnimatedPieView/blob/master/art/pie_with_text.gif)    |
| Click effect       | setCanTouch(true) / setOnPieSelectListener()    |   ![pie_click_effect](https://github.com/razerdp/AnimatedPieView/blob/master/art/pie_click_effect.gif)    |
| Alpha animation on focused(rev)      | setFocusAlphaType(<br>AnimatedPieViewConfig.FOCUS_WITH_ALPHA_REV,150<br>)    |   ![pie_click_with_focus_alpha_type_rev](https://github.com/razerdp/AnimatedPieView/blob/master/art/pie_click_with_focus_alpha_type_rev.gif)    |
| Alpha animation on focused       | setFocusAlphaType(<br>AnimatedPieViewConfig.FOCUS_WITH_ALPHA,150<br>)    |   ![pie_click_with_focus_alpha_type](https://github.com/razerdp/AnimatedPieView/blob/master/art/pie_click_with_focus_alpha_type.gif)    |

### Download

Use in gradle（please replace {latestVersion} to the donload badge verision）
```xml
	dependencies {
	        compile 'com.github.razerdp:AnimatedPieView:{latestVersion}'
	}
```

#### Usage（usually）：

```java
        AnimatedPieView mAnimatedPieView = findViewById(R.id.animatedPieView);
        AnimatedPieViewConfig config = new AnimatedPieViewConfig();
        config.setStartAngle(-90)// set the start offset angle
                .addData(new SimplePieInfo(30, getColor("FFC5FF8C"), "这是第一段"))//bind your data which must implements IPieInfo
                .addData(new SimplePieInfo(18.0f, getColor("FFFFD28C"), "这是第二段"))
                ...(尽管addData吧)
                .setDuration(2000)// animation draw duration
        mAnimatedPieView.applyConfig(config);
        mAnimatedPieView.start();
```

### Api：
```java
        AnimatedPieViewConfig mConfig=mAnimatedPieView.getConfig();
        mConfig.setTouchAnimation(true)// can play animation on touch（default : true）
                        .addData(IPieInfo info, boolean autoDesc)// bind your data which must implements IPieInfo. @params autoDesc：auto fill desc（etc. 3%）
                        .setTouchExpandAngle(15f)// expand angle for pie when touch
                        .setTouchShadowRadius(18f)// shadow radius for pie when touch
                        .setTouchScaleUpDuration(500)// scale-up animation duration for focused paragraph
                        .setTouchScaleDownDuration(500)// scale-down animation duration for focused paragraph
                        .setTouchScaleSize(15)// scale size for focused paragraph,only support for pie-chart
                        .setDrawStrokeOnly(true)// draw ring-chat or pie-chat（default : true）
                        .setStrokeWidth(15)// stroke width for ring-chat
                        .setDuration(2500)// animation draw duration
                        .setStartAngle(-90f)// the start offset angle
                        .setOnPieSelectListener(new OnPieSelectListener<IPieInfo>())//click callback
                        .setDrawText(true)// can draw description (default : true)
                        .setTextSize(12)// description text size
                        .getTextMarginLine(8)// the margin distance between description text and indicating line 
                        .setPieRadiusScale(0.8f)// the percent for chat's radius and the view's size
                        .setTextPointRadius(2)// size of description text indicating line point
                        .setTextLineStrokeWidth(4)// width of description text indicating line
                        .setTextLineTransitionLength(8)// the length of the text indicating line at the corner
                        .setTextLineStartMargin(8)// the 
                        .setDirectText(true)// set description text is unified direction 【
                            -true：the text will be drawn on the description line
                            -false：the text is drawn above the line in the 1, 2 quadrants and below the line in the 3, 4 quadrants
                        】
                        .setCanTouch(true)// allow touch to scale-up
                        .setSplitAngle(1)// clearance angle
                        .setFocusAlphaType(AnimatedPieViewConfig.FOCUS_WITH_ALPHA_REV,150)// alpha animation on focused
                        .setStrokePaintCap(Paint.Cap.ROUND)// set the cap for the paint
                        .setConfig(new AnimatedPieViewConfig());// apply another config to current config（this will override all option,just show this api here,Not necessary to achieve）
```

---

### Donate（wechat support）
![wechat](https://github.com/razerdp/AnimatedPieView/blob/master/art/wechat.jpg)

### Development process for the project(Chinese language)
 - [自定义控件——弄个甜甜圈吧（1）： 起源](http://www.jianshu.com/p/b2a2d82e107e)
 - [自定义控件——弄个甜甜圈吧（2）： 搭建](http://www.jianshu.com/p/562c525ff927)
 - [自定义控件——弄个甜甜圈吧（3）： 动画篇【生长动画】](http://www.jianshu.com/p/f7842a97cb3e)
 - Coming soon...

### LICENSE
Apache-2.0
   
   

   



