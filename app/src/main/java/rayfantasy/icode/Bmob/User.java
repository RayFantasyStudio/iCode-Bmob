package rayfantasy.icode.Bmob;

import cn.bmob.v3.*;
import java.io.*;
import cn.bmob.v3.datatype.*;

public class User extends BmobUser
{
	private String About;
	private BmobFile Head;	
	private String HeadColor;
	private String HeadUri;
	private Integer HeadVersion;
	
	public void setAbout(String About){
		this.About=About;
	}
	
	public void setHeadColor(String HeadColor){
		this.HeadColor=HeadColor;
	}
	
	public String getAbout(){
		return About;
	}

	public String getHeadColor(){
		return HeadColor;
	}
	
	public BmobFile getHead(){
		return Head;
	}
	
	public void setHead(BmobFile head){
		this.Head = head;
	}
	
	public String getHeadUri(){
		return HeadUri;
	}

	public void setHeadUri(String HeadUri){
		this.HeadUri = HeadUri;
	}
	
	public void setHeadVersion(Integer HeadVersion){
		this.HeadVersion=HeadVersion;
	}
	
	public Integer getHeadVersion(){
		return HeadVersion;
	}
}
