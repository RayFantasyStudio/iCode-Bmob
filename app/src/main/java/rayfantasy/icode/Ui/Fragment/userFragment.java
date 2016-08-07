package rayfantasy.icode.Ui.Fragment;

import android.view.View.*;
import android.view.*;
import android.os.*;
import android.widget.*;
import android.graphics.*;

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
import android.view.animation.*;
import android.content.*;
import android.net.*;
import android.database.*;
import android.provider.*;
import cn.bmob.v3.datatype.*;
import java.io.*;
import java.net.*;
import rayfantasy.icode.Ui.*;
import android.support.v4.app.Fragment;
import android.app.*;
import at.markushi.ui.*;
import com.soundcloud.android.crop.*;

public class userFragment extends Fragment
{
	private MyApplication myApplication;
	private MaterialEditText user_name,user_about;
	
	private TextDrawable drawableBuilder;
	private CircleImageView userImage;
	private TextView tv1;
	private CircleButton updateHead;
	
	private int HeadColor,color;
	private String UserName,UserAbout;
	//头像选择路径
	private String PicturePath;
	private User user;
	
	private MenuItem item;
	private Intent i;
	
	private dialogProgressFragment dialog_progress;
	private static final int IMAGE_REQUEST_CODE = 0;
	
	public interface OnFabClickListener{
		public void OnClick(View v1,int color)
	}
	private OnFabClickListener mOnFabClickListener;
	

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		try {
            mOnFabClickListener = (OnFabClickListener) activity;
		} catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() );
        }
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_user,container,false);
		i=getActivity().getIntent();
		myApplication=(MyApplication)getActivity().getApplication();
		initView(v);
		init();
		return v;
	}

	private void initView(View v)
	{
		user_name=(MaterialEditText)v.findViewById(R.id.user_name);
		user_about=(MaterialEditText)v.findViewById(R.id.user_about);
		tv1=(TextView)v.findViewById(R.id.fragmentuserTextView1);
		updateHead=(CircleButton)v.findViewById(R.id.update_head);
		
		userImage=(CircleImageView)v.findViewById(R.id.user_image);
		
		dialog_progress=new dialogProgressFragment();
		user_name.setTextSize(25);
		user_about.setTextSize(15);
		setMetVisibility(true);
		setHasOptionsMenu(true);
	}

	private void init()
	{
		user=BmobUser.getCurrentUser(User.class);
		HeadColor=i.getIntExtra("HeadColor",getResources().getColor(R.color.PrimaryColor));
		UserAbout=i.getStringExtra("About");
		UserName=i.getStringExtra("UserName");
		myApplication.setHead(userImage);
		user_name.setText(UserName);
		user_about.setText(UserAbout);
		
		tv1.setTextColor(HeadColor);
		updateHead.setColor(HeadColor);
		user_name.setTextColor(HeadColor);
		user_about.setTextColor(HeadColor);
		user_name.setPrimaryColor(HeadColor);
		user_about.setPrimaryColor(HeadColor);
		color=HeadColor;
		updateHead.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					uploadImage(PicturePath,user.getHeadVersion().intValue()+1);
					dialog_progress.show(getFragmentManager(),"dialogProgressFragment");
				}
		});
		
		updateHead.setOnLongClickListener(new OnLongClickListener(){
				@Override
				public boolean onLongClick(View p1)
				{
					Snackbar.make(p1,"上传头像",1000).show();
					return true;
				}
		});
		userImage.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					if(item!=null&&item.getTitle().equals("确定")){
						color=getUserRandomColor();
						userImage.setBackgroundDrawable(drawableBuilder.builder().buildRound(UserName.subSequence(0,1).toString(),color));
						user_name.setTextColor(color);
						user_about.setTextColor(color);
						user_name.setPrimaryColor(color);
						user_about.setPrimaryColor(color);
						if(mOnFabClickListener!=null){
							mOnFabClickListener.OnClick(p1,color);
						}
					}
				}
		});
		
		userImage.setOnLongClickListener(new OnLongClickListener(){
				@Override
				public boolean onLongClick(View p1)
				{
					resizeImage();
					return true;
				}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == IMAGE_REQUEST_CODE && resultCode == getActivity().RESULT_OK && null != data) {
			PicturePath = data.getData().toString().subSequence(7,data.getData().toString().length()).toString();
			tv1.setText("已选择头像:"+PicturePath);
			updateHead.setVisibility(View.VISIBLE);
			userImage.setBackgroundResource(0);
			userImage.setImageBitmap(BitmapFactory.decodeFile(PicturePath));
		}
	}

	
	public void resizeImage() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		intent.putExtra("return-data", true);
		startActivityForResult(intent, IMAGE_REQUEST_CODE);
	}

	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		getActivity().getMenuInflater().inflate(R.menu.user_menu,menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id=item.getItemId();
		this.item=item;
		switch(id){
			case R.id.finish:
				finishLogin();
			break;
			case R.id.update:
				if(item.getTitle().toString().equals("修改")){
					setMetVisibility(false);
					item.setTitle("确定");
				}else{
					if(myApplication.isCharacter(user_name.getText().toString().length(),user_about.getText().toString().length(),5,18,0,50)){
						updata(user_name.getText().toString(),user_about.getText().toString(),color+"");
					}else{
						myApplication.showSnackBar(getActivity(),"其中一项不符合规则");
					}
				}
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void setMetVisibility(boolean v){
		if(v){
			user_name.setFocusable(false);
			user_name.setFocusableInTouchMode(false);
			user_name.setHideUnderline(true);
			user_about.setFocusable(false);
			user_about.setHideUnderline(true);
			user_about.setFocusableInTouchMode(false);
		}else{
			user_name.setFocusable(true);
			user_name.setFocusableInTouchMode(true);
			user_name.setHideUnderline(false);
			user_about.setFocusable(true);
			user_about.setHideUnderline(false);
			user_about.setFocusableInTouchMode(true);
		}

	}
	
	//上传图片
	public void uploadImage(String File,final int HeadVersion){
		final BmobFile bmobFile = new BmobFile(new File(File));
		bmobFile.upload(new UploadFileListener() {
				@Override
				public void done(BmobException e)
				{
					if(e==null){
						upUser(HeadVersion,bmobFile.getUrl(),bmobFile);
					}else{
						myApplication.showToast("头像上传失败：" + e.getErrorCode()+",msg = "+e.getMessage());
					}
				}
		});
	}

	public void upUser(int HeadVersion,String HeadUri,BmobFile Head){
		User newUser = new User();
		newUser.setHeadVersion(HeadVersion);
		newUser.setHeadUri(HeadUri);
		newUser.setHead(Head);
		User bmobuser=BmobUser.getCurrentUser(User.class);
		newUser.update(bmobuser.getObjectId(), new UpdateListener() {
				@Override
				public void done(BmobException e){
					if(e==null){
						myApplication.showToast("头像上传成功");
						dialog_progress.dismiss();
						updateHead.setVisibility(View.GONE);
					}else{
						myApplication.showToast("头像上传失败：" + e.getErrorCode()+",msg = "+e.getMessage());
						dialog_progress.dismiss();
					}
				}
			});
	}
	
	private void updata(String UserName,String About,String color){
		User bmobuser=BmobUser.getCurrentUser(User.class);
		User newUser = new User();
		if(myApplication.noEquals(this.UserName,UserName)){
			newUser.setUsername(UserName);
		}
		newUser.setHeadColor(color);
		newUser.setAbout(About);
		newUser.update(bmobuser.getObjectId(), new UpdateListener() {
				@Override
				public void done(BmobException e) {
					if(e==null){
						item.setTitle("修改");
						setMetVisibility(true);
						myApplication.showToast("更新用户信息成功");
					}else{
						myApplication.showToast("错误码："+e.getErrorCode()+",错误原因："+e.getMessage());	
					}
				}
			});
	}
	
	public int getUserRandomColor(){
		return Color.rgb((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
	}
	
	//退出登录
	private void finishLogin(){
		BmobUser.logOut();
		//清除缓存用户对象
		BmobUser currentUser = BmobUser.getCurrentUser();
		// 现在的currentUser是null了
		getActivity().finish();
		myApplication.editBoolean(user.getUsername()+"_isLoading",false);
	}
	
	
}
