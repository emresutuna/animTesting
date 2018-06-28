package com.example.emre.animatetest;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    ImageView imageView;
    AccelerateInterpolator accelerateInterpolator;
    LinearLayout linear;
    ViewTreeObserver viewTreeObserver;
    int height;
    int status=0;
    AnimationDrawable anim;
    public ProgressBar progressBar;
private  Handler mhandler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        linear = (LinearLayout) findViewById(R.id.linear2);
        imageView = (ImageView) findViewById(R.id.imageview);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        if (imageView == null) throw new AssertionError();
        imageView.setBackgroundResource(R.drawable.animation_plane);

        progressBar.setProgress(0);
        viewTreeObserver = imageView.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                height = imageView.getHeight();
                return true;
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationDrawable anim = (AnimationDrawable) imageView.getBackground();
                anim.start();
                prepareObjectAnimator(accelerateInterpolator);
                prepareInterpolator();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while (status <100) {
                                status++;
                                android.os.SystemClock.sleep(50);
                                mhandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setProgress(status); }
                                });
                        }
                        }catch (Exception e){

                        }finally {
                            mhandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Main2Activity.this, "fff", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                }).start();
            }
        });
    }
    public void prepareInterpolator() {
        accelerateInterpolator = new AccelerateInterpolator();
    }
    public void prepareObjectAnimator(TimeInterpolator timeInterpolator) {
        //float w = (float)playGround.getWidth();
        float h = (float) linear.getHeight();
        float propertyStart = 0f;
        float propertyEnd = (h / 4 - (float) height / 4);
        String propertyName = "translationY";
        ObjectAnimator objectAnimator
                = ObjectAnimator.ofFloat(imageView, propertyName, propertyStart, propertyEnd);
        objectAnimator.setDuration(2000);
        objectAnimator.setRepeatCount(5);
        objectAnimator.setRepeatMode(ObjectAnimator.REVERSE);
        objectAnimator.setInterpolator(timeInterpolator);
        objectAnimator.start();
    }
    }