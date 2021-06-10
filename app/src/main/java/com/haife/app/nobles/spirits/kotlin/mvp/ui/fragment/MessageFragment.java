package com.haife.app.nobles.spirits.kotlin.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.haife.app.nobles.spirits.kotlin.R;
import com.haife.app.nobles.spirits.kotlin.app.constant.SPConstant;
import com.haife.app.nobles.spirits.kotlin.di.component.DaggerMessageComponent;
import com.haife.app.nobles.spirits.kotlin.mvp.contract.MessageContract;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.FriendBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.GroupMsgBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.LoginInfoBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.MessageBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.MyGroup;
import com.haife.app.nobles.spirits.kotlin.mvp.presenter.MessagePresenter;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.activity.AskFriendListActivity;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.activity.FriendChatActivity;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.activity.GroupChatActivity;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.adapter.FriendListAdapter;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.adapter.MyGroupListAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/22/2021 13:15
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MessageFragment extends BaseFragment<MessagePresenter> implements MessageContract.View, OnLoadMoreListener, OnRefreshListener {
    @BindView(R.id.srl_layout)
    SmartRefreshLayout srl_layout;
    @BindView(R.id.rv_message_list1)
    RecyclerView rv_message_list1;
    @BindView(R.id.rv_message_list2)
    RecyclerView rv_message_list2;
    @BindView(R.id.iv_header)
    ImageView iv_header;
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;
    int page = 1;
    int limit = 20;


    MyGroupListAdapter myGroupListAdapter;
    FriendListAdapter friendListAdapter;
    private View emptyView_noinfo;

    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMessageComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRefreshLayout();
        initRecyclerView();
    }
    @Subscriber(tag = SPConstant.loginInfoSuccess)
    public void loginInfoSuccess(LoginInfoBean data) {
        Glide.with(this)
                .asBitmap()
                .thumbnail(0.6f)
                .load(data.getAvatar())

                .into(iv_header);
        tv_nickname.setText(data.getName());
    }
    /**
     * @date: 2021/1/14 15:48
     * @description 初始化列表
     */
    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rv_message_list1.setLayoutManager(linearLayoutManager);

        myGroupListAdapter = new MyGroupListAdapter(1);
        rv_message_list1.setAdapter(myGroupListAdapter);
//        emptyView_noinfo = LayoutInflater.from(getActivity()).inflate(R.layout.empty_placehold, (ViewGroup) rv_message_list.getParent(), false);
//        TextView tv_empty = emptyView_noinfo.findViewById(R.id.tv_empty);
//        myGroupListAdapter.setEmptyView(emptyView_noinfo);
        myGroupListAdapter.setOnItemClickListener((adapter, view, position) -> {
            MyGroup listBean = myGroupListAdapter.getData().get(position);
            listBean.setUnMsgCount(0);
            myGroupListAdapter.notifyItemChanged(position);
            mPresenter.cleargroupMsg(listBean.getGroupId());
            Intent intent = new Intent(getActivity(), GroupChatActivity.class);
            intent.putExtra("groupid", listBean.getGroupId());
            intent.putExtra("avatar", listBean.getGroup().getAvatar());
            intent.putExtra("name", listBean.getGroup().getName());
            intent.putExtra("remark", listBean.getGroup().getRemark());
            intent.putExtra("ismine", (listBean.getGroup().getUid() == SPUtils.getInstance().getLong(SPConstant.UID)));
            startActivity(intent);
        });

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
        rv_message_list2.setLayoutManager(linearLayoutManager2);
        friendListAdapter = new FriendListAdapter(1);
        rv_message_list2.setAdapter(friendListAdapter);
//        emptyView_noinfo = LayoutInflater.from(getActivity()).inflate(R.layout.empty_placehold, (ViewGroup) rv_message_list.getParent(), false);
//        TextView tv_empty = emptyView_noinfo.findViewById(R.id.tv_empty);
//        friendListAdapter.setEmptyView(emptyView_noinfo);
        friendListAdapter.setOnItemClickListener((adapter, view, position) -> {

            FriendBean listBean = friendListAdapter.getData().get(position);
            listBean.setUnMsgCount(0);
            myGroupListAdapter.notifyItemChanged(position);
            mPresenter.clearfriendMsg(listBean.getFriendUid());
            Intent intent = new Intent(getActivity(), FriendChatActivity.class);
            intent.putExtra("senderUid", listBean.getFriendUid());
            intent.putExtra("avatar", listBean.getUser().getAvatar());
            startActivity(intent);
            listBean.setUnMsgCount(0);

        });

    }
    /**
     * @date: 2021/1/20 15:50
     * @description 初始化刷新
     */
    private void initRefreshLayout() {
        srl_layout.setOnRefreshListener(this);
        srl_layout.setOnLoadMoreListener(this);
        srl_layout.setEnableRefresh(false);
        srl_layout.setEnableLoadMore(false);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        try {
            if (srl_layout.getState() == RefreshState.Refreshing) {
                srl_layout.finishRefresh();
            } else if (srl_layout.getState() == RefreshState.Loading) {
                srl_layout.finishLoadMore();
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

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }
    @OnClick({R.id.iv_more, R.id.iv_header, R.id.tv_nickname, R.id.iv_person_add, R.id.iv_group_add})
    public void onViewClick(View view) {
        switch (view.getId()) {

            case R.id.iv_person_add:
                EventBus.getDefault().post(1,SPConstant.ADDPERSON);
                break;
            case R.id.iv_group_add:
                EventBus.getDefault().post(1,SPConstant.ADDGROUP);
                break;
            case R.id.iv_header:
                EventBus.getDefault().post(1,SPConstant.SHOWINFO);
                break;
            case R.id.iv_more:
                EventBus.getDefault().post(1,SPConstant.LOGOUT);
                break;

        }
    }

    @Subscriber(tag = SPConstant.FRIENDMESSAGE)
    public void receivefriendmsg(List<FriendBean> data) {
        friendListAdapter.setNewData(data);
    }

    @Subscriber(tag = SPConstant.GROUPMESSAGE)
    public void receivegroupmsg(List<MyGroup> data) {
        myGroupListAdapter.setNewData(data);
    }


    @Subscriber(tag = SPConstant.RECEIVEWSSINGLECHATE)
    public void wsreceivemsg(MessageBean data) {
   long id=data.getSenderUid();
        LogUtils.debugInfo("测试消息frag收到消息接收" + id);

        for(int    i=0;    i<friendListAdapter.getData().size();    i++)    {
            if(friendListAdapter.getData().get(i).getFriendUid()==id) {
                LogUtils.debugInfo("测试消息frag执行了" + id);
                friendListAdapter.getData().get(i).setLastMsgContent(data.getMsgContent());
                friendListAdapter.getData().get(i).setUnMsgCount(1);
                friendListAdapter.notifyItemChanged(i);
            }

        }

    }

    @Subscriber(tag = SPConstant.RECEIVEWSGROUPCHATE)
    public void wsreceivegroupmsg(GroupMsgBean data) {
        long id=data.getGroupId();
        for(int    i=0;    i<myGroupListAdapter.getData().size();    i++)    {
            if(myGroupListAdapter.getData().get(i).getGroupId()==id) {
                myGroupListAdapter.getData().get(i).setLastMsgContent(data.getMsgContent());
                myGroupListAdapter.getData().get(i).setUnMsgCount(1);
                myGroupListAdapter.getData().get(i).setLastMsgTime("");
                myGroupListAdapter.notifyItemChanged(i);
            }

        }
    }

    @Override
    public void cleargroupMsgSuccess() {
LogUtils.debugInfo("测试删除群组未读成功");
    }

    @Override
    public void clearfriendMsgSuccess() {
        LogUtils.debugInfo("测试删除私聊未读成功");
    }
}
