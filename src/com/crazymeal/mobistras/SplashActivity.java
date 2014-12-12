package com.crazymeal.mobistras;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class SplashActivity extends Activity {
	
	private static final int STOPSPLASH = 0;
	private static final long SPLASHTIME = 2000;

	private final transient Handler splashHandler = new Handler() {
		@Override
		public void handleMessage(Message msg)
		{
		    if (msg.what == STOPSPLASH)
		    {
		        final Animation animation = AnimationUtils.loadAnimation(getBaseContext(),
		                                                                 android.R.anim.fade_out);
		        animation.setAnimationListener(new AnimationListener()
		        {
		            @Override public void onAnimationEnd(Animation animation)
		            {
		                ((LinearLayout)findViewById(R.id.splash)).setVisibility(View.INVISIBLE);
		                final Intent intent = new Intent(SplashActivity.this, MainActivity.class);
		                startActivity(intent);
		                finish();
		            }

		            @Override public void onAnimationRepeat(Animation animation)
		            {
		            }

		            @Override public void onAnimationStart(Animation animation)
		            {
		            }
		        });

		        ((LinearLayout)findViewById(R.id.splash)).startAnimation(animation);
		    }

		    super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		final Message msg = new Message();
		msg.what = STOPSPLASH;
		splashHandler.sendMessageDelayed(msg, SPLASHTIME);
	}
	
}