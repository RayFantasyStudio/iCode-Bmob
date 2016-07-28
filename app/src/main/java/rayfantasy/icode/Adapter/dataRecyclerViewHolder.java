package rayfantasy.icode.Adapter;

import android.content.*;
import android.graphics.*;
import android.os.*;
import android.preference.*;
import android.support.v7.widget.*;
import android.support.v7.widget.RecyclerView.*;
import android.view.*;
import android.widget.*;
import android.widget.RelativeLayout.*;
import cn.bmob.v3.*;
import cn.bmob.v3.datatype.*;
import cn.bmob.v3.exception.*;
import cn.bmob.v3.listener.*;
import com.amulyakhare.textdrawable.*;
import de.hdodenhof.circleimageview.*;
import java.io.*;
import java.util.*;
import rayfantasy.icode.*;
import rayfantasy.icode.Bmob.*;

import android.widget.RelativeLayout.LayoutParams;
import rayfantasy.icode.R;

public class dataRecyclerViewHolder<T extends java.lang.Object> extends RecyclerView.Adapter<ViewHolder>
{
	private List<Data> dataList;
	private List<Material> dataMaterialList;
	
	private TextDrawable drawableBuilder;
	private static final int TYPE_ITEM = 0;
	private static final int TYPE_FOOTER = 1;
	private int t;
	private MyApplication myApplication;
	private String path=Environment.getExternalStorageDirectory().getPath()+"/.iCode";
	
	
	public dataRecyclerViewHolder(List<T> dataList,int i,MyApplication myApplication){
		t=i;
		this.myApplication=myApplication;
		switch(i){
			case 1:
				this.dataList=(List<Data>)dataList;
			break;
			case 2:
				this.dataMaterialList=(List<Material>)dataList;
			break;
		}
	}
	
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		if (viewType == TYPE_ITEM) {
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.
				cardview_text, null);
			view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
												  LayoutParams.WRAP_CONTENT));
			return new MyViewHolder(view);
		}else if(viewType == TYPE_FOOTER){
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loadmore, null);
			view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
												  LayoutParams.WRAP_CONTENT));
			return new FooterViewHolder(view);
		}
		return null;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int i)
	{
		if(holder instanceof MyViewHolder){
			MyViewHolder itemview=(MyViewHolder)holder;
			User u=BmobUser.getCurrentUser(User.class);
			Data data = null;
			switch(t){
				case 1:
					data=dataList.get(i);
					itemview.title.setText(data.getTitle());
					itemview.message.setText(data.getMessage());
					itemview.time.setText(data.getCreatedAt());
					itemview.user.setText(data.getUser());
				break;
				case 2:
					Material data2=dataMaterialList.get(i);
					itemview.title.setText(data2.getTitle());
					itemview.message.setText(data2.getMessage());
					itemview.time.setText(data2.getCreatedAt());
					itemview.user.setText(data2.getUser());
				break;
			}
			//用户是否登录
			if(u!=null){
				itemview.title.setTextColor(getTextColor(u.getHeadColor()));
				if(data.getHeadVersion().intValue()==0){
					//当用户从未上传头像时，设置默认头像
					itemview.userimage.setImageResource(0);
					itemview.userimage.setBackground(drawableBuilder.builder().buildRound(itemview.user.getText().toString().subSequence(0,1).toString(),
													  getHeadColor(u.getHeadColor())));
				}else if(myApplication.isFile("/cache/"+data.getEmail()+"_"+data.getHeadVersion()+".png")){
					itemview.userimage.setBackgroundResource(0);
					itemview.userimage.setImageBitmap(BitmapFactory.decodeFile(path+"/cache/"+data.getEmail()+"_"+data.getHeadVersion().intValue()+".png"));
				}else{
					myApplication.downloadFile(new BmobFile(data.getEmail(),"",data.getHeadUri()),data.getEmail()+"_"+data.getHeadVersion().intValue(),itemview.userimage);
				}
			}else{
				//处理用户未登录
				itemview.title.setTextColor(R.color.PrimaryColor);
				itemview.userimage.setImageResource(0);
				itemview.userimage.setBackground(drawableBuilder.builder().buildRound(itemview.user.getText().toString().subSequence(0,1).toString(),
												  R.color.PrimaryColor));
				}
				
			}
	}

	private int getHeadColor(String headColor)
	{
		return Integer.valueOf(headColor).intValue();
	}
		
	//String转int
	public int getTextColor(String s){
		return Integer.valueOf(s).intValue();
	}

	@Override
	public int getItemViewType(int position)
	{
		// 最后一个item设置为footerView
		if (position + 1 == getItemCount()) {
			return TYPE_FOOTER;
		} else {
			return TYPE_ITEM;
		}
	}

	@Override
	public int getItemCount()
	{
		if(t==1){
			return /*dataList.size() == 0 ? 0 : */dataList.size() + 1;
		}else if(t==2){
			return /*dataList.size() == 0 ? 0 : */dataMaterialList.size() + 1;
		}
		return 0;
	}
	
	//比较两个String不相等返回true
	public boolean noEquals(String a,String b){
		if(a.equals(b)){
			return false;
		}else{
			return true;
		}
	}
	
	class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, message, time, user;
        public CardView bg;
		public CircleImageView userimage;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.list_title);
            message = (TextView) itemView.findViewById(R.id.list_message);
            time = (TextView) itemView.findViewById(R.id.list_time);
			user = (TextView) itemView.findViewById(R.id.list_user);
			userimage=(CircleImageView)itemView.findViewById(R.id.cardviewtextImageView1);
			
            bg = (CardView) itemView.findViewById(R.id.element_bg);
        }
    }
	
	class FooterViewHolder extends ViewHolder {

		public FooterViewHolder(View view) {
			super(view);
		}

	}
}
