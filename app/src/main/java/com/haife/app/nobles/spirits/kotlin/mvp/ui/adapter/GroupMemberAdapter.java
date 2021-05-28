package com.haife.app.nobles.spirits.kotlin.mvp.ui.adapter;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haife.app.nobles.spirits.kotlin.R;
import com.haife.app.nobles.spirits.kotlin.app.view.CircleImageView;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.GroupMemberBean;

import java.util.List;

public class GroupMemberAdapter extends BaseQuickAdapter<GroupMemberBean, BaseViewHolder> {
    public GroupMemberAdapter() {
        super(R.layout.pop_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupMemberBean item) {
      CircleImageView pop_header=helper.getView(R.id.pop_header);
        TextView pop_name=helper.getView(R.id.pop_name);

        Glide.with(mContext)
                .asBitmap()
                .thumbnail(0.6f)
                .load(item.getUser().getAvatar())

                .into(pop_header);
        pop_name.setText(item.getUser().getName());

    }
}
