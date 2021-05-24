package com.haife.app.nobles.spirits.kotlin.app.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.haife.app.nobles.spirits.kotlin.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import razerdp.basepopup.BasePopupWindow;


/**
 * Created by 大灯泡 on 2019/9/24
 * <p>
 * Description：朋友圈
 */
public class PopupFriendCircle extends BasePopupWindow {

    public static boolean outSideTouch = false;
    public static boolean link = false;
    public static boolean blur = false;
    @BindView(R.id.ll_addfriend)
    LinearLayout ll_addfriend;
    @BindView(R.id.ll_addgroup)
    LinearLayout ll_addgroup;
    @BindView(R.id.ll_setting)
    LinearLayout ll_setting;
    @BindView(R.id.ll_mineinfo)
    LinearLayout ll_mineinfo;
    @BindView(R.id.ll_logout)
    LinearLayout ll_logout;

    ClickLisetener clickLisetener;
    boolean iscollect;

    ValueAnimator valueAnimator;
    private int type;
    private boolean ismine;

    public PopupFriendCircle(Context context) {
        super(context);
        setBackgroundColor(0);
    }

    public void setClickcollectLisetener(ClickLisetener clickLisetener) {
        this.clickLisetener = clickLisetener;

    }

    @Override
    public void onViewCreated(View contentView) {
        ButterKnife.bind(this, contentView);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_friend_circle_comment);
    }

    @Override
    public void showPopupWindow(View anchorView) {
        setBlurBackgroundEnable(blur);
        if (outSideTouch) {
            setOutSideTouchable(true);
            setOutSideDismiss(false);
        } else {
            setOutSideDismiss(true);
            setOutSideTouchable(false);
        }
        linkTo(link ? anchorView : null);
        super.showPopupWindow(anchorView);
    }

    @Override
    protected Animation onCreateShowAnimation() {
        Animation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
                1f,
                Animation.RELATIVE_TO_PARENT,
                0,
                Animation.RELATIVE_TO_PARENT,
                0,
                Animation.RELATIVE_TO_PARENT,
                0);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(350);
        return animation;
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        Animation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
                0f,
                Animation.RELATIVE_TO_PARENT,
                1f,
                Animation.RELATIVE_TO_PARENT,
                0,
                Animation.RELATIVE_TO_PARENT,
                0);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(350);
        return animation;
    }

//    public PopupFriendCircle setInfo(int type, boolean ismine, boolean iscollect) {
//        this.iscollect = iscollect;
//        this.type = type;
//        this.ismine = ismine;
//        //type=0,评论的更多键，有回复子item
//        if (type == 0) {
//            iv_star.setImageResource(R.mipmap.ic_mine_message);
//            tv_star.setText("回复");
//        } else {
//            if (iscollect) {
//                if (ScreenUtils.isdark()) {
//                    iv_star.setImageResource(R.mipmap.ic_article_detials_collect);
//                } else {
//                    iv_star.setImageResource(R.mipmap.ic_article_detials_collect_light);
//                }
//
//            } else {
//                iv_star.setImageResource(R.mipmap.ic_mine_select);
//            }
//        }
//
//        if (ismine) {
//            ll_delete.setVisibility(View.VISIBLE);
//        } else {
//            ll_delete.setVisibility(View.GONE);
//        }
//
//        return this;
//    }

    @OnClick({R.id.ll_addfriend, R.id.ll_addgroup, R.id.ll_logout ,R.id.ll_setting, R.id.ll_mineinfo})
    void onStarClick(View view) {

        switch (view.getId()) {
            case R.id.ll_addfriend:
                iscollect = (!iscollect);
                dismiss();
                if (clickLisetener == null) {
                    return;
                }
                clickLisetener.addfriend();

                break;
            case R.id.ll_addgroup:
                dismiss();
                if (clickLisetener == null) {

                    return;
                }
                clickLisetener.addgroup();
                break;
            case R.id.ll_logout:
                dismiss();
                if (clickLisetener == null) {
                    return;
                }
                clickLisetener.logout();
                break;
            case R.id.ll_setting:
                dismiss();
                if (clickLisetener == null) {
                    return;
                }
                clickLisetener.setting();
                break;
            case R.id.ll_mineinfo:
                dismiss();
                if (clickLisetener == null) {
                    return;
                }
                clickLisetener.mineinfo();
                break;
        }

    }


    public interface ClickLisetener {
        void addfriend();
        void addgroup();
        void logout();
        void setting();
        void mineinfo();
    }


}
