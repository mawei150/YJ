package com.example.util.advanced;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;


/**
 * @author MW
 * @date 2019/3/22
 * <p>
 * 描述：  进阶   自定义View之圆形头像
 */

public class CircleImageView extends android.support.v7.widget.AppCompatImageView {


/*



    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
    }

    //onDraw方法这里是绘制圆形图片
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Drawable drawable = getDrawable();//我理解为拿到图片
        if (null != drawable) {//如果图片不为空
      */
    /*       Bitmap位图包括像素以及长、宽、颜色等描述信息。长宽和像素位数是用来描述图片的
     * 位图可以理解为一个画架，把图放到上面然后可以对图片做一些列的处理。
     *//*

            //Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            int w = drawable.getIntrinsicWidth();
            int h = drawable.getIntrinsicHeight();
            Bitmap bitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);

            Bitmap b=getCircleBitmap(bitmap, 14);

            final Rect rectSrc = new Rect(0, 0, b.getWidth(), b.getHeight());
            final Rect rectDest = new Rect(0,0,getWidth(),getHeight());
            paint.reset();//重置paint
            canvas.drawBitmap(b, rectSrc, rectDest, paint);
        }else{
            super.onDraw(canvas);
        }

    }


  */
    /*  *
     * 获取圆形图片方法
     *
     * @param bitmap
     * @param pixels
     * @return Bitmap
     * @author caizhiming*//*


    private Bitmap getCircleBitmap(Bitmap bitmap, int pixels) {
       // ARGB指的是一种 色彩模式，里面A代表Alpha，R表示red，G表示green，B表示blue. 位图位数越高代表其可以存储的颜色信息越多，当然图像也就越逼真
        //参数1.位图的宽度  参数2.位图的高度  参数3:ARGB_8888——代表32位ARGB位图
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);

     */
    /*   Canvas 表示一块画布  你可以在上面画你想画的东西
     *  Canvas(Bitmap bitmap):以bitmap对象创建一个画布，
     *  则将内容都绘制在bitmap上，bitmap不得为null;
     * *//*

        Canvas canvas = new Canvas(output);//以bitmap对象创建一个画布，Canvas(Bitmap bitmap):以bitmap对象创建一个画布，则将内容都绘制在bitmap上，bitmap不得为null;
        final  int color=0xff424242;


        //Rect类主要用于表示坐标系中的一块矩形区域，并可以对其做一些简单操作
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        paint.setAntiAlias(true);//抗锯齿  使锯齿痕迹不那么明显
        canvas.drawARGB(0, 0, 0, 0);//画布的颜色分量
        paint.setColor(color);

        int x=bitmap.getWidth();

       */
    /* *
     *使用指定的绘图绘制指定的圆。如果半径<=0，则没有
     *绘制。圆将根据绘图中的样式进行填充或边框。
     *
     *@参数cx待绘制圆环中心的x坐标
     *@参数：待绘制圆环中心的Y坐标
     *@参数半径要绘制的圆环半径
     *@参数绘制用于绘制圆的绘制*//*


        canvas.drawCircle(x / 2, x / 2, x / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));//设置或清除传输模式对象
        canvas.drawBitmap(bitmap, rect, rect, paint);//这个方法  第一个参数是图片原来的大小，第二个参数是 绘画该图片需显示多少。也就是说你想绘画该图片的某一些地方，而不是全部图片，第三个参数表示该图片绘画的位置

        return  output;
    }
*/

    private float width;
    private float height;
    private float radius;//半径
    private Paint paint;//画笔  可以画几何图形、文本、bitmap
    private Matrix matrix;

    /* 4个构造函数
    1.在代码中直接new一个View实例的时候,会调用第一个构造函数.这个没有任何争议.
    2.在xml布局文件中调用Custom View的时候,会调用第二个构造函数.这个也没有争议.
    3. 在xml布局文件中调用Custom View,并且Custom View标签中还有自定义属性时,这里调用的还是第二个构造函数.
       也就是说,系统默认只会调用Custom View的前两个构造函数,至于第三个构造函数的调用,
       通常是我们自己在构造函数中主动调用的（例如,在第二个构造函数中调用第三个构造函数）
！*/


    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);   //设置抗锯齿   使锯齿痕迹不那么明显
        matrix = new Matrix();      //初始化缩放矩阵
    }


    /*  *
     * 测量控件的宽高，并获取其内切圆的半径*/

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        radius = Math.min(width, height) / 2;
    }

    /* Bitmap位图包括像素以及长、宽、颜色等描述信息。长宽和像素位数是用来描述图片的
     * 位图可以理解为一个画架，把图放到上面然后可以对图片做一些列的处理。
     */

    //onDraw方法这里是绘制圆形图片
    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();

        /*   Canvas 表示一块画布  你可以在上面画你想画的东西
         *  Canvas(Bitmap bitmap):以bitmap对象创建一个画布，
         *  则将内容都绘制在bitmap上，bitmap不得为null;
         * */

        if (drawable == null) {//如果图片 为空  执行父类方法 返回
            super.onDraw(canvas);
            return;
        }
        if (drawable instanceof BitmapDrawable) {//如果左边对象  是右边类的实例
            paint.setShader(initBitmapShader((BitmapDrawable) drawable));//将着色器设置给画笔
            canvas.drawCircle(width / 2, height / 2, radius, paint);//使用画笔在画布上画圆 对drawCircle的分析，四个参数：
           /* 1.圆心的X坐标
            2.圆心的Y坐标
            3.圆的半径
            4.画笔*/
            return;
        }
        super.onDraw(canvas);
    }


    /*   *
     * 获取ImageView中资源图片的Bitmap，利用Bitmap初始化图片着色器,通过缩放矩阵将原资源图片缩放到铺满整个绘制区域，避免边界填充
     */
    private BitmapShader initBitmapShader(BitmapDrawable drawable) {
        Bitmap bitmap = drawable.getBitmap();
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = Math.max(width / bitmap.getWidth(), height / bitmap.getHeight());
        matrix.setScale(scale, scale);//将图片宽高等比例缩放，避免拉伸
        bitmapShader.setLocalMatrix(matrix);
        return bitmapShader;
    }

}