package com.haife.app.nobles.spirits.kotlin.mvp.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.haife.app.nobles.spirits.kotlin.R;
import com.haife.app.nobles.spirits.kotlin.app.constant.SPConstant;
import com.haife.app.nobles.spirits.kotlin.di.component.DaggerFriendComponent;
import com.haife.app.nobles.spirits.kotlin.mvp.contract.FriendContract;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.FriendAskBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.FriendBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.LoginInfoBean;
import com.haife.app.nobles.spirits.kotlin.mvp.presenter.FriendPresenter;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.activity.AskFriendListActivity;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.activity.FriendChatActivity;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.adapter.FriendListAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.jingewenku.abrahamcaijin.commonutil.AppValidationMgr;
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
 * Created by MVPArmsTemplate on 05/22/2021 13:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class FriendFragment extends BaseFragment<FriendPresenter> implements FriendContract.View, OnLoadMoreListener, OnRefreshListener {
    @BindView(R.id.srl_layout)
    SmartRefreshLayout srl_layout;
    @BindView(R.id.rv_message_list)
    RecyclerView rv_message_list;
    @BindView(R.id.tv_new_friend)
    TextView tv_new_friend;
    @BindView(R.id.center_num)
    TextView center_num;
    @BindView(R.id.iv_header)
    ImageView iv_header;
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;


    int page2 = 1;
    int limit2 = 100;
    int page = 1;
    int limit = 20;
    FriendListAdapter friendListAdapter;
    private View emptyView_noinfo;
    private Dialog dia;

    public static FriendFragment newInstance() {
        FriendFragment fragment = new FriendFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerFriendComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friend, container, false);
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
    @Override
    public void onResume() {
        super.onResume();

        mPresenter.friendList(page, limit);
        mPresenter.addFriendList(page2, limit2);
    }

    /**
     * @date: 2021/1/20 15:50
     * @description 初始化刷新
     */
    private void initRefreshLayout() {
        srl_layout.setOnRefreshListener(this);
        srl_layout.setOnLoadMoreListener(this);
        srl_layout.setEnableRefresh(true);
        srl_layout.setEnableLoadMore(true);
    }

    /**
     * @date: 2021/1/14 15:48
     * @description 初始化列表
     */
    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rv_message_list.setLayoutManager(linearLayoutManager);

        friendListAdapter = new FriendListAdapter(0);
        rv_message_list.setAdapter(friendListAdapter);
        emptyView_noinfo = LayoutInflater.from(getActivity()).inflate(R.layout.empty_placehold, (ViewGroup) rv_message_list.getParent(), false);
        TextView tv_empty = emptyView_noinfo.findViewById(R.id.tv_empty);
        friendListAdapter.setEmptyView(emptyView_noinfo);
        friendListAdapter.setOnItemClickListener((adapter, view, position) -> {
            FriendBean listBean = friendListAdapter.getData().get(position);
            Intent intent = new Intent(getActivity(), FriendChatActivity.class);
            intent.putExtra("senderUid", listBean.getFriendUid());
            intent.putExtra("avatar", listBean.getUser().getAvatar());
            LogUtils.debugInfo("测试发送朋友的头像是" + listBean.getUser().getAvatar());
            startActivity(intent);
        });


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

        mPresenter.friendList(page, limit);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        mPresenter.friendList(page, limit);
        mPresenter.addFriendList(page2, limit2);
    }

    @Override
    public void friendListSuccess(List<FriendBean> data) {
        TextView tv_empty = emptyView_noinfo.findViewById(R.id.tv_empty);
        tv_empty.setText("暂无数据");
        friendListAdapter.setEmptyView(emptyView_noinfo);

        if (page == 1) {

            friendListAdapter.setNewData(data);
        } else {

            friendListAdapter.addData(data);
        }
        page++;

        EventBus.getDefault().post(friendListAdapter.getData(), SPConstant.FRIENDMESSAGE);
    }

    @Override
    public void addFriendListSuccess(List<FriendAskBean> data) {
        int num=0;
     for(int    i=0;    i<data.size();    i++)    {
        if(data.get(i).getStatus()==0) {
            num++;
        }

    }



        if (num > 0) {
            center_num.setText(data.size()+"");
            center_num.setVisibility(View.VISIBLE);
        } else {
            center_num.setVisibility(View.GONE);
        }
    }

    @Override
    public void deleteFriendSuccess() {
        page = 1;
        mPresenter.friendList(page, limit);
    }

    @OnClick({R.id.iv_more, R.id.iv_header, R.id.tv_nickname, R.id.iv_person_add, R.id.iv_group_add,R.id.rl_new_friend})
        public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.rl_new_friend:
                launchActivity(new Intent(getActivity(), AskFriendListActivity.class));
                break;
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
}
