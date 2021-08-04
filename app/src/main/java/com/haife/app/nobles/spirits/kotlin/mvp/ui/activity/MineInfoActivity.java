package com.haife.app.nobles.spirits.kotlin.mvp.ui.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.gyf.immersionbar.ImmersionBar;
import com.haife.app.nobles.spirits.kotlin.R;
import com.haife.app.nobles.spirits.kotlin.app.view.CircleImageView;
import com.haife.app.nobles.spirits.kotlin.di.component.DaggerMineInfoComponent;
import com.haife.app.nobles.spirits.kotlin.mvp.contract.MineInfoContract;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.R_ChangeNameBean;
import com.haife.app.nobles.spirits.kotlin.mvp.presenter.MineInfoPresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.kongzue.dialog.v2.SelectDialog;
import com.kongzue.dialog.v2.TipDialog;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/10/2021 13:27
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MineInfoActivity extends BaseActivity<MineInfoPresenter> implements MineInfoContract.View {

    @BindView(R.id.portraitImageView)
    CircleImageView portraitImageView;
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.titleTextView)
    TextView titleTextView;
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;
    @BindView(R.id.tv_id)
    TextView tv_id;
    @BindView(R.id.status_bar_view)
    View status_bar_view;


    long uid;
    String avatar;
    String name;
    private Dialog dia;
    private String friendName;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMineInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_mine_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this)
                .statusBarView(status_bar_view)
                .init();
        centerTitle.setText("好友详情");
        uid = getIntent().getLongExtra("uid", 0);
        avatar = getIntent().getStringExtra("avatar");
        name = getIntent().getStringExtra("name");
        friendName = getIntent().getStringExtra("friendName");
        Glide.with(this)
                .asBitmap()
                .thumbnail(0.6f)
                .load(avatar)
                .into(portraitImageView);
        titleTextView.setText(friendName);
        tv_id.setText("黑猪号：" + uid);
        tv_nickname.setText("用户名：" + name);
    }

    @Override
    public void showLoading() {

    }

    @OnClick({R.id.rl_back})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
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

    /**
     * 弹出添加好友dialog
     */
    public void shwoDialog1() {
        if (dia != null) {
            dia.show();
            return;
        }
        dia = new Dialog(this, R.style.edit_AlertDialog_style);
        dia.setContentView(R.layout.dialog_add);
        TextView tv_cofirm = dia.findViewById(R.id.tv_cofirm);
        TextView tv_cancel = dia.findViewById(R.id.tv_cancel);
        TextView tv_title = dia.findViewById(R.id.tv_title);
        EditText edt_id = dia.findViewById(R.id.edt_id);
        EditText edt_remark = dia.findViewById(R.id.edt_remark);
        tv_cofirm.setText("确认修改");
        tv_cancel.setText("取消修改");
        tv_title.setText("修改备注");
        edt_remark.setVisibility(View.GONE);
        edt_id.setHint("请输入好友备注");
        tv_cofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.changenickname(edt_id.getText().toString(), uid);
                dia.dismiss();
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_id.getText().clear();
                edt_remark.getText().clear();
                dia.dismiss();
            }
        });
        dia.show();
    }

    @Override
    public void killMyself() {
        finish();
    }

    @OnClick({R.id.rl_change_name, R.id.chatButton, R.id.delete_Button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_change_name:
                shwoDialog1();
                break;
            case R.id.chatButton:
                Intent intent = new Intent(this, FriendChatActivity.class);
                intent.putExtra("senderUid", uid);
                intent.putExtra("avatar", avatar);
                intent.putExtra("friendName",titleTextView.getText().toString());
                startActivity(intent);
                break;

            case R.id.delete_Button:
                SelectDialog.show(this, "提示", "确认删除" + name + "吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deleteFriend(uid);
                    }
                });
                break;

        }
    }

    @Override
    public void deleteFriendSuccess() {
        finish();
    }

    @Override
    public void changenicknameSuccess(String friendName) {
        ToastUtils.showShort("备注成功");

        titleTextView.setText(friendName);
    }
}
