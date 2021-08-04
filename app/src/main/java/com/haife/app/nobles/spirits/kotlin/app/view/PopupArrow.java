package com.haife.app.nobles.spirits.kotlin.app.view;


import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.haife.app.nobles.spirits.kotlin.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import razerdp.basepopup.BasePopupWindow;
import razerdp.util.animation.AlphaConfig;
import razerdp.util.animation.AnimationHelper;

/**
 * Created by 大灯泡 on 2020/5/6.
 */
public class PopupArrow extends BasePopupWindow {
    @BindView(R.id.iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;

    public PopupArrow(Context context) {
        super(context);
        setViewPivotRatio(mIvArrow, 0.5f, 0.5f);
    }


    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_arrow);
    }

    @Override
    public void onViewCreated(@NonNull View contentView) {

        ButterKnife.bind(this, contentView);
    }
    public  void setViewPivotRatio(final View v, final float pvX, final float pvY) {
        if (v == null) return;
        v.post(new Runnable() {
            @Override
            public void run() {
                v.setPivotX(v.getWidth() * pvX);
                v.setPivotY(v.getHeight() * pvY);
            }
        });
    }
    @Override
    protected Animation onCreateShowAnimation(int width, int height) {
        return AnimationHelper.asAnimation()
                .withAlpha(AlphaConfig.IN)
                .toShow();
    }

    @Override
    protected Animation onCreateDismissAnimation(int width, int height) {
        return AnimationHelper.asAnimation()
                .withAlpha(AlphaConfig.OUT)
                .toDismiss();
    }

    @Override
    public void onPopupLayout(@NonNull Rect popupRect, @NonNull Rect anchorRect) {
        int gravity = computeGravity(popupRect, anchorRect);
        boolean verticalCenter = false;
        switch (gravity & Gravity.VERTICAL_GRAVITY_MASK) {
            case Gravity.TOP:
                mIvArrow.setVisibility(View.VISIBLE);
                mIvArrow.setTranslationX((popupRect.width() - mIvArrow.getWidth()) >> 1);
                mIvArrow.setTranslationY(popupRect.height() - mIvArrow.getHeight());
                mIvArrow.setRotation(0f);
                break;
            case Gravity.BOTTOM:
                mIvArrow.setVisibility(View.VISIBLE);
                mIvArrow.setTranslationX((popupRect.width() - mIvArrow.getWidth()) >> 1);
                mIvArrow.setTranslationY(0);
                mIvArrow.setRotation(180f);
                break;
            case Gravity.CENTER_VERTICAL:
                verticalCenter = true;
                break;
        }
        switch (gravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
            case Gravity.LEFT:
                mIvArrow.setVisibility(View.VISIBLE);
                mIvArrow.setTranslationX(popupRect.width() - mIvArrow.getWidth());
                mIvArrow.setTranslationY((popupRect.height() - mIvArrow.getHeight()) >> 1);
                mIvArrow.setRotation(270f);
                break;
            case Gravity.RIGHT:
                mIvArrow.setVisibility(View.VISIBLE);
                mIvArrow.setTranslationX(0);
                mIvArrow.setTranslationY((popupRect.height() - mIvArrow.getHeight()) >> 1);
                mIvArrow.setRotation(90f);
                break;
            case Gravity.CENTER_HORIZONTAL:
                mIvArrow.setVisibility(verticalCenter ? View.INVISIBLE : View.VISIBLE);
                break;
        }
    }
}

