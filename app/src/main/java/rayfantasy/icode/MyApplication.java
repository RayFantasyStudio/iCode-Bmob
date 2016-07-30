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
	
	public void findData(String Email,final String UserName,final String HeadUri,final int HeadVersion){
		BmobQuery<Data> query = new BmobQuery<Data>();
		query.addWhereEqualTo("Email", Email);
		query.setLimit(100);
		query.findObjects(new FindListener<Data>() {
				@Override
				public void done(List<Data> object, BmobException e) {
					
					if(e==null){
						//showToast("共"+object.size()+"条数据");
						for (Data data : object) {
							//更新更换头像Data表中不是最新头像的数据
							if(data.getHeadVersion().intValue()<HeadVersion||noEquals(data.getUser(),UserName)){
								updata(data.getObjectId(),UserName,HeadUri,HeadVersion);
							}
						}
					}
					
				}
			});
	}
	
	public void updata(String id,String UserName,String HeadUri,int HeadVersion){
		Data newData = new Data();
		if(UserName!=null){
			newData.setUser(UserName);
		}
		if(HeadUri!=null){
			newData.setHeadVersion(HeadVersion);
			newData.setHeadUri(HeadUri);
		}
		newData.update(id, new UpdateListener() {
				@Override
				public void done(BmobException e) {
				}
			});
	}
	
	public void upUser(final String HeadUri,String id,final int HeadVersion){
		User newUser = new User();
		newUser.setHeadUri(HeadUri);
		newUser.setId(id);
		newUser.setHeadVersion(HeadVersion);
		User bmobuser=BmobUser.getCurrentUser(User.class);
		//更换头像时更新User表数据
		findData(bmobuser.getEmail(),null,HeadUri,HeadVersion);
		newUser.update(bmobuser.getObjectId(), new UpdateListener() {
				@Override
				public void done(BmobException e) {
				}
			});
	}
	
	public void deleteHead(String id){
		BmobHead gameScore = new BmobHead();
		gameScore.setObjectId(id);
		gameScore.delete(new UpdateListener() {
				@Override
				public void done(BmobException e) {
					if(e==null){
				//		showToast("删除成功");
					}
				}
			});
	}
	
	//上传
	public void saveData(String Data,String UserName,String Email,String HeadUri,int HeadVersion,String HeadColor,String Title,String Message){
		Data data = new Data();
		User user=BmobUser.getCurrentUser(User.class);
		data.setAuthor(user);
		data.setUser(UserName);
		data.setHeadVersion(HeadVersion);
		data.setHeadColor(HeadColor);
		data.setEmail(Email);
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
	
	//评论
	public void saveComment(String id,String content){
		User user = BmobUser.getCurrentUser(User.class);
		Data post = new Data();
		post.setObjectId(id);
		final Comment comment = new Comment();
		comment.setContent(content);
		comment.setData(post);
		comment.setUser(user);
		comment.save(new SaveListener<String>() {

				@Override
				public void done(String objectId,BmobException e) {
					if(e==null){
						showToast("评论发表成功");
					}else{
						showToast("失败："+e.getMessage());
					}
				}

			});
	}
	
	public void uploadHead(final String HeadName,final String file,final int HeadVersion){
		final BmobFile bmobFile = new BmobFile(new File(file));
		bmobFile.uploadblock(new UploadFileListener() {
				@Override
				public void done(BmobException p1)
				{
					if(p1==null){
						insertObject(new BmobHead(HeadName,bmobFile.getFileUrl(),bmobFile),file,HeadName,bmobFile.getFileUrl(),HeadVersion);
					}
				}
			});
	}
	
	/** 创建操作
	 * insertObject
	 * @return void
	 * @throws
	 */
	private void insertObject(final BmobObject obj,final String file,final String HeadName,final String HeadUrl,final int HeadVersion){
		obj.save(new SaveListener<String>() {
				@Override
				public void done(String s, BmobException e) {
					if(e==null){
						//Drawable Image=new BitmapDrawable(BitmapFactory.decodeFile(file));
						//saveFile(Image,HeadName+"_"+HeadVersion+".png");
						upUser(HeadUrl,obj.getObjectId(),HeadVersion);
						showToast("头像上传成功");
					}else{
						showToast("创建数据失败：" + e.getErrorCode()+",msg = "+e.getMessage());
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
