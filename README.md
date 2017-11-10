# AnimatedPieView

#### // 一个好吃的甜甜圈？请问客官要啥口味捏-V-

[ ![Download](https://api.bintray.com/packages/razerdp/maven/AnimatedPieView/images/download.svg) ](https://bintray.com/razerdp/maven/AnimatedPieView/_latestVersion)
[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/razerdp/AnimatedPieView/blob/master/LICENSE)
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

#### 打赏（您的支持是我维护的动力-V-愿意的话，给个零食呗）
![wechat](https://github.com/razerdp/AnimatedPieView/blob/master/art/wechat.jpg)

#### 控件思路（书写中。。。地址会更新到这里的）

#### LICENSE
Apache-2.0
   
   

   



