package rayfantasy.icode.Adapter;

import android.support.v7.widget.RecyclerView.*;
import android.view.*;
import android.widget.*;
import rayfantasy.icode.R;
import android.support.v7.widget.*;
import rayfantasy.icode.Bmob.*;
import cn.bmob.v3.*;
import com.amulyakhare.textdrawable.*;
import android.graphics.*;
import rayfantasy.icode.*;
import android.os.*;
import cn.bmob.v3.datatype.*;
import de.hdodenhof.circleimageview.*;
import java.util.*;


public class commentHolder extends RecyclerView.Adapter<ViewHolder>
{
	private List<Comment> mListComment;
	private Comment mComment;
	
	private TextDrawable drawableBuilder;
	private MyApplication myApplication;
	private String path=Environment.getExternalStorageDirectory().getPath()+"/.iCode";
	
	public commentHolder(List<Comment> mListComment,MyApplication myApplication){
		this.mListComment=mListComment;
		this.myApplication=myApplication;
	}
	
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
	{
		return new MyViewHolder(LayoutInflater.from(p1.getContext()).inflate(R.layout.cardview_comment,null));
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int p2)
	{
		
		if(holder instanceof MyViewHolder){
			MyViewHolder itemview=(MyViewHolder)holder;
			User u=BmobUser.getCurrentUser(User.class);
			mComment=mListComment.get(p2);
			itemview.User.setText(mComment.getUser().getUsername());
			itemview.Time.setText("第"+(p2+1)+"楼"+"  "+mComment.getCreatedAt());
			itemview.Content.setText(mComment.getContent());
			itemview.mCardView.setElevation(3);
			if(u!=null){
				itemview.Content.setTextColor(getTextColor(u.getHeadColor()));
				if(mComment.getUser().getHeadVersion().intValue()==0){
					//当用户从未上传头像时，设置默认头像
					itemview.Head.setImageResource(0);
					itemview.Head.setBackground(drawableBuilder.builder().buildRound(itemview.User.getText().toString().subSequence(0,1).toString(),
																						  getTextColor(u.getHeadColor())));
				}else if(myApplication.isFile("/cache/"+mComment.getUser().getEmail()+"_"+mComment.getUser().getHeadVersion()+".png")){
					itemview.Head.setBackgroundResource(0);
					itemview.Head.setImageBitmap(BitmapFactory.decodeFile(path+"/cache/"+mComment.getUser().getEmail()+"_"+mComment.getUser().getHeadVersion().intValue()+".png"));
				}else{
					myApplication.downloadFile(new BmobFile(mComment.getUser().getEmail(),"",mComment.getUser().getHeadUri()),mComment.getUser().getEmail()+"_"+mComment.getUser().getHeadVersion().intValue(),itemview.Head);
				}
			}else{
				//处理用户未登录
				itemview.Content.setTextColor(R.color.PrimaryColor);
				itemview.Head.setImageResource(0);
				itemview.Head.setBackground(drawableBuilder.builder().buildRound(itemview.User.getText().toString().subSequence(0,1).toString(),
																					  R.color.PrimaryColor));
			}
		}
		
	}
	

	//String转int
	public int getTextColor(String s){
		return Integer.valueOf(s).intValue();
	}
	
	@Override
	public int getItemCount()
	{
		return mListComment.size()==0 ? 0 : mListComment.size();
	}
	
	class MyViewHolder extends RecyclerView.ViewHolder{
		public TextView User,Time,Content;
		public CircleImageView Head;
		public CardView mCardView;
		
		public MyViewHolder(View v){
			super(v);
			
			User=(TextView)v.findViewById(R.id.cardviewcommentTextView1);
			Time=(TextView)v.findViewById(R.id.cardviewcommentTextView2);
			Content=(TextView)v.findViewById(R.id.cardviewcommentTextView3);
			
			Head=(CircleImageView)v.findViewById(R.id.cardviewcommentCircleImageView1);
			mCardView=(CardView)v.findViewById(R.id.cardviewcommentCardView1);
		}
	}
	
}
