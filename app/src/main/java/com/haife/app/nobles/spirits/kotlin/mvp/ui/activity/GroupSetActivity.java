package com.haife.app.nobles.spirits.kotlin.mvp.ui.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.haife.app.nobles.spirits.kotlin.R;
import com.haife.app.nobles.spirits.kotlin.app.constant.SPConstant;
import com.haife.app.nobles.spirits.kotlin.di.component.DaggerGroupSetComponent;
import com.haife.app.nobles.spirits.kotlin.mvp.contract.GroupSetContract;
import com.haife.app.nobles.spirits.kotlin.mvp.presenter.GroupSetPresenter;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.utlis.ImageUtils;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.utlis.PhotoSelectSingleUtile;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.kongzue.dialog.v2.DialogSettings;
import com.kongzue.dialog.v2.MessageDialog;
import com.kongzue.dialog.v2.SelectDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.kongzue.dialog.v2.DialogSettings.THEME_LIGHT;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/09/2021 16:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class GroupSetActivity extends BaseActivity<GroupSetPresenter> implements GroupSetContract.View {


    @BindView(R.id.rl_back)
    RelativeLayout rl_back;
    @BindView(R.id.center_title)
    TextView center_title;

    @BindView(R.id.portraitImageView)
    ImageView portraitImageView;


    @BindView(R.id.tv_change_header)
    TextView tv_change_header;

    @BindView(R.id.groupNameTextView)
    TextView groupNameTextView;


    @BindView(R.id.edit)
    ImageView edit;

    @BindView(R.id.tv_remark)
    TextView tv_remark;

    @BindView(R.id.tv_id)
    TextView tv_id;


    @BindView(R.id.actionButton)
    TextView actionButton;

    String avatar;
    String name;
    String remark;
    long groupid;
    boolean ismine;
    private Dialog dia;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerGroupSetComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_group_set; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        avatar = getIntent().getStringExtra("avatar");
        name = getIntent().getStringExtra("name");
        remark = getIntent().getStringExtra("remark");
        groupid = getIntent().getLongExtra("groupid", 0);
        ismine = getIntent().getBooleanExtra("ismine", false);
        center_title.setText("群详情");
        tv_id.setText(groupid + "");
        groupNameTextView.setText(name);
        tv_remark.setText(remark);

        if (ismine) {
            tv_change_header.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
            actionButton.setText("解散群组");
        }
        Glide.with(this)
                .asBitmap()
                .thumbnail(0.6f)
                .load(avatar)
                .apply(new RequestOptions().placeholder(R.mipmap.mandefult))
                .into(portraitImageView);


    }
    //选择的图片集合
    private List<LocalMedia> mSelectList = new ArrayList<>();
    //选择头像图片之后的回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    mSelectList = PictureSelector.obtainMultipleResult(data);
                    if (mSelectList != null && mSelectList.size() > 0) {
//                        ImageUtils.getPic(ImageUtils.selectPhotoShow(this, mSelectList.get(0)), civHeader, this, R.mipmap.ic_launcher_round);

                        MultipartBody.Builder builder = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM);
                        File file = new File(ImageUtils.selectPhotoShow(this, mSelectList.get(0)));
                        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);//表单类型
                        builder.addFormDataPart("file", file.getName(), body);
                        builder.addFormDataPart("type", "2");
                        builder.addFormDataPart("uid", SPUtils.getInstance().getLong(SPConstant.UID,0)+"");
                        builder.addFormDataPart("groupId", groupid+"");
                        List<MultipartBody.Part> parts = builder.build().parts();
                        mPresenter.uploadAvatar(parts);
//                        upload(file,AppConstants.qiniutoken);
                    }
                    break;
            }
        }
    }


    @OnClick({R.id.rl_back, R.id.tv_change_header, R.id.edit, R.id.actionButton})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_change_header:
                PhotoSelectSingleUtile.selectPhoto(this, mSelectList, 1);

                break;
            case R.id.edit:
                shwoDialog1();
                break;

            case R.id.actionButton:
                if (ismine) {
                    SelectDialog.show(this, "提示", "确认解散群组吗", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPresenter.deleteMyGroup(groupid);
                        }
                    });
                } else {
                    SelectDialog.show(this, "提示", "确认退出群组吗", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPresenter.deleteGroup(groupid);
                        }
                    });
                }
                break;
//            case R.id.iv_info:
//                DialogSettings.dialog_theme = THEME_LIGHT;
//                DialogSettings.use_blur = true;
//                MessageDialog.show(this, "群详情", "群ID："+groupid, "知道了", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                });
//                break;
        }
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
        edt_id.setHint("输入新群名");
        edt_remark.setHint("输入新签名");
        tv_title.setText("修改群信息");
        tv_cofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (TextUtils.isEmpty(edt_id.getText().toString())) {
//                    ToastUtils.showShort("账号不能为空");
//                    return;
//                }
//                if (!AppValidationMgr.isInteger(edt_id.getText().toString())) {
//                    ToastUtils.showShort("账号不能非数字");
//                    return;
//                }
//                if (TextUtils.isEmpty(edt_remark.getText().toString())) {
//                    ToastUtils.showShort("密码不能为空");
//                    return;
//                }
                mPresenter.groupUpdate(edt_id.getText().toString(), avatar, edt_remark.getText().toString(), groupid);

            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dia.dismiss();
            }
        });
        dia.show();
    }

    @Override
    public void showLoading() {

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
    public void deleteMyGroupSuccess() {
        ToastUtils.showShort("已解散群组");
        launchActivity(new Intent(GroupSetActivity.this, MainActivity.class));
    }

    @Override
    public void groupUpdateSuccess() {
        ToastUtils.showShort("修改信息成功");

    }

    @Override
    public void uploadSuccess(String data) {

    }

    @Override
    public void uploadAvatarSuccess(String data) {
ToastUtils.showShort("更换成功");
        avatar=data;
        Glide.with(this)
                .asBitmap()
                .thumbnail(0.6f)
                .load(data)
                .apply(new RequestOptions().placeholder(R.mipmap.mandefult))
                .into(portraitImageView);

    }

    @Override
    public void deleteGroupSuccess() {
        ToastUtils.showShort("已退出群组");
        launchActivity(new Intent(GroupSetActivity.this, MainActivity.class));
    }
}
