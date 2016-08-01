package rayfantasy.icode.Ui.Fragment;

import android.app.*;
import android.os.*;
import android.view.*;
import rayfantasy.icode.R;
import com.melnykov.fab.*;
import rayfantasy.icode.Ui.View.*;
import rayfantasy.icode.*;
import android.view.View.*;
import android.content.*;
import com.rengwuxian.materialedittext.*;
import rayfantasy.icode.Bmob.*;
import cn.bmob.v3.listener.*;
import cn.bmob.v3.*;
import cn.bmob.v3.exception.*;

public class writeFragment extends Fragment
{
	private MyApplication myApplication;
	private Intent i;
	private int HeadColor;
	
	private View v;
	private FloatingActionButton fab_write;
	private MaterialEditText mMaterialEditText;
	private TextEditorView mTextEditirView;
	
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
		HeadColor=i.getIntExtra("HeadColor",getResources().getColor(R.color.PrimaryColor));
	}

	private void initView(View v)
	{
		fab_write=(FloatingActionButton)v.findViewById(R.id.fab_write);
		mMaterialEditText=(MaterialEditText)v.findViewById(R.id.fragmentwriteMaterialEditText1);
		mTextEditirView=(TextEditorView)v.findViewById(R.id.fragmentwriteTextEditorView1);
		
		fab_write.setColorNormal(HeadColor);
		fab_write.setColorPressed(HeadColor);
		mMaterialEditText.setPrimaryColor(HeadColor);
		mMaterialEditText.setTextColor(HeadColor);
		
		fab_write.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					int title=mMaterialEditText.getText().toString().length();
					if(title>0&&title<100&&mTextEditirView.getText().toString().length()>0){
						saveData("Data",mMaterialEditText.getText().toString(),mTextEditirView.getText().toString());
					}else{
						myApplication.showSnackBar(getActivity(),"无法提交，标题太长:"+title+"字/(100字以内)\n或标题/内容为空！");
					}
				}
			
		});
	}

	//上传
	public void saveData(String Data,String Title,String Message){
		Data data = new Data();
		User user=BmobUser.getCurrentUser(User.class);
		data.setAuthor(user);
		data.setCommentSize(0);
		data.setTitle(Title);
		data.setMessage(Message);
		data.save(new SaveListener<String>() {
				@Override
				public void done(String objectId, BmobException e) {
					if(e==null){
						getActivity().finish();
						myApplication.showToast("上传代码成功");
					}else{
						myApplication.showToast("上传失败："+e.getMessage()+","+e.getErrorCode());
					}
				}
			});
	}
	
	
}
