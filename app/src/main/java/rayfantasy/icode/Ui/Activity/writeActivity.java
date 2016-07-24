package rayfantasy.icode.Ui.Activity;

import android.os.*;

import rayfantasy.icode.*;

import android.support.v7.app.*;
import android.support.v7.widget.*;

public class writeActivity extends BaseActivity
{
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	protected int getLayoutRes()
	{
		return R.layout.activity_write;
	}

	@Override
	protected String getTitleText()
	{
		return "发布";
	}
	
	@Override
	protected int getBackgroundColor()
	{
		return getResources().getColor(R.color.PrimaryColor);
	}
}
