package com.haife.app.nobles.spirits.kotlin.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.haife.app.nobles.spirits.kotlin.R;
import com.haife.app.nobles.spirits.kotlin.app.constant.SPConstant;
import com.haife.app.nobles.spirits.kotlin.di.component.DaggerLoginComponent;
import com.haife.app.nobles.spirits.kotlin.mvp.contract.LoginContract;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.LoginBean;
import com.haife.app.nobles.spirits.kotlin.mvp.presenter.LoginPresenter;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.utlis.BarUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.jingewenku.abrahamcaijin.commonutil.AppValidationMgr;
import com.kongzue.dialog.v2.TipDialog;
import com.yescpu.keyboardchangelib.KeyboardChangeListener;
import com.zhy.autolayout.attr.MaxHeightAttr;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/22/2021 13:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View  , KeyboardChangeListener.KeyboardListener{
    @BindView(R.id.status_bar_view)
    View status_bar_view;
    @BindView(R.id.edt_id)
    EditText edt_acc;
    @BindView(R.id.edt_remark)
    EditText edt_pwd;
    @BindView(R.id.tv_login)
    TextView tv_login;
    @BindView(R.id.tv_register)
    TextView tv_register;
    @BindView(R.id.tv_operate)
    TextView tv_operate;
    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.ll_login)
    LinearLayout ll_login;


    private Drawable drawablebutton;
    private Drawable drawablebuttonno;
    private boolean login=true;
    private long mPressedTime;
    private KeyboardChangeListener mKeyboardChangeListener;
    //最长名字限制
    private int MAXNAME=10;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this)
                .statusBarView(status_bar_view)
                .init();
//        KeyBoardListener.getInstance(this).init();
        drawablebutton = getResources().getDrawable(R.drawable.shape_login_button);
        drawablebuttonno = getResources().getDrawable(R.drawable.shape_nologin_button);
        if(SPUtils.getInstance().getLong(SPConstant.REMEBERID,0)!=0){
            edt_acc.setText(SPUtils.getInstance().getLong(SPConstant.REMEBERID)+"");
        }else {
            edt_acc.setHint("请输入uid");
        }
        initEditview();
        mKeyboardChangeListener = KeyboardChangeListener.create(this);
        mKeyboardChangeListener.setKeyboardListener(this);

    }



    @Override
    public void showLoading() {

    }

    private void initEditview() {
        edt_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!TextUtils.isEmpty(edt_acc.getText().toString()) && !TextUtils.isEmpty(charSequence.toString())) {

                    tv_operate.setBackground(drawablebutton);
                } else {
                    tv_operate.setBackground(drawablebuttonno);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        edt_acc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!TextUtils.isEmpty(edt_pwd.getText().toString()) && !TextUtils.isEmpty(charSequence.toString())) {

                    tv_operate.setBackground(drawablebutton);
                } else {
                    tv_operate.setBackground(drawablebuttonno);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
    @Override
    public void onBackPressed() {
        long mNowTime = System.currentTimeMillis();//获取第一次按键时间
        if ((mNowTime - mPressedTime) > 2000) {//比较两次按键时间差
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mPressedTime = mNowTime;
        } else {//退出程序
            this.finish();
            System.exit(0);
        }
    }
    @OnClick({  R.id.tv_login, R.id.tv_register,R.id.tv_operate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
             login=true;
                tv_login.setBackground(getResources().getDrawable(R.drawable.home_radio_bg_left));
                tv_register.setBackground(null);
                if(SPUtils.getInstance().getLong(SPConstant.REMEBERID,0)!=0){
                    edt_acc.setText(SPUtils.getInstance().getLong(SPConstant.REMEBERID)+"");
                }else {
                    edt_acc.setHint("请输入uid");
                }
                tv_login.setTextColor(Color.WHITE);
                tv_register.setTextColor(Color.BLACK);
                tv_operate.setText("登录");
                break;
            case R.id.tv_register:
               login=false;
                tv_register.setBackground(getResources().getDrawable(R.drawable.home_radio_bg));
                tv_login.setBackground(null);
                edt_acc.setHint("请输入用户名");
                edt_acc.getText().clear();
                edt_pwd.getText().clear();
                tv_login.setTextColor(Color.BLACK);
                tv_register.setTextColor(Color.WHITE);
                tv_operate.setText("注册");
                break;

            case R.id.tv_operate:
                if(login){
                    if (TextUtils.isEmpty(edt_acc.getText().toString())) {
                        ToastUtils.showShort("id不能为空");
                        return;
                    }
                    if (!AppValidationMgr.isNumber(edt_acc.getText().toString())) {
                        ToastUtils.showShort("id输入错误");
                        return;
                    }
                    if (TextUtils.isEmpty(edt_pwd.getText().toString())) {
                        ToastUtils.showShort("密码不能为空");
                        return;
                    }
                    mPresenter.loginbyPwd(Long.parseLong(edt_acc.getText().toString()), edt_pwd.getText().toString());
                }else {
                    if (TextUtils.isEmpty(edt_acc.getText().toString())) {
                        ToastUtils.showShort("用户名不能为空");
                        return;
                    }
                    if (edt_acc.getText().toString().length()> MAXNAME) {
                        ToastUtils.showShort("用户名过长");
                        return;
                    }
                    if (TextUtils.isEmpty(edt_pwd.getText().toString())) {
                        ToastUtils.showShort("密码不能为空");
                        return;
                    }
                    mPresenter.registerUser(edt_acc.getText().toString(), edt_pwd.getText().toString());
                }




                break;

        }
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        TipDialog.show(this, message, TipDialog.SHOW_TIME_SHORT, TipDialog.TYPE_ERROR);

//        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void loginbyPwdSuccess(LoginBean data) {
        SPUtils.getInstance().put(SPConstant.UID,data.getUid());
        SPUtils.getInstance().put(SPConstant.SID,data.getSid());

        SPUtils.getInstance().put(SPConstant.REMEBERID,data.getUid());
        launchActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    @Override
    public void registerUserSuccess(LoginBean data) {
        SPUtils.getInstance().put(SPConstant.UID,data.getUid());
        SPUtils.getInstance().put(SPConstant.SID,data.getSid());
        SPUtils.getInstance().put(SPConstant.REMEBERID,data.getUid());
        launchActivity(new Intent(LoginActivity.this,MainActivity.class));
    }

    @Override
    public void onKeyboardChange(boolean isShow, int keyboardHeight) {
        if(isShow){
            SPUtils.getInstance().put(SPConstant.KEYBOARD,keyboardHeight);
        }

    }
}
