package rayfantasy.icode.Ui.Activity;

import android.os.Bundle;
import android.view.View;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import rayfantasy.icode.R;
import android.graphics.*;

public abstract class BaseActivity extends AppCompatActivity {
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
	protected abstract String getTitleText();
	
	protected abstract int getBackgroundColor();

    protected abstract int getLayoutRes();

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAfterTransition(this);
    }
}
