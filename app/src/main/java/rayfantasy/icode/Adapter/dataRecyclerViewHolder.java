package rayfantasy.icode.Adapter;

import android.graphics.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.amulyakhare.textdrawable.*;
import java.util.*;
import rayfantasy.icode.*;
import rayfantasy.icode.Bmob.*;

public class dataRecyclerViewHolder extends RecyclerView.Adapter<dataRecyclerViewHolder.MyViewHolder>
{
	private List<Data> dataList;
	private TextDrawable.IBuilder drawableBuilder;
	
	
	public dataRecyclerViewHolder(List<Data> dataList){
		this.dataList=dataList;
	}
	
	@Override
	public dataRecyclerViewHolder.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
	{
		View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_text, viewGroup, false);
        return new MyViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(dataRecyclerViewHolder.MyViewHolder holder, int i)
	{
		Data data = dataList.get(i);
      
		holder.title.setText(data.getTitle());
		holder.bg.setElevation(5);
		holder.message.setText(data.getMessage());
		holder.time.setText(data.getCreatedAt());
		holder.user.setText(data.getUser());
		
		//设置用户头像
		drawableBuilder=TextDrawable.builder().round();
		//根据用户名称的第一位字符设置头像
		holder.userimage.setImageDrawable(drawableBuilder.build(holder.user.getText().toString().subSequence(0,1).toString(),Color.rgb(0,84,255)));
	}

	@Override
	public int getItemCount()
	{
		return dataList.size();
	}
	
	
	public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, message, time, user;
        public CardView bg;
		public ImageView userimage;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.list_title);
            message = (TextView) itemView.findViewById(R.id.list_message);
            time = (TextView) itemView.findViewById(R.id.list_time);
			user = (TextView) itemView.findViewById(R.id.list_user);
			userimage=(ImageView)itemView.findViewById(R.id.cardviewtextImageView1);
			
            bg = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}
