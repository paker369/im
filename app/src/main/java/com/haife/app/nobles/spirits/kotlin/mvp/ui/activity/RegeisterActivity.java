package com.haife.app.nobles.spirits.kotlin.mvp.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.haife.app.nobles.spirits.kotlin.app.constant.SPConstant;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.LoginBean;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.haife.app.nobles.spirits.kotlin.di.component.DaggerRegeisterComponent;
import com.haife.app.nobles.spirits.kotlin.mvp.contract.RegeisterContract;
import com.haife.app.nobles.spirits.kotlin.mvp.presenter.RegeisterPresenter;

import com.haife.app.nobles.spirits.kotlin.R;
import com.jingewenku.abrahamcaijin.commonutil.AppValidationMgr;


import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/22/2021 13:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class RegeisterActivity extends BaseActivity<RegeisterPresenter> implements RegeisterContract.View {
    @BindView(R.id.status_bar_view)
    View status_bar_view;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.edt_acc)
    EditText edt_acc;
    @BindView(R.id.edt_pwd)
    TextView edt_pwd;
    @BindView(R.id.tv_login)
    TextView tv_login;


    private Drawable drawablebutton;
    private Drawable drawablebuttonno;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRegeisterComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_regeister; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        drawablebutton = getResources().getDrawable(R.drawable.shape_login_button);
        drawablebuttonno = getResources().getDrawable(R.drawable.shape_nologin_button);
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

                    tv_login.setBackground(drawablebutton);
                } else {
                    tv_login.setBackground(drawablebuttonno);
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

                    tv_login.setBackground(drawablebutton);
                } else {
                    tv_login.setBackground(drawablebuttonno);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
    @OnClick({ R.id.tv_login})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_login:
                if (TextUtils.isEmpty(edt_acc.getText().toString())) {
                    ToastUtils.showShort("用户名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(edt_pwd.getText().toString())) {
                    ToastUtils.showShort("密码不能为空");
                    return;
                }
                mPresenter.registerUser(edt_acc.getText().toString(), edt_pwd.getText().toString());
                break;


        }
    }
    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
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
    public void registerUserSuccess(LoginBean data) {
        SPUtils.getInstance().put(SPConstant.UID,data.getUid());
        SPUtils.getInstance().put(SPConstant.SID,data.getSid());
        launchActivity(new Intent(RegeisterActivity.this,MainActivity.class));

    }
}
