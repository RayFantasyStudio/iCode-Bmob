package rayfantasy.icode.Ui.Fragment;

import android.app.*;
import android.content.*;
import android.os.*;
import android.support.v4.widget.*;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.view.View.*;
import cn.bmob.v3.*;
import cn.bmob.v3.listener.*;
import com.melnykov.fab.*;
import java.util.*;
import rayfantasy.icode.*;
import rayfantasy.icode.Adapter.*;
import rayfantasy.icode.Bmob.*;
import rayfantasy.icode.Ui.Activity.*;

import rayfantasy.icode.R;
import android.graphics.*;
import android.animation.*;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.*;
import android.view.animation.*;
import android.util.*;
import cn.bmob.v3.exception.*;


public class dataFragment extends Fragment implements OnClickListener,SwipeRefreshLayout.OnRefreshListener
{	
	private SwipeRefreshLayout mSwipeRefreshLayout;
	
	private RecyclerView recyclerView;
	private dataRecyclerViewHolder dataRecyclerViewHolder;
	private LinearLayoutManager mLinearLayoutManager;
	
	private View v;
	private List<Data> mList=new ArrayList<Data>();
	
	private FloatingActionButton mFloatingActionButton;
	
	private MyApplication myApplication;
	private int data_skip=0;
	private int lastVisibleItem=0;
	private User user;
	private int HeadColor;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		v=inflater.inflate(R.layout.fragment_data,container,false);
		initView(v);
		return v;
	}
	
	private void initView(View v)
	{
		myApplication=(MyApplication)getActivity().getApplication();
		mSwipeRefreshLayout=(SwipeRefreshLayout)v.findViewById(R.id.swipe_layout);
		mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
											  android.R.color.holo_green_light,
											  android.R.color.holo_orange_light, android.R.color.holo_red_light);
		mSwipeRefreshLayout.setOnRefreshListener(this);
		
		mSwipeRefreshLayout.post(new Runnable() {
				@Override
				public void run() {
					mSwipeRefreshLayout.setRefreshing(true);
				}
			});
		mFloatingActionButton=(FloatingActionButton)v.findViewById(R.id.new_code);
		mFloatingActionButton.setOnClickListener(this);
		
		recyclerView=(RecyclerView)v.findViewById(R.id.recycler_view);
		mLinearLayoutManager = new LinearLayoutManager(getActivity());
		recyclerView.setLayoutManager(mLinearLayoutManager);
		dataRecyclerViewHolder=new dataRecyclerViewHolder(mList);
		recyclerView.setAdapter(dataRecyclerViewHolder);
		recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
				@Override
				public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
					super.onScrolled(recyclerView, dx, dy);
					lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
				}

				@Override
				public void onScrollStateChanged(RecyclerView recyclerView,int newState) {
					super.onScrollStateChanged(recyclerView, newState);
					if(newState ==RecyclerView.SCROLL_STATE_IDLE 
					   && lastVisibleItem + 1 == dataRecyclerViewHolder.getItemCount()){
						data_skip++;
						isNetwork_LoadingData(data_skip);
					}
				}
			});
	}

	private void initData(final int skip)
	{
		// 刷新数据
		final BmobQuery<Data> query = new BmobQuery<Data>();
		query.order("-createdAt");
		query.setLimit(10);
		//忽略前面数据
		query.setSkip(skip*10);
		if(skip==0){
			mList.clear();
			data_skip=0;
		}
		query.findObjects(new FindListener<Data>(){
				@Override
				public void done(List<Data> losts, BmobException p2)
				{
					if(p2==null){
						if (losts == null || losts.size() == 0) {
							return;
						}
						mList.addAll(dataRecyclerViewHolder.getItemCount() - 1, losts);
						dataRecyclerViewHolder.notifyDataSetChanged();
						recyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.list_main));
						mSwipeRefreshLayout.setRefreshing(false);
						//dataRecyclerViewHolder.notifyItemRemoved(dataRecyclerViewHolder.getItemCount());
					}else{
						myApplication.showToast("加载数据出错"+p2);
						//dataRecyclerViewHolder.notifyItemRemoved(dataRecyclerViewHolder.getItemCount());
					}
				}
			});
	}
	
	private void initBmobUser(){
		user=BmobUser.getCurrentUser(User.class);
		if(user!=null){
			mFloatingActionButton.setVisibility(View.VISIBLE);
			HeadColor=myApplication.getHeadColor((String)user.getObjectByKey("HeadColor"));
		}else{
			mFloatingActionButton.setVisibility(View.GONE);
			HeadColor=getResources().getColor(R.color.PrimaryColor);
		}
		mFloatingActionButton.setColorNormal(HeadColor);
	}

	@Override
	public void onStart()
	{
		super.onStart();
		initBmobUser();
		isNetwork_LoadingData(0);
	}
	
	
	@Override
	public void onRefresh()
	{
		isNetwork_LoadingData(0);
	}
	
	public void isNetwork_LoadingData(int skip){
		if(myApplication.isNetwork(getActivity())){
			initData(skip);
		}else{
			myApplication.showToast("当前无网络");
			mSwipeRefreshLayout.setRefreshing(false);
		}
	}
	
	@Override
	public void onClick(View p1)
	{
		switch(p1.getId()){
			case R.id.new_code:
				//startActivity(new Intent(getActivity(),writeActivity.class));
				myApplication.saveData(user.getUsername(),user.getHeadColor(),"标题","本内容为测试内容");
				break;
		}
	}
	
}
