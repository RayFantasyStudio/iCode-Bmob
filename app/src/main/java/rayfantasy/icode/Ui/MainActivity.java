package rayfantasy.icode.Ui;

import android.content.*;
import android.content.pm.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

import rayfantasy.icode.*;
import rayfantasy.icode.Ui.Activity.*;
import rayfantasy.icode.Ui.Fragment.*;
import rayfantasy.icode.Var;

import android.support.design.widget.*;
import android.support.v4.view.*;
import android.support.v4.widget.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.Snackbar;

import com.tencent.bugly.crashreport.*;
import cn.bmob.v3.*;
import cn.bmob.v3.update.*;
import com.amulyakhare.textdrawable.*;
import java.util.*;

public class MainActivity extends AppCompatActivity implements OnClickListener
{
	private Toolbar toolbar;
    
	private DrawerLayout drawerLayout;
	private NavigationView navigationView;
	
	//头像
	private ImageView ImageView_user;
	private MyApplication myApplication;
	//用户名称
	private TextView userName;
	//获取本地登录的bmob信息
	private BmobUser bmobUser;
	//圆形头像
	private TextDrawable.IBuilder drawableBuilder;

	private int drawerLayoutCheck = GravityCompat.START;
	

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		//BmobUpdateAgent.initAppVersion(this);//自动在Web端创建表，第一次运行后创建，第二删除这行或注释掉
		BmobUpdateAgent.setUpdateOnlyWifi(false);//在任何环境下提示更新
		BmobUpdateAgent.update(this);//调用更新
        setContentView(R.layout.activity_main);
		
		initbugly();
		
		initSign();
		
		initView();
		
		initUser();
		
		initData();
    }
	
	private void initbugly(){
		CrashReport.initCrashReport(getApplicationContext(),Var.String("BuglyID"),false);
	}

	private void initData()
	{
		getFragmentManager().beginTransaction().replace(R.id.frame,new dataFragment()).commit();
	}

	private void initUser()
	{
		bmobUser = BmobUser.getCurrentUser(MainActivity.this);
		//如果本地用户不为null证明登录了
		if(bmobUser != null){
			//设置用户名称
			userName.setText(bmobUser.getUsername());
			//设置用户头像
			drawableBuilder=TextDrawable.builder().round();
			//根据用户名称的第一位字符设置头像
			ImageView_user.setImageDrawable(drawableBuilder.build(userName.getText().toString().subSequence(0,1).toString(),
			myApplication.getUserRandomColor()));
			
		}else{
			userName.setText("登录iCode");
			ImageView_user.setImageDrawable(getResources().getDrawable(R.drawable.icode_user));
			
		}
		
	}

	private void initView()
	{
		myApplication=(MyApplication)getApplication();
		toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
		
		navigationView = (NavigationView)findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
				@Override
				public boolean onNavigationItemSelected(MenuItem menuItem) {

					if(menuItem.isChecked()) menuItem.setChecked(false);
					else menuItem.setChecked(true);

					closeDrawer();

					switch (menuItem.getItemId()){
						case R.id.about:
							return true;
						case R.id.setting:
							return true;
						case R.id.release:
							startActivity(new Intent(MainActivity.this,writeActivity.class));
							return true;
						default:
							Toast.makeText(MainActivity.this,"错误",Toast.LENGTH_SHORT).show();
							return true;
					}
				}
			});
			
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

		drawerLayout.setDrawerListener(actionBarDrawerToggle);
		actionBarDrawerToggle.syncState();
		userName=(TextView)findViewById(R.id.main_username);
		ImageView_user=(ImageView)findViewById(R.id.icode_user);
		
		ImageView_user.setOnClickListener(this);
		
		
	}
	
	public void initSign(){
        try
        {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            int code = sign.hashCode();
            int official = 507144210;
            int debug = -253306175;
			
            if (code != official)
            {
                if (code != debug){
                    Toast.makeText(this,"Sign failed!",0).show();
                    finish();
                }
                else{
                    Toast.makeText(this,"Debug Version!" ,0).show();
                }
            }
        }
        catch (PackageManager.NameNotFoundException e){
        }
    }

	@Override
	public void onClick(View p1)
	{
		switch(p1.getId()){
			case R.id.user_image:
				//点击头像跳转
				closeDrawer();
				startActivity(new Intent(MainActivity.this,userActivity.class));
				break;		
		}
	}

	
	public void closeDrawer(){
		drawerLayout.closeDrawer(drawerLayoutCheck);
	}
	
	public void openDrawer(){
		drawerLayout.openDrawer(drawerLayoutCheck);
	}

	@Override
	protected void onRestart()
	{
		super.onRestart();
		
		initUser();
	}
	
	private void BarWithAction(String Message,String ActionText,final String Function){
		Snackbar.make(getCurrentFocus(), Message, Snackbar.LENGTH_LONG).setAction(ActionText, new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					switch(Function){
						case "finish":
							finish();
					}
				}
			}).show();
	}
	
	private void Bar(String s){
		Snackbar.make(getCurrentFocus(),s,0).show();
	}

		
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{ 
			BarWithAction("你确定要退出？","确定","finish");
		}
		return false;
	}

}
