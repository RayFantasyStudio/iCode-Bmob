package com.icode.Ui.Activity;

import android.os.*;

import com.icode.*;

import android.support.v7.app.*;
import android.support.v7.widget.*;

public class writeActivity extends BaseActivity
{

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		toolbar.setTitle("发布");
		
	}

	@Override
	protected int getLayoutRes()
	{
		return R.layout.activity_write;
	}

	
}
