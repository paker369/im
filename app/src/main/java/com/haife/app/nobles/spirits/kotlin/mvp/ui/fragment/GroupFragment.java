package com.haife.app.nobles.spirits.kotlin.mvp.ui.fragment;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.haife.app.nobles.spirits.kotlin.R;
import com.haife.app.nobles.spirits.kotlin.app.constant.SPConstant;
import com.haife.app.nobles.spirits.kotlin.app.view.PopupFriendCircle;
import com.haife.app.nobles.spirits.kotlin.di.component.DaggerGroupComponent;
import com.haife.app.nobles.spirits.kotlin.mvp.contract.GroupContract;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.FriendBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.LoginInfoBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.MyGroup;
import com.haife.app.nobles.spirits.kotlin.mvp.presenter.GroupPresenter;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.activity.GroupChatActivity;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.activity.MineInfoActivity;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.adapter.MyGroupListAdapter;
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
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">?????????????????????</a>
 * ================================================
 */
public class GroupFragment extends BaseFragment<GroupPresenter> implements GroupContract.View, OnLoadMoreListener, OnRefreshListener {
    @BindView(R.id.srl_layout)
    SmartRefreshLayout srl_layout;
    @BindView(R.id.rv_message_list)
    RecyclerView rv_message_list;
    @BindView(R.id.iv_header)
    ImageView iv_header;
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;
    @BindView(R.id.iv_more)
    ImageView iv_more;

    int page = 1;
    int limit = 20;
    MyGroupListAdapter myGroupListAdapter;
    private View emptyView_noinfo;
    private Dialog dia;
    private TextView tv_send;
    private TextView tv_copy;

    public static GroupFragment newInstance() {
        GroupFragment fragment = new GroupFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerGroupComponent //??????????????????,?????????????????????
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
        page=1;
    mPresenter.myGroupList(page,limit);
    }

    /**
     * @date: 2021/1/20 15:50
     * @description ???????????????
     */
    private void initRefreshLayout() {
        srl_layout.setOnRefreshListener(this);
        srl_layout.setOnLoadMoreListener(this);
        srl_layout.setEnableRefresh(true);
        srl_layout.setEnableLoadMore(true);
    }
    /**
     * @date: 2021/1/14 15:48
     * @description ???????????????
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
//            Intent intent = new Intent(getActivity(), GroupChatActivity.class);
//            intent.putExtra("groupid", listBean.getGroupId());
//            intent.putExtra("avatar", listBean.getGroup().getAvatar());
//            intent.putExtra("name", listBean.getGroup().getName());
//            intent.putExtra("remark", listBean.getGroup().getRemark());
//            intent.putExtra("ismine", (listBean.getGroup().getUid() == SPUtils.getInstance().getLong(SPConstant.UID)));
//            startActivity(intent);
            shwoDialog3(listBean.getGroup().getAvatar(), listBean.getGroup().getName(), listBean.getGroupId(),listBean.getGroup().getRemark(),(listBean.getGroup().getUid() == SPUtils.getInstance().getLong(SPConstant.UID)));

        });
        myGroupListAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            MyGroup listBean = myGroupListAdapter.getData().get(position);
//            Intent intent = new Intent(getActivity(), FriendChatActivity.class);
//            intent.putExtra("senderUid", listBean.getFriendUid());
//            intent.putExtra("avatar", listBean.getUser().getAvatar());
//            LogUtils.debugInfo("??????????????????????????????" + listBean.getUser().getAvatar());
//            startActivity(intent);
            if(listBean.getGroup().getUid()==SPUtils.getInstance().getLong(SPConstant.UID)){
                SelectDialog.show(getActivity(), "??????", "?????????????????????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deleteMyGroup(listBean.getGroupId(),position);
                    }
                });
            }else {
                SelectDialog.show(getActivity(), "??????", "???????????????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deleteGroup(listBean.getGroupId(),position);
                    }
                });
            }

//            shwoDialog3(listBean.getUser().getAvatar(), listBean.getUser().getName(), listBean.getUser().getUid());
            return true;
        });

    }
    private Dialog dia3;
    private TextView tv_uid;
    private TextView tv_name;
    private ImageView iv_header1;
    /**
     * ??????????????????dialog
     */
    public void shwoDialog3(String avatar, String name, long groupid,String remark, boolean ismine) {
        if (dia3 != null) {
            Glide.with(this)
                    .asBitmap()
                    .thumbnail(0.6f)
                    .load(avatar)
                    .apply(new RequestOptions().placeholder(R.mipmap.mandefult))
                    .into(iv_header1);
            tv_name.setText(name);
            tv_uid.setText("????????? " + groupid);
            tv_copy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager cmb = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);

                    cmb.setText(groupid + "");
                    ToastUtils.showShort("????????????");
                    dia3.dismiss();
                }
            });
            tv_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dia3.dismiss();
                    Intent intent = new Intent(getActivity(), GroupChatActivity.class);
                    intent.putExtra("groupid", groupid);
                    intent.putExtra("avatar", avatar);
                    intent.putExtra("name", name);
                    intent.putExtra("remark", remark);
                    intent.putExtra("ismine", ismine);
                    startActivity(intent);

                }
            });
            dia3.show();
            return;
        }
        dia3 = new Dialog(getActivity(), R.style.edit_AlertDialog_style);
        dia3.setContentView(R.layout.dialog_friend_show);
        tv_uid = dia3.findViewById(R.id.tv_uid);
         tv_copy = dia3.findViewById(R.id.tv_copy);
         tv_send = dia3.findViewById(R.id.tv_send);

        tv_name = dia3.findViewById(R.id.tv_name);
        iv_header1 = dia3.findViewById(R.id.iv_header);
        tv_copy.setText("????????????");
        Glide.with(this)
                .asBitmap()
                .thumbnail(0.6f)
                .load(avatar)
                .apply(new RequestOptions().placeholder(R.mipmap.mandefult))
                .into(iv_header1);
        tv_name.setText(name);
        tv_uid.setText("????????? " + groupid);
        tv_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cmb = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);

                cmb.setText(groupid + "");
                ToastUtils.showShort("????????????");
                dia3.dismiss();
            }
        });
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dia3.dismiss();
                Intent intent = new Intent(getActivity(), GroupChatActivity.class);
                intent.putExtra("groupid", groupid);
                intent.putExtra("avatar", avatar);
                intent.putExtra("name", name);
                intent.putExtra("remark", remark);
                intent.putExtra("ismine", ismine);
                startActivity(intent);
            }
        });


        dia3.show();
    }


    /**
     * ??????????????????dialog
     */
    public void shwoDialog1() {
        if (dia != null) {
            dia.show();
            return;
        }
        dia = new Dialog(getActivity(), R.style.edit_AlertDialog_style);
        dia.setContentView(R.layout.dialog_add);
        TextView tv_cofirm = dia.findViewById(R.id.tv_cofirm);
        TextView tv_cancel = dia.findViewById(R.id.tv_cancel);
        TextView tv_title = dia.findViewById(R.id.tv_title);
        EditText edt_id = dia.findViewById(R.id.edt_id);
        EditText edt_remark = dia.findViewById(R.id.edt_remark);
        edt_id.setHint("????????????");
        edt_remark.setHint("???????????????");
        tv_cofirm.setText("????????????");
        tv_cancel.setText("????????????");
        tv_title.setText("?????????");
        tv_cofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edt_id.getText().toString())) {
                    ToastUtils.showShort("??????????????????");
                    return;
                }
//                if (!AppValidationMgr.isNumber(edt_id.getText().toString())) {
//                    ToastUtils.showShort("?????????????????????");
//                    return;
//                }
//                if (TextUtils.isEmpty(edt_remark.getText().toString())) {
//                    ToastUtils.showShort("??????????????????");
//                    return;
//                }
                mPresenter.createGroup(edt_id.getText().toString(), edt_remark.getText().toString());

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
        mPresenter.myGroupList(page,limit);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        mPresenter.myGroupList(page,limit);
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
    @OnClick({R.id.iv_more, R.id.iv_header, R.id.tv_nickname,R.id.iv_creat_group, R.id.iv_person_add, R.id.iv_group_add,})
    public void onViewClick(View view) {
        switch (view.getId()) {

            case R.id.iv_creat_group:
                shwoDialog1();
                break;
            case R.id.iv_person_add:
                EventBus.getDefault().post(1, SPConstant.ADDPERSON);
                break;
            case R.id.iv_group_add:
                EventBus.getDefault().post(1, SPConstant.ADDGROUP);
                break;
            case R.id.iv_header:
                EventBus.getDefault().post(1,SPConstant.SHOWINFO);
                break;
            case R.id.iv_more:
                EventBus.getDefault().post(1, SPConstant.OPENSLIDE);
//            showPopup(iv_more, new PopupFriendCircle.ClickLisetener() {
//
//
//                @Override
//                public void logout() {
//                    EventBus.getDefault().post(1, SPConstant.LOGOUT);
//                }
//
//                @Override
//                public void mineinfo() {
//                    launchActivity(new Intent(getActivity(), MineInfoActivity.class));
//                }
//            });
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

    @Override
    public void myGroupListSuccess(List<MyGroup> data) {
        TextView tv_empty = emptyView_noinfo.findViewById(R.id.tv_empty);
        tv_empty.setText("???????????????????????????????????????~");
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
    public void createGroupSuccess(MyGroup.GroupBean data) {
        if (dia != null && dia.isShowing())
            dia.dismiss();
        ToastUtils.showShort("????????????");
//        MyGroup myGroup=new MyGroup();
//        myGroup.setGroup(data);
//        myGroupListAdapter.getData().add(0,myGroup);
//        myGroupListAdapter.notifyItemInserted(0);
        page=1;
        mPresenter.myGroupList(page,limit);
    }

    @Override
    public void deleteGroupSuccess(int position) {
        List<MyGroup> list = myGroupListAdapter.getData();
        MyGroup listBean = myGroupListAdapter.getData().get(position);
        Iterator<MyGroup> iterator = list.iterator();
        int  i=0;
        while (iterator.hasNext()) {
            MyGroup obj = iterator.next();
            if (obj == listBean) {
                iterator.remove();
                break;
            }
        }
        myGroupListAdapter.notifyItemRemoved(position);
        myGroupListAdapter.notifyItemRangeChanged(0, list.size());
    }

    @Override
    public void deleteMyGroupSuccess(int position) {
        List<MyGroup> list = myGroupListAdapter.getData();
        MyGroup listBean = myGroupListAdapter.getData().get(position);
        Iterator<MyGroup> iterator = list.iterator();
        int  i=0;
        while (iterator.hasNext()) {
            MyGroup obj = iterator.next();
            if (obj == listBean) {
                iterator.remove();
                break;
            }
        }
        myGroupListAdapter.notifyItemRemoved(position);
        myGroupListAdapter.notifyItemRangeChanged(0, list.size());
    }
}
