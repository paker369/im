package com.haife.app.nobles.spirits.kotlin.mvp.ui.adapter;


import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ezreal.emojilibrary.EmojiUtils;
import com.haife.app.nobles.spirits.kotlin.R;
import com.haife.app.nobles.spirits.kotlin.app.constant.SPConstant;
import com.haife.app.nobles.spirits.kotlin.app.utils.TimeUtils;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.MessageBean;
import com.jess.arms.utils.LogUtils;
import com.jingewenku.abrahamcaijin.commonutil.AppDateMgr;


import java.util.Date;
import java.util.List;

public class ChatMessageAdapter extends BaseMultiItemQuickAdapter<MessageBean, BaseViewHolder> {
    String avatar;
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ChatMessageAdapter(List<MessageBean> data,String avatar) {
        super(data);
        //普通文本
        addItemType(1, R.layout.item_chat_text);
        //图片
        addItemType(2, R.layout.item_chat_image);
        this.avatar=avatar;
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean item) {
        ImageView otherheader=helper.getView(R.id.other_header);
        TextView othername=helper.getView(R.id.other_name);
        TextView me_name=helper.getView(R.id.me_name);
        me_name.setVisibility(View.GONE);
//        otherheader.setVisibility(View.GONE);
        othername.setVisibility(View.GONE);
        switch (helper.getItemViewType()) {
            case 1:
                RelativeLayout rl_other = helper.getView(R.id.rl_other);
                RelativeLayout rl_me = helper.getView(R.id.rl_me);
                if (SPUtils.getInstance().getLong(SPConstant.UID) == item.getSenderUid()) {
                    rl_other.setVisibility(View.GONE);
                    rl_me.setVisibility(View.VISIBLE);
                    Glide.with(mContext)
                            .asBitmap()
                            .thumbnail(0.6f)
                            .load(SPUtils.getInstance().getString(SPConstant.HEADER))
                            .apply(new RequestOptions()
                                    .transforms(new CenterCrop(), new CircleCrop())
                                    .placeholder(R.mipmap.mandefult)

                            )
                            .into((ImageView) helper.getView(R.id.me_header));

                    helper.setText(R.id.me_name, SPUtils.getInstance().getString(SPConstant.USERNAME));
                    if(TextUtils.isEmpty(item.getCreateTime())){
                        helper.setText(R.id.me_time, AppDateMgr.formatDateTime(new Date(),"yyyy-MM-dd HH:mm:ss") );
                    }else {
                        helper.setText(R.id.me_time, TimeUtils.progressDate(item.getCreateTime()) );
                    }


                    String content = item.getMsgContent();
                    TextView tvMsg = helper.getView(R.id.me_content);
                    SpannableString msg = EmojiUtils.text2Emoji(mContext, content, tvMsg.getTextSize());
                    helper.setText(R.id.me_content, msg);
                } else {
                    rl_other.setVisibility(View.VISIBLE);
                    rl_me.setVisibility(View.GONE);
                    Glide.with(mContext)
                            .asBitmap()
                            .thumbnail(0.6f)
                            .load(avatar)
                            .apply(new RequestOptions()
                                    .transforms(new CenterCrop(), new CircleCrop())
                                    .placeholder(R.mipmap.mandefult)

                            )
                            .into((ImageView) helper.getView(R.id.other_header));
                    if(TextUtils.isEmpty(item.getCreateTime())){
                        helper.setText(R.id.other_time, AppDateMgr.formatDateTime(new Date(),"yyyy-MM-dd HH:mm:ss") );
                    }else {
                        helper.setText(R.id.other_time, TimeUtils.progressDate(item.getCreateTime()) );
                    }                    TextView tvMsg = helper.getView(R.id.other_content);
                    String content = TextUtils.isEmpty(item.getMsgContent()) ? "" : item.getMsgContent();
                    SpannableString msg = EmojiUtils.text2Emoji(mContext, content, tvMsg.getTextSize());
                    helper.setText(R.id.other_content, msg);
                }
                helper.addOnLongClickListener(R.id.me_header);
                helper.addOnLongClickListener(R.id.other_header);
                break;
            case 2:
                RelativeLayout rlOthers = helper.getView(R.id.rl_other);
                RelativeLayout rlMes = helper.getView(R.id.rl_me);
                if (SPUtils.getInstance().getLong(SPConstant.UID) == item.getSenderUid()) {
                    rlOthers.setVisibility(View.GONE);
                    rlMes.setVisibility(View.VISIBLE);
                    Glide.with(mContext)
                            .asBitmap()
                            .thumbnail(0.6f)
                            .load(SPUtils.getInstance().getString(SPConstant.HEADER))
                            .apply(new RequestOptions()
                                    .transforms(new CenterCrop(), new CircleCrop())
                                    .placeholder(R.mipmap.mandefult)

                            )
                            .into((ImageView) helper.getView(R.id.me_header));
                    helper.setText(R.id.me_name,SPUtils.getInstance().getString(SPConstant.USERNAME));
                    if(TextUtils.isEmpty(item.getCreateTime())){
                        helper.setText(R.id.me_time, AppDateMgr.formatDateTime(new Date(),"yyyy-MM-dd HH:mm:ss") );
                    }else {
                        helper.setText(R.id.me_time, TimeUtils.progressDate(item.getCreateTime()) );
                    }//                    Picasso.with(mContext)
//                            .load(item.getPicture().getFilePath())
//                                    .error(R.mipmap.ic_error)
//                                    .placeholder(R.mipmap.loading)
//                            .into((ImageView) helper.getView(R.id.me_img));
                    Glide.with(mContext)
                            .asBitmap()
                            .thumbnail(0.6f)
                            .load(item.getMsgContent())
                            .apply(new RequestOptions()
                                    .transforms(new FitCenter())
//                                    .placeholder(R.mipmap.ic_placeholder)
//                                    .error(R.mipmap.ic_error)
                                    .diskCacheStrategy(DiskCacheStrategy.DATA))
                            .into((ImageView) helper.getView(R.id.me_img));
                    helper.addOnClickListener(R.id.me_img);
                } else {
                    rlOthers.setVisibility(View.VISIBLE);
                    rlMes.setVisibility(View.GONE);
                    if(TextUtils.isEmpty(item.getCreateTime())){
                        helper.setText(R.id.me_time, AppDateMgr.formatDateTime(new Date(),"yyyy-MM-dd HH:mm:ss") );
                    }else {
                        helper.setText(R.id.me_time, TimeUtils.progressDate(item.getCreateTime()) );
                    }//                    Glide.with(mContext)
//                            .load(item.getPicture().getFilePath())
//
//                                    .error(R.mipmap.ic_error)
//                                    .placeholder(R.mipmap.loading)
//                            .into((ImageView) helper.getView(R.id.other_img));
                    Glide.with(mContext)
                            .asBitmap()
                            .thumbnail(0.6f)
                            .load(avatar)
                            .apply(new RequestOptions()
                                    .transforms(new CenterCrop(), new CircleCrop())
                                    .placeholder(R.mipmap.mandefult)

                            )
                            .into((ImageView) helper.getView(R.id.other_header));
                    Glide.with(mContext)
                            .asBitmap()
                            .thumbnail(0.6f)
                            .load(item.getMsgContent())
                            .apply(new RequestOptions()
                                    .transforms(new FitCenter())
                                    .placeholder(R.mipmap.icon_app)
//                                    .error(R.mipmap.ic_error)
)
                            .into((ImageView) helper.getView(R.id.other_img)
                            );
                }
                helper.addOnLongClickListener(R.id.me_header);
                helper.addOnLongClickListener(R.id.other_header);
                helper.addOnClickListener(R.id.other_img);
                break;
        }
    }
}

