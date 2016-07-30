package rayfantasy.icode.Ui.Fragment;

import android.app.*;
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

public class codeFragment extends Fragment
{
	private MyApplication myApplication;
	private View v;
	private Intent i;
	private User user;
	
	private TextView Title,Message,Time,UserName;
	private CircleImageView Head;
	private TextDrawable drawableBuilder;
	private RecyclerView RecyclerView;

	private int HeadColor;
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
	}

	private void initView(View v)
	{
		Time=(TextView)v.findViewById(R.id.list_time);
		Title=(TextView)v.findViewById(R.id.list_title);
		Message=(TextView)v.findViewById(R.id.list_message);
		UserName=(TextView)v.findViewById(R.id.list_user);
		Head=(CircleImageView)v.findViewById(R.id.list_head);
		
		Title.setText(i.getStringExtra("Title"));
		Message.setText(i.getStringExtra("Message"));
		UserName.setText(i.getStringExtra("UserName"));
		Time.setText(i.getStringExtra("Time"));
		
		/*if(myApplication.noEquals(user.getId(),"0")){
			if(myApplication.isFile("/cache/"+user.getEmail()+"_"+user.getHeadVersion()+".png")){
				Head.setBackgroundResource(0);
				Head.setImageBitmap(BitmapFactory.decodeFile(path+"/cache/"+user.getEmail()+"_"+user.getHeadVersion()+".png"));
			}else{
				//未缓存下载
				myApplication.downloadFile(new BmobFile(user.getEmail(),"",user.getHeadUri()),user.getEmail()+"_"+user.getHeadVersion(),Head);
			}
		}else{
			Head.setImageResource(0);
			Head.setBackground(drawableBuilder.builder().buildRound(UserName.getText().toString().subSequence(0,1).toString(),HeadColor-1000));
		}*/
	}
	
	
	private void findComment(String id){
		BmobQuery<Comment> query = new BmobQuery<Comment>();
		Data post = new Data();
		post.setObjectId(id);
		query.addWhereEqualTo("post",new BmobPointer(post));        
		query.include("user,post.Author");
		query.findObjects(new FindListener<Comment>() {
				@Override
				public void done(List<Comment> objects,BmobException e) {
					
				}
			});
	}
	
}
