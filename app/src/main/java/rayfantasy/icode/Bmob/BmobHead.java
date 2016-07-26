package rayfantasy.icode.Bmob;

import cn.bmob.v3.*;
import cn.bmob.v3.datatype.*;

public class BmobHead extends BmobObject
{
	private String HeadName;//头像名字=用户邮箱+.png
	private String HeadUri;//头像Uri
	private BmobFile Head;
	
	public BmobHead(){
		
	}
	
	
	public BmobHead(String HeadName,String HeadUri,BmobFile Head){
		this.HeadName=HeadName;
		this.HeadUri=HeadUri;
		this.Head=Head;
	}
	
	public void setHeadName(String HeadName){
		this.HeadName=HeadName;
	}
	
	public String getHeadName(){
		return HeadName;
	}
	
	public void setHeadUri(String HeadUri){
		this.HeadUri=HeadUri;
	}

	public String getHeadUri(){
		return HeadUri;
	}
	
	public void setHead(BmobFile Head){
		this.Head=Head;
	}

	public BmobFile getHead(){
		return Head;
	}
}
