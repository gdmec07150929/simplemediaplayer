package com.gourd.adien.simplemediaplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="SimpleMediaPlayer";
    private MediaPlayer mMdiaPlayer;
    private String mPath;
    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        Uri uri = intent.getData();
        if (uri!=null){
            mPath = uri.getPath();
            if (intent.getType().contains("audio")){
                setContentView(android.R.layout.simple_list_item_1);
                mMdiaPlayer = new MediaPlayer();
                try {
                    mMdiaPlayer.setDataSource(mPath);
                    mMdiaPlayer.prepare();
                    mMdiaPlayer.start();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }else if(intent.getType().contains("vedio")){
                mVideoView = new VideoView(this);
                mVideoView.setVideoPath(mPath);
                mVideoView.setMediaController(new MediaController(this));
                mVideoView.start();
                setContentView(mVideoView);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0,"暂停");
        menu.add(0,2,0,"开始");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:
                if (mMdiaPlayer==null||!mMdiaPlayer.isPlaying()){
                    Toast.makeText(this,"没有音乐文件，不需要暂停",Toast.LENGTH_SHORT).show();
                }else{
                    mMdiaPlayer.pause();
                }
                break;
            case 2:
                if (mMdiaPlayer==null){
                    Toast.makeText(this,"没有选中音乐文件，请到文件夹中点击音乐文件后再播放",Toast.LENGTH_SHORT).show();
                }else{
                    mMdiaPlayer.start();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMdiaPlayer!=null){
            mMdiaPlayer.stop();
        }
        if (mMdiaPlayer!=null){
            mVideoView.stopPlayback();
        }
    }
}
