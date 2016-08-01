package rayfantasy.icode;

import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.content.res.*;
import android.graphics.*;
import android.net.*;
import android.os.*;
import android.preference.*;
import android.support.design.widget.*;
import android.telephony.*;
import android.util.*;
import android.widget.*;
import cn.bmob.v3.*;
import cn.bmob.v3.datatype.*;
import cn.bmob.v3.exception.*;
import cn.bmob.v3.listener.*;
import com.tencent.bugly.crashreport.*;
import de.hdodenhof.circleimageview.*;
import java.io.*;
import java.util.*;
import rayfantasy.icode.Bmob.*;
import rayfantasy.icode.Data.*;

public class MyApplication extends Application
{
	private SharedPreferences setting;
	private Context context;
	private String path=Environment.getExternalStorageDirectory().getPath()+"/.iCode";
	
	public static final String Bmob_APPID="ed40b22516d8679a79eca831e77ec5ad";

	public void onCreate() {
        super.onCreate();
		setting = PreferenceManager.getDefaultSharedPreferences(this);
        
		context=this;
		initbugly();
		initBmob();
	}

	private void initBmob()
	{
		Bmob.initialize(this, Bmob_APPID);
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
	/*public static boolean isNetwork(Activity a) {  
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
	*/
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
	
	//激活邮箱
	public void EmailVerified(final String email){
		BmobUser.requestEmailVerify(email, new UpdateListener() {
				@Override
				public void done(BmobException e) {
					if(e==null){
						showToast("请求验证邮件成功，请到" + email + "邮箱中进行激活。");
					}else{
						showToast("错误码："+e.getErrorCode()+",错误原因："+e.getLocalizedMessage());
					}
				}
			});
	}
	
	public void downloadFile(BmobFile file,String fileName,final CircleImageView civ){
		//允许设置下载文件的存储路径，默认下载文件的目录为：context.getApplicationContext().getCacheDir()+"/bmob/"
		File saveFile = new File(path+"/cache",fileName+".png");
		file.download(saveFile, new DownloadFileListener() {
				@Override
				public void onStart() {
					//toast("开始下载...");
				}
				@Override
				public void done(String savePath,BmobException e) {
					if(e==null){
						civ.setBackgroundResource(0);
						civ.setImageBitmap(BitmapFactory.decodeFile(savePath));
					}else{
						//toast("下载失败："+e.getErrorCode()+","+e.getMessage());
						civ.setImageResource(0);
					}
				}
				//下载进度
				@Override
				public void onProgress(Integer value, long newworkSpeed) {
				}

			});
	}
	
	
	public boolean isFile(String file){
		File f=new File(path+file);
		if(f.exists()){
			return true;
		}else{
			return false;
		}
	}
	
	
}
