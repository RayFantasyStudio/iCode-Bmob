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
import rayfantasy.icode.Data.Var;

import android.support.design.widget.*;
import android.support.v4.view.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.Snackbar;

import com.tencent.bugly.crashreport.*;
import cn.bmob.v3.*;
import cn.bmob.v3.update.*;
import com.amulyakhare.textdrawable.*;
import java.util.*;
import android.net.*;
import java.io.*;
import de.hdodenhof.circleimageview.CircleImageView;
import android.support.v4.widget.*;
import rayfantasy.icode.Bmob.*;

public class MainActivity extends AppCompatActivity implements OnClickListener
{
	private Toolbar toolbar;
    
	private DrawerLayout drawerLayout;
	private NavigationView navigationView;
	
	private MyApplication myApplication;
	//用户名称
	private TextView userName;
	//获取本地登录的bmob信息
	private User bmobUser;
	//圆形头像
	private TextDrawable drawableBuilder;
	private CircleImageView mCircleImageView;
	private int drawerLayoutCheck = GravityCompat.START;
	
	private int HeadColor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		BmobUpdateAgent.setUpdateOnlyWifi(false);//在任何环境下提示更新
		BmobUpdateAgent.update(this);//调用更新
        setContentView(R.layout.activity_main);
		
		initView();
		
		initUser();
		
		if(myApplication.isNetwork(this) || checkCache()){
			initData();
		}else{
			showDialog("无网络","请检查网络状态！","设置","取消","Network");
		}
    }
	
	private boolean checkCache(){
		String s = "/data/data/rayfantasy.icode/code_cache/com.android.opengl.shaders_cache";
		File f = new File(s);
		if(f.exists()){
			if(f.isFile() && f.length() != 0){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}
	
	private void initData()
	{
		getFragmentManager().beginTransaction().replace(R.id.frame,new dataFragment()).commit();
	}

	private void initUser()
	{
		bmobUser = BmobUser.getCurrentUser(User.class);
		//如果本地用户不为null证明登录了
		if(bmobUser != null){
			//设置用户名称
			userName.setText(bmobUser.getUsername());
			//清空
			mCircleImageView.setImageResource(0);
			//根据用户名称的第一位字符设置头像
			HeadColor=myApplication.getHeadColor((String)bmobUser.getObjectByKey("HeadColor"));
			mCircleImageView.setBackground(drawableBuilder.builder().buildRound(userName.getText().toString().subSequence(0,1).toString(),HeadColor));
			
		}else{
			userName.setText("登录iCode");
			//清空
			mCircleImageView.setBackgroundResource(0);
			mCircleImageView.setImageDrawable(getResources().getDrawable(R.drawable.icode_user));
			
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
		mCircleImageView=(CircleImageView)findViewById(R.id.icode_user);
		
		mCircleImageView.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View p1)
	{
		switch(p1.getId()){
			case R.id.icode_user:
				//点击头像跳转
				closeDrawer();
				Intent i=new Intent(MainActivity.this,userActivity.class);
				i.putExtra("UserName",userName.getText().toString());
				startActivity(i);
			break;		
		}
	}
	
	//无网络跳转
	public void NetworkIntent(){
		Intent intent = null;
		if (android.os.Build.VERSION.SDK_INT > 10) {
			intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
		} else {
			intent = new Intent();
			ComponentName component = new ComponentName(
				"com.android.settings",
				"com.android.settings.WirelessSettings");
			intent.setComponent(component);
			intent.setAction("android.intent.action.VIEW");
		}
		startActivity(intent);
		myApplication.showToast("当前无网络");
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
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			showDialog("退出","你确定要退出？","确定","取消","finish");
		}
		return false;
	}

	private void showDialog(String Title,String Message,String Position,String Negative,final String Listener){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(Title);
		builder.setMessage(Message);
		builder.setPositiveButton(Position, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch(Listener){
						case "finish":
							finish();
						break;
						case "Network":
							NetworkIntent();
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
	
}
