package com.haife.app.nobles.spirits.kotlin.app.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;


/**
 * @author
 * @date 2020/6/24 17:04.
 * description 自动粗体+可以换肤的textview
 */
public class MediumBoldTextView extends androidx.appcompat.widget.AppCompatTextView {


    public MediumBoldTextView(Context context) {
        this(context, null);
    }

    public MediumBoldTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);


    }

    public MediumBoldTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //获取当前控件的画笔
        TextPaint paint = getPaint();
        //设置画笔的描边宽度值
        paint.setStrokeWidth(1.25f);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        super.onDraw(canvas);
    }

    @Override
    public void setBackgroundResource(@DrawableRes int resId) {
        super.setBackgroundResource(resId);
    }

    @Override
    public void setTextAppearance(int resId) {
        setTextAppearance(getContext(), resId);
    }

    @Override
    public void setTextAppearance(Context context, int resId) {
        super.setTextAppearance(context, resId);

    }


}

