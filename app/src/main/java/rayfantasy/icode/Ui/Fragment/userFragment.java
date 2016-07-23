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



public class userFragment extends Fragment implements OnClickListener,OnLongClickListener
{
	private MyApplication myApplication;
	private User user;
	
	private FloatingActionButton fab_finish_user;
	private TextView userName,userabout;
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

		userName=(TextView)v.findViewById(R.id.usermain_username);
		userabout=(TextView)v.findViewById(R.id.usermain_about);
		userImage=(CircleImageView)v.findViewById(R.id.user_image);

		fab_finish_user.setOnClickListener(this);
		fab_finish_user.setOnLongClickListener(this);
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
		BmobUser.logOut();
		//清除缓存用户对象
		BmobUser currentUser = BmobUser.getCurrentUser();
		// 现在的currentUser是null了
		getActivity().finish();
	}
	
}
