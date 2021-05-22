package com.haife.app.nobles.spirits.kotlin.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.flyco.tablayout.SlidingTabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.haife.app.nobles.spirits.kotlin.R;
import com.haife.app.nobles.spirits.kotlin.app.constant.SPConstant;
import com.haife.app.nobles.spirits.kotlin.di.component.DaggerMainComponent;
import com.haife.app.nobles.spirits.kotlin.mvp.contract.MainContract;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.LoginInfoBean;
import com.haife.app.nobles.spirits.kotlin.mvp.presenter.MainPresenter;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.fragment.FriendFragment;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.fragment.GroupFragment;
import com.haife.app.nobles.spirits.kotlin.mvp.ui.fragment.MessageFragment;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/22/2021 11:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {


    @BindView(R.id.tab_indi)

    SlidingTabLayout tab_indi;
    @BindView(R.id.nvp_layout)
    ViewPager nvpLayout;
    @BindView(R.id.iv_header)
    ImageView iv_header;
    @BindView(R.id.iv_more)
    ImageView iv_more;
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;
    @BindView(R.id.tv_remark)
    TextView tv_remark;
    @BindView(R.id.status_bar_view)
    View status_bar_view;


    final String[] mTitles = {
            "消息", "朋友", "群组"
    };
    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this)
                .statusBarView(status_bar_view)
                .init();
        if (SPUtils.getInstance().getInt(SPConstant.UID, 0) == 0) {
            launchActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        initFragment();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loginInfo();
    }

    @Override
    public void showLoading() {

    }

    /**
     * @date: 2021/1/16 9:58
     * @description 初始化fragment
     */
    private void initFragment() {

        mFragmentList.add(MessageFragment.newInstance());
        mFragmentList.add(FriendFragment.newInstance());
        mFragmentList.add(GroupFragment.newInstance());

        nvpLayout.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        nvpLayout.setOffscreenPageLimit(2);

        tab_indi.setViewPager(nvpLayout, mTitles);
        nvpLayout.setCurrentItem(0, false);

    }

    @Override
    public void hideLoading() {

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
    public void loginInfoSuccess(LoginInfoBean data) {
                 Glide.with(this)
            .asBitmap()
                .thumbnail(0.6f)
                .load(data.getAvatar())

                        .into(iv_header);
                 tv_nickname.setText(data.getName());
                 tv_remark.setText(data.getRemark());
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);

        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }
    }
}
