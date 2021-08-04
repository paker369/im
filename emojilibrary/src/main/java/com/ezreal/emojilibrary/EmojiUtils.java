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


    private static List<EmojiBean> sEmojiBeans;
    private static Map<String,Integer> sEmojiMap;


    private static final String[] EMOJI_NAME = {
            "[抓狂]",
            "[钞票]",
            "[晕]",
            "[招财猫]",
            "[钻戒]",
            "[跳跳]",
            "[回头]",
            "[转圈]",
            "[饭]",
            "[擦汗]",
            "[凋谢]",
            "[礼物]",
            "[篮球]",
            "[挥手]",
            "[喝彩]",
            "[发财]",
            "[鼓掌]",
            "[咒骂]",
            "[糗大了]",
            "[下雨]",
            "[亲嘴]",
            "[爱你]",
            "[献吻]",
            "[快哭了]",
            "[困]",
            "[多云]",
            "[爆筋]",
            "[左车头]",
            "[呲牙]",
            "[发怒]",
            "[右哼哼]",
            "[喝奶]",
            "[棒棒糖]",
            "[帅]",
            "[流泪]",
            "[刀]",
            "[左太极]",
            "[抠鼻]",
            "[祈祷]",
            "[纸巾]",
            "[阴险]",
            "[咖啡]",
            "[太阳]",
            "[灯泡]",
            "[便便]",
            "[闹钟]",
            "[西瓜]",
            "[鞭炮]",
            "[手枪]",
            "[瓢虫]",
            "[购物]",
            "[憨笑]",
            "[饥饿]",
            "[飞吻]",
            "[啤酒]",
            "[色]",
            "[差劲]",
            "[可怜]",
            "[衰]",
            "[乒乓]",
            "[雨伞]",
            "[再见]",
            "[菜刀]",
            "[坏笑]",
            "[右车头]",
            "[大哭]",
            "[示爱]",
            "[害羞]",
            "[蛋糕]",
            "[胶囊]",
            "[委屈]",
            "[OK]",
            "[流汗]",
            "[敲打]",
            "[右太极]",
            "[握手]",
            "[玫瑰]",
            "[吓]",
            "[拥抱]",
            "[心碎]",
            "[撇嘴]",
            "[可爱]",
            "[怄火]",
            "[胜利]",
            "[开车]",
            "[K歌]",
            "[抱拳]",
            "[哈欠]",
            "[尴尬]",
            "[拳头]",
            "[飞机]",
            "[香蕉]",
            "[傲慢]",
            "[熊猫]",
            "[跳绳]",
            "[闭嘴]",
            "[大兵]",
            "[炸弹]",
            "[微笑]",
            "[激动]",
            "[邮件]",
            "[磕头]",
            "[青蛙]",
            "[惊讶]",
            "[偷笑]",
            "[沙发]",
            "[NO]",
            "[人民币]",
            "[闪电]",
            "[足球]",
            "[气球]",
            "[强]",
            "[嘘]",
            "[风车]",
            "[猪头]",
            "[灯笼]",
            "[下面]",
            "[双喜]",
            "[难过]",
            "[疑问]",
            "[爱情]",
            "[月亮]",
            "[弱]",
            "[严肃]",
            "[惊恐]",
            "[折磨]",
            "[奋斗]",
            "[街舞]",
            "[吐]",
            "[小女孩]",
            "[冷汗]",
            "[鄙视]",
            "[白眼]",
            "[车厢]",
            "[得意]",
            "[发抖]",
            "[爱心]",
            "[睡]",
            "[勾引]",
            "[调皮]",
            "[左哼哼]",
            "[发呆]",
            "[骷髅]",
    };
    static {
        createEmojiList();
    }


    private static void createEmojiList(){
        sEmojiBeans = new ArrayList<>();
        sEmojiMap = new HashMap<>();
        EmojiBean emojiBean;
        EmojiBean emojiBean2;
//        for (int i = 0;i<EMOJI_INDEX.length;i++){
//            emojiBean = new EmojiBean();
//            emojiBean.setResIndex(EMOJI_INDEX[i]);
//            emojiBean.setEmojiName(EMOJI_NAME[i]);
//            sEmojiBeans.add(emojiBean);
//            sEmojiMap.put(EMOJI_NAME[i],EMOJI_INDEX[i]);
//        }
        sEmojiMap.put( "[抓狂]",         R.drawable.face1_018);
        sEmojiMap.put( "[钞票]",         R.drawable.face1_130);
        sEmojiMap.put( "[晕]"   ,         R.drawable.face1_034);
        sEmojiMap.put( "[招财猫]",        R.drawable.face1_107);
        sEmojiMap.put( "[钻戒]",        R.drawable.face1_137);
        sEmojiMap.put( "[跳跳]",        R.drawable.face1_092);
        sEmojiMap.put( "[回头]",        R.drawable.face1_097);
        sEmojiMap.put( "[转圈]",        R.drawable.face1_095);
        sEmojiMap.put( "[饭]",        R.drawable.face1_061);
        sEmojiMap.put( "[擦汗]",        R.drawable.face1_040);
        sEmojiMap.put( "[凋谢]",        R.drawable.face1_064);
        sEmojiMap.put( "[礼物]",        R.drawable.face1_077);
        sEmojiMap.put( "[篮球]",        R.drawable.face1_058);
        sEmojiMap.put( "[挥手]",        R.drawable.face1_099);
        sEmojiMap.put( "[喝彩]",        R.drawable.face1_116);
        sEmojiMap.put( "[发财]",        R.drawable.face1_111);
        sEmojiMap.put( "[鼓掌]",        R.drawable.face1_042);
        sEmojiMap.put( "[咒骂]",        R.drawable.face1_031);
        sEmojiMap.put( "[糗大了]",        R.drawable.face1_043);
        sEmojiMap.put( "[下雨]",        R.drawable.face1_129);
        sEmojiMap.put( "[亲嘴]",        R.drawable.face1_052);
        sEmojiMap.put( "[爱你]",        R.drawable.face1_087);
        sEmojiMap.put( "[献吻]",        R.drawable.face1_102);
        sEmojiMap.put( "[快哭了]",        R.drawable.face1_050);
        sEmojiMap.put( "[困]",        R.drawable.face1_025);
        sEmojiMap.put( "[多云]",        R.drawable.face1_128);
        sEmojiMap.put( "[爆筋]",        R.drawable.face1_118);
        sEmojiMap.put( "[左车头]",        R.drawable.face1_125);
        sEmojiMap.put( "[呲牙]",        R.drawable.face1_013);
        sEmojiMap.put( "[发怒]",        R.drawable.face1_011);
        sEmojiMap.put( "[右哼哼]",        R.drawable.face1_046);
        sEmojiMap.put( "[喝奶]",        R.drawable.face1_120);
        sEmojiMap.put( "[棒棒糖]",        R.drawable.face1_119);
        sEmojiMap.put( "[帅]",        R.drawable.face1_115);
        sEmojiMap.put( "[流泪]",        R.drawable.face1_005);
        sEmojiMap.put( "[刀]",        R.drawable.face1_071);
        sEmojiMap.put( "[左太极]",        R.drawable.face1_103);
        sEmojiMap.put( "[抠鼻]",        R.drawable.face1_041);
        sEmojiMap.put( "[祈祷]",        R.drawable.face1_117);
        sEmojiMap.put( "[纸巾]",        R.drawable.face1_139);
        sEmojiMap.put( "[阴险]",        R.drawable.face1_051);
        sEmojiMap.put( "[咖啡]",        R.drawable.face1_060);
        sEmojiMap.put( "[太阳]",        R.drawable.face1_076);
        sEmojiMap.put( "[灯泡]",        R.drawable.face1_132);
        sEmojiMap.put( "[便便]",        R.drawable.face1_074);
        sEmojiMap.put( "[闹钟]",        R.drawable.face1_134);
        sEmojiMap.put( "[西瓜]",        R.drawable.face1_056);
        sEmojiMap.put( "[鞭炮]",        R.drawable.face1_109);
        sEmojiMap.put( "[手枪]",        R.drawable.face1_141);
        sEmojiMap.put( "[瓢虫]",        R.drawable.face1_073);
        sEmojiMap.put( "[购物]",        R.drawable.face1_113);
        sEmojiMap.put( "[憨笑]",        R.drawable.face1_028);
        sEmojiMap.put( "[饥饿]",        R.drawable.face1_024);
        sEmojiMap.put( "[飞吻]",        R.drawable.face1_091);
        sEmojiMap.put( "[啤酒]",        R.drawable.face1_057);
        sEmojiMap.put( "[色]",        R.drawable.face1_002);
        sEmojiMap.put( "[差劲]",        R.drawable.face1_086);
        sEmojiMap.put( "[可怜]",        R.drawable.face1_054);
        sEmojiMap.put( "[衰]",        R.drawable.face1_036);
        sEmojiMap.put( "[乒乓]",        R.drawable.face1_059);
        sEmojiMap.put( "[雨伞]",        R.drawable.face1_135);
        sEmojiMap.put( "[再见]",        R.drawable.face1_039);
        sEmojiMap.put( "[菜刀]",        R.drawable.face1_055);
        sEmojiMap.put( "[坏笑]",        R.drawable.face1_044);
        sEmojiMap.put( "[右车头]",        R.drawable.face1_127);
        sEmojiMap.put( "[大哭]",        R.drawable.face1_009);
        sEmojiMap.put( "[示爱]",        R.drawable.face1_065);
        sEmojiMap.put( "[害羞]",        R.drawable.face1_006);
        sEmojiMap.put( "[蛋糕]",        R.drawable.face1_068);
        sEmojiMap.put( "[胶囊]",        R.drawable.face1_140);
        sEmojiMap.put( "[委屈]",        R.drawable.face1_049);
        sEmojiMap.put( "[OK]",        R.drawable.face1_089);
        sEmojiMap.put( "[流汗]",        R.drawable.face1_027);
        sEmojiMap.put( "[敲打]",        R.drawable.face1_038);
        sEmojiMap.put( "[右太极]",        R.drawable.face1_104);
        sEmojiMap.put( "[握手]",        R.drawable.face1_081);
        sEmojiMap.put( "[玫瑰]",        R.drawable.face1_063);
        sEmojiMap.put( "[吓]",        R.drawable.face1_053);
        sEmojiMap.put( "[拥抱]",        R.drawable.face1_078);
        sEmojiMap.put( "[心碎]",        R.drawable.face1_067);
        sEmojiMap.put( "[撇嘴]",        R.drawable.face1_001);
        sEmojiMap.put( "[可爱]",        R.drawable.face1_021);
        sEmojiMap.put( "[怄火]",        R.drawable.face1_094);
        sEmojiMap.put( "[胜利]",        R.drawable.face1_082);
        sEmojiMap.put( "[开车]",        R.drawable.face1_124);
        sEmojiMap.put( "[K歌]",        R.drawable.face1_112);
        sEmojiMap.put( "[抱拳]",        R.drawable.face1_083);
        sEmojiMap.put( "[哈欠]",        R.drawable.face1_047);
        sEmojiMap.put( "[尴尬]",        R.drawable.face1_010);
        sEmojiMap.put( "[拳头]",        R.drawable.face1_085);
        sEmojiMap.put( "[飞机]",        R.drawable.face1_123);
        sEmojiMap.put( "[香蕉]",        R.drawable.face1_122);
        sEmojiMap.put( "[傲慢]",        R.drawable.face1_023);
        sEmojiMap.put( "[熊猫]",        R.drawable.face1_131);
        sEmojiMap.put( "[跳绳]",        R.drawable.face1_098);
        sEmojiMap.put( "[闭嘴]",        R.drawable.face1_007);
        sEmojiMap.put( "[大兵]",        R.drawable.face1_029);
        sEmojiMap.put( "[炸弹]",        R.drawable.face1_070);
        sEmojiMap.put( "[微笑]",        R.drawable.face1_000);
        sEmojiMap.put( "[激动]",        R.drawable.face1_100);
        sEmojiMap.put( "[邮件]",        R.drawable.face1_114);
        sEmojiMap.put( "[磕头]",        R.drawable.face1_096);
        sEmojiMap.put( "[青蛙]",        R.drawable.face1_142);
        sEmojiMap.put( "[惊讶]",        R.drawable.face1_014);
        sEmojiMap.put( "[偷笑]",        R.drawable.face1_020);
        sEmojiMap.put( "[沙发]",        R.drawable.face1_138);
        sEmojiMap.put( "[NO]",        R.drawable.face1_088);
        sEmojiMap.put( "[人民币]",        R.drawable.face1_106);
        sEmojiMap.put( "[闪电]",        R.drawable.face1_069);
        sEmojiMap.put( "[足球]",        R.drawable.face1_072);
        sEmojiMap.put( "[气球]",        R.drawable.face1_136);
        sEmojiMap.put( "[强]",        R.drawable.face1_079);
        sEmojiMap.put( "[嘘]",        R.drawable.face1_033);
        sEmojiMap.put( "[风车]",        R.drawable.face1_133);
        sEmojiMap.put( "[猪头]",        R.drawable.face1_062);
        sEmojiMap.put( "[灯笼]",        R.drawable.face1_110);
        sEmojiMap.put( "[下面]",        R.drawable.face1_121);
        sEmojiMap.put( "[双喜]",        R.drawable.face1_108);
        sEmojiMap.put( "[难过]",        R.drawable.face1_015);
        sEmojiMap.put( "[疑问]",        R.drawable.face1_032);
        sEmojiMap.put( "[爱情]",        R.drawable.face1_090);
        sEmojiMap.put( "[月亮]",        R.drawable.face1_075);
        sEmojiMap.put( "[弱]",        R.drawable.face1_080);
        sEmojiMap.put( "[严肃]",        R.drawable.face1_016);
        sEmojiMap.put( "[惊恐]",        R.drawable.face1_026);
        sEmojiMap.put( "[折磨]",        R.drawable.face1_035);
        sEmojiMap.put( "[奋斗]",        R.drawable.face1_030);
        sEmojiMap.put( "[街舞]",        R.drawable.face1_101);
        sEmojiMap.put( "[吐]",        R.drawable.face1_019);
        sEmojiMap.put( "[小女孩]",        R.drawable.face1_105);
        sEmojiMap.put( "[冷汗]",        R.drawable.face1_017);
        sEmojiMap.put( "[鄙视]",        R.drawable.face1_048);
        sEmojiMap.put( "[白眼]",        R.drawable.face1_022);
        sEmojiMap.put( "[车厢]",        R.drawable.face1_126);
        sEmojiMap.put( "[得意]",        R.drawable.face1_004);
        sEmojiMap.put( "[发抖]",        R.drawable.face1_093);
        sEmojiMap.put( "[爱心]",        R.drawable.face1_066);
        sEmojiMap.put( "[睡]",        R.drawable.face1_008);
        sEmojiMap.put( "[勾引]",        R.drawable.face1_084);
        sEmojiMap.put( "[调皮]",        R.drawable.face1_012);
        sEmojiMap.put( "[左哼哼]",        R.drawable.face1_045);
        sEmojiMap.put( "[发呆]",        R.drawable.face1_003);
        sEmojiMap.put( "[骷髅]",        R.drawable.face1_037);
        for (int i = 0;i<sEmojiMap.size();i++){
            emojiBean = new EmojiBean();
            emojiBean2 = new EmojiBean();
            emojiBean.setResIndex(sEmojiMap.get(EMOJI_NAME[i]));
            emojiBean.setEmojiName(EMOJI_NAME[i]);
            sEmojiBeans.add(emojiBean);
            if(isfourtimes(i+1)||i==(sEmojiMap.size()-1)){
                emojiBean2.setResIndex(R.drawable.face1_delete);
                emojiBean2.setEmojiName("[删除]");
                sEmojiBeans.add(emojiBean2);
            }
        }

    }
    static List<EmojiBean> getEmojiBeans(){
        if (sEmojiBeans == null){
            createEmojiList();
        }
        return sEmojiBeans;
    }
    /**
     * 从 Resource 中读取 Emoji 表情
     * @param res Resource
     * @param resId Emoji'id in Resource
     * @param reqWidth ImageView Width
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
     * @param options Bitmap Decode Option
     * @param reqWidth ImageView Width
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
            inSampleSize = heightRatio < widthRatio ? heightRatio:  widthRatio;
        }
        return inSampleSize;
    }

    public static boolean isfourtimes(int cout) {
        // TODO Auto-generated method stub

        if(cout%34==0){
            return true;
        }else {return false;}

    }
    // 单位转化
    static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static SpannableString text2Emoji(Context context,final String source,final float textSize) {
        SpannableString spannableString = new SpannableString(source);
        Resources res = context.getResources();
        String regexEmotion = "\\[([\u4e00-\u9fa5\\w])+\\]";
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

    public static Integer getImgByName(String name){
        if(sEmojiMap.containsKey(name)) {
            return sEmojiMap.get(name);
        }else return 0;

    }

}
