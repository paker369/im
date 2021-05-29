package com.haife.app.nobles.spirits.kotlin.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.haife.app.nobles.spirits.kotlin.app.constant.SPConstant;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.FriendBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.MyGroup;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.activity.AskFriendListActivity;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.activity.FriendChatActivity;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.activity.GroupChatActivity;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.adapter.FriendListAdapter;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.adapter.MyGroupListAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.haife.app.nobles.spirits.kotlin.di.component.DaggerGroupComponent;
import com.haife.app.nobles.spirits.kotlin.mvp.contract.GroupContract;
import com.haife.app.nobles.spirits.kotlin.mvp.presenter.GroupPresenter;

import com.haife.app.nobles.spirits.kotlin.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.simple.eventbus.EventBus;

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
public class GroupFragment extends BaseFragment<GroupPresenter> implements GroupContract.View, OnLoadMoreListener, OnRefreshListener {
    @BindView(R.id.srl_layout)
    SmartRefreshLayout srl_layout;
    @BindView(R.id.rv_message_list)
    RecyclerView rv_message_list;
    @BindView(R.id.ll_create_group)
    LinearLayout ll_create_group;
    @BindView(R.id.edt_name)
    EditText edt_name;
    @BindView(R.id.edt_remark)
    EditText edt_remark;

    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    @BindView(R.id.tv_cofirm)
    TextView tv_cofirm;

    @BindView(R.id.tv_new_group)
    TextView tv_new_group;




    int page = 1;
    int limit = 20;
    MyGroupListAdapter myGroupListAdapter;
    private View emptyView_noinfo;
    public static GroupFragment newInstance() {
        GroupFragment fragment = new GroupFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerGroupComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_group, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRefreshLayout();
        initRecyclerView();

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.myGroupList(page,limit);
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

        myGroupListAdapter = new MyGroupListAdapter(0);
        rv_message_list.setAdapter(myGroupListAdapter);
        emptyView_noinfo = LayoutInflater.from(getActivity()).inflate(R.layout.empty_placehold, (ViewGroup) rv_message_list.getParent(), false);
        TextView tv_empty = emptyView_noinfo.findViewById(R.id.tv_empty);
        myGroupListAdapter.setEmptyView(emptyView_noinfo);
        myGroupListAdapter.setOnItemClickListener((adapter, view, position) -> {
            MyGroup listBean = myGroupListAdapter.getData().get(position);
            Intent intent = new Intent(getActivity(), GroupChatActivity.class);
            intent.putExtra("groupid", listBean.getGroupId());
            intent.putExtra("avatar", listBean.getGroup().getAvatar());
            intent.putExtra("name", listBean.getGroup().getName());
            intent.putExtra("remark", listBean.getGroup().getRemark());
            intent.putExtra("ismine", (listBean.getGroup().getUid()== SPConstant.MYUID));

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
        mPresenter.myGroupList(page,limit);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        mPresenter.myGroupList(page,limit);
    }
    @OnClick({R.id.tv_new_group,R.id.tv_cancel,R.id.tv_cofirm,})
    public void onViewClick(View view) {
        switch (view.getId()) {

            case R.id.tv_new_group:
                tv_new_group.setVisibility(View.GONE);
                ll_create_group.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_cancel:
                ll_create_group.setVisibility(View.GONE);
                tv_new_group.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_cofirm:
                if (TextUtils.isEmpty(edt_name.getText().toString())) {
                    ToastUtils.showShort("群名不能为空");
                    return;
                }
                mPresenter.createGroup(edt_name.getText().toString(),edt_remark.getText().toString());
                ll_create_group.setVisibility(View.GONE);
                tv_new_group.setVisibility(View.VISIBLE);
                break;
        }
    }
    @Override
    public void myGroupListSuccess(List<MyGroup> data) {
        TextView tv_empty = emptyView_noinfo.findViewById(R.id.tv_empty);
        tv_empty.setText("暂无数据");
        myGroupListAdapter.setEmptyView(emptyView_noinfo);
        if(page==1){
            myGroupListAdapter.setNewData(data);
        }else {
            myGroupListAdapter.addData(data);
        }
        page++;
        EventBus.getDefault().post(myGroupListAdapter.getData(), SPConstant.GROUPMESSAGE);

    }

    @Override
    public void deleteGroupSuccess() {
        mPresenter.myGroupList(page,limit);
    }

    @Override
    public void createGroupSuccess(MyGroup.GroupBean data) {

//        MyGroup myGroup=new MyGroup();
//        myGroup.setGroup(data);
//        myGroupListAdapter.getData().add(0,myGroup);
//        myGroupListAdapter.notifyItemInserted(0);
        page=1;
        mPresenter.myGroupList(page,limit);
    }
}
