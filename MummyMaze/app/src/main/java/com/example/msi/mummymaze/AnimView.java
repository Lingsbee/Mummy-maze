package com.example.msi.mummymaze;
import java.io.InputStream;
import java.util.Stack;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.SoundPool;
import android.os.Bundle;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * xuanyusong@gmail.com
 * @author 宣雨松
 *
 */
public class AnimView extends View {
    /**
     * 向下移动动画
     **/
    public final static int ANIM_DOWN = 0;
    /**
     * 向左移动动画
     **/
    public final static int ANIM_LEFT = 1;
    /**
     * 向右移动动画
     **/
    public final static int ANIM_RIGHT = 2;
    /**
     * 向上移动动画
     **/
    public final static int ANIM_UP = 3;
    /**
     * 动画的总数量
     **/
    public final static int ANIM_COUNT = 5;
    private int manx = 0;
    private int many = 0;
    private int mmyx;
    private int mmyy;
    private boolean Play;
    private OnChangeListener changeListener;
    Stack<Integer> Manstep;
    Stack<Integer> Mmystep;
    Stack<Integer> ManstepRec;
    Stack<Integer> MmystepRec;

    int[][][] qiang = {
            {
                    {1100, 0, 100, 110, 1100, 110},
                    {1000, 10, 1000, 1, 0, 10},
                    {1000, 0, 0, 100, 0, 10},
                    {1000, 1, 1, 0, 0, 11},
                    {1010, 1100, 100, 0, 1, 110},
                    {1011, 1001, 1, 1, 101, 11}
            },
            {
                    {1100, 100, 101, 100, 1, 110},
                    {1010, 1000, 100, 0, 101, 10},
                    {1001, 0, 1, 10, 1101, 10},
                    {1110, 1000, 100, 1, 100, 10},
                    {1010, 1010, 1001, 100, 0, 10},
                    {1001, 1, 111, 1001, 1, 11}
            },
            {
                    {1100, 101, 100, 101, 100, 110},
                    {1000, 111, 1001, 101, 0, 10},
                    {1000, 101, 100, 100, 11, 1010},
                    {1010, 1110, 1001, 0, 100, 11},
                    {1000, 1, 101, 1, 1, 110},
                    {1001, 101, 110, 1101, 101, 11}
            }
            ,{
            {1100,1,101,100,100,110},
            {1000,0,110,1000,1,10},
            {1010,1000,0,10,1100,0},
            {1000,1,0,0,0,11},
            {1000,110,1000,0,10,1110},
            {1001,1,1,1,1,11}},
   {
            {1110,1100,100,100,100,10},
            {1000,0,0,0,0,10},
            {1011,1000,0,0,0,11},
            {1100,0,0,0,0,110},
            {1000,0,0,0,0,10},
            {1001,1,1,1,1,11}},
            {
            {110,1100,100,100,100,110},
            {1000,1,0,1,0,10},
            {1000,110,1001,111,1000,10},
            {1000,1,110,1110,1001,10},
            {0,111,1001,11,1100,10},
            {1001,101,101,101,1,11}},
    {
            {1100,101,100,100,100,110},
            {1000,101,0,10,1000,11},
            {1000,110,1000,11,1000,110},
            {1000,0,0,100,10,1010},
            {1000,0,0,0,0,10},
            {1001,1,1,1,1,1}},
  {
            {1100,100,100,100,100,110},
            {1010,1000,0,1,0,11},
            {1011,1010,1000,100,10,1110},
            {1110,1010,1000,11,1000,10},
            {1000,0,1,111,1000,10},
            {1001,1,101,101,1,10}},
    {
            {1100,101,100,100,110,1110},
            {1000,111,1000,1,10,1010},
            {1000,110,1000,110,1010,1010},
            {1001,0,0,0,10,1010},
            {1101,0,0,0,0,10},
            {101,1,1,11,1001,11}},

   {
            {1110,1110,1101,100,100,111},
            {1010,1000,111,1010,1000,110},
            {1010,1001,101,0,10,1011},
            {1000,100,101,10,1010,1100},
            {1000,1,100,11,1000,10},
            {1001,101,1,101,1,11}
}};
    private int[][] Man;
    private int[][] Mmy;
    private int[][] qiangbi;
    private int Manx;
    private int Many;
    private int Mmyx;
    private int Mmyy;
    private int moveF;
    private int RecmoveF;
    private int level;
    private boolean move;
    private RelativeLayout layout;
    Animation mHeroAnim[] = new Animation[ANIM_COUNT];
    Animation mTestAnim[] = new Animation[ANIM_COUNT];

    Paint mPaint = null;


    private int mdirection = 4;
    private boolean onclick = false;

    //当前绘制动画状态ID
    int mAnimationState = 0;
    private Rect mDestRect1, mDestRect2;
    //上一篇已经介绍了地图块的绘制 这里我暂时先写成一张PNG
    Bitmap mMapImage = null;
    private OnOverListener OverListener;
    private OnCancelListener cancelListener;

    /**
     * 构造方法
     *
     * @param context
     */

    public AnimView(Context context) {
        this(context, null);
    }

    public AnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
//		setOnClickListener(this);
    }

    public AnimView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);
        mPaint = new Paint();
        //这里可以用循环来处理总之我们需要把动画的ID传进去

        //利用程序来切割图片
        Bitmap testmap = ReadBitMap(context, R.drawable.explorer);
        Bitmap[][] bitmap = new Bitmap[ANIM_COUNT][ANIM_COUNT];
        int tileWidth = testmap.getWidth() / ANIM_COUNT;
        int tileHeight = testmap.getHeight() / 4;
        int i = 0, x = 0, y = 0;
        for (i = 0; i < ANIM_COUNT; i++) {
            y = 0;
            bitmap[ANIM_UP][i] = BitmapClipBitmap(testmap, x, y, tileWidth, tileHeight);
            y += tileHeight;
            bitmap[ANIM_RIGHT][i] = BitmapClipBitmap(testmap, x, y, tileWidth, tileHeight);
            y += tileHeight;
            bitmap[ANIM_DOWN][i] = BitmapClipBitmap(testmap, x, y, tileWidth, tileHeight);
            y += tileHeight;
            bitmap[ANIM_LEFT][i] = BitmapClipBitmap(testmap, x, y, tileWidth, tileHeight);
            x += tileWidth;
        }
        mHeroAnim[ANIM_DOWN] = new Animation(context, bitmap[ANIM_DOWN], true);
        mHeroAnim[ANIM_LEFT] = new Animation(context, bitmap[ANIM_LEFT], true);
        mHeroAnim[ANIM_RIGHT] = new Animation(context, bitmap[ANIM_RIGHT], true);
        mHeroAnim[ANIM_UP] = new Animation(context, bitmap[ANIM_UP], true);
        testmap = ReadBitMap(context, R.drawable.mummy);
        bitmap = new Bitmap[ANIM_COUNT][ANIM_COUNT];
        tileWidth = testmap.getWidth() / ANIM_COUNT;
        tileHeight = testmap.getHeight() / 4;
        i = 0;
        x = 0;
        y = 0;
        for (i = 0; i < ANIM_COUNT; i++) {
            y = 0;
            bitmap[ANIM_UP][i] = BitmapClipBitmap(testmap, x, y, tileWidth, tileHeight);
            y += tileHeight;
            bitmap[ANIM_RIGHT][i] = BitmapClipBitmap(testmap, x, y, tileWidth, tileHeight);
            y += tileHeight;
            bitmap[ANIM_DOWN][i] = BitmapClipBitmap(testmap, x, y, tileWidth, tileHeight);
            y += tileHeight;
            bitmap[ANIM_LEFT][i] = BitmapClipBitmap(testmap, x, y, tileWidth, tileHeight);
            x += tileWidth;
        }
        mTestAnim[ANIM_DOWN] = new Animation(context, bitmap[ANIM_DOWN], true);
        mTestAnim[ANIM_LEFT] = new Animation(context, bitmap[ANIM_LEFT], true);
        mTestAnim[ANIM_RIGHT] = new Animation(context, bitmap[ANIM_RIGHT], true);
        mTestAnim[ANIM_UP] = new Animation(context, bitmap[ANIM_UP], true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void initialize(int level) {
        this.level = level;
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth() / 8;
        qiangbi = qiang[level];
        Man = new int[][]{{5, 1}, {2, 2}, {1, 1},{0,1},{2,4},{1,4 },{1,3},{1,4},{2,1},{2,2}};
        manx = Man[level][0] * (width + width / 30);
        many = (Man[level][1]) * (width + width / 5);
        Manx = Man[level][0];
        Many = Man[level][1];
        Manstep=new Stack<Integer>();
        ManstepRec=new Stack<Integer>();
        Manstep.push(Many);
        Manstep.push(Manx);
        if (Manx>3)
            manx -= Manx *width / 30;
        mDestRect1 = new Rect(manx, many, manx + width, many + width);
        Mmy = new int[][]{{1, 5}, {0, 0}, {1, 3},{1,5},{3,1},{1,1},{5,2},{5,5},{4,4},{5,3}};
        mmyx = Mmy[level][0] * (width+ width / 30);
        mmyy = Mmy[level][1] * (width + width / 5);
        Mmyx = Mmy[level][0];
        Mmyy = Mmy[level][1];
        Mmystep=new Stack<Integer>();
        MmystepRec=new Stack<Integer>();
        Mmystep.push(Mmyy);
        Mmystep.push(Mmyx);
        if (Mmyx>3)
            mmyx -= Mmyx *width / 30;
        mDestRect2 = new Rect(mmyx, mmyy, mmyx + width, mmyy + width);
        mdirection = 1;
        moveF = 0;
        RecmoveF = 0;
        mAnimationState = 0;
        onclick = false;
        Play=false;
        move=true;
        invalidate();
    }

    @Override

    protected void onDraw(Canvas canvas) {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth() / 8;
        canvas.translate(width, width + width / 3);
        canvas.save();
        canvas.restore();
        /**根据按键更新显示动画**/
        if (onclick) {
            if(!Manstep.empty())
            {
                    ManstepRec.push(Manstep.pop());
                    ManstepRec.push(Manstep.pop());
            } else
            {
                Manstep.push(Many);
                Manstep.push(Manx);
            }
            if(!Mmystep.empty())
            {
                MmystepRec.push(Mmystep.pop());
                MmystepRec.push(Mmystep.pop());
            }
            else
            {
                Mmystep.push(Mmyy);
                Mmystep.push(Mmyx);
            }
            switch (mdirection) {
                case 0:
                    if (qiangbi[Many][Manx] % 1000 / 100 == 0) {
                        move=true;
                        mAnimationState = ANIM_UP;
                        for (int i = 0; i < ANIM_COUNT; i++) {
                            many = many - width / ANIM_COUNT / 5 - width / ANIM_COUNT;
                            mDestRect1 = new Rect(manx, many, manx + width, many + width);
                            mHeroAnim[mAnimationState].DrawAnimation(canvas, mPaint, mDestRect1);
                        }
                        Many -= 1;
                        if(GameOver()==1)
                            break;
                        for (int j = 0; j < 2; j++) {
                            moveF = this.MmyMove();
                            for (int i = 0; i < ANIM_COUNT; i++) {
                                switch (moveF) {
                                    case 0:
                                        mmyy = mmyy - width / ANIM_COUNT / 5 - width / ANIM_COUNT;
                                        RecmoveF = 3;
                                        break;
                                    case 1:
                                        mmyy = mmyy + width / ANIM_COUNT / 5 + width / ANIM_COUNT;
                                        RecmoveF = 0;
                                        break;
                                    case 2:
                                        mmyx = mmyx - width / ANIM_COUNT / 30 - width / ANIM_COUNT;
                                        RecmoveF = 1;
                                        break;
                                    case 3:
                                        mmyx = mmyx + width / ANIM_COUNT / 30 + width / ANIM_COUNT;
                                        RecmoveF = 2;
                                        break;
                                }
                                if (moveF != 4) {
                                    mDestRect2 = new Rect(mmyx, mmyy, mmyx + width, mmyy + width);
                                    mTestAnim[moveF].DrawAnimation(canvas, mPaint, mDestRect2);
                                }
                                ;
                            }
                            switch (moveF) {
                                case 0:
                                    Mmyy -= 1;
                                    break;
                                case 1:
                                    Mmyy += 1;
                                    break;
                                case 2:
                                    Mmyx -= 1;
                                    break;
                                case 3:
                                    Mmyx += 1;
                                    break;
                            }
                            if(GameOver()==0)
                                break;
                        }
                    }else
                        move=false;
                    break;
                case 1:
                    if (qiangbi[Many][Manx] % 10 == 0) {
                        move=true;
                        mAnimationState = ANIM_DOWN;
                        for (int i = 0; i < ANIM_COUNT; i++) {
                            many = many + width / ANIM_COUNT / 5 + width / ANIM_COUNT;
                            mDestRect1 = new Rect(manx, many, manx + width, many + width);
                            mHeroAnim[mAnimationState].DrawAnimation(canvas, mPaint, mDestRect1);
                        }
                        Many += 1;
                        if(GameOver()==1)
                            break;
                        for (int j = 0; j < 2; j++) {
                            moveF = this.MmyMove();
                            for (int i = 0; i < ANIM_COUNT; i++) {
                                switch (moveF) {
                                    case 0:
                                        mmyy = mmyy - width / ANIM_COUNT / 5 - width / ANIM_COUNT;
                                        RecmoveF = 3;
                                        break;
                                    case 1:
                                        mmyy = mmyy + width / ANIM_COUNT / 5 + width / ANIM_COUNT;
                                        RecmoveF = 0;
                                        break;
                                    case 2:
                                        mmyx = mmyx - width / ANIM_COUNT / 30 - width / ANIM_COUNT;
                                        RecmoveF = 1;
                                        break;
                                    case 3:
                                        mmyx = mmyx + width / ANIM_COUNT / 30 + width / ANIM_COUNT;
                                        RecmoveF = 2;
                                        break;
                                }
                                if (moveF != 4) {
                                    mDestRect2 = new Rect(mmyx, mmyy, mmyx + width, mmyy + width);
                                    mTestAnim[moveF].DrawAnimation(canvas, mPaint, mDestRect2);
                                }
                            }
                            switch (moveF) {
                                case 0:
                                    Mmyy -= 1;
                                    break;
                                case 1:
                                    Mmyy += 1;
                                    break;
                                case 2:
                                    Mmyx -= 1;
                                    break;
                                case 3:
                                    Mmyx += 1;
                                    break;
                            }
                            if(GameOver()==0)
                                break;
                        }
                    }else
                        move=false;
                    break;
                case 2:
                    if (qiangbi[Many][Manx] / 1000 == 0) {
                        move=true;
                        mAnimationState = ANIM_LEFT;
                        for (int i = 0; i < ANIM_COUNT; i++) {
                            manx = manx - width / ANIM_COUNT / 30 - width / ANIM_COUNT;
                            mDestRect1 = new Rect(manx, many, manx + width, many + width);
                            mHeroAnim[mAnimationState].DrawAnimation(canvas, mPaint, mDestRect1);
                        }
                        Manx -= 1;
                        if(GameOver()==1)
                            break;
                        for (int j = 0; j < 2; j++) {
                            moveF = this.MmyMove();
                            for (int i = 0; i < ANIM_COUNT; i++) {
                                switch (moveF) {
                                    case 0:
                                        mmyy = mmyy - width / ANIM_COUNT / 5 - width / ANIM_COUNT;
                                        RecmoveF = 3;
                                        break;
                                    case 1:
                                        mmyy = mmyy + width / ANIM_COUNT / 5 + width / ANIM_COUNT;
                                        RecmoveF = 0;
                                        break;
                                    case 2:
                                        mmyx = mmyx - width / ANIM_COUNT / 30 - width / ANIM_COUNT;
                                        RecmoveF = 1;
                                        break;
                                    case 3:
                                        mmyx = mmyx + width / ANIM_COUNT / 30 + width / ANIM_COUNT;
                                        RecmoveF = 2;
                                        break;
                                }
                                if (moveF != 4) {
                                    mDestRect2 = new Rect(mmyx, mmyy, mmyx + width, mmyy + width);
                                    mTestAnim[moveF].DrawAnimation(canvas, mPaint, mDestRect2);
                                }
                            }
                            switch (moveF) {
                                case 0:
                                    Mmyy -= 1;
                                    break;
                                case 1:
                                    Mmyy += 1;
                                    break;
                                case 2:
                                    Mmyx -= 1;
                                    break;
                                case 3:
                                    Mmyx += 1;
                                    break;
                            }
                            if(GameOver()==0)
                                break;
                        }
                    }else
                        move=false;
                    break;
                case 3:
                    if (qiangbi[Many][Manx] % 100 / 10 == 0) {
                        move=true;
                        mAnimationState = ANIM_RIGHT;
                        for (int i = 0; i < ANIM_COUNT; i++) {
                            manx = manx + width / ANIM_COUNT / 30 + width / ANIM_COUNT;
                            mDestRect1 = new Rect(manx, many, manx + width, many + width);
                            mHeroAnim[mAnimationState].DrawAnimation(canvas, mPaint, mDestRect1);
                        }
                        Manx += 1;
                        if(GameOver()==1)
                            break;
                        for (int j = 0; j < 2; j++) {
                            moveF = this.MmyMove();
                            for (int i = 0; i < ANIM_COUNT; i++) {
                                switch (moveF) {
                                    case 0:
                                        mmyy = mmyy - width / ANIM_COUNT / 5 - width / ANIM_COUNT;
                                        RecmoveF = 3;
                                        break;
                                    case 1:
                                        mmyy = mmyy + width / ANIM_COUNT / 5 + width / ANIM_COUNT;
                                        RecmoveF = 0;
                                        break;
                                    case 2:
                                        mmyx = mmyx - width / ANIM_COUNT / 30 - width / ANIM_COUNT;
                                        RecmoveF = 1;
                                        break;
                                    case 3:
                                        mmyx = mmyx + width / ANIM_COUNT / 30 + width / ANIM_COUNT;
                                        RecmoveF = 2;
                                        break;
                                }
                                if (moveF != 4) {
                                    mDestRect2 = new Rect(mmyx, mmyy, mmyx + width, mmyy + width);
                                    mTestAnim[moveF].DrawAnimation(canvas, mPaint, mDestRect2);
                                }
                            }
                            switch (moveF) {
                                case 0:
                                    Mmyy -= 1;
                                    break;
                                case 1:
                                    Mmyy += 1;
                                    break;
                                case 2:
                                    Mmyx -= 1;
                                    break;
                                case 3:
                                    Mmyx += 1;
                                    break;
                            }
                            if(GameOver()==0)
                                break;
                        }
                    }

                    else
                        move=false;
                    break;
                case 4:
                    for (int j = 0; j < 2; j++) {
                        moveF = this.MmyMove();
                        for (int i = 0; i < ANIM_COUNT; i++) {
                            switch (moveF) {
                                case 0:
                                    mmyy = mmyy - width / ANIM_COUNT / 5 - width / ANIM_COUNT;
                                    RecmoveF = 3;
                                    break;
                                case 1:
                                    mmyy = mmyy + width / ANIM_COUNT / 5 + width / ANIM_COUNT;
                                    RecmoveF = 0;
                                    break;
                                case 2:
                                    mmyx = mmyx - width / ANIM_COUNT / 30 - width / ANIM_COUNT;
                                    RecmoveF = 1;
                                    break;
                                case 3:
                                    mmyx = mmyx + width / ANIM_COUNT / 30 + width / ANIM_COUNT;
                                    RecmoveF = 2;
                                    break;
                            }
                            if (moveF != 4) {
                                mDestRect2 = new Rect(mmyx, mmyy, mmyx + width, mmyy + width);
                                mTestAnim[moveF].DrawAnimation(canvas, mPaint, mDestRect2);
                            }
                        }
                        switch (moveF) {
                            case 0:
                                Mmyy -= 1;
                                break;
                            case 1:
                                Mmyy += 1;
                                break;
                            case 2:
                                Mmyx -= 1;
                                break;
                            case 3:
                                Mmyx += 1;
                                break;
                        }
                        if(GameOver()==0)
                            break;
                    }
                    move=false;
                    break;
            }
            /**绘制主角动画**/

            onclick = false;
            Manstep.push(Many);
            Manstep.push(Manx);
            Mmystep.push(Mmyy);
            Mmystep.push(Mmyx);
        } //else {
        if (moveF == 4)
            moveF = 0;
        mHeroAnim[mAnimationState].DrawFrame(canvas, mPaint, mDestRect1, 0);
        mTestAnim[RecmoveF].DrawFrame(canvas, mPaint, mDestRect2, 0);
        //}
        super.onDraw(canvas);
        invalidate();
    }

    public int GameOver() {
        if (level != 9) {
            if (Manx == Mmyx && Many == Mmyy)
            {
                if (OverListener != null)
                    OverListener.onOver(true,level);
                return 0;
            }
            if (Manx == -1 || Manx == 6 || Many == -1 || Many == 6) {
                this.level = this.level+1;
                if (changeListener != null)
                    changeListener.onChange(level);
                initialize(level);
                return 1;
            }
        } else {
            if (Manx == Mmyx && Many == Mmyy)
            {
                if (OverListener != null)
                    OverListener.onOver(true,level);
                return 0;
            }
            if (Manx == -1 || Manx == 6 || Many == -1 || Many == 6)
            {     if (changeListener != null)
                    changeListener.onChange(10);
                return 1;
        }}
        return 2;
    }

    /**
     * 程序切割图片
     *
     * @param bitmap
     * @param x
     * @param y
     * @param w
     * @param h
     * @return
     */
    public Bitmap BitmapClipBitmap(Bitmap bitmap, int x, int y, int w, int h) {
        return Bitmap.createBitmap(bitmap, x, y, w, h);
    }
    public boolean getmove(){
        return  this.move;
    }
    /**
     * 读取本地资源的图片
     *
     * @param context
     * @param resId
     * @return
     */
    public Bitmap ReadBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    public int MmyMove() {
        if (Manx > Mmyx) //在右边
            if (qiangbi[Mmyy][Mmyx] % 100 / 10 == 0)
            {
                move=true;
                return 3;
            } //没有墙 向右走 有墙判断上下方向
            else
            if (Many > Mmyy)  //在下面
            {
                if (qiangbi[Mmyy][Mmyx] % 10 == 0)
                    //没有墙向下走
                {   move=true;return 1;}
                else
                {   move=false;return 4; }//不走动
            } else  if(Many < Mmyy)//在上面
                if (qiangbi[Mmyy][Mmyx] % 1000 / 100 == 0)
                    //没有墙向上走
                    return 0;
                else
                    return 4; //不走动
            else
                return 4;
        else if (Manx < Mmyx)//在左边
            if (qiangbi[Mmyy][Mmyx] / 1000 == 0) //没有墙 向左走
                return 2;
            else //有墙判断上下
                if (Many > Mmyy)  //在下面
                {
                    if (qiangbi[Mmyy][Mmyx] % 10 == 0)
                        //没有墙向下走
                        return 1;
                    else
                        return 4; //不走动
                } else if (Many < Mmyy) //在上面
                    if (qiangbi[Mmyy][Mmyx] % 1000 / 100 == 0)
                        //没有墙向上走
                        return 0;
                    else
                        return 4; //不走动
                else
                    return 4;
        else
            //有墙判断上下
            if (Many > Mmyy)  //在下面
            {
                if (qiangbi[Mmyy][Mmyx] % 10 == 0)
                    //没有墙向下走
                    return 1;
                else
                    return 4; //不走动
            } else if (Many < Mmyy)//在上面
                if (qiangbi[Mmyy][Mmyx] % 1000 / 100 == 0)
                    //没有墙向上走
                    return 0;
                else
                    return 4; //不走动
            else
                return 4;
    }


    /**
     * 设置按键状态true为按下 false为抬起
     *
     * @param direction 方向
     */
    public void setmove(int direction) {
        switch (direction) {
            case 0:
                mdirection = 0;
                onclick = true;
                invalidate();
                break;
            case 1:
                mdirection = 1;
                onclick = true;
                invalidate();
                break;
            case 2:
                mdirection = 2;
                onclick = true;
                invalidate();
                break;
            case 3:
                mdirection = 3;
                onclick = true;
                invalidate();
                break;
            case 4:
                mdirection = 4;
                onclick = true;
                invalidate();
                break;
        }
    }
    protected int getLevel(){
        return this.level;
    }
    protected void MakeCancel(){
        if(cancelListener!=null) {
            WindowManager wm = (WindowManager) getContext()
                    .getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth() / 8;
            if (ManstepRec.empty()) {
                Manx = Manstep.pop();
                Many = Manstep.pop();
                Manstep.push(Many);
                Manstep.push(Manx);
            } else
            {
                Many=ManstepRec.pop();
                Manx=ManstepRec.pop();
                Manstep.pop();
                Manstep.pop();
                Manstep.push(Many);
                Manstep.push(Manx);
            }
            if (MmystepRec.empty()) {
                Mmyx =  Mmystep.pop();
                Mmyy = Mmystep.pop();
                Mmystep.push(Mmyy);
                Mmystep.push(Mmyx);
            } else
            {
                Mmyy=MmystepRec.pop();
                Mmyx=MmystepRec.pop();
                Mmystep.pop();
                Mmystep.pop();
                Mmystep.push(Mmyy);
                Mmystep.push(Mmyx);
            }
            manx = Manx* (width + width / 30);
            many =  Many * (width + width / 5);
            if (Manx > 3)
                manx -=  Manx*width / 30;
            mDestRect1 = new Rect(manx, many, manx + width, many + width);
            mmyx = Mmyx* (width + width / 30);
            mmyy =  Mmyy * (width + width / 5);
            if (Mmyx > 3)
                mmyx -= Mmyy*width / 30;
            mDestRect2 = new Rect(mmyx, mmyy, mmyx + width, mmyy + width);
            invalidate();
        }
    }
    public void setCancelListener(OnCancelListener cancelListener) {
        this.cancelListener = cancelListener;
    }

    public interface OnCancelListener {
        ;
    }

    public void setChangeListener(OnChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public interface OnChangeListener {
        void onChange(int level);
    }

    public void setOverListener(OnOverListener OverListener) {
        this.OverListener = OverListener;
    }

    public interface OnOverListener {
        void onOver(Boolean over,int level);
    }
}