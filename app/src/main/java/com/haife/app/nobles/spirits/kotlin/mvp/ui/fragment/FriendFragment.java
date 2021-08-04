package com.haife.app.nobles.spirits.kotlin.mvp.ui.fragment;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
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
import com.bumptech.glide.request.RequestOptions;
import com.haife.app.nobles.spirits.kotlin.R;
import com.haife.app.nobles.spirits.kotlin.app.constant.SPConstant;
import com.haife.app.nobles.spirits.kotlin.app.view.PopupFriendCircle;
import com.haife.app.nobles.spirits.kotlin.di.component.DaggerFriendComponent;
import com.haife.app.nobles.spirits.kotlin.mvp.contract.FriendContract;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.FriendAskBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.FriendBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.LoginInfoBean;
import com.haife.app.nobles.spirits.kotlin.mvp.presenter.FriendPresenter;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.activity.AskFriendListActivity;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.activity.FriendChatActivity;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.activity.MineInfoActivity;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.adapter.FriendListAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.kongzue.dialog.v2.SelectDialog;
import com.kongzue.dialog.v2.TipDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.Iterator;
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
    @BindView(R.id.iv_more)
    ImageView iv_more;


    int page2 = 1;
    int limit2 = 100;
    int page = 1;
    int limit = 20;
    FriendListAdapter friendListAdapter;
    private View emptyView_noinfo;
    private Dialog dia;
    private Dialog dia3;
    private TextView tv_uid;
    private TextView tv_name;
    private ImageView iv_header1;



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
        page = 1;
        page2 = 1;
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
            Intent intent = new Intent(getActivity(), MineInfoActivity.class);
            intent.putExtra("uid", listBean.getFriendUid());
            intent.putExtra("avatar", listBean.getUser().getAvatar());
            intent.putExtra("name", listBean.getUser().getName());
            intent.putExtra("friendName", listBean.getFriendName());
            startActivity(intent);
//            shwoDialog3(listBean.getUser().getAvatar(), listBean.getUser().getName(), listBean.getUser().getUid());
        });
        friendListAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            FriendBean listBean = friendListAdapter.getData().get(position);
//            Intent intent = new Intent(getActivity(), FriendChatActivity.class);
//            intent.putExtra("senderUid", listBean.getFriendUid());
//            intent.putExtra("avatar", listBean.getUser().getAvatar());
//            LogUtils.debugInfo("测试发送朋友的头像是" + listBean.getUser().getAvatar());
//            startActivity(intent);
            SelectDialog.show(getActivity(), "提示", "确认删除"+listBean.getUser().getName()+"", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mPresenter.deleteFriend(listBean.getUser().getUid(),position);
                }
            });
//            shwoDialog3(listBean.getUser().getAvatar(), listBean.getUser().getName(), listBean.getUser().getUid());
       return true;
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
        TipDialog.show(getActivity(), message, TipDialog.SHOW_TIME_SHORT, TipDialog.TYPE_ERROR);

//        ArmsUtils.snackbarText(message);
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
        tv_empty.setText("暂无好友，赶紧去添加好友吧~");
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
            center_num.setText(num + "");
            center_num.setVisibility(View.VISIBLE);
        } else {
            center_num.setVisibility(View.GONE);
        }
    }

    @Override
    public void deleteFriendSuccess(int position) {
        List<FriendBean> list = friendListAdapter.getData();
        FriendBean listBean = friendListAdapter.getData().get(position);
        Iterator<FriendBean> iterator = list.iterator();
        int  i=0;
        while (iterator.hasNext()) {
            FriendBean obj = iterator.next();
            if (obj == listBean) {
                iterator.remove();
                break;
            }
        }
        friendListAdapter.notifyItemRemoved(position);
        friendListAdapter.notifyItemRangeChanged(0, list.size());
    }

    /**
     * 弹出添加好友dialog
     */
    public void shwoDialog3(String avatar, String name, long uid) {
        if (dia3 != null) {
            Glide.with(this)
                    .asBitmap()
                    .thumbnail(0.6f)
                    .load(avatar)
                    .apply(new RequestOptions().placeholder(R.mipmap.mandefult))
                    .into(iv_header1);
            tv_name.setText(name);
            tv_uid.setText("UID： " + uid);
            dia3.show();
            return;
        }
        dia3 = new Dialog(getActivity(), R.style.edit_AlertDialog_style);
        dia3.setContentView(R.layout.dialog_friend_show);
        tv_uid = dia3.findViewById(R.id.tv_uid);
        TextView tv_copy = dia3.findViewById(R.id.tv_copy);
        TextView tv_send = dia3.findViewById(R.id.tv_send);
        EditText edt_id = dia3.findViewById(R.id.edt_id);
        ImageView iv_change = dia3.findViewById(R.id.iv_change);
        tv_name = dia3.findViewById(R.id.tv_name);
        iv_header1 = dia3.findViewById(R.id.iv_header);
        tv_copy.setText("复制ID");
        Glide.with(this)
                .asBitmap()
                .thumbnail(0.6f)
                .load(avatar)
                .apply(new RequestOptions().placeholder(R.mipmap.mandefult))
                .into(iv_header1);
        tv_name.setText(name);
        tv_uid.setText("UID： " + uid);
        tv_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cmb = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);

                cmb.setText(uid + "");
                ToastUtils.showShort("复制成功");
            }
        });
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FriendChatActivity.class);
                intent.putExtra("senderUid",uid);
                intent.putExtra("avatar", avatar);
                startActivity(intent);
            }
        });
        iv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_name.setVisibility(View.GONE);
                edt_id.setVisibility(View.VISIBLE);
            }
        });

        dia3.show();
    }



    @OnClick({R.id.iv_more, R.id.iv_header, R.id.tv_nickname, R.id.iv_person_add, R.id.iv_group_add,R.id.rl_new_friend})
        public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.rl_new_friend:
                launchActivity(new Intent(getActivity(), AskFriendListActivity.class));
                break;
            case R.id.iv_person_add:
                EventBus.getDefault().post(1, SPConstant.ADDPERSON);
                break;
            case R.id.iv_group_add:
                EventBus.getDefault().post(1, SPConstant.ADDGROUP);
                break;
            case R.id.iv_header:
                EventBus.getDefault().post(1, SPConstant.SHOWINFO);
                break;
            case R.id.iv_more:
                EventBus.getDefault().post(1, SPConstant.OPENSLIDE);
//                showPopup(iv_more, new PopupFriendCircle.ClickLisetener() {
//
//
//                    @Override
//                    public void logout() {
//                        EventBus.getDefault().post(1, SPConstant.LOGOUT);
//                    }
//
//                    @Override
//                    public void mineinfo() {
//                        launchActivity(new Intent(getActivity(), MineInfoActivity.class));
//                    }
//                });
                break;
        }
    }

    private void showPopup(View v, PopupFriendCircle.ClickLisetener lisetener
    ) {
        PopupFriendCircle popupFriendCircle = null;
        if (popupFriendCircle == null) {
            popupFriendCircle = new PopupFriendCircle(getActivity());
            popupFriendCircle.setPopupGravity(Gravity.LEFT | Gravity.BOTTOM);
        }
        popupFriendCircle.linkTo(v);
        popupFriendCircle.setClickcollectLisetener(lisetener);
        popupFriendCircle.showPopupWindow(v);
    }
}
