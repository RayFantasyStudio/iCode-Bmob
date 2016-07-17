package rayfantasy.icode.Ui;

import android.os.*;
import android.view.*;
import android.content.*;

import android.support.v7.app.*;

import rayfantasy.icode.R;

public class Splash extends AppCompatActivity
{
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		setContentView(R.layout.activity_splash);
		handler.postDelayed(new Runnable(){
			public void run(){
				startActivity(new Intent(Splash.this,MainActivity.class));
			}
		},700);
	}
}
