package com.haife.app.nobles.spirits.kotlin.app.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.haife.app.nobles.spirits.kotlin.app.utils.SystemUtils;


/**
 * author: pro
 * time：2019-11-17
 * desc：
 * fixed time：
 */
public class ChatMsgRootLayout extends RelativeLayout {

    private ChatInputLayout mInputLayout;
    private Activity mActivity;

    public ChatMsgRootLayout(Context context) {
        this(context, null);
    }

    public ChatMsgRootLayout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public ChatMsgRootLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (mInputLayout != null) {
                if (mInputLayout.extendIsShouwing() || isSoftInputShow()) {
                    mInputLayout.hideOverView();
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    public void bindChatInputView(ChatInputLayout input) {
        mInputLayout = input;
    }

    public void bindActivity(Activity activity) {
        mActivity = activity;
    }

    private boolean isSoftInputShow() {
        if (mActivity == null || mActivity.isFinishing()) return false;
        return SystemUtils.getKeyBoardHeight(mActivity) > 50;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mActivity = null;
        mInputLayout = null;
    }
}
