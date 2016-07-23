package rayfantasy.icode;

import android.app.*;
import android.content.*;
import android.net.*;
import android.preference.*;
import android.telephony.*;
import android.widget.*;

import cn.bmob.v3.*;
import cn.bmob.v3.listener.*;

import rayfantasy.icode.Bmob.*;
import android.graphics.*;
import com.tencent.bugly.crashreport.*;
import rayfantasy.icode.Data.*;
import android.content.pm.*;
import android.support.design.widget.*;
import cn.bmob.v3.exception.*;

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
		initbugly();
	}
	
	private void initbugly(){
		CrashReport.initCrashReport(getApplicationContext(),Var.String("BuglyID"),false);
	}

	public void initSign(){
        try
        {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            int code = sign.hashCode();
            int official = Var.Int("official");
            int debug = Var.Int("debug");

            if (code != official)
            {
                if (code != debug){
                    Toast.makeText(this,"Sign failed!",0).show();
                }
                else{
                    Toast.makeText(this,"Debug Version!" ,0).show();
                }
            }
        }
        catch (PackageManager.NameNotFoundException e){
        }
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
	
	//String转int
	public int getHeadColor(String s){
		return Integer.valueOf(s).intValue();
	}

	public void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
	
	public void showSnackBar(Activity a,String Message){
		Snackbar.make(a.getCurrentFocus(), Message, Snackbar.LENGTH_SHORT).show();
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
		showToast("当前无网络");
	}

	//检测输入框是否符合规则
	public boolean isCharacter(int i,int j,int a,int b,int c,int d){
		if(i>=a&&i<=b&&j>=c&&j<=d){
			return true;
		}else{
			return false;
		}
	}
	
	//获取imei
	public String getImei(){
		TelephonyManager tm=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}
	
	//判断网络状态
	public static boolean isNetwork(Activity a) {  
		ConnectivityManager manager = (ConnectivityManager)a.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);  
		if (manager == null) {  
			return false;  
		}  
		NetworkInfo networkinfo = manager.getActiveNetworkInfo();  
		if (networkinfo == null || !networkinfo.isAvailable()) {  
			return false;  
		}  
		return true;  
    }
	
	public String getUserRandomColor(){
		return Color.rgb((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255))+"";
	}
	
}
