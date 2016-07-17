package com.icode.Ui.Fragment;

import android.content.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.app.*;

import com.icode.*;
import com.icode.Bmob.*;
import com.icode.Ui.*;
import com.icode.R;

import android.support.design.widget.*;

import cn.bmob.v3.*;
import cn.bmob.v3.listener.*;
import com.melnykov.fab.FloatingActionButton;

import com.rengwuxian.materialedittext.*;


public class signFragment extends Fragment implements OnClickListener,OnLongClickListener
{
	private MaterialEditText account,password;
	private FloatingActionButton fab_sign_up,fab_sign_in;
	
	private MyApplication myApplication;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v=inflater.inflate(R.layout.fragment_sign,container,false);
		myApplication=(MyApplication)this.getActivity().getApplication();
		initView(v);
		return v;
		
	}

	private void initView(View v)
	{
		account=(MaterialEditText)v.findViewById(R.id.usermainMaterialEditText_account);
		password=(MaterialEditText)v.findViewById(R.id.usermainMaterialEditText_password);
		fab_sign_up=(FloatingActionButton)v.findViewById(R.id.sign_up);
		fab_sign_in=(FloatingActionButton)v.findViewById(R.id.sign_in);
		
		
		
		fab_sign_in.setOnClickListener(this);
		fab_sign_up.setOnClickListener(this);
		fab_sign_up.setOnLongClickListener(this);
		fab_sign_in.setOnLongClickListener(this);
		
		
	}
	
	@Override
	public void onClick(View p1)
	{
		switch(p1.getId()){
			case R.id.sign_up:
				if(isCharacter()){
					Login(account.getText().toString(),password.getText().toString());
				}else{
					Snackbar.make(p1,"输入格式有误！",1000).show();
				}
				break;
			case R.id.sign_in:
				if(isCharacter()){
					LoginData(account.getText().toString(),password.getText().toString());
				}else{
					Snackbar.make(p1,"输入格式有误！",1000).show();
				}
				break;
		}
	}

	@Override
	public boolean onLongClick(View p1)
	{
		switch(p1.getId()){
			case R.id.sign_up:
				Snackbar.make(p1,"登录",1000).show();
				break;
			case R.id.sign_in:
				Snackbar.make(p1,"注册",1000).show();
				break;
		}
		return false;
	}
	
	//登录
	private void Login(String UserName,String Password){
		User bu2 = new User();
		bu2.setUsername(UserName);
		bu2.setPassword(Password);
		bu2.login(getActivity(), new SaveListener() {

				@Override
				public void onSuccess()
				{
					myApplication.showToast("登录成功");
					startActivity(new Intent(getActivity(),MainActivity.class));
					getActivity().finish();
				}

				@Override
				public void onFailure(int p1, String p2)
				{
					if(p1==101){
						myApplication.showToast("帐号或密码不正确");
					}else if(p1==304){
						myApplication.showToast("其中一项输入为空");
					}else{
						myApplication.showToast("登录失败："+p1+p2);
					}
				}
			});
	}

	//注册
	public void LoginData(final String UserName,final String Password){
		User bu = new User();
		bu.setUsername(UserName);
		bu.setPassword(Password);
		bu.signUp(getActivity(), new SaveListener(){
				@Override
				public void onSuccess()
				{
					myApplication.showToast("注册成功，正在登录");
					Login(UserName,Password);
				}

				@Override
				public void onFailure(int p1, String p2)
				{
					if(p1==202){
						myApplication.showToast("该用户名已被注册！");
					}else if(p1==304){
						myApplication.showToast("其中一项输入为空");
					}else{
						myApplication.showToast("注册失败"+p1);
					}
				}
			});
	}
	
	private boolean isCharacter(){
		if(account.getText().toString().length()>5||account.getText().toString().length()<18
		   &&password.getText().toString().length()>6||password.getText().toString().length()<18){
			return true;
		}else{
			return false;
		}
	}

	
	
}
