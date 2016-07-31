package rayfantasy.icode.Ui.Fragment;

import android.app.*;
import rayfantasy.icode.*;
import android.view.*;
import android.os.*;
import android.content.*;
import rayfantasy.icode.Bmob.*;
import cn.bmob.v3.*;
import cn.bmob.v3.datatype.*;
import cn.bmob.v3.listener.*;
import java.util.*;
import cn.bmob.v3.exception.*;
import android.widget.*;
import android.support.v7.widget.*;
import android.graphics.*;
import com.amulyakhare.textdrawable.*;
import de.hdodenhof.circleimageview.*;
import rayfantasy.icode.R;
import android.support.v4.widget.SwipeRefreshLayout;
import rayfantasy.icode.Adapter.*;
import android.view.animation.*;
import com.rengwuxian.materialedittext.*;
import android.view.View.*;

public class codeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
	private MyApplication myApplication;
	private View v;
	private Intent i;
	private User user;
	
	private TextView Title,Message,Time,UserName;
	private CircleImageView Head;
	private TextDrawable drawableBuilder;
	
	private RecyclerView recyclerView;
	private LinearLayoutManager mLinearLayoutManager;
	private SwipeRefreshLayout mSwipeRefreshLayout;
	
	//评论
	private CardView mCardView;
	private MaterialEditText mText;
	private Button mButton;
	
	private int HeadColor,HeadVersion;
	private String id,Email,HeadUri,STitle,SMessage,STime,SUserName;
	private String path=Environment.getExternalStorageDirectory().getPath()+"/.iCode";
	
	private List<Comment> mListComment = new ArrayList<Comment>();
	private commentHolder mCommentHolder;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		v=inflater.inflate(R.layout.fragment_code,container,false);
		init();
		initView(v);
		return v;
	}

	private void init()
	{
		i=getActivity().getIntent();
		myApplication=(MyApplication)getActivity().getApplication();
		user=BmobUser.getCurrentUser(User.class);
		
		HeadColor=i.getIntExtra("HeadColor",getResources().getColor(R.color.PrimaryColor));
		STitle=i.getStringExtra("Title");
		SMessage=i.getStringExtra("Message");
		STime=i.getStringExtra("Time");
		SUserName=i.getStringExtra("UserName");
		id=i.getStringExtra("Id");
		Email=i.getStringExtra("Email");
		HeadUri=i.getStringExtra("HeadUri");
		HeadVersion=i.getIntExtra("HeadVersion",0);
	}

	private void initView(View v)
	{
		Time=(TextView)v.findViewById(R.id.list_time);
		Title=(TextView)v.findViewById(R.id.list_title);
		Message=(TextView)v.findViewById(R.id.list_message);
		UserName=(TextView)v.findViewById(R.id.list_user);
		Head=(CircleImageView)v.findViewById(R.id.list_head);
		mSwipeRefreshLayout=(SwipeRefreshLayout)v.findViewById(R.id.fragmentcodeSwipeRefreshLayout);
		mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
													android.R.color.holo_green_light,
													android.R.color.holo_orange_light, android.R.color.holo_red_light);
		mSwipeRefreshLayout.setOnRefreshListener(this);
		mSwipeRefreshLayout.post(new Runnable() {
				@Override
				public void run() {
					findComment(id);
					mSwipeRefreshLayout.setRefreshing(true);
				}
			});
		recyclerView=(RecyclerView)v.findViewById(R.id.recycler_comment);
		mLinearLayoutManager = new LinearLayoutManager(getActivity());
		recyclerView.setLayoutManager(mLinearLayoutManager);
		mCommentHolder=new commentHolder(mListComment,myApplication);
		recyclerView.setAdapter(mCommentHolder);
		
		Title.setText(STitle);
		if(SMessage.length()<300){
			Message.setText(SMessage);
		}else{
			Message.setText(SMessage.subSequence(0,300)+"…");
		}
		UserName.setText(SUserName);
		Time.setText(STime);
		//用户是否登录
		if(user!=null){
			Title.setTextColor(getTextColor(user.getHeadColor()));
			if(i.getIntExtra("HeadVersion",0)==0){
				//当用户从未上传头像时，设置默认头像
				Head.setImageResource(0);
				Head.setBackground(drawableBuilder.builder().buildRound(UserName.getText().toString().subSequence(0,1).toString(),
																					  getTextColor(user.getHeadColor())));
			}else if(myApplication.isFile("/cache/"+Email+"_"+HeadVersion+".png")){
				Head.setBackgroundResource(0);
				Head.setImageBitmap(BitmapFactory.decodeFile(path+"/cache/"+Email+"_"+HeadVersion+".png"));
			}else{
				myApplication.downloadFile(new BmobFile(Email,"",HeadUri),Email+"_"+HeadVersion,Head);
			}
		}else{
			//处理用户未登录
			Title.setTextColor(R.color.PrimaryColor);
			Head.setImageResource(0);
			Head.setBackground(drawableBuilder.builder().buildRound(UserName.getText().toString().subSequence(0,1).toString(),
											R.color.PrimaryColor));
		}
		mCardView=(CardView)v.findViewById(R.id.list_cardview_comment);
		mButton=(Button)v.findViewById(R.id.fragmentcodeButton1);
		mText=(MaterialEditText)v.findViewById(R.id.fragmentcodeMaterialEditText1);
		
		mText.setTextColor(HeadColor);
		mText.setPrimaryColor(HeadColor);
		
		if(user!=null){
			mCardView.setVisibility(View.VISIBLE);
		}else{
			mCardView.setVisibility(View.GONE);
		}
		
		mButton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					myApplication.saveComment(id,mText.getText().toString());
					mText.setText("");
				}
				
		});
	}

	@Override
	public void onRefresh()
	{
		findComment(id);
	}
	
	private void findComment(final String id){
		BmobQuery<Comment> query = new BmobQuery<Comment>();
		Data post = new Data();
		post.setObjectId(id);
		query.order("createdAt");
		mListComment.clear();
		query.setLimit(10);
		query.addWhereEqualTo("data",new BmobPointer(post));
		query.include("user,data");
		query.findObjects(new FindListener<Comment>() {
				@Override
				public void done(List<Comment> objects,BmobException e) {
				
					if(e==null){
						if(objects.size()==0||objects==null){
							mSwipeRefreshLayout.setRefreshing(false);
							myApplication.showToast("此贴还没有评论哦！");
							return ;
						}else{
							mListComment.addAll(0,objects);
							mCommentHolder.notifyDataSetChanged();
							recyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.list_main));
						}
						mSwipeRefreshLayout.setRefreshing(false);
					}else{
						mSwipeRefreshLayout.setRefreshing(false);
						myApplication.showToast("查询评论失败"+e.getMessage());
					}
					
				}
			});
	}

	
	//String转int
	public int getTextColor(String s){
		return Integer.valueOf(s).intValue();
	}
	
}
