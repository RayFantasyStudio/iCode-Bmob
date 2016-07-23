package rayfantasy.icode.Bmob;

import cn.bmob.v3.*;
import java.io.*;

public class User extends BmobUser
{

	private String About;
	private String userImage_Color;
	private File userImage;
	
	
	public void setAbout(String About){
		this.About=About;
	}
	
	
	public String getAbout(){
		return About;
	}
	
	public void setuserImage_Color(String userImage_Color){
		this.userImage_Color=userImage_Color;
	}


	public String getuserImage_Color(){
		return userImage_Color;
	}
}
