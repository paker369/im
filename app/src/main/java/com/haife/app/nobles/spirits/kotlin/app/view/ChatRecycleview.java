package com.haife.app.nobles.spirits.kotlin.app.view;//package com.ynkj.jdb4android.app.view.chat;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.ynkj.jdb4android.R;
//
//public class ChatRecycleview extends RecyclerView {
//    Context context;
//    public ChatRecycleview(@NonNull Context context) {
//        super(context);
//        this.context=context;
//    }
//
//    public ChatRecycleview(@NonNull Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//        this.context=context;
//    }
//
//    public ChatRecycleview(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        this.context=context;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == -1) {
//            return new ViewHolder(new View(context));
//        }
//        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.ui_simpletext, parent, false));
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (position == 0) {
//            return -1;
//        }
//        return super.getItemViewType(position);
//    }
//
//}
