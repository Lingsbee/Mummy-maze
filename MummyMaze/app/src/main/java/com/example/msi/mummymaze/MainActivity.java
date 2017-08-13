package com.example.msi.mummymaze;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by msi on 2017/7/15.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private AlertDialog dialog;
    private int choise;
    private SoundPool soundPool;
    private int soundID[];//创建某个声音对应的音频ID
    private AlertDialog.Builder builder;
    private ImageView tutorial;
    public AudioManager audiomanage;
    public SeekBar soundBar;
    private int maxVolume, currentVolume;
    private MediaPlayer mp;
    Intent intent=new Intent();
    @SuppressLint("NewApi")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        builder = new AlertDialog.Builder(this);
        soundPool = new SoundPool.Builder().build();
        soundID=new int[4];
        soundID[0] =soundPool.load(this, R.raw.start, 0);
        choise=0;
        mp = MediaPlayer.create(this,R.raw.bgmusic);
        mp.setLooping(true);
        mp.start();
        if (getSupportActionBar()!=null) {
            getSupportActionBar().hide();
        }
    }
    View.OnClickListener handler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onBackPressed();
        }
    };

    public void onClick(final View view) {

        switch (view.getId()){
            case R.id.start:
                showSimpleDialog();
                break;
            case R.id.close:
                System.exit(0);
                break;
            case R.id.setup:
                dialog=builder.show();
                Window window = dialog.getWindow();
                window.setContentView(R.layout.volume);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                soundBar = (SeekBar) window.findViewById(R.id.sound);
                audiomanage = (AudioManager)this. getSystemService(Context.AUDIO_SERVICE);
                maxVolume = audiomanage.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                soundBar.setMax(maxVolume);
                currentVolume = audiomanage.getStreamVolume(AudioManager.STREAM_MUSIC);
                soundBar.setProgress(currentVolume);
                soundBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    public void onProgressChanged(SeekBar arg0, int progress, boolean fromUser) {
                        audiomanage.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                        currentVolume = audiomanage.getStreamVolume(AudioManager.STREAM_MUSIC);
                        soundBar.setProgress(currentVolume);
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });
                break;
            case R.id.tutorial:
                dialog=builder.show();
                Window win = dialog.getWindow();
                WindowManager.LayoutParams lp = win.getAttributes();
                // 设置透明度为0.3
                win.setContentView(R.layout.dialog);
                win.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                break;
        }
    }

    private void showSimpleDialog() {
        builder=new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("关卡选择");
        final String[] items={"Level_one","Level_two","Level_three",
                "Level_four","Level_five","Level_six","Level_seven",
                "Level_eight","Level_night","Level_ten",};
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), items[i], Toast.LENGTH_SHORT).show();
                choise=i;
            }
        });

        builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intent=new Intent(getApplicationContext(),GameActivity.class);
                intent.putExtra("level",choise);
                startActivity(intent);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                playSound(0);
            }
        })  ;
        builder.setNegativeButton("取消",null);
        builder.setCancelable(true);
        AlertDialog dialog=builder.create();
        Window window =  dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        // 设置透明度为0.3
        lp.alpha = 0.8f;
        window.setAttributes(lp);
        dialog.show();
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
