package rayfantasy.icode.Adapter;

import android.support.v7.widget.RecyclerView;

import android.widget.*;
import android.support.v7.widget.*;
import android.view.*;
import rayfantasy.icode.*;
import java.util.*;
import rayfantasy.icode.Bmob.*;
import android.app.*;

public class dataRecyclerViewHolder extends RecyclerView.Adapter<dataRecyclerViewHolder.MyViewHolder>
{
	private List<Data> dataList;
	
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
	}

	@Override
	public int getItemCount()
	{
		return dataList.size();
	}
	
	
	public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, message, time, user;
        public CardView bg;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.list_title);
            message = (TextView) itemView.findViewById(R.id.list_message);
            time = (TextView) itemView.findViewById(R.id.list_time);
			user = (TextView) itemView.findViewById(R.id.list_user);
            bg = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}
