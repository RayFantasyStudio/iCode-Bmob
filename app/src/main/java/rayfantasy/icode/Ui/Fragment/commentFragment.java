package rayfantasy.icode.Ui.Fragment;

import android.content.*;
import android.os.*;
import android.support.v4.app.Fragment;
import android.support.v4.widget.*;
import android.support.v7.widget.*;
import android.view.*;
import com.melnykov.fab.*;
import rayfantasy.icode.*;
import rayfantasy.icode.Bmob.*;

import rayfantasy.icode.R;
import rayfantasy.icode.Adapter.*;
import java.util.*;
import cn.bmob.v3.*;
import cn.bmob.v3.exception.*;
import cn.bmob.v3.datatype.*;
import cn.bmob.v3.listener.*;
import android.view.animation.*;
import android.view.View.*;
import rayfantasy.icode.Util.*;
import android.app.*;
import rayfantasy.icode.Ui.Fragment.inputCommentFragment.*;
import rayfantasy.icode.Ui.Fragment.dialogProgressFragment.*;

public class commentFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,OnCommentClickListener
{
	private MyApplication myApplication;
	private View v;
	private Intent i;
	private int HeadColor;
	private String id;
	private User user;
	
	private RecyclerView recyclerView;
	private LinearLayoutManager mLinearLayoutManager;
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private FloatingActionButton fab_comment;
	
	private List<Comment> mListComment = new ArrayList<Comment>();
	private commentHolder mCommentHolder;
	
	private inputCommentFragment input;
	private dialogProgressFragment dialog_progress;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		v=inflater.inflate(R.layout.fragment_comment,container,false);
		init();
		initView(v);
		return v;
	}

	private void init()
	{
		i=getActivity().getIntent();
		myApplication=(MyApplication)getActivity().getApplication();
		HeadColor=i.getIntExtra("HeadColor",getResources().getColor(R.color.PrimaryColor));
		id=i.getStringExtra("Id");
		user=BmobUser.getCurrentUser(User.class);
	}

	private void initView(View v)
	{
		mSwipeRefreshLayout=(SwipeRefreshLayout)v.findViewById(R.id.fragmentcodeSwipeRefreshLayout);
		mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
													android.R.color.holo_green_light,
													android.R.color.holo_orange_light, android.R.color.holo_red_light);
		mSwipeRefreshLayout.setOnRefreshListener(this);
		mSwipeRefreshLayout.post(new Runnable() {
				@Override
				public void run() {
					findComment(id,0);
					mSwipeRefreshLayout.setRefreshing(true);
				}
			});
		recyclerView=(RecyclerView)v.findViewById(R.id.recycler_comment);
		mLinearLayoutManager = new LinearLayoutManager(getActivity());
		recyclerView.setLayoutManager(mLinearLayoutManager);
		mCommentHolder=new commentHolder(mListComment,myApplication);
		recyclerView.setAdapter(mCommentHolder);
		
		dialog_progress=new dialogProgressFragment();
		input=new inputCommentFragment();
		input.setOnCommentClickListener(this);
		fab_comment=(FloatingActionButton)v.findViewById(R.id.fab_comment);
		fab_comment.setColorNormal(HeadColor);
		fab_comment.setColorPressed(HeadColor);
		fab_comment.attachToRecyclerView(recyclerView);
		fab_comment.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					findUser(user.getUsername());
				}
			});
		if(user==null){
			fab_comment.setVisibility(View.GONE);
		}
	}
	
	@Override
	public void onRefresh()
	{
		findComment(id,0);
	}
	
	@Override
	public void onClick(String comment)
	{
		if(comment.length()==0){
			myApplication.showToast("输入内容为空");
		}else if(comment.length()<100){
			dialog_progress.show(getFragmentManager(),"dialogProgressFragment");
			saveComment(id,comment);
			setInputVisibility(false);
		}else{
			myApplication.showToast("当前字数:"+comment.length()+" 大于评论规定字数:200");
		}
	}
	
	//评论
	public void saveComment(final String id,String content){
		final Data post = new Data();
		post.setObjectId(id);
		final Comment comment = new Comment();
		comment.setContent(content);
		comment.setData(post);
		comment.setUser(user);
		comment.save(new SaveListener<String>() {
				@Override
				public void done(String objectId,BmobException e) {
					if(e==null){
						findComment(id,1);
					}else{
						myApplication.showToast("失败："+e.getMessage());
					}
				}

			});
	}
	
	
	public void updateCommentSize(int size){
		Data data=new Data();
		data.setCommentSize(size);
		data.update(id, new UpdateListener(){
				@Override
				public void done(BmobException e)
				{
					if(e==null){
						findComment(id,0);
						myApplication.showToast("评论发表成功");
						dialog_progress.dismiss();
					}else{
						myApplication.showToast("失败："+e.getMessage());
					}
				}
			});
	}
	
	
	public void findComment(final String id,final int listener){
		BmobQuery<Comment> query = new BmobQuery<Comment>();
		Data post = new Data();
		post.setObjectId(id);
		query.order("createdAt");
		mListComment.clear();
		query.setLimit(100);
		query.addWhereEqualTo("data",new BmobPointer(post));
		query.include("user,data");
		query.findObjects(new FindListener<Comment>() {
				@Override
				public void done(List<Comment> objects,BmobException e) {

					if(e==null){
						if(listener==0){
							mListComment.addAll(0,objects);
							mCommentHolder.notifyDataSetChanged();
							recyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.list_main));
						}else if(listener==1){
							updateCommentSize(objects.size());
						}
						mSwipeRefreshLayout.setRefreshing(false);
					}else{
						mSwipeRefreshLayout.setRefreshing(false);
						myApplication.showToast("查询评论失败"+e.getMessage());
					}

				}
			});
	}
	
	
	private void findUser(String username){
		BmobQuery<User> query = new BmobQuery<User>();
		query.addWhereEqualTo("username", username);
		query.findObjects(new FindListener<User>(){
				@Override
				public void done(List<User> object,BmobException e) {
					if(e==null){
						if(object.get(0).getEmailVerified()){
							setInputVisibility(true);
						}else{
							showDialog("激活","未激活邮箱，是否立即激活邮箱？","激活","取消","EmailVerified");
						}
					}else{
						myApplication.showToast("查询状态失败，无法发帖");
					}
				}
			});
	}

	private void showDialog(String Title,String Message,String Position,String Negative,final String Listener){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(Title);
		builder.setMessage(Message);
		builder.setPositiveButton(Position, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch(Listener){
						case "EmailVerified":
							myApplication.EmailVerified(user.getEmail());
							break;
					}
				}
			});

		builder.setNegativeButton(Negative, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
		builder.create().show();
	}
	
	public void setInputVisibility(boolean Visibility){
		if(Visibility){
			input.show(getFragmentManager(), "inputCommentFragment");		
		}else{
			input.dismiss();
		}
	}
	
}
