package rayfantasy.icode.Bmob;

import cn.bmob.v3.*;
import java.io.*;

public class User extends BmobUser
{

	private String About;
	private String HeadColor;
	private File Head;	
	
	public void setAbout(String About){
		this.About=About;
	}
	
	
	public String getAbout(){
		return About;
	}
	
	public void setHeadColor(String HeadColor){
		this.HeadColor=HeadColor;
	}


	public String getHeadColor(){
		return HeadColor;
	}
}
