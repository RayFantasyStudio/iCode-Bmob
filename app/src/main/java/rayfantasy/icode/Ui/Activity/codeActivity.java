package rayfantasy.icode.Ui.Activity;

import android.os.*;
import rayfantasy.icode.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.content.*;
import rayfantasy.icode.Ui.Fragment.*;
import it.neokree.materialtabs.*;
import android.support.v4.view.ViewPager;
import rayfantasy.icode.R;
import android.support.v4.app.*;
import android.graphics.drawable.*;
import android.content.res.*;
import android.view.*;

public class codeActivity extends AppCompatActivity implements MaterialTabListener
{
	private Window window;
	
	private Intent i;
	private Toolbar toolbar;
	private MaterialTabHost tabHost;
	private ViewPager pager;
    private ViewPagerAdapter pagerAdapter;
	private Resources res;
	
	private int HeadColor;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		init();
	}

	private void init()
	{
		i=getIntent();
		HeadColor=i.getIntExtra("HeadColor",getResources().getColor(R.color.PrimaryColor));
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			window = getWindow();
			window.setFlags(
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setNavigationBarColor(HeadColor);
			window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		}
        setContentView(R.layout.activity_code);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setBackgroundColor(HeadColor);
		setTitle(i.getStringExtra("Title"));
		setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onBackPressed();
				}
			});
			
			
		res = this.getResources();
		tabHost = (MaterialTabHost) this.findViewById(R.id.tabHost);
		
		//tabHost.setAccentColor(HeadColor);
		tabHost.setPrimaryColor(HeadColor);
        pager = (ViewPager) this.findViewById(R.id.pager);
        // init view pager
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
				@Override
				public void onPageSelected(int position) {
					// when user do a swipe the selected tab change
					tabHost.setSelectedNavigationItem(position);
				}
			});
        // insert all tabs from pagerAdapter data
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            tabHost.addTab(
				tabHost.newTab()
				.setIcon(getIcon(i))
				.setTabListener(this)
            );
        }
	}
	
	@Override
	public void onTabSelected(MaterialTab tab)
	{
		pager.setCurrentItem(tab.getPosition());
		
	}

	@Override
	public void onTabReselected(MaterialTab p1)
	{
	}

	@Override
	public void onTabUnselected(MaterialTab p1)
	{
	}
	
	private class ViewPagerAdapter extends FragmentStatePagerAdapter {
		
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }
		
        public Fragment getItem(int num) {
			switch(num){
				case 0:
					return new codeFragment();
				case 1:
					return new commentFragment();
				default:
					return null;
			}
		}
		
        @Override
        public int getCount() {
            return 2;
        }
		
        @Override
        public CharSequence getPageTitle(int position) {
            switch(position) {
                case 0:
					return "帖子";
                case 1:
					return "评论";
                default:
					return null;
            }
        }
	}
	
    private Drawable getIcon(int position) {
        switch(position) {
            case 0:
                return res.getDrawable(R.drawable.ic_person_white);
            case 1:
                return res.getDrawable(R.drawable.ic_comment_white);
        }
        return null;
    }
	
}
