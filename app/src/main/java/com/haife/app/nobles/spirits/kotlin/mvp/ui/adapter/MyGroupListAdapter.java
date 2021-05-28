package com.haife.app.nobles.spirits.kotlin.mvp.ui.adapter;

import android.text.SpannableString;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ezreal.emojilibrary.EmojiUtils;
import com.haife.app.nobles.spirits.kotlin.R;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.FriendBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.MyGroup;

import java.util.List;

public class MyGroupListAdapter  extends BaseQuickAdapter<MyGroup, BaseViewHolder> {
    public MyGroupListAdapter() {
        super(R.layout.item_group_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyGroup item) {
        TextView nameTextView = helper.getView(R.id.nameTextView);
        TextView descTextView = helper.getView(R.id.descTextView);
        ImageView portraitImageView = helper.getView(R.id.portraitImageView);
        Glide.with(mContext)
                .asBitmap()
                .thumbnail(0.6f)
                .load(item.getGroup().getAvatar())
                .apply(new RequestOptions().placeholder(R.mipmap.mandefult))
                .into(portraitImageView);
//        SpannableString msg = EmojiUtils.text2Emoji(mContext, item.getLastMsgContent(), descTextView.getTextSize());

        descTextView.setText(item.getGroup().getRemark());
        nameTextView.setText(item.getGroup().getName());
    }
}
