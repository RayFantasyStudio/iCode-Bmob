package rayfantasy.icode.Ui.Activity;

import android.os.*;

import rayfantasy.icode.*;

import android.support.v7.app.*;
import android.support.v7.widget.*;

public class writeActivity extends BaseActivity
{

	@Override
	protected String getTitleText()
	{
		return "发布";
	}


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

	
}
