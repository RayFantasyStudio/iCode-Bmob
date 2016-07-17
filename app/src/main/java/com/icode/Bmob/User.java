package com.icode.Bmob;

import cn.bmob.v3.*;

public class User extends BmobUser
{

	private String About;
	
	public void setAbout(String About){
		this.About=About;
	}
	
	
	public String getAbout(){
		return About;
	}

}
