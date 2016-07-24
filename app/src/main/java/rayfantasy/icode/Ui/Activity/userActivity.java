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

public class userActivity extends BaseActivity implements userFragment.OnFabClickListener
{
	private User bmobUser;
	
	private Intent i;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		init();
	}

	private void init()
	{
		bmobUser=BmobUser.getCurrentUser(User.class);
		if(bmobUser!=null){
			getFragmentManager().beginTransaction().replace(R.id.fragmentLayout,new userFragment()).commit();
		}else{
			getFragmentManager().beginTransaction().replace(R.id.fragmentLayout,new signFragment()).commit();
		}
		
	}

	@Override
	public void OnClick(View v1, int color)
	{
		setToolBarBackgroundColor(color);
	}
	
	
	@Override
	protected String getTitleText()
	{
		i=getIntent();
		return i.getStringExtra("UserName");
	}
	
	@Override
	protected int getBackgroundColor()
	{
		i=getIntent();
		return i.getIntExtra("HeadColor",getResources().getColor(R.color.PrimaryColor));
	}
	
	@Override
	protected int getLayoutRes()
	{
		return R.layout.activity_user;
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}
	
}
