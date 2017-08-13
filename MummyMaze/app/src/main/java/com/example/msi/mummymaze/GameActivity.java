package com.example.msi.mummymaze;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.SoundPool;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.logging.Level;

import static com.example.msi.mummymaze.R.id.game;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    private AnimView animView;
    private Button up;
    private Button down;
    private Button left;
    private Button right;
    private int level;
    private RelativeLayout layout;
    private SoundPool soundPool;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private int[] soundID;//创建某个声音对应的音频ID

        @SuppressLint("NewApi")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
            setContentView(R.layout.activity_game);
            level=getIntent().getIntExtra("level",0);
            layout=(RelativeLayout)findViewById(R.id.mylayout);
            builder = new AlertDialog.Builder(this);
            switch (level){
                case 1:layout.setBackgroundResource(R.drawable.map2);break;
                case 2: layout.setBackgroundResource(R.drawable.map3);break;
                case 3: layout.setBackgroundResource(R.drawable.map4);break;
                case 4: layout.setBackgroundResource(R.drawable.map5);break;
                case 5: layout.setBackgroundResource(R.drawable.map6);break;
                case 6: layout.setBackgroundResource(R.drawable.map7);break;
                case 7: layout.setBackgroundResource(R.drawable.map8);break;
                case 8: layout.setBackgroundResource(R.drawable.map9);break;
                case 9: layout.setBackgroundResource(R.drawable.map10);break;
            }
            animView = (AnimView) findViewById(game);
            animView.initialize(level);
            up = (Button) findViewById(R.id.up);
            down = (Button) findViewById(R.id.down);
            left = (Button) findViewById(R.id.left);
            right = (Button) findViewById(R.id.right);
            up.setOnClickListener(this);
            down.setOnClickListener(this);
            left.setOnClickListener(this);
            right.setOnClickListener(this);
            animView.setChangeListener(changeListener);
            animView.setOverListener(overListener);
            soundPool = new SoundPool.Builder().build();
            soundID=new int[4];
            soundID[0]=soundPool.load(this, R.raw.gameover, 0);
            soundID[1]=soundPool.load(this, R.raw.walk, 0);
            soundID[2]=soundPool.load(this, R.raw.victory, 0);
            soundID[3]=soundPool.load(this, R.raw.tombslide, 0);
            if (getSupportActionBar()!=null) {
                getSupportActionBar().hide();
            }
        }
    private AnimView.OnCancelListener cancelListener = new AnimView.OnCancelListener(){
        ;
    };

    private AnimView.OnOverListener overListener=new AnimView.OnOverListener() {
            @Override
            public void onOver(Boolean over,int levels) {
                if(over==true)
                    level=levels ;
                ShowOverdialog();
            }
        };
        private AnimView.OnChangeListener changeListener = new AnimView.OnChangeListener(){
            @Override
            public void onChange(int level) {
                switch (level){
                    case 1:layout.setBackgroundResource(R.drawable.map2);break;
                    case 2: layout.setBackgroundResource(R.drawable.map3);break;
                    case 3: layout.setBackgroundResource(R.drawable.map4);break;
                    case 4: layout.setBackgroundResource(R.drawable.map5);break;
                    case 5: layout.setBackgroundResource(R.drawable.map6);break;
                    case 6: layout.setBackgroundResource(R.drawable.map7);break;
                    case 7: layout.setBackgroundResource(R.drawable.map8);break;
                    case 8: layout.setBackgroundResource(R.drawable.map9);break;
                    case 9: layout.setBackgroundResource(R.drawable.map10);break;
                    case 10:dialog=builder.show();
                        Window window = dialog.getWindow();
                        window.setContentView(R.layout.vitory);
                        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        soundPool.play(soundID[2], 1f, 1f, 0, 0, 1);break;
                }
            }
        };

        public void onClick(View view) {
            switch (view.getId()){
                case R.id.up:
                    animView.setmove(0);
                    if(animView.getmove())
                        soundPool.play(soundID[1], 1f, 1f, 0, 0, 1);
                    break;
                case R.id.down :
                    animView.setmove(1);
                    if(animView.getmove())
                        soundPool.play(soundID[1], 1f, 1f, 0, 0, 1);
                    break;
                case R.id.left:
                    animView.setmove(2);
                    if(animView.getmove())
                        soundPool.play(soundID[1], 1f, 1f, 0, 0, 1);
                    break;
                case R.id.right:
                    animView.setmove(3);
                    if(animView.getmove())
                        soundPool.play(soundID[1], 1f, 1f, 0, 0, 1);
                    break;
                case R.id.space:
                    animView.setmove(4);
                    if(animView.getmove())
                        soundPool.play(soundID[1], 1f, 1f, 0, 0, 1);
                    break;
                case R.id.cancel:
                    animView.setCancelListener(cancelListener);
                    animView.MakeCancel();
                    break;
                case R.id.restart:
                    animView.initialize(animView.getLevel());
                    break;
                case R.id.menu:
                    soundPool.play(soundID[3], 1f, 1f, 0, 0, 1);
                    finish();
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                    break;
            }
        }
        private void ShowOverdialog(){
            soundPool.play(soundID[0], 1f, 1f, 0, 0, 1);
            AlertDialog.Builder builder;
            builder=new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.over);
            builder.setTitle("游戏结束,是否重来");
            builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    animView.initialize(level);
                }
            });
            builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            AlertDialog dialog=builder.create();
            Window window =  dialog.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            // 设置透明度为0.3
            lp.alpha = 0.8f;
            window.setAttributes(lp);
            dialog.show();
        }
    @Override
    public void onBackPressed() {
        soundPool.play(soundID[3], 1f, 1f, 0, 0, 1);
        finish();
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }
}