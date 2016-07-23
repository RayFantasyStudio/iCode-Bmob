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

public class userActivity extends BaseActivity
{
	private BmobUser bmobUser;
	private Intent i;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		init();
	}

	private void init()
	{
		bmobUser=BmobUser.getCurrentUser();
		if(bmobUser!=null){
			userFragment fragment_user=new userFragment();
			getFragmentManager().beginTransaction().replace(R.id.fragmentLayout,fragment_user).commit();
			
		}else{
			signFragment fragment_sign=new signFragment();
			getFragmentManager().beginTransaction().replace(R.id.fragmentLayout,fragment_sign).commit();
		}
		
	}
	
	@Override
	protected String getTitleText()
	{
		i=getIntent();
		return i.getStringExtra("UserName");
	}
	
	@Override
	protected int getLayoutRes()
	{
		return R.layout.activity_user;
	}
	
}
