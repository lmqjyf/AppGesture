# AppGesture 
Android 手势解锁

```
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <declare-styleable name="GestureView">
        <!--颜色设置-->
        <attr name="bigGraphicalColor" format="color"/>
        <attr name="bigSelectGraphicalColor" format="color"/>
        <attr name="smallGraphicalColor" format="color"/>
        <attr name="smallSelectGraphicalColor" format="color"/>
        <attr name="lineColor" format="color"/>
        <attr name="arrowColor" format="color"/>
        <!--是否需要显示箭头-->
        <attr name="arrowIsNeed" format="boolean"/>
        <!--是否跳过中间的点-->
        <attr name="isSkipMiddlePoint" format="boolean"/>
        <!--长度-->
        <attr name="arrowRadius" format="dimension"/>
        <attr name="bigGraphicalRadius" format="dimension"/>
        <attr name="smallGraphicalRadius" format="dimension"/>
        <!--最低选中点的个数-->
        <attr name="needSelectPointNumber" format="integer"/>
        <!--选中错误颜色-->
        <attr name="errorColor" format="color"/>
        <!--手势类型-->
        <attr name="gestureType" format="enum">
        	<!--检查手势-->
            <enum name="check_gesture" value="3"/>
            <!--设置手势-->
            <enum name="set_gesture" value="4"/>
        </attr>
    </declare-styleable>
</resources>
```

**支持自定义大圆，小圆，以及连接的线段**

|方法|参数  |
|--|--|
| setHandleBigGraphical | IGraphicalView接口 |
| setHandleSmallGraphical | IGraphicalView接口 |
| setHandleLineGraphical | IGraphicalView接口 |

```

/**
 * FileName：IGraphicalView
 * Create By：liumengqiang
 * Description：自定义需要实现的接口
 */
public interface IGraphicalView {

    /**
     * 初始化绘制
     * @param paint 画笔
     * @param canvas 画板
     * @param coordinateList 9个点的中心点坐标
     * @param attrsModel 属性对象
     */
    void onDrawInitView(Paint paint, Canvas canvas, List<ChildGraphicalView> coordinateList, AttrsModel attrsModel);

    /**
     * 
     * @param paint
     * @param canvas
     * @param selectMap 当前选中点的信息
     * @param attrsModel
     * @param TYPE 当前的类型（成功？错误？绘制中？）
     */
    void onDrawSelectView(Paint paint, Canvas canvas, LinkedHashMap<Integer, ChildGraphicalView> selectMap, AttrsModel attrsModel, int TYPE);
}
```

详细的自定义可见：HandleBigGraphical类。

**支持自定义手势结果处理器**
**支持自定义手势结果处理器**
**支持自定义手势结果处理器**



|方法|参数  |返回值  |
|--|--|--|
| setProcessor | IProcessor接口 |TYPE_COMPLETE，TYPE_ERROR， TYPE_RESET|

示例详见：CheckGestureProcessor类，设置手势示例。

```
public class CheckGestureProcessor implements IProcessor{


    private String gestureValue;

    public CheckGestureHandleData() {
    }

    /**
     *
     * @param selectIndexArray
     * @return
     */
    public int handleData(Set<Integer> selectIndexArray) {
        if(gestureValue.equals(getGestureValue(selectIndexArray))) { //密码一致
            return GestureViewType.TYPE_COMPLETE;
        }  else { // 密码不一致
            return GestureViewType.TYPE_ERROR;
        }
    }

    private String getGestureValue(Set<Integer> selectIndexArray) {
        String value = "";
        for(Integer integer : selectIndexArray) {
            value += integer;
        }
        return value;
    }

    @Override
    public void setGestureValue(String gestureValue) {
        if(gestureValue == null) {
            throw new IllegalArgumentException("输入参数GestureValue非法！");
        } else {
            this.gestureValue = gestureValue;
        }
    }
}
```
handleData方法内部处理自定需要自定义的逻辑。
然后设置自定义处理：

```
gestureView.setiHandleData(new CheckGestureProcessor());
```


**结果回调GestureListener**

|方法|描述  |
|--|--|
| onStart | 手势开始时触发 |
| onPointNumberChange | 移动过程中，选中点时触发|
| onComplete | 设置成功时触发 |
| onFailed | 小于最低设置的点个数触发 |
| valueDisaccord | 输入手势值不一样时触发|
| transitionStatus |过渡状态触发（需要多次输入校验，比如：设置密码） |

如下示例：

```
        gestureView.setGestureListener(new GestureListener() {
            @Override
            public void valueDisaccord() {
                Toast.makeText(CheckGestureActivity.this, "输入密码错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void transitionStatus() {

            }

            @Override
            public void onStart() {
                Log.e(TAG , "初始化完成");
            }

            @Override
            public void onPointNumberChange(int selectIndex) {
                Log.e(TAG, "点被选中");
            }

            @Override
            public void onComplete(List<Integer> list) {
                Toast.makeText(CheckGestureActivity.this, "校验成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed() {
                Toast.makeText(CheckGestureActivity.this, "个数小于四个", Toast.LENGTH_SHORT).show();
            }
        });
```

再附上一张整体效果Gif：
<img src="https://img-blog.csdnimg.cn/20191104182520558.gif" width='30%'></img>
 
