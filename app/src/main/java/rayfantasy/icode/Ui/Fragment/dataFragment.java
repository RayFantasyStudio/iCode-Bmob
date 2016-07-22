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
import com.chanven.lib.cptr.*;
import com.chanven.lib.cptr.loadmore.*;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;


public class dataFragment extends Fragment implements OnClickListener,SwipeRefreshLayout.OnRefreshListener
{	
	//private SwipeRefreshLayout mSwipeRefreshLayout;
	private PtrClassicFrameLayout ptrClassicFrameLayout;
	
	private RecyclerView recyclerView;
	private dataRecyclerViewHolder dataRecyclerViewHolder;
	private LinearLayoutManager mLinearLayoutManager;
	
	private View v;
	private List<Data> mList=new ArrayList<Data>();
	
	private FloatingActionButton mFloatingActionButton;
	
	private MyApplication myApplication;
	private int data_skip=0;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		v=inflater.inflate(R.layout.fragment_data,container,false);
		initView(v);
		initData(0);
		return v;
	}
	
	private void initView(View v)
	{
		myApplication=(MyApplication)getActivity().getApplication();
		/*mSwipeRefreshLayout=(SwipeRefreshLayout)v.findViewById(R.id.swipe_layout);
		mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
											  android.R.color.holo_green_light,
											  android.R.color.holo_orange_light, android.R.color.holo_red_light);
		mSwipeRefreshLayout.setOnRefreshListener(this);
		*/
		recyclerView=(RecyclerView)v.findViewById(R.id.recycler_view);
		mLinearLayoutManager = new LinearLayoutManager(getActivity());
		recyclerView.setLayoutManager(mLinearLayoutManager);
		
		
        recyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.list_main));
		dataRecyclerViewHolder=new dataRecyclerViewHolder(mList);
		recyclerView.setAdapter(dataRecyclerViewHolder);
		
		mFloatingActionButton=(FloatingActionButton)v.findViewById(R.id.new_code);
		mFloatingActionButton.setOnClickListener(this);
		mFloatingActionButton.attachToRecyclerView(recyclerView);
		
		ptrClassicFrameLayout=(PtrClassicFrameLayout)v.findViewById(R.id.recycler_view_frame);
		
		ptrClassicFrameLayout.postDelayed(new Runnable() {
				@Override
				public void run() {
					ptrClassicFrameLayout.autoRefresh(true);
				}
		}, 150);
		ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
				@Override
				public void onRefreshBegin(PtrFrameLayout frame) {
					new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								if(myApplication.isNetwork(getActivity())){
									initData(0);
								}else{
									myApplication.showToast("当前无网络");
								}	
								ptrClassicFrameLayout.refreshComplete();
								//ptrClassicFrameLayout.setLoadMoreEnable(true);
							}
						}, 1000);
				}
			});
				
		ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
				@Override
				public void loadMore() {
					new Handler().postDelayed(new Runnable() {

							@Override
							public void run() {
								data_skip++;
								initData(data_skip);
								
								ptrClassicFrameLayout.loadMoreComplete(true);
							}
						}, 1000);
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
		}
		query.findObjects(getActivity(), new FindListener<Data>(){

				@Override
				public void onSuccess(List<Data> losts)
				{
					if (losts == null || losts.size() == 0) {
						return;
					}
					mList.addAll(skip, losts);
					dataRecyclerViewHolder.notifyDataSetChanged();
					//mSwipeRefreshLayout.setRefreshing(false);
				}

				@Override
				public void onError(int p1, String p2)
				{
					
				}
				
			
		}); 
	}

	@Override
	public void onRefresh()
	{
		if(myApplication.isNetwork(getActivity())){
			initData(0);
		}else{
			myApplication.showToast("当前无网络");
			//mSwipeRefreshLayout.setRefreshing(false);
		}
	}

	@Override
	public void onClick(View p1)
	{
		
		switch(p1.getId()){
			case R.id.new_code:
				startActivity(new Intent(getActivity(),writeActivity.class));
				break;
		}
		
	}

    public void onPostLoadMore() {
        initData(data_skip);
		myApplication.showToast("加载更多");
        dataRecyclerViewHolder.notifyDataSetChanged();
    }
	
}
