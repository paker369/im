package com.haife.app.nobles.spirits.kotlin.mvp.ui.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.zhouwei.library.CustomPopWindow;
import com.haife.app.nobles.spirits.kotlin.R;
import com.haife.app.nobles.spirits.kotlin.app.constant.SPConstant;
import com.haife.app.nobles.spirits.kotlin.app.view.ChatInputLayout;
import com.haife.app.nobles.spirits.kotlin.app.view.ChatMsgRootLayout;
import com.haife.app.nobles.spirits.kotlin.di.component.DaggerGroupChatComponent;
import com.haife.app.nobles.spirits.kotlin.mvp.contract.GroupChatContract;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.GroupMemberBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.GroupMsgBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.UserBean;
import com.haife.app.nobles.spirits.kotlin.mvp.presenter.GroupChatPresenter;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.adapter.ChatMessageAdapter1;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.adapter.GroupMemberAdapter;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.utlis.BarUtils;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.utlis.ImageUtils;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.utlis.PhotoSelectSingleUtile;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.jingewenku.abrahamcaijin.commonutil.AppDateMgr;
import com.kongzue.dialog.v2.DialogSettings;
import com.kongzue.dialog.v2.MessageDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.simple.eventbus.Subscriber;

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
 * Created by MVPArmsTemplate on 05/22/2021 14:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class GroupChatActivity extends BaseActivity<GroupChatPresenter> implements GroupChatContract.View, ChatInputLayout.OnInputLayoutListener, OnRefreshListener {
    @BindView(R.id.ll_root)
    LinearLayout ll_root;
    @BindView(R.id.rll)
    ChatMsgRootLayout rll;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.color_recycler)
    RecyclerView color_recycler;


    @BindView(R.id.rv_newmessege)
    RelativeLayout rv_newmessege;
    @BindView(R.id.newmessege)
    TextView newmessege;
    @BindView(R.id.input_layout)
    ChatInputLayout input_layout;
    @BindView(R.id.tv_groupname)
    TextView tv_nickname;

    @BindView(R.id.status_bar_view)
    View status_bar_view;
    @BindView(R.id.iv_setting)
    ImageView iv_setting;

    @BindView(R.id.rl_top)
    RelativeLayout rl_top;

    String avatar;
    String name;
    String remark;
    long groupid;
    ChatMessageAdapter1 chatMessageAdapter;
    private LinearLayoutManager layouts;
    int page = 1;
    int limit = 20;
    private int firstCompletelyVisibleItemPosition;
    List<GroupMsgBean> messagepool = new ArrayList<>();
    private Handler mHandler;
    private int messengecount;
    private List<GroupMsgBean> listBeans = new ArrayList<>();
    private CustomPopWindow customPopupWindow;
    private GroupMemberAdapter mshowPeriodAdapter;
    List<GroupMemberBean> memberList;
    private Dialog dia;
   boolean ismine;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerGroupChatComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_group_chat; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        BarUtils.setStatusBarAlpha(this, 0, true);
        BarUtils.setStatusBarLightMode(this, true);
        avatar = getIntent().getStringExtra("avatar");
        name = getIntent().getStringExtra("name");
        remark = getIntent().getStringExtra("remark");
        groupid = getIntent().getLongExtra("groupid", 0);
        ismine = getIntent().getBooleanExtra("ismine", false);
        LogUtils.debugInfo("测试这是我的群吗"+ismine);
//        if(ismine){
//            iv_setting.setVisibility(View.VISIBLE);
//        }
//        Glide.with(this)
//                .asBitmap()
//                .thumbnail(0.6f)
//                .load(avatar)
//.apply(new RequestOptions().placeholder(R.mipmap.mandefult))
//                .into(iv_header);
//        LogUtils.debugInfo("测试朋友的头像是"+data.getAvatar());
//        LogUtils.debugInfo("测试view"+(iv_header==null));
        tv_nickname.setText(name);
//        tv_remark.setText(remark);
//        ImmersionBar.with(this)
//                .statusBarView(status_bar_view)
//                .barAlpha(0)
//                .applySystemFits(false)
//                .init();
//        KeyBoardListener.getInstance(this).init();
        initRecycler();
        input_layout.setLayoutListener(this);
        input_layout.bindInputLayout(this, rll);

        mPresenter.groupMsgList(page, limit, groupid);
        mPresenter.groupMemberList(groupid);
    }

    @Override
    public void showLoading() {

    }

    private void initRecycler() {
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setEnableLoadMore(false);
        layouts = new LinearLayoutManager(this);
        layouts.setStackFromEnd(true);//列表再底部开始展示，反转后由上面开始展示
        layouts.setReverseLayout(true);//列表翻转
        color_recycler.setLayoutManager(layouts);
        color_recycler.setHasFixedSize(true);
        color_recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                Log.i(TAG, "--------------------------------------");
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                firstCompletelyVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
                Log.i(TAG, "测试firstCompletelyVisibleItemPosition: "+firstCompletelyVisibleItemPosition);
                if (firstCompletelyVisibleItemPosition <= 3) {
                    //                    Log.i(TAG, "滑动到di部");
                    //recycleview滚动时不能通知刷新
                    if (!messagepool.isEmpty()) {
                        getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                chatMessageAdapter.addData(0, messagepool);
                                messagepool.clear();
                                messengecount=0;
                                chatMessageAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                    rv_newmessege.setVisibility(View.GONE);
                    messengecount = 0;
                }
//
//
//                int lastCompletelyVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
//                Log.i(TAG, "lastCompletelyVisibleItemPosition: "+lastCompletelyVisibleItemPosition);
//                if(lastCompletelyVisibleItemPosition==layoutManager.getItemCount()-1){

//                }


            }
        });
        chatMessageAdapter = new ChatMessageAdapter1(listBeans);
        chatMessageAdapter.setEmptyView(R.layout.empty_placehold, color_recycler);
        color_recycler.setAdapter(chatMessageAdapter);


    }

    private Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        return mHandler;
    }


    @Override
    public void hideLoading() {
        try {
            if (refreshLayout.getState() == RefreshState.Refreshing) {
                refreshLayout.finishRefresh();
            } else if (refreshLayout.getState() == RefreshState.Loading) {
                refreshLayout.finishLoadMore();
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void groupMsgListSuccess(List<GroupMsgBean> data) {
        if (data != null && data.size() > 0) {
            if (page == 1) {
                getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        chatMessageAdapter.getData().clear();
                    }
                });
            }
            getHandler().post(new Runnable() {
                @Override
                public void run() {
                    chatMessageAdapter.addData(data);
                }
            });
            page++;
        }
    }

    @OnClick({R.id.iv_setting, R.id.newmessege, R.id.iv_back, R.id.tv_groupname, R.id.iv_pulldown})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_groupname:
                DialogSettings.dialog_theme = THEME_LIGHT;
                DialogSettings.use_blur = true;
                MessageDialog.show(this, "群详情", "群ID：" + groupid, "知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                break;
            case R.id.iv_pulldown:
                showPopListView();
                break;
//            case R.id.iv_delete:
//                if(ismine){
//                    SelectDialog.show(this, "提示", "确认解散群组吗", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            mPresenter.deleteMyGroup(groupid);
//                        }
//                    });
//                }else {
//                    SelectDialog.show(this, "提示", "确认退出群组吗", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            mPresenter.deleteGroup(groupid);
//                        }
//                    });
//                }
//
//
//                break;
//            case R.id.iv_header:
//            case R.id.iv_show_member:
//                showPopListView();
//                break;
            case R.id.iv_setting:
//                shwoDialog1();
                Intent intent = new Intent(this, GroupSetActivity.class);
                intent.putExtra("groupid", groupid);
                intent.putExtra("avatar", avatar);
                intent.putExtra("name", name);
                intent.putExtra("remark", remark);
                intent.putExtra("ismine", ismine);
                startActivity(intent);
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
            case R.id.newmessege:
                rv_newmessege.setVisibility(View.GONE);
                messengecount = 0;
                if (firstCompletelyVisibleItemPosition > 70) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            layouts.scrollToPositionWithOffset(0, Integer.MIN_VALUE);
                            color_recycler.scrollToPosition(0);
                        }
                    });

                } else {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            color_recycler.smoothScrollToPosition(0);
                            color_recycler.scrollToPosition(0);
                        }
                    });
                }
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
                mPresenter.groupUpdate(edt_id.getText().toString(), "",edt_remark.getText().toString(),groupid);

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

    private void showPopListView() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop, null);
        //处理popWindow 显示内容
        handleListView(contentView);
        //创建并显示popWindow
        customPopupWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, 600)//显示大小
                .create()
                .showAsDropDown(rl_top, 0, 0);

    }

    private void handleListView(View contentView) {
        RecyclerView recyclerView = contentView.findViewById(R.id.select);
        mshowPeriodAdapter = new GroupMemberAdapter();
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, LinearLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mshowPeriodAdapter);
//        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
//            @Override
//            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
//                mViewPager.setCurrentItem(position);
//                customPopupWindow.dissmiss();
//            }
//        });
        mshowPeriodAdapter.setNewData(memberList);
    }

    @Override
    public void sendGroupMsgSuccess(int type, String content) {
//        GroupMsgBean data = new MessageBean(SPConstant.MYUID, senderUid, SPConstant.MYUID, type, content.toString(), AppDateMgr.todayYyyyMmDdHhMmSs());
        GroupMsgBean data = new GroupMsgBean(groupid, SPUtils.getInstance().getLong(SPConstant.UID), type, content, AppDateMgr.todayYyyyMmDdHhMmSs(), new UserBean(SPConstant.USERNAME, SPConstant.HEADER));
        input_layout.hideOverView();

        if (firstCompletelyVisibleItemPosition > 70) {
            messagepool.add(0, data);
            getHandler().post(new Runnable() {
                @Override
                public void run() {
                    layouts.scrollToPositionWithOffset(0, Integer.MIN_VALUE);
                    color_recycler.scrollToPosition(0);
                }
            });
        }else if(firstCompletelyVisibleItemPosition==0) {
            chatMessageAdapter.addData(0, data);
            chatMessageAdapter.notifyDataSetChanged();
        } else {
            messagepool.add(0, data);
            getHandler().post(new Runnable() {
                @Override
                public void run() {
                    color_recycler.smoothScrollToPosition(0);
                    color_recycler.scrollToPosition(0);
                }
            });
        }
    }



    @Override
    public void groupMemberListSuccess(List<GroupMemberBean> data) {
        LogUtils.debugInfo("测试拿到成员名单" + data.size());
        memberList = data;
    }

    @Override
    public void groupUpdateSuccess() {
        ToastUtils.showShort("修改信息成功");
        finish();
    }

    @Override
    public void deleteMyGroupSuccess() {
        ToastUtils.showShort("已解散群组");
        finish();
    }
    @Override
    public void deleteGroupSuccess() {
        ToastUtils.showShort("已退出群组");
        finish();
    }

    @Override
    public void uploadSuccess(String data) {
        mPresenter.sendGroupMsg(data, 1, groupid);

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
                        List<MultipartBody.Part> parts = builder.build().parts();
                        mPresenter.upload(parts);
//                        upload(file,AppConstants.qiniutoken);
                    }
                    break;
            }
        }
    }


    @Override
    public void sendBtnClick(CharSequence textMessage) {
        mPresenter.sendGroupMsg(textMessage.toString(), 0, groupid);
    }

    @Override
    public void photoBtnClick() {
        PhotoSelectSingleUtile.selectPhoto(this, mSelectList, 1);
    }

    @Override
    public void cameraBtnClick() {

    }

    @Override
    public void exLayoutShow() {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.groupMsgList(page, limit, groupid);
    }

    public boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        return firstCompletelyVisibleItemPosition <= 1;
    }

    @Subscriber(tag = SPConstant.RECEIVEWSGROUPCHATE)
    public void receivemsg(GroupMsgBean data) {

        LogUtils.debugInfo("测试收到群组消息");
        if (data.getGroupId() == groupid) {
            messengecount++;
            if (!isSlideToBottom(color_recycler)) {
                messagepool.add(0, data);
                rv_newmessege.clearAnimation();
//              iv_newmessege.setImageResource(R.mipmap.icon_downto);
                rv_newmessege.setVisibility(View.VISIBLE);
              newmessege.setBackgroundColor(getResources().getColor(R.color.newmesseng));
                newmessege.setText(messengecount + "条新消息");
            } else {

                //recycleview滚动时不能通知刷新
                getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (chatMessageAdapter.getData().size() == 0) {
                            chatMessageAdapter.getData().add(0, data);
                            chatMessageAdapter.notifyDataSetChanged();
                        } else {
                            chatMessageAdapter.getData().add(0, data);
                            //                                chatMessageAdapter.notifyDataSetChanged();
                            chatMessageAdapter.notifyItemInserted(0);
                            color_recycler.scrollToPosition(0);
                        }

                    }
                });


            }
        }
    }
}
