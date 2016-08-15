package rayfantasy.icode.Ui.Fragment;

import android.graphics.*;
import android.os.*;
import android.support.v4.app.*;
import android.util.*;
import android.view.*;
import com.hanks.htextview.*;
import rayfantasy.icode.*;
import rayfantasy.icode.Bmob.*;
import rayfantasy.icode.Util.*;

import rayfantasy.icode.R;
import cn.bmob.v3.*;

public class aboutFragment extends Fragment
{
	private View v;
	private User user;
	private MyApplication myApplication;
	private int HeadColor;
	
	private HTextView hTextView;
	private Handler mHandler;
    private Runnable mRunnable = new Runnable() {
        @Override
		public void run() {
            mHandler.postDelayed(mRunnable, 3000);	
			if(hTextView.getText().toString().equals("iCode")){
				ChangeText(HTextViewType.SCALE,"zzyandzzy");
			}else if(hTextView.getText().toString().equals("zzyandzzy")){
				ChangeText(HTextViewType.RAINBOW,"QQ:1428658308");
			}else if(hTextView.getText().toString().equals("QQ:1428658308")){
				ChangeText(HTextViewType.EVAPORATE,"Rayfantasy");
			}else if(hTextView.getText().toString().equals("Rayfantasy")){
				ChangeText(HTextViewType.FALL,"I will love gdq forever");
			}else if(hTextView.getText().toString().equals("I will love gdq forever")){
				ChangeText(HTextViewType.ANVIL,"iCode");
			}
		}
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		v=inflater.inflate(R.layout.fragment_about,container,false);
		initData();
		initView(v);
		return v;
	}

	private void initData()
	{
		myApplication=(MyApplication)getActivity().getApplication();
		user=BmobUser.getCurrentUser(User.class);
		if(user!=null){
			HeadColor = myApplication.getHeadColor(user.getHeadColor());
		}else{
			HeadColor=getResources().getColor(R.color.PrimaryColor);
		}
		mHandler = new Handler();
	}

	private void initView(View v)
	{
		hTextView=(HTextView)v.findViewById(R.id.about_HTextView);
		hTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		hTextView.reset(hTextView.getText());
		hTextView.setTextColor(HeadColor);
		hTextView.setBackgroundColor(Color.TRANSPARENT);
		hTextView.setTypeface(FontManager.getInstance(getActivity().getAssets()).getFont("fonts/PoiretOne-Regular.ttf"));
		mHandler.postDelayed(mRunnable, 0);
	}
	
	private void ChangeText(final HTextViewType Type,final String text){
		hTextView.setTypeface(FontManager.getInstance(getActivity().getAssets()).getFont("fonts/PoiretOne-Regular.ttf"));
		hTextView.setAnimateType(Type);
		hTextView.animateText(text);
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		mHandler.removeCallbacks(mRunnable);
		getFragmentManager().popBackStack();
	}
	
	
}
