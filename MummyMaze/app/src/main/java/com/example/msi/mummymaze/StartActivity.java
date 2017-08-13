package com.example.msi.mummymaze;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class StartActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView pic;
    private Button start;
    private Button quit;
    private MediaPlayer mp;
    private SoundPool soundPool;
    private int soundID[];//创建某个声音对应的音频ID
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);
        pic= (ImageView) findViewById(R.id.imageView);
        start= (Button) findViewById(R.id.Startgame);
        quit= (Button) findViewById(R.id.Quitgame);
        mp = MediaPlayer.create(this,R.raw.startpage);
        mp.setLooping(true);
        mp.start();
        soundPool = new SoundPool.Builder().build();
        soundID=new int[4];
        soundID[0] = soundPool.load(this, R.raw.clickstart, 0);
        if (getSupportActionBar()!=null) {
            getSupportActionBar().hide();
        }
    }


    @Override
    public void onClick(View view) {
        Intent intent=new Intent(this,MainActivity.class);
        Timer timer = new Timer();
       switch (view.getId()){
           case R.id.Startgame:
               startActivity(intent);
               finish();
               playSound(0);
               mp.stop();
               break;
           case R.id.Quitgame:
               playSound(0);
               System.exit(0);
               break;
       }
    }
    private void playSound(int id) {
        soundPool.play(
                soundID[id],
                1f,   //左耳道音量【0~1】
                1f,   //右耳道音量【0~1】
                0,     //播放优先级【0表示最低优先级】
                0,     //循环模式【0表示循环一次，-1表示一直循环，其他表示数字+1表示当前数字对应的循环次数】
                1     //播放速度【1是正常，范围从0~2】
        );
    }
}
