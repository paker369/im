package com.haife.app.nobles.spirits.kotlin.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.haife.app.nobles.spirits.kotlin.R;
import com.haife.app.nobles.spirits.kotlin.di.component.DaggerMessageComponent;
import com.haife.app.nobles.spirits.kotlin.mvp.contract.MessageContract;
import com.haife.app.nobles.spirits.kotlin.mvp.presenter.MessagePresenter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;

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
    int page = 1;
    int limit = 20;
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
    /**
     * @date: 2021/1/14 15:48
     * @description 初始化列表
     */
    private void initRecyclerView() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
//        rv_message_list.setLayoutManager(linearLayoutManager);
//
//                fotCompetetionTypeAdapter = new FotCompetetionTypeAdapter();
//        rv_message_list.setAdapter(fotCompetetionTypeAdapter);
//                emptyView_noinfo = LayoutInflater.from(getActivity()).inflate(R.layout.empty_placehold, (ViewGroup) match_ry.getParent(), false);
//                ImageView iv_empty = emptyView_noinfo.findViewById(R.id.iv_empty);
//                Glide.with(this).load(R.drawable.loadingg).into(iv_empty);
//                fotCompetetionTypeAdapter.setEmptyView(emptyView_noinfo);
//                fotCompetetionTypeAdapter.setOnItemClickListener((adapter, view, position) -> {
//                    FootballAllStatusBean.FootballAllStatusitemBean listBean = fotCompetetionTypeAdapter.getData().get(position);
//                    Intent intent = new Intent(getActivity(), FootballDetailsActivity.class);
//                    intent.putExtra("teama", listBean.getaTeamName());
//                    intent.putExtra("teamb", listBean.getbTeamName());
//                    intent.putExtra("idteama", listBean.getaTeamId());
//                    intent.putExtra("idteamb", listBean.getbTeamId());
//                    intent.putExtra("score", listBean.getMatchScore());
//                    intent.putExtra("icona", listBean.getaLogo());
//                    intent.putExtra("iconb", listBean.getbLogo());
//                    intent.putExtra("state", listBean.getMatchType());
//                    intent.putExtra("nameid", listBean.getNamiId());
//                    intent.putExtra("stringdown", listBean.getMatchNameZh() + listBean.getMatchStartTime());
//                    intent.putExtra("matchid", listBean.getMatchId());
//                    startActivity(intent);
//                });



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
}
