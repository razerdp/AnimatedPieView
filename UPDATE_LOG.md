# AnimatedPieView更新日志

#### 17/11/30 - ver 1.1.1
 - `AnimatedPieViewConfig.AnimatedPieViewHelper#prepare()`计算值的时候采取绝对值，对负数容错
 - 默认插值器取`LinearInterpolator`，暂时去掉插值器定义方法，如果解决了再开放[issue#2](https://github.com/razerdp/AnimatedPieView/issues/2)
 - 去掉最小角度为1的实现（当数值为0的时候）

#### 17/11/29 - ver 1.1.0
 - 增加是否允许甜甜圈点击方法`setCanTouch(boolean)`
 - 增加甜甜圈间隙角度`setSplitAngle(float)`
 - 焦点甜甜圈的alpha表现形式`setFocusAlphaType()`
 - 更新demo使用例子

#### 17/11/25 - ver 1.0.8~1.0.9
 - 增加文字自适应动画位置

#### 17/11/24 - ver 1.0.7

 - 修复不断点击时绘制越来越卡的问题
    + 原因（猜测）：path没有重置，然而onDraw里面会不断的添加点的数据，造成每一段甜甜圈来回绘制，进而越来越卡

#### 17/11/23 - ver 1.0.6

 - 完成了指示线动画效果

#### 17/11/21 - ver 1.0.5:

  - 修复指示线折角空缺的问题
  - 增加文字错位参数`setDirectText(boolean)`
  - 增加文字指示线起始点大小设置`setTextPointRadius(float)`
  - 增加文字指示线线宽大小设置`setTextLineStrokeWidth(int)`
  - 增加文字指示线折线长度`setTextLineTransitionLength(int)`
  - 增加文字指示线开始距离外圆半径的大小`setTextLineStartMargin(float)`

#### 17/11/20 - ver 1.0.4：

  - 修改了方法名`getTextMarginBottom()`->`getTextMarginLine()`
  - 增加点击回调`OnPieSelectListener()`中反馈当前选中的甜甜圈是否处于上浮状态的标志位
  - 增加javadoc


