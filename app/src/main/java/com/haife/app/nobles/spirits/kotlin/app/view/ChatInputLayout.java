package com.haife.app.nobles.spirits.kotlin.app.view;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;


import com.ezreal.emojilibrary.EmojiBean;
import com.ezreal.emojilibrary.EmojiUtils;
import com.ezreal.emojilibrary.ExpressLayout;
import com.haife.app.nobles.spirits.kotlin.R;
import com.haife.app.nobles.spirits.kotlin.app.constant.SPConstant;
import com.haife.app.nobles.spirits.kotlin.app.utils.SharedPreferencesUtil;
import com.haife.app.nobles.spirits.kotlin.app.utils.SystemUtils;
import com.jess.arms.utils.LogUtils;
import com.luck.picture.lib.tools.SPUtils;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * 聊天界面 输入相关组合布局
 * Created by wudeng on 2017/11/2.
 */

public class ChatInputLayout extends RelativeLayout {

    @BindView(R.id.et_chat_message)
    public EditText mEtInput;
    @BindView(R.id.iv_expression)
    ImageView mIvExpress;
    @BindView(R.id.iv_more)
    ImageView mIvMore;
    @BindView(R.id.tv_btn_send)
    TextView mBtnSend;

    @BindView(R.id.layout_extension)
    RelativeLayout mExtensionLayout;
    @BindView(R.id.layout_express)
    ExpressLayout mExpressLayout;

    private InputMethodManager mInputManager;
    private OnInputLayoutListener mLayoutListener;
    private Activity mActivity;
    private View mContentView;

    public ChatInputLayout(Context context) {
        this(context, null);
    }

    public ChatInputLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatInputLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View root = LayoutInflater.from(context).inflate(R.layout.layout_chat_input,
                this, true);
        ButterKnife.bind(root);
        mInputManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        initListener();
    }

    /**
     * 根据Activity和内容区域，设置隐藏显示时的控件高度
     */
    public void bindInputLayout(Activity activity, View contentView) {
        mActivity = activity;
        mContentView = contentView;
        int keyboardHeight = SPUtils.getInstance().getInt( SPConstant.KEYBOARD
                , 0);
        if (keyboardHeight == 0) {
            keyboardHeight = 618;
        }

        ViewGroup.LayoutParams layoutParams = mExpressLayout.getLayoutParams();
        layoutParams.height = keyboardHeight;
        mExpressLayout.setLayoutParams(layoutParams);

        layoutParams = mExtensionLayout.getLayoutParams();
        layoutParams.height = keyboardHeight;
        mExtensionLayout.setLayoutParams(layoutParams);
    }

    private void initListener() {
        // 文本输入框触摸监听
        mEtInput.setOnTouchListener(new MyOnTouchListener());
        // 录音按钮初始化和录音监听
//        mBtnAudioRecord.init(AppConstants.APP_CACHE_AUDIO);
//        mBtnAudioRecord.setRecordingListener(new AudioRecordButton.OnRecordingListener() {
//            @Override
//            public void recordFinish(String audioFilePath, long recordTime) {
//                if (mLayoutListener != null) {
//                    mLayoutListener.audioRecordFinish(audioFilePath, recordTime);
//                }
//            }
//
//            @Override
//            public void recordError(String message) {
//                if (mLayoutListener != null) {
//                    mLayoutListener.audioRecordError(message);
//                }
//            }
//        });

        // 文本输入框输入监听
        mEtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {
                    mIvMore.setVisibility(View.GONE);
                    mBtnSend.setVisibility(View.VISIBLE);
                } else {
                    mIvMore.setVisibility(View.VISIBLE);
                    mBtnSend.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mExpressLayout.setOnExpressSelListener(new ExpressLayout.OnExpressSelListener() {
            @Override
            public void onEmojiSelect(EmojiBean emojiBean) {
                // 如果点击了表情,则添加到输入框中

                // 获取当前光标位置,在指定位置上添加表情图片文本
                int curPosition = mEtInput.getSelectionStart();
                StringBuilder sb = new StringBuilder(mEtInput.getText().toString());
                sb.insert(curPosition, emojiBean.getEmojiName());
                // 特殊文字处理,将表情等转换一下
                SpannableString spannableString = EmojiUtils.text2Emoji(getContext(),
                        sb.toString(), mEtInput.getTextSize());
                mEtInput.setText(spannableString);
                // 将光标设置到新增完表情的右侧
                mEtInput.setSelection(curPosition + emojiBean.getEmojiName().length());
            }

            @Override
            public void onEmojiDelete() {
                // 调用系统的删除操作
                mEtInput.dispatchKeyEvent(new KeyEvent(
                        KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (h > oldh) {
            if (mLayoutListener != null) {
                mLayoutListener.exLayoutShow();
            }
        }
    }

    /************ 按钮点击事件 ***********/


    @OnClick(R.id.iv_more)
    public void clickMoreBtn() {
        if (isSoftInputShow()) {
            lockContentHeight();
            hideSoftInput();
            mExtensionLayout.setVisibility(VISIBLE);
            unLockContentHeight();
        } else if (mExpressLayout.isShown()) {
            lockContentHeight();
            mExpressLayout.setVisibility(GONE);
            mIvExpress.setImageResource(R.mipmap.expression);
            mExtensionLayout.setVisibility(VISIBLE);
            unLockContentHeight();
        } else if (mExtensionLayout.isShown()) {
            lockContentHeight();
            mExtensionLayout.setVisibility(GONE);
            showSoftInput();
            unLockContentHeight();
        } else {
            mExtensionLayout.setVisibility(VISIBLE);
        }
    }

    @OnClick(R.id.iv_expression)
    public void clickExpressBtn() {
        // 如果录音按钮显示，则改变为 文本输入框显示
        if (mExtensionLayout.isShown()) {
            lockContentHeight();
            mExtensionLayout.setVisibility(GONE);
            mExpressLayout.setVisibility(VISIBLE);
            unLockContentHeight();
        } else if (mExpressLayout.isShown()) {
            lockContentHeight();
            mExpressLayout.setVisibility(GONE);
            mIvExpress.setImageResource(R.mipmap.expression);
            showSoftInput();
            unLockContentHeight();
        } else if (isSoftInputShow()) {
            lockContentHeight();
            hideSoftInput();
            mExpressLayout.postDelayed(() ->
                    mExpressLayout.setVisibility(VISIBLE), 50);
            mIvExpress.setImageResource(R.mipmap.keyboard);
            unLockContentHeight();
        } else {
            mExpressLayout.setVisibility(VISIBLE);
            mIvExpress.setImageResource(R.mipmap.keyboard);
        }
    }

    @OnClick(R.id.tv_btn_send)
    public void sendTextMessage() {
        String text = mEtInput.getText().toString();

        if (mLayoutListener != null) {
            if( mLayoutListener.sendBtnClick(text)){
                mEtInput.getText().clear();
            }

        }
    }

    @OnClick(R.id.layout_image)
    public void selectPhoto() {
        if (mLayoutListener != null) {
            mLayoutListener.photoBtnClick();
        }
    }

//    @OnClick(R.id.layout_camera)
//    public void startCamera() {
//        if (mLayoutListener != null) {
//            mLayoutListener.cameraBtnClick();
//        }
//    }




    @OnClick(R.id.layout_extension)
    public void hintLayout() {
        hideOverView();
    }

    public boolean extendIsShouwing() {
        return mExpressLayout.getVisibility() == VISIBLE
                || mExtensionLayout.getVisibility() == VISIBLE;
    }

    /**
     * 锁定内容 View 的高度，解决闪屏问题
     */
    private void lockContentHeight() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)
                mContentView.getLayoutParams();
        layoutParams.height = mContentView.getHeight();
        layoutParams.weight = 0.0f;
    }

    /**
     * 释放内容 View 高度
     */
    private void unLockContentHeight() {
        mEtInput.postDelayed(new Runnable() {
            @Override
            public void run() {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)
                        mContentView.getLayoutParams();
                layoutParams.height = 0;
                layoutParams.weight = 1.0f;
            }
        }, 200);
    }

    /**
     * 编辑框获取焦点，并显示软件盘
     */
    private void showSoftInput() {
        mEtInput.requestFocus();
        mEtInput.post(new Runnable() {
            @Override
            public void run() {
                mInputManager.showSoftInput(mEtInput, 0);
            }
        });
    }

    /**
     * 隐藏软件盘
     */
    private void hideSoftInput() {
        mInputManager.hideSoftInputFromWindow(mEtInput.getWindowToken(), 0);
    }

    private boolean isSoftInputShow() {
        System.out.println("=======测试============" + SystemUtils.getKeyBoardHeight(mActivity));
        return SystemUtils.getKeyBoardHeight(mActivity) > 50;
    }

    public void setLayoutListener(OnInputLayoutListener layoutListener) {
        this.mLayoutListener = layoutListener;
    }

    /**
     * 隐藏所有已显示的布局（键盘，表情，扩展）
     */
    public void hideOverView() {
        if (isSoftInputShow()) {
            hideSoftInput();
        }

        if (mExpressLayout.isShown()) {
            mExpressLayout.setVisibility(GONE);
            mIvExpress.setImageResource(R.mipmap.expression);
        }

        if (mExtensionLayout.isShown()) {
            mExtensionLayout.setVisibility(GONE);
        }
    }

    public interface OnInputLayoutListener {
        boolean sendBtnClick(CharSequence textMessage);

        void photoBtnClick();

        void cameraBtnClick();

        void exLayoutShow();

    }

    private class MyOnTouchListener implements OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (mExpressLayout.isShown()) {
                    lockContentHeight();
                    mExpressLayout.setVisibility(GONE);
                    mIvExpress.setImageResource(R.mipmap.expression);
                    showSoftInput();
                    unLockContentHeight();
                } else if (mExtensionLayout.isShown()) {
                    lockContentHeight();
                    mExtensionLayout.setVisibility(GONE);
                    showSoftInput();
                    unLockContentHeight();
                } else {
                    showSoftInput();
                }
                return true;
            }
            return false;
        }

    }
}
