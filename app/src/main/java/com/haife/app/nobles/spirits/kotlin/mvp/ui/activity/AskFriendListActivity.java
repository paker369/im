package com.haife.app.nobles.spirits.kotlin.mvp.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.FriendAskBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.FriendBean;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.adapter.AskFriendAdapter;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.adapter.FriendListAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.haife.app.nobles.spirits.kotlin.di.component.DaggerAskFriendListComponent;
import com.haife.app.nobles.spirits.kotlin.mvp.contract.AskFriendListContract;
import com.haife.app.nobles.spirits.kotlin.mvp.presenter.AskFriendListPresenter;

import com.haife.app.nobles.spirits.kotlin.R;
import com.kongzue.dialog.v2.DialogSettings;
import com.kongzue.dialog.v2.MessageDialog;
import com.kongzue.dialog.v2.SelectDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.kongzue.dialog.v2.DialogSettings.THEME_LIGHT;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/25/2021 09:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AskFriendListActivity extends BaseActivity<AskFriendListPresenter> implements AskFriendListContract.View, OnLoadMoreListener, OnRefreshListener {
    @BindView(R.id.srl_layout)
    SmartRefreshLayout srl_layout;
    @BindView(R.id.rv_message_list)
    RecyclerView rv_message_list;

    @BindView(R.id.rl_back)
    RelativeLayout rl_back;
    @BindView(R.id.center_title)
    TextView center_title;


    int page = 1;
    int limit = 20;
    private AskFriendAdapter askFriendAdapter;
    private View emptyView_noinfo;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAskFriendListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_ask_friend_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        center_title.setText("申请列表");
        initRefreshLayout();
        initRecyclerView();
        mPresenter.addFriendList(page,limit);
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
    /**
     * @date: 2021/1/14 15:48
     * @description 初始化列表
     */
    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_message_list.setLayoutManager(linearLayoutManager);

        askFriendAdapter = new AskFriendAdapter();
        rv_message_list.setAdapter(askFriendAdapter);
        emptyView_noinfo = LayoutInflater.from(this).inflate(R.layout.empty_placehold, (ViewGroup) rv_message_list.getParent(), false);
        TextView tv_empty = emptyView_noinfo.findViewById(R.id.tv_empty);
        askFriendAdapter.setEmptyView(emptyView_noinfo);
        askFriendAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            click(0, askFriendAdapter.getItem(position), view, position);
        });


    }

    private void click(int i, FriendAskBean item, View view, int position) {
        FriendAskBean list = askFriendAdapter.getData().get(position);
        switch (view.getId()) {
            case R.id.acceptnonoButton:
                mPresenter.confirmOrNot(list.getId(),2,position);

                break;
            case R.id.acceptButton:
                mPresenter.confirmOrNot(list.getId(),1,position);
                break;

        }
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
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void confirmOrNotSuccess(int status,int position) {
        FriendAskBean list = askFriendAdapter.getData().get(position);
        list.setStatus(status);
        askFriendAdapter.notifyItemChanged(position);
    }

    @Override
    public void addFriendListSuccess(List<FriendAskBean> data) {
        if (data != null && data.size() > 0) {
            if (page == 1) {
                askFriendAdapter.setNewData(data);
            }else {
                askFriendAdapter.addData(data);
            }
            page++;
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

        mPresenter.addFriendList(page,limit);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page=1;
        mPresenter.addFriendList(page,limit);
    }
}
