package rayfantasy.icode.Ui.Fragment;

import android.content.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
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
import at.markushi.ui.*;
import com.amulyakhare.textdrawable.*;
import android.widget.*;
import android.support.v4.app.*;
import com.blankj.utilcode.utils.*;


public class signFragment extends Fragment implements OnClickListener,OnLongClickListener
{
	private MaterialEditText account,password;
	private CircleButton cb_sign_up;
	private Button fab_sign_in;
	private MyApplication myApplication;
	private ImageView mCircleImageView;
	private TextDrawable drawableBuilder;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v=inflater.inflate(R.layout.fragment_sign,container,false);
		myApplication=(MyApplication)getActivity().getApplication();
		initView(v);
		return v;
		
	}

	private void initView(View v)
	{
		account=(MaterialEditText)v.findViewById(R.id.usermainMaterialEditText_account);
		password=(MaterialEditText)v.findViewById(R.id.usermainMaterialEditText_password);
		
		mCircleImageView=(ImageView)v.findViewById(R.id.user_image_sign);
		cb_sign_up=(CircleButton)v.findViewById(R.id.sign_up);
		fab_sign_in=(Button)v.findViewById(R.id.sign_in);
		
		cb_sign_up.setColor(getResources().getColor(R.color.PrimaryColor));
		//fab_sign_in.setColorNormal(getResources().getColor(R.color.PrimaryColor));
		//fab_sign_in.setColorPressed(getResources().getColor(R.color.PrimaryColor));
		
		fab_sign_in.setOnClickListener(this);
		cb_sign_up.setOnClickListener(this);
		cb_sign_up.setOnLongClickListener(this);
		fab_sign_in.setOnLongClickListener(this);
		
		account.addTextChangedListener(new EditChangedListener());
	}
	
	@Override
	public void onClick(View p1)
	{
		switch(p1.getId()){
			case R.id.sign_up:
				if(isCharacter(password.getText().toString().length(),6,18)){
					setMetVisibility(true);
					loginByEmail(account.getText().toString(),password.getText().toString());
				}else{
					myApplication.showSnackBar(getActivity(),"输入格式有误！");
				}
				break;
			case R.id.sign_in:
				if(isCharacter(password.getText().toString().length(),6,18)){
					setMetVisibility(true);
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
	
	private void setMetVisibility(boolean v){
		if(v){
			account.setFocusable(false);
			account.setFocusableInTouchMode(false);
			account.setHideUnderline(true);
			password.setFocusable(false);
			password.setHideUnderline(true);
			password.setFocusableInTouchMode(false);
		}else{
			account.setFocusable(true);
			account.setFocusableInTouchMode(true);
			account.setHideUnderline(false);
			password.setFocusable(true);
			password.setHideUnderline(false);
			password.setFocusableInTouchMode(true);
		}

	}
	
	//注册
	public void LoginData(final String UserName,final String Password){
		User bu = new User();
		bu.setPassword(Password);
		bu.setEmail(UserName);
		bu.setHeadUri(UserName.subSequence(0,1).toString());
		bu.setHeadVersion(0);
		bu.setUsername(UserName);
		bu.setHeadColor(myApplication.getUserRandomColor());
		bu.setAbout(UserName+"很懒什么都没有写");
		bu.signUp(new SaveListener<User>(){
				@Override
				public void done(User p1, BmobException e)
				{
					if(e==null){
						loginByEmail(UserName,Password);
						myApplication.showSnackBar(getActivity(),"注册成功，正在登录");
					}else{
						setMetVisibility(false);
						myApplication.showToast("错误码："+e.getErrorCode()+",错误原因："+e.getLocalizedMessage());
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
						SPUtils.putInt(getActivity(),user.getEmail(),user.getHeadVersion());
						SPUtils.putBoolean(getActivity(),user.getEmail()+"_isLoading",true);
						getFragmentManager().popBackStack();
						getActivity().finish();
					}else{
						setMetVisibility(false);
						myApplication.showToast("错误码："+e.getErrorCode()+",错误原因："+e.getLocalizedMessage());
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
	
	class EditChangedListener implements TextWatcher {
        private CharSequence temp;//监听前的文本
        private int editStart;//光标开始位置
        private int editEnd;//光标结束位置
        private final int charMaxNum = 10;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
			
            if (temp.length() > charMaxNum) {
				/*s.delete(editStart - 1, editEnd);
                int tempSelection = editStart;
                account.setText(s);
                account.setSelection(tempSelection);*/
				mCircleImageView.setBackgroundDrawable(drawableBuilder.builder().buildRound(
				account.getText().toString().substring(0,1).toString(),
				getResources().getColor(R.color.PrimaryColor)));
            }
			
        }
    };
}
