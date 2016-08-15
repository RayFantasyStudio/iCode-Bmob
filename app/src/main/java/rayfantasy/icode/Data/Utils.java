package rayfantasy.icode.Data;

import android.os.*;

public class Utils
{
	
	public static String getBmobAppId(){
		return "5854190bd0548915f2ee096bbbb45870";
	}
	
	public static String getiCodePath(){
		return Environment.getExternalStorageDirectory().getPath()+"/.iCode";
	}
}
