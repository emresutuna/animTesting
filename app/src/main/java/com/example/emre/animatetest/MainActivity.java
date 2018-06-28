package com.example.emre.animatetest;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    LinearLayout playGround;
    ImageView image;
    AccelerateInterpolator accelerateInterpolator;
    public int height;
    ViewTreeObserver viewTreeObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView)findViewById(R.id.image);
        viewTreeObserver=image.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                image.getViewTreeObserver().removeOnPreDrawListener(this);
                height=image.getHeight();
                return  true;
            }
        });

        Button clk=(Button)findViewById(R.id.button);
        Button git=(Button)findViewById(R.id.button2);
        playGround = (LinearLayout)findViewById(R.id.playground);
        git.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(i);
            }
        });

        clk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    prepareObjectAnimator(accelerateInterpolator);

            }
        });
        prepareInterpolator();
    }

public void prepareInterpolator(){
        accelerateInterpolator=new AccelerateInterpolator();
}

    public void prepareObjectAnimator(TimeInterpolator timeInterpolator){
        //float w = (float)playGround.getWidth();
        float h = (float)playGround.getHeight();
        float propertyStart = 0f;
        float propertyEnd = (h/2 - (float)height/2);
        String propertyName = "translationY";
        ObjectAnimator objectAnimator
                = ObjectAnimator.ofFloat(image, propertyName, propertyStart, propertyEnd);
        objectAnimator.setDuration(1000);
        objectAnimator.setRepeatCount(5);
        objectAnimator.setRepeatMode(ObjectAnimator.REVERSE);
        objectAnimator.setInterpolator(timeInterpolator);
        objectAnimator.start();
    }

}
