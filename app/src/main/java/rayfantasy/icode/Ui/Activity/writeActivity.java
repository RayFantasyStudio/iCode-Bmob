package rayfantasy.icode.Ui.Activity;

import android.content.*;
import android.os.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import rayfantasy.icode.*;
import rayfantasy.icode.Ui.Fragment.*;

public class writeActivity extends AppCompatActivity
{
	private Intent i;
	protected Toolbar toolbar;
	private Window window;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			window = getWindow();
			window.setFlags(
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		setContentView(R.layout.activity_write);
		getSupportFragmentManager().beginTransaction().add(R.id.fragmentLayout_write,new writeFragment()).commit();
		i=getIntent();
		
        toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setBackgroundColor(i.getIntExtra("HeadColor",getResources().getColor(R.color.PrimaryColor)));
		setTitle(i.getStringExtra("id") == null ? "发布" : "修改");
		setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onBackPressed();
				}
			});
		
		
	}
	

}
