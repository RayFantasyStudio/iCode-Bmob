package com.icode.Ui.Fragment;

import android.view.View.*;
import android.view.*;
import android.os.*;
import android.widget.*;
import android.graphics.*;
import android.app.*;

import com.icode.*;
import com.icode.R;

import cn.bmob.v3.*;
import com.melnykov.fab.FloatingActionButton;
import com.amulyakhare.textdrawable.*;

import android.support.design.widget.*;



public class userFragment extends Fragment implements OnClickListener,OnLongClickListener
{
	private MyApplication myApplication;
	private BmobUser bmobUser;
	
	
	private FloatingActionButton fab_finish_user;
	private TextView userName;
	private TextDrawable.IBuilder drawableBuilder;
	private ImageView userImage;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_user,container,false);
		myApplication=(MyApplication)this.getActivity().getApplication();
		initView(v);
		init();
		return v;
	}

	private void initView(View v)
	{
		fab_finish_user=(FloatingActionButton)v.findViewById(R.id.finish_user);

		userName=(TextView)v.findViewById(R.id.usermain_username);
		userImage=(ImageView)v.findViewById(R.id.user_image);


		fab_finish_user.setOnClickListener(this);
		fab_finish_user.setOnLongClickListener(this);
	}

	private void init()
	{
		bmobUser = BmobUser.getCurrentUser(getActivity());
		if(bmobUser!=null){
			userName.setText(bmobUser.getUsername());
			drawableBuilder=TextDrawable.builder().round();
			userImage.setImageDrawable(drawableBuilder.build(userName.getText().toString().subSequence(0,1).toString(),
															 Color.BLUE));
		}
	}
	
	@Override
	public void onClick(View p1)
	{
		switch(p1.getId()){
			case R.id.finish_user:
				finishLogin();
				break;
		}
	}


	@Override
	public boolean onLongClick(View p1)
	{
		switch(p1.getId()){
			case R.id.finish_user:
				Snackbar.make(p1,"退出当前账户",1000).show();
				break;
		}
		return false;
	}

	//退出登录
	private void finishLogin(){
		BmobUser.logOut(getActivity());
		//清除缓存用户对象
		BmobUser currentUser = BmobUser.getCurrentUser(getActivity());
		// 现在的currentUser是null了
		getActivity().finish();
	}
	
}
