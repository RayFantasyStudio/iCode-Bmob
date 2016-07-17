package com.icode;

import android.app.*;
import android.content.*;
import android.net.*;
import android.preference.*;
import android.telephony.*;
import android.widget.*;

import cn.bmob.v3.*;
import cn.bmob.v3.listener.*;

import com.icode.Bmob.*;

public class MyApplication extends Application
{
	private SharedPreferences setting;
	private Context context;
	
	public static final String Bmob_APPID="ed40b22516d8679a79eca831e77ec5ad";

	public void onCreate() {
        super.onCreate();
		setting = PreferenceManager.getDefaultSharedPreferences(this);
        Bmob.initialize(this, Bmob_APPID);
		context=this;
	}

    public void editString(String s, String message) {
        setting.edit().putString(s, message).apply();
    }

    public void editBoolean(String s, boolean message) {
        setting.edit().putBoolean(s, message).apply();
    }

    public void editint(String s, int message) {
        setting.edit().putInt(s, message).apply();
    }

    public int getint(String s, int message) {
        return setting.getInt(s, message);
    }

    public boolean getBoolean(String s, boolean message) {
        return setting.getBoolean(s, message);
    }

    public String getString(String s, String message) {
        return setting.getString(s, message);
    }

	public void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

	public String getImei(){
		TelephonyManager tm=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}
	//判断网络状态
	public static boolean detect(Activity act) {  
		ConnectivityManager manager = (ConnectivityManager) act  
			.getApplicationContext().getSystemService(  
			Context.CONNECTIVITY_SERVICE);  
		if (manager == null) {  
			return false;  
		}  
		NetworkInfo networkinfo = manager.getActiveNetworkInfo();  
		if (networkinfo == null || !networkinfo.isAvailable()) {  
			return false;  
		}  
		return true;  
    }
	
	//上传数据Title为标题，Message为内容，User为用户名
	public void saveData(String Title,String Message,String User){
		Data bmobdata = new Data();
		bmobdata.setTitle(Title);
		bmobdata.setMessage(Message);
		bmobdata.setUser(User);
		bmobdata.save(this, new SaveListener() {
				@Override
				public void onSuccess() {
					showToast("上传代码成功!");
				}
				@Override
				public void onFailure(int code, String arg0) {
					showToast("上传代码失败:"+arg0);
				}
			});
	}
	
}
