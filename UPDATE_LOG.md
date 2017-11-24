# AnimatedPieView更新日志

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


