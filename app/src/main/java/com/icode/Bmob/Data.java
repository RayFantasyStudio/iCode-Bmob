package com.icode.Bmob;

import cn.bmob.v3.*;

public class Data extends BmobObject
{
	private String Title;
	private String Message;
    private Number index;
	private String userName;
	
    public Data() {
        this.setTableName("Data");
    }

    public String getTitle() {
        return Title;
    }

	public String getMessage(){
		return Message;
	}

	public Number getindex(){
		return index;
	}
	
	
	
	public void setMessage(String Message){
		this.Message=Message;
	}

	public void setTitle(String Title){
		this.Title=Title;
	}

	public void setindex(Number index){
		this.index=index;
	}
	
	public void setUser(String User){
		this.userName=User;
	}
	public String getUser(){
		return userName;
	}
	
}
