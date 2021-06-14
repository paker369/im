package com.haife.app.nobles.spirits.kotlin.mvp.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haife.app.nobles.spirits.kotlin.R;
import com.haife.app.nobles.spirits.kotlin.app.constant.SPConstant;
import com.haife.app.nobles.spirits.kotlin.app.view.ChatInputLayout;
import com.haife.app.nobles.spirits.kotlin.di.component.DaggerFriendChatComponent;
import com.haife.app.nobles.spirits.kotlin.mvp.contract.FriendChatContract;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.MessageBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.ReadOtherInfoBean;
import com.haife.app.nobles.spirits.kotlin.mvp.presenter.FriendChatPresenter;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.adapter.ChatMessageAdapter;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.utlis.BarUtils;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.utlis.ImageUtils;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.utlis.PhotoSelectSingleUtile;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.jingewenku.abrahamcaijin.commonutil.AppDateMgr;
import com.kongzue.dialog.v2.SelectDialog;
import com.kongzue.dialog.v2.TipDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yescpu.keyboardchangelib.KeyboardChangeListener;

import org.simple.eventbus.Subscriber;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import indi.liyi.viewer.ImageLoader;
import indi.liyi.viewer.ImageViewer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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
public class FriendChatActivity extends BaseActivity<FriendChatPresenter> implements FriendChatContract.View
        , ChatInputLayout.OnInputLayoutListener
        , OnRefreshListener
        , KeyboardChangeListener.KeyboardListener{

    @BindView(R.id.ll_root)
    LinearLayout ll_root;
    @BindView(R.id.rll)
    RelativeLayout rll;
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

    @BindView(R.id.status_bar_view)
    View status_bar_view;

    @BindView(R.id.photo_view)
    ImageViewer photo_view;


    ChatMessageAdapter chatMessageAdapter;
    private LinearLayoutManager layouts;
    int page = 1;
    int limit = 20;
    long senderUid;
    private int firstCompletelyVisibleItemPosition = -1;
    List<MessageBean> messagepool = new ArrayList<>();
    private Handler mHandler;
    private int messengecount;
    private List<MessageBean> listBeans = new ArrayList<>();
    private String avatar = "";
    private KeyboardChangeListener mKeyboardChangeListener;


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
        senderUid = getIntent().getLongExtra("senderUid", 0);
        avatar = getIntent().getStringExtra("avatar");
        BarUtils.setStatusBarAlpha(this, 0, true);
        BarUtils.setStatusBarLightMode(this, true);
        View mFaceTextEmotionTrigger = input_layout.findViewById(R.id.iv_expression);
        View mFaceTextInputLayout = input_layout.findViewById(R.id.layout_express);
//        ImmersionBar.with(this)
//                .statusBarView(status_bar_view)
//                .barAlpha(0)
////                .applySystemFits(false)
//                .init();
//        KeyBoardListener.getInstance(this).init();
//        mSmartKeyboardManager = new SmartKeyboardManager.Builder(this).setContentView(color_recycler)
//                .setEmotionKeyboard(mFaceTextInputLayout) // 表情键盘View
//                .setEditText(input_layout) // 输入框
//                .setEmotionTrigger(mFaceTextEmotionTrigger) // 表情键盘和软键盘的切换按钮
//                .addOnContentViewScrollListener(new SmartKeyboardManager.OnContentViewScrollListener() {
//                    @Override public void shouldScroll(int distance) {
//                        color_recycler.scrollBy(0, distance); // 将 recyclerview 滚动相应的距离，内部已经把滚动的方向问题处理好了，大胆使用吧
//                    }})
//                .create();
        initRecycler();
        mKeyboardChangeListener = KeyboardChangeListener.create(this);
        mKeyboardChangeListener.setKeyboardListener(this);
        input_layout.setLayoutListener(this);
        input_layout.bindInputLayout(this, rll);
        mPresenter.friendList(page, limit, senderUid);
        mPresenter.read(senderUid);
        // 一般来讲， ImageWatcher 需要占据全屏的位置

        // 如果不是透明状态栏，你需要给ImageWatcher标记 一个偏移值，以修正点击ImageView查看的启动动画的Y轴起点的不正确


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

    @OnClick({R.id.iv_delete, R.id.tv_nickname, R.id.newmessege, R.id.iv_back})
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
            case R.id.iv_back:
                finish();
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
        chatMessageAdapter = new ChatMessageAdapter(listBeans, avatar);
        chatMessageAdapter.setEmptyView(R.layout.empty_placehold, color_recycler);
        color_recycler.setAdapter(chatMessageAdapter);
        chatMessageAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MessageBean listBean = chatMessageAdapter.getData().get(position);
                if (view.getId() == R.id.me_img || view.getId() == R.id.other_img) {
                    LogUtils.debugInfo("测试点击了图片");
                    String filePath = listBean.getMsgContent() != null ? listBean.getMsgContent() : "";
                    List<String> image = new ArrayList<>();
                    image.add(filePath);
                    photo_view.overlayStatusBar(false)// ImageViewer 是否会占据 StatusBar 的空间
                            .imageData(image) // 图片数据
                            .imageLoader(new ImageLoader() {
                                @Override
                                public void displayImage(Object src, ImageView imageView, LoadCallback callback) {
                                    if (!FriendChatActivity.this.isFinishing())
                                        Glide.with(imageView.getContext())
                                                .asBitmap().thumbnail(0.6f)
                                                .load(src)
                                                .into(imageView);
//                .into(imageView);
                                }
                            }) // 设置图片加载方式
                            .draggable(false)
                            .playEnterAnim(false) // 是否开启进场动画，默认为true
                            .playExitAnim(false) // 是否开启退场动画，默认为true
                            // 是否显示图片索引，默认为true
//                            .setOnItemLongPressListener(new OnItemLongPressListener() {
//                                @Override
//                                public boolean onItemLongPress(int position, ImageView imageView) {
//                                    save(imageUrllist.get(position));
//                                    return false;
//                                }
//                            })

//                            .setOnItemClickListener(new OnItemClickListener() {
//                                @Override
//                                public boolean onItemClick(int position, ImageView imageView) {
//                                    finish();
//                                    return true;
//                                }
//                            })
                            .loadProgressUI(null) // 自定义图片加载进度样式，内置默认样式
                            .watch(0); // 开启浏览
                }
            }
        });
    }

    private Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        return mHandler;
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
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        TipDialog.show(this, message, TipDialog.SHOW_TIME_SHORT, TipDialog.TYPE_ERROR);

//        ArmsUtils.snackbarText(message);
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
        //相册回调
        // 进入相册 以下是例子：不需要的api可以不写
        PhotoSelectSingleUtile.selectPhoto(this, mSelectList, 1);

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
        MessageBean data = new MessageBean(SPUtils.getInstance().getLong(SPConstant.UID), senderUid, SPUtils.getInstance().getLong(SPConstant.UID), type, content.toString(), AppDateMgr.todayYyyyMmDdHhMmSs());

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
    public void readSuccess(ReadOtherInfoBean data) {
        tv_nickname.setText(data.getName());
    }

    @Override
    public void deleteFriendSuccess() {
        ToastUtils.showShort("已删除好友");
        finish();

    }

    @Override
    public void uploadSuccess(String data) {
        mPresenter.sendMsg(data, 1, senderUid);
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


    @Override
    public void onKeyboardChange(boolean isShow, int keyboardHeight) {
        if(isShow){
            SPUtils.getInstance().put(SPConstant.KEYBOARD,keyboardHeight);
        }
    }
}
