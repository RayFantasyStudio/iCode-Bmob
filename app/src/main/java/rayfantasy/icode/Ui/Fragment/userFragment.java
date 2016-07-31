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
import android.view.animation.*;
import android.content.*;
import android.net.*;
import android.database.*;
import android.provider.*;
import cn.bmob.v3.datatype.*;
import java.io.*;
import java.net.*;
import rayfantasy.icode.Ui.*;

public class userFragment extends Fragment
{
	private MyApplication myApplication;
	private MaterialEditText user_name,user_about;
	
	private TextDrawable drawableBuilder;
	private CircleImageView userImage;
	private int HeadColor,color;
	private String UserName,UserAbout;
	//头像选择路径
	private String PicturePath;
	private User user;
	
	private MenuItem item;
	private Intent i;
	
	private String path=Environment.getExternalStorageDirectory().getPath()+"/.iCode";
	
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
		
		userImage=(CircleImageView)v.findViewById(R.id.user_image);
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
		if(myApplication.noEquals(user.getId(),"0")){
			if(myApplication.isFile("/cache/"+user.getEmail()+"_"+user.getHeadVersion()+".png")){
				userImage.setBackgroundResource(0);
				userImage.setImageBitmap(BitmapFactory.decodeFile(path+"/cache/"+user.getEmail()+"_"+user.getHeadVersion()+".png"));
			}else{
				//未缓存下载
				myApplication.downloadFile(new BmobFile(user.getEmail(),"",user.getHeadUri()),user.getEmail()+"_"+user.getHeadVersion(),userImage);
			}
		}else{
			userImage.setImageResource(0);
			userImage.setBackground(drawableBuilder.builder().buildRound(UserName.subSequence(0,1).toString(),HeadColor-1000));
		}
		user_name.setText(UserName);
		user_about.setText(UserAbout);
		
		user_name.setTextColor(HeadColor);
		user_about.setTextColor(HeadColor);
		user_name.setPrimaryColor(HeadColor);
		user_about.setPrimaryColor(HeadColor);
		color=HeadColor;
		userImage.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					if(item!=null&&item.getTitle().equals("确定")){
						color=getUserRandomColor();
						
						userImage.setBackground(drawableBuilder.builder().buildRound(UserName.subSequence(0,1).toString(),color));
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
					return false;
				}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		 Uri selectedImage = null;
		if (requestCode == IMAGE_REQUEST_CODE && resultCode == getActivity().RESULT_OK && null != data) {
			selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = getActivity().getContentResolver().query(selectedImage,
																	 filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			PicturePath = cursor.getString(columnIndex);
			cursor.close();
			if(myApplication.noEquals(user.getId(),"")){
				myApplication.deleteHead(user.getId());
			}
			myApplication.uploadHead(user.getEmail(),PicturePath,user.getHeadVersion().intValue()+1);
			myApplication.showToast("正在上传头像，请稍等...");
			userImage.setBackgroundResource(0);
			userImage.setImageBitmap(BitmapFactory.decodeFile(PicturePath));
		}		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	public void resizeImage() {
		Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, IMAGE_REQUEST_CODE);
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

	private void updata(String UserName,String About,String color){
		User newUser = new User();
		if(myApplication.noEquals(this.UserName,UserName)){
			newUser.setUsername(UserName);
		}
		newUser.setHeadColor(color);
		newUser.setAbout(About);
		myApplication.findData(user.getEmail(),UserName,null,0);
		User bmobuser=BmobUser.getCurrentUser(User.class);
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
