package rayfantasy.icode.Bmob;

import cn.bmob.v3.*;

public class Data extends BmobObject
{
	private String Title="Title";
	private String Message="Message";
   	private String UserName="UserName";
	private Integer Head_Color=0;
	
    public Data() {
        this.setTableName("Data");
    }

    public String getTitle() {
        return Title;
    }

	public String getMessage(){
		return Message;
	}

	public void setMessage(String Message){
		this.Message=Message;
	}

	public void setTitle(String Title){
		this.Title=Title;
	}

	public void setUser(String User){
		this.UserName=User;
	}
	public String getUser(){
		return UserName;
	}
	
	public void setHead_Color(Integer Head_Color){
		this.Head_Color=Head_Color;
	}


	public Integer getHead_Color(){
		return Head_Color;
	}
}
