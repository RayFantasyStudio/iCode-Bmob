package rayfantasy.icode.Ui.Fragment;

import android.view.*;
import android.os.*;
import rayfantasy.icode.R;
import android.widget.*;
import android.content.res.*;
import android.support.v4.app.*;



public class dialogProgressFragment extends DialogFragment
{
	private View v;
	private ProgressBar progress;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
		v=inflater.inflate(R.layout.fragment_dialog_progress,container,false);
		initView(v);
		return v;
	}

	private void initView(View v)
	{
		progress=(ProgressBar)v.findViewById(R.id.fragmentdialogprogressProgressBar1);
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
