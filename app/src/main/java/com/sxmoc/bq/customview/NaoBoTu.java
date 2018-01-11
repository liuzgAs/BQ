package com.sxmoc.bq.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.sxmoc.bq.util.DpUtils;

/**
 * Created by zhangjiebo on 2018/1/11/011.
 *
 * @author ZhangJieBo
 */

public class NaoBoTu extends View{
    /**
     * 横线画笔
     */
    private Paint paintHengXian;
    /**
     * 文字画笔
     */
    private Paint paintWenZi;
    /**
     * 横线数量
     */
    int hengXianNum = 5;
    /**
     * 底部字体占用高度
     */
    float diBuZiHeght;
    /**
     * 右边字体占用宽度
     */
    float youBianZiWidth;
    /**
     * 底部文字
     */
    String[] strDiBu = new String[]{
            "10",
            "20",
            "30",
            "40",
            "50",
            "60",
    };
    float diBuHengXianWidth ;
    float qiTaHengXianWidth ;
    float diBuTextSize ;
    private Rect rectDiBu;

    public NaoBoTu(Context context) {
        super(context);
    }

    public NaoBoTu(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NaoBoTu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * 初始化距离
         */
        diBuZiHeght = DpUtils.convertDpToPixel(50f, context);
        youBianZiWidth = DpUtils.convertDpToPixel(60f, context);
        diBuHengXianWidth = DpUtils.convertDpToPixel(1.2f, context);
        qiTaHengXianWidth = DpUtils.convertDpToPixel(0.8f, context);
        diBuTextSize = DpUtils.convertDpToPixel(12f, context);

        setLayerType(LAYER_TYPE_SOFTWARE, null);
        /**
         * 初始化横线画笔
         */
        paintHengXian = new Paint(Paint.ANTI_ALIAS_FLAG);
        /**
         * 初始化文字画笔
         */
        paintWenZi = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintWenZi.setColor(Color.parseColor("#b3d7fa"));
        paintWenZi.setStyle(Paint.Style.FILL);
        paintWenZi.setTextSize(diBuTextSize);
        rectDiBu = new Rect();
        paintHengXian.getTextBounds(strDiBu[0], 0, strDiBu[0].length(), rectDiBu);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 画底部线条
         */
        paintHengXian.setColor(Color.parseColor("#88b3d7fa"));
        paintHengXian.setStrokeWidth(diBuHengXianWidth);
        canvas.drawLine(0,getHeight()-diBuZiHeght,getWidth(),getHeight()-diBuZiHeght, paintHengXian);
        /**
         * 画其他横线
         */
        paintHengXian.setColor(Color.parseColor("#55b3d7fa"));
        paintHengXian.setStrokeWidth(qiTaHengXianWidth);
        //高度间隔
        float gaoDuJianGe = (getHeight() - diBuZiHeght)/(hengXianNum+1);
        for (int i = 0; i < hengXianNum; i++) {
            canvas.drawLine(0, getHeight() - diBuZiHeght- gaoDuJianGe* (i+1), getWidth()-youBianZiWidth, getHeight() - diBuZiHeght- gaoDuJianGe* (i+1), paintHengXian);
        }
        /**
         * 画底部文字
         */
        //宽度间隔
        float kuanDuJianGe = (getWidth()-youBianZiWidth)/strDiBu.length;
        for (int i = 0; i < strDiBu.length; i++) {
            canvas.drawText(strDiBu[i],i*kuanDuJianGe+0.5f*kuanDuJianGe-0.5f*rectDiBu.width(),getHeight()-diBuZiHeght/2,paintWenZi);
        }
        ;
//        kuanDuJianGe*(1+1)-rectDiBu.width()/2;
    }
}
