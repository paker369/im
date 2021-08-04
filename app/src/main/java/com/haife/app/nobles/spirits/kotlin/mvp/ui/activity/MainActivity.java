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
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
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
            "消息", "朋友", "群组"
    };
    private List<Fragment> mFragmentList = new ArrayList<>();
    private Dialog dia;
    private Dialog dia2;
    private WebSocketClient mSocket;
    private Handler mHandler;
    private Dialog dia3;
    long mPressedTime;
    //选择的图片集合
    private List<LocalMedia> mSelectList = new ArrayList<>();
    private Dialog dia4;
    private WebSocketManager manager;
    private PopupArrow mPopupArrow;
    private String apkUrl;
    private String message;
    private String versionname;
    private DownloadManager manager1;
    private int versioncode;

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
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    private void initWebSocket() {
        WebSocketSetting setting = new WebSocketSetting();
        //连接地址，必填，例如 wss://echo.websocket.org
        setting.setConnectUrl("ws://www.haoqbo.com/ws");//必填
//        setting.setHttpHeaders(map);

        //设置连接超时时间
        setting.setConnectTimeout(8 * 1000);

        //设置心跳间隔时间
        setting.setConnectionLostTimeout(0);

        //设置断开后的重连次数，可以设置的很大，不会有什么性能上的影响
        setting.setReconnectFrequency(10);

        //网络状态发生变化后是否重连，
        //需要调用 WebSocketHandler.registerNetworkChangedReceiver(context) 方法注册网络监听广播
        setting.setReconnectWithNetworkChanged(true);

        //通过 init 方法初始化默认的 WebSocketManager 对象
        manager = WebSocketHandler.init(setting);
        //启动连接
        manager.start();

        // 1、链接ws
        // 2、发送 一条数据  uid sid  type = 1

        //注意，需要在 AndroidManifest 中配置网络状态获取权限
        //注册网路连接状态变化广播
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
//            LogUtils.warnInfo("测试主ws链接上了");
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
//            LogUtils.warnInfo("测试主ws断开了链接");
        }

        @Override
        public void onSendDataError(ErrorResponse errorResponse) {
//            appendMsgDisplay("onSendDataError:" + errorResponse.toString());
//            errorResponse.release();
        }

        @Override
        public <T> void onMessage(String message, T data) {
//            WebSocketHandler.getDefault().getSetting().getConnectUrl();
//            LogUtils.debugInfo("111测试主ws收到消息" + message);
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
//            LogUtils.debugInfo("222测试主ws收到消息" + bytes);

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

//                LogUtils.debugInfo("222测试主ws收到消息内容" + );
//                LogUtils.debugInfo("222测试主ws收到消息图片还是文本" + wsBaseResProto.getMessage().getMsgType());
//                LogUtils.debugInfo("222测试主ws收到消息接收id" + );
//                LogUtils.debugInfo("222测试主ws收到消息分类" + );
//                LogUtils.debugInfo("222测试主ws收到消息发送人id" + wsBaseResProto.getUser().getUid());
//
                if(type==1){
                    //私聊消息
                    EventBus.getDefault().post(new MessageBean(0,0,senderid,msgType,
                          content,""
                           ),SPConstant.RECEIVEWSSINGLECHATE);
                }else {
                    //群聊消息
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
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
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
        long mNowTime = System.currentTimeMillis();//获取第一次按键时间
        if ((mNowTime - mPressedTime) > 2000) {//比较两次按键时间差
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mPressedTime = mNowTime;
        } else {//退出程序
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
//                    LogUtils.debugInfo("测试检查下掉线没"+manager.isConnect());
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
     * @description 初始化fragment
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
        ToastUtils.showShort("请求已发送");
    }

    @Override
    public void addGroupSuccess(MyGroup data) {
        if (dia2 != null && dia2.isShowing())
            dia2.dismiss();
        onResume();
        ToastUtils.showShort("请求已发送");
    }

    @Override
    public void changeinfoSuccess(ChangeInfoBean data) {
        ToastUtils.showShort("修改资料成功");
        groupNameTextView.setText(data.getName());
        tv_remark.setText(data.getRemark());
    }

    @Override
    public void uploadAvatarSuccess(String data) {
        ToastUtils.showShort("更换成功");
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
        ToastUtils.showShort("获取成功");
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
         * 整个库允许配置的内容
         * 非必选
         */
        UpdateConfiguration configuration = new UpdateConfiguration()
                //输出错误日志
                .setEnableLog(true)
                //设置自定义的下载
                //.setHttpManager()
                //下载完成自动跳动安装页面
                .setJumpInstallPage(true)
                //设置对话框背景图片 (图片规范参照demo中的示例图)
                //.setDialogImage(R.drawable.ic_dialog)
                //设置按钮的颜色
                //.setDialogButtonColor(Color.parseColor("#E743DA"))
                //设置对话框强制更新时进度条和文字的颜色
                //.setDialogProgressBarColor(Color.parseColor("#E743DA"))
                //设置按钮的文字颜色
                .setDialogButtonTextColor(Color.WHITE)
                //设置是否显示通知栏进度
                .setShowNotification(true)
                //设置是否提示后台下载toast
//            .setShowBgdToast(true)
                //设置是否上报数据
                .setUsePlatform(true)
                //设置强制更新

                .setForcedUpgrade(false)


                //设置对话框按钮的点击监听
                .setButtonClickListener(new OnButtonClickListener() {
                    @Override
                    public void onButtonClick(int id) {

                    }
                });
        //设置下载过程的监听
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
        SelectDialog.show(this, "提示", "确认退出登录？", "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        }, "取消", new DialogInterface.OnClickListener() {
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

        tv_title.setText("添加好友");
        tv_cofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edt_id.getText().toString())) {
                    ToastUtils.showShort("黑猪号不能为空");
                    return;
                }
                if (!AppValidationMgr.isNumber(edt_id.getText().toString())) {
                    ToastUtils.showShort("黑猪号不能非数字");
                    return;
                }
//                if (TextUtils.isEmpty(edt_remark.getText().toString())) {
//                    ToastUtils.showShort("密码不能为空");
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
     * 弹出添加好友dialog
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
        edt_id.setHint("请输入群号");
        edt_remark.setVisibility(View.GONE);
        tv_title.setText("添加群组");
        tv_cofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edt_id.getText().toString())) {
                    ToastUtils.showShort("群号不能为空");
                    return;
                }
                if (!AppValidationMgr.isNumber(edt_id.getText().toString())) {
                    ToastUtils.showShort("群号不能非数字");
                    return;
                }
//                if (TextUtils.isEmpty(edt_remark.getText().toString())) {
//                    ToastUtils.showShort("密码不能为空");
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
     * 弹出修改信息dialog
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
        tv_title.setText("修改资料");
        tv_cofirm.setText("确认修改");
        tv_cofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edt_id.getText().toString())) {
                    ToastUtils.showShort("昵称不能为空");
                    return;
                }

//                if (TextUtils.isEmpty(edt_remark.getText().toString())) {
//                    ToastUtils.showShort("密码不能为空");
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
     * 弹出添加好友dialog
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
        tv_cofirm.setText("复制黑猪号");
        Glide.with(this)
                .asBitmap()
                .thumbnail(0.6f)
                .load(SPUtils.getInstance().getString(SPConstant.HEADER))
                .apply(new RequestOptions().placeholder(R.mipmap.mandefult))
                .into(iv_header);
        tv_name.setText(SPUtils.getInstance().getString(SPConstant.USERNAME));
        tv_uid.setText("黑猪号： " + SPUtils.getInstance().getLong(SPConstant.UID));
        tv_cofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

                cmb.setText(SPUtils.getInstance().getLong(SPConstant.UID) + "");
                ToastUtils.showShort("复制成功");
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
     * 获取当前客户端版本信息
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
//        MessageDialog.show(this, "我的信息", "我的UID："+SPUtils.getInstance().getLong(SPConstant.UID), "知道了", new DialogInterface.OnClickListener() {
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
