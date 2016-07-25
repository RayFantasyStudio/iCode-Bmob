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
import android.content.res.*;

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

	//检测输入框是否符合规则
	public boolean isCharacter(int i,int j,int a,int b,int c,int d){
		if(i>=a&&i<=b&&j>=c&&j<=d){
			return true;
		}else{
			return false;
		}
	}
	
	//比较两个String不想等返回true
	public boolean noEquals(String a,String b){
		if(a.equals(b)){
			return false;
		}else{
			return true;
		}
	}
	
	//判断是否为空
	public String isStringNull(String s){
		return s.equals(null) ? "" : s;
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
	
	public ColorStateList createSelector(int color1,int color2,int color3,int color4) { 
		int statePressed = android.R.attr.state_pressed; 
		int stateFocesed = android.R.attr.state_focused; 
		int[][] state = {{statePressed},{-statePressed},{stateFocesed},{-stateFocesed}}; 
		int[] color = {color1, color2, color3, color4}; 
		ColorStateList colorStateList = new ColorStateList(state,color); 
		return colorStateList; 
    }
	
	public void saveData(String Data,String UserName,String HeadColor,String Title,String Message){
		Data data = new Data();
		data.setUser(UserName);
		data.setHeadColor(HeadColor);
		data.setTitle(Title);
		data.setMessage(Message);
		
		data.save(new SaveListener<String>() {
				@Override
				public void done(String objectId, BmobException e) {
					if(e==null){
						showToast("上传代码成功");
					}else{
						showToast("上传失败："+e.getMessage()+","+e.getErrorCode());
					}
				}
			});
	}
}
