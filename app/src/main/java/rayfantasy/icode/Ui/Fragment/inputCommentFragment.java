package rayfantasy.icode.Ui.Fragment;

import rayfantasy.icode.Bmob.*;
import com.rengwuxian.materialedittext.*;
import android.support.v7.widget.*;
import rayfantasy.icode.*;
import android.widget.*;
import android.view.*;
import android.os.*;
import android.content.*;
import android.view.View.*;
import cn.bmob.v3.listener.*;
import cn.bmob.v3.*;
import cn.bmob.v3.exception.*;
import android.view.inputmethod.*;
import rayfantasy.icode.R;
import android.support.v4.app.*;
import android.content.res.*;
import at.markushi.ui.*;
import rayfantasy.icode.Ui.Activity.*;
import cn.bmob.v3.datatype.*;
import java.util.*;


public class inputCommentFragment extends DialogFragment {

	private User user;
	private int HeadColor;
	private String id;
	private MyApplication myApplication;
	
	private MaterialEditText mText;
	private CircleButton mButton;
	
	private View v;
	private Intent i;
	
	public interface OnCommentClickListener{
		void onClick(String comment)
	}
	
	private OnCommentClickListener mOnCommentClickListener;
	
	public void setOnCommentClickListener(OnCommentClickListener mOnCommentClickListener){
		this.mOnCommentClickListener=mOnCommentClickListener;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
		v=inflater.inflate(R.layout.fragment_input_comment,container,false);
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		init();
		initView(v);
	}

	private void init()
	{
		i=getActivity().getIntent();
		myApplication=(MyApplication)getActivity().getApplication();
		user=BmobUser.getCurrentUser(User.class);
		HeadColor=i.getIntExtra("HeadColor",R.color.PrimaryColor);
		id=i.getStringExtra("Id");
	}
	
	private void initView(View v)
	{
		mButton=(CircleButton)v.findViewById(R.id.fragmentcodeButton1);
		mText=(MaterialEditText)v.findViewById(R.id.fragmentcodeMaterialEditText1);
		mText.setTextSize(15);
		mText.setTextColor(HeadColor);
		mText.setPrimaryColor(HeadColor);
		mButton.setColor(HeadColor);
		mText.requestFocus();
		mText.post(new Runnable() {
				@Override
				public void run() {
					((InputMethodManager) getActivity().getSystemService(Context
											 .INPUT_METHOD_SERVICE)).showSoftInput(mText, 0);
				}
			});
			
		mButton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					if(mOnCommentClickListener!=null){
						mOnCommentClickListener.onClick(mText.getText().toString());
					}

				}

			});
	}
	
	
	@Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawableResource(android.R.color.transparent);

        final Resources res = getResources();
        final int titleDividerId = res.getIdentifier("titleDivider", "id", "android");
        if (titleDividerId > 0) {
            final View titleDivider = getDialog().findViewById(titleDividerId);
            if (titleDivider != null) {
                titleDivider.setBackgroundColor(res.getColor(android.R.color.transparent));
            }
        }
    }
	
}
