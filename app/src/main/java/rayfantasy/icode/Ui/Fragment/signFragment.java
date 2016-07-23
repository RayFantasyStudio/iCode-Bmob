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
				if(isCharacter(password.getText().toString().length(),6,18)){
					loginByEmail(account.getText().toString(),password.getText().toString());
				}else{
					myApplication.showSnackBar(getActivity(),"输入格式有误！");
				}
				break;
			case R.id.sign_in:
				if(isCharacter(password.getText().toString().length(),6,18)){
					LoginData(account.getText().toString(),password.getText().toString());
				}else{
					myApplication.showSnackBar(getActivity(),"输入格式有误！");
				}
				break;
		}
	}

	@Override
	public boolean onLongClick(View p1)
	{
		switch(p1.getId()){
			case R.id.sign_up:
				myApplication.showSnackBar(getActivity(),"登录");
				break;
			case R.id.sign_in:
				myApplication.showSnackBar(getActivity(),"注册");
				break;
		}
		return false;
	}
	
	//注册
	public void LoginData(final String UserName,final String Password){
		User bu = new User();
		bu.setPassword(Password);
		bu.setEmail(UserName);
		bu.setUsername(UserName);
		bu.setHeadColor(myApplication.getUserRandomColor());
		bu.setAbout(UserName+"很懒什么都没有写");
		bu.signUp(new SaveListener<User>(){
				@Override
				public void done(User p1, BmobException e)
				{
					if(e==null){
						myApplication.showSnackBar(getActivity(),"注册成功，正在登录");
						loginByEmail(UserName,Password);
					}else{
						myApplication.showToast("注册失败"+e);
					}
				}
		});
		
	}
	
	private void loginByEmail(String Email,String Password){
		BmobUser.loginByAccount(Email, Password, new LogInListener<User>() {
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
	
	//验证邮箱验证码
	private void isEmailVerified(final String email){
		BmobUser.requestEmailVerify(email, new UpdateListener() {
				@Override
				public void done(BmobException e) {
					if(e==null){
						myApplication.showToast("请求验证邮件成功，请到" + email + "邮箱中进行激活。");
					}else{
						myApplication.showToast("失败:" + e.getMessage());
					}
				}
			});
	}

	//检测输入框是否符合规则
	public boolean isCharacter(int i,int a,int b){
		if(i>=a&&i<=b){
			return true;
		}else{
			return false;
		}
	}
	
	
	//密码+帐号登录，已弃用
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
	
}
