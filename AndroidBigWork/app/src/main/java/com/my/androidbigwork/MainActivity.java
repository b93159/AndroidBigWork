package com.my.androidbigwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private androidx.recyclerview.widget.RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MyAdapter myAdapter;
    private List<VideoInfo> mVideoInfos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (androidx.recyclerview.widget.RecyclerView)findViewById(R.id.recycler_view);

        //创建linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mVideoInfos = new ArrayList<>();
        //创建adapter
        myAdapter = new MyAdapter(this);

        recyclerView.setAdapter(myAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://beiyou.bytedance.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getVideoInfos().enqueue(new Callback<List<VideoInfo>>() {

            @Override
            public void onResponse(Call<List<VideoInfo>> call, Response<List<VideoInfo>> response) {
                if (response.body() != null){
                    List<VideoInfo> videoInfos = response.body();
                    if (videoInfos.size() != 0){
                        myAdapter.setData(videoInfos);
                        myAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onFailure(Call<List<VideoInfo>> call, Throwable t) {
                //Log.d("retrofit fail", t.getMessage());
            }
        });



    }
}
