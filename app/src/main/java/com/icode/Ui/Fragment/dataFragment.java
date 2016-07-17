package com.icode.Ui.Fragment;

import android.app.*;
import android.os.*;
import android.view.*;
import com.icode.*;
import android.support.v4.widget.*;
import android.widget.*;
import java.util.*;
import cn.bmob.v3.*;
import com.icode.Bmob.*;
import cn.bmob.v3.listener.*;
import cn.bmob.v3.exception.*;

public class dataFragment extends Fragment
{
	//下拉刷新控件
	private SwipeRefreshLayout mSwipeLayout;
	private ListView listview;
	private ArrayList<Map<String, Object>> mDate = new ArrayList<>();
	private SimpleAdapter adapter;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v=inflater.inflate(R.layout.fragment_data,container,false);
		initView(v);
		initData();
		return v;
	}

	private void initData()
	{
		// 刷新数据
		final BmobQuery<Data> query = new BmobQuery<Data>();
		query.order("-createdAt");
		query.findObjects(getActivity(), new FindListener<Data>(){

				@Override
				public void onSuccess(List<Data> p1)
				{
					if(p1!=null){
						mDate.clear();
						for (Data date_android : p1) {
							final Map<String, Object> item = new HashMap<String, Object>();
							String title = date_android.getTitle();
							String message = date_android.getMessage();
							String user = date_android.getUser();
							String time=date_android.getCreatedAt();
							item.put("list_title",title);
							item.put("list_message", message);
							item.put("list_user", user);
							item.put("list_time",time);
							mDate.add(item);
						}
							adapter.notifyDataSetChanged();
						}
				}

				@Override
				public void onError(int p1, String p2)
				{
					// TODO: Implement this method
				}
				
			
		});
	}

	private void initView(View v)
	{
		listview=(ListView)v.findViewById(R.id.ListView);
		adapter = new SimpleAdapter(getActivity(), mDate, R.layout.list_text,
									new String[]{ "list_title", "list_message", "list_user","list_time"},
									new int[]{R.id.list_title, R.id.list_message, R.id.list_user,R.id.list_time});
		listview.setAdapter(adapter);
		
		
	}
	
}
