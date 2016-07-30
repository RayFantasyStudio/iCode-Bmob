package rayfantasy.icode.Ui.Activity;

import android.os.*;

import rayfantasy.icode.*;

import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.content.*;
import rayfantasy.icode.Ui.Fragment.*;

public class codeActivity extends BaseActivity
{
	private Intent i;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		init();
	}

	private void init()
	{
		getFragmentManager().beginTransaction().replace(R.id.fragmentLayout,new codeFragment()).commit();
	}

	@Override
	protected int getLayoutRes()
	{
		return R.layout.activity_code;
	}
	
	@Override
	protected String getTitleText()
	{
		i=getIntent();
		return i.getStringExtra("Title");
	}

	@Override
	protected int getBackgroundColor()
	{
		i=getIntent();
		return i.getIntExtra("HeadColor",getResources().getColor(R.color.PrimaryColor));
	}

	@Override
	protected int getNavigationBarColor()
	{
		i=getIntent();
		return i.getIntExtra("HeadColor",getResources().getColor(R.color.PrimaryColor));
	}
	
}
