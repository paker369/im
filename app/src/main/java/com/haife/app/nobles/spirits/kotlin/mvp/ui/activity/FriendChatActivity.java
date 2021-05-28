package com.haife.app.nobles.spirits.kotlin.mvp.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.gyf.immersionbar.ImmersionBar;
import com.haife.app.nobles.spirits.kotlin.R;
import com.haife.app.nobles.spirits.kotlin.app.constant.SPConstant;
import com.haife.app.nobles.spirits.kotlin.app.utils.KeyBoardListener;
import com.haife.app.nobles.spirits.kotlin.app.view.ChatInputLayout;
import com.haife.app.nobles.spirits.kotlin.app.view.ChatMsgRootLayout;
import com.haife.app.nobles.spirits.kotlin.app.view.CircleImageView;
import com.haife.app.nobles.spirits.kotlin.di.component.DaggerFriendChatComponent;
import com.haife.app.nobles.spirits.kotlin.mvp.contract.FriendChatContract;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.MessageBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.ReadOtherInfoBean;
import com.haife.app.nobles.spirits.kotlin.mvp.presenter.FriendChatPresenter;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.adapter.ChatMessageAdapter;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.utlis.BarUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.jingewenku.abrahamcaijin.commonutil.AppDateMgr;
import com.kongzue.dialog.v2.SelectDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


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
public class FriendChatActivity extends BaseActivity<FriendChatPresenter> implements FriendChatContract.View, ChatInputLayout.OnInputLayoutListener, OnRefreshListener {

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
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;
    @BindView(R.id.tv_remark)
    TextView tv_remark;
    @BindView(R.id.status_bar_view)
    View status_bar_view;
    @BindView(R.id.iv_header)
    CircleImageView iv_header;

    ChatMessageAdapter chatMessageAdapter;
    private LinearLayoutManager layouts;
    int page = 1;
    int limit = 20;
    long senderUid;
    private int firstCompletelyVisibleItemPosition=-1;
    List<MessageBean> messagepool = new ArrayList<>();
    private Handler mHandler;
    private int messengecount;
    private List<MessageBean> listBeans = new ArrayList<>();
    ;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFriendChatComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_friend_chat; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        senderUid =  getIntent().getLongExtra("senderUid", 0);
        ImmersionBar.with(this)
                .statusBarView(status_bar_view)
                .barAlpha(0)
                .applySystemFits(false)
                .init();
        KeyBoardListener.getInstance(this).init();
        initRecycler();
        input_layout.setLayoutListener(this);
        input_layout.bindInputLayout(this, rll);
        mPresenter.friendList(page, limit, senderUid);
        mPresenter.read(senderUid);
    }

    @Override
    public void showLoading() {

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
    @OnClick({R.id.iv_delete, R.id.iv_header, R.id.newmessege})
    public void onViewClick(View view) {
        switch (view.getId()) {

            case R.id.iv_delete:
                SelectDialog.show(this, "提示", "确认删除好友吗", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deleteFriend(senderUid);
                    }
                });
                break;
            case R.id.iv_header:

                break;
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
//                Log.i(TAG, "firstCompletelyVisibleItemPosition: "+firstCompletelyVisibleItemPosition);
                if (firstCompletelyVisibleItemPosition <= 3) {
                    //                    Log.i(TAG, "滑动到di部");
                    //recycleview滚动时不能通知刷新
                    if (!messagepool.isEmpty()) {
                        getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                chatMessageAdapter.addData(0, messagepool);
                                messagepool.clear();
                                messengecount = 0;
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
        chatMessageAdapter = new ChatMessageAdapter(listBeans);
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
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.friendList(page, limit, senderUid);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void sendBtnClick(CharSequence textMessage) {
        mPresenter.sendMsg(textMessage.toString(), 0, senderUid);
    }

    @Override
    public void photoBtnClick() {

    }

    @Override
    public void cameraBtnClick() {

    }

    @Override
    public void exLayoutShow() {

    }

    @Override
    public void friendMsgListSuccess(List<MessageBean> data) {
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

    @Override
    public void sendMsgtSuccess(int type, String content) {
        MessageBean data = new MessageBean(SPConstant.MYUID, senderUid, SPConstant.MYUID, type, content.toString(), AppDateMgr.todayYyyyMmDdHhMmSs());

        input_layout.hideOverView();
        messagepool.add(0, data);
        if (firstCompletelyVisibleItemPosition > 70) {
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
    public void readSuccess(ReadOtherInfoBean data) {
        Glide.with(this)
                .asBitmap()
                .thumbnail(0.6f)
                .load(data.getAvatar())

                .into(iv_header);
        LogUtils.debugInfo("测试朋友的头像是" + data.getAvatar());
        LogUtils.debugInfo("测试view" + (iv_header == null));
        tv_nickname.setText(data.getName());
        tv_remark.setText(data.getRemark());
    }

    @Override
    public void deleteFriendSuccess() {
        ToastUtils.showShort("已删除好友");
        finish();

    }

    public boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        return firstCompletelyVisibleItemPosition <= 1;
    }

    @Subscriber(tag = SPConstant.RECEIVEWSSINGLECHATE)
    public void receivemsg(MessageBean data) {
        if (data.getSenderUid() == senderUid) {
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
