package com.example.hasee.cardanimationdemo;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private FrameLayout mCardMianContainer;
    private LinearLayout mCardFont,mCardBack;
    private AnimatorSet mRightout,mLeftIn;

    private boolean mIsShowBac = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
    }
    private void initView(){
        mCardMianContainer = (FrameLayout)findViewById(R.id.card_mian_continer);
        mCardFont = (LinearLayout)findViewById(R.id.card_font_container);
        mCardBack = (LinearLayout)findViewById(R.id.card_back_container);

        setAnimators();
        setCameraDistance();//设置镜头距离
    }

    private void initEvent(){
        mCardMianContainer.setOnClickListener(this);
    }

    private void setAnimators(){
        mLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.anim_left_in);
        mRightout = (AnimatorSet)AnimatorInflater.loadAnimator(this,R.animator.anim_right_in);

        //设置点击事件
        mRightout.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mCardMianContainer.setClickable(false);
            }
        });

        mLeftIn.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mCardMianContainer.setClickable(true);
            }
        });

    }
    private void setCameraDistance(){
        int distance = 1600;
        float scale = getResources().getDisplayMetrics().density*distance;
        mCardBack.setCameraDistance(scale);
        mCardFont.setCameraDistance(scale);
    }

    private void flipCard(){
        if (!mIsShowBac){
            //正面在上
            mRightout.setTarget(mCardFont);
            mLeftIn.setTarget(mCardBack);
            mRightout.start();
            mLeftIn.start();
            mIsShowBac = true;
        }else {
            //背面在上
            mRightout.setTarget(mCardBack);
            mLeftIn.setTarget(mCardFont);
            mRightout.start();
            mLeftIn.start();
            mIsShowBac = false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card_mian_continer:
                flipCard();
                break;
        }

    }
}
