package rayfantasy.icode.Ui.Fragment;

import android.content.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import cn.bmob.v3.*;
import cn.bmob.v3.exception.*;
import cn.bmob.v3.listener.*;
import com.melnykov.fab.*;
import com.rengwuxian.materialedittext.*;
import rayfantasy.icode.*;
import rayfantasy.icode.Bmob.*;
import rayfantasy.icode.Ui.View.*;

import rayfantasy.icode.R;
import cn.bmob.v3.datatype.*;
import java.io.*;
import android.support.v4.app.*;

public class writeFragment extends Fragment
{
	private MyApplication myApplication;
	private Intent i;
	private int HeadColor;
	private String sTitle,sMessage,id;
	
	private View v;
	private FloatingActionButton fab_write;
	private MaterialEditText mMaterialEditText;
	private TextEditorView mTextEditirView;
	private TextView tv1;
	
	private User user;
	
	private String filepath;
	private static final int File_REQUEST = 1;
	private boolean isIntent=false;
	
	private dialogProgressFragment dialog_progress;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		v=inflater.inflate(R.layout.fragment_write,container,false);
		init();
		initView(v);
		return v;
	}

	private void init()
	{
		i=getActivity().getIntent();
		myApplication=(MyApplication)getActivity().getApplication();
		user=BmobUser.getCurrentUser(User.class);
		HeadColor=myApplication.getHeadColor(user.getHeadColor());
		sTitle=i.getStringExtra("Title");
		sMessage=i.getStringExtra("Message");
		id=i.getStringExtra("id");
		setHasOptionsMenu(true);
	}

	private void initView(View v)
	{
		fab_write=(FloatingActionButton)v.findViewById(R.id.fab_write);
		mMaterialEditText=(MaterialEditText)v.findViewById(R.id.fragmentwriteMaterialEditText1);
		mTextEditirView=(TextEditorView)v.findViewById(R.id.fragmentwriteTextEditorView1);
		tv1=(TextView)v.findViewById(R.id.fragmentwriteTextView1);
		
		dialog_progress=new dialogProgressFragment();
		
		fab_write.setColorNormal(HeadColor);
		fab_write.setColorPressed(HeadColor);
		mMaterialEditText.setPrimaryColor(HeadColor);
		mMaterialEditText.setTextColor(HeadColor);
		
		if(sTitle!=null&&sMessage!=null&&sTitle.length()!=0&&sMessage.length()!=0){
			mMaterialEditText.setText(sTitle);
			mTextEditirView.setText(sMessage);
			isIntent=true;
		}
		
		fab_write.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					int title=mMaterialEditText.getText().toString().length();
					if(title>0&&title<100&&mTextEditirView.getText().toString().length()>0){
						dialog_progress.show(getFragmentManager(),"");
						if(filepath!=null&&filepath.length()!=0){
							uploadFile(filepath.toString());
						}else{
							uploadFile(null);
						}
					}else{
						myApplication.showSnackBar(getActivity(),"无法提交，标题太长:"+title+"字/(100字以内)\n或标题/内容为空！");
					}
				}
			
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == File_REQUEST && resultCode == getActivity().RESULT_OK && null != data) {
			filepath = data.getData().toString().subSequence(7,data.getData().toString().length()).toString();
			tv1.setText("已选择附件:"+filepath);
		}		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		getActivity().getMenuInflater().inflate(R.menu.write_menu,menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id=item.getItemId();
		switch(id){
			case R.id.write_fj:
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("zip/*");
				intent.putExtra("return-data", true);
				startActivityForResult(intent, File_REQUEST);
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void uploadFile(String File){
		if(File==null){
			if(isIntent){
				updata(id,mMaterialEditText.getText().toString(),mTextEditirView.getText().toString());
			}else{
				saveData(null,mMaterialEditText.getText().toString(),mTextEditirView.getText().toString());
			}
		}else{
			final BmobFile bmobFile = new BmobFile(new File(File));
			bmobFile.upload(new UploadFileListener() {
					@Override
					public void done(BmobException e)
					{
						if(e==null){
							saveData(bmobFile,mMaterialEditText.getText().toString(),mTextEditirView.getText().toString());
						}else{
							myApplication.showToast("附件上传失败：" + e.getErrorCode()+",msg = "+e.getMessage());
						}
					}
				});
		}
	}
	
	
	//上传
	public void saveData(BmobFile fj,String Title,String Message){
		Data data = new Data();
		data.setAuthor(user);
		data.setCommentSize(0);
		data.setTitle(Title);
		if(fj!=null){
			data.setFj(fj);
		}
		data.setMessage(Message);
		data.save(new SaveListener<String>() {
				@Override
				public void done(String objectId, BmobException e) {
					if(e==null){
						myApplication.showToast("上传代码成功");
						dialog_progress.dismiss();
						getActivity().finish();
					}else{
						myApplication.showToast("上传失败："+e.getMessage()+","+e.getErrorCode());
						dialog_progress.dismiss();
					}
				}
			});
	}
	
	
	private void updata(String Id,String Title,String Message){
		Data newUser = new Data();
		newUser.setTitle(Title);
		newUser.setMessage(Message);
		newUser.update(Id, new UpdateListener() {
				@Override
				public void done(BmobException e) {
					if(e==null){
						myApplication.showToast("更新帖子成功");
						dialog_progress.dismiss();
						getActivity().finish();
					}else{
						myApplication.showToast("错误码："+e.getErrorCode()+",错误原因："+e.getMessage());	
					}
				}
			});
	}
	
}
