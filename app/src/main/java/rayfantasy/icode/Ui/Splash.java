package rayfantasy.icode.Ui;

import android.os.*;
import android.view.*;
import android.content.*;
import android.app.*;
import android.*;
import android.content.pm.*;

import rayfantasy.icode.R;
import java.io.*;
import rayfantasy.icode.Data.*;
import rayfantasy.icode.Util.*;


public class Splash extends Activity
{
	private Handler handler = new Handler();
	
	String[] PER_ALL = {"android.permission.WRITE_EXTERNAL_STORAGE",
		                "android.permission.READ_PHONE_STATE"};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		setContentView(R.layout.activity_splash);
		if(needPm()){
			Notice();
			File f=new File(Utils.getiCodePath());
			if(!f.exists()){
				f.mkdir();
			}
		}
		else{
			LoadAc();
		}
	}
	
	private void PmRequest(String[] PER_NAME, int PER_CODE){
		requestPermissions(PER_NAME, PER_CODE);
    }
	private boolean needPm(){
		if (Build.VERSION.SDK_INT >= 23){
			if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
				return true;
			}
        }
		return false;
	}
    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults){
        switch (permsRequestCode){
            case 666:
				if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
					LoadAc();
				}
		}
	}
	
	private void Notice(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("提示");
		builder.setMessage("自Android6.0，由于新增权限管理API\n\n若要正常使用iCode，请接受以下权限申请");
		builder.setNegativeButton("明白", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					PmRequest(PER_ALL,666);
				}
			});

		builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});
		builder.create().show();
	}
	
	private void LoadAc(){
		handler.postDelayed(new Runnable(){
				public void run(){
					startActivity(new Intent(Splash.this,MainActivity.class));
					finish();
				}
			}
		,888);		
	}
}
