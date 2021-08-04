package com.haife.app.nobles.spirits.kotlin.app.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextViewURLOnclick {
    private Context mContext;
    public TextViewURLOnclick(Context context) {
        mContext = context;
    }
    public void setLinkClickIntercept(TextView tv) {
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        CharSequence text = tv.getText();
        if (text instanceof Spannable) {
            Spannable sp = (Spannable) tv.getText();
            initLinks(sp, hyypUrl, tv);
        }
    }

    private void initLinks(Spannable sp, Pattern hyypUrl, TextView tv) {
        Matcher m = hyypUrl.matcher(sp);
        LinkedList<String> urls = new LinkedList<String>();
        while (m.find()) {
            urls.add(m.group());
        }
        String con = tv.getText().toString();
        SpannableStringBuilder spannable = new SpannableStringBuilder(con);
        for (int i = 0; i < urls.size(); i++) {
            String url = urls.get(i);
            int start = con.indexOf(url);
            int end = start + url.length();
            MyURLSpan myURLSpan = new MyURLSpan(url);
            spannable.setSpan(myURLSpan, start,
                    end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
            spannable.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            avoidHintColor(tv);
            tv.setText(spannable);
        }
    }

    /**
     * 处理TextView中的链接点击事件
     */
    private class MyURLSpan extends ClickableSpan {
        private String mUrl;

        MyURLSpan(String url) {
            mUrl = url;
        }

        @Override
        public void onClick(View widget) {
            avoidHintColor(widget);
            Toast.makeText(mContext, mUrl, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 点击链接的时候，连接背景色设置*
     */
    private void avoidHintColor(View view) {
        if (view instanceof TextView)
            ((TextView) view).setHighlightColor(mContext.getResources().getColor(android.R.color.transparent));
    }
    public static final Pattern hyypUrl
            = Pattern.compile(
            "(((http|ftp|https)://)|(\\s))?(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?");}
