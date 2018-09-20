AnimatedPieView
---
**一个好吃的甜甜圈？请问客官要啥口味捏-V-**
 
[**English Doc**](https://github.com/razerdp/AnimatedPieView/blob/master/README_EN.md)

[![jcenter](https://api.bintray.com/packages/razerdp/maven/AnimatedPieView/images/download.svg)](https://bintray.com/razerdp/maven/AnimatedPieView/_latestVersion)
[![license](https://img.shields.io/badge/license-Apache--2.0-blue.svg)](https://github.com/razerdp/AnimatedPieView/blob/master/LICENSE)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-AnimatedPieView-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/6507)
[![Api](https://img.shields.io/badge/Api-14%2B-green.svg)](https://img.shields.io/badge/Api-14%2B-green.svg)
[![Author](https://img.shields.io/badge/Author-razerdp-blue.svg)](https://github.com/razerdp) 

使用建议:
---

**1.2.0重构版正式发布，建议升级到该版本，1.2.0将整个项目由头到尾重构了一遍，更完善的注释和文档，以及针对一些命名和旧版本的bug统一修复，同时更加抽象，方便以后的功能添加。欢迎提交相关Bug~我会第一时间去解决问题的**

开发进度 （更新日志->[日志](https://github.com/razerdp/AnimatedPieView/blob/master/UPDATE_LOG.md)）
---

  > 如果您有别的需求，可以提交您的issue哦，当然，也可以直接修改源码-V-
  
  * ~~增加描述标签支持~~
  * ~~项目优化/重构，1.2.0发布~~
  * ~~允许alpha突出选中的甜甜圈~~
  * ~~允许甜甜圈之间含有间隔~~
  * ~~文字自适应点击动画位置~~
  * ~~文字描述动画~~
  * ~~有文字描述的甜甜圈~~
  * ~~点击事件回调的甜甜圈~~
  * ~~点击动画的甜甜圈~~
  * ~~可以点击的甜甜圈~~
  * ~~可以变成大饼的甜甜圈~~
  * ~~动画长大的甜甜圈~~

主要功能
---

| 描述        | 方法    |  预览  |
| --------   | :-----   | ---- |
| 动画生长        | --      |   ![pie_animation](https://github.com/razerdp/AnimatedPieView/blob/master/art/pie_animation.gif)    |
| 饼图/甜甜圈转换        | strokeMode(boolean)      |   ![pie_switch](https://github.com/razerdp/AnimatedPieView/blob/master/art/pie_switch.gif)    |
| 角度间隙       | splitAngle(float)      |   ![pie_split_angle](https://github.com/razerdp/AnimatedPieView/blob/master/art/pie_split_angle.gif)    |
| 绘制文字       | drawText(true)      |   ![pie_with_text](https://github.com/razerdp/AnimatedPieView/blob/master/art/pie_with_text.gif)    |
| 点击效果       | canTouch(true) / selectListener()    |   ![pie_click_effect](https://github.com/razerdp/AnimatedPieView/blob/master/art/pie_click_effect.gif)    |
| 焦点甜甜圈效果 (反向)      | focusAlphaType(<br>AnimatedPieViewConfig.FOCUS_WITH_ALPHA_REV,150<br>)    |   ![pie_click_with_focus_alpha_type_rev](https://github.com/razerdp/AnimatedPieView/blob/master/art/pie_click_with_focus_alpha_type_rev.gif)    |
| 焦点甜甜圈效果       | focusAlphaType(<br>AnimatedPieViewConfig.FOCUS_WITH_ALPHA,150<br>)    |   ![pie_click_with_focus_alpha_type](https://github.com/razerdp/AnimatedPieView/blob/master/art/pie_click_with_focus_alpha_type.gif)    |
| 甜甜圈标签       | IPieInfo.PieOption    |   ![pie_option](https://github.com/razerdp/AnimatedPieView/blob/master/art/pie_option.png)    |

依赖
---

添加依赖（请把{latestVersion}替换成上面的jcenter标签所示版本）
```xml
	dependencies {
	        implementation 'com.github.razerdp:AnimatedPieView:{latestVersion}'
	}
```

基本使用方式（简单的超乎想像）
---

**step 1：定义任意类实现IPieInfo接口（如果懒，可以使用SimplePieInfo）**

```java
public class Test implements IPieInfo {
    @Override
    public float getValue() {
        //这个数值将会决定其所占有的饼图百分比
        return 0.5f;
    }

    @Override
    public int getColor() {
        //该段甜甜圈的颜色，请返回@colorInt，不要返回@colorRes
        return Color.WHITE;
    }

    @Override
    public String getDesc() {
        //描述文字，可不返回
        return "这是一个测试";
    }
    
    @Nullable
    @Override
    public PieOption getPieOpeion() {
       //一些别的设置，比如标签
       return mPieOption;
    }
}
```

**step 2：定义config并配置就可以了**


```java
AnimatedPieView mAnimatedPieView = findViewById(R.id.animatedPieView);
AnimatedPieViewConfig config = new AnimatedPieViewConfig();
config.startAngle(-90)// 起始角度偏移
      .addData(new SimplePieInfo(30, getColor("FFC5FF8C"), "这是第一段"))//数据（实现IPieInfo接口的bean）
      .addData(new SimplePieInfo(18.0f, getColor("FFFFD28C"), "这是第二段"))
      ...(尽管addData吧)
      .duration(2000);// 持续时间
                
// 以下两句可以直接用 mAnimatedPieView.start(config); 解决，功能一致
mAnimatedPieView.applyConfig(config);
mAnimatedPieView.start();
        
```

进阶用法（所有配置都在config，and...相信我，我提供大多数配置，但日常用到的，其实不多哈哈）
---

```java
AnimatedPieViewConfig mConfig=mAnimatedPieView.getConfig();
mConfig.animOnTouch(true)// 点击事件是否播放浮现动画/回退动画（默认true）
       .addData(IPieInfo info, boolean autoDesc)// 添加数据，autoDesc：是否自动补充描述？（百分比）
       .floatExpandAngle(15f)// 点击后圆弧/扇形扩展的角度
       .floatShadowRadius(18f)// 点击后的阴影扩散范围
       .floatUpDuration(500)// 点击浮现动画时间
       .floatDownDuration(500)// 上一个浮现的圆弧回退的动画时间
       .floatExpandSize(15)// 点击后扇形放大数值,，只对饼图有效
       .strokeMode(true)// 是否只画圆弧【甜甜圈哈哈】，否则画扇形（默认true）
       .strokeWidth(15)// 圆弧（甜甜圈）宽度
       .duration(2500)// 动画时间
       .startAngle(-90f)// 开始的角度
       .selectListener(new OnPieSelectListener<IPieInfo>())//点击事件
       .drawText(true)// 是否绘制文字描述
       .textSize(12)// 绘制的文字大小
       .textMargin(8)// 绘制文字与导航线的距离
       .autoSize(true)// 自动测量甜甜圈半径
       .pieRadius(100)// 甜甜圈半径
       .pieRadiusRatio(0.8f)// 甜甜圈半径占比
       .guidePointRadius(2)// 设置描述文字的开始小点的大小
       .guideLineWidth(4)// 设置描述文字的指示线宽度
       .guideLineMarginStart(8)// 设置描述文字的指示线开始距离外圆半径的大小
       .textGravity(AnimatedPieViewConfig.ABOVE)// 设置描述文字方向 【
            -AnimatedPieViewConfig.ABOVE：文字将会在导航线上方绘制
            -AnimatedPieViewConfig.BELOW：文字在导航线下方绘制
            -AnimatedPieViewConfig.ALIGN：文字与导航线对齐
            -AnimatedPieViewConfig.ECTOPIC：文字在1、2象限部分绘制在线的上方，在3、4象限绘制在线的下方
       】
       .canTouch(true)// 是否允许甜甜圈点击放大
       .splitAngle(1)// 甜甜圈间隙角度
       .focusAlphaType(AnimatedPieViewConfig.FOCUS_WITH_ALPHA_REV,150)// 焦点甜甜圈的alpha表现形态及alpha削减值
       .interpolator(new DecelerateInterpolator())// 动画插值器
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

 [一起弄个甜甜圈吧](https://github.com/razerdp/Article/blob/master/%E4%B8%80%E8%B5%B7%E6%92%B8%E4%B8%AA%E7%94%9C%E7%94%9C%E5%9C%88.md)


LICENSE
---

[Apache-2.0](https://github.com/razerdp/AnimatedPieView/blob/master/LICENSE)
   
   

   



