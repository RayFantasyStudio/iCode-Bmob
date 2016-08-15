package rayfantasy.icode.Ui.Fragment;

import android.content.*;
import android.graphics.*;
import android.os.*;
import android.support.v4.app.*;
import android.view.*;
import android.widget.*;
import cn.bmob.v3.*;
import cn.bmob.v3.datatype.*;
import cn.bmob.v3.exception.*;
import cn.bmob.v3.listener.*;
import com.amulyakhare.textdrawable.*;
import de.hdodenhof.circleimageview.*;
import java.io.*;
import rayfantasy.icode.*;
import rayfantasy.icode.Bmob.*;
import rayfantasy.icode.Ui.View.*;

import rayfantasy.icode.R;
import com.melnykov.fab.*;
import android.view.View.*;
import rayfantasy.icode.Util.*;
import android.util.*;
import rayfantasy.icode.Ui.Activity.*;
import rayfantasy.icode.Data.*;

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
	private FloatingActionButton fab_code;
	
	private int HeadColor,HeadVersion;
	private String id,Email,HeadUri,STitle,SMessage,STime,SUserName;
	private String pathfj=Environment.getExternalStorageDirectory().getPath();
	private BmobFile file;
	
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

	private void initView(final View v)
	{
		Time=(TextView)v.findViewById(R.id.list_time);
		Title=(TextView)v.findViewById(R.id.list_title);
		Message=(TextEditorView)v.findViewById(R.id.list_message);
		UserName=(TextView)v.findViewById(R.id.list_user);
		Head=(CircleImageView)v.findViewById(R.id.list_head);
		fab_code=(FloatingActionButton)v.findViewById(R.id.fab_code);
		
		fab_code.setColorNormal(HeadColor);
		fab_code.setColorPressed(HeadColor);
		fab_code.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					Intent i=new Intent(getActivity(),writeActivity.class);
					i.putExtra("Title",STitle);
					i.putExtra("Message",SMessage);
					i.putExtra("id",id);
					TransitionUtil.startActivity(getActivity(), i, Pair.create(	v, "element_bg"));
					getActivity().finish();
				};
			
		});
		Title.setText(STitle);
		Message.setText(SMessage);
		UserName.setText(SUserName);
		Time.setText(STime);
		//用户是否登录
		if(user!=null){
			Title.setTextColor(getTextColor(user.getHeadColor()));
			if(i.getIntExtra("HeadVersion",0)==0){
				//当用户从未上传头像时，设置默认头像
				Head.setImageResource(0);
				Head.setBackgroundDrawable(drawableBuilder.builder().buildRound(UserName.getText().toString().subSequence(0,1).toString(),
																					  getTextColor(user.getHeadColor())));
			}else if(myApplication.isFile("/cache/"+Email+"_"+HeadVersion+".png")){
				Head.setBackgroundResource(0);
				Head.setImageBitmap(BitmapFactory.decodeFile(Utils.getiCodePath()+"/cache/"+Email+"_"+HeadVersion+".png"));
			}else{
				myApplication.downloadFile(new BmobFile(Email,"",HeadUri),Email+"_"+HeadVersion,Head);
			}
		}else{
			//处理用户未登录
			Title.setTextColor(R.color.PrimaryColor);
			Head.setImageResource(0);
			Head.setBackgroundDrawable(drawableBuilder.builder().buildRound(UserName.getText().toString().subSequence(0,1).toString(),
											R.color.PrimaryColor));
		}
		findfj(id);
		if(user!=null){
			if(Email.equals(user.getEmail())){
				fab_code.setVisibility(View.VISIBLE);
			}else{
				fab_code.setVisibility(View.GONE);
			}
		}else{
			fab_code.setVisibility(View.GONE);
		}
		
	}
	
	private void findfj(String id){
		BmobQuery<Data> bmobQuery = new BmobQuery<Data>();
		bmobQuery.getObject(id, new QueryListener<Data>() {
				@Override
				public void done(Data p1, BmobException p2)
				{
					if(p2==null){
						if(p1.getFj().getUrl()!=null){
							file=p1.getFj();
							setHasOptionsMenu(true);
						}
					}
				}
		});
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		getActivity().getMenuInflater().inflate(R.menu.code_menu,menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id=item.getItemId();
		switch(id){
			case R.id.menu_downloadfj:
				downloadFile(file);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void downloadFile(BmobFile file){
		//允许设置下载文件的存储路径，默认下载文件的目录为：context.getApplicationContext().getCacheDir()+"/bmob/"
		File saveFile = new File(pathfj+"/"+file.getFilename());
		file.download(saveFile, new DownloadFileListener() {
				@Override
				public void onStart() {
					//toast("开始下载...");
				}
				@Override
				public void done(String savePath,BmobException e) {
					if(e==null){
						myApplication.showToast("附件下载成功保存路径"+savePath);
					}else{
						myApplication.showToast("下载失败："+e.getErrorCode()+","+e.getMessage());
					}
				}
				//下载进度
				@Override
				public void onProgress(Integer value, long newworkSpeed) {
				}

			});
	}
	
	//String转int
	public int getTextColor(String s){
		return Integer.valueOf(s).intValue();
	}
	
}
