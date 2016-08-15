package rayfantasy.icode.Ui.Activity;

import android.app.*;
import android.os.*;

import rayfantasy.icode.*;
import rayfantasy.icode.Ui.Fragment.*;

import cn.bmob.v3.*;

import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.content.*;
import rayfantasy.icode.Bmob.*;
import android.view.*;
import rayfantasy.icode.Ui.Fragment.userFragment.*;

public class userActivity extends AppCompatActivity implements userFragment.OnFabClickListener
{
	private User bmobUser;
	protected Toolbar toolbar;
	private Window window;
	private Intent i;
	
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
        setContentView(R.layout.activity_user);
		i=getIntent();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setBackgroundColor(	i.getIntExtra("HeadColor",getResources().getColor(R.color.PrimaryColor)));
		setTitle(i.getStringExtra("UserName"));
		setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onBackPressed();
				}
			});
		init();
	}

	private void init()
	{
		bmobUser=BmobUser.getCurrentUser(User.class);
		if(bmobUser!=null){
			getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout,new userFragment()).commit();
		}else{
			getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout,new signFragment()).commit();
		}
		
	}

	@Override
	public void OnClick(View v1, int color)
	{
		setToolBarBackgroundColor(color);
	}
	
	public void setToolBarBackgroundColor(int color){
		toolbar.setBackgroundColor(color);
	}
	
	
}
