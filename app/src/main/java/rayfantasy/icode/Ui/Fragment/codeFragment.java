package rayfantasy.icode.Ui.Fragment;

import rayfantasy.icode.*;
import android.view.*;
import android.os.*;
import android.content.*;
import rayfantasy.icode.Bmob.*;
import cn.bmob.v3.*;
import cn.bmob.v3.datatype.*;
import cn.bmob.v3.listener.*;
import java.util.*;
import cn.bmob.v3.exception.*;
import android.widget.*;
import android.support.v7.widget.*;
import android.graphics.*;
import com.amulyakhare.textdrawable.*;
import de.hdodenhof.circleimageview.*;
import rayfantasy.icode.R;
import android.support.v4.widget.SwipeRefreshLayout;
import rayfantasy.icode.Adapter.*;
import android.view.animation.*;
import com.rengwuxian.materialedittext.*;
import android.support.v4.app.Fragment;
import android.view.View.*;
import com.melnykov.fab.*;
import rayfantasy.icode.Ui.View.*;

public class codeFragment extends Fragment
{
	private MyApplication myApplication;
	private View v;
	private Intent i;
	private User user;
	
	private TextView Title,Time,UserName;
	private TextEditorView Message;
	private CircleImageView Head;
	private TextDrawable drawableBuilder;
	
	private int HeadColor,HeadVersion;
	private String id,Email,HeadUri,STitle,SMessage,STime,SUserName;
	private String path=Environment.getExternalStorageDirectory().getPath()+"/.iCode";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		v=inflater.inflate(R.layout.fragment_code,container,false);
		init();
		initView(v);
		return v;
	}

	private void init()
	{
		i=getActivity().getIntent();
		myApplication=(MyApplication)getActivity().getApplication();
		user=BmobUser.getCurrentUser(User.class);
		
		HeadColor=i.getIntExtra("HeadColor",getResources().getColor(R.color.PrimaryColor));
		STitle=i.getStringExtra("Title");
		SMessage=i.getStringExtra("Message");
		STime=i.getStringExtra("Time");
		SUserName=i.getStringExtra("UserName");
		id=i.getStringExtra("Id");
		Email=i.getStringExtra("Email");
		HeadUri=i.getStringExtra("HeadUri");
		HeadVersion=i.getIntExtra("HeadVersion",0);
	}

	private void initView(View v)
	{
		Time=(TextView)v.findViewById(R.id.list_time);
		Title=(TextView)v.findViewById(R.id.list_title);
		Message=(TextEditorView)v.findViewById(R.id.list_message);
		UserName=(TextView)v.findViewById(R.id.list_user);
		Head=(CircleImageView)v.findViewById(R.id.list_head);
		
		
		Title.setText(STitle);
		//if(SMessage.length()<300){
			Message.setText(SMessage);
		/*}else{
			Message.setText(SMessage.subSequence(0,300)+"…");
		}*/
		UserName.setText(SUserName);
		Time.setText(STime);
		//用户是否登录
		if(user!=null){
			Title.setTextColor(getTextColor(user.getHeadColor()));
			if(i.getIntExtra("HeadVersion",0)==0){
				//当用户从未上传头像时，设置默认头像
				Head.setImageResource(0);
				Head.setBackground(drawableBuilder.builder().buildRound(UserName.getText().toString().subSequence(0,1).toString(),
																					  getTextColor(user.getHeadColor())));
			}else if(myApplication.isFile("/cache/"+Email+"_"+HeadVersion+".png")){
				Head.setBackgroundResource(0);
				Head.setImageBitmap(BitmapFactory.decodeFile(path+"/cache/"+Email+"_"+HeadVersion+".png"));
			}else{
				myApplication.downloadFile(new BmobFile(Email,"",HeadUri),Email+"_"+HeadVersion,Head);
			}
		}else{
			//处理用户未登录
			Title.setTextColor(R.color.PrimaryColor);
			Head.setImageResource(0);
			Head.setBackground(drawableBuilder.builder().buildRound(UserName.getText().toString().subSequence(0,1).toString(),
											R.color.PrimaryColor));
		}
		
	}
	
	//String转int
	public int getTextColor(String s){
		return Integer.valueOf(s).intValue();
	}
	
}
