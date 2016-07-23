package rayfantasy.icode.Ui.Fragment;

import android.view.View.*;
import android.view.*;
import android.os.*;
import android.widget.*;
import android.graphics.*;
import android.app.*;

import rayfantasy.icode.*;
import rayfantasy.icode.R;

import cn.bmob.v3.*;
import com.melnykov.fab.FloatingActionButton;
import com.amulyakhare.textdrawable.*;

import android.support.design.widget.*;
import de.hdodenhof.circleimageview.*;
import android.graphics.drawable.*;
import rayfantasy.icode.Bmob.*;
import cn.bmob.v3.listener.*;
import cn.bmob.v3.exception.*;
import com.rengwuxian.materialedittext.*;

public class userFragment extends Fragment implements OnClickListener,OnLongClickListener
{
	private MyApplication myApplication;
	private User user;
	
	private FloatingActionButton fab_finish_user,fab_updata_user;
	private TextView userName,userabout;
	private MaterialEditText met1,met2;
	
	private TextDrawable drawableBuilder;
	private CircleImageView userImage;
	private int HeadColor;
	private String User_About;
	
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
		fab_updata_user=(FloatingActionButton)v.findViewById(R.id.updata_user);
		
		userName=(TextView)v.findViewById(R.id.usermain_username);
		userabout=(TextView)v.findViewById(R.id.usermain_about);
		met1=(MaterialEditText)v.findViewById(R.id.fragmentuserMaterialEditText1);
		met2=(MaterialEditText)v.findViewById(R.id.fragmentuserMaterialEditText2);
		
		userImage=(CircleImageView)v.findViewById(R.id.user_image);
		met1.setTextSize(25);
		met2.setTextSize(15);

		fab_finish_user.setOnClickListener(this);
		fab_finish_user.setOnLongClickListener(this);
		fab_updata_user.setOnClickListener(this);
		fab_updata_user.setOnLongClickListener(this);
	}

	private void init()
	{
		user = BmobUser.getCurrentUser(User.class);
		if(user!=null){
			userName.setText(user.getUsername());
			HeadColor=myApplication.getHeadColor((String)user.getObjectByKey("HeadColor"));
			User_About=(String)user.getObjectByKey("About");
			userImage.setBackground(drawableBuilder.builder().buildRound(userName.getText().toString().subSequence(0,1).toString(),HeadColor));
			userabout.setText(User_About);
			met1.setVisibility(View.GONE);
			met2.setVisibility(View.GONE);
		}
	}
	
	@Override
	public void onClick(View p1)
	{
		switch(p1.getId()){
			case R.id.finish_user:
				finishLogin();
				break;
			case R.id.updata_user:
				if(fab_finish_user.getVisibility()==View.VISIBLE){
					userName.setVisibility(View.GONE);
					userabout.setVisibility(View.GONE);
					met1.setVisibility(View.VISIBLE);
					met2.setVisibility(View.VISIBLE);
					met1.setText(userName.getText());
					met2.setText(userabout.getText());
					fab_finish_user.setVisibility(View.GONE);
				}else{
					if(myApplication.isCharacter(met1.getText().toString().length(),met2.getText().toString().length(),5,18,0,50)){
						userName.setVisibility(View.VISIBLE);
						userabout.setVisibility(View.VISIBLE);
						met1.setVisibility(View.GONE);
						met2.setVisibility(View.GONE);
						fab_finish_user.setVisibility(View.VISIBLE);
						updata(met1.getText().toString(),met2.getText().toString());
					}else{
						myApplication.showSnackBar(getActivity(),"其中一项不符合规则");
					}
				}
				
				break;
		}
	}

	private void updata(String UserName,String About){
		User newUser = new User();
		newUser.setAbout(About);
		newUser.setUsername(UserName);
		BmobUser bmobUser = BmobUser.getCurrentUser(User.class);
		newUser.update(bmobUser.getObjectId(), new UpdateListener() {
				@Override
				public void done(BmobException e) {
					if(e==null){
						myApplication.showToast("更新用户信息成功");
					}else{
						myApplication.showToast("更新用户信息失败:" + e.getMessage());
					}
				}
			});
	}

	@Override
	public boolean onLongClick(View p1)
	{
		switch(p1.getId()){
			case R.id.finish_user:
				Snackbar.make(p1,"退出当前账户",1000).show();
				break;
			case R.id.updata_user:
				Snackbar.make(p1,"修改",1000).show();
				break;
		}
		return false;
	}

	//退出登录
	private void finishLogin(){
		BmobUser.logOut();
		//清除缓存用户对象
		BmobUser currentUser = BmobUser.getCurrentUser();
		// 现在的currentUser是null了
		getActivity().finish();
	}
	
}
