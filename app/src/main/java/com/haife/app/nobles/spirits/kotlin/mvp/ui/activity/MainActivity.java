package com.haife.app.nobles.spirits.kotlin.mvp.ui.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.example.songzeceng.studyofretrofit.item.PersonProto;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.haife.app.nobles.spirits.kotlin.R;
import com.haife.app.nobles.spirits.kotlin.app.constant.SPConstant;
import com.haife.app.nobles.spirits.kotlin.app.view.CircleImageView;
import com.haife.app.nobles.spirits.kotlin.app.view.PopupFriendCircle;
import com.haife.app.nobles.spirits.kotlin.di.component.DaggerMainComponent;
import com.haife.app.nobles.spirits.kotlin.mvp.contract.MainContract;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.GroupMsgBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.LoginInfoBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.MessageBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.MyGroup;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.R_Ws;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.UserBean;
import com.haife.app.nobles.spirits.kotlin.mvp.presenter.MainPresenter;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.fragment.FriendFragment;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.fragment.GroupFragment;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.fragment.MessageFragment;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.jingewenku.abrahamcaijin.commonutil.AppValidationMgr;
import com.kongzue.dialog.v2.DialogSettings;
import com.kongzue.dialog.v2.MessageDialog;
import com.zhangke.websocket.SimpleListener;
import com.zhangke.websocket.SocketListener;
import com.zhangke.websocket.WebSocketHandler;
import com.zhangke.websocket.WebSocketManager;
import com.zhangke.websocket.WebSocketSetting;
import com.zhangke.websocket.response.ErrorResponse;

import org.java_websocket.client.WebSocketClient;
import org.simple.eventbus.EventBus;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.kongzue.dialog.v2.DialogSettings.THEME_DARK;
import static com.kongzue.dialog.v2.DialogSettings.THEME_LIGHT;


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


    @BindView(R.id.tab_indi)

    SlidingTabLayout tab_indi;
    @BindView(R.id.nvp_layout)
    ViewPager nvpLayout;
    @BindView(R.id.iv_header)
    CircleImageView iv_header;
    @BindView(R.id.iv_more)
    ImageView iv_more;
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;
    @BindView(R.id.tv_remark)
    TextView tv_remark;
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
        setting.setConnectUrl("ws://chat.networkheizhu.com/ws");//必填
//        setting.setHttpHeaders(map);

        //设置连接超时时间
        setting.setConnectTimeout(8 * 1000);

        //设置心跳间隔时间
        setting.setConnectionLostTimeout(3);

        //设置断开后的重连次数，可以设置的很大，不会有什么性能上的影响
        setting.setReconnectFrequency(10);

        //网络状态发生变化后是否重连，
        //需要调用 WebSocketHandler.registerNetworkChangedReceiver(context) 方法注册网络监听广播
        setting.setReconnectWithNetworkChanged(true);

        //通过 init 方法初始化默认的 WebSocketManager 对象
        WebSocketManager manager = WebSocketHandler.init(setting);
        //启动连接
        manager.start();

        // 1、链接ws
        // 2、发送 一条数据  uid sid  type = 1

        //注意，需要在 AndroidManifest 中配置网络状态获取权限
        //注册网路连接状态变化广播
        WebSocketHandler.registerNetworkChangedReceiver(this);
    }



    public  String transferTime (String s){


        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.CHINA);
        Date date = new Date();
        try{
            date = sdf.parse(s);
        }catch (Exception e){
            e.printStackTrace();
        }
        String formatStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
return  formatStr;



    }
    private SocketListener socketListener = new SimpleListener() {
        @Override
        public void onConnected() {
//            appendMsgDisplay("onConnected");
            LogUtils.warnInfo("测试主ws链接上了");
            PersonProto.WSBaseReqProto.Builder builder = PersonProto.WSBaseReqProto.newBuilder();
            builder.setUid(SPConstant.MYUID);
            LogUtils.debugInfo("测试sid是" + SPConstant.MYSID);
            builder.setSid(SPConstant.MYSID);
            builder.setType(1);
            PersonProto.WSBaseReqProto person = builder.build();
            R_Ws userInfoBean = new R_Ws(SPConstant.MYUID, SPConstant.MYSID, 1);
            byte[] i = new Gson().toJson(userInfoBean).getBytes();
            WebSocketHandler.getDefault().send(person.toByteArray());
//            WebSocketHandler.getDefault().send(person.toString());
            LogUtils.debugInfo("111测试发送是" + person.toByteArray());
            LogUtils.debugInfo("111测试发送是" + person.toByteString());
        }

        @Override
        public void onConnectFailed(Throwable e) {

        }

        @Override
        public void onDisconnect() {
//            appendMsgDisplay("onDisconnect");
            LogUtils.warnInfo("测试主ws断开了链接");
        }

        @Override
        public void onSendDataError(ErrorResponse errorResponse) {
//            appendMsgDisplay("onSendDataError:" + errorResponse.toString());
//            errorResponse.release();
        }

        @Override
        public <T> void onMessage(String message, T data) {
//            WebSocketHandler.getDefault().getSetting().getConnectUrl();
            LogUtils.debugInfo("111测试主ws收到消息" + message);
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

            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes.array());
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
                int receiveid=(int)wsBaseResProto.getMessage().getReceiveId();
                int senderid= (int)wsBaseResProto.getUser().getUid();
                String name=wsBaseResProto.getUser().getName();
                String avatar=wsBaseResProto.getUser().getAvatar();
                String createtime= transferTime(wsBaseResProto.getCreateTime());

//                LogUtils.debugInfo("222测试主ws收到消息内容" + );
//                LogUtils.debugInfo("222测试主ws收到消息图片还是文本" + wsBaseResProto.getMessage().getMsgType());
//                LogUtils.debugInfo("222测试主ws收到消息接收id" + );
//                LogUtils.debugInfo("222测试主ws收到消息分类" + );
//                LogUtils.debugInfo("222测试主ws收到消息发送人id" + wsBaseResProto.getUser().getUid());
//
                if(type==1){
                    //私聊消息
                    EventBus.getDefault().post(new MessageBean(0,0,senderid,msgType,
                          content,createtime
                           ),SPConstant.RECEIVEWSSINGLECHATE);
                }else {
                    LogUtils.debugInfo("222测试主ws收到消息接收shijian" + wsBaseResProto.getCreateTime());
                    //群聊消息
                    EventBus.getDefault().post(new GroupMsgBean(receiveid,senderid,msgType,content,createtime,new UserBean(name,avatar)),SPConstant.RECEIVEWSGROUPCHATE);
                }

            }


        }
//       connectRunnable();

    };

    //             private void connectRunnable() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                if(mSocket == null){
//                    try {
//                        mSocket = new WebSocketClient(new URI("ws://chat.networkheizhu.com/ws")) {
//                            @Override
//                            public void onOpen(ServerHandshake handshakedata) {
//                                LogUtils.warnInfo("测试主wsonOpen");
//                            }
//
//                            @Override
//                            public void onMessage(String message) {
//                                LogUtils.warnInfo("测试主wsonMessage");
//                            }
//
//                            @Override
//                            public void onClose(int code, String reason, boolean remote) {
//                                LogUtils.warnInfo("测试主wsonClose");
//                            }
//
//                            @Override
//                            public void onError(Exception ex) {
//                                LogUtils.warnInfo("测试主wsonError");
//                            }
//                        };
//                        mSocket.connect();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
//
//    }
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this)
                .statusBarView(status_bar_view)
                .init();
        if (SPUtils.getInstance().getLong(SPConstant.UID, 0) == 0) {
            launchActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        initFragment();
        initWebSocket();
        WebSocketHandler.getDefault().addListener(socketListener);
        tab_indi.getTitleView(0).setCompoundDrawablesRelativeWithIntrinsicBounds(getResources().getDrawable(R.mipmap.message), null, null, null);
        tab_indi.getTitleView(1).setCompoundDrawablesRelativeWithIntrinsicBounds(getResources().getDrawable(R.mipmap.friend), null, null, null);
        tab_indi.getTitleView(2).setCompoundDrawablesRelativeWithIntrinsicBounds(getResources().getDrawable(R.mipmap.group), null, null, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loginInfo();
    }

    @Override
    public void showLoading() {

    }

    private void showPopup(View v, PopupFriendCircle.ClickLisetener lisetener
    ) {
        PopupFriendCircle popupFriendCircle = null;
        if (popupFriendCircle == null) {
            popupFriendCircle = new PopupFriendCircle(this);
            popupFriendCircle.setPopupGravity(Gravity.LEFT | Gravity.BOTTOM);
        }
        popupFriendCircle.linkTo(v);
        popupFriendCircle.setClickcollectLisetener(lisetener);
        popupFriendCircle.showPopupWindow(v);
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

        tab_indi.setViewPager(nvpLayout, mTitles);

        nvpLayout.setCurrentItem(0, false);

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
    public void loginInfoSuccess(LoginInfoBean data) {
        Glide.with(this)
                .asBitmap()
                .thumbnail(0.6f)
                .load(data.getAvatar())

                .into(iv_header);
        tv_nickname.setText(data.getName());
        tv_remark.setText(data.getRemark());
        SPUtils.getInstance().put(SPConstant.HEADER,data.getAvatar());
        SPUtils.getInstance().put(SPConstant.USERNAME,data.getName());
    }

    @Override
    public void addFriendSuccess() {
        if (dia != null && dia.isShowing())
            dia.dismiss();
    }

    @Override
    public void addGroupSuccess(MyGroup data) {
        if (dia2 != null && dia2.isShowing())
            dia2.dismiss();
    }

    @Override
    public void addfriend() {
        shwoDialog1();
    }

    private Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        return mHandler;
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
                    ToastUtils.showShort("账号不能为空");
                    return;
                }
                if (!AppValidationMgr.isInteger(edt_id.getText().toString())) {
                    ToastUtils.showShort("账号不能非数字");
                    return;
                }
//                if (TextUtils.isEmpty(edt_remark.getText().toString())) {
//                    ToastUtils.showShort("密码不能为空");
//                    return;
//                }
                mPresenter.addFriend(Integer.valueOf(edt_id.getText().toString()), edt_remark.getText().toString());

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
        edt_id.setHint("请输入群id");
        edt_remark.setVisibility(View.GONE);
        tv_title.setText("添加群");
        tv_cofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edt_id.getText().toString())) {
                    ToastUtils.showShort("群号不能为空");
                    return;
                }
                if (!AppValidationMgr.isInteger(edt_id.getText().toString())) {
                    ToastUtils.showShort("群号不能非数字");
                    return;
                }
//                if (TextUtils.isEmpty(edt_remark.getText().toString())) {
//                    ToastUtils.showShort("密码不能为空");
//                    return;
//                }
                mPresenter.addGroup(Integer.valueOf(edt_id.getText().toString()));

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

    @Override
    public void addgroup() {
        shwoDialog2();
    }

    @OnClick({R.id.iv_more, R.id.iv_header})
    public void onViewClick(View view) {
        switch (view.getId()) {

            case R.id.iv_more:
                showPopup(iv_more, this);
                break;
            case R.id.iv_header:

                break;

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

    @Override
    public void setting() {

    }

    @Override
    public void mineinfo() {
        DialogSettings.dialog_theme = THEME_LIGHT;
        DialogSettings.use_blur = true;
        MessageDialog.show(this, "我的信息", "我的UID："+SPConstant.MYUID, "知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
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
