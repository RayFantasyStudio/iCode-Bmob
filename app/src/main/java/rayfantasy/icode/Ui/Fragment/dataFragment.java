package rayfantasy.icode.Ui.Fragment;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

import rayfantasy.icode.*;
import rayfantasy.icode.Bmob.*;
import rayfantasy.icode.Adapter.*;

import android.support.v4.widget.*;

import java.util.*;
import cn.bmob.v3.*;
import cn.bmob.v3.listener.*;
import cn.bmob.v3.exception.*;

import android.support.v7.widget.*;
import android.support.design.widget.*;

public class dataFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{	
	private RecyclerView recyclerView;
	private dataRecyclerViewHolder dataRecyclerViewHolder;
	
	private LinearLayoutManager layoutManager;
	private View v;
	private List<Data> mList=new ArrayList<Data>();
	
	private SwipeRefreshLayout swipeRefreshLayout;
	
	private int data_skip=0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		v=inflater.inflate(R.layout.fragment_data,container,false);
		initView(v);
		initData(0);
		return v;
	}
	
	private void LodgingData(){
		
	}

	private void initData(int skip)
	{
		// 刷新数据
		final BmobQuery<Data> query = new BmobQuery<Data>();
		query.order("-createdAt");
		query.setLimit(10);
		//忽略前面数据
		query.setSkip(skip*10);
		if(skip==0){
			mList.clear();
		}
		query.findObjects(getActivity(), new FindListener<Data>(){

				@Override
				public void onSuccess(List<Data> losts)
				{
					if (losts == null || losts.size() == 0) {
						return;
					}
					mList.addAll(0, losts);
					dataRecyclerViewHolder.notifyDataSetChanged();
					swipeRefreshLayout.setRefreshing(false);
					
				}

				@Override
				public void onError(int p1, String p2)
				{
					
				}
				
			
		}); 
	}

	private void initView(View v)
	{
		
		swipeRefreshLayout=(SwipeRefreshLayout)v.findViewById(R.id.swipe_refreshlayout);
		swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_light,
								   android.R.color.holo_green_light,
								   android.R.color.holo_orange_light, android.R.color.holo_red_light);
		swipeRefreshLayout.setOnRefreshListener(this);
		recyclerView=(RecyclerView)v.findViewById(R.id.recycler_view);
		layoutManager = new LinearLayoutManager(getActivity());
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

		dataRecyclerViewHolder=new dataRecyclerViewHolder(mList);
		recyclerView.setAdapter(dataRecyclerViewHolder);
		
	}

	@Override
	public void onRefresh()
	{
		initData(0);
	}

	
}
