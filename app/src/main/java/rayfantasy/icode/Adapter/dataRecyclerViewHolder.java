package rayfantasy.icode.Adapter;

import android.graphics.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.amulyakhare.textdrawable.*;
import java.util.*;
import rayfantasy.icode.*;
import rayfantasy.icode.Bmob.*;
import de.hdodenhof.circleimageview.*;
import rayfantasy.icode.R;
import android.support.v7.widget.RecyclerView.ViewHolder;
import cn.bmob.v3.*;

public class dataRecyclerViewHolder extends RecyclerView.Adapter<ViewHolder>
{
	private List<Data> dataList;
	private TextDrawable drawableBuilder;
	private static final int TYPE_ITEM = 0;
	private static final int TYPE_FOOTER = 1;
	
	public dataRecyclerViewHolder(List<Data> dataList){
		this.dataList=dataList;
	}
	
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		if (viewType == TYPE_ITEM) {
			View view = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.
				cardview_text, null);
			return new MyViewHolder(view);
		}else if(viewType == TYPE_FOOTER){
			View view = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.loadmore, null);
			return new FooterViewHolder(view);
		}
		return null;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int i)
	{
		if(holder instanceof MyViewHolder){
			MyViewHolder itemview=(MyViewHolder)holder;
			Data data = dataList.get(i);
			
			itemview.title.setText(data.getTitle());
			itemview.message.setText(data.getMessage());
			itemview.time.setText(data.getCreatedAt());
			itemview.user.setText(data.getUser());
			//根据用户名称的第一位字符设置头像
			itemview.userimage.setBackground(drawableBuilder.builder().buildRound(itemview.user.getText().toString().subSequence(0,1).toString(),
			getHeadColor(data.getHeadColor())));
																				  
		}
	}
	
	private int getHeadColor(String s){
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
		return dataList.size()+1;
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
