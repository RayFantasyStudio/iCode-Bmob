package com.icode.Ui;

import android.os.*;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.*;

import android.support.v7.widget.Toolbar;
import android.view.View.*;
import android.content.*;
import android.support.v4.view.*;
import com.icode.*;
import com.icode.R;
import cn.bmob.v3.*;
import com.amulyakhare.textdrawable.*;
import android.graphics.*;
import com.icode.Ui.Activity.*;
import com.icode.Ui.Fragment.*;
import android.app.*;
import cn.bmob.v3.update.*;

public class MainActivity extends AppCompatActivity 
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
		BmobUpdateAgent.initAppVersion(this);//自动在Web端创建表，第一次运行后创建，第二删除这行或注释掉
		BmobUpdateAgent.setUpdateOnlyWifi(false);//在任何环境下提示更新
		BmobUpdateAgent.update(this);//调用更新
        setContentView(R.layout.activity_main);
		
		initView();
		
		initUser();
		
		initData();
    }

	private void initData()
	{
		getFragmentManager().beginTransaction().replace(R.id.frame,new dataFragment()).commit();
	}

	private void initUser()
	{
		bmobUser = BmobUser.getCurrentUser(MainActivity.this);
		//如果本地用户不为bull证明登录了
		if(bmobUser != null){
			//设置用户名称
			userName.setText(bmobUser.getUsername());
			//设置用户头像
			drawableBuilder=TextDrawable.builder().round();
			//根据用户名称的第一位字符设置头像
			ImageView_user.setImageDrawable(drawableBuilder.build(userName.getText().toString().subSequence(0,1).toString(),
			Color.argb(200,255,127,127)));
		}else{
			userName.setText("登录iCode账户");
			ImageView_user.setImageDrawable(getResources().getDrawable(R.drawable.icode_user));
		}
		
	}

	private void initView()
	{
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
		
		ImageView_user.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					//点击头像跳转
					closeDrawer();
					
					startActivity(new Intent(MainActivity.this,userActivity.class));
				}
			
		});
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

}
