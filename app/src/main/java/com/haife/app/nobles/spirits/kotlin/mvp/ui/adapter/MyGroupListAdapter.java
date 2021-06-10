package com.haife.app.nobles.spirits.kotlin.mvp.ui.adapter;

import android.text.SpannableString;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ezreal.emojilibrary.EmojiUtils;
import com.haife.app.nobles.spirits.kotlin.R;
import com.haife.app.nobles.spirits.kotlin.mvp.model.bean.MyGroup;
import com.jingewenku.abrahamcaijin.commonutil.AppDateMgr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyGroupListAdapter  extends BaseQuickAdapter<MyGroup, BaseViewHolder> {

    int showtype;
    public MyGroupListAdapter(int showtype) {
        super(R.layout.item_group_list);
        this.showtype=showtype;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyGroup item) {
        TextView nameTextView = helper.getView(R.id.nameTextView);
        TextView descTextView = helper.getView(R.id.descTextView);

        TextView tv_show_time = helper.getView(R.id.tv_show_time);
        ImageView portraitImageView = helper.getView(R.id.portraitImageView);
        TextView center_num = helper.getView(R.id.center_num);
        center_num.setVisibility(View.GONE);
        if(showtype==1){
            if(item.getUnMsgCount()>0){
                center_num.setVisibility(View.VISIBLE);
                center_num.setText(item.getUnMsgCount()+"");
            }
            if(item.getLastMsgTime()!=null){
                Date date=null;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.000+0000'", Locale.CHINA);
                try {
                    date=  simpleDateFormat.parse(item.getLastMsgTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(date!=null){
                    tv_show_time.setText( AppDateMgr.formatFriendly(date));
                }
            }else {

                helper.setText(R.id.tv_show_time, AppDateMgr.formatFriendly(new Date()) );

            }

        }

        Glide.with(mContext)
                .asBitmap()
                .thumbnail(0.6f)
                .load(item.getGroup().getAvatar())
                .apply(new RequestOptions().placeholder(R.mipmap.mandefult))
                .into(portraitImageView);

        if(showtype==1){
            if(item.getLastMsgContent()==null){return;}
            SpannableString msg = EmojiUtils.text2Emoji(mContext, item.getLastMsgContent(), descTextView.getTextSize());
            descTextView.setText(msg);

        }else {
            descTextView.setVisibility(View.GONE);

        }

        nameTextView.setText(item.getGroup().getName());
    }
}
