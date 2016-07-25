package rayfantasy.icode.Ui.Activity;

import android.os.Bundle;
import android.view.View;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import rayfantasy.icode.R;
import android.graphics.*;
import android.view.*;
import android.os.*;

public abstract class BaseActivity extends AppCompatActivity {
    protected Toolbar toolbar;
	private Window window;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			window = getWindow();
			window.setFlags(
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setNavigationBarColor(getNavigationBarColor());
		}
        setContentView(getLayoutRes());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setBackgroundColor(getBackgroundColor());
		setTitle(getTitleText());
		setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
			}
        });
	}
	
	
	public void setToolBarBackgroundColor(int color){
		toolbar.setBackgroundColor(color);
	}
	
	public void setNavigationBarColor(int color){
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			window.setNavigationBarColor(color);
		}
	}
	
	protected abstract String getTitleText();
	
	protected abstract int getBackgroundColor();
	
	protected abstract int getNavigationBarColor();

    protected abstract int getLayoutRes();

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAfterTransition(this);
    }
}
