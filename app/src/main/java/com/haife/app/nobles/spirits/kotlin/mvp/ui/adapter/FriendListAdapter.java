package com.haife.app.nobles.spirits.kotlin.mvp.ui.adapter;

import android.text.SpannableString;
import android.view.View;
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

import java.util.List;

public class FriendListAdapter   extends BaseQuickAdapter<FriendBean, BaseViewHolder> {
    int showtype;
    public FriendListAdapter(  int showtype) {
        super(R.layout.item_friend_list);
        this.showtype=   showtype;
    }

    @Override
    protected void convert(BaseViewHolder helper, FriendBean item) {
        TextView nameTextView = helper.getView(R.id.nameTextView);
        TextView descTextView = helper.getView(R.id.descTextView);
        ImageView portraitImageView = helper.getView(R.id.portraitImageView);
        TextView center_num = helper.getView(R.id.center_num);
        center_num.setVisibility(View.GONE);
        if(showtype==1){
            if(item.getUnMsgCount()>0){
                center_num.setVisibility(View.VISIBLE);
                center_num.setText(item.getUnMsgCount()+"");
            }
        }

        Glide.with(mContext)
                .asBitmap()
                .thumbnail(0.6f)
                .load(item.getUser().getAvatar())
.apply(new RequestOptions().placeholder(R.mipmap.mandefult))
                .into(portraitImageView);
//        SpannableString msg = EmojiUtils.text2Emoji(mContext, item.getLastMsgContent(), descTextView.getTextSize());
        if(showtype==1){
            descTextView.setText(item.getLastMsgContent());

        }else {
            descTextView.setText(item.getUser().getRemark());

        }
        nameTextView.setText(item.getUser().getName());
    }
}
