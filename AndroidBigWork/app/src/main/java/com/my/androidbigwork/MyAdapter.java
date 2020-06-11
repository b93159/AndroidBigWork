package com.my.androidbigwork;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<VideoInfo> videoData;
    private Context mContext;
    private static final int UPDATE_UI = 1;
    private int oldProgress = 0;

    public MyAdapter(Context context){
        mContext = context;
    }


    // 每一个videoholder对应一个视频
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public MyVideo videoView;
        public ImageButton btn_play;
        public Button btn_pause;
        public ImageView vedioImage;



        public MyViewHolder(View v){
            super(v);
            videoView=v.findViewById(R.id.videoView);
            btn_pause=v.findViewById(R.id.btn_pause);
            btn_play=v.findViewById(R.id.btn_play);
            vedioImage=v.findViewById(R.id.videoImage);
        }
    }

    public void setData(List<VideoInfo> myDataset) { videoData = myDataset; }

    @Override
	//将view封装在viewHolder中
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
	//渲染数据到view中
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.videoView.setVideoPath(videoData.get(position).feedurl);
        holder.videoView.setMediaController(new MediaController(mContext));


        Glide.with(mContext).load(videoData.get(position).avatar).placeholder(R.drawable.loading).into(holder.vedioImage);

        //点击播放暂停
        holder.videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (holder.videoView.isPlaying()){
                    holder.videoView.pause();
                }else {
                    holder.videoView.start();
                    holder.vedioImage.setVisibility(View.GONE);
                }
                return false;
            }
        });

    }



    // 返回item数
    @Override
    public int getItemCount() {
        return videoData == null ? 0 : videoData.size();
    }

}

