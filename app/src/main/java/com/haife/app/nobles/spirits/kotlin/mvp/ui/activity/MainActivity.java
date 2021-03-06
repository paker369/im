package com.haife.app.nobles.spirits.kotlin.mvp.ui.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.azhon.appupdate.config.UpdateConfiguration;
import com.azhon.appupdate.listener.OnButtonClickListener;
import com.azhon.appupdate.manager.DownloadManager;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chaychan.library.BottomBarLayout;
import com.example.songzeceng.studyofretrofit.item.PersonProto;
import com.gyf.immersionbar.ImmersionBar;
import com.haife.app.nobles.spirits.kotlin.R;
import com.haife.app.nobles.spirits.kotlin.app.constant.SPConstant;
import com.haife.app.nobles.spirits.kotlin.app.view.CircleImageView;
import com.haife.app.nobles.spirits.kotlin.app.view.PopupArrow;
import com.haife.app.nobles.spirits.kotlin.app.view.PopupFriendCircle;
import com.haife.app.nobles.spirits.kotlin.di.component.DaggerMainComponent;
import com.haife.app.nobles.spirits.kotlin.mvp.contract.MainContract;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.ChangeInfoBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.GroupMsgBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.LoginInfoBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.MessageBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.MyGroup;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.UserBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.VersionBean;
import com.haife.app.nobles.spirits.kotlin.mvp.presenter.MainPresenter;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.fragment.FriendFragment;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.fragment.GroupFragment;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.fragment.MessageFragment;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.utlis.ImageUtils;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.utlis.PhotoSelectSingleUtile;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.jingewenku.abrahamcaijin.commonutil.AppValidationMgr;
import com.kongzue.dialog.v2.SelectDialog;
import com.kongzue.dialog.v2.TipDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.zhangke.websocket.SimpleListener;
import com.zhangke.websocket.SocketListener;
import com.zhangke.websocket.WebSocketHandler;
import com.zhangke.websocket.WebSocketManager;
import com.zhangke.websocket.WebSocketSetting;
import com.zhangke.websocket.response.ErrorResponse;

import org.java_websocket.client.WebSocketClient;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import razerdp.basepopup.BasePopupWindow;

import static com.haife.app.nobles.spirits.kotlin.app.constant.SPConstant.curVersionCode;
import static com.haife.app.nobles.spirits.kotlin.app.view.PopupFriendCircle.blur;
import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/22/2021 11:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">?????????????????????</a>
 * ================================================
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, PopupFriendCircle.ClickLisetener {


    @BindView(R.id.bbl)

    BottomBarLayout bbl;
    @BindView(R.id.nvp_layout)
    ViewPager nvpLayout;
    @BindView(R.id.drawerlayout)
    DrawerLayout drawerlayout;
    @BindView(R.id.portraitImageView)
    CircleImageView portraitImageView;
    @BindView(R.id.groupNameTextView)
    TextView groupNameTextView;
    @BindView(R.id.tv_remark)
    TextView tv_remark;
    @BindView(R.id.tv_invitecode)
    TextView tv_invitecode;
    @BindView(R.id.actionButton)
    TextView actionButton;
    @BindView(R.id.status_bar_view)
    View status_bar_view;


    final String[] mTitles = {
            "??????", "??????", "??????"
    };
    private List<Fragment> mFragmentList = new ArrayList<>();
    private Dialog dia;
    private Dialog dia2;
    private WebSocketClient mSocket;
    private Handler mHandler;
    private Dialog dia3;
    long mPressedTime;
    //?????????????????????
    private List<LocalMedia> mSelectList = new ArrayList<>();
    private Dialog dia4;
    private WebSocketManager manager;
    private PopupArrow mPopupArrow;
    private String apkUrl;
    private String message;
    private String versionname;
    private DownloadManager manager1;
    private int versioncode;

    //?????????????????????????????????
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // ????????????????????????
                    mSelectList = PictureSelector.obtainMultipleResult(data);
                    if (mSelectList != null && mSelectList.size() > 0) {
//                        ImageUtils.getPic(ImageUtils.selectPhotoShow(this, mSelectList.get(0)), civHeader, this, R.mipmap.ic_launcher_round);

                        MultipartBody.Builder builder = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM);
                        File file = new File(ImageUtils.selectPhotoShow(this, mSelectList.get(0)));
                        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);//????????????
                        builder.addFormDataPart("file", file.getName(), body);
                        builder.addFormDataPart("type", "1");
                        builder.addFormDataPart("uid", SPUtils.getInstance().getLong(SPConstant.UID, 0) + "");
                        builder.addFormDataPart("groupId", "");
                        List<MultipartBody.Part> parts = builder.build().parts();
                        mPresenter.uploadAvatar(parts);
//                        upload(file,AppConstants.qiniutoken);
                    }
                    break;
            }
        }
    }
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //??????????????????,?????????????????????
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main; //???????????????????????????????????? setContentView(id) ??????????????????,????????? 0
    }

    private void initWebSocket() {
        WebSocketSetting setting = new WebSocketSetting();
        //?????????????????????????????? wss://echo.websocket.org
        setting.setConnectUrl("ws://www.haoqbo.com/ws");//??????
//        setting.setHttpHeaders(map);

        //????????????????????????
        setting.setConnectTimeout(8 * 1000);

        //????????????????????????
        setting.setConnectionLostTimeout(0);

        //??????????????????????????????????????????????????????????????????????????????????????????
        setting.setReconnectFrequency(10);

        //??????????????????????????????????????????
        //???????????? WebSocketHandler.registerNetworkChangedReceiver(context) ??????????????????????????????
        setting.setReconnectWithNetworkChanged(true);

        //?????? init ???????????????????????? WebSocketManager ??????
        manager = WebSocketHandler.init(setting);
        //????????????
        manager.start();

        // 1?????????ws
        // 2????????? ????????????  uid sid  type = 1

        //?????????????????? AndroidManifest ?????????????????????????????????
        //????????????????????????????????????
        WebSocketHandler.registerNetworkChangedReceiver(this);
    }


    //    public  String transferTime (String s){
//
//
//        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.CHINA);
//        Date date = new Date();
//        try{
//            date = sdf.parse(s);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        String formatStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
//return  formatStr;
//
//
//
//    }
    private Runnable heartBeatRunnable = new Runnable() {
        @Override
        public void run() {
            if (manager.isConnect()) {

                PersonProto.WSBaseReqProto.Builder builder = PersonProto.WSBaseReqProto.newBuilder();
                builder.setUid(SPUtils.getInstance().getLong(SPConstant.UID));
                builder.setSid(SPUtils.getInstance().getString(SPConstant.SID));
                builder.setType(0);
                PersonProto.WSBaseReqProto person = builder.build();
                manager.send(person.toByteArray());

            }


            mHandler.postDelayed(this, 5000);
        }
    };


    private SocketListener socketListener = new SimpleListener() {
        @Override
        public void onConnected() {
//            appendMsgDisplay("onConnected");
//            LogUtils.warnInfo("?????????ws????????????");
            PersonProto.WSBaseReqProto.Builder builder = PersonProto.WSBaseReqProto.newBuilder();
            builder.setUid(SPUtils.getInstance().getLong(SPConstant.UID));
            builder.setSid(SPUtils.getInstance().getString(SPConstant.SID));
            builder.setType(1);
            PersonProto.WSBaseReqProto person = builder.build();
            manager.send(person.toByteArray());
            getHandler().post(heartBeatRunnable);
//            WebSocketHandler.getDefault().send(person.toString());
        }

        @Override
        public void onConnectFailed(Throwable e) {

        }

        @Override
        public void onDisconnect() {
//            appendMsgDisplay("onDisconnect");
//            LogUtils.warnInfo("?????????ws???????????????");
        }

        @Override
        public void onSendDataError(ErrorResponse errorResponse) {
//            appendMsgDisplay("onSendDataError:" + errorResponse.toString());
//            errorResponse.release();
        }

        @Override
        public <T> void onMessage(String message, T data) {
//            WebSocketHandler.getDefault().getSetting().getConnectUrl();
//            LogUtils.debugInfo("111?????????ws????????????" + message);
//            WebsocketBean webSocketResultBean = null;
            if(message.equals("123")){
                return;
            }
            try {
//                webSocketResultBean = new Gson().fromJson(message, WebsocketBean.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            if(webSocketResultBean==null){return;}
//            EventBus.getDefault().post(webSocketResultBean);
        }

        @Override
        public <T> void onMessage(ByteBuffer bytes, T data) {
//            LogUtils.debugInfo("222?????????ws????????????" + bytes);

//            PersonProto.Person.Builder builder = PersonProto.Person.newBuilder();

            PersonProto.WSBaseResProto wsBaseResProto = null;
            try {
                wsBaseResProto = PersonProto.WSBaseResProto.parseFrom(bytes.array());

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (wsBaseResProto != null) {

                String content=wsBaseResProto.getMessage().getMsgContent();
                int msgType=wsBaseResProto.getMessage().getMsgType();
                int type=wsBaseResProto.getType();
                long receiveid=wsBaseResProto.getMessage().getReceiveId();
                long senderid= wsBaseResProto.getUser().getUid();
                String name=wsBaseResProto.getUser().getName();
                String avatar=wsBaseResProto.getUser().getAvatar();
//                String createtime= transferTime(wsBaseResProto.getCreateTime());

//                LogUtils.debugInfo("222?????????ws??????????????????" + );
//                LogUtils.debugInfo("222?????????ws??????????????????????????????" + wsBaseResProto.getMessage().getMsgType());
//                LogUtils.debugInfo("222?????????ws??????????????????id" + );
//                LogUtils.debugInfo("222?????????ws??????????????????" + );
//                LogUtils.debugInfo("222?????????ws?????????????????????id" + wsBaseResProto.getUser().getUid());
//
                if(type==1){
                    //????????????
                    EventBus.getDefault().post(new MessageBean(0,0,senderid,msgType,
                          content,""
                           ),SPConstant.RECEIVEWSSINGLECHATE);
                }else {
                    //????????????
                    EventBus.getDefault().post(new GroupMsgBean(receiveid,senderid,msgType,content,"",new UserBean(name,avatar)),SPConstant.RECEIVEWSGROUPCHATE);
                }

            }


        }
//       connectRunnable();

    };


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        boolean regiester = getIntent().getBooleanExtra("regiester", false);
        drawerlayout.closeDrawers();
        ImmersionBar.with(this)
                .statusBarView(status_bar_view)
                .statusBarDarkFont(true)   //????????????????????????????????????????????????
                .init();
        initFragment();
        initWebSocket();
        WebSocketHandler.getDefault().addListener(socketListener);
        getCurrentVersion();
//        if (Build.VERSION.SDK_INT >= 23) {
//            String[] mPermissionList = new String[]{
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//                    , Manifest.permission.CAMERA
//                    , Manifest.permission.READ_EXTERNAL_STORAGE
//            };
//            ActivityCompat.requestPermissions(this, mPermissionList, 123);
//        }
        mPresenter.getversion(2, SPConstant.curVersionName);

    }
    @Override
    public void onBackPressed() {
        long mNowTime = System.currentTimeMillis();//???????????????????????????
        if ((mNowTime - mPressedTime) > 2000) {//???????????????????????????
            Toast.makeText(this, "????????????????????????", Toast.LENGTH_SHORT).show();
            mPressedTime = mNowTime;
        } else {//????????????
            this.finish();
            System.exit(0);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loginInfo();
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                    LogUtils.debugInfo("????????????????????????"+manager.isConnect());
                if (manager.needReConnect()) {
                    manager.reconnect();
                }
            }
        }, 1000);
    }

    @Override
    public void showLoading() {

    }



    /**
     * @date: 2021/1/16 9:58
     * @description ?????????fragment
     */
    private void initFragment() {

        mFragmentList.add(MessageFragment.newInstance());
        mFragmentList.add(FriendFragment.newInstance());
        mFragmentList.add(GroupFragment.newInstance());

        nvpLayout.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        nvpLayout.setOffscreenPageLimit(2);

        bbl.setViewPager(nvpLayout);

        nvpLayout.setCurrentItem(0, false);

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
    public void loginInfoSuccess(LoginInfoBean data) {
//        Glide.with(this)
//                .asBitmap()
//                .thumbnail(0.6f)
//                .load(data.getAvatar())
//
//                .into(iv_header);
//        tv_nickname.setText(data.getName());
//        tv_remark.setText(data.getRemark());
        SPUtils.getInstance().put(SPConstant.HEADER, data.getAvatar());
        SPUtils.getInstance().put(SPConstant.USERNAME, data.getName());
        SPUtils.getInstance().put(SPConstant.REMARKE, data.getRemark());
        Glide.with(this)
                .asBitmap()
                .thumbnail(0.6f)
                .load(data.getAvatar())

                .into(portraitImageView);
        groupNameTextView.setText(data.getName());
        tv_remark.setText(data.getRemark());
        tv_invitecode.setText(data.getInvitationCode());
        EventBus.getDefault().post(data, SPConstant.loginInfoSuccess);
    }

    @Override
    public void addFriendSuccess() {
        if (dia != null && dia.isShowing())
            dia.dismiss();
        ToastUtils.showShort("???????????????");
    }

    @Override
    public void addGroupSuccess(MyGroup data) {
        if (dia2 != null && dia2.isShowing())
            dia2.dismiss();
        onResume();
        ToastUtils.showShort("???????????????");
    }

    @Override
    public void changeinfoSuccess(ChangeInfoBean data) {
        ToastUtils.showShort("??????????????????");
        groupNameTextView.setText(data.getName());
        tv_remark.setText(data.getRemark());
    }

    @Override
    public void uploadAvatarSuccess(String data) {
        ToastUtils.showShort("????????????");
//        avatar=data;
        SPUtils.getInstance().put(SPConstant.HEADER, data);
        Glide.with(this)
                .asBitmap()
                .thumbnail(0.6f)
                .load(data)
                .apply(new RequestOptions().placeholder(R.mipmap.mandefult))
                .into(portraitImageView);
        mPresenter.loginInfo();
    }

    @Override
    public void getInvitationCodeSuccess() {
        ToastUtils.showShort("????????????");
    }

    @Override
    public void getversionSuccess(VersionBean data) {
        if (data != null) {
            com.luck.picture.lib.tools.SPUtils.getInstance().put(SPConstant.Version, data.getVersionCurrent());
            com.luck.picture.lib.tools.SPUtils.getInstance().put(SPConstant.ApkUrl, data.getApkUrl());
            switch (data.getType()) {
                case 0:

                    break;
                case 1:
                    apkUrl = data.getApkUrl();
                    message = data.getUpgradeMsg();
                    versionname = data.getVersionCurrent();
                    startUpdate3();
                    break;

                case 2:
                    apkUrl = data.getApkUrl();
                    message = data.getUpgradeMsg();
                    versionname = data.getVersionCurrent();
                    startUpdate3();
                    break;

            }

        }
    }

    //    @Override
//    public void addfriend() {
//        shwoDialog1();
//    }
    private void startUpdate3() {
        /*
         * ??????????????????????????????
         * ?????????
         */
        UpdateConfiguration configuration = new UpdateConfiguration()
                //??????????????????
                .setEnableLog(true)
                //????????????????????????
                //.setHttpManager()
                //????????????????????????????????????
                .setJumpInstallPage(true)
                //??????????????????????????? (??????????????????demo???????????????)
                //.setDialogImage(R.drawable.ic_dialog)
                //?????????????????????
                //.setDialogButtonColor(Color.parseColor("#E743DA"))
                //?????????????????????????????????????????????????????????
                //.setDialogProgressBarColor(Color.parseColor("#E743DA"))
                //???????????????????????????
                .setDialogButtonTextColor(Color.WHITE)
                //?????????????????????????????????
                .setShowNotification(true)
                //??????????????????????????????toast
//            .setShowBgdToast(true)
                //????????????????????????
                .setUsePlatform(true)
                //??????????????????

                .setForcedUpgrade(false)


                //????????????????????????????????????
                .setButtonClickListener(new OnButtonClickListener() {
                    @Override
                    public void onButtonClick(int id) {

                    }
                });
        //???????????????????????????
//                .setOnDownloadListener(listenerAdapter);
        manager1 = DownloadManager.getInstance(this);
        manager1.setApkName("zhuge" + com.luck.picture.lib.tools.SPUtils.getInstance().getString(SPConstant.Version) + ".apk")
                .setApkUrl(apkUrl)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setShowNewerToast(true)
                .setConfiguration(configuration)
                .setApkVersionCode(100000)
                .setApkVersionName(versionname)
                .setApkDescription(message)
                .download();
    }
    private Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        return mHandler;
    }

    @Subscriber(tag = SPConstant.ADDGROUP)
    public void receiveadd(int i) {
        shwoDialog2();
    }

    @Subscriber(tag = SPConstant.OPENSLIDE)
    public void receiveopenslide(int i) {

        drawerlayout.openDrawer(GravityCompat.START);
    }

    @Subscriber(tag = SPConstant.ADDPERSON)
    public void receiveaddperson(int i) {

        shwoDialog1();
    }

    @Subscriber(tag = SPConstant.SHOWINFO)
    public void receiveshowifo(int i) {

        shwoDialog3();
    }
    @Subscriber(tag = SPConstant.LOGOUT)
    public void receivelogout(int i) {
        SelectDialog.show(this, "??????", "?????????????????????", "??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        }, "??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

    }


    public void toShow(View v) {
        if (mPopupArrow == null) {
            mPopupArrow = new PopupArrow(v.getContext());
        }
        mPopupArrow.setBlurBackgroundEnable(blur);
        mPopupArrow.setPopupGravity(BasePopupWindow.GravityMode.RELATIVE_TO_ANCHOR, Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        mPopupArrow.showPopupWindow(v);
    }

    /**
     * ??????????????????dialog
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

        tv_title.setText("????????????");
        tv_cofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edt_id.getText().toString())) {
                    ToastUtils.showShort("?????????????????????");
                    return;
                }
                if (!AppValidationMgr.isNumber(edt_id.getText().toString())) {
                    ToastUtils.showShort("????????????????????????");
                    return;
                }
//                if (TextUtils.isEmpty(edt_remark.getText().toString())) {
//                    ToastUtils.showShort("??????????????????");
//                    return;
//                }
                mPresenter.addFriend(Long.parseLong(edt_id.getText().toString()), edt_remark.getText().toString());

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

    /**
     * ??????????????????dialog
     */
    public void shwoDialog2() {
        if (dia2 != null) {
            dia2.show();
            return;
        }
        dia2 = new Dialog(this, R.style.edit_AlertDialog_style);
        dia2.setContentView(R.layout.dialog_add);
        TextView tv_cofirm = dia2.findViewById(R.id.tv_cofirm);
        TextView tv_cancel = dia2.findViewById(R.id.tv_cancel);
        TextView tv_title = dia2.findViewById(R.id.tv_title);
        EditText edt_id = dia2.findViewById(R.id.edt_id);
        EditText edt_remark = dia2.findViewById(R.id.edt_remark);
        edt_id.setHint("???????????????");
        edt_remark.setVisibility(View.GONE);
        tv_title.setText("????????????");
        tv_cofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edt_id.getText().toString())) {
                    ToastUtils.showShort("??????????????????");
                    return;
                }
                if (!AppValidationMgr.isNumber(edt_id.getText().toString())) {
                    ToastUtils.showShort("?????????????????????");
                    return;
                }
//                if (TextUtils.isEmpty(edt_remark.getText().toString())) {
//                    ToastUtils.showShort("??????????????????");
//                    return;
//                }
                mPresenter.addGroup(Long.parseLong(edt_id.getText().toString()));

            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_id.getText().clear();
                edt_remark.getText().clear();
                dia2.dismiss();
            }
        });
        dia2.show();
    }

    /**
     * ??????????????????dialog
     */
    public void shwoDialog4() {
        if (dia4 != null) {
            dia4.show();
            return;
        }
        dia4 = new Dialog(this, R.style.edit_AlertDialog_style);
        dia4.setContentView(R.layout.dialog_add);
        TextView tv_cofirm = dia4.findViewById(R.id.tv_cofirm);
        TextView tv_cancel = dia4.findViewById(R.id.tv_cancel);
        TextView tv_title = dia4.findViewById(R.id.tv_title);
        EditText edt_id = dia4.findViewById(R.id.edt_id);
        EditText edt_remark = dia4.findViewById(R.id.edt_remark);
        edt_id.setText(SPUtils.getInstance().getString(SPConstant.USERNAME));
        edt_remark.setText(SPUtils.getInstance().getString(SPConstant.REMARKE));
        tv_title.setText("????????????");
        tv_cofirm.setText("????????????");
        tv_cofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edt_id.getText().toString())) {
                    ToastUtils.showShort("??????????????????");
                    return;
                }

//                if (TextUtils.isEmpty(edt_remark.getText().toString())) {
//                    ToastUtils.showShort("??????????????????");
//                    return;
//                }
                mPresenter.changeinfo(edt_id.getText().toString(), SPUtils.getInstance().getString(SPConstant.HEADER), edt_remark.getText().toString());
                dia4.dismiss();
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dia4.dismiss();
            }
        });
        dia4.show();
    }


    /**
     * ??????????????????dialog
     */
    public void shwoDialog3() {
        if (dia3 != null) {
            dia3.show();
            return;
        }
        dia3 = new Dialog(this, R.style.edit_AlertDialog_style);
        dia3.setContentView(R.layout.dialog_show_info);
        TextView tv_uid = dia3.findViewById(R.id.tv_uid);
        TextView tv_cofirm = dia3.findViewById(R.id.tv_cofirm);
        TextView tv_name = dia3.findViewById(R.id.tv_name);
        CircleImageView iv_header = dia3.findViewById(R.id.iv_header);
        tv_cofirm.setText("???????????????");
        Glide.with(this)
                .asBitmap()
                .thumbnail(0.6f)
                .load(SPUtils.getInstance().getString(SPConstant.HEADER))
                .apply(new RequestOptions().placeholder(R.mipmap.mandefult))
                .into(iv_header);
        tv_name.setText(SPUtils.getInstance().getString(SPConstant.USERNAME));
        tv_uid.setText("???????????? " + SPUtils.getInstance().getLong(SPConstant.UID));
        tv_cofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

                cmb.setText(SPUtils.getInstance().getLong(SPConstant.UID) + "");
                ToastUtils.showShort("????????????");
            }
        });


        dia3.show();
    }


//
//    @Override
//    public void addgroup() {
//        shwoDialog2();
//    }

    @OnClick({R.id.actionButton, R.id.portraitImageView, R.id.tv_xiugaiziliao, R.id.ll_invitecode})
    public void onViewClick(View view) {
        switch (view.getId()) {

            case R.id.actionButton:
                receivelogout(1);
                break;
            case R.id.tv_xiugaiziliao:
                shwoDialog4();
                break;
            case R.id.portraitImageView:
                PhotoSelectSingleUtile.selectPhoto(this, mSelectList, 1);

                break;
            case R.id.ll_invitecode:
                ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(tv_invitecode.getText().toString());
                toShow(tv_invitecode);
                break;

        }
    }

    /**
     * ?????????????????????????????????
     */
    private void getCurrentVersion() {
        try {
            PackageInfo info = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
            SPConstant.curVersionName = info.versionName;
            curVersionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public void logout() {
        SPUtils.getInstance().remove(SPConstant.UID);
        SPUtils.getInstance().remove(SPConstant.SID);
        SPUtils.getInstance().remove(SPConstant.USERNAME);
        SPUtils.getInstance().remove(SPConstant.HEADER);
        launchActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

//    @Override
//    public void setting() {
//
//    }

    @Override
    public void mineinfo() {
//        DialogSettings.dialog_theme = THEME_LIGHT;
//        DialogSettings.use_blur = true;
//        MessageDialog.show(this, "????????????", "??????UID???"+SPUtils.getInstance().getLong(SPConstant.UID), "?????????", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//            }
//        });
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);

        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }
    }
}
