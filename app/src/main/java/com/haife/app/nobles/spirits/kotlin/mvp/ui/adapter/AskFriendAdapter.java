package com.haife.app.nobles.spirits.kotlin.mvp.ui.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haife.app.nobles.spirits.kotlin.R;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.FriendAskBean;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.MessageBean;

import org.w3c.dom.Text;

import java.util.List;

public class AskFriendAdapter extends BaseQuickAdapter<FriendAskBean, BaseViewHolder> {

    public AskFriendAdapter() {
        super(R.layout.item_ask_friend);
    }

    @Override
    protected void convert(BaseViewHolder helper, FriendAskBean item) {

       ImageView portraitImageView=helper.getView(R.id.portraitImageView);
        TextView nameTextView=helper.getView(R.id.nameTextView);
        TextView introTextView=helper.getView(R.id.introTextView);
        TextView acceptStatusTextView=helper.getView(R.id.acceptStatusTextView);
        Button acceptnonoButton=helper.getView(R.id.acceptnonoButton);
        Button acceptButton=helper.getView(R.id.acceptButton);
        acceptStatusTextView.setVisibility(View.GONE);
        acceptnonoButton.setVisibility(View.VISIBLE);
        acceptButton.setVisibility(View.VISIBLE);

        Glide.with(mContext)
                .asBitmap()
                .thumbnail(0.6f)
                .load(item.getUser().getAvatar())
                .apply(new RequestOptions().placeholder(R.mipmap.mandefult))
                .into(portraitImageView);
        nameTextView.setText(item.getUser().getName());
        introTextView.setText(item.getRemark());
        switch (item.getStatus()){
            case 0:
                acceptStatusTextView.setVisibility(View.GONE);

                break;
            case 1:
                acceptStatusTextView.setText("已添加");
                acceptStatusTextView.setVisibility(View.VISIBLE);
                acceptnonoButton.setVisibility(View.GONE);
                acceptButton.setVisibility(View.GONE);
                break;
            case 2:
                acceptStatusTextView.setText("已拒绝");
                acceptStatusTextView.setVisibility(View.VISIBLE);
                acceptnonoButton.setVisibility(View.GONE);
                acceptButton.setVisibility(View.GONE);
                break;

        }
        helper.addOnClickListener(R.id.acceptnonoButton,R.id.acceptButton);


    }
}
