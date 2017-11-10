# AnimatedPieView

#### // 一个好吃的甜甜圈？请问客官要啥口味捏-V-

[![Download](https://api.bintray.com/packages/razerdp/maven/AnimatedPieView/images/download.svg)](https://bintray.com/razerdp/maven/AnimatedPieView/_latestVersion)
[![license](https://img.shields.io/badge/license-Apache--2.0-blue.svg)](https://github.com/razerdp/AnimatedPieView/blob/master/LICENSE)
![Progress](http://progressed.io/bar/59?title=dev_progress)   
[![Api](https://img.shields.io/badge/Api-14%2B-green.svg)](https://img.shields.io/badge/Api-14%2B-green.svg)
[![Author](https://img.shields.io/badge/Author-razerdp-blue.svg)](https://github.com/razerdp) 


##### 这是一个可爱的甜甜圈，也许是一个圆饼图，包含着动画和点击效果，目前仍在维护和不断加料中

--- 

##### 主要功能：
 
  - 动画展开
  
  ![image](https://github.com/razerdp/AnimatedPieView/blob/master/art/anima.gif)
  
   - 点击事件（ps...刚完成，回调还没给呢~）
   
   ![image](https://github.com/razerdp/AnimatedPieView/blob/master/art/click.gif)
   
   
##### 依赖

添加依赖（请把{latestVersion}替换成上面的jcenter标签所示版本）
```xml
	dependencies {
	        compile 'com.github.razerdp:AnimatedPieView:{latestVersion}'
	}
```

#### 基本使用方式（简单的超乎想像）：

##### ps:目前还没全部完成，后续会继续完善的


```java
        AnimatedPieView mAnimatedPieView = findViewById(R.id.animatedPieView);
        AnimatedPieViewConfig config = new AnimatedPieViewConfig();
        config.setStartAngle(-90)//起始角度偏移
                .addData(new SimplePieInfo(30, getColor("FFC5FF8C"), "这是第一段"))//数据（实现IPieInfo接口的bean）
                .addData(new SimplePieInfo(18.0f, getColor("FFFFD28C"), "这是第二段"))
                ...(尽管addData吧)
                .setDuration(2000)//持续时间
                .setInterpolator(new DecelerateInterpolator(2.5f));//插值器
        mAnimatedPieView.applyConfig(config);
        mAnimatedPieView.start();
```

##### 进阶用法（所有配置都在config，下面的只是展示用哦）：
```java
        AnimatedPieViewConfig mConfig=mAnimatedPieView.getConfig();
        mConfig.setTouchAnimation(true)//点击事件是否播放浮现动画/回退动画（默认true）
                        .setTouchExpandAngle(15f)//点击后圆弧/扇形扩展的角度
                        .setTouchShadowRadius(18f)//点击后的阴影扩散范围
                        .setTouchScaleDownDuration(500)//点击浮现动画时间
                        .setTouchScaleDownDuration(500)//上一个浮现的圆弧回退的动画时间
                        .setTouchScaleSize(15)//点击后圆弧/扇形放大数值
                        .setDrawStrokeOnly(true)//是否只画圆弧【甜甜圈哈哈】，否则画扇形（默认true）
                        .setStrokeWidth(15)//圆弧（甜甜圈）宽度
                        .setDuration(2500)//动画时间
                        .setInterpolator(new LinearInterpolator())//插值器
                        .setStartAngle(-90f)//开始的角度
                        .setConfig(new AnimatedPieViewConfig());//配置
```

#### 打赏（您的支持是我维护的动力-V-愿意的话，给个零食呗）
![wechat](https://github.com/razerdp/AnimatedPieView/blob/master/art/wechat.jpg)

#### 控件思路（书写中。。。地址会更新到这里的）

#### LICENSE
Apache-2.0
   
   

   



