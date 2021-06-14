package com.haife.app.nobles.spirits.kotlin.app;//package com.example.zhugeyouliao.app;
//
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.content.pm.ResolveInfo;
//import android.net.Uri;
//import android.os.Build;
//import android.util.Log;
//
//import com.example.zhugeyouliao.utils.ToastUtils;
//import com.jess.arms.utils.LogUtils;
//
//import java.util.List;
//
//public class Example {

//    @Subscriber(tag = "refresh")
//    public void loadmore(Event data) {
//        LogUtils.debugInfo("测试成功使用eventbus"+type);
//        if(data.getMessgae().equals(LOADMORE_COMPET)){
//
//        }
//    }
//
//  homeColorAdapter.setOnItemClickListener((adapter, view, position) -> {
//        HomeColorBean.ListBean listBean = homeColorAdapter.getData().get(position);
//        startActLiuheGallery(listBean.getYear() + "", listBean.getColor() + "", true);
//    });


//    Intent chatIntent = new Intent(getActivity(), ChatActivity.class);
//    CHATROOMID = finalData.getList().get(position).getId();
//                chatIntent.putExtra("chatId", CHATROOMID + "");
//                chatIntent.putExtra("chatName", finalData.getList().get(position).getName());
//    startActivity(chatIntent);


//    name = getIntent().getStringExtra("chatName");

//
//         Glide.with(this)
//            .asBitmap()
//                .thumbnail(0.6f)
//                .load(item.getHeadUrl())
//            .apply(new RequestOptions()
//                        .skipMemoryCache(true)
//                        .diskCacheStrategy(DiskCacheStrategy.DATA)
//                )
//                        .into(civ_people_head);


//    Bundle args = new Bundle();
//        args.putInt("teama", teama);
//        args.putInt("teamb", teamb);
//        fragment.setArguments(args);


//    Bundle bundle = getArguments();
//    name = bundle.getString("name");
//    guessid=bundle.getInt("guessid");
//    first = bundle.getInt("first");
//    mPlatform = bundle.getInt("mPlatform");
//    mYear=bundle.getInt("year");


//
//    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
//        match_ry.setLayoutManager(linearLayoutManager);
//    mCompetitionTypeAdapter = new BasCompetetionTypeAdapter();


//    SPUtils.getInstance().getString(AppConstants.TOKEN)


//     <include layout="@layout/common_title_bar" />


//  private List<Fragment> mFragmentList=new ArrayList<>();


//    private String[] mTitles={"足球赛事","篮球赛事","电竞赛事"};
//    private class MyPagerAdapter extends FragmentStatePagerAdapter {
//
//
//        public MyPagerAdapter(FragmentManager fm ) {
//            super(fm);
//
//        }
//
//
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mTitles[position];
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return  mFragmentList.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return 3;
//        }
//
//        @Override
//        public void notifyDataSetChanged() {
//            super.notifyDataSetChanged();
//        }
//    }

//     for(int    i=0;    i<pictureList.size();    i++)    {
//        if(bannerDatastring==""){
//            bannerDatastring = pictureList.get(i).getPictureUrl()+"&&&"+pictureList.get(i).getId();
//
//        }else{
//            bannerDatastring = bannerDatastring+ ","+ pictureList.get(i).getPictureUrl()+"&&&"+pictureList.get(i).getId();
//        }
//
//    }


/**
 * @date: 2021/1/22 6:48
 * @description 初始化列表
 */
//    private void initRecyclerView() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
//        rv_hotlist.setLayoutManager(linearLayoutManager);
//        articleHotListAdapter = new ArticleHotListAdapter(getActivity());
//        rv_hotlist.setAdapter(articleHotListAdapter);
//        articleHotListAdapter.setOnItemClickListener((adapter, view, position) -> {
//            ArticleHotBean.ArticleHotitemBean listBean = articleHotListAdapter.getData().get(position);
//            Intent chatIntent = new Intent(getActivity(), CommunityArticleDetailsActivity.class);
//            chatIntent.putExtra("articleid", listBean.getId());
//            startActivity(chatIntent);
//
//        });
//
//    }

//
//       try {
//        if (refreshLayout.getState() == RefreshState.Refreshing) {
//            refreshLayout.finishRefresh();
//        } else if (refreshLayout.getState() == RefreshState.Loading) {
//            refreshLayout.finishLoadMore();
//        } else {
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//    }

//    private void  initRefreshLayout(){
//        refresh.setEnableLoadMore(true);
//        refresh.setEnableRefresh(true);
//        refresh.setOnLoadMoreListener(this);
//        refresh.setOnRefreshListener(this);
//    }
//
//    int current;
//    int size;

//    SPUtils.getInstance().getInt(AppConstants.USER_ID,0)
//
//     redmanAdapter.setOnItemChildClickListener((adapter, view, position) -> {
//        click(0, redmanAdapter.getItem(position), view, position);


//            helper.addOnClickListener(R.id.civ_people_head, R.id.tv_people_name,R.id.tv_people_num, R.id.tv_people_attention,R.id.iv_header,R.id.tv_fans);
//    public void click(int type, CommentListBean bean, View view, int position) {
//
//        switch (view.getId()) {
//            //名字
//            case R.id.tv_poster_nickname:
//                Intent intent1 = new Intent(this, MyConcernBrowseActivity.class);
//                intent1.putExtra("username", bean.getNickname());
//                intent1.putExtra("concernedUserId", bean.getUserId() + "");
//                intent1.putExtra("fromdetail", 1);
//                startActivity(intent1);
//
//                break;
//
//            //头像
//            case R.id.iv_header:
//                Intent intent2 = new Intent(this, MyConcernBrowseActivity.class);
//                intent2.putExtra("username", bean.getNickname());
//                intent2.putExtra("concernedUserId", bean.getUserId() + "");
//                intent2.putExtra("fromdetail", 1);
//                startActivity(intent2);
//
//                break;
//        }}
//跳转应用市场代码示例
//    Uri uri = Uri.parse("market://search?q=诸葛有料");
//    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
//        //可以接收
//        startActivity(intent);
//    } else {
//
//        ToastUtils.show(getActivity(),"哈哈只能应用内更新了");
//
//    }


//
//    private void selfStartManagerSettingIntent(Context context) {
//        String mtype = Build.BRAND; // 手机型号
//
//        Intent intent = new Intent();
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        ComponentName componentName = null;
//        LogUtils.debugInfo("测试手机型号是"+mtype);
//        if (mtype.startsWith("Redmi")||mtype.startsWith("MI")) {
//            componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
//        } else if (mtype.startsWith("HONOR")) {
//            LogUtils.debugInfo("测试这是HUAWEI");
//            startPackageName("com.huawei.systemmanager");
//            componentName = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
//        } else if (mtype.startsWith("vivo")) {
//            Log.e(TAG, "selfStartManagerSettingIntent: vivo");
//            componentName = new ComponentName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity");
//        } else if (mtype.startsWith("ZTE")) {
////            /.autorun.AppAutoRunManager
//            componentName = new ComponentName("com.zte.heartyservice", "com.zte.heartyservice.autorun.AppAutoRunManager");
//        } else if (mtype.startsWith("F")) {
//            Log.e(TAG, "selfStartManagerSettingIntent: F");
//            componentName = new ComponentName("com.gionee.softmanager", "com.gionee.softmanager.oneclean.AutoStartMrgActivity");
//        } else if (mtype.startsWith("PBEM00")) {
//            LogUtils.debugInfo("测试这是oppo");
//            startPackageName("com.coloros.phonemanager");
//        }
////        intent.setComponent(componentName);
////        try {
////            context.startActivity(intent);
////        } catch (Exception e) {//抛出异常就直接打开设置页面
//////            intent = new Intent(Settings.ACTION_SETTINGS);
//////            context.startActivity(intent);
////            LogUtils.debugInfo("测试打开失败");
////        }
//
//
//
//
//
//    }


//跳转杀毒界面
//    private void startPackageName(String packagename) {
//
//        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
//        PackageInfo packageinfo = null;
//        try {
//            packageinfo = getActivity().getPackageManager().getPackageInfo(packagename, 0);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        if (packageinfo == null) {
//            return;
//        }
//
//        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
//        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
//        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//        resolveIntent.setPackage(packageinfo.packageName);
//
//        // 通过getPackageManager()的queryIntentActivities方法遍历
//        List<ResolveInfo> resolveinfoList = getActivity().getPackageManager()
//                .queryIntentActivities(resolveIntent, 0);
//
//        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
//        if (resolveinfo != null) {
//            // packagename = 参数packname
//            String packageName = resolveinfo.activityInfo.packageName;
//            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
//            String className = resolveinfo.activityInfo.name;
//            // LAUNCHER Intent
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_LAUNCHER);
//
//            // 设置ComponentName参数1:packagename参数2:MainActivity路径
//            ComponentName cn = new ComponentName(packageName, className);
//
//            intent.setComponent(cn);
//            startActivity(intent);
//        }
//    }
//
//}

//recyclerview添加分割线
//   recycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
//           .color(getResources().getColor(R.color.dividerColor))
//           .sizeResId(R.dimen.divider)
//           .marginResId(R.dimen.leftmargin16, R.dimen.rightmargin16)
//           .build());