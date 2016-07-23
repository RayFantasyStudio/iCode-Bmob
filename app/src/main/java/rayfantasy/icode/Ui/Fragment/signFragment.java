package rayfantasy.icode.Ui.Fragment;

import android.content.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.app.*;

import rayfantasy.icode.*;
import rayfantasy.icode.Bmob.*;
import rayfantasy.icode.Ui.*;
import rayfantasy.icode.R;

import android.support.design.widget.*;

import com.melnykov.fab.FloatingActionButton;
import com.rengwuxian.materialedittext.*;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.*;
import cn.bmob.v3.listener.*;
import android.text.*;


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
		//phone_Number=(MaterialEditText)v.findViewById(R.id.usermainMaterialEditText_phoneNumber);
		//phone_Number.setInputType(InputType.TYPE_CLASS_PHONE);
		
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
				if(isCharacter(account.getText().toString().length(),password.getText().toString().length())){
					loginByAccountPwd(account.getText().toString(),password.getText().toString());
				}else{
					Snackbar.make(p1,"输入格式有误！",1000).show();
				}
				break;
			case R.id.sign_in:
				if(isCharacter(account.getText().toString().length(),password.getText().toString().length())){
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
	
	//手机号登录
	private void loginByAccountPwd(String Account,String Password){
		BmobUser.loginByAccount(Account, Password, new LogInListener<User>() {
				@Override
				public void done(User user, BmobException e) {
					if(user!=null){
						myApplication.showToast("登录成功");
						getActivity().finish();
					}else{
						myApplication.showToast("错误码："+e.getErrorCode()+",错误原因："+e.getLocalizedMessage());
					}
				}
			});			
	}

	//注册
	public void LoginData(final String UserName,final String Password){
		User bu = new User();
		bu.setPassword(Password);
		bu.setUsername(UserName);
		bu.setHead_Color(myApplication.getUserRandomColor());
		bu.setAbout(UserName+"很懒什么都没有写");
		bu.signUp(new SaveListener<User>(){
				@Override
				public void done(User p1, BmobException e)
				{
					if(e==null){
						myApplication.showToast("注册成功，正在登录");
						loginByAccountPwd(UserName,Password);
					}else{
						myApplication.showToast("注册失败"+e);
					}
				}
		});
		
	}
	
	private boolean isCharacter(int i,int j){
		if(i>=5&&i<=18&&j>=6&&j<=18){
			return true;
		}else{
			return false;
		}
	}

	
	
}
