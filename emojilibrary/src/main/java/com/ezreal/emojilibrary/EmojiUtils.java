package com.ezreal.emojilibrary;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表情包管理工具类
 * Created by wudeng on 2017/11/2.
 */

public class EmojiUtils {

    private static final int[] EMOJI_INDEX = {
            R.drawable.smile, // 呵呵
            R.drawable.laughing, // 可爱
            R.drawable.blush, // 太开心
            R.drawable.heart_eyes, // 鼓掌
            R.drawable.smirk, // 嘻嘻
            R.drawable.flushed, // 哈哈
            R.drawable.grin, // 笑哭
            R.drawable.kissing_smiling_eyes, // 调皮
            R.drawable.wink, // 汗
            R.drawable.kissing_closed_eyes, // 挖鼻屎
            R.drawable.stuck_out_tongue_winking_eye,// 馋嘴
            R.drawable.sleeping, // 黑线
            R.drawable.worried, // 哼
            R.drawable.sweat_smile, // 怒
            R.drawable.cold_sweat, // 可怜
            R.drawable.joy, // 流泪
            R.drawable.sob, // 大哭
            R.drawable.angry,// 害羞
            R.drawable.mask, // 爱你
            R.drawable.scream,// 亲亲


            R.drawable.sunglasses, // doge
            R.drawable.thumbsup, // miao
            R.drawable.clap, //阴险
            R.drawable.ok_hand,// 偷笑


    };

    private static final String[] EMOJI_NAME = {
            ":emoji[smile]",
            ":emoji[laughing]",
            ":emoji[blush]",
            ":emoji[heart_eyes]",
            ":emoji[smirk]",
            ":emoji[flushed]",
            ":emoji[grin]",
            ":emoji[kissing_smiling_eyes]",
            ":emoji[wink]",
            ":emoji[kissing_closed_eyes]",
            ":emoji[stuck_out_tongue_winking_eye]",
            ":emoji[sleeping]",
            ":emoji[worried]",
            ":emoji[sweat_smile]",
            ":emoji[cold_sweat]",
            ":emoji[joy]",
            ":emoji[sob]",
            ":emoji[angry]",
            ":emoji[mask]",
            ":emoji[scream]",

            ":emoji[sunglasses]",
            ":emoji[thumbsup]",
            ":emoji[clap]",
            ":emoji[ok_hand]",

    };

    private static List<EmojiBean> sEmojiBeans;
    private static Map<String, Integer> sEmojiMap;
    private static EmojiBean emojiBean2;

    static {
        createEmojiList();
    }

    static List<EmojiBean> getEmojiBeans() {
        if (sEmojiBeans == null) {
            createEmojiList();
        }
        return sEmojiBeans;
    }
    public static boolean isfourtimes(int cout) {
        // TODO Auto-generated method stub

        if(cout%20==0){
            return true;
        }else {return false;}

    }
    private static void createEmojiList() {
        sEmojiBeans = new ArrayList<>();
        sEmojiMap = new HashMap<>();
        EmojiBean emojiBean;
        for (int i = 0; i < EMOJI_INDEX.length; i++) {
            emojiBean = new EmojiBean();
            emojiBean2 = new EmojiBean();
            emojiBean.setResIndex(EMOJI_INDEX[i]);
            emojiBean.setEmojiName(EMOJI_NAME[i]);
            sEmojiBeans.add(emojiBean);
            if(isfourtimes(i+1)||i==(EMOJI_INDEX.length-1)){
                emojiBean2.setResIndex(R.drawable.face_delete);
                emojiBean2.setEmojiName("[删除]");
                sEmojiBeans.add(emojiBean2);
            }
            sEmojiMap.put(EMOJI_NAME[i], EMOJI_INDEX[i]);
        }

    }

    /**
     * 从 Resource 中读取 Emoji 表情
     *
     * @param res       Resource
     * @param resId     Emoji'id in Resource
     * @param reqWidth  ImageView Width
     * @param reqHeight ImageView Height
     * @return Emoji with Bitmap
     */
    static Bitmap decodeBitmapFromRes(Resources res, int resId,
                                      int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 计算图片压缩比例
     *
     * @param options   Bitmap Decode Option
     * @param reqWidth  ImageView Width
     * @param reqHeight ImageView Height
     * @return inSample Size value
     */
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    // 单位转化
    static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static SpannableString text2Emoji(Context context, final String source, final float textSize) {
        SpannableString spannableString = new SpannableString(source);
        Resources res = context.getResources();
        String regexEmotion = "\\:emoji\\[(.*?)\\]";
        Pattern patternEmotion = Pattern.compile(regexEmotion);
        Matcher matcherEmotion = patternEmotion.matcher(spannableString);
        while (matcherEmotion.find()) {
            // 获取匹配到的具体字符
            String key = matcherEmotion.group();
            // 匹配字符串的开始位置
            int start = matcherEmotion.start();
            // 利用表情名字获取到对应的图片
            Integer imgRes = getImgByName(key);
            if (imgRes != 0) {
                // 压缩表情图片
                int size = (int) (textSize * 13.0f / 10.0f);
                Bitmap bitmap = BitmapFactory.decodeResource(res, imgRes);
                Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);
                ImageSpan span = new ImageSpan(context, scaleBitmap);
                spannableString.setSpan(span, start, start + key.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return spannableString;
    }

    public static Integer getImgByName(String name) {
        if (sEmojiMap.containsKey(name)) {
            return sEmojiMap.get(name);
        } else return 0;

    }

}
